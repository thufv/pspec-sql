package org.apache.spark.sql.catalyst.checker

import java.lang.reflect.Method

import edu.thu.ss.lang.pojo.DataCategory
import edu.thu.ss.lang.pojo.DesensitizeOperation

class LocalMetaRegistry extends MetaRegistry {

	def lookup(database: String, table: String, column: String): DataLabel = {
		val data = new DataCategory();
		data.setId(column);
		return  DataLabel(data, table, column);
	}

	def lookup(udf: String, method: Method): DesensitizeOperation = {
		val op = new DesensitizeOperation;
		op.setUdf(udf);
		return op;
	}

}