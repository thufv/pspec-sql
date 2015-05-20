package edu.thu.ss.experiment.query

import java.io.BufferedReader
import java.io.FileReader
import java.io.File
import scala.collection.mutable.ArrayBuffer
import java.io.PrintStream
import java.io.FileInputStream

object Generator {

  def adult(path: String, num: Int, simple: Int, ratio: (Double, Double), complex: Int) {
    val generator = new SQLGenerator("adult");
    loadSchema(generator, "adult");
    val param = new Parameter(num, (simple, 0), ratio, (complex, 0), "age");
    val output = new PrintStream(new File(path));
    generator.generate(param, output);
    output.close();
  }

  private def loadSchema(generator: SQLGenerator, path: String) {
    val reader = new BufferedReader(new FileReader(new File(path)));
    var line = reader.readLine();
    while (line != null) {
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
      line = reader.readLine();
    }
    reader.close();
  }
}