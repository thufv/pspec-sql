package org.apache.spark.api.python;
/**
 * Internal class that acts as an <code>AccumulatorParam</code> for Python accumulators. Inside, it
 * collects a list of pickled strings that we pass to Python through a socket.
 */
private  class PythonAccumulatorParam implements org.apache.spark.AccumulatorParam<java.util.List<byte[]>> {
  public   PythonAccumulatorParam (java.lang.String serverHost, int serverPort) { throw new RuntimeException(); }
  public  int bufferSize () { throw new RuntimeException(); }
  /** 
   * We try to reuse a single Socket to transfer accumulator updates, as they are all added
   * by the DAGScheduler's single-threaded actor anyway.
   */
  public  java.net.Socket socket () { throw new RuntimeException(); }
  public  java.net.Socket openSocket () { throw new RuntimeException(); }
  public  java.util.List<byte[]> zero (java.util.List<byte[]> value) { throw new RuntimeException(); }
  public  java.util.List<byte[]> addInPlace (java.util.List<byte[]> val1, java.util.List<byte[]> val2) { throw new RuntimeException(); }
}
