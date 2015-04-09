package org.apache.spark.sql.hive

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import scala.collection.JavaConversions._
import org.apache.spark.sql.Row
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.catalyst.checker.PrivacyException
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.NumericType
import edu.thu.ss.spec.global.MetaManager
import org.apache.spark.sql.catalyst.checker.dp.TableInfo
import org.apache.spark.sql.catalyst.checker.dp.AttributeInfo
import org.apache.spark.sql.catalyst.expressions.Attribute
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.MapType
import org.apache.spark.sql.types.ArrayType
import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.catalyst.checker.util.TypeUtil
import org.apache.spark.sql.catalyst.expressions.Literal
import edu.thu.ss.spec.meta.BaseType
import edu.thu.ss.spec.meta
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil
import edu.thu.ss.spec.meta.MetaRegistry
import org.apache.spark.rdd.RDD
import edu.thu.ss.spec.meta.CompositeType
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.checker.dp.AttributeInfo

class HiveTableInfo(val hive: HiveContext) extends TableInfo with Logging {
  private class Table {
    private val infos: Map[String, AttributeInfo] = new HashMap;

    def get(column: String): AttributeInfo = {
      val range = infos.get(column);
      range match {
        case Some(r) => r
        case _ => null;
      }
    }

    def put(column: String, range: AttributeInfo) {
      infos.put(column, range);
    }

  }

  private val tableInfos: Map[String, Map[String, Table]] = new HashMap;

  def get(dbName: String, tableName: String, columnName: String): AttributeInfo = {
    val database = tableInfos.get(dbName);
    val info = database match {
      case Some(d) => {
        val table = d.get(tableName);
        table match {
          case Some(t) => {
            t.get(columnName);
          }
          case _ => throw new PrivacyException(s"table $tableName not exist.");
        }
      }
      case _ => throw new PrivacyException(s"database $dbName not exist.");
    }
    if (info == null) {
      return updateRange(dbName, tableName, columnName);
    } else {
      return info;
    }

  }

  def get(tableName: String, columnName: String): AttributeInfo = {
    get("default", tableName, columnName);
  }

  private def put(dbName: String, tableName: String, columnName: String, info: AttributeInfo) {
    val db = tableInfos.getOrElseUpdate(dbName, new HashMap);
    val table = db.getOrElseUpdate(tableName, new Table);
    table.put(columnName, info);
  }

  def initialize() {
    val result = hive.sql("show databases").collect;
    result.foreach(row => {
      val db = row.getString(0);
      hive.sql(s"use $db");
      queryDatabase(db);
    });

    hive.sql("use default");
  }

  //TODO luochen add support for complex types, for map type, only predefined keys are queried
  private def queryDatabase(database: String) {
    val rdd = hive.sql("show tables").collect;
    rdd.foreach(row => {
      val table = row.getString(0);
      val meta = MetaManager.get(database, table);
      val result = hive.catalog.lookupRelation(database :: table :: Nil);
      val relation = result match {
        case r: MetastoreRelation => r;
        case _ => null;
      }
      val attributes = relation.attributes.map(attr => {
        val baseType =
          if (meta != null) {
            meta.lookup(database, table, attr.name);
          } else {
            null;
          }
        collectAttribute(attr, baseType);
      }).filter(_ != null);

      if (!attributes.isEmpty) {
        val row = queryRange(table, attributes);
        resolveResult(attributes, row, database, table, meta);
      }
    });

  }

  private def collectAttribute(attribute: Attribute, baseType: BaseType): (Attribute, Seq[String]) = {
    attribute.dataType match {
      case numeric: NumericType => {
        (attribute, Nil);
      }
      case complex if (!complex.isPrimitive) => {
        val types = new ListBuffer[String];
        resolveSubtypes(complex, types, attribute.name, baseType);
        if (!types.isEmpty) {
          (attribute, types.map(TypeUtil.getColumnString(_)));
        } else {
          null;
        }
      }
      case _ => {
        null;
      }
    }
  }

