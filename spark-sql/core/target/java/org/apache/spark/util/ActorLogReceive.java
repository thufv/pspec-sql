package org.apache.spark.util;
/**
 * A trait to enable logging all Akka actor messages. Here's an example of using this:
 * <p>
 * <pre><code>
 *   class BlockManagerMasterActor extends Actor with ActorLogReceive with Logging {
 *     ...
 *     override def receiveWithLogging = {
 *       case GetLocations(blockId) =&gt;
 *         sender ! getLocations(blockId)
 *       ...
 *     }
 *     ...
 *   }
 * </code></pre>
 * <p>
 */
private abstract interface ActorLogReceive {
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receive () ;
  public abstract  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () ;
  protected abstract  org.slf4j.Logger log () ;
}
