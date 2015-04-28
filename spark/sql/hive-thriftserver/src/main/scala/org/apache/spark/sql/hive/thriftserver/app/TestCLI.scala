package org.apache.spark.sql.hive.thriftserver.app

import org.apache.spark.sql.hive.thriftserver.SparkSQLCLIDriver
import scala.math.BigDecimal

/**
 * start spark-sql in eclipse
 */
object TestCLI extends App {
  System.setProperty("spark.privacy.refine", "false");
  System.setProperty("spark.privacy.tracking.index", "true");
  System.setProperty("spark.master", "local");
  SparkSQLCLIDriver.main(args);
}


object Main extends App{
  val runtime = Runtime.getRuntime();
  
}

