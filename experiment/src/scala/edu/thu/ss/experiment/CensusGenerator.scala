package edu.thu.ss.experiment

import java.io.BufferedReader
import java.io.FileReader
import java.io.File
import scala.collection.mutable.ArrayBuffer
import java.io.PrintStream
import org.apache.spark.sql.types.IntegerType
import org.apache.log4j.PropertyConfigurator
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.sources.LogicalRDD
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.types.StructField
import java.io.FileInputStream
import org.apache.spark.sql.catalyst.plans.logical.Subquery

object CensusGenerator {

  def createSQLContext(): SQLContext = {
    PropertyConfigurator.configure("spark-log4j.properties");
    val context = new SQLContext;
    val schema = StructType(
      Seq(
        StructField("age", IntegerType),
        StructField("class of worker", StringType),
        StructField("detailed industry recode", IntegerType),
        StructField("detailed occupation recode", IntegerType),
        StructField("education", StringType),
        StructField("wage per hour", IntegerType),
        StructField("enroll in edu inst last wk", StringType),
        StructField("marital stat", StringType),
        StructField("major industry code", StringType),
        StructField("major occupation code", StringType),
        StructField("race", StringType),
        StructField("hispanic origin", StringType),
        StructField("sex", StringType),
        StructField("member of a labor union", StringType),
        StructField("reason for unemployment", StringType),

        StructField("full or part time employment stat", StringType),
        StructField("capital gains", IntegerType),
        StructField("capital losses", IntegerType),
        StructField("dividends from stocks", IntegerType),
        StructField("tax filer stat", StringType),
        StructField("region of previous residence", StringType),

        StructField("state of previous residence", StringType),
        StructField("detailed household and family stat", StringType),
        StructField("detailed household summary in household", StringType),
        StructField("instance weight", StringType),
        StructField("migration code-change in msa", StringType),
        StructField("migration code-change in reg", StringType),

        StructField("migration code-move within reg", StringType),
        StructField("live in this house 1 year ago", StringType),
        StructField("migration prev res in sunbelt", StringType),
        StructField("num persons worked for employer", IntegerType),
        StructField("family members under 18", StringType),

        StructField("country of birth father", StringType),
        StructField("country of birth mother", StringType),
        StructField("country of birth self", StringType),
        StructField("citizenship", StringType),
        StructField("own business or self employed", IntegerType),
        StructField("fill inc questionnaire for veteran's admin", StringType),
        StructField("veterans benefits", IntegerType),
        StructField("weeks worked in year", IntegerType),
        StructField("year", IntegerType)));
    context.registerTable("census", schema);
    return context;
  }

  def adult(path: String, params: SQLParams) {
    val generator = new SQLGenerator("census");
    loadSchema(generator, "census");
    val output = new PrintStream(new File(path));
    generator.generate(params, output);
    output.close();
  }

  private def loadSchema(generator: SQLGenerator, path: String) {
    val reader = new BufferedReader(new FileReader(new File(path)));
    var line = reader.readLine();
    while (line != null) {
      if (!line.startsWith("#")) {
        val strs = line.split(":|,");
        val column = strs(0);
        if (strs(1) == "continuous") {
          val min = strs(2).toInt;
          val max = strs(3).toInt;
          generator.addColumn(NumericalColumn(s"`$column`", min, max, false));

        } else {
          val values = new ArrayBuffer[String];
          strs.tail.foreach(values.append(_));
          generator.addColumn(CategoricalColumn(s"`$column`", values));
        }
      }
      line = reader.readLine();
    }
    reader.close();
  }

  def main(args: Array[String]) {
    case class Record(name: String, index: Int, var min: Double, var max: Double) {

    }

    val records = new ListBuffer[Record]();

    val context = createSQLContext();

    val census = context.catalog.lookupRelation(Seq("census")).asInstanceOf[Subquery].child.asInstanceOf[LogicalRDD].schema;

    var index = 0;
    census.fields.foreach { field =>
      if (field.dataType.isInstanceOf[IntegerType]) {
        records.append(new Record(field.name, index, Double.MaxValue, Double.MinValue));
      }
      index += 1;
    }

    val path = "/Users/luochen/Desktop/census/census-income.data";

    val reader = new BufferedReader(new FileReader(path));
    var line = reader.readLine();

    var processed = 0;
    while (line != null) {
      val parts = line.split(", ");
      records.foreach { record =>
        val value = parts(record.index).toDouble;
        if (value > record.max) {
          record.max = value;
        }
        if (value < record.min) {
          record.min = value;
        }
      }
      processed += 1;
      if (processed % 1000 == 0) {
        println(processed);
      }
      line = reader.readLine();
    }
    reader.close();

    records.foreach { println(_) }

  }

}