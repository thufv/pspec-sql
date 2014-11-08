package org.apache.spark.sql.catalyst.checker

import java.lang.reflect.Method

import edu.thu.ss.lang.pojo.DesensitizeOperation

trait MetaRegistry {

	def lookup(database: String, table: String, column: String): DataLabel;

	def lookup(udf: String, method: Method): DesensitizeOperation;
}

object MetaRegistry {
	private val instance: MetaRegistry = new LocalMetaRegistry;

	def get(): MetaRegistry = instance;

}