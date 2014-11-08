package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Utility trait for classes that want to log data. Creates a SLF4J logger for the class and allows
 * logging messages at different levels using methods that only evaluate parameters lazily if the
 * log level is enabled.
 * <p>
 * NOTE: DO NOT USE this class outside of Spark. It is intended as an internal utility.
 *       This will likely be changed or removed in future releases.
 */
public abstract interface Logging {
  static private  boolean initialized () { throw new RuntimeException(); }
  static public  java.lang.Object initLock () { throw new RuntimeException(); }
  private  org.slf4j.Logger log_ () ;
  protected  java.lang.String logName () ;
  protected  org.slf4j.Logger log () ;
  protected  void logInfo (scala.Function0<java.lang.String> msg) ;
  protected  void logDebug (scala.Function0<java.lang.String> msg) ;
  protected  void logTrace (scala.Function0<java.lang.String> msg) ;
  protected  void logWarning (scala.Function0<java.lang.String> msg) ;
  protected  void logError (scala.Function0<java.lang.String> msg) ;
  protected  void logInfo (scala.Function0<java.lang.String> msg, java.lang.Throwable throwable) ;
  protected  void logDebug (scala.Function0<java.lang.String> msg, java.lang.Throwable throwable) ;
  protected  void logTrace (scala.Function0<java.lang.String> msg, java.lang.Throwable throwable) ;
  protected  void logWarning (scala.Function0<java.lang.String> msg, java.lang.Throwable throwable) ;
  protected  void logError (scala.Function0<java.lang.String> msg, java.lang.Throwable throwable) ;
  protected  boolean isTraceEnabled () ;
  private  void initializeIfNecessary () ;
  private  void initializeLogging () ;
}