  private def resolveSubtypes(dataType: DataType, list: ListBuffer[String], prefix: String, baseType: BaseType) {
    val compositeType = CheckerUtil.asType(baseType, classOf[meta.CompositeType]);
    if (compositeType != null) {
      //should be the end
      compositeType.getExtractOperations().values().foreach(extract => {
        val function = hive.functionRegistry.lookupFunction(extract.name, Seq(AttributeReference("", dataType)()));
        function match {
          case _: HiveSimpleUdf | _: HiveGenericUdf => {
            if (function.dataType.isInstanceOf[NumericType]) {
              val typeString = TypeUtil.concatComplexAttribute(prefix, extract.name);
            }
          }
          case _ =>
        }
      });
      return ;
    }

    dataType match {
      case struct: StructType => {
        struct.fields.foreach(field => {
          val typeString = TypeUtil.concatComplexAttribute(prefix, TypeUtil.toFieldString(field));
          field.dataType match {
            case numeric: NumericType => {
              list.append(typeString);
            }
            case complex if (!complex.isPrimitive) => {
              val structType = CheckerUtil.asType(baseType, classOf[meta.StructType]);
              if (structType != null) {
                resolveSubtypes(complex, list, typeString, structType.getFieldType(field.name));
              } else {
                resolveSubtypes(complex, list, typeString, null);
              }
            }
            case _ =>
          }
        });
      }
      case map: MapType => {
        val mapType = CheckerUtil.asType(baseType, classOf[meta.MapType]);
        map.valueType match {
          case numeric: NumericType => {
            if (mapType != null) {
              mapType.getEntries().keys.foreach(key => {
                val typeString = TypeUtil.concatComplexAttribute(prefix, TypeUtil.toItemString(Literal(key), classOf[MapType]));
                list.append(typeString);
              });
            }
          }
          case complex if (!complex.isPrimitive) => {
            if (mapType != null) {
              mapType.getEntries().values.foreach(entry => {
                val typeString = TypeUtil.concatComplexAttribute(prefix, TypeUtil.toItemString(Literal(entry.key), classOf[MapType]));
                resolveSubtypes(complex, list, typeString, entry.valueType);
              });
            }
          }
          case _ =>
        }
      }
      case array: ArrayType => {
        val typeString = TypeUtil.concatComplexAttribute(prefix, TypeUtil.toItemString(Literal("0"), classOf[ArrayType]));
        array.elementType match {
          case numeric: NumericType => {
            list.append(typeString);
          }
          case complex if (!complex.isPrimitive) => {
            val arrayType = CheckerUtil.asType(baseType, classOf[meta.ArrayType]);
            if (arrayType != null) {
              resolveSubtypes(complex, list, typeString, arrayType.getItemType());
            } else {
              resolveSubtypes(complex, list, typeString, null);
            }
          }
          case _ =>
        }
      }
      case _ =>
    }
  }

  private def resolveResult(attributes: Seq[(Attribute, Seq[String])], row: Row, database: String, table: String, meta: MetaRegistry) {

    var columnIndex = 0;
    for (i <- 0 to attributes.length - 1) {
      //add multiplicity information
      val column = attributes(i)._1.name;
      var multiplicity: Option[Int] = None;
      if (meta != null && meta.isJoinable(database, table, column)) {
        val value = meta.getMultiplicity(database, table, column);
        if (value != null) {
          multiplicity = Some(value);
        } else {
          multiplicity = Some(queryMultiplicity(table, column));
        }
      }

      val seq = attributes(i)._2;
      if (seq.isEmpty) {
        put(database, table, column, new AttributeInfo(row(2 * columnIndex), row(2 * columnIndex + 1), multiplicity));
        columnIndex += 1;
      } else {
        seq.foreach(attr => {
          put(database, table, attr, new AttributeInfo(row(2 * columnIndex), row(2 * columnIndex + 1), multiplicity));
          columnIndex += 1;
        });
      }

    }
  }

  private def updateRange(db: String, table: String, column: String): AttributeInfo = {
    val transformed = TypeUtil.toSQLString(column);
    val row = queryRange(table, Seq((null, Seq(transformed))));
    val info = new AttributeInfo(row(0), row(1), None);
    put(db, table, column, info);
    return info;
  }

  private def queryRange(table: String, attributes: Seq[(Attribute, Seq[String])]): Row = {
    val sql = new StringBuilder;
    sql.append("select ");
    var i = 0;
    val columns = attributes.map(t => {
      val attr = t._1;
      val seq = t._2;
      if (seq.isEmpty) {
        val name = attr.name;
        s"min($name), max($name)";
      } else {
        seq.map(name => {
          val transformed = TypeUtil.toSQLString(name);
          s"min($transformed), max($transformed)"
        }).mkString(",");

      }
    }).mkString(",");
    sql.append(columns);
    sql.append(s" from $table");
    logWarning(sql.toString);
    val result = hive.sql(sql.toString).collect;
    //only the first row is effective
    result(0);
  }

  private def queryMultiplicity(table: String, column: String): Int = {
    val sql = s"""select max(tmp)
                  from (select count($column) as tmp
                        from $table
                        where $column != 0
                        group by $column) x""";

    val result = hive.sql(sql).collect;
    logWarning(sql);
    result(0).getLong(0).toInt;
  }

}