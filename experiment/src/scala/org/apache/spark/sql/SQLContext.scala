/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql

import java.beans.Introspector
import java.util.Properties
import scala.collection.JavaConversions._
import scala.collection.immutable
import scala.language.implicitConversions
import scala.reflect.runtime.universe.TypeTag
import org.apache.spark.annotation.{ DeveloperApi, Experimental }
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.analysis._
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.optimizer.{ DefaultOptimizer, Optimizer }
import org.apache.spark.sql.catalyst.plans.logical.{ LocalRelation, LogicalPlan, NoRelation }
import org.apache.spark.sql.catalyst.rules.RuleExecutor
import org.apache.spark.sql.catalyst.{ ScalaReflection, expressions }
import org.apache.spark.sql.types._
import org.apache.spark.util.Utils
import org.apache.spark.sql.sources.LogicalRDD

/**
 * The entry point for working with structured data (rows and columns) in Spark.  Allows the
 * creation of [[DataFrame]] objects as well as the execution of SQL queries.
 *
 * @groupname basic Basic Operations
 * @groupname ddl_ops Persistent Catalog DDL
 * @groupname cachemgmt Cached Table Management
 * @groupname genericdata Generic Data Sources
 * @groupname specificdata Specific Data Sources
 * @groupname config Configuration
 * @groupname dataframes Custom DataFrame Creation
 * @groupname Ungrouped Support functions for language integrated queries.
 */
class SQLContext extends Logging
  with Serializable {

  self =>

  // Note that this is a lazy val so we can override the default value in subclasses.
  protected[sql] lazy val conf: SQLConf = new SQLConf

  /**
   * Set Spark SQL configuration properties.
   *
   * @group config
   */
  def setConf(props: Properties): Unit = conf.setConf(props)

  /**
   * Set the given Spark SQL configuration property.
   *
   * @group config
   */
  def setConf(key: String, value: String): Unit = conf.setConf(key, value)

  /**
   * Return the value of Spark SQL configuration property for the given key.
   *
   * @group config
   */
  def getConf(key: String): String = conf.getConf(key)

  /**
   * Return the value of Spark SQL configuration property for the given key. If the key is not set
   * yet, return `defaultValue`.
   *
   * @group config
   */
  def getConf(key: String, defaultValue: String): String = conf.getConf(key, defaultValue)

  /**
   * Return all the configuration properties that have been set (i.e. not the default).
   * This creates a new copy of the config properties in the form of a Map.
   *
   * @group config
   */
  def getAllConfs: immutable.Map[String, String] = conf.getAllConfs

  @transient
  protected[sql] lazy val catalog: Catalog = new SimpleCatalog(true)

  @transient
  protected[sql] lazy val functionRegistry: FunctionRegistry = new SimpleFunctionRegistry(true)

  @transient
  protected[sql] lazy val analyzer: Analyzer =
    new Analyzer(catalog, functionRegistry, caseSensitive = true);
  @transient
  protected[sql] lazy val optimizer: Optimizer = DefaultOptimizer

  @transient
  protected[sql] val sqlParser = {
    val fallback = new catalyst.SqlParser
    new SparkSQLParser(fallback(_))
  }

  def parseSql(sql: String): LogicalPlan = {
    val plan = sqlParser(sql);
    val analyzed = analyzer(plan);
    val optimized = optimizer(analyzed);
    return optimized;
  }

  @transient
  val udf: UDFRegistration = new UDFRegistration(this)

  /**
   * Returns the names of tables in the current database as an array.
   *
   * @group ddl_ops
   */
  def tableNames(): Array[String] = {
    catalog.getTables(None).map {
      case (tableName, _) => tableName
    }.toArray
  }

  def registerTable(table: String, schema: StructType) = {
    val attrs = schema.toAttributes;
    val rdd = LogicalRDD(table, attrs)(this);
    catalog.registerTable(Seq(table), rdd);
  }

}
