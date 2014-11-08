package org.apache.spark.sql.hive.thrifserver

import org.apache.spark.sql.hive.thriftserver.SparkSQLCLIDriver

object TestCLI {

  def main(args: Array[String]) {
    System.setProperty("spark.master", "local");
    SparkSQLCLIDriver.main(args);
  }
}