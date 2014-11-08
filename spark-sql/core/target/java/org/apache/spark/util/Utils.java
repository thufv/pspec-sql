package org.apache.spark.util;
// no position
/**
 * Various utility methods used by Spark.
 */
private  class Utils implements org.apache.spark.Logging {
  static public  java.util.Random random () { throw new RuntimeException(); }
  static public  java.io.File sparkBin (java.lang.String sparkHome, java.lang.String which) { throw new RuntimeException(); }
  /** Serialize an object using Java serialization */
  static public <T extends java.lang.Object> byte[] serialize (T o) { throw new RuntimeException(); }
  /** Deserialize an object using Java serialization */
  static public <T extends java.lang.Object> T deserialize (byte[] bytes) { throw new RuntimeException(); }
  /** Deserialize an object using Java serialization and the given ClassLoader */
  static public <T extends java.lang.Object> T deserialize (byte[] bytes, java.lang.ClassLoader loader) { throw new RuntimeException(); }
  /** Deserialize a Long value (used for {@link org.apache.spark.api.python.PythonPartitioner}) */
  static public  long deserializeLongValue (byte[] bytes) { throw new RuntimeException(); }
  /** Serialize via nested stream using specific serializer */
  static public  void serializeViaNestedStream (java.io.OutputStream os, org.apache.spark.serializer.SerializerInstance ser, scala.Function1<org.apache.spark.serializer.SerializationStream, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /** Deserialize via nested stream using specific serializer */
  static public  void deserializeViaNestedStream (java.io.InputStream is, org.apache.spark.serializer.SerializerInstance ser, scala.Function1<org.apache.spark.serializer.DeserializationStream, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Get the ClassLoader which loaded Spark.
   */
  static public  java.lang.ClassLoader getSparkClassLoader () { throw new RuntimeException(); }
  /**
   * Get the Context ClassLoader on this thread or, if not present, the ClassLoader that
   * loaded Spark.
   * <p>
   * This should be used whenever passing a ClassLoader to Class.ForName or finding the currently
   * active loader when setting up ClassLoader delegation chains.
   */
  static public  java.lang.ClassLoader getContextOrSparkClassLoader () { throw new RuntimeException(); }
  /** Determines whether the provided class is loadable in the current thread. */
  static public  boolean classIsLoadable (java.lang.String clazz) { throw new RuntimeException(); }
  /** Preferred alternative to Class.forName(className) */
  static public  java.lang.Class<?> classForName (java.lang.String className) { throw new RuntimeException(); }
  /**
   * Primitive often used when writing {@link java.nio.ByteBuffer} to {@link java.io.DataOutput}.
   */
  static public  void writeByteBuffer (java.nio.ByteBuffer bb, java.io.ObjectOutput out) { throw new RuntimeException(); }
  static public  boolean isAlpha (char c) { throw new RuntimeException(); }
  /** Split a string into words at non-alphabetic characters */
  static public  scala.collection.Seq<java.lang.String> splitWords (java.lang.String s) { throw new RuntimeException(); }
  static private  scala.collection.mutable.HashSet<java.lang.String> shutdownDeletePaths () { throw new RuntimeException(); }
  static private  scala.collection.mutable.HashSet<java.lang.String> shutdownDeleteTachyonPaths () { throw new RuntimeException(); }
  static public  void registerShutdownDeleteDir (java.io.File file) { throw new RuntimeException(); }
  static public  void registerShutdownDeleteDir (tachyon.client.TachyonFile tachyonfile) { throw new RuntimeException(); }
  static public  boolean hasShutdownDeleteDir (java.io.File file) { throw new RuntimeException(); }
  static public  boolean hasShutdownDeleteTachyonDir (tachyon.client.TachyonFile file) { throw new RuntimeException(); }
  static public  boolean hasRootAsShutdownDeleteDir (java.io.File file) { throw new RuntimeException(); }
  static public  boolean hasRootAsShutdownDeleteDir (tachyon.client.TachyonFile file) { throw new RuntimeException(); }
  /** Create a temporary directory inside the given parent directory */
  static public  java.io.File createTempDir (java.lang.String root) { throw new RuntimeException(); }
  /** Copy all data from an InputStream to an OutputStream */
  static public  long copyStream (java.io.InputStream in, java.io.OutputStream out, boolean closeStreams) { throw new RuntimeException(); }
  /**
   * Construct a URI container information used for authentication.
   * This also sets the default authenticator to properly negotiation the
   * user/password based on the URI.
   * <p>
   * Note this relies on the Authenticator.setDefault being set properly to decode
   * the user name and password. This is currently set in the SecurityManager.
   */
  static public  java.net.URI constructURIForAuthentication (java.net.URI uri, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  /**
   * Download a file requested by the executor. Supports fetching the file in a variety of ways,
   * including HTTP, HDFS and files on a standard filesystem, based on the URL parameter.
   * <p>
   * Throws SparkException if the target file already exists and has different contents than
   * the requested file.
   */
  static public  void fetchFile (java.lang.String url, java.io.File targetDir, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  /**
   * Get the path of a temporary directory.  Spark's local directories can be configured through
   * multiple settings, which are used with the following precedence:
   * <p>
   *   - If called from inside of a YARN container, this will return a directory chosen by YARN.
   *   - If the SPARK_LOCAL_DIRS environment variable is set, this will return a directory from it.
   *   - Otherwise, if the spark.local.dir is set, this will return a directory from it.
   *   - Otherwise, this will return java.io.tmpdir.
   * <p>
   * Some of these configuration options might be lists of multiple paths, but this method will
   * always return a single directory.
   */
  static public  java.lang.String getLocalDir (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  static private  boolean isRunningInYarnContainer (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * Gets or creates the directories listed in spark.local.dir or SPARK_LOCAL_DIRS,
   * and returns only the directories that exist / could be created.
   * <p>
   * If no directories could be created, this will return an empty list.
   */
  static private  java.lang.String[] getOrCreateLocalRootDirs (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /** Get the Yarn approved local directories. */
  static private  java.lang.String getYarnLocalDirs (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * Shuffle the elements of a collection into a random order, returning the
   * result in a new collection. Unlike scala.util.Random.shuffle, this method
   * uses a local random number generator, avoiding inter-thread contention.
   */
  static public <T extends java.lang.Object> scala.collection.Seq<T> randomize (scala.collection.TraversableOnce<T> seq, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  /**
   * Shuffle the elements of an array into a random order, modifying the
   * original array. Returns the original array.
   */
  static public <T extends java.lang.Object> java.lang.Object randomizeInPlace (java.lang.Object arr, java.util.Random rand) { throw new RuntimeException(); }
  /**
   * Get the local host's IP address in dotted-quad format (e.g. 1.2.3.4).
   * Note, this is typically not used from within core spark.
   */
  static public  java.lang.String localIpAddress () { throw new RuntimeException(); }
  static public  java.lang.String localIpAddressHostname () { throw new RuntimeException(); }
  static private  java.lang.String findLocalIpAddress () { throw new RuntimeException(); }
  static private  scala.Option<java.lang.String> customHostname () { throw new RuntimeException(); }
  /**
   * Allow setting a custom host name because when we run on Mesos we need to use the same
   * hostname it reports to the master.
   */
  static public  void setCustomHostname (java.lang.String hostname) { throw new RuntimeException(); }
  /**
   * Get the local machine's hostname.
   */
  static public  java.lang.String localHostName () { throw new RuntimeException(); }
  static public  java.lang.String getAddressHostName (java.lang.String address) { throw new RuntimeException(); }
  static public  void checkHost (java.lang.String host, java.lang.String message) { throw new RuntimeException(); }
  static public  void checkHostPort (java.lang.String hostPort, java.lang.String message) { throw new RuntimeException(); }
  static private  java.util.concurrent.ConcurrentHashMap<java.lang.String, scala.Tuple2<java.lang.String, java.lang.Object>> hostPortParseResults () { throw new RuntimeException(); }
  static public  scala.Tuple2<java.lang.String, java.lang.Object> parseHostPort (java.lang.String hostPort) { throw new RuntimeException(); }
  static private  com.google.common.util.concurrent.ThreadFactoryBuilder daemonThreadFactoryBuilder () { throw new RuntimeException(); }
  /**
   * Create a thread factory that names threads with a prefix and also sets the threads to daemon.
   */
  static public  java.util.concurrent.ThreadFactory namedThreadFactory (java.lang.String prefix) { throw new RuntimeException(); }
  /**
   * Wrapper over newCachedThreadPool. Thread names are formatted as prefix-ID, where ID is a
   * unique, sequentially assigned integer.
   */
  static public  java.util.concurrent.ThreadPoolExecutor newDaemonCachedThreadPool (java.lang.String prefix) { throw new RuntimeException(); }
  /**
   * Wrapper over newFixedThreadPool. Thread names are formatted as prefix-ID, where ID is a
   * unique, sequentially assigned integer.
   */
  static public  java.util.concurrent.ThreadPoolExecutor newDaemonFixedThreadPool (int nThreads, java.lang.String prefix) { throw new RuntimeException(); }
  /**
   * Return the string to tell how long has passed in milliseconds.
   */
  static public  java.lang.String getUsedTimeMs (long startTimeMs) { throw new RuntimeException(); }
  static private  scala.collection.Seq<java.io.File> listFilesSafely (java.io.File file) { throw new RuntimeException(); }
  /**
   * Delete a file or directory and its contents recursively.
   * Don't follow directories if they are symlinks.
   */
  static public  void deleteRecursively (java.io.File file) { throw new RuntimeException(); }
  /**
   * Delete a file or directory and its contents recursively.
   */
  static public  void deleteRecursively (tachyon.client.TachyonFile dir, tachyon.client.TachyonFS client) { throw new RuntimeException(); }
  /**
   * Check to see if file is a symbolic link.
   */
  static public  boolean isSymlink (java.io.File file) { throw new RuntimeException(); }
  /**
   * Finds all the files in a directory whose last modified time is older than cutoff seconds.
   * @param dir  must be the path to a directory, or IllegalArgumentException is thrown
   * @param cutoff measured in seconds. Files older than this are returned.
   */
  static public  scala.collection.Seq<java.io.File> findOldFiles (java.io.File dir, long cutoff) { throw new RuntimeException(); }
  /**
   * Convert a Java memory parameter passed to -Xmx (such as 300m or 1g) to a number of megabytes.
   */
  static public  int memoryStringToMb (java.lang.String str) { throw new RuntimeException(); }
  /**
   * Convert a quantity in bytes to a human-readable string such as "4.0 MB".
   */
  static public  java.lang.String bytesToString (long size) { throw new RuntimeException(); }
  /**
   * Returns a human-readable string representing a duration such as "35ms"
   */
  static public  java.lang.String msDurationToString (long ms) { throw new RuntimeException(); }
  /**
   * Convert a quantity in megabytes to a human-readable string such as "4.0 MB".
   */
  static public  java.lang.String megabytesToString (long megabytes) { throw new RuntimeException(); }
  /**
   * Execute a command in the given working directory, throwing an exception if it completes
   * with an exit code other than 0.
   */
  static public  void execute (scala.collection.Seq<java.lang.String> command, java.io.File workingDir) { throw new RuntimeException(); }
  /**
   * Execute a command in the current working directory, throwing an exception if it completes
   * with an exit code other than 0.
   */
  static public  void execute (scala.collection.Seq<java.lang.String> command) { throw new RuntimeException(); }
  /**
   * Execute a command and get its output, throwing an exception if it yields a code other than 0.
   */
  static public  java.lang.String executeAndGetOutput (scala.collection.Seq<java.lang.String> command, java.io.File workingDir, scala.collection.Map<java.lang.String, java.lang.String> extraEnvironment) { throw new RuntimeException(); }
  /**
   * Execute a block of code that evaluates to Unit, forwarding any uncaught exceptions to the
   * default UncaughtExceptionHandler
   */
  static public  void tryOrExit (scala.Function0<scala.runtime.BoxedUnit> block) { throw new RuntimeException(); }
  /**
   * A regular expression to match classes of the "core" Spark API that we want to skip when
   * finding the call site of a method.
   */
  static private  scala.util.matching.Regex SPARK_CLASS_REGEX () { throw new RuntimeException(); }
  /**
   * When called inside a class in the spark package, returns the name of the user code class
   * (outside the spark package) that called into Spark, as well as which Spark method they called.
   * This is used, for example, to tell users where in their code each RDD got created.
   */
  static public  org.apache.spark.util.CallSite getCallSite () { throw new RuntimeException(); }
  /** Return a string containing part of a file from byte 'start' to 'end'. */
  static public  java.lang.String offsetBytes (java.lang.String path, long start, long end) { throw new RuntimeException(); }
  /**
   * Return a string containing data across a set of files. The <code>startIndex</code>
   * and <code>endIndex</code> is based on the cumulative size of all the files take in
   * the given order. See figure below for more details.
   */
  static public  java.lang.String offsetBytes (scala.collection.Seq<java.io.File> files, long start, long end) { throw new RuntimeException(); }
  /**
   * Clone an object using a Spark serializer.
   */
  static public <T extends java.lang.Object> T clone (T value, org.apache.spark.serializer.SerializerInstance serializer, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  /**
   * Detect whether this thread might be executing a shutdown hook. Will always return true if
   * the current thread is a running a shutdown hook but may spuriously return true otherwise (e.g.
   * if System.exit was just called by a concurrent thread).
   * <p>
   * Currently, this detects whether the JVM is shutting down by Runtime#addShutdownHook throwing
   * an IllegalStateException.
   */
  static public  boolean inShutdown () { throw new RuntimeException(); }
  static public  boolean isSpace (char c) { throw new RuntimeException(); }
  /**
   * Split a string of potentially quoted arguments from the command line the way that a shell
   * would do it to determine arguments to a command. For example, if the string is 'a "b c" d',
   * then it would be parsed as three arguments: 'a', 'b c' and 'd'.
   */
  static public  scala.collection.Seq<java.lang.String> splitCommandString (java.lang.String s) { throw new RuntimeException(); }
  static public  int nonNegativeMod (int x, int mod) { throw new RuntimeException(); }
  static public  int nonNegativeHash (java.lang.Object obj) { throw new RuntimeException(); }
  /** Returns a copy of the system properties that is thread-safe to iterator over. */
  static public  scala.collection.Map<java.lang.String, java.lang.String> getSystemProperties () { throw new RuntimeException(); }
  /**
   * Method executed for repeating a task for side effects.
   * Unlike a for comprehension, it permits JVM JIT optimization
   */
  static public  void times (int numIters, scala.Function0<scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Timing method based on iterations that permit JVM JIT optimization.
   * @param numIters number of iterations
   * @param f function to be executed
   */
  static public  long timeIt (int numIters, scala.Function0<scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Counts the number of elements of an iterator using a while loop rather than calling
   * {@link scala.collection.Iterator#size} because it uses a for loop, which is slightly slower
   * in the current version of Scala.
   */
  static public <T extends java.lang.Object> long getIteratorSize (scala.collection.Iterator<T> iterator) { throw new RuntimeException(); }
  /**
   * Creates a symlink. Note jdk1.7 has Files.createSymbolicLink but not used here
   * for jdk1.6 support.  Supports windows by doing copy, everything else uses "ln -sf".
   * @param src absolute path to the source
   * @param dst relative path for the destination
   */
  static public  void symlink (java.io.File src, java.io.File dst) { throw new RuntimeException(); }
  /** Return the class name of the given object, removing all dollar signs */
  static public  java.lang.String getFormattedClassName (java.lang.Object obj) { throw new RuntimeException(); }
  /** Return an option that translates JNothing to None */
  static public  scala.Option<org.json4s.JsonAST.JValue> jsonOption (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  /** Return an empty JSON object */
  static public  org.json4s.JsonAST.JObject emptyJson () { throw new RuntimeException(); }
  /**
   * Return a Hadoop FileSystem with the scheme encoded in the given path.
   */
  static public  org.apache.hadoop.fs.FileSystem getHadoopFileSystem (java.net.URI path) { throw new RuntimeException(); }
  /**
   * Return a Hadoop FileSystem with the scheme encoded in the given path.
   */
  static public  org.apache.hadoop.fs.FileSystem getHadoopFileSystem (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Return the absolute path of a file in the given directory.
   */
  static public  org.apache.hadoop.fs.Path getFilePath (java.io.File dir, java.lang.String fileName) { throw new RuntimeException(); }
  /**
   * Whether the underlying operating system is Windows.
   */
  static public  boolean isWindows () { throw new RuntimeException(); }
  /**
   * Pattern for matching a Windows drive, which contains only a single alphabet character.
   */
  static public  scala.util.matching.Regex windowsDrive () { throw new RuntimeException(); }
  /**
   * Format a Windows path such that it can be safely passed to a URI.
   */
  static public  java.lang.String formatWindowsPath (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Indicates whether Spark is currently running unit tests.
   */
  static public  boolean isTesting () { throw new RuntimeException(); }
  /**
   * Strip the directory from a path name
   */
  static public  java.lang.String stripDirectory (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Wait for a process to terminate for at most the specified duration.
   * Return whether the process actually terminated after the given timeout.
   */
  static public  boolean waitForProcess (java.lang.Process process, long timeoutMs) { throw new RuntimeException(); }
  /**
   * Return the stderr of a process after waiting for the process to terminate.
   * If the process does not terminate within the specified timeout, return None.
   */
  static public  scala.Option<java.lang.String> getStderr (java.lang.Process process, long timeoutMs) { throw new RuntimeException(); }
  /** 
   * Execute the given block, logging and re-throwing any uncaught exception.
   * This is particularly useful for wrapping code that runs in a thread, to ensure
   * that exceptions are printed, and to avoid having to catch Throwable.
   */
  static public <T extends java.lang.Object> T logUncaughtExceptions (scala.Function0<T> f) { throw new RuntimeException(); }
  /** Returns true if the given exception was fatal. See docs for scala.util.control.NonFatal. */
  static public  boolean isFatalError (java.lang.Throwable e) { throw new RuntimeException(); }
  /**
   * Return a well-formed URI for the file described by a user input string.
   * <p>
   * If the supplied path does not contain a scheme, or is a relative path, it will be
   * converted into an absolute path with a file:// scheme.
   */
  static public  java.net.URI resolveURI (java.lang.String path, boolean testWindows) { throw new RuntimeException(); }
  /** Resolve a comma-separated list of paths. */
  static public  java.lang.String resolveURIs (java.lang.String paths, boolean testWindows) { throw new RuntimeException(); }
  /** Return all non-local paths from a comma-separated list of paths. */
  static public  java.lang.String[] nonLocalPaths (java.lang.String paths, boolean testWindows) { throw new RuntimeException(); }
  /** Return a nice string representation of the exception, including the stack trace. */
  static public  java.lang.String exceptionString (java.lang.Exception e) { throw new RuntimeException(); }
  /** Return a nice string representation of the exception, including the stack trace. */
  static public  java.lang.String exceptionString (java.lang.String className, java.lang.String description, java.lang.StackTraceElement[] stackTrace) { throw new RuntimeException(); }
  /**
   * Convert all spark properties set in the given SparkConf to a sequence of java options.
   */
  static public  scala.collection.Seq<java.lang.String> sparkJavaOpts (org.apache.spark.SparkConf conf, scala.Function1<java.lang.String, java.lang.Object> filterKey) { throw new RuntimeException(); }
  /**
   * Default number of retries in binding to a port.
   */
  static public  int portMaxRetries () { throw new RuntimeException(); }
  /**
   * Attempt to start a service on the given port, or fail after a number of attempts.
   * Each subsequent attempt uses 1 + the port used in the previous attempt (unless the port is 0).
   * <p>
   * @param startPort The initial port to start the service on.
   * @param maxRetries Maximum number of retries to attempt.
   *                   A value of 3 means attempting ports n, n+1, n+2, and n+3, for example.
   * @param startService Function to start service on a given port.
   *                     This is expected to throw java.net.BindException on port collision.
   */
  static public <T extends java.lang.Object> scala.Tuple2<T, java.lang.Object> startServiceOnPort (int startPort, scala.Function1<java.lang.Object, scala.Tuple2<T, java.lang.Object>> startService, java.lang.String serviceName, int maxRetries) { throw new RuntimeException(); }
  /**
   * Return whether the exception is caused by an address-port collision when binding.
   */
  static public  boolean isBindCollision (java.lang.Throwable exception) { throw new RuntimeException(); }
}
