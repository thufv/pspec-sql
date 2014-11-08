package org.apache.spark.scheduler.cluster;
// no position
private  class CoarseGrainedClusterMessages {
  // no position
  static public  class RetrieveSparkProps$ implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public   RetrieveSparkProps$ () { throw new RuntimeException(); }
  }
  static public  class LaunchTask implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public  org.apache.spark.util.SerializableBuffer data () { throw new RuntimeException(); }
    // not preceding
    public   LaunchTask (org.apache.spark.util.SerializableBuffer data) { throw new RuntimeException(); }
  }
  // no position
  // not preceding
  static public  class LaunchTask$ extends scala.runtime.AbstractFunction1<org.apache.spark.util.SerializableBuffer, org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessages.LaunchTask> implements scala.Serializable {
    public   LaunchTask$ () { throw new RuntimeException(); }
  }
  static public  class KillTask implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public  long taskId () { throw new RuntimeException(); }
    public  java.lang.String executor () { throw new RuntimeException(); }
    public  boolean interruptThread () { throw new RuntimeException(); }
    // not preceding
    public   KillTask (long taskId, java.lang.String executor, boolean interruptThread) { throw new RuntimeException(); }
  }
  // no position
  // not preceding
  static public  class KillTask$ extends scala.runtime.AbstractFunction3<java.lang.Object, java.lang.String, java.lang.Object, org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessages.KillTask> implements scala.Serializable {
    public   KillTask$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class RegisteredExecutor$ implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public   RegisteredExecutor$ () { throw new RuntimeException(); }
  }
  static public  class RegisterExecutorFailed implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public  java.lang.String message () { throw new RuntimeException(); }
    // not preceding
    public   RegisterExecutorFailed (java.lang.String message) { throw new RuntimeException(); }
  }
  // no position
  static public  class RegisterExecutorFailed$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessages.RegisterExecutorFailed> implements scala.Serializable {
    public   RegisterExecutorFailed$ () { throw new RuntimeException(); }
  }
  static public  class RegisterExecutor implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public  java.lang.String executorId () { throw new RuntimeException(); }
    public  java.lang.String hostPort () { throw new RuntimeException(); }
    public  int cores () { throw new RuntimeException(); }
    // not preceding
    public   RegisterExecutor (java.lang.String executorId, java.lang.String hostPort, int cores) { throw new RuntimeException(); }
  }
  // no position
  static public  class RegisterExecutor$ extends scala.runtime.AbstractFunction3<java.lang.String, java.lang.String, java.lang.Object, org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessages.RegisterExecutor> implements scala.Serializable {
    public   RegisterExecutor$ () { throw new RuntimeException(); }
  }
  static public  class StatusUpdate implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public  java.lang.String executorId () { throw new RuntimeException(); }
    public  long taskId () { throw new RuntimeException(); }
    public  scala.Enumeration.Value state () { throw new RuntimeException(); }
    public  org.apache.spark.util.SerializableBuffer data () { throw new RuntimeException(); }
    // not preceding
    public   StatusUpdate (java.lang.String executorId, long taskId, scala.Enumeration.Value state, org.apache.spark.util.SerializableBuffer data) { throw new RuntimeException(); }
  }
  // no position
  static public  class StatusUpdate$ implements scala.Serializable {
    public   StatusUpdate$ () { throw new RuntimeException(); }
    /** Alternate factory method that takes a ByteBuffer directly for the data field */
    public  org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessages.StatusUpdate apply (java.lang.String executorId, long taskId, scala.Enumeration.Value state, java.nio.ByteBuffer data) { throw new RuntimeException(); }
  }
  // no position
  /** Alternate factory method that takes a ByteBuffer directly for the data field */
  static public  class ReviveOffers$ implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public   ReviveOffers$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class StopDriver$ implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public   StopDriver$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class StopExecutor$ implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public   StopExecutor$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class StopExecutors$ implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public   StopExecutors$ () { throw new RuntimeException(); }
  }
  static public  class RemoveExecutor implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public  java.lang.String executorId () { throw new RuntimeException(); }
    public  java.lang.String reason () { throw new RuntimeException(); }
    // not preceding
    public   RemoveExecutor (java.lang.String executorId, java.lang.String reason) { throw new RuntimeException(); }
  }
  // no position
  static public  class RemoveExecutor$ extends scala.runtime.AbstractFunction2<java.lang.String, java.lang.String, org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessages.RemoveExecutor> implements scala.Serializable {
    public   RemoveExecutor$ () { throw new RuntimeException(); }
  }
  static public  class AddWebUIFilter implements org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessage, scala.Product, scala.Serializable {
    public  java.lang.String filterName () { throw new RuntimeException(); }
    public  java.lang.String filterParams () { throw new RuntimeException(); }
    public  java.lang.String proxyBase () { throw new RuntimeException(); }
    // not preceding
    public   AddWebUIFilter (java.lang.String filterName, java.lang.String filterParams, java.lang.String proxyBase) { throw new RuntimeException(); }
  }
  // no position
  static public  class AddWebUIFilter$ extends scala.runtime.AbstractFunction3<java.lang.String, java.lang.String, java.lang.String, org.apache.spark.scheduler.cluster.CoarseGrainedClusterMessages.AddWebUIFilter> implements scala.Serializable {
    public   AddWebUIFilter$ () { throw new RuntimeException(); }
  }
}
