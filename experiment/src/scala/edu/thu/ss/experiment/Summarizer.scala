package edu.thu.ss.experiment

import scala.collection.mutable.HashMap
import scala.collection.SortedMap
import java.util.TreeMap
import java.io.PrintWriter
import java.io.File
import java.io.BufferedReader
import java.io.FileReader
import scala.collection.JavaConverters._

case class Record(var value: Double, var count: Int = 1) {

}

class Statistics {

  val values = new HashMap[String, Record]();

  def add(key: String, value: Double) {
    val record = values.get(key);
    record match {
      case Some(r) =>
        r.value += value;
        r.count += 1;
      case None =>
        values.put(key, new Record(value))
    }
  }

}

class Experiment {
  val map = new TreeMap[String, Statistics];

  def getStatistics(value: String): Statistics = {
    var stat = map.get(value);
    if (stat == null) {
      stat = new Statistics();
      map.put(value, stat);
    }
    return stat;
  }

}

class Summarizer {

  val simpleTotalTime = "simple_total_time";

  val simplePartitions = "simple_partitions";

  val rangeTotalTime = "range_total_time";

  val rangePartitions = "range_partitions";

  val rangeHits = "range_hits";

  val rangeTime = "range_time";

  val rangeSMTTime = "range_smt_time";

  val keys = Array(simpleTotalTime,
    simplePartitions,
    rangeTime,
    rangeHits,
    rangeSMTTime,
    rangeTotalTime,
    rangePartitions);

  def summarize(inputDir: String, outputPath: String) {

    val experiment = new Experiment;

    val input = new File(inputDir);
    val inputs = input.listFiles();

    inputs.withFilter(_.getName.contains("-")).foreach { file =>
      val name = file.getName();

      val parts = name.split("-");

      val expr = parts(0);
      val value = parts(1);
      val n = parts(2).toInt;

      val stat = experiment.getStatistics(value);

      parseFile(expr, file, stat);

    }

    outputExperiment(outputPath, inputDir, experiment);

    println("success");
  }

  private def parseFile(expr: String, file: File, stat: Statistics) {
    val reader = new BufferedReader(new FileReader(file));

    var line: String = reader.readLine();

    while (line != null) {
      if (line.contains("###")) {
        line = line.replace("###", "");
        val parts = line.split(":");
        val param = parts(0);
        val value = parts(1).toDouble;
        expr match {
          case "simple" =>
            param match {
              case "partitions" =>
                stat.add(simplePartitions, value);
              case "track-time" =>
                stat.add(simpleTotalTime, value);
              case _ =>
            }
          case "range" =>
            param match {
              case "partitions" =>
                stat.add(rangePartitions, value);
              case "smt-time" =>
                stat.add(rangeSMTTime, value);
              case "track-time" =>
                stat.add(rangeTotalTime, value);
              case "range-time" =>
                stat.add(rangeTime, value);
              case "range-hits" =>
                stat.add(rangeHits, value);
              case _ =>
            }
        }
      }
      line = reader.readLine();
    }
    reader.close();

  }

  private def outputExperiment(path: String, param: String, experiment: Experiment) {
    val file = new File(path);
    if (file.exists()) {
      file.delete();
    }

    val parent = file.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }

    val writer = new PrintWriter(file);
    writer.print(path);
    writer.print('\t');
    keys.foreach { key =>
      writer.print(key);
      writer.print('\t');
    }

    writer.println();
    experiment.map.keySet().asScala.foreach { value =>
      writer.print(value);
      writer.print('\t');
      val stat = experiment.map.get(value);
      keys.foreach { key =>
        val record = stat.values.getOrElse(key, null);
        if (record == null) {
          writer.print("N/A");
        } else {
          writer.print((record.value / record.count).formatted("%.2f"));
        }
        writer.print('\t');

      }
      writer.println();
    }

    writer.flush();
    writer.close();
  }
}