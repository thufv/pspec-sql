package org.apache.spark.rdd;
public  class PairRDDFunctionsSuite extends org.scalatest.FunSuite implements org.apache.spark.SharedSparkContext {
  public   PairRDDFunctionsSuite () { throw new RuntimeException(); }
  // no position
  private  class StratifiedAuxiliary {
    public   StratifiedAuxiliary () { throw new RuntimeException(); }
    public  scala.Function1<java.lang.Object, java.lang.String> stratifier (double fractionPositive) { throw new RuntimeException(); }
    public  boolean checkSize (boolean exact, boolean withReplacement, long expected, long actual, double p) { throw new RuntimeException(); }
    public  void testSampleExact (org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.String, java.lang.Object>> stratifiedData, double samplingRate, long seed, long n) { throw new RuntimeException(); }
    public  void testSample (org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.String, java.lang.Object>> stratifiedData, double samplingRate, long seed, long n) { throw new RuntimeException(); }
    public  void testBernoulli (org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.String, java.lang.Object>> stratifiedData, boolean exact, double samplingRate, long seed, long n) { throw new RuntimeException(); }
    public  void testPoisson (org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.String, java.lang.Object>> stratifiedData, boolean exact, double samplingRate, long seed, long n) { throw new RuntimeException(); }
  }
  // not preceding
  private  org.apache.spark.rdd.PairRDDFunctionsSuite.StratifiedAuxiliary$ StratifiedAuxiliary () { throw new RuntimeException(); }
}
