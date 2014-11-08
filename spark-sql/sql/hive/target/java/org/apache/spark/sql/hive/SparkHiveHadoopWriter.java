package org.apache.spark.sql.hive;
/**
 * Internal helper class that saves an RDD using a Hive OutputFormat.
 * It is based on {@link SparkHadoopWriter}.
 */
private  class SparkHiveHadoopWriter implements org.apache.spark.Logging, org.apache.hadoop.mapred.SparkHadoopMapRedUtil, scala.Serializable {
  static public  org.apache.hadoop.fs.Path createPathFromString (java.lang.String path, org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  public   SparkHiveHadoopWriter (org.apache.hadoop.mapred.JobConf jobConf, org.apache.hadoop.hive.ql.plan.FileSinkDesc fileSinkConf) { throw new RuntimeException(); }
  private  java.util.Date now () { throw new RuntimeException(); }
  private  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.JobConf> conf () { throw new RuntimeException(); }
  private  int jobID () { throw new RuntimeException(); }
  private  int splitID () { throw new RuntimeException(); }
  private  int attemptID () { throw new RuntimeException(); }
  private  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.JobID> jID () { throw new RuntimeException(); }
  private  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.TaskAttemptID> taID () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.exec.FileSinkOperator.RecordWriter writer () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.io.HiveOutputFormat<java.lang.Object, org.apache.hadoop.io.Writable> format () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.OutputCommitter committer () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.JobContext jobContext () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.TaskAttemptContext taskContext () { throw new RuntimeException(); }
  public  void preSetup () { throw new RuntimeException(); }
  public  void setup (int jobid, int splitid, int attemptid) { throw new RuntimeException(); }
  public  void open () { throw new RuntimeException(); }
  public  void write (org.apache.hadoop.io.Writable value) { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
  public  void commit () { throw new RuntimeException(); }
  public  void commitJob () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.io.HiveOutputFormat<java.lang.Object, org.apache.hadoop.io.Writable> getOutputFormat () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.OutputCommitter getOutputCommitter () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.JobContext getJobContext () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.TaskAttemptContext getTaskContext () { throw new RuntimeException(); }
  private  void setIDs (int jobId, int splitId, int attemptId) { throw new RuntimeException(); }
  private  void setConfParams () { throw new RuntimeException(); }
}
