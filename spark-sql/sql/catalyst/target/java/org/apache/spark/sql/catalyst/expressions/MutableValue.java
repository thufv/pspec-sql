package org.apache.spark.sql.catalyst.expressions;
/**
 * A parent class for mutable container objects that are reused when the values are changed,
 * resulting in less garbage.  These values are held by a {@link SpecificMutableRow}.
 * <p>
 * The following code was roughly used to generate these objects:
 * <pre><code>
 * val types = "Int,Float,Boolean,Double,Short,Long,Byte,Any".split(",")
 * types.map {tpe =&gt;
 * s"""
 * final class Mutable$tpe extends MutableValue {
 *   var value: $tpe = 0
 *   def boxed = if (isNull) null else value
 *   def update(v: Any) = value = {
 *     isNull = false
 *     v.asInstanceOf[$tpe]
 *   }
 *   def copy() = {
 *     val newCopy = new Mutable$tpe
 *     newCopy.isNull = isNull
 *     newCopy.value = value
 *     newCopy.asInstanceOf[this.type]
 *   }
 * }"""
 * }.foreach(println)
 *
 * types.map { tpe =&gt;
 * s"""
 *   override def set$tpe(ordinal: Int, value: $tpe): Unit = {
 *     val currentValue = values(ordinal).asInstanceOf[Mutable$tpe]
 *     currentValue.isNull = false
 *     currentValue.value = value
 *   }
 *
 *   override def get$tpe(i: Int): $tpe = {
 *     values(i).asInstanceOf[Mutable$tpe].value
 *   }"""
 * }.foreach(println)
 * </code></pre>
 */
public abstract class MutableValue implements scala.Serializable {
  public   MutableValue () { throw new RuntimeException(); }
  public  boolean isNull () { throw new RuntimeException(); }
  public abstract  Object boxed () ;
  public abstract  void update (Object v) ;
  public abstract  org.apache.spark.sql.catalyst.expressions.MutableValue copy () ;
}
