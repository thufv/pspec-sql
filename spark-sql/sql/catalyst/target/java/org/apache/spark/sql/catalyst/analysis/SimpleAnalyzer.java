package org.apache.spark.sql.catalyst.analysis;
// no position
/**
 * A trivial {@link Analyzer} with an {@link EmptyCatalog} and {@link EmptyFunctionRegistry}. Used for testing
 * when all relations are already filled in and the analyser needs only to resolve attribute
 * references.
 */
public  class SimpleAnalyzer extends org.apache.spark.sql.catalyst.analysis.Analyzer {
}
