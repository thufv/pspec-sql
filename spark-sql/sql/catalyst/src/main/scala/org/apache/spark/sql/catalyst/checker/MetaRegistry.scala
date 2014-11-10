package org.apache.spark.sql.catalyst.checker

import java.lang.reflect.Method
import edu.thu.ss.lang.pojo.DesensitizeOperation
import edu.thu.ss.lang.pojo.UserCategory
import edu.thu.ss.lang.pojo.Policy

trait MetaRegistry {

	def lookup(database: String, table: String, column: String): DataLabel;

	def lookup(udf: String, method: Method): DesensitizeOperation;

	def lookup(udf: String, data: DataLabel): DesensitizeOperation;

	def currentUser(): UserCategory;

	def init(policy: Policy): Unit;
}

object MetaRegistry {
	private val instance: MetaRegistry = new LocalMetaRegistry;

	def get(): MetaRegistry = instance;

}