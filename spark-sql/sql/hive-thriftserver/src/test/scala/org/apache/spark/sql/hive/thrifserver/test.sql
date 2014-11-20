SELECT i_item_id FROM item;

  SELECT COUNT(i_item_id), i_class
    FROM item
GROUP BY i_class
  HAVING COUNT(i_item_id) > 10;

  SELECT COUNT(it.i_item_id), it.i_class
    FROM item it
GROUP BY i_class
  HAVING COUNT(i_item_id) > 10;

SELECT UPPER(c_first_name), LOWER(c_last_name)
  FROM (SELECT c_first_name, c_last_name, cd_gender
          FROM customer
               JOIN customer_demographics ON c_current_cdemo_sk = cd_demo_sk
         WHERE cd_dep_count + cd_dep_college_count < 10) tmp
 WHERE cd_gender = 'M';

SELECT CASE
          WHEN cd_gender = 'M' THEN c_first_name
          WHEN cd_gender = 'F' THEN c_last_name
          ELSE 'unknown'
       END
  FROM (SELECT c_first_name, c_last_name, cd_gender
          FROM customer
               JOIN customer_demographics ON c_current_cdemo_sk = cd_demo_sk
         WHERE cd_dep_count + cd_dep_college_count < 10) tmp
 WHERE cd_gender = 'M';

SELECT IF(cd_gender = 'M', c_first_name, c_last_name)
  FROM (SELECT c_first_name, c_last_name, cd_gender
          FROM customer
               JOIN customer_demographics ON c_current_cdemo_sk = cd_demo_sk
         WHERE cd_dep_count + cd_dep_college_count < 10) tmp
 WHERE cd_gender = 'M';

SELECT LOG(i_item_id) FROM item;

  SELECT d_year,
         item.i_brand_id brand_id,
         item.i_brand brand,
         SUM(ss_ext_discount_amt) sum_agg
    FROM date_dim JOIN store_sales JOIN item
   WHERE     d_date_sk = ss_sold_date_sk
         AND ss_item_sk = i_item_sk
         AND i_manufact_id = 783
         AND d_moy = 11
GROUP BY d_year, item.i_brand, item.i_brand_id;

  SELECT a.ca_state state, COUNT(*) cnt
    FROM customer_address a
         JOIN customer c JOIN store_sales s JOIN date_dim d JOIN item i
   WHERE     a.ca_address_sk = c.c_current_addr_sk
         AND c.c_customer_sk = s.ss_customer_sk
         AND s.ss_sold_date_sk = d.d_date_sk
         AND s.ss_item_sk = i.i_item_sk
         AND i.i_current_price > 100
GROUP BY a.ca_state
  HAVING COUNT(*) >= 10
ORDER BY cnt;

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
GROUP BY i_item_id;

  SELECT i_item_id,
         i_item_desc,
         s_state,
         COUNT(ss_quantity) AS store_sales_quantitycount,
         AVG(ss_quantity) AS store_sales_quantityave,
         stddev_samp(ss_quantity) AS store_sales_quantitystdev,
         stddev_samp(ss_quantity) / AVG(ss_quantity) AS store_sales_quantitycov,
         COUNT(sr_return_quantity) as_store_returns_quantitycount,
         AVG(sr_return_quantity) as_store_returns_quantityave,
         stddev_samp(sr_return_quantity) as_store_returns_quantitystdev,
         stddev_samp(sr_return_quantity) / AVG(sr_return_quantity)
            AS store_returns_quantitycov,
         COUNT(cs_quantity) AS catalog_sales_quantitycount,
         AVG(cs_quantity) AS catalog_sales_quantityave,
         stddev_samp(cs_quantity) / AVG(cs_quantity)
            AS catalog_sales_quantitystdev,
         stddev_samp(cs_quantity) / AVG(cs_quantity)
            AS catalog_sales_quantitycov
    FROM store_sales
         JOIN store_returns
         JOIN catalog_sales
         JOIN date_dim d1
         JOIN date_dim d2
         JOIN date_dim d3
         JOIN store
         JOIN item
   WHERE     d1.d_quarter_name = '2001Q1'
         AND d1.d_date_sk = ss_sold_date_sk
         AND i_item_sk = ss_item_sk
         AND s_store_sk = ss_store_sk
         AND ss_customer_sk = sr_customer_sk
         AND ss_item_sk = sr_item_sk
         AND ss_ticket_number = sr_ticket_number
         AND sr_returned_date_sk = d2.d_date_sk
         AND d2.d_quarter_name IN ('2001Q1', '2001Q2', '2001Q3')
         AND sr_customer_sk = cs_bill_customer_sk
         AND sr_item_sk = cs_item_sk
         AND cs_sold_date_sk = d3.d_date_sk
         AND d3.d_quarter_name IN ('2001Q1', '2001Q2', '2001Q3')
