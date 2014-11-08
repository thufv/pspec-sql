package org.apache.spark.sql.execution;
public abstract interface Command {
  /**
   * A concrete command should override this lazy field to wrap up any side effects caused by the
   * command or any other computation that should be evaluated exactly once. The value of this field
   * can be used as the contents of the corresponding RDD generated from the physical plan of this
   * command.
   * <p>
   * The <code>execute()</code> method of all the physical command classes should reference <code>sideEffectResult</code>
   * so that the command can be executed eagerly right after the command query is created.
   */
  protected  scala.collection.Seq<java.lang.Object> sideEffectResult () ;
}
