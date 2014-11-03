
--start query 61 using template query61.tpl
--@private, @condition(customer_address)
SELECT
        top 100 promotions
        ,total
        ,cast(
            promotions AS DECIMAL(15, 4)
        ) / cast(
            total AS DECIMAL(15, 4)
        ) * 100
    FROM
        (
            SELECT
                    SUM(ss_ext_sales_price) promotions
                FROM
                    store_sales
                    ,store
                    ,promotion
                    ,date_dim
                    ,customer
                    ,customer_address
                    ,item
                WHERE
                    ss_sold_date_sk = d_date_sk
                    AND ss_store_sk = s_store_sk
                    AND ss_promo_sk = p_promo_sk
                    AND ss_customer_sk = c_customer_sk
                    AND ca_address_sk = c_current_addr_sk
                    AND ss_item_sk = i_item_sk
                    AND ca_gmt_offset = -6
                    AND i_category = 'Jewelry'
                    AND (
                        p_channel_dmail = 'Y'
                        OR p_channel_email = 'Y'
                        OR p_channel_tv = 'Y'
                    )
                    AND s_gmt_offset = -6
                    AND d_year = 2000
                    AND d_moy = 11
        ) promotional_sales
        ,(
            SELECT
                    SUM(ss_ext_sales_price) total
                FROM
                    store_sales
                    ,store
                    ,date_dim
                    ,customer
                    ,customer_address
                    ,item
                WHERE
                    ss_sold_date_sk = d_date_sk
                    AND ss_store_sk = s_store_sk
                    AND ss_customer_sk = c_customer_sk
                    AND ca_address_sk = c_current_addr_sk
                    AND ss_item_sk = i_item_sk
                    AND ca_gmt_offset = -6
                    AND i_category = 'Jewelry'
                    AND s_gmt_offset = -6
                    AND d_year = 2000
                    AND d_moy = 11
        ) all_sales
    ORDER BY
        promotions
        ,total;
--end query 61 using template query61.tpl
--start query 62 using template query62.tpl
-- @public
SELECT
        top 100 SUBSTR(w_warehouse_name, 1, 20)
        ,sm_type
        ,web_name
        ,SUM(CASE
            WHEN (ws_ship_date_sk - ws_sold_date_sk <= 30) THEN 1
            ELSE 0
        END) AS "30 days"
        ,SUM(CASE
            WHEN (ws_ship_date_sk - ws_sold_date_sk > 30)
            AND (ws_ship_date_sk - ws_sold_date_sk <= 60) THEN 1
            ELSE 0
        END) AS "31-60 days"
        ,SUM(CASE
            WHEN (ws_ship_date_sk - ws_sold_date_sk > 60)
            AND (ws_ship_date_sk - ws_sold_date_sk <= 90) THEN 1
            ELSE 0
        END) AS "61-90 days"
        ,SUM(CASE
            WHEN (ws_ship_date_sk - ws_sold_date_sk > 90)
            AND (ws_ship_date_sk - ws_sold_date_sk <= 120) THEN 1
            ELSE 0
        END) AS "91-120 days"
        ,SUM(CASE
            WHEN (ws_ship_date_sk - ws_sold_date_sk > 120) THEN 1
            ELSE 0
        END) AS ">120 days"
    FROM
        web_sales
        ,warehouse
        ,ship_mode
        ,web_site
        ,date_dim
    WHERE
        d_month_seq BETWEEN 1212 AND 1212 + 11
        AND ws_ship_date_sk = d_date_sk
        AND ws_warehouse_sk = w_warehouse_sk
        AND ws_ship_mode_sk = sm_ship_mode_sk
        AND ws_web_site_sk = web_site_sk
    GROUP BY
        SUBSTR(w_warehouse_name, 1, 20)
        ,sm_type
        ,web_name
    ORDER BY
        SUBSTR(w_warehouse_name, 1, 20)
        ,sm_type
        ,web_name;
--end query 62 using template query62.tpl
--start query 63 using template query63.tpl
--@public
SELECT
        top 100 *
    FROM
        (
            SELECT
                    i_manager_id
                    ,SUM(ss_sales_price) sum_sales
                    ,AVG(SUM(ss_sales_price)) OVER (
                        PARTITION BY
                            i_manager_id
                    ) avg_monthly_sales
                FROM
                    item
                    ,store_sales
                    ,date_dim
                    ,store
                WHERE
                    ss_item_sk = i_item_sk
                    AND ss_sold_date_sk = d_date_sk
                    AND ss_store_sk = s_store_sk
                    AND d_month_seq IN (
                        1206
                        ,1206+1
                        ,1206+2
                        ,1206+3
                        ,1206+4
                        ,1206+5
                        ,1206+6
                        ,1206+7
                        ,1206+8
                        ,1206+9
                        ,1206+10
                        ,1206+11
                    )
                    AND (
                        (
                            i_category IN (
                                'Books'
                                ,'Children'
                                ,'Electronics'
                            )
                            AND i_class IN (
                                'personal'
                                ,'portable'
                                ,'refernece'
                                ,'self-help'
                            )
                            AND i_brand IN (
                                'scholaramalgamalg #14'
                                ,'scholaramalgamalg #7'
                                ,'exportiunivamalg #9'
                                ,'scholaramalgamalg #9'
                            )
                        )
                        OR (
                            i_category IN (
                                'Women'
                                ,'Music'
                                ,'Men'
                            )
                            AND i_class IN (
                                'accessories'
                                ,'classical'
                                ,'fragrances'
                                ,'pants'
                            )
                            AND i_brand IN (
                                'amalgimporto #1'
                                ,'edu packscholar #1'
                                ,'exportiimporto #1'
                                ,'importoamalg #1'
                            )
                        )
                    )
                GROUP BY
                    i_manager_id
                    ,d_moy
        ) tmp1
    WHERE
        CASE
            WHEN avg_monthly_sales > 0 THEN ABS(sum_sales - avg_monthly_sales) / avg_monthly_sales
            ELSE null
        END > 0.1
    ORDER BY
        i_manager_id
        ,avg_monthly_sales
        ,sum_sales;