GROUP BY i_item_id, i_item_desc, s_state
ORDER BY i_item_id, i_item_desc, s_state;


SELECT c_first_name, c_last_name, cd_gender
  FROM customer JOIN customer_demographics
 WHERE cd_dep_count < 10;

SELECT c_first_name, c_last_name, cd_gender
  FROM customer JOIN customer_demographics ON c_current_cdemo_sk = cd_demo_sk
 WHERE CASE
          WHEN cd_gender = 'M' THEN cd_dep_count
          WHEN cd_gender = 'F' THEN cd_dep_college_count
       END < 10;

  SELECT w_warehouse_name,
         w_warehouse_sq_ft,
         w_city,
         w_county,
         w_state,
         w_country,
         ship_carriers,
         year,
         SUM(jan_sales) AS jan_sales,
         SUM(feb_sales) AS feb_sales,
         SUM(mar_sales) AS mar_sales,
         SUM(apr_sales) AS apr_sales,
         SUM(may_sales) AS may_sales,
         SUM(jun_sales) AS jun_sales,
         SUM(jul_sales) AS jul_sales,
         SUM(aug_sales) AS aug_sales,
         SUM(sep_sales) AS sep_sales,
         SUM(oct_sales) AS oct_sales,
         SUM(nov_sales) AS nov_sales,
         SUM(dec_sales) AS dec_sales,
         SUM(jan_sales / w_warehouse_sq_ft) AS jan_sales_per_sq_foot,
         SUM(feb_sales / w_warehouse_sq_ft) AS feb_sales_per_sq_foot,
         SUM(mar_sales / w_warehouse_sq_ft) AS mar_sales_per_sq_foot,
         SUM(apr_sales / w_warehouse_sq_ft) AS apr_sales_per_sq_foot,
         SUM(may_sales / w_warehouse_sq_ft) AS may_sales_per_sq_foot,
         SUM(jun_sales / w_warehouse_sq_ft) AS jun_sales_per_sq_foot,
         SUM(jul_sales / w_warehouse_sq_ft) AS jul_sales_per_sq_foot,
         SUM(aug_sales / w_warehouse_sq_ft) AS aug_sales_per_sq_foot,
         SUM(sep_sales / w_warehouse_sq_ft) AS sep_sales_per_sq_foot,
         SUM(oct_sales / w_warehouse_sq_ft) AS oct_sales_per_sq_foot,
         SUM(nov_sales / w_warehouse_sq_ft) AS nov_sales_per_sq_foot,
         SUM(dec_sales / w_warehouse_sq_ft) AS dec_sales_per_sq_foot,
         SUM(jan_net) AS jan_net,
         SUM(feb_net) AS feb_net,
         SUM(mar_net) AS mar_net,
         SUM(apr_net) AS apr_net,
         SUM(may_net) AS may_net,
         SUM(jun_net) AS jun_net,
         SUM(jul_net) AS jul_net,
         SUM(aug_net) AS aug_net,
         SUM(sep_net) AS sep_net,
         SUM(oct_net) AS oct_net,
         SUM(nov_net) AS nov_net,
         SUM(dec_net) AS dec_net
    FROM (  SELECT w_warehouse_name,
                   w_warehouse_sq_ft,
                   w_city,
                   w_county,
                   w_state,
                   w_country,
                   'DIAMOND , ZOUROS' AS ship_carriers,
                   d_year AS year,
                   SUM(
                      CASE
                         WHEN d_moy = 1 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS jan_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 2 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS feb_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 3 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS mar_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 4 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS apr_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 5 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS may_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 6 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS jun_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 7 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS jul_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 8 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS aug_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 9 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS sep_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 10 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS oct_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 11 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS nov_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 12 THEN ws_ext_sales_price * ws_quantity
                         ELSE 0
                      END)
                      AS dec_sales,
                   SUM(
                      CASE
                         WHEN d_moy = 1 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS jan_net,
                   SUM(
                      CASE
                         WHEN d_moy = 2 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS feb_net,
                   SUM(
                      CASE
                         WHEN d_moy = 3 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS mar_net,
                   SUM(
                      CASE
                         WHEN d_moy = 4 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS apr_net,
                   SUM(
                      CASE
                         WHEN d_moy = 5 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS may_net,
                   SUM(
                      CASE
                         WHEN d_moy = 6 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS jun_net,
                   SUM(
                      CASE
                         WHEN d_moy = 7 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS jul_net,
                   SUM(
                      CASE
                         WHEN d_moy = 8 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS aug_net,
                   SUM(
                      CASE
                         WHEN d_moy = 9 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS sep_net,
                   SUM(
                      CASE
                         WHEN d_moy = 10 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS oct_net,
                   SUM(
                      CASE
                         WHEN d_moy = 11 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS nov_net,
                   SUM(
                      CASE
                         WHEN d_moy = 12 THEN ws_net_paid_inc_ship * ws_quantity
                         ELSE 0
                      END)
                      AS dec_net
              FROM web_sales
                   JOIN warehouse JOIN date_dim JOIN time_dim JOIN ship_mode
             WHERE     ws_warehouse_sk = w_warehouse_sk
                   AND ws_sold_date_sk = d_date_sk
                   AND ws_sold_time_sk = t_time_sk
                   AND ws_ship_mode_sk = sm_ship_mode_sk
                   AND d_year = 1999
                   AND t_time BETWEEN 46185 AND 46185 + 28800
                   AND sm_carrier IN ('DIAMOND', 'ZOUROS')
          GROUP BY w_warehouse_name,
                   w_warehouse_sq_ft,
                   w_city,
                   w_county,
                   w_state,
                   w_country,
                   d_year) x
GROUP BY w_warehouse_name,
         w_warehouse_sq_ft,
         w_city,
         w_county,
         w_state,
         w_country,
         ship_carriers,
         year
ORDER BY w_warehouse_name;


  SELECT i_brand_id brand_id,
         i_brand brand,
         t_hour,
         t_minute,
         SUM(ext_price) ext_price
    FROM item
         JOIN
         (SELECT ws_ext_sales_price AS ext_price,
                 ws_sold_date_sk AS sold_date_sk,
                 ws_item_sk AS sold_item_sk,
                 ws_sold_time_sk AS time_sk
            FROM web_sales JOIN date_dim
           WHERE d_date_sk = ws_sold_date_sk AND d_moy = 12 AND d_year = 2002
          UNION ALL
          SELECT cs_ext_sales_price AS ext_price,
                 cs_sold_date_sk AS sold_date_sk,
                 cs_item_sk AS sold_item_sk,
                 cs_sold_time_sk AS time_sk
            FROM catalog_sales JOIN date_dim
           WHERE d_date_sk = cs_sold_date_sk AND d_moy = 12 AND d_year = 2002
          UNION ALL
          SELECT ss_ext_sales_price AS ext_price,
                 ss_sold_date_sk AS sold_date_sk,
                 ss_item_sk AS sold_item_sk,
                 ss_sold_time_sk AS time_sk
            FROM store_sales JOIN date_dim
           WHERE d_date_sk = ss_sold_date_sk AND d_moy = 12 AND d_year = 2002)
         tmp
         JOIN time_dim
   WHERE     sold_item_sk = i_item_sk
         AND i_manager_id = 1
         AND time_sk = t_time_sk
         AND (t_meal_time = 'breakfast' OR t_meal_time = 'dinner')
GROUP BY i_brand,
         i_brand_id,
         t_hour,
         t_minute
ORDER BY ext_price DESC, i_brand_id;

SELECT c_first_name, c_last_name, ca_country
  FROM customer JOIN customer_address ON c_current_addr_sk = ca_address_sk
 WHERE c_birth_year > 1990
 
select d_date from date_dim JOIN customer ON d_date_sk = c_first_sales_date_sk;

SELECT d1.d_date, d2.d_date
  FROM customer
       JOIN date_dim d1 ON c_first_shipto_date_sk = d1.d_date_sk
       JOIN date_dim d2 ON d1.d_date_sk = d2.d_date_sk

SELECT i_brand 
    FROM store_sales 
        JOIN store_returns ON ss_item_sk = sr_item_sk
        JOIN item on sr_item_sk = i_item_sk
        

SELECT i_brand
    FROM (select i_brand from item join store_sales on i_item_sk = ss_item_sk UNION all
        select i_brand from item join store_returns on i_item_sk = sr_item_sk) x
        
SELECT d2.d_date
    FROM customer
       JOIN date_dim d1 ON c_first_shipto_date_sk = d1.d_date_sk
       JOIN date_dim d2
       
SELECT case when c_first_shipto_date_sk = d_date_sk then d_date end 
FROM date_dim JOIN customer

SELECT d_date
FROM date_dim JOIN store_sales JOIN store_returns
WHERE case when 1>0 then ss_sold_date_sk else sr_returned_date_sk end = d_date_sk

SELECT i_brand
FROM store_sales JOIN store_returns JOIN item
WHERE case when 1>0 then ss_item_sk else sr_item_sk end sk = i_item_sk
