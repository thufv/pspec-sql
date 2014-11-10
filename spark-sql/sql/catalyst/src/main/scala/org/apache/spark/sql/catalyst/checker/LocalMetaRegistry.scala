package org.apache.spark.sql.catalyst.checker

import java.lang.reflect.Method
import edu.thu.ss.lang.pojo.DataCategory
import edu.thu.ss.lang.pojo.DesensitizeOperation
import edu.thu.ss.lang.pojo.UserCategory
import edu.thu.ss.lang.pojo.Policy
import org.junit.experimental.categories.Categories

class LocalMetaRegistry extends MetaRegistry {

	var policy: Policy = null;

	def init(policy: Policy): Unit = {
		this.policy = policy;
	}

	def lookup(database: String, table: String, column: String): DataLabel = {
		val datas = policy.getDatas();
		val data = datas.get(column);
		if (data != null) {
			DataLabel(data, table, column);
		} else {
			null
		}
	}

	def lookup(udf: String, method: Method): DesensitizeOperation = {
		val op = new DesensitizeOperation;
		op.setUdf(udf.toLowerCase());
		return op;
	}

	def lookup(udf: String, label: DataLabel): DesensitizeOperation = {
		//TODO
		val op = new DesensitizeOperation;
		op.setUdf(udf.toLowerCase());
		return op;
	}

	def currentUser(): UserCategory = {
		return policy.getUsers().get("student");
	}

}