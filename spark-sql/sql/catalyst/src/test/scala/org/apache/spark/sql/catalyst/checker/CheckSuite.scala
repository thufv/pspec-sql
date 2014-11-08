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
package org.apache.spark.sql.catalyst.checker

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import scala.collection.mutable.HashMap
import scala.collection.mutable.Map

class CheckerSuite extends FunSuite with BeforeAndAfter {

	test("test map") {
		val map1: Map[Integer, Integer] = new HashMap;
		val map2: Map[Integer, Integer] = new HashMap;
		map2.put(1, 2);
		map1 ++= map2;
		assert(map1.size == 1);
	}

}
