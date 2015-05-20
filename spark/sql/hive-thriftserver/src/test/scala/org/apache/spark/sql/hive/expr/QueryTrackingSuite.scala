package org.apache.spark.sql.hive.expr

import org.scalatest.FunSuite
import scala.collection.immutable
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.hive.thriftserver.SparkSQLCLIDriver
import edu.thu.ss.experiment.query.Generator
import java.io.File
import org.slf4j.Logger
import org.apache.log4j.PropertyConfigurator
import java.io.FileReader
import java.io.BufferedReader
import org.apache.commons.lang.StringUtils
import edu.thu.ss.spec.global.MetaManager
import org.apache.spark.sql.catalyst.checker.SparkChecker
import java.io.BufferedWriter
import java.io.FileWriter
import org.apache.spark.sql.catalyst.checker.dp.TrackerStatistics
import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer

object QueryTrackingSuite {
  private var writer: PrintWriter = null;

  private var usages = new ArrayBuffer[Int];
  private var indexTimes = new ArrayBuffer[Double];
  private var times = new ArrayBuffer[Double];
  private var indexHits = new ArrayBuffer[Int];

  private val repeat = 1;

  private val round = 5;

  object Default {
    val N = 5;
    val Ns = 3;
    val us = 0.1;
    val ss = 0.02;
    val Nc = 2;
  }

  private val sc = {
    val conf = new SparkConf;
    conf.setAppName("luochen");
    conf.setMaster("local");
    conf.set("spark.privacy.refine", "false");
    conf.set("spark.privacy.tracking", "true");
    PropertyConfigurator.configure("spark-log4j.properties");
    new SparkContext(conf);
  }

  private def createHive(): HiveContext = {
    val hive = new HiveContext(sc);
    return hive;
  }

  def main(args: Array[String]) {
    testNs(1);
    testus(1);
    testss(1);
    testNc(1);
  }

  def testN(id: Int) {
    val N = Seq(500, 1000, 1500, 2000, 2500);
    test("N", "test-N" + id, N(_), r =>
      experiment(N(r), Default.Ns, Default.us, Default.ss, Default.Nc));
  };

  def testNs(id: Int) {
    val Ns = Seq(1, 3, 5, 7, 9);
    test("Ns", "test-Ns" + id, Ns(_), r =>
      experiment(Default.N, Ns(r), Default.us, Default.ss, Default.Nc));
  }

  def testus(id: Int) {
    val us = Seq(0.05, 0.1, 0.15, 0.2, 0.25);
    test("us", "test-us" + id, us(_), r =>
      experiment(Default.N, Default.Ns, us(r), Default.ss, Default.Nc));
  }

  def testss(id: Int) {
    val ss = Seq(0.01, 0.02, 0.03, 0.04, 0.05);
    test("ss", "test-ss" + id, ss(_), r =>
      experiment(Default.N, Default.Ns, Default.us, ss(r), Default.Nc));
  }

  def testNc(id: Int) {
    val Nc = Seq(1, 2, 3, 4, 5);
    test("Nc", "test-Nc" + id, Nc(_), r =>
      experiment(Default.N, Default.Ns, Default.us, Default.ss, Nc(r)));
  }

  private def test(param: String, path: String, roundp: Int => Any, roundr: Int => (Int, Double, Double, Int)) {
    beginOutput(path, param);
    for (i <- 0 to round - 1) {
      println(s"start round:$i for $param");
      outputResult(roundp(i), roundr(i));
    }
    endOuptut;
  }

  private def experiment(N: Int, Ns: Int, us: Double, ss: Double, Nc: Int): (Int, Double, Double, Int) = {
    Generator.adult("test.sql", N, Ns, (us, ss), Nc);
    for (i <- 1 to repeat) {
      sc.conf.set("spark.privacy.tracking.index", "false");
      val hive1 = createHive;
      process(hive1, "test.sql", false, N);
      System.gc();

      sc.conf.set("spark.privacy.tracking.index", "true");
      val hive2 = createHive;
      process(hive2, "test.sql", true, N);
      System.gc();
    }
    val result = (usages.sum / repeat, times.sum / repeat, indexTimes.sum / repeat, indexHits.sum / repeat);
    usages.clear;
    times.clear;
    indexTimes.clear;
    indexHits.clear;
    return result;
  }

  private def clear(path: String) {
    val file = new File(path);
    if (file.exists()) {
      file.delete();
    }
  }

  private def process(hive: HiveContext, input: String, index: Boolean, N: Int) {
    val file = new File(input);
    val sb = new StringBuilder;
    val reader = new BufferedReader(new FileReader(file));
    var line = reader.readLine();

    while (line != null) {
      if (!line.startsWith("--")) {
        sb.append(line + "\n");
      }
      line = reader.readLine();
    }
    var processed = 0;
    val user = MetaManager.currentUser();
    for (sql <- sb.toString.split(";")) {
      if (!StringUtils.isBlank(sql)) {
        hive.executeSql(sql).sparkPlan;
        SparkChecker.get.commit(user);
        processed += 1;
        if (processed % 50 == 0) {
          println(s"processed queries:${processed}/${N}");
        }
      }
    }

    reader.close();

    collectResult(index);
  }

  private def collectResult(index: Boolean) {
    val stat = TrackerStatistics.get;
    if (index) {
      usages.append(stat.budgetUsage);
      indexTimes.append(stat.averageTime);
      indexHits.append(stat.indexHit);
    } else {
      times.append(stat.averageTime);
    }
    TrackerStatistics.reset;
  }

  private def beginOutput(path: String, param: String) {
    writer = new PrintWriter(new FileWriter(path));
    writer.println(s"$param\tUsage\tTimes\tIndex Times\tIndex Hits");
  }

  private def outputResult(value: Any, result: (Int, Double, Double, Int)) {
    writer.println(s"$value\t${result._1}\t${result._2}\t${result._3}\t${result._4}");
    writer.flush();
  }

  private def endOuptut() {
    writer.close();
    writer = null;
  }

}