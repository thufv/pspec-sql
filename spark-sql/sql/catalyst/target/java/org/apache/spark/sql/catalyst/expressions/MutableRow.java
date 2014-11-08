package org.apache.spark.sql.catalyst.expressions;
/**
 * An extended interface to {@link Row} that allows the values for each column to be updated.  Setting
 * a value through a primitive function implicitly marks that column as not null.
 */
public  interface MutableRow extends org.apache.spark.sql.catalyst.expressions.Row {
  public abstract  void setNullAt (int i) ;
  public abstract  void update (int ordinal, Object value) ;
  public abstract  void setInt (int ordinal, int value) ;
  public abstract  void setLong (int ordinal, long value) ;
  public abstract  void setDouble (int ordinal, double value) ;
  public abstract  void setBoolean (int ordinal, boolean value) ;
  public abstract  void setShort (int ordinal, short value) ;
  public abstract  void setByte (int ordinal, byte value) ;
  public abstract  void setFloat (int ordinal, float value) ;
  public abstract  void setString (int ordinal, java.lang.String value) ;
}
