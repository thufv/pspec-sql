package org.apache.spark;
/**
 * Internal helper class that saves an RDD using a Hadoop OutputFormat.
 * <p>
 * Saves the RDD using a JobConf, which should contain an output key class, an output value class,
 * a filename to write to, etc, exactly like in a Hadoop MapReduce job.
 */
private  class SparkHadoopWriter implements org.apache.spark.Logging, org.apache.hadoop.mapred.SparkHadoopMapRedUtil, scala.Serializable {
  static public  org.apache.hadoop.mapred.JobID createJobID (java.util.Date time, int id) { throw new RuntimeException(); }
  static public  org.apache.hadoop.fs.Path createPathFromString (java.lang.String path, org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  public   SparkHadoopWriter (org.apache.hadoop.mapred.JobConf jobConf) { throw new RuntimeException(); }
  private  java.util.Date now () { throw new RuntimeException(); }
  private  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.JobConf> conf () { throw new RuntimeException(); }
  private  int jobID () { throw new RuntimeException(); }
  private  int splitID () { throw new RuntimeException(); }
  private  int attemptID () { throw new RuntimeException(); }
  private  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.JobID> jID () { throw new RuntimeException(); }
  private  org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.TaskAttemptID> taID () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.RecordWriter<java.lang.Object, java.lang.Object> writer () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.OutputFormat<java.lang.Object, java.lang.Object> format () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.OutputCommitter committer () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.JobContext jobContext () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.TaskAttemptContext taskContext () { throw new RuntimeException(); }
  public  void preSetup () { throw new RuntimeException(); }
  public  void setup (int jobid, int splitid, int attemptid) { throw new RuntimeException(); }
  public  void open () { throw new RuntimeException(); }
  public  void write (java.lang.Object key, java.lang.Object value) { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
  public  void commit () { throw new RuntimeException(); }
  public  void commitJob () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.OutputFormat<java.lang.Object, java.lang.Object> getOutputFormat () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.OutputCommitter getOutputCommitter () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.JobContext getJobContext () { throw new RuntimeException(); }
  private  org.apache.hadoop.mapred.TaskAttemptContext getTaskContext () { throw new RuntimeException(); }
  private  void setIDs (int jobid, int splitid, int attemptid) { throw new RuntimeException(); }
}
