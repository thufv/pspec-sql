

package edu.thu.ss.experiment.query

import org.apache.spark.SparkConf
import java.io.PrintWriter
import java.io.BufferedReader
import org.apache.log4j.PropertyConfigurator
import org.apache.spark.sql.catalyst.checker.dp.TrackerStatistics
import scala.collection.mutable.ArrayBuffer
import java.io.File
import org.apache.spark.sql.catalyst.checker.dp.TrackerStatistics
import java.io.FileWriter
import java.io.FileReader
import org.apache.spark.sql.catalyst.checker.dp.TrackerStatistics
import edu.thu.ss.spec.global.MetaManager
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.catalyst.checker.dp.QueryTracker
import org.apache.spark.sql.catalyst.checker.dp.GlobalBudgetManager
import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import scala.collection.mutable.Set

object Main {

  private def createContext(): SQLContext = {
    PropertyConfigurator.configure("spark-log4j.properties");
    val context = new SQLContext;
    val schema = StructType(
      Seq(
        StructField("age", IntegerType),
        StructField("workclass", StringType),
        StructField("fnlwgt", IntegerType),
        StructField("education", StringType),
        StructField("education-num", IntegerType),
        StructField("marital-status", StringType),
        StructField("occupation", StringType),
        StructField("relationship", StringType),
        StructField("race", StringType),
        StructField("sex", StringType),
        StructField("capital-gain", IntegerType),
        StructField("capital-loss", IntegerType),
        StructField("hours-per-week", IntegerType),
        StructField("native-country", StringType),
        StructField("salary", StringType)));
    context.registerTable("adult", schema);
    return context;
  }

  def main(args: Array[String]) {
    if (args.length != 8) {
      println("Usage: result N Ns us ss Nc index limit");
      return ;
    }

    val path = args(0);
    val N = args(1).toInt;
    val Ns = args(2).toInt;
    val us = args(3).toDouble;
    val ss = args(4).toDouble;
    val Nc = args(5).toInt;

    val index = args(6).toBoolean;
    val limit = args(7).toInt;

    experiment(path, N, Ns, us, ss, Nc, index, limit);

  }

  private def experiment(path: String, N: Int, Ns: Int, us: Double, ss: Double, Nc: Int, index: Boolean, limit: Int) {
    Generator.adult("test.sql", N, Ns, (us, ss), Nc);
    //  sc.conf.set("spark.privacy.tracking.index", "false");
    val context = createContext;
    process(context, "test.sql", index, limit, N);

    val stat = TrackerStatistics.get;
    stat.show;
    val writer = new PrintWriter(new FileWriter(path + "-" + System.currentTimeMillis()));
    if (index) {
      writer.println("usage\ttime\tindex hit");
      writer.println(s"${stat.budgetUsage}/$N\t${stat.averageTime}\t${stat.indexHit}");
    } else {
      writer.println("usage\ttime");
      writer.println(s"${stat.budgetUsage}/$N\t${stat.averageTime}");
    }
    writer.close;
  }

  private def clear(path: String) {
    val file = new File(path);
    if (file.exists()) {
      file.delete();
    }
  }

  private def process(context: SQLContext, input: String, index: Boolean, limit: Int, N: Int) {
    val file = new File(input);
    val sb = new StringBuilder;
    val reader = new BufferedReader(new FileReader(file));
    var line = reader.readLine();

    val budget = new GlobalBudgetManager(100);
    val tracker = QueryTracker.newInstance(budget, true, index, limit);

    var processed = 0;
    val failed = Set.empty[Int];
    while (line != null) {
      if (!StringUtils.isBlank(line)) {
        val sql = line.substring(0, line.length() - 1);
        val plan = context.parseSql(sql).asInstanceOf[Aggregate];
        plan.aggregateExpressions.foreach(agg => agg.foreach(expr => {
          expr match {
            //TODO luochen remove a.sensitivity>0 temporarily
            case a: AggregateExpression => {
              a.enableDP = true;
              a.epsilon = 0.01;
              a.sensitivity = 1;
            }
            case _ =>
          }
        }));

        tracker.track(plan);

        tracker.commit(failed);

        processed += 1;
        println(processed);
        if (processed % 50 == 0) {
          println(s"processed queries:${processed}/${N}");
        }
      }
      line = reader.readLine();
    }

    reader.close();
  }

}