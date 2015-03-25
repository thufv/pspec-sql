package org.apache.spark.sql;
/**
 * Functions for registering user-defined functions. Use {@link SQLContext.udf} to access this.
 */
public  class UDFRegistration implements org.apache.spark.Logging {
  public   UDFRegistration (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.analysis.FunctionRegistry functionRegistry () { throw new RuntimeException(); }
  protected  void registerPython (java.lang.String name, byte[] command, java.util.Map<java.lang.String, java.lang.String> envVars, java.util.List<java.lang.String> pythonIncludes, java.lang.String pythonExec, java.util.List<org.apache.spark.broadcast.Broadcast<org.apache.spark.api.python.PythonBroadcast>> broadcastVars, org.apache.spark.Accumulator<java.util.List<byte[]>> accumulator, java.lang.String stringDataType) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 0 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function0<RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$1) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 1 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function1<A1, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$2, scala.reflect.api.TypeTags.TypeTag<A1> evidence$3) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 2 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function2<A1, A2, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$4, scala.reflect.api.TypeTags.TypeTag<A1> evidence$5, scala.reflect.api.TypeTags.TypeTag<A2> evidence$6) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 3 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function3<A1, A2, A3, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$7, scala.reflect.api.TypeTags.TypeTag<A1> evidence$8, scala.reflect.api.TypeTags.TypeTag<A2> evidence$9, scala.reflect.api.TypeTags.TypeTag<A3> evidence$10) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 4 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function4<A1, A2, A3, A4, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$11, scala.reflect.api.TypeTags.TypeTag<A1> evidence$12, scala.reflect.api.TypeTags.TypeTag<A2> evidence$13, scala.reflect.api.TypeTags.TypeTag<A3> evidence$14, scala.reflect.api.TypeTags.TypeTag<A4> evidence$15) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 5 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function5<A1, A2, A3, A4, A5, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$16, scala.reflect.api.TypeTags.TypeTag<A1> evidence$17, scala.reflect.api.TypeTags.TypeTag<A2> evidence$18, scala.reflect.api.TypeTags.TypeTag<A3> evidence$19, scala.reflect.api.TypeTags.TypeTag<A4> evidence$20, scala.reflect.api.TypeTags.TypeTag<A5> evidence$21) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 6 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function6<A1, A2, A3, A4, A5, A6, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$22, scala.reflect.api.TypeTags.TypeTag<A1> evidence$23, scala.reflect.api.TypeTags.TypeTag<A2> evidence$24, scala.reflect.api.TypeTags.TypeTag<A3> evidence$25, scala.reflect.api.TypeTags.TypeTag<A4> evidence$26, scala.reflect.api.TypeTags.TypeTag<A5> evidence$27, scala.reflect.api.TypeTags.TypeTag<A6> evidence$28) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 7 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function7<A1, A2, A3, A4, A5, A6, A7, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$29, scala.reflect.api.TypeTags.TypeTag<A1> evidence$30, scala.reflect.api.TypeTags.TypeTag<A2> evidence$31, scala.reflect.api.TypeTags.TypeTag<A3> evidence$32, scala.reflect.api.TypeTags.TypeTag<A4> evidence$33, scala.reflect.api.TypeTags.TypeTag<A5> evidence$34, scala.reflect.api.TypeTags.TypeTag<A6> evidence$35, scala.reflect.api.TypeTags.TypeTag<A7> evidence$36) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 8 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function8<A1, A2, A3, A4, A5, A6, A7, A8, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$37, scala.reflect.api.TypeTags.TypeTag<A1> evidence$38, scala.reflect.api.TypeTags.TypeTag<A2> evidence$39, scala.reflect.api.TypeTags.TypeTag<A3> evidence$40, scala.reflect.api.TypeTags.TypeTag<A4> evidence$41, scala.reflect.api.TypeTags.TypeTag<A5> evidence$42, scala.reflect.api.TypeTags.TypeTag<A6> evidence$43, scala.reflect.api.TypeTags.TypeTag<A7> evidence$44, scala.reflect.api.TypeTags.TypeTag<A8> evidence$45) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 9 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$46, scala.reflect.api.TypeTags.TypeTag<A1> evidence$47, scala.reflect.api.TypeTags.TypeTag<A2> evidence$48, scala.reflect.api.TypeTags.TypeTag<A3> evidence$49, scala.reflect.api.TypeTags.TypeTag<A4> evidence$50, scala.reflect.api.TypeTags.TypeTag<A5> evidence$51, scala.reflect.api.TypeTags.TypeTag<A6> evidence$52, scala.reflect.api.TypeTags.TypeTag<A7> evidence$53, scala.reflect.api.TypeTags.TypeTag<A8> evidence$54, scala.reflect.api.TypeTags.TypeTag<A9> evidence$55) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 10 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$56, scala.reflect.api.TypeTags.TypeTag<A1> evidence$57, scala.reflect.api.TypeTags.TypeTag<A2> evidence$58, scala.reflect.api.TypeTags.TypeTag<A3> evidence$59, scala.reflect.api.TypeTags.TypeTag<A4> evidence$60, scala.reflect.api.TypeTags.TypeTag<A5> evidence$61, scala.reflect.api.TypeTags.TypeTag<A6> evidence$62, scala.reflect.api.TypeTags.TypeTag<A7> evidence$63, scala.reflect.api.TypeTags.TypeTag<A8> evidence$64, scala.reflect.api.TypeTags.TypeTag<A9> evidence$65, scala.reflect.api.TypeTags.TypeTag<A10> evidence$66) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 11 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$67, scala.reflect.api.TypeTags.TypeTag<A1> evidence$68, scala.reflect.api.TypeTags.TypeTag<A2> evidence$69, scala.reflect.api.TypeTags.TypeTag<A3> evidence$70, scala.reflect.api.TypeTags.TypeTag<A4> evidence$71, scala.reflect.api.TypeTags.TypeTag<A5> evidence$72, scala.reflect.api.TypeTags.TypeTag<A6> evidence$73, scala.reflect.api.TypeTags.TypeTag<A7> evidence$74, scala.reflect.api.TypeTags.TypeTag<A8> evidence$75, scala.reflect.api.TypeTags.TypeTag<A9> evidence$76, scala.reflect.api.TypeTags.TypeTag<A10> evidence$77, scala.reflect.api.TypeTags.TypeTag<A11> evidence$78) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 12 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$79, scala.reflect.api.TypeTags.TypeTag<A1> evidence$80, scala.reflect.api.TypeTags.TypeTag<A2> evidence$81, scala.reflect.api.TypeTags.TypeTag<A3> evidence$82, scala.reflect.api.TypeTags.TypeTag<A4> evidence$83, scala.reflect.api.TypeTags.TypeTag<A5> evidence$84, scala.reflect.api.TypeTags.TypeTag<A6> evidence$85, scala.reflect.api.TypeTags.TypeTag<A7> evidence$86, scala.reflect.api.TypeTags.TypeTag<A8> evidence$87, scala.reflect.api.TypeTags.TypeTag<A9> evidence$88, scala.reflect.api.TypeTags.TypeTag<A10> evidence$89, scala.reflect.api.TypeTags.TypeTag<A11> evidence$90, scala.reflect.api.TypeTags.TypeTag<A12> evidence$91) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 13 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$92, scala.reflect.api.TypeTags.TypeTag<A1> evidence$93, scala.reflect.api.TypeTags.TypeTag<A2> evidence$94, scala.reflect.api.TypeTags.TypeTag<A3> evidence$95, scala.reflect.api.TypeTags.TypeTag<A4> evidence$96, scala.reflect.api.TypeTags.TypeTag<A5> evidence$97, scala.reflect.api.TypeTags.TypeTag<A6> evidence$98, scala.reflect.api.TypeTags.TypeTag<A7> evidence$99, scala.reflect.api.TypeTags.TypeTag<A8> evidence$100, scala.reflect.api.TypeTags.TypeTag<A9> evidence$101, scala.reflect.api.TypeTags.TypeTag<A10> evidence$102, scala.reflect.api.TypeTags.TypeTag<A11> evidence$103, scala.reflect.api.TypeTags.TypeTag<A12> evidence$104, scala.reflect.api.TypeTags.TypeTag<A13> evidence$105) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 14 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$106, scala.reflect.api.TypeTags.TypeTag<A1> evidence$107, scala.reflect.api.TypeTags.TypeTag<A2> evidence$108, scala.reflect.api.TypeTags.TypeTag<A3> evidence$109, scala.reflect.api.TypeTags.TypeTag<A4> evidence$110, scala.reflect.api.TypeTags.TypeTag<A5> evidence$111, scala.reflect.api.TypeTags.TypeTag<A6> evidence$112, scala.reflect.api.TypeTags.TypeTag<A7> evidence$113, scala.reflect.api.TypeTags.TypeTag<A8> evidence$114, scala.reflect.api.TypeTags.TypeTag<A9> evidence$115, scala.reflect.api.TypeTags.TypeTag<A10> evidence$116, scala.reflect.api.TypeTags.TypeTag<A11> evidence$117, scala.reflect.api.TypeTags.TypeTag<A12> evidence$118, scala.reflect.api.TypeTags.TypeTag<A13> evidence$119, scala.reflect.api.TypeTags.TypeTag<A14> evidence$120) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 15 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object, A15 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$121, scala.reflect.api.TypeTags.TypeTag<A1> evidence$122, scala.reflect.api.TypeTags.TypeTag<A2> evidence$123, scala.reflect.api.TypeTags.TypeTag<A3> evidence$124, scala.reflect.api.TypeTags.TypeTag<A4> evidence$125, scala.reflect.api.TypeTags.TypeTag<A5> evidence$126, scala.reflect.api.TypeTags.TypeTag<A6> evidence$127, scala.reflect.api.TypeTags.TypeTag<A7> evidence$128, scala.reflect.api.TypeTags.TypeTag<A8> evidence$129, scala.reflect.api.TypeTags.TypeTag<A9> evidence$130, scala.reflect.api.TypeTags.TypeTag<A10> evidence$131, scala.reflect.api.TypeTags.TypeTag<A11> evidence$132, scala.reflect.api.TypeTags.TypeTag<A12> evidence$133, scala.reflect.api.TypeTags.TypeTag<A13> evidence$134, scala.reflect.api.TypeTags.TypeTag<A14> evidence$135, scala.reflect.api.TypeTags.TypeTag<A15> evidence$136) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 16 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object, A15 extends java.lang.Object, A16 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$137, scala.reflect.api.TypeTags.TypeTag<A1> evidence$138, scala.reflect.api.TypeTags.TypeTag<A2> evidence$139, scala.reflect.api.TypeTags.TypeTag<A3> evidence$140, scala.reflect.api.TypeTags.TypeTag<A4> evidence$141, scala.reflect.api.TypeTags.TypeTag<A5> evidence$142, scala.reflect.api.TypeTags.TypeTag<A6> evidence$143, scala.reflect.api.TypeTags.TypeTag<A7> evidence$144, scala.reflect.api.TypeTags.TypeTag<A8> evidence$145, scala.reflect.api.TypeTags.TypeTag<A9> evidence$146, scala.reflect.api.TypeTags.TypeTag<A10> evidence$147, scala.reflect.api.TypeTags.TypeTag<A11> evidence$148, scala.reflect.api.TypeTags.TypeTag<A12> evidence$149, scala.reflect.api.TypeTags.TypeTag<A13> evidence$150, scala.reflect.api.TypeTags.TypeTag<A14> evidence$151, scala.reflect.api.TypeTags.TypeTag<A15> evidence$152, scala.reflect.api.TypeTags.TypeTag<A16> evidence$153) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 17 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object, A15 extends java.lang.Object, A16 extends java.lang.Object, A17 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$154, scala.reflect.api.TypeTags.TypeTag<A1> evidence$155, scala.reflect.api.TypeTags.TypeTag<A2> evidence$156, scala.reflect.api.TypeTags.TypeTag<A3> evidence$157, scala.reflect.api.TypeTags.TypeTag<A4> evidence$158, scala.reflect.api.TypeTags.TypeTag<A5> evidence$159, scala.reflect.api.TypeTags.TypeTag<A6> evidence$160, scala.reflect.api.TypeTags.TypeTag<A7> evidence$161, scala.reflect.api.TypeTags.TypeTag<A8> evidence$162, scala.reflect.api.TypeTags.TypeTag<A9> evidence$163, scala.reflect.api.TypeTags.TypeTag<A10> evidence$164, scala.reflect.api.TypeTags.TypeTag<A11> evidence$165, scala.reflect.api.TypeTags.TypeTag<A12> evidence$166, scala.reflect.api.TypeTags.TypeTag<A13> evidence$167, scala.reflect.api.TypeTags.TypeTag<A14> evidence$168, scala.reflect.api.TypeTags.TypeTag<A15> evidence$169, scala.reflect.api.TypeTags.TypeTag<A16> evidence$170, scala.reflect.api.TypeTags.TypeTag<A17> evidence$171) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 18 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object, A15 extends java.lang.Object, A16 extends java.lang.Object, A17 extends java.lang.Object, A18 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$172, scala.reflect.api.TypeTags.TypeTag<A1> evidence$173, scala.reflect.api.TypeTags.TypeTag<A2> evidence$174, scala.reflect.api.TypeTags.TypeTag<A3> evidence$175, scala.reflect.api.TypeTags.TypeTag<A4> evidence$176, scala.reflect.api.TypeTags.TypeTag<A5> evidence$177, scala.reflect.api.TypeTags.TypeTag<A6> evidence$178, scala.reflect.api.TypeTags.TypeTag<A7> evidence$179, scala.reflect.api.TypeTags.TypeTag<A8> evidence$180, scala.reflect.api.TypeTags.TypeTag<A9> evidence$181, scala.reflect.api.TypeTags.TypeTag<A10> evidence$182, scala.reflect.api.TypeTags.TypeTag<A11> evidence$183, scala.reflect.api.TypeTags.TypeTag<A12> evidence$184, scala.reflect.api.TypeTags.TypeTag<A13> evidence$185, scala.reflect.api.TypeTags.TypeTag<A14> evidence$186, scala.reflect.api.TypeTags.TypeTag<A15> evidence$187, scala.reflect.api.TypeTags.TypeTag<A16> evidence$188, scala.reflect.api.TypeTags.TypeTag<A17> evidence$189, scala.reflect.api.TypeTags.TypeTag<A18> evidence$190) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 19 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object, A15 extends java.lang.Object, A16 extends java.lang.Object, A17 extends java.lang.Object, A18 extends java.lang.Object, A19 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$191, scala.reflect.api.TypeTags.TypeTag<A1> evidence$192, scala.reflect.api.TypeTags.TypeTag<A2> evidence$193, scala.reflect.api.TypeTags.TypeTag<A3> evidence$194, scala.reflect.api.TypeTags.TypeTag<A4> evidence$195, scala.reflect.api.TypeTags.TypeTag<A5> evidence$196, scala.reflect.api.TypeTags.TypeTag<A6> evidence$197, scala.reflect.api.TypeTags.TypeTag<A7> evidence$198, scala.reflect.api.TypeTags.TypeTag<A8> evidence$199, scala.reflect.api.TypeTags.TypeTag<A9> evidence$200, scala.reflect.api.TypeTags.TypeTag<A10> evidence$201, scala.reflect.api.TypeTags.TypeTag<A11> evidence$202, scala.reflect.api.TypeTags.TypeTag<A12> evidence$203, scala.reflect.api.TypeTags.TypeTag<A13> evidence$204, scala.reflect.api.TypeTags.TypeTag<A14> evidence$205, scala.reflect.api.TypeTags.TypeTag<A15> evidence$206, scala.reflect.api.TypeTags.TypeTag<A16> evidence$207, scala.reflect.api.TypeTags.TypeTag<A17> evidence$208, scala.reflect.api.TypeTags.TypeTag<A18> evidence$209, scala.reflect.api.TypeTags.TypeTag<A19> evidence$210) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 20 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object, A15 extends java.lang.Object, A16 extends java.lang.Object, A17 extends java.lang.Object, A18 extends java.lang.Object, A19 extends java.lang.Object, A20 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$211, scala.reflect.api.TypeTags.TypeTag<A1> evidence$212, scala.reflect.api.TypeTags.TypeTag<A2> evidence$213, scala.reflect.api.TypeTags.TypeTag<A3> evidence$214, scala.reflect.api.TypeTags.TypeTag<A4> evidence$215, scala.reflect.api.TypeTags.TypeTag<A5> evidence$216, scala.reflect.api.TypeTags.TypeTag<A6> evidence$217, scala.reflect.api.TypeTags.TypeTag<A7> evidence$218, scala.reflect.api.TypeTags.TypeTag<A8> evidence$219, scala.reflect.api.TypeTags.TypeTag<A9> evidence$220, scala.reflect.api.TypeTags.TypeTag<A10> evidence$221, scala.reflect.api.TypeTags.TypeTag<A11> evidence$222, scala.reflect.api.TypeTags.TypeTag<A12> evidence$223, scala.reflect.api.TypeTags.TypeTag<A13> evidence$224, scala.reflect.api.TypeTags.TypeTag<A14> evidence$225, scala.reflect.api.TypeTags.TypeTag<A15> evidence$226, scala.reflect.api.TypeTags.TypeTag<A16> evidence$227, scala.reflect.api.TypeTags.TypeTag<A17> evidence$228, scala.reflect.api.TypeTags.TypeTag<A18> evidence$229, scala.reflect.api.TypeTags.TypeTag<A19> evidence$230, scala.reflect.api.TypeTags.TypeTag<A20> evidence$231) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 21 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object, A15 extends java.lang.Object, A16 extends java.lang.Object, A17 extends java.lang.Object, A18 extends java.lang.Object, A19 extends java.lang.Object, A20 extends java.lang.Object, A21 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$232, scala.reflect.api.TypeTags.TypeTag<A1> evidence$233, scala.reflect.api.TypeTags.TypeTag<A2> evidence$234, scala.reflect.api.TypeTags.TypeTag<A3> evidence$235, scala.reflect.api.TypeTags.TypeTag<A4> evidence$236, scala.reflect.api.TypeTags.TypeTag<A5> evidence$237, scala.reflect.api.TypeTags.TypeTag<A6> evidence$238, scala.reflect.api.TypeTags.TypeTag<A7> evidence$239, scala.reflect.api.TypeTags.TypeTag<A8> evidence$240, scala.reflect.api.TypeTags.TypeTag<A9> evidence$241, scala.reflect.api.TypeTags.TypeTag<A10> evidence$242, scala.reflect.api.TypeTags.TypeTag<A11> evidence$243, scala.reflect.api.TypeTags.TypeTag<A12> evidence$244, scala.reflect.api.TypeTags.TypeTag<A13> evidence$245, scala.reflect.api.TypeTags.TypeTag<A14> evidence$246, scala.reflect.api.TypeTags.TypeTag<A15> evidence$247, scala.reflect.api.TypeTags.TypeTag<A16> evidence$248, scala.reflect.api.TypeTags.TypeTag<A17> evidence$249, scala.reflect.api.TypeTags.TypeTag<A18> evidence$250, scala.reflect.api.TypeTags.TypeTag<A19> evidence$251, scala.reflect.api.TypeTags.TypeTag<A20> evidence$252, scala.reflect.api.TypeTags.TypeTag<A21> evidence$253) { throw new RuntimeException(); }
  /**
   * Register a Scala closure of 22 arguments as user-defined function (UDF).
   * @tparam RT return type of UDF.
   */
  public <RT extends java.lang.Object, A1 extends java.lang.Object, A2 extends java.lang.Object, A3 extends java.lang.Object, A4 extends java.lang.Object, A5 extends java.lang.Object, A6 extends java.lang.Object, A7 extends java.lang.Object, A8 extends java.lang.Object, A9 extends java.lang.Object, A10 extends java.lang.Object, A11 extends java.lang.Object, A12 extends java.lang.Object, A13 extends java.lang.Object, A14 extends java.lang.Object, A15 extends java.lang.Object, A16 extends java.lang.Object, A17 extends java.lang.Object, A18 extends java.lang.Object, A19 extends java.lang.Object, A20 extends java.lang.Object, A21 extends java.lang.Object, A22 extends java.lang.Object> org.apache.spark.sql.UserDefinedFunction register (java.lang.String name, scala.Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, RT> func, scala.reflect.api.TypeTags.TypeTag<RT> evidence$254, scala.reflect.api.TypeTags.TypeTag<A1> evidence$255, scala.reflect.api.TypeTags.TypeTag<A2> evidence$256, scala.reflect.api.TypeTags.TypeTag<A3> evidence$257, scala.reflect.api.TypeTags.TypeTag<A4> evidence$258, scala.reflect.api.TypeTags.TypeTag<A5> evidence$259, scala.reflect.api.TypeTags.TypeTag<A6> evidence$260, scala.reflect.api.TypeTags.TypeTag<A7> evidence$261, scala.reflect.api.TypeTags.TypeTag<A8> evidence$262, scala.reflect.api.TypeTags.TypeTag<A9> evidence$263, scala.reflect.api.TypeTags.TypeTag<A10> evidence$264, scala.reflect.api.TypeTags.TypeTag<A11> evidence$265, scala.reflect.api.TypeTags.TypeTag<A12> evidence$266, scala.reflect.api.TypeTags.TypeTag<A13> evidence$267, scala.reflect.api.TypeTags.TypeTag<A14> evidence$268, scala.reflect.api.TypeTags.TypeTag<A15> evidence$269, scala.reflect.api.TypeTags.TypeTag<A16> evidence$270, scala.reflect.api.TypeTags.TypeTag<A17> evidence$271, scala.reflect.api.TypeTags.TypeTag<A18> evidence$272, scala.reflect.api.TypeTags.TypeTag<A19> evidence$273, scala.reflect.api.TypeTags.TypeTag<A20> evidence$274, scala.reflect.api.TypeTags.TypeTag<A21> evidence$275, scala.reflect.api.TypeTags.TypeTag<A22> evidence$276) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 1 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF1<?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 2 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF2<?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 3 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF3<?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 4 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF4<?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 5 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF5<?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 6 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF6<?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 7 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF7<?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 8 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF8<?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 9 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF9<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 10 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 11 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF11<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 12 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 13 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF13<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 14 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF14<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 15 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 16 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF16<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 17 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF17<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 18 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF18<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 19 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 20 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF20<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 21 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF21<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
  /**
   * Register a user-defined function with 22 arguments.
   */
  public  void register (java.lang.String name, org.apache.spark.sql.api.java.UDF22<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.types.DataType returnType) { throw new RuntimeException(); }
}
