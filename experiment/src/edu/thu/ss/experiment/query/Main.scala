package edu.thu.ss.experiment.query

import java.io.BufferedReader
import java.io.FileReader
import java.io.File
import scala.collection.mutable.ArrayBuffer

object Main {
  def main(args: Array[String]) {
    adult;

  }

  def adult() {
    val generator = new SQLGenerator("adult");
    loadSchema(generator, "adult");

    val attribute = (3, 0.5);
    val range = (0.1, 0.1);
    val complex = (2, 0.5);
    val param = new Parameter(100, attribute, range, complex, "age");
    generator.generate(param, System.out);
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