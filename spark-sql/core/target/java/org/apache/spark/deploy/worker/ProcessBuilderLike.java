package org.apache.spark.deploy.worker;
private  interface ProcessBuilderLike {
  static public  java.lang.Object apply (java.lang.ProcessBuilder processBuilder) { throw new RuntimeException(); }
  public abstract  java.lang.Process start () ;
  public abstract  scala.collection.Seq<java.lang.String> command () ;
}
