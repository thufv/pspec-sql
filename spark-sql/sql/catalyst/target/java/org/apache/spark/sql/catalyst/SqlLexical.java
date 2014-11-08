package org.apache.spark.sql.catalyst;
public  class SqlLexical extends scala.util.parsing.combinator.lexical.StdLexical {
  public  scala.collection.Seq<java.lang.String> keywords () { throw new RuntimeException(); }
  // not preceding
  public   SqlLexical (scala.collection.Seq<java.lang.String> keywords) { throw new RuntimeException(); }
  public  class FloatLit extends scala.util.parsing.combinator.token.Tokens.Token implements scala.Product, scala.Serializable {
    public  java.lang.String chars () { throw new RuntimeException(); }
    // not preceding
    public   FloatLit (java.lang.String chars) { throw new RuntimeException(); }
    public  java.lang.String toString () { throw new RuntimeException(); }
  }
  // no position
  public  class FloatLit extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.sql.catalyst.SqlLexical.FloatLit> implements scala.Serializable {
    public   FloatLit () { throw new RuntimeException(); }
  }
  public  scala.util.parsing.combinator.Parsers.Parser<scala.util.parsing.combinator.token.Tokens.Token> token () { throw new RuntimeException(); }
  public  scala.util.parsing.combinator.Parsers.Parser<java.lang.Object> identChar () { throw new RuntimeException(); }
  public  scala.util.parsing.combinator.Parsers.Parser<java.lang.Object> whitespace () { throw new RuntimeException(); }
  /** Generate all variations of upper and lower case of a given string */
  public  scala.collection.immutable.Stream<java.lang.String> allCaseVersions (java.lang.String s, java.lang.String prefix) { throw new RuntimeException(); }
}
