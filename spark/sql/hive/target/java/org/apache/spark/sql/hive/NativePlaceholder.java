package org.apache.spark.sql.hive;
// no position
/**
 * Used when we need to start parsing the AST before deciding that we are going to pass the command
 * back for Hive to execute natively.  Will be replaced with a native command that contains the
 * cmd string.
 */
public  class NativePlaceholder extends org.apache.spark.sql.catalyst.plans.logical.Command implements scala.Product, scala.Serializable {
}
