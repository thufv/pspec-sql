package org.apache.spark.sql.hive;
/**
 * Internal helper class that saves an RDD using a Hive OutputFormat.
 * It is based on {@link SparkHadoopWriter}.
 */
public  class SparkHiveWriterContainer implements org.apache.spark.Logging, org.apache.spark.mapred.SparkHadoopMapRedUtil, scala.Serializable {
  static public  org.apache.hadoop.fs.Path createPathFromString (java.lang.String path, org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  public   SparkHiveWriterContainer (org.apache.hadoop.mapred.JobConf jobConf, org.apache.spark.sql.hive.ShimFileSinkDesc fileSinkConf) { throw new RuntimeException(); }
  private  java.util.Date now () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.plan.TableDesc tableDesc () { throw new RuntimeException(); }
  protected  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.JobConf> conf () { throw new RuntimeException(); }
  private  int jobID () { throw new RuntimeException(); }
  private  int splitID () { throw new RuntimeException(); }
  private  int attemptID () { throw new RuntimeException(); }
  private  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.JobID> jID () { throw new RuntimeException(); }
  private  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.TaskAttemptID> taID () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.exec.FileSinkOperator.RecordWriter writer () { throw new RuntimeException(); }
  protected  org.apache.hadoop.mapred.OutputCommitter committer () { throw new RuntimeException(); }
  protected  org.apache.hadoop.mapred.JobContext jobContext () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.TaskAttemptContext taskContext () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.io.HiveOutputFormat<java.lang.Object, org.apache.hadoop.io.Writable> outputFormat () { throw new RuntimeException(); }
  public  void driverSideSetup () { throw new RuntimeException(); }
  public  void executorSideSetup (int jobId, int splitId, int attemptId) { throw new RuntimeException(); }
  protected  java.lang.String getOutputName () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.exec.FileSinkOperator.RecordWriter getLocalFileWriter (org.apache.spark.sql.Row row) { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
  public  void commitJob () { throw new RuntimeException(); }
  protected  void initWriters () { throw new RuntimeException(); }
  protected  void commit () { throw new RuntimeException(); }
  private  void setIDs (int jobId, int splitId, int attemptId) { throw new RuntimeException(); }
  private  void setConfParams () { throw new RuntimeException(); }
}
