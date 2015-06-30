package org.apache.spark.sql.catalyst.checker

import edu.thu.ss.spec.meta.MetaRegistry
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.analysis.Catalog
import edu.thu.ss.spec.meta.BaseType
import edu.thu.ss.spec.meta.ArrayType
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.PrimitiveType
import org.apache.spark.sql.types.DataType
import edu.thu.ss.spec.meta.CompositeType
import edu.thu.ss.spec.meta.MapType
import org.apache.spark.sql.types
import edu.thu.ss.spec.meta.Database
import org.apache.spark.Logging
import scala.collection.JavaConversions._
import scala.collection.mutable.HashSet

private class MetaChecker extends Logging {

  private var error = false;
  /**
   * check whether column is properly labeled
   */
  def checkMeta(meta: MetaRegistry, catalog: Catalog): Boolean = {
    error = false;
    val databases = meta.getDatabases();
    for (db <- databases) {
      val dbName = Some(db._1); ;
      val tables = db._2.getTables();
      for (t <- tables) {
        val table = t._1;
        val relation = lookupRelation(catalog, dbName, table);
        if (relation == null) {
          error = true;
          logError(s"Error in MetaRegistry, table: ${table} not found in database: ${db._1}.");
        } else {
          t._2.getColumns().values.foreach(c => checkColumn(c.getName(), c.getType(), relation, table));
          t._2.getCondColumns().values.foreach(c => {
            c.getTypes().values.foreach(t => checkColumn(c.getName(), t, relation, table));
          });
          val conds = t._2.getAllConditions();
          val condColumns = new HashSet[String];
          conds.foreach(join => {
            val list = join.getJoinColumns();
            list.foreach(e => condColumns.add(e.column));
            val name = join.getJoinTable();
            val relation = lookupRelation(catalog, dbName, name);
            if (relation == null) {
              error = true;
              logError(s"Error in MetaRegistry, table: $name (joined with ${t._1}) not found in database: ${db._1}.");
            } else {
              val cols = list.map(_.target);
              cols.foreach(checkColumn(_, null, relation, table));
            }
          });
        }
      }
    }
    return error;
  }

  private def checkColumn(name: String, labelType: BaseType, relation: LogicalPlan, table: String) = {
    val attribute = relation.output.find(attr => attr.name == name).getOrElse(null);
    if (attribute == null) {
      error = true;
      logError(s"Error in MetaRegistry. Column: $name not exist in table: ${table}");
    }
    if (labelType != null) {
      if (checkDataType(labelType, attribute.dataType)) {
        error = true;
        logError(s"Error in MetaRegistry. Type mismatch for column: $name in table: $table. Expected type: ${attribute.dataType}");
      }
    }
  }

  private def checkDataType(labelType: BaseType, attrType: DataType): Boolean = {
    var error = false;
    labelType match {
      case _: PrimitiveType =>
      case _: CompositeType =>
      case array: ArrayType => {
        attrType match {
          case attrArray: types.ArrayType => {
            error = array.getAllTypes().values.exists(item => checkDataType(item, attrArray.elementType));
          }
          case _ => error = true;
        }
      }
      case struct: StructType => {
        attrType match {
          case attrStruct: types.StructType => {
            error = struct.getAllTypes().exists(t => {
              val fieldName = t._1;
              val fieldType = t._2;
              val attrField = attrStruct.fields.find(f => f.name == fieldName).getOrElse(null);
              if (attrField == null) {
                true;
              } else {
                checkDataType(fieldType, attrField.dataType);
              }
            });
          }
          case _ => error = true;
        }
      }
      case map: MapType => {
        attrType match {
          case attrMap: types.MapType => {
            error = map.getAllTypes.values.exists(entry => {
              checkDataType(entry, attrMap.valueType);
            });
          }
          case _ => error = true;
        }
      }
    }
    error;
  }

  private def lookupRelation(catalog: Catalog, database: Option[String], table: String): LogicalPlan = {
    try {
      database match {
        case Some(db) => catalog.lookupRelation(db :: table :: Nil, None);
        case _ => catalog.lookupRelation(table :: Nil, None);
      }
    } catch {
      case _: Throwable => null;
    }
  }

}
