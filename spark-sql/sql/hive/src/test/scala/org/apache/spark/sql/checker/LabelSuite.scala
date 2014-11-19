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

package org.apache.spark.sql.checker

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.BeforeAndAfterAll
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.checker.LabelPropagator

class LabelSuite extends FunSuite with BeforeAndAfterAll {
  val conf = new SparkConf().setAppName("test").setMaster("local");
  val context = new HiveContext(new SparkContext(conf));

  test("parse analyze commands") {
    var plan = context.sql("select i_item_id from item").queryExecution.optimizedPlan;
    var prop = new LabelPropagator;
    prop(plan);

    plan = context.sql(""" 
		  SELECT i_item_id,
         AVG(ss_quantity) agg1,
         AVG(ss_list_price) agg2,
         AVG(ss_coupon_amt) agg3,
         AVG(ss_sales_price) agg4
    FROM store_sales
         JOIN customer_demographics JOIN date_dim JOIN item JOIN promotion
   WHERE     ss_sold_date_sk = d_date_sk
         AND ss_item_sk = i_item_sk
         AND ss_cdemo_sk = cd_demo_sk
         AND ss_promo_sk = p_promo_sk
         AND cd_gender = 'M'
         AND cd_marital_status = 'M'
         AND cd_education_status = '4 yr Degree'
         AND (p_channel_email = 'N' OR p_channel_event = 'N')
         AND d_year = 2001
				GROUP BY i_item_id
		""").queryExecution.optimizedPlan;
    prop = new LabelPropagator();
    prop(plan);

    plan = context.sql("""
				SELECT
        i_brand_id brand_id
        ,i_brand brand
        ,t_hour
        ,t_minute
        ,SUM(ext_price) ext_price
    FROM
        item
        JOIN (
            SELECT
                    ws_ext_sales_price AS ext_price
                    ,ws_sold_date_sk AS sold_date_sk
                    ,ws_item_sk AS sold_item_sk
                    ,ws_sold_time_sk AS time_sk
                FROM
                    web_sales
                    JOIN date_dim
                WHERE
                    d_date_sk = ws_sold_date_sk
                    AND d_moy = 12
                    AND d_year = 2002
            UNION ALL
            SELECT
                    cs_ext_sales_price AS ext_price
                    ,cs_sold_date_sk AS sold_date_sk
                    ,cs_item_sk AS sold_item_sk
                    ,cs_sold_time_sk AS time_sk
                FROM
                    catalog_sales
                    JOIN date_dim
                WHERE
                    d_date_sk = cs_sold_date_sk
                    AND d_moy = 12
                    AND d_year = 2002
            UNION ALL
            SELECT
                    ss_ext_sales_price AS ext_price
                    ,ss_sold_date_sk AS sold_date_sk
                    ,ss_item_sk AS sold_item_sk
                    ,ss_sold_time_sk AS time_sk
                FROM
                    store_sales
                    JOIN date_dim
                WHERE
                    d_date_sk = ss_sold_date_sk
                    AND d_moy = 12
                    AND d_year = 2002
        ) tmp
        JOIN time_dim
    WHERE
        sold_item_sk = i_item_sk
        AND i_manager_id = 1
        AND time_sk = t_time_sk
        AND (
            t_meal_time = 'breakfast'
            OR t_meal_time = 'dinner'
        )
    GROUP BY
        i_brand
        ,i_brand_id
        ,t_hour
        ,t_minute
    ORDER BY
        ext_price DESC
        ,i_brand_id
		""").queryExecution.optimizedPlan;
    prop = new LabelPropagator();
    prop(plan);
  }

}
