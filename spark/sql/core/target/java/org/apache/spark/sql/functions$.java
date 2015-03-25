package org.apache.spark.sql;
// no position
// not preceding
public  class functions$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final functions$ MODULE$ = null;
  /**
   * Aggregate function: returns the number of distinct items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column countDistinct (org.apache.spark.sql.Column expr, org.apache.spark.sql.Column... exprs) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the number of distinct items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column countDistinct (java.lang.String columnName, java.lang.String... columnNames) { throw new RuntimeException(); }
  /**
   * Returns the first column that is not null.
   * <pre><code>
   *   df.select(coalesce(df("a"), df("b")))
   * </code></pre>
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column coalesce (org.apache.spark.sql.Column... e) { throw new RuntimeException(); }
  public   functions$ () { throw new RuntimeException(); }
  private  org.apache.spark.sql.Column toColumn (org.apache.spark.sql.catalyst.expressions.Expression expr) { throw new RuntimeException(); }
  /**
   * Returns a {@link Column} based on the given column name.
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column col (java.lang.String colName) { throw new RuntimeException(); }
  /**
   * Returns a {@link Column} based on the given column name. Alias of {@link col}.
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column column (java.lang.String colName) { throw new RuntimeException(); }
  /**
   * Creates a {@link Column} of literal value.
   * <p>
   * The passed in object is returned directly if it is already a {@link Column}.
   * If the object is a Scala Symbol, it is converted into a {@link Column} also.
   * Otherwise, a new {@link Column} is created to represent the literal value.
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column lit (Object literal) { throw new RuntimeException(); }
  /**
   * Returns a sort expression based on ascending order of the column.
   * {{
   *   // Sort by dept in ascending order, and then age in descending order.
   *   df.sort(asc("dept"), desc("age"))
   * }}
   * <p>
   * @group sort_funcs
   */
  public  org.apache.spark.sql.Column asc (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Returns a sort expression based on the descending order of the column.
   * {{
   *   // Sort by dept in ascending order, and then age in descending order.
   *   df.sort(asc("dept"), desc("age"))
   * }}
   * <p>
   * @group sort_funcs
   */
  public  org.apache.spark.sql.Column desc (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the sum of all values in the expression.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column sum (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the sum of all values in the given column.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column sum (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the sum of distinct values in the expression.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column sumDistinct (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the sum of distinct values in the expression.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column sumDistinct (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the number of items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column count (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the number of items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column count (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the number of distinct items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column countDistinct (org.apache.spark.sql.Column expr, scala.collection.Seq<org.apache.spark.sql.Column> exprs) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the number of distinct items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column countDistinct (java.lang.String columnName, scala.collection.Seq<java.lang.String> columnNames) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the approximate number of distinct items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column approxCountDistinct (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the approximate number of distinct items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column approxCountDistinct (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the approximate number of distinct items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column approxCountDistinct (org.apache.spark.sql.Column e, double rsd) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the approximate number of distinct items in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column approxCountDistinct (java.lang.String columnName, double rsd) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the average of the values in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column avg (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the average of the values in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column avg (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the first value in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column first (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the first value of a column in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column first (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the last value in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column last (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the last value of the column in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column last (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the minimum value of the expression in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column min (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the minimum value of the column in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column min (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the maximum value of the expression in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column max (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Aggregate function: returns the maximum value of the column in a group.
   * <p>
   * @group agg_funcs
   */
  public  org.apache.spark.sql.Column max (java.lang.String columnName) { throw new RuntimeException(); }
  /**
   * Returns the first column that is not null.
   * <pre><code>
   *   df.select(coalesce(df("a"), df("b")))
   * </code></pre>
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column coalesce (scala.collection.Seq<org.apache.spark.sql.Column> e) { throw new RuntimeException(); }
  /**
   * Unary minus, i.e. negate the expression.
   * <pre><code>
   *   // Select the amount column and negates all values.
   *   // Scala:
   *   df.select( -df("amount") )
   *
   *   // Java:
   *   df.select( negate(df.col("amount")) );
   * </code></pre>
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column negate (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Inversion of boolean expression, i.e. NOT.
   * {{
   *   // Scala: select rows that are not active (isActive === false)
   *   df.filter( !df("isActive") )
   * <p>
   *   // Java:
   *   df.filter( not(df.col("isActive")) );
   * }}
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column not (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Converts a string expression to upper case.
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column upper (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Converts a string exprsesion to lower case.
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column lower (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Computes the square root of the specified float value.
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column sqrt (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Computes the absolutle value.
   * <p>
   * @group normal_funcs
   */
  public  org.apache.spark.sql.Column abs (org.apache.spark.sql.Column e) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 0 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function0<RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$1) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 1 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function1<A1, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$2, scala.reflect.api.TypeTags.TypeTag<A1> evidence$3) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 2 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function2<A1, A2, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$4, scala.reflect.api.TypeTags.TypeTag<A1> evidence$5, scala.reflect.api.TypeTags.TypeTag<A2> evidence$6) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 3 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function3<A1, A2, A3, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$7, scala.reflect.api.TypeTags.TypeTag<A1> evidence$8, scala.reflect.api.TypeTags.TypeTag<A2> evidence$9, scala.reflect.api.TypeTags.TypeTag<A3> evidence$10) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 4 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function4<A1, A2, A3, A4, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$11, scala.reflect.api.TypeTags.TypeTag<A1> evidence$12, scala.reflect.api.TypeTags.TypeTag<A2> evidence$13, scala.reflect.api.TypeTags.TypeTag<A3> evidence$14, scala.reflect.api.TypeTags.TypeTag<A4> evidence$15) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 5 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function5<A1, A2, A3, A4, A5, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$16, scala.reflect.api.TypeTags.TypeTag<A1> evidence$17, scala.reflect.api.TypeTags.TypeTag<A2> evidence$18, scala.reflect.api.TypeTags.TypeTag<A3> evidence$19, scala.reflect.api.TypeTags.TypeTag<A4> evidence$20, scala.reflect.api.TypeTags.TypeTag<A5> evidence$21) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 6 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function6<A1, A2, A3, A4, A5, A6, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$22, scala.reflect.api.TypeTags.TypeTag<A1> evidence$23, scala.reflect.api.TypeTags.TypeTag<A2> evidence$24, scala.reflect.api.TypeTags.TypeTag<A3> evidence$25, scala.reflect.api.TypeTags.TypeTag<A4> evidence$26, scala.reflect.api.TypeTags.TypeTag<A5> evidence$27, scala.reflect.api.TypeTags.TypeTag<A6> evidence$28) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 7 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function7<A1, A2, A3, A4, A5, A6, A7, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$29, scala.reflect.api.TypeTags.TypeTag<A1> evidence$30, scala.reflect.api.TypeTags.TypeTag<A2> evidence$31, scala.reflect.api.TypeTags.TypeTag<A3> evidence$32, scala.reflect.api.TypeTags.TypeTag<A4> evidence$33, scala.reflect.api.TypeTags.TypeTag<A5> evidence$34, scala.reflect.api.TypeTags.TypeTag<A6> evidence$35, scala.reflect.api.TypeTags.TypeTag<A7> evidence$36) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 8 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function8<A1, A2, A3, A4, A5, A6, A7, A8, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$37, scala.reflect.api.TypeTags.TypeTag<A1> evidence$38, scala.reflect.api.TypeTags.TypeTag<A2> evidence$39, scala.reflect.api.TypeTags.TypeTag<A3> evidence$40, scala.reflect.api.TypeTags.TypeTag<A4> evidence$41, scala.reflect.api.TypeTags.TypeTag<A5> evidence$42, scala.reflect.api.TypeTags.TypeTag<A6> evidence$43, scala.reflect.api.TypeTags.TypeTag<A7> evidence$44, scala.reflect.api.TypeTags.TypeTag<A8> evidence$45) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 9 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$46, scala.reflect.api.TypeTags.TypeTag<A1> evidence$47, scala.reflect.api.TypeTags.TypeTag<A2> evidence$48, scala.reflect.api.TypeTags.TypeTag<A3> evidence$49, scala.reflect.api.TypeTags.TypeTag<A4> evidence$50, scala.reflect.api.TypeTags.TypeTag<A5> evidence$51, scala.reflect.api.TypeTags.TypeTag<A6> evidence$52, scala.reflect.api.TypeTags.TypeTag<A7> evidence$53, scala.reflect.api.TypeTags.TypeTag<A8> evidence$54, scala.reflect.api.TypeTags.TypeTag<A9> evidence$55) { throw new RuntimeException(); }
  /**
   * Defines a user-defined function of 10 arguments as user-defined function (UDF).
   * The data types are automatically inferred based on the function's signature.
   * <p>
   * @group udf_funcs
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction udf (scala.Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, RT> f, scala.reflect.api.TypeTags.TypeTag<RT> evidence$56, scala.reflect.api.TypeTags.TypeTag<A1> evidence$57, scala.reflect.api.TypeTags.TypeTag<A2> evidence$58, scala.reflect.api.TypeTags.TypeTag<A3> evidence$59, scala.reflect.api.TypeTags.TypeTag<A4> evidence$60, scala.reflect.api.TypeTags.TypeTag<A5> evidence$61, scala.reflect.api.TypeTags.TypeTag<A6> evidence$62, scala.reflect.api.TypeTags.TypeTag<A7> evidence$63, scala.reflect.api.TypeTags.TypeTag<A8> evidence$64, scala.reflect.api.TypeTags.TypeTag<A9> evidence$65, scala.reflect.api.TypeTags.TypeTag<A10> evidence$66) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 0 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function0<?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 1 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function1<?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 2 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function2<?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 3 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function3<?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2, org.apache.spark.sql.Column arg3) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 4 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function4<?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2, org.apache.spark.sql.Column arg3, org.apache.spark.sql.Column arg4) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 5 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function5<?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2, org.apache.spark.sql.Column arg3, org.apache.spark.sql.Column arg4, org.apache.spark.sql.Column arg5) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 6 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function6<?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2, org.apache.spark.sql.Column arg3, org.apache.spark.sql.Column arg4, org.apache.spark.sql.Column arg5, org.apache.spark.sql.Column arg6) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 7 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function7<?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2, org.apache.spark.sql.Column arg3, org.apache.spark.sql.Column arg4, org.apache.spark.sql.Column arg5, org.apache.spark.sql.Column arg6, org.apache.spark.sql.Column arg7) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 8 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function8<?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2, org.apache.spark.sql.Column arg3, org.apache.spark.sql.Column arg4, org.apache.spark.sql.Column arg5, org.apache.spark.sql.Column arg6, org.apache.spark.sql.Column arg7, org.apache.spark.sql.Column arg8) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 9 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function9<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2, org.apache.spark.sql.Column arg3, org.apache.spark.sql.Column arg4, org.apache.spark.sql.Column arg5, org.apache.spark.sql.Column arg6, org.apache.spark.sql.Column arg7, org.apache.spark.sql.Column arg8, org.apache.spark.sql.Column arg9) { throw new RuntimeException(); }
  /**
   * Call a Scala function of 10 arguments as user-defined function (UDF). This requires
   * you to specify the return data type.
   * <p>
   * @group udf_funcs
   */
  public  org.apache.spark.sql.Column callUDF (scala.Function10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType, org.apache.spark.sql.Column arg1, org.apache.spark.sql.Column arg2, org.apache.spark.sql.Column arg3, org.apache.spark.sql.Column arg4, org.apache.spark.sql.Column arg5, org.apache.spark.sql.Column arg6, org.apache.spark.sql.Column arg7, org.apache.spark.sql.Column arg8, org.apache.spark.sql.Column arg9, org.apache.spark.sql.Column arg10) { throw new RuntimeException(); }
}