--end query 63 using template query63.tpl
--start query 64 using template query64.tpl
-- @private, @projection(customer_address)
WITH cs_ui AS (
        SELECT
                cs_item_sk
                ,SUM(cs_ext_list_price) AS sale
                ,SUM(cr_refunded_cash + cr_reversed_charge + cr_store_credit) AS refund
            FROM
                catalog_sales
                ,catalog_returns
            WHERE
                cs_item_sk = cr_item_sk
                AND cs_order_number = cr_order_number
            GROUP BY
                cs_item_sk
            HAVING
                SUM(cs_ext_list_price) > 2 * SUM(cr_refunded_cash + cr_reversed_charge + cr_store_credit)
)
,cross_sales AS (
    SELECT
            i_product_name product_name
            ,i_item_sk item_sk
            ,s_store_name store_name
            ,s_zip store_zip
            ,ad1.ca_street_number b_street_number
            ,ad1.ca_street_name b_streen_name
            ,ad1.ca_city b_city
            ,ad1.ca_zip b_zip
            ,ad2.ca_street_number c_street_number
            ,ad2.ca_street_name c_street_name
            ,ad2.ca_city c_city
            ,ad2.ca_zip c_zip
            ,d1.d_year AS syear
            ,d2.d_year AS fsyear
            ,d3.d_year s2year
            ,COUNT(*) cnt
            ,SUM(ss_wholesale_cost) s1
            ,SUM(ss_list_price) s2
            ,SUM(ss_coupon_amt) s3
        FROM
            store_sales
            ,store_returns
            ,cs_ui
            ,date_dim d1
            ,date_dim d2
            ,date_dim d3
            ,store
            ,customer
            ,customer_demographics cd1
            ,customer_demographics cd2
            ,promotion
            ,household_demographics hd1
            ,household_demographics hd2
            ,customer_address ad1
            ,customer_address ad2
            ,income_band ib1
            ,income_band ib2
            ,item
        WHERE
            ss_store_sk = s_store_sk
            AND ss_sold_date_sk = d1.d_date_sk
            AND ss_customer_sk = c_customer_sk
            AND ss_cdemo_sk = cd1.cd_demo_sk
            AND ss_hdemo_sk = hd1.hd_demo_sk
            AND ss_addr_sk = ad1.ca_address_sk
            AND ss_item_sk = i_item_sk
            AND ss_item_sk = sr_item_sk
            AND ss_ticket_number = sr_ticket_number
            AND ss_item_sk = cs_ui.cs_item_sk
            AND c_current_cdemo_sk = cd2.cd_demo_sk
            AND c_current_hdemo_sk = hd2.hd_demo_sk
            AND c_current_addr_sk = ad2.ca_address_sk
            AND c_first_sales_date_sk = d2.d_date_sk
            AND c_first_shipto_date_sk = d3.d_date_sk
            AND ss_promo_sk = p_promo_sk
            AND hd1.hd_income_band_sk = ib1.ib_income_band_sk
            AND hd2.hd_income_band_sk = ib2.ib_income_band_sk
            AND cd1.cd_marital_status <> cd2.cd_marital_status
            AND i_color IN (
                'lavender'
                ,'metallic'
                ,'beige'
                ,'gainsboro'
                ,'chartreuse'
                ,'lemon'
            )
            AND i_current_price BETWEEN 6 AND 6 + 10
            AND i_current_price BETWEEN 6 + 1 AND 6 + 15
        GROUP BY
            i_product_name
            ,i_item_sk
            ,s_store_name
            ,s_zip
            ,ad1.ca_street_number
            ,ad1.ca_street_name
            ,ad1.ca_city
            ,ad1.ca_zip
            ,ad2.ca_street_number
            ,ad2.ca_street_name
            ,ad2.ca_city
            ,ad2.ca_zip
            ,d1.d_year
            ,d2.d_year
            ,d3.d_year
) SELECT
        cs1.product_name
        ,cs1.store_name
        ,cs1.store_zip
        ,cs1.b_street_number
        ,cs1.b_streen_name
        ,cs1.b_city
        ,cs1.b_zip
        ,cs1.c_street_number
        ,cs1.c_street_name
        ,cs1.c_city
        ,cs1.c_zip
        ,cs1.syear
        ,cs1.cnt
        ,cs1.s1
        ,cs1.s2
        ,cs1.s3
        ,cs2.s1
        ,cs2.s2
        ,cs2.s3
        ,cs2.syear
        ,cs2.cnt
    FROM
        cross_sales cs1
        ,cross_sales cs2
    WHERE
        cs1.item_sk = cs2.item_sk
        AND cs1.syear = 1999
        AND cs2.syear = 1999 + 1
        AND cs2.cnt <= cs1.cnt
        AND cs1.store_name = cs2.store_name
        AND cs1.store_zip = cs2.store_zip
    ORDER BY
        cs1.product_name
        ,cs1.store_name
        ,cs2.cnt;
--end query 64 using template query64.tpl
--start query 65 using template query65.tpl
--@public
SELECT
        top 100 s_store_name
        ,i_item_desc
        ,sc.revenue
        ,i_current_price
        ,i_wholesale_cost
        ,i_brand
    FROM
        store
        ,item
        ,(
            SELECT
                    ss_store_sk
                    ,AVG(revenue) AS ave
                FROM
                    (
                        SELECT
                                ss_store_sk
                                ,ss_item_sk
                                ,SUM(ss_sales_price) AS revenue
                            FROM
                                store_sales
                                ,date_dim
                            WHERE
                                ss_sold_date_sk = d_date_sk
                                AND d_month_seq BETWEEN 1223 AND 1223+11
                            GROUP BY
                                ss_store_sk
                                ,ss_item_sk
                    ) sa
                GROUP BY
                    ss_store_sk
        ) sb
        ,(
            SELECT
                    ss_store_sk
                    ,ss_item_sk
                    ,SUM(ss_sales_price) AS revenue
                FROM
                    store_sales
                    ,date_dim
                WHERE
                    ss_sold_date_sk = d_date_sk
                    AND d_month_seq BETWEEN 1223 AND 1223+11
                GROUP BY
                    ss_store_sk
                    ,ss_item_sk
        ) sc
    WHERE
        sb.ss_store_sk = sc.ss_store_sk
        AND sc.revenue <= 0.1 * sb.ave
        AND s_store_sk = sc.ss_store_sk
        AND i_item_sk = sc.ss_item_sk
    ORDER BY
        s_store_name
        ,i_item_desc;
