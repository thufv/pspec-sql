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

package org.apache.spark.sql.hive

import java.io.{ BufferedReader, FileInputStream, InputStreamReader }

class HiveTpcdsSuite extends HiveTpcdsTables {
  var query = scala.collection.mutable.ArrayBuffer[String]()
  val queryPath = "/Users/luochen/Documents/Research/privacy/tpc-ds/transformed/all.sql"
  val user = ""

  def loadQuery(): Unit = {
    val input: BufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(queryPath)))

    val buf = new StringBuilder
    var strLine: String = input.readLine()
    while (strLine != null) {
      if (strLine.startsWith("--end")) {
        query += buf.toString.replace(";", "")
        buf.clear()
        //println(query.last)
      } else {
        //println("|" + strLine + "|")
        if (!strLine.startsWith("--start") && !strLine.isEmpty())
          buf.append(strLine.trim + " ")
      }
      strLine = input.readLine()
    }
    input.close()
    println("load query done : " + query.length)
  }

  def doCheck(number: Int): Unit = {
    print("query\t" + number + "\t")
    val plan = tpcdsAnalyzer(HiveQl.parseSql(query(number)));
    //tpcdsChecker.check(user, ))
    println(plan.toString);
  }

  loadQuery()

  test("Test") {
    /*  doCheck(0)

    try {doCheck(0)} catch { case e: Exception => println(e.toString)}
    try {doCheck(1)} catch { case e: Exception => println(e.toString)}
    try {doCheck(2)} catch { case e: Exception => println(e.toString)}
    try {doCheck(3)} catch { case e: Exception => println(e.toString)}
    try {doCheck(4)} catch { case e: Exception => println(e.toString)}
    try {doCheck(5)} catch { case e: Exception => println(e.toString)}
    try {doCheck(6)} catch { case e: Exception => println(e.toString)}
    try {doCheck(7)} catch { case e: Exception => println(e.toString)}
    try {doCheck(8)} catch { case e: Exception => println(e.toString)}
    try {doCheck(9)} catch { case e: Exception => println(e.toString)}

    try {doCheck(10)} catch { case e: Exception => println(e.toString)}
    try {doCheck(11)} catch { case e: Exception => println(e.toString)}
    try {doCheck(12)} catch { case e: Exception => println(e.toString)}
    try {doCheck(13)} catch { case e: Exception => println(e.toString)}
    try {doCheck(14)} catch { case e: Exception => println(e.toString)}
    try {doCheck(15)} catch { case e: Exception => println(e.toString)}
    try {doCheck(16)} catch { case e: Exception => println(e.toString)}
    try {doCheck(17)} catch { case e: Exception => println(e.toString)}
    try {doCheck(18)} catch { case e: Exception => println(e.toString)}
    try {doCheck(19)} catch { case e: Exception => println(e.toString)}

    try {doCheck(20)} catch { case e: Exception => println(e.toString)}
    try {doCheck(21)} catch { case e: Exception => println(e.toString)}
    try {doCheck(22)} catch { case e: Exception => println(e.toString)}
    try {doCheck(23)} catch { case e: Exception => println(e.toString)}
    try {doCheck(24)} catch { case e: Exception => println(e.toString)}
    try {doCheck(25)} catch { case e: Exception => println(e.toString)}
    try {doCheck(26)} catch { case e: Exception => println(e.toString)}
    try {doCheck(27)} catch { case e: Exception => println(e.toString)}
    try {doCheck(28)} catch { case e: Exception => println(e.toString)}
    try {doCheck(29)} catch { case e: Exception => println(e.toString)}

    try {doCheck(30)} catch { case e: Exception => println(e.toString)}
    try {doCheck(31)} catch { case e: Exception => println(e.toString)}
    try {doCheck(32)} catch { case e: Exception => println(e.toString)}
    try {doCheck(33)} catch { case e: Exception => println(e.toString)}
    try {doCheck(34)} catch { case e: Exception => println(e.toString)}
    try {doCheck(35)} catch { case e: Exception => println(e.toString)}
    try {doCheck(36)} catch { case e: Exception => println(e.toString)}
    try {doCheck(37)} catch { case e: Exception => println(e.toString)}
    try {doCheck(38)} catch { case e: Exception => println(e.toString)}
    try {doCheck(39)} catch { case e: Exception => println(e.toString)}

    try {doCheck(40)} catch { case e: Exception => println(e.toString)}
    try {doCheck(41)} catch { case e: Exception => println(e.toString)}
    try {doCheck(42)} catch { case e: Exception => println(e.toString)}
    try {doCheck(43)} catch { case e: Exception => println(e.toString)}
    try {doCheck(44)} catch { case e: Exception => println(e.toString)}
    try {doCheck(45)} catch { case e: Exception => println(e.toString)}
    try {doCheck(46)} catch { case e: Exception => println(e.toString)}
    try {doCheck(47)} catch { case e: Exception => println(e.toString)}
    try {doCheck(48)} catch { case e: Exception => println(e.toString)}
    try {doCheck(49)} catch { case e: Exception => println(e.toString)}

    try {doCheck(50)} catch { case e: Exception => println(e.toString)}
    try {doCheck(51)} catch { case e: Exception => println(e.toString)}
    try {doCheck(52)} catch { case e: Exception => println(e.toString)}
    try {doCheck(53)} catch { case e: Exception => println(e.toString)}
    try {doCheck(54)} catch { case e: Exception => println(e.toString)}
    try {doCheck(55)} catch { case e: Exception => println(e.toString)}
    try {doCheck(56)} catch { case e: Exception => println(e.toString)}
    try {doCheck(57)} catch { case e: Exception => println(e.toString)}
    try {doCheck(58)} catch { case e: Exception => println(e.toString)}
    try {doCheck(59)} catch { case e: Exception => println(e.toString)}

    try {doCheck(60)} catch { case e: Exception => println(e.toString)}
    try {doCheck(61)} catch { case e: Exception => println(e.toString)}
    try {doCheck(62)} catch { case e: Exception => println(e.toString)}
    try {doCheck(63)} catch { case e: Exception => println(e.toString)}
    try {doCheck(64)} catch { case e: Exception => println(e.toString)}
    try {doCheck(65)} catch { case e: Exception => println(e.toString)}
    try {doCheck(66)} catch { case e: Exception => println(e.toString)}
    try {doCheck(67)} catch { case e: Exception => println(e.toString)}
    try {doCheck(68)} catch { case e: Exception => println(e.toString)}
    try {doCheck(69)} catch { case e: Exception => println(e.toString)}

    try {doCheck(70)} catch { case e: Exception => println(e.toString)}
    try {doCheck(71)} catch { case e: Exception => println(e.toString)}
    try {doCheck(72)} catch { case e: Exception => println(e.toString)}
    try {doCheck(73)} catch { case e: Exception => println(e.toString)}
    try {doCheck(74)} catch { case e: Exception => println(e.toString)}
    try {doCheck(75)} catch { case e: Exception => println(e.toString)}
    try {doCheck(76)} catch { case e: Exception => println(e.toString)}
    try {doCheck(77)} catch { case e: Exception => println(e.toString)}
    try {doCheck(78)} catch { case e: Exception => println(e.toString)}
    try {doCheck(79)} catch { case e: Exception => println(e.toString)}

    try {doCheck(80)} catch { case e: Exception => println(e.toString)}
    try {doCheck(81)} catch { case e: Exception => println(e.toString)}
    try {doCheck(82)} catch { case e: Exception => println(e.toString)}
    try {doCheck(83)} catch { case e: Exception => println(e.toString)}
    try {doCheck(84)} catch { case e: Exception => println(e.toString)}
    try {doCheck(85)} catch { case e: Exception => println(e.toString)}
    try {doCheck(86)} catch { case e: Exception => println(e.toString)}
    try {doCheck(87)} catch { case e: Exception => println(e.toString)}
    try {doCheck(88)} catch { case e: Exception => println(e.toString)}
    try {doCheck(89)} catch { case e: Exception => println(e.toString)}

    try {doCheck(90)} catch { case e: Exception => println(e.toString)}
    try {doCheck(91)} catch { case e: Exception => println(e.toString)}
    try {doCheck(92)} catch { case e: Exception => println(e.toString)}
    try {doCheck(93)} catch { case e: Exception => println(e.toString)}
    * 
    * 
    */
    try {
      doCheck(94);
    }catch{
      case e:Exception=>e.printStackTrace();
      
    }

    //try {doCheck(94)} catch { case e: Exception => println(e.toString)}
    /*
    try {doCheck(95)} catch { case e: Exception => println(e.toString)}
    try {doCheck(96)} catch { case e: Exception => println(e.toString)}
    try {doCheck(97)} catch { case e: Exception => println(e.toString)}
    try {doCheck(98)} catch { case e: Exception => println(e.toString)}*/
  }
}
