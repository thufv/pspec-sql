package edu.thu.ss.experiment

import java.io.BufferedReader
import org.apache.log4j.PropertyConfigurator
import org.apache.spark.sql.catalyst.checker.dp.TrackerStat
import java.io.File
import org.apache.spark.sql.catalyst.checker.dp.TrackerStat
import java.io.FileReader
import org.apache.spark.sql.catalyst.checker.dp.TrackerStat
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.catalyst.checker.dp.QueryTracker
import org.apache.spark.sql.catalyst.checker.dp.GlobalBudgetManager
import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import scala.collection.mutable.Set
import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.Logging

case class ExperimentParams(N: Int, Ns: Int, us: Double, ss: Double, Nc: Int, range: Boolean, limit: Int) {

}

object ExperimentConf {
  val Max_Track_Time = "5000";

  val Max_Solve_Time = "1000";

}

object Experimenter extends Logging {

  def main(args: Array[String]) {
    val command = args(0);
    command match {
      case "sql" =>
        val path = args(1);
        val N = args(2).toInt;
        val Ns = args(3).toInt;
        val us = args(4).toDouble;
        val ss = args(5).toDouble;
        val Nc = args(6).toInt;
        val param = new SQLParams(N, Ns, (us, ss), Nc, "age");
        CensusGenerator.adult(path, param);
        println(s"generate $path.");
      case "expr" =>
        val sqlPath = args(1);
        val outputPath = args(2);
        val limit = args(3).toInt;
        val range = args(4).toBoolean;
        runExperiment(sqlPath, outputPath, limit, range);
      case "summarize" =>
        val inputDir = args(1);
        val output = args(2);
        val sum = new Summarizer;
        sum.summarize(inputDir, output);
      case _ => println(s"unknown command: $command");
    }

  }

  private def runExperiment(sqlPath: String, outputPath: String, limit: Int, range: Boolean) {
    val context = CensusGenerator.createSQLContext;
    process(context, sqlPath, limit, range);

    //finished
    val stat = TrackerStat.get;
    stat.show(outputPath);
  }

  private def process(context: SQLContext, input: String, limit: Int, range: Boolean) {
    val file = new File(input);
    val sb = new StringBuilder;
    val reader = new BufferedReader(new FileReader(file));
    var line = reader.readLine();

    val budget = new GlobalBudgetManager(Double.MaxValue);
    val tracker = QueryTracker.newInstance(budget, true, range, limit);

    var processed = 0;
    while (line != null) {
      if (!StringUtils.isBlank(line)) {
        val sql = line.substring(0, line.length() - 1);
        val plan = context.parseSql(sql).asInstanceOf[Aggregate];
        plan.aggregateExpressions.foreach(agg => agg.foreach(expr => {
          expr match {
            case a: AggregateExpression => {
              a.enableDP = true;
              a.epsilon = 0.01;
              a.sensitivity = 1;
            }
            case _ =>
          }
        }));

        tracker.track(plan);

        processed += 1;

        logWarning(s"$processed");
        if (processed % 25 == 0) {
          logWarning(s"processed queries:${processed}");
        }
      }
      line = reader.readLine();
    }

    tracker.stop();

    reader.close();
  }

}