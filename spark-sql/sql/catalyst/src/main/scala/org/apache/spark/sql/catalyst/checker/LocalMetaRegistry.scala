package org.apache.spark.sql.catalyst.checker

import java.lang.reflect.Method
import edu.thu.ss.lang.pojo.DesensitizeOperation
import edu.thu.ss.lang.pojo.DataCategory

class LocalMetaRegistry extends MetaRegistry {

	def lookup(database: String, table: String, column: String): DataLabel = {
		val data = new DataCategory();
		data.setId(column);
		return new DataLabel(data);
	}

	def lookup(udf: String, method: Method): DesensitizeOperation = {
		val op = new DesensitizeOperation;
		op.setUdf(udf);
		return op;
	}

}