--end query 65 using template query65.tpl
--start query 66 using template query66.tpl
--@public
SELECT
        top 100 w_warehouse_name
        ,w_warehouse_sq_ft
        ,w_city
        ,w_county
        ,w_state
        ,w_country
        ,ship_carriers
        ,year
        ,SUM(jan_sales) AS jan_sales
        ,SUM(feb_sales) AS feb_sales
        ,SUM(mar_sales) AS mar_sales
        ,SUM(apr_sales) AS apr_sales
        ,SUM(may_sales) AS may_sales
        ,SUM(jun_sales) AS jun_sales
        ,SUM(jul_sales) AS jul_sales
        ,SUM(aug_sales) AS aug_sales
        ,SUM(sep_sales) AS sep_sales
        ,SUM(oct_sales) AS oct_sales
        ,SUM(nov_sales) AS nov_sales
        ,SUM(dec_sales) AS dec_sales
        ,SUM(jan_sales / w_warehouse_sq_ft) AS jan_sales_per_sq_foot
        ,SUM(feb_sales / w_warehouse_sq_ft) AS feb_sales_per_sq_foot
        ,SUM(mar_sales / w_warehouse_sq_ft) AS mar_sales_per_sq_foot
        ,SUM(apr_sales / w_warehouse_sq_ft) AS apr_sales_per_sq_foot
        ,SUM(may_sales / w_warehouse_sq_ft) AS may_sales_per_sq_foot
        ,SUM(jun_sales / w_warehouse_sq_ft) AS jun_sales_per_sq_foot
        ,SUM(jul_sales / w_warehouse_sq_ft) AS jul_sales_per_sq_foot
        ,SUM(aug_sales / w_warehouse_sq_ft) AS aug_sales_per_sq_foot
        ,SUM(sep_sales / w_warehouse_sq_ft) AS sep_sales_per_sq_foot
        ,SUM(oct_sales / w_warehouse_sq_ft) AS oct_sales_per_sq_foot
        ,SUM(nov_sales / w_warehouse_sq_ft) AS nov_sales_per_sq_foot
        ,SUM(dec_sales / w_warehouse_sq_ft) AS dec_sales_per_sq_foot
        ,SUM(jan_net) AS jan_net
        ,SUM(feb_net) AS feb_net
        ,SUM(mar_net) AS mar_net
        ,SUM(apr_net) AS apr_net
        ,SUM(may_net) AS may_net
        ,SUM(jun_net) AS jun_net
        ,SUM(jul_net) AS jul_net
        ,SUM(aug_net) AS aug_net
        ,SUM(sep_net) AS sep_net
        ,SUM(oct_net) AS oct_net
        ,SUM(nov_net) AS nov_net
        ,SUM(dec_net) AS dec_net
    FROM
        (
            (
                SELECT
                        w_warehouse_name
                        ,w_warehouse_sq_ft
                        ,w_city
                        ,w_county
                        ,w_state
                        ,w_country
                        ,'DIAMOND' || ',' || 'ZOUROS' AS ship_carriers
                        ,d_year AS year
                        ,SUM(CASE
                            WHEN d_moy = 1 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS jan_sales
                        ,SUM(CASE
                            WHEN d_moy = 2 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS feb_sales
                        ,SUM(CASE
                            WHEN d_moy = 3 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS mar_sales
                        ,SUM(CASE
                            WHEN d_moy = 4 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS apr_sales
                        ,SUM(CASE
                            WHEN d_moy = 5 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS may_sales
                        ,SUM(CASE
                            WHEN d_moy = 6 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS jun_sales
                        ,SUM(CASE
                            WHEN d_moy = 7 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS jul_sales
                        ,SUM(CASE
                            WHEN d_moy = 8 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS aug_sales
                        ,SUM(CASE
                            WHEN d_moy = 9 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS sep_sales
                        ,SUM(CASE
                            WHEN d_moy = 10 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS oct_sales
                        ,SUM(CASE
                            WHEN d_moy = 11 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS nov_sales
                        ,SUM(CASE
                            WHEN d_moy = 12 THEN ws_ext_sales_price * ws_quantity
                            ELSE 0
                        END) AS dec_sales
                        ,SUM(CASE
                            WHEN d_moy = 1 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS jan_net
                        ,SUM(CASE
                            WHEN d_moy = 2 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS feb_net
                        ,SUM(CASE
                            WHEN d_moy = 3 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS mar_net
                        ,SUM(CASE
                            WHEN d_moy = 4 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS apr_net
                        ,SUM(CASE
                            WHEN d_moy = 5 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS may_net
                        ,SUM(CASE
                            WHEN d_moy = 6 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS jun_net
                        ,SUM(CASE
                            WHEN d_moy = 7 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS jul_net
                        ,SUM(CASE
                            WHEN d_moy = 8 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS aug_net
                        ,SUM(CASE
                            WHEN d_moy = 9 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS sep_net
                        ,SUM(CASE
                            WHEN d_moy = 10 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS oct_net
                        ,SUM(CASE
                            WHEN d_moy = 11 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS nov_net
                        ,SUM(CASE
                            WHEN d_moy = 12 THEN ws_net_paid_inc_ship * ws_quantity
                            ELSE 0
                        END) AS dec_net
                    FROM
                        web_sales
                        ,warehouse
                        ,date_dim
                        ,time_dim
                        ,ship_mode
                    WHERE
                        ws_warehouse_sk = w_warehouse_sk
                        AND ws_sold_date_sk = d_date_sk
                        AND ws_sold_time_sk = t_time_sk
                        AND ws_ship_mode_sk = sm_ship_mode_sk
                        AND d_year = 1999
                        AND t_time BETWEEN 46185 AND 46185+28800
                        AND sm_carrier IN (
                            'DIAMOND'
                            ,'ZOUROS'
                        )
                    GROUP BY
                        w_warehouse_name
                        ,w_warehouse_sq_ft
                        ,w_city
                        ,w_county
                        ,w_state
                        ,w_country
                        ,d_year
            )
            UNION ALL
            (
                SELECT
                        w_warehouse_name
                        ,w_warehouse_sq_ft
                        ,w_city
                        ,w_county
                        ,w_state
                        ,w_country
                        ,'DIAMOND' || ',' || 'ZOUROS' AS ship_carriers
                        ,d_year AS year
                        ,SUM(CASE
                            WHEN d_moy = 1 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS jan_sales
                        ,SUM(CASE
                            WHEN d_moy = 2 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS feb_sales
                        ,SUM(CASE
                            WHEN d_moy = 3 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS mar_sales
                        ,SUM(CASE
                            WHEN d_moy = 4 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS apr_sales
                        ,SUM(CASE
                            WHEN d_moy = 5 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS may_sales
                        ,SUM(CASE
                            WHEN d_moy = 6 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS jun_sales
                        ,SUM(CASE
                            WHEN d_moy = 7 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS jul_sales
                        ,SUM(CASE
                            WHEN d_moy = 8 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS aug_sales
                        ,SUM(CASE
                            WHEN d_moy = 9 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS sep_sales
                        ,SUM(CASE
                            WHEN d_moy = 10 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS oct_sales
                        ,SUM(CASE
                            WHEN d_moy = 11 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS nov_sales
                        ,SUM(CASE
                            WHEN d_moy = 12 THEN cs_sales_price * cs_quantity
                            ELSE 0
                        END) AS dec_sales
                        ,SUM(CASE
                            WHEN d_moy = 1 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS jan_net
                        ,SUM(CASE
                            WHEN d_moy = 2 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS feb_net
                        ,SUM(CASE
                            WHEN d_moy = 3 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS mar_net
                        ,SUM(CASE
                            WHEN d_moy = 4 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS apr_net
                        ,SUM(CASE
                            WHEN d_moy = 5 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS may_net
                        ,SUM(CASE
                            WHEN d_moy = 6 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS jun_net
                        ,SUM(CASE
                            WHEN d_moy = 7 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS jul_net
                        ,SUM(CASE
                            WHEN d_moy = 8 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS aug_net
                        ,SUM(CASE
                            WHEN d_moy = 9 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS sep_net
                        ,SUM(CASE
                            WHEN d_moy = 10 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS oct_net
                        ,SUM(CASE
                            WHEN d_moy = 11 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS nov_net
                        ,SUM(CASE
                            WHEN d_moy = 12 THEN cs_net_paid_inc_ship_tax * cs_quantity
                            ELSE 0
                        END) AS dec_net
                    FROM
                        catalog_sales
                        ,warehouse
                        ,date_dim
                        ,time_dim
                        ,ship_mode
                    WHERE
                        cs_warehouse_sk = w_warehouse_sk
                        AND cs_sold_date_sk = d_date_sk
                        AND cs_sold_time_sk = t_time_sk
                        AND cs_ship_mode_sk = sm_ship_mode_sk
                        AND d_year = 1999
                        AND t_time BETWEEN 46185 AND 46185+28800
                        AND sm_carrier IN (
                            'DIAMOND'
                            ,'ZOUROS'
                        )
                    GROUP BY
                        w_warehouse_name
                        ,w_warehouse_sq_ft
                        ,w_city
                        ,w_county
                        ,w_state
                        ,w_country
                        ,d_year
            )
        ) x
    GROUP BY
        w_warehouse_name
        ,w_warehouse_sq_ft
        ,w_city
        ,w_county
        ,w_state
        ,w_country
        ,ship_carriers
        ,year
    ORDER BY
        w_warehouse_name;
--end query 66 using template query66.tpl
--start query 67 using template query67.tpl
--@public
SELECT
        top 100 *
    FROM
        (
            SELECT
                    i_category
                    ,i_class
                    ,i_brand
                    ,i_product_name
                    ,d_year
                    ,d_qoy
                    ,d_moy
                    ,s_store_id
                    ,sumsales
                    ,rank() OVER (
                        PARTITION BY
                            i_category
                        ORDER BY
                            sumsales DESC
                    ) rk
                FROM
                    (
                        SELECT
                                i_category
                                ,i_class
                                ,i_brand
                                ,i_product_name
                                ,d_year
                                ,d_qoy
                                ,d_moy
                                ,s_store_id
                                ,SUM(COALESCE(ss_sales_price * ss_quantity, 0)) sumsales
                            FROM
                                store_sales
                                ,date_dim
                                ,store
                                ,item
                            WHERE
                                ss_sold_date_sk = d_date_sk
                                AND ss_item_sk = i_item_sk
                                AND ss_store_sk = s_store_sk
                                AND d_month_seq BETWEEN 1214 AND 1214+11
                            GROUP BY
                                rollup(
                                    i_category
                                    ,i_class
                                    ,i_brand
                                    ,i_product_name
                                    ,d_year
                                    ,d_qoy
                                    ,d_moy
                                    ,s_store_id
                                )
                    ) dw1
        ) dw2
    WHERE
        rk <= 100
    ORDER BY
        i_category
        ,i_class
        ,i_brand
        ,i_product_name
        ,d_year
        ,d_qoy
        ,d_moy
        ,s_store_id
        ,sumsales
        ,rk;
--end query 67 using template query67.tpl
--start query 68 using template query68.tpl
--@private, @projection(customer), @condition(demographics)
SELECT
        top 100 c_last_name
        ,c_first_name
        ,ca_city
        ,bought_city
        ,ss_ticket_number
        ,extended_price
        ,extended_tax
        ,list_price
    FROM
        (
            SELECT
                    ss_ticket_number
                    ,ss_customer_sk
                    ,ca_city bought_city
                    ,SUM(ss_ext_sales_price) extended_price
                    ,SUM(ss_ext_list_price) list_price
                    ,SUM(ss_ext_tax) extended_tax
                FROM
                    store_sales
                    ,date_dim
                    ,store
                    ,household_demographics
                    ,customer_address
                WHERE
                    store_sales.ss_sold_date_sk = date_dim.d_date_sk
                    AND store_sales.ss_store_sk = store.s_store_sk
                    AND store_sales.ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND store_sales.ss_addr_sk = customer_address.ca_address_sk
                    AND date_dim.d_dom BETWEEN 1 AND 2
                    AND (
                        household_demographics.hd_dep_count = 9
                        OR household_demographics.hd_vehicle_count = 3
                    )
                    AND date_dim.d_year IN (
                        1998
                        ,1998+1
                        ,1998+2
                    )
                    AND store.s_city IN (
                        'Midway'
                        ,'Fairview'
                    )
                GROUP BY
                    ss_ticket_number
                    ,ss_customer_sk
                    ,ss_addr_sk
                    ,ca_city
        ) dn
        ,customer
        ,customer_address current_addr
    WHERE
        ss_customer_sk = c_customer_sk
        AND customer.c_current_addr_sk = current_addr.ca_address_sk
        AND current_addr.ca_city <> bought_city
    ORDER BY
        c_last_name
        ,ss_ticket_number;
--end query 68 using template query68.tpl
--start query 69 using template query69.tpl
--@private, @projection(demographics), @condition(customer_address)
SELECT
        top 100 cd_gender
        ,cd_marital_status
        ,cd_education_status
        ,COUNT(*) cnt1
        ,cd_purchase_estimate
        ,COUNT(*) cnt2
        ,cd_credit_rating
        ,COUNT(*) cnt3
    FROM
        customer c
        ,customer_address ca
        ,customer_demographics
    WHERE
        c.c_current_addr_sk = ca.ca_address_sk
        AND ca_state IN (
            'SD'
            ,'KY'
            ,'MO'
        )
        AND cd_demo_sk = c.c_current_cdemo_sk
        AND EXISTS (
            SELECT
                    *
                FROM
                    store_sales
                    ,date_dim
                WHERE
                    c.c_customer_sk = ss_customer_sk
                    AND ss_sold_date_sk = d_date_sk
                    AND d_year = 2004
                    AND d_moy BETWEEN 3 AND 3+2
        )
        AND (
            NOT EXISTS (
                SELECT
                        *
                    FROM
                        web_sales
                        ,date_dim
                    WHERE
                        c.c_customer_sk = ws_bill_customer_sk
                        AND ws_sold_date_sk = d_date_sk
                        AND d_year = 2004
                        AND d_moy BETWEEN 3 AND 3+2
            )
            AND NOT EXISTS (
                SELECT
                        *
                    FROM
                        catalog_sales
                        ,date_dim
                    WHERE
                        c.c_customer_sk = cs_ship_customer_sk
                        AND cs_sold_date_sk = d_date_sk
                        AND d_year = 2004
                        AND d_moy BETWEEN 3 AND 3+2
            )
        )
    GROUP BY
        cd_gender
        ,cd_marital_status
        ,cd_education_status
        ,cd_purchase_estimate
        ,cd_credit_rating
    ORDER BY
        cd_gender
        ,cd_marital_status
        ,cd_education_status
        ,cd_purchase_estimate
        ,cd_credit_rating;
--end query 69 using template query69.tpl
--start query 70 using template query70.tpl
--@public
SELECT
        top 100 SUM(ss_net_profit) AS total_sum
        ,s_state
        ,s_county
        ,grouping(s_state) + grouping(s_county) AS lochierarchy
        ,rank() OVER (
            PARTITION BY
                grouping(s_state) + grouping(s_county)
                ,CASE
                    WHEN grouping(s_county) = 0 THEN s_state
                END
            ORDER BY
                SUM(ss_net_profit) DESC
        ) AS rank_within_parent
    FROM
        store_sales
        ,date_dim d1
        ,store
    WHERE
        d1.d_month_seq BETWEEN 1181 AND 1181+11
        AND d1.d_date_sk = ss_sold_date_sk
        AND s_store_sk = ss_store_sk
        AND s_state IN (
            SELECT
                    s_state
                FROM
                    (
                        SELECT
                                s_state AS s_state
                                ,rank() OVER (
                                    PARTITION BY
                                        s_state
                                    ORDER BY
                                        SUM(ss_net_profit) DESC
                                ) AS ranking
                            FROM
                                store_sales
                                ,store
                                ,date_dim
                            WHERE
                                d_month_seq BETWEEN 1181 AND 1181+11
                                AND d_date_sk = ss_sold_date_sk
                                AND s_store_sk = ss_store_sk
                            GROUP BY
                                s_state
                    ) tmp1
                WHERE
                    ranking <= 5
        )
    GROUP BY
        rollup(
            s_state
            ,s_county
        )
    ORDER BY
        lochierarchy DESC
        ,CASE
            WHEN lochierarchy = 0 THEN s_state
        END
        ,rank_within_parent;
--end query 70 using template query70.tpl
--start query 71 using template query71.tpl
--@public
SELECT
        i_brand_id brand_id
        ,i_brand brand
        ,t_hour
        ,t_minute
        ,SUM(ext_price) ext_price
    FROM
        item
        ,(
            SELECT
                    ws_ext_sales_price AS ext_price
                    ,ws_sold_date_sk AS sold_date_sk
                    ,ws_item_sk AS sold_item_sk
                    ,ws_sold_time_sk AS time_sk
                FROM
                    web_sales
                    ,date_dim
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
                    ,date_dim
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
                    ,date_dim
                WHERE
                    d_date_sk = ss_sold_date_sk
                    AND d_moy = 12
                    AND d_year = 2002
        ) AS tmp
        ,time_dim
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
        ,i_brand_id;
--end query 71 using template query71.tpl
--start query 72 using template query72.tpl
--@private, @condition(demographics)
SELECT
        top 100 i_item_desc
        ,w_warehouse_name
        ,d1.d_week_seq
        ,COUNT(CASE
            WHEN p_promo_sk IS null THEN 1
            ELSE 0
        END) no_promo
        ,COUNT(CASE
            WHEN p_promo_sk IS NOT null THEN 1
            ELSE 0
        END) promo
        ,COUNT(*) total_cnt
    FROM
        catalog_sales
            JOIN inventory
                ON (cs_item_sk = inv_item_sk)
            JOIN warehouse
                ON (w_warehouse_sk = inv_warehouse_sk)
            JOIN item
                ON (i_item_sk = cs_item_sk)
            JOIN customer_demographics
                ON (cs_bill_cdemo_sk = cd_demo_sk)
            JOIN household_demographics
                ON (cs_bill_hdemo_sk = hd_demo_sk)
            JOIN date_dim d1
                ON (cs_sold_date_sk = d1.d_date_sk)
            JOIN date_dim d2
                ON (inv_date_sk = d2.d_date_sk)
            JOIN date_dim d3
                ON (cs_ship_date_sk = d3.d_date_sk)
            LEFT OUTER JOIN promotion
                ON (cs_promo_sk = p_promo_sk)
            LEFT OUTER JOIN catalog_returns
                ON (
                    cr_item_sk = cs_item_sk
                    AND cr_order_number = cs_order_number
                )
    WHERE
        d1.d_week_seq = d2.d_week_seq
        AND inv_quantity_on_hand < cs_quantity
        AND d3.d_date > d1.d_date + 5
        AND hd_buy_potential = '1001-5000'
        AND d1.d_year = 1998
        AND hd_buy_potential = '1001-5000'
        AND cd_marital_status = 'M'
        AND d1.d_year = 1998
    GROUP BY
        i_item_desc
        ,w_warehouse_name
        ,d1.d_week_seq
    ORDER BY
        total_cnt DESC
        ,i_item_desc
        ,w_warehouse_name
        ,d_week_seq;
--end query 72 using template query72.tpl
--start query 73 using template query73.tpl
--@private, @projection(customer), @condition(demographics)
SELECT
        c_last_name
        ,c_first_name
        ,c_salutation
        ,c_preferred_cust_flag
        ,ss_ticket_number
        ,cnt
    FROM
        (
            SELECT
                    ss_ticket_number
                    ,ss_customer_sk
                    ,COUNT(*) cnt
                FROM
                    store_sales
                    ,date_dim
                    ,store
                    ,household_demographics
                WHERE
                    store_sales.ss_sold_date_sk = date_dim.d_date_sk
                    AND store_sales.ss_store_sk = store.s_store_sk
                    AND store_sales.ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND date_dim.d_dom BETWEEN 1 AND 2
                    AND (
                        household_demographics.hd_buy_potential = '>10000'
                        OR household_demographics.hd_buy_potential = '5001-10000'
                    )
                    AND household_demographics.hd_vehicle_count > 0
                    AND CASE
                        WHEN household_demographics.hd_vehicle_count > 0 THEN household_demographics.hd_dep_count / household_demographics.hd_vehicle_count
                        ELSE null
                    END > 1
                    AND date_dim.d_year IN (
                        1998
                        ,1998+1
                        ,1998+2
                    )
                    AND store.s_county IN (
                        'Williamson County'
                        ,'Williamson County'
                        ,'Williamson County'
                        ,'Williamson County'
                    )
                GROUP BY
                    ss_ticket_number
                    ,ss_customer_sk
        ) dj
        ,customer
    WHERE
        ss_customer_sk = c_customer_sk
        AND cnt BETWEEN 1 AND 5
    ORDER BY
        cnt DESC;
--end query 73 using template query73.tpl
--start query 74 using template query74.tpl
--@private, @projection(customer)
WITH year_total AS (
        SELECT
                c_customer_id customer_id
                ,c_first_name customer_first_name
                ,c_last_name customer_last_name
                ,d_year AS year
                ,MAX(ss_net_paid) year_total
                ,'s' sale_type
            FROM
                customer
                ,store_sales
                ,date_dim
            WHERE
                c_customer_sk = ss_customer_sk
                AND ss_sold_date_sk = d_date_sk
                AND d_year IN (
                    1998
                    ,1998+1
                )
            GROUP BY
                c_customer_id
                ,c_first_name
                ,c_last_name
                ,d_year
    UNION ALL
    SELECT
            c_customer_id customer_id
            ,c_first_name customer_first_name
            ,c_last_name customer_last_name
            ,d_year AS year
            ,MAX(ws_net_paid) year_total
            ,'w' sale_type
        FROM
            customer
            ,web_sales
            ,date_dim
        WHERE
            c_customer_sk = ws_bill_customer_sk
            AND ws_sold_date_sk = d_date_sk
            AND d_year IN (
                1998
                ,1998+1
            )
        GROUP BY
            c_customer_id
            ,c_first_name
            ,c_last_name
            ,d_year
) SELECT
        top 100 t_s_secyear.customer_id
        ,t_s_secyear.customer_first_name
        ,t_s_secyear.customer_last_name
    FROM
        year_total t_s_firstyear
        ,year_total t_s_secyear
        ,year_total t_w_firstyear
        ,year_total t_w_secyear
    WHERE
        t_s_secyear.customer_id = t_s_firstyear.customer_id
        AND t_s_firstyear.customer_id = t_w_secyear.customer_id
        AND t_s_firstyear.customer_id = t_w_firstyear.customer_id
        AND t_s_firstyear.sale_type = 's'
        AND t_w_firstyear.sale_type = 'w'
        AND t_s_secyear.sale_type = 's'
        AND t_w_secyear.sale_type = 'w'
        AND t_s_firstyear.year = 1998
        AND t_s_secyear.year = 1998+1
        AND t_w_firstyear.year = 1998
        AND t_w_secyear.year = 1998+1
        AND t_s_firstyear.year_total > 0
        AND t_w_firstyear.year_total > 0
        AND CASE
            WHEN t_w_firstyear.year_total > 0 THEN t_w_secyear.year_total / t_w_firstyear.year_total
            ELSE null
        END > CASE
            WHEN t_s_firstyear.year_total > 0 THEN t_s_secyear.year_total / t_s_firstyear.year_total
            ELSE null
        END
    ORDER BY
        1
        ,2
        ,3;
--end query 74 using template query74.tpl
--start query 75 using template query75.tpl
-- For two consecutive years track the sales of items by brand, class and category.
--@public
WITH all_sales AS (
        SELECT
                d_year
                ,i_brand_id
                ,i_class_id
                ,i_category_id
                ,i_manufact_id
                ,SUM(sales_cnt) AS sales_cnt
                ,SUM(sales_amt) AS sales_amt
            FROM
                (
                    SELECT
                            d_year
                            ,i_brand_id
                            ,i_class_id
                            ,i_category_id
                            ,i_manufact_id
                            ,cs_quantity - COALESCE(cr_return_quantity, 0) AS sales_cnt
                            ,cs_ext_sales_price - COALESCE(cr_return_amount, 0.0) AS sales_amt
                        FROM
                            catalog_sales
                                JOIN item
                                    ON i_item_sk = cs_item_sk
                                JOIN date_dim
                                    ON d_date_sk = cs_sold_date_sk
                                LEFT JOIN catalog_returns
                                    ON (
                                        cs_order_number = cr_order_number
                                        AND cs_item_sk = cr_item_sk
                                    )
                        WHERE
                            i_category = 'Shoes'
                    UNION
                    SELECT
                            d_year
                            ,i_brand_id
                            ,i_class_id
                            ,i_category_id
                            ,i_manufact_id
                            ,ss_quantity - COALESCE(sr_return_quantity, 0) AS sales_cnt
                            ,ss_ext_sales_price - COALESCE(sr_return_amt, 0.0) AS sales_amt
                        FROM
                            store_sales
                                JOIN item
                                    ON i_item_sk = ss_item_sk
                                JOIN date_dim
                                    ON d_date_sk = ss_sold_date_sk
                                LEFT JOIN store_returns
                                    ON (
                                        ss_ticket_number = sr_ticket_number
                                        AND ss_item_sk = sr_item_sk
                                    )
                        WHERE
                            i_category = 'Shoes'
                    UNION
                    SELECT
                            d_year
                            ,i_brand_id
                            ,i_class_id
                            ,i_category_id
                            ,i_manufact_id
                            ,ws_quantity - COALESCE(wr_return_quantity, 0) AS sales_cnt
                            ,ws_ext_sales_price - COALESCE(wr_return_amt, 0.0) AS sales_amt
                        FROM
                            web_sales
                                JOIN item
                                    ON i_item_sk = ws_item_sk
                                JOIN date_dim
                                    ON d_date_sk = ws_sold_date_sk
                                LEFT JOIN web_returns
                                    ON (
                                        ws_order_number = wr_order_number
                                        AND ws_item_sk = wr_item_sk
                                    )
                        WHERE
                            i_category = 'Shoes'
                ) sales_detail
            GROUP BY
                d_year
                ,i_brand_id
                ,i_class_id
                ,i_category_id
                ,i_manufact_id
) SELECT
        top 100 prev_yr.d_year AS prev_year
        ,curr_yr.d_year AS year
        ,curr_yr.i_brand_id
        ,curr_yr.i_class_id
        ,curr_yr.i_category_id
        ,curr_yr.i_manufact_id
        ,prev_yr.sales_cnt AS prev_yr_cnt
        ,curr_yr.sales_cnt AS curr_yr_cnt
        ,curr_yr.sales_cnt - prev_yr.sales_cnt AS sales_cnt_diff
        ,curr_yr.sales_amt - prev_yr.sales_amt AS sales_amt_diff
    FROM
        all_sales curr_yr
        ,all_sales prev_yr
    WHERE
        curr_yr.i_brand_id = prev_yr.i_brand_id
        AND curr_yr.i_class_id = prev_yr.i_class_id
        AND curr_yr.i_category_id = prev_yr.i_category_id
        AND curr_yr.i_manufact_id = prev_yr.i_manufact_id
        AND curr_yr.d_year = 2000
        AND prev_yr.d_year = 2000-1
        AND CAST(
            curr_yr.sales_cnt AS DECIMAL(17, 2)
        ) / CAST(
            prev_yr.sales_cnt AS DECIMAL(17, 2)
        ) < 0.9
    ORDER BY
        sales_cnt_diff;
--end query 75 using template query75.tpl
--start query 76 using template query76.tpl
--@public
SELECT
        top 100 channel
        ,col_name
        ,d_year
        ,d_qoy
        ,i_category
        ,COUNT(*) sales_cnt
        ,SUM(ext_sales_price) sales_amt
    FROM
        (
            SELECT
                    'store' AS channel
                    ,'ss_hdemo_sk' col_name
                    ,d_year
                    ,d_qoy
                    ,i_category
                    ,ss_ext_sales_price ext_sales_price
                FROM
                    store_sales
                    ,item
                    ,date_dim
                WHERE
                    ss_hdemo_sk IS NULL
                    AND ss_sold_date_sk = d_date_sk
                    AND ss_item_sk = i_item_sk
            UNION ALL
            SELECT
                    'web' AS channel
                    ,'ws_promo_sk' col_name
                    ,d_year
                    ,d_qoy
                    ,i_category
                    ,ws_ext_sales_price ext_sales_price
                FROM
                    web_sales
                    ,item
                    ,date_dim
                WHERE
                    ws_promo_sk IS NULL
                    AND ws_sold_date_sk = d_date_sk
                    AND ws_item_sk = i_item_sk
            UNION ALL
            SELECT
                    'catalog' AS channel
                    ,'cs_bill_customer_sk' col_name
                    ,d_year
                    ,d_qoy
                    ,i_category
                    ,cs_ext_sales_price ext_sales_price
                FROM
                    catalog_sales
                    ,item
                    ,date_dim
                WHERE
                    cs_bill_customer_sk IS NULL
                    AND cs_sold_date_sk = d_date_sk
                    AND cs_item_sk = i_item_sk
        ) foo
    GROUP BY
        channel
        ,col_name
        ,d_year
        ,d_qoy
        ,i_category
    ORDER BY
        channel
        ,col_name
        ,d_year
        ,d_qoy
        ,i_category;
--end query 76 using template query76.tpl
--start query 77 using template query77.tpl
--@public
WITH ss AS (
        SELECT
                s_store_sk
                ,SUM(ss_ext_sales_price) AS sales
                ,SUM(ss_net_profit) AS profit
            FROM
                store_sales
                ,date_dim
                ,store
            WHERE
                ss_sold_date_sk = d_date_sk
                AND d_date BETWEEN cast(
                    '1998-08-14' AS DATE
                ) AND (
                    cast(
                        '1998-08-14' AS DATE
                    ) + 30 days
                )
                AND ss_store_sk = s_store_sk
            GROUP BY
                s_store_sk
)
,sr AS (
    SELECT
            s_store_sk
            ,SUM(sr_return_amt) AS returns
            ,SUM(sr_net_loss) AS profit_loss
        FROM
            store_returns
            ,date_dim
            ,store
        WHERE
            sr_returned_date_sk = d_date_sk
            AND d_date BETWEEN cast(
                '1998-08-14' AS DATE
            ) AND (
                cast(
                    '1998-08-14' AS DATE
                ) + 30 days
            )
            AND sr_store_sk = s_store_sk
        GROUP BY
            s_store_sk
)
,cs AS (
    SELECT
            cs_call_center_sk
            ,SUM(cs_ext_sales_price) AS sales
            ,SUM(cs_net_profit) AS profit
        FROM
            catalog_sales
            ,date_dim
        WHERE
            cs_sold_date_sk = d_date_sk
            AND d_date BETWEEN cast(
                '1998-08-14' AS DATE
            ) AND (
                cast(
                    '1998-08-14' AS DATE
                ) + 30 days
            )
        GROUP BY
            cs_call_center_sk
)
,cr AS (
    SELECT
            SUM(cr_return_amount) AS returns
            ,SUM(cr_net_loss) AS profit_loss
        FROM
            catalog_returns
            ,date_dim
        WHERE
            cr_returned_date_sk = d_date_sk
            AND d_date BETWEEN cast(
                '1998-08-14' AS DATE
            ) AND (
                cast(
                    '1998-08-14' AS DATE
                ) + 30 days
            )
)
,ws AS (
    SELECT
            wp_web_page_sk
            ,SUM(ws_ext_sales_price) AS sales
            ,SUM(ws_net_profit) AS profit
        FROM
            web_sales
            ,date_dim
            ,web_page
        WHERE
            ws_sold_date_sk = d_date_sk
            AND d_date BETWEEN cast(
                '1998-08-14' AS DATE
            ) AND (
                cast(
                    '1998-08-14' AS DATE
                ) + 30 days
            )
            AND ws_web_page_sk = wp_web_page_sk
        GROUP BY
            wp_web_page_sk
)
,wr AS (
    SELECT
            wp_web_page_sk
            ,SUM(wr_return_amt) AS returns
            ,SUM(wr_net_loss) AS profit_loss
        FROM
            web_returns
            ,date_dim
            ,web_page
        WHERE
            wr_returned_date_sk = d_date_sk
            AND d_date BETWEEN cast(
                '1998-08-14' AS DATE
            ) AND (
                cast(
                    '1998-08-14' AS DATE
                ) + 30 days
            )
            AND wr_web_page_sk = wp_web_page_sk
        GROUP BY
            wp_web_page_sk
) SELECT
        top 100 channel
        ,id
        ,SUM(sales) AS sales
        ,SUM(returns) AS returns
        ,SUM(profit) AS profit
    FROM
        (
            SELECT
                    'store channel' AS channel
                    ,ss.s_store_sk AS id
                    ,sales
                    ,COALESCE(returns, 0) AS returns
                    ,(
                        profit - COALESCE(profit_loss, 0)
                    ) AS profit
                FROM
                    ss
                        LEFT JOIN sr
                            ON ss.s_store_sk = sr.s_store_sk
            UNION ALL
            SELECT
                    'catalog channel' AS channel
            ,cs_call_center_sk AS id
            ,sales
            ,returns
            ,(profit - profit_loss) AS profit
        FROM
            cs
            ,cr
            UNION ALL
            SELECT
                    'web channel' AS channel
                    ,ws.wp_web_page_sk AS id
                    ,sales
                    ,COALESCE(returns, 0) returns
                    ,(
                        profit - COALESCE(profit_loss, 0)
                    ) AS profit
                FROM
                    ws
                        LEFT JOIN wr
                            ON ws.wp_web_page_sk = wr.wp_web_page_sk
        ) x
GROUP BY
rollup(
    channel
    ,id
)
ORDER BY
channel
,id;
--end query 77 using template query77.tpl
--start query 78 using template query78.tpl
--@private, @projection(customer)
WITH ws AS (
        SELECT
                d_year AS ws_sold_year
                ,ws_item_sk
                ,ws_bill_customer_sk ws_customer_sk
                ,SUM(ws_quantity) ws_qty
                ,SUM(ws_wholesale_cost) ws_wc
                ,SUM(ws_sales_price) ws_sp
            FROM
                web_sales
                    LEFT JOIN web_returns
                        ON wr_order_number = ws_order_number
                        AND ws_item_sk = wr_item_sk
                    JOIN date_dim
                        ON ws_sold_date_sk = d_date_sk
            WHERE
                wr_order_number IS null
            GROUP BY
                d_year
                ,ws_item_sk
                ,ws_bill_customer_sk
)
,cs AS (
    SELECT
            d_year AS cs_sold_year
            ,cs_item_sk
            ,cs_bill_customer_sk cs_customer_sk
            ,SUM(cs_quantity) cs_qty
            ,SUM(cs_wholesale_cost) cs_wc
            ,SUM(cs_sales_price) cs_sp
        FROM
            catalog_sales
                LEFT JOIN catalog_returns
                    ON cr_order_number = cs_order_number
                    AND cs_item_sk = cr_item_sk
                JOIN date_dim
                    ON cs_sold_date_sk = d_date_sk
        WHERE
            cr_order_number IS null
        GROUP BY
            d_year
            ,cs_item_sk
            ,cs_bill_customer_sk
)
,ss AS (
    SELECT
            d_year AS ss_sold_year
            ,ss_item_sk
            ,ss_customer_sk
            ,SUM(ss_quantity) ss_qty
            ,SUM(ss_wholesale_cost) ss_wc
            ,SUM(ss_sales_price) ss_sp
        FROM
            store_sales
                LEFT JOIN store_returns
                    ON sr_ticket_number = ss_ticket_number
                    AND ss_item_sk = sr_item_sk
                JOIN date_dim
                    ON ss_sold_date_sk = d_date_sk
        WHERE
            sr_ticket_number IS null
        GROUP BY
            d_year
            ,ss_item_sk
            ,ss_customer_sk
) SELECT
        top 100 ss_customer_sk
        ,ROUND(ss_qty / (
            COALESCE(ws_qty + cs_qty, 1)
        ), 2) ratio
        ,ss_qty store_qty
        ,ss_wc store_wholesale_cost
        ,ss_sp store_sales_price
        ,COALESCE(ws_qty, 0) + COALESCE(cs_qty, 0) other_chan_qty
        ,COALESCE(ws_wc, 0) + COALESCE(cs_wc, 0) other_chan_wholesale_cost
        ,COALESCE(ws_sp, 0) + COALESCE(cs_sp, 0) other_chan_sales_price
    FROM
        ss
            LEFT JOIN ws
                ON (
                    ws_sold_year = ss_sold_year
                    AND ws_item_sk = ss_item_sk
                    AND ws_customer_sk = ss_customer_sk
                )
            LEFT JOIN cs
                ON (
                    cs_sold_year = ss_sold_year
                    AND cs_item_sk = cs_item_sk
                    AND cs_customer_sk = ss_customer_sk
                )
    WHERE
        COALESCE(ws_qty, 0) > 0
        AND COALESCE(cs_qty, 0) > 0
        AND ss_sold_year = 2001
    ORDER BY
        ss_customer_sk
        ,ss_qty DESC
        ,ss_wc DESC
        ,ss_sp DESC
        ,other_chan_qty
        ,other_chan_wholesale_cost
        ,other_chan_sales_price
        ,ROUND(ss_qty / (
            COALESCE(ws_qty + cs_qty, 1)
        ), 2);
--end query 78 using template query78.tpl
--start query 79 using template query79.tpl
--@private, @projection(customer), @condition(demographics)
SELECT
        top 100 c_last_name
        ,c_first_name
        ,SUBSTR(s_city, 1, 30)
        ,ss_ticket_number
        ,amt
        ,profit
    FROM
        (
            SELECT
                    ss_ticket_number
                    ,ss_customer_sk
                    ,store.s_city
                    ,SUM(ss_coupon_amt) amt
                    ,SUM(ss_net_profit) profit
                FROM
                    store_sales
                    ,date_dim
                    ,store
                    ,household_demographics
                WHERE
                    store_sales.ss_sold_date_sk = date_dim.d_date_sk
                    AND store_sales.ss_store_sk = store.s_store_sk
                    AND store_sales.ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND (
                        household_demographics.hd_dep_count = 1
                        OR household_demographics.hd_vehicle_count > -1
                    )
                    AND date_dim.d_dow = 1
                    AND date_dim.d_year IN (
                        1999
                        ,1999+1
                        ,1999+2
                    )
                    AND store.s_number_employees BETWEEN 200 AND 295
                GROUP BY
                    ss_ticket_number
                    ,ss_customer_sk
                    ,ss_addr_sk
                    ,store.s_city
        ) ms
        ,customer
    WHERE
        ss_customer_sk = c_customer_sk
    ORDER BY
        c_last_name
        ,c_first_name
        ,SUBSTR(s_city, 1, 30)
        ,profit;
--end query 79 using template query79.tpl
--start query 80 using template query80.tpl
--@public
WITH ssr AS (
        SELECT
                s_store_id AS store_id
                ,SUM(ss_ext_sales_price) AS sales
                ,SUM(COALESCE(sr_return_amt, 0)) AS returns
                ,SUM(ss_net_profit - COALESCE(sr_net_loss, 0)) AS profit
            FROM
                store_sales
                    LEFT OUTER JOIN store_returns
                        ON (
                            ss_item_sk = sr_item_sk
                            AND ss_ticket_number = sr_ticket_number
                        )
                ,date_dim
                ,store
                ,item
                ,promotion
            WHERE
                ss_sold_date_sk = d_date_sk
                AND d_date BETWEEN cast(
                    '1999-08-25' AS DATE
                ) AND (
                    cast(
                        '1999-08-25' AS DATE
                    ) + 30 days
                )
                AND ss_store_sk = s_store_sk
                AND ss_item_sk = i_item_sk
                AND i_current_price > 50
                AND ss_promo_sk = p_promo_sk
                AND p_channel_tv = 'N'
            GROUP BY
                s_store_id
)
,csr AS (
    SELECT
            cp_catalog_page_id AS catalog_page_id
            ,SUM(cs_ext_sales_price) AS sales
            ,SUM(COALESCE(cr_return_amount, 0)) AS returns
            ,SUM(cs_net_profit - COALESCE(cr_net_loss, 0)) AS profit
        FROM
            catalog_sales
                LEFT OUTER JOIN catalog_returns
                    ON (
                        cs_item_sk = cr_item_sk
                        AND cs_order_number = cr_order_number
                    )
            ,date_dim
            ,catalog_page
            ,item
            ,promotion
        WHERE
            cs_sold_date_sk = d_date_sk
            AND d_date BETWEEN cast(
                '1999-08-25' AS DATE
            ) AND (
                cast(
                    '1999-08-25' AS DATE
                ) + 30 days
            )
            AND cs_catalog_page_sk = cp_catalog_page_sk
            AND cs_item_sk = i_item_sk
            AND i_current_price > 50
            AND cs_promo_sk = p_promo_sk
            AND p_channel_tv = 'N'
        GROUP BY
            cp_catalog_page_id
)
,wsr AS (
    SELECT
            web_site_id
            ,SUM(ws_ext_sales_price) AS sales
            ,SUM(COALESCE(wr_return_amt, 0)) AS returns
            ,SUM(ws_net_profit - COALESCE(wr_net_loss, 0)) AS profit
        FROM
            web_sales
                LEFT OUTER JOIN web_returns
                    ON (
                        ws_item_sk = wr_item_sk
                        AND ws_order_number = wr_order_number
                    )
            ,date_dim
            ,web_site
            ,item
            ,promotion
        WHERE
            ws_sold_date_sk = d_date_sk
            AND d_date BETWEEN cast(
                '1999-08-25' AS DATE
            ) AND (
                cast(
                    '1999-08-25' AS DATE
                ) + 30 days
            )
            AND ws_web_site_sk = web_site_sk
            AND ws_item_sk = i_item_sk
            AND i_current_price > 50
            AND ws_promo_sk = p_promo_sk
            AND p_channel_tv = 'N'
        GROUP BY
            web_site_id
) SELECT
        top 100 channel
        ,id
        ,SUM(sales) AS sales
        ,SUM(returns) AS returns
        ,SUM(profit) AS profit
    FROM
        (
            SELECT
                    'store channel' AS channel
                    ,'store' || store_id AS id
                    ,sales
                    ,returns
                    ,profit
                FROM
                    ssr
            UNION ALL
            SELECT
                    'catalog channel' AS channel
                    ,'catalog_page' || catalog_page_id AS id
                    ,sales
                    ,returns
                    ,profit
                FROM
                    csr
            UNION ALL
            SELECT
                    'web channel' AS channel
                    ,'web_site' || web_site_id AS id
                    ,sales
                    ,returns
                    ,profit
                FROM
                    wsr
        ) x
    GROUP BY
        rollup(
            channel
            ,id
        )
    ORDER BY
        channel
        ,id;
--end query 80 using template query80.tpl