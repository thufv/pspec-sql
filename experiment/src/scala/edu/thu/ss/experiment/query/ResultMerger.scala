package edu.thu.ss.experiment.query

import scala.collection.mutable.HashMap
import java.io.File
import java.io.BufferedReader
import java.io.FileReader
import java.util.SortedMap
import java.util.TreeMap
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Buffer
import java.io.PrintWriter
import java.io.FileWriter

case class Result(usage: Int, total: Int, time: Int, hit: Int = 0) {

}

class ResultMerger {

  val simpleResults = new HashMap[String, SortedMap[Double, Buffer[Result]]];
  val indexResults = new HashMap[String, SortedMap[Double, Buffer[Result]]];

  def process(dirPath: String) {
    val dir = new File(dirPath);
    val files = dir.listFiles();

    files.foreach(file => {
      process(file);
    })

    simpleResults.keys.foreach(param => {
      val simple = simpleResults.get(param);
      val index = indexResults.get(param);

      val writer = new PrintWriter(new FileWriter(param));
      print(writer, simple.get, "simple");
      print(writer, index.get, "index");
      writer.close();
    });
  }

  def print(writer: PrintWriter, results: SortedMap[Double, Buffer[Result]], name: String) {
    writer.println(s"average $name");
    writer.println(s"param\tusage/total\ttime\thit");
    results.keys.foreach(value => {
      val result = results.get(value).reduce((r1, r2) => {
        Result(r1.usage + r2.usage, r1.total + r2.total, r1.time + r2.time, r1.hit + r2.hit);
      });
      val length = results.get(value).length;
      writer.println(s"$value\t${result.usage/length}/${result.total/length}\t${result.time/length}\t${result.hit/length}");
    })
    writer.println();

    writer.println(s"detailed $name");
    writer.println(s"param\tusage/total\ttime\thit");

    results.keys.foreach(value => {
      val buffer = results.get(value);
      buffer.foreach(result => {
        writer.println(s"$value\t${result.usage}/${result.total}\t${result.time}\t${result.hit}");
      });
      writer.println();
    });
  }

  def process(file: File) {
    val name = file.getName();
    val splits = name.split("-");
    if (splits.length != 4) {
      println(s"skip invalid file $name");
      return ;
    }

    val param = splits(0);
    val value = splits(1).toDouble;
    val simple = splits(2) == "simple";
    val reader = new BufferedReader(new FileReader(file));
    reader.readLine();
    val line = reader.readLine();

    val entries = line.split("\t");

    val usage = entries(0).split("/")(0).toInt;
    val total = entries(0).split("/")(1).toInt;
    val time = entries(1).toInt;
    if (simple) {
      val result = Result(usage, total, time);
      val map = simpleResults.getOrElseUpdate(param, new TreeMap);
      val seq = map.getOrElseUpdate(value, new ListBuffer);
      seq.append(result);
    } else {
      val hit = entries(2).toInt;
      val result = Result(usage, total, time, hit);
      val map = indexResults.getOrElseUpdate(param, new TreeMap);
      val seq = map.getOrElseUpdate(value, new ListBuffer);
      seq.append(result);
    }
    reader.close();

  }

}

object ResultMerger {
  def main(args: Array[String]) {
    val merger = new ResultMerger;
    merger.process("/Users/luochen/Desktop/expr/result/");
  }

}