package org.apache.spark.util;
/** Provides a basic/boilerplate Iterator implementation. */
private abstract class NextIterator<U extends java.lang.Object> implements scala.collection.Iterator<U> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   NextIterator () { throw new RuntimeException(); }
  private  boolean gotNext () { throw new RuntimeException(); }
  private  U nextValue () { throw new RuntimeException(); }
  private  boolean closed () { throw new RuntimeException(); }
  protected  boolean finished () { throw new RuntimeException(); }
  /**
   * Method for subclasses to implement to provide the next element.
   * <p>
   * If no next element is available, the subclass should set <code>finished</code>
   * to <code>true</code> and may return any value (it will be ignored).
   * <p>
   * This convention is required because <code>null</code> may be a valid value,
   * and using <code>Option</code> seems like it might create unnecessary Some/None
   * instances, given some iterators might be called in a tight loop.
   * <p>
   * @return U, or set 'finished' when done
   */
  protected abstract  U getNext () ;
  /**
   * Method for subclasses to implement when all elements have been successfully
   * iterated, and the iteration is done.
   * <p>
   * <b>Note:</b> <code>NextIterator</code> cannot guarantee that <code>close</code> will be
   * called because it has no control over what happens when an exception
   * happens in the user code that is calling hasNext/next.
   * <p>
   * Ideally you should have another try/catch, as in HadoopRDD, that
   * ensures any resources are closed should iteration fail.
   */
  protected abstract  void close () ;
  /**
   * Calls the subclass-defined close method, but only once.
   * <p>
   * Usually calling <code>close</code> multiple times should be fine, but historically
   * there have been issues with some InputFormats throwing exceptions.
   */
  public  void closeIfNeeded () { throw new RuntimeException(); }
  public  boolean hasNext () { throw new RuntimeException(); }
  public  U next () { throw new RuntimeException(); }
}
