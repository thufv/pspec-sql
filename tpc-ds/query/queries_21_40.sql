--start query 21 using template query21.tpl
-- @public
SELECT
        top 100 *
    FROM
        (
            SELECT
                    w_warehouse_name
                    ,i_item_id
                    ,SUM(CASE
                        WHEN (
                            cast(
                                d_date AS DATE
                            ) < cast(
                                '2001-03-14' AS DATE
                            )
                        ) THEN inv_quantity_on_hand
                        ELSE 0
                    END) AS inv_before
                    ,SUM(CASE
                        WHEN (
                            cast(
                                d_date AS DATE
                            ) >= cast(
                                '2001-03-14' AS DATE
                            )
                        ) THEN inv_quantity_on_hand
                        ELSE 0
                    END) AS inv_after
                FROM
                    inventory
                    ,warehouse
                    ,item
                    ,date_dim
                WHERE
                    i_current_price BETWEEN 0.99 AND 1.49
                    AND i_item_sk = inv_item_sk
                    AND inv_warehouse_sk = w_warehouse_sk
                    AND inv_date_sk = d_date_sk
                    AND d_date BETWEEN (
                        cast(
                            '2001-03-14' AS DATE
                        ) - 30 days
                    ) AND (
                        cast(
                            '2001-03-14' AS DATE
                        ) + 30 days
                    )
                GROUP BY
                    w_warehouse_name
                    ,i_item_id
        ) x
    WHERE
        (
            CASE
                WHEN inv_before > 0 THEN inv_after / inv_before
                ELSE null
            END
        ) BETWEEN 2.0 / 3.0 AND 3.0 / 2.0
    ORDER BY
        w_warehouse_name
        ,i_item_id;
--end query 21 using template query21.tpl
--start query 22 using template query22.tpl
-- @public
SELECT
        top 100 i_product_name
        ,i_brand
        ,i_class
        ,i_category
        ,AVG(inv_quantity_on_hand) qoh
    FROM
        inventory
        ,date_dim
        ,item
        ,warehouse
    WHERE
        inv_date_sk = d_date_sk
        AND inv_item_sk = i_item_sk
        AND inv_warehouse_sk = w_warehouse_sk
        AND d_month_seq BETWEEN 1199 AND 1199 + 11
    GROUP BY
        rollup(
            i_product_name
            ,i_brand
            ,i_class
            ,i_category
        )
    ORDER BY
        qoh
        ,i_product_name
        ,i_brand
        ,i_class
        ,i_category;
--end query 22 using template query22.tpl
--start query 23 using template query23.tpl
-- @private, @projection(name)
WITH frequent_ss_items AS (
        SELECT
                SUBSTR(i_item_desc, 1, 30) itemdesc
                ,i_item_sk item_sk
                ,d_date solddate
                ,COUNT(*) cnt
            FROM
                store_sales
                ,date_dim
                ,item
            WHERE
                ss_sold_date_sk = d_date_sk
                AND ss_item_sk = i_item_sk
                AND d_year IN (
                    1999
                    ,1999+1
                    ,1999+2
                    ,1999+3
                )
            GROUP BY
                SUBSTR(i_item_desc, 1, 30)
                ,i_item_sk
                ,d_date
            HAVING
                COUNT(*) > 4
)
,max_store_sales AS (
    SELECT
            MAX(csales) tpcds_cmax
        FROM
            (
                SELECT
                        c_customer_sk
                        ,SUM(ss_quantity * ss_sales_price) csales
                    FROM
                        store_sales
                        ,customer
                        ,date_dim
                    WHERE
                        ss_customer_sk = c_customer_sk
                        AND ss_sold_date_sk = d_date_sk
                        AND d_year IN (
                            1999
                            ,1999+1
                            ,1999+2
                            ,1999+3
                        )
                    GROUP BY
                        c_customer_sk
            ) x
)
,best_ss_customer AS (
    SELECT
            c_customer_sk
            ,SUM(ss_quantity * ss_sales_price) ssales
        FROM
            store_sales
            ,customer
        WHERE
            ss_customer_sk = c_customer_sk
        GROUP BY
            c_customer_sk
        HAVING
            SUM(ss_quantity * ss_sales_price) > (95 / 100.0) * (
                SELECT
                        *
                    FROM
                        max_store_sales
            )
) SELECT
        top 100 SUM(sales)
    FROM
        (
            (
                SELECT
                        cs_quantity * cs_list_price sales
                    FROM
                        catalog_sales
                        ,date_dim
                    WHERE
                        d_year = 1999
                        AND d_moy = 6
                        AND cs_sold_date_sk = d_date_sk
                        AND cs_item_sk IN (
                            SELECT
                                    item_sk
                                FROM
                                    frequent_ss_items
                        )
                        AND cs_bill_customer_sk IN (
                            SELECT
                                    c_customer_sk
                                FROM
                                    best_ss_customer
                        )
            )
    UNION ALL
    (
        SELECT
                ws_quantity * ws_list_price sales
            FROM
                web_sales
                ,date_dim
            WHERE
                d_year = 1999
                AND d_moy = 6
                AND ws_sold_date_sk = d_date_sk
                AND ws_item_sk IN (
                    SELECT
                            item_sk
                        FROM
                            frequent_ss_items
                )
                AND ws_bill_customer_sk IN (
                    SELECT
                            c_customer_sk
                        FROM
                            best_ss_customer
                )
    )
        ) y;
WITH frequent_ss_items AS (
        SELECT
                SUBSTR(i_item_desc, 1, 30) itemdesc
                ,i_item_sk item_sk
                ,d_date solddate
                ,COUNT(*) cnt
            FROM
                store_sales
                ,date_dim
                ,item
            WHERE
                ss_sold_date_sk = d_date_sk
                AND ss_item_sk = i_item_sk
                AND d_year IN (
                    1999
                    ,1999 + 1
                    ,1999 + 2
                    ,1999 + 3
                )
            GROUP BY
                SUBSTR(i_item_desc, 1, 30)
                ,i_item_sk
                ,d_date
            HAVING
                COUNT(*) > 4
)
,max_store_sales AS (
    SELECT
            MAX(csales) tpcds_cmax
        FROM
            (
                SELECT
                        c_customer_sk
                        ,SUM(ss_quantity * ss_sales_price) csales
                    FROM
                        store_sales
                        ,customer
                        ,date_dim
                    WHERE
                        ss_customer_sk = c_customer_sk
                        AND ss_sold_date_sk = d_date_sk
                        AND d_year IN (
                            1999
                            ,1999+1
                            ,1999+2
                            ,1999+3
                        )
                    GROUP BY
                        c_customer_sk
            ) x
)
,best_ss_customer AS (
    SELECT
            c_customer_sk
            ,SUM(ss_quantity * ss_sales_price) ssales
        FROM
            store_sales
            ,customer
        WHERE
            ss_customer_sk = c_customer_sk
        GROUP BY
            c_customer_sk
        HAVING
            SUM(ss_quantity * ss_sales_price) > (95 / 100.0) * (
                SELECT
                        *
                    FROM
                        max_store_sales
            )
) SELECT
        top 100 c_last_name
        ,c_first_name
        ,sales
    FROM
        (
            (
                SELECT
                        c_last_name
                        ,c_first_name
                        ,SUM(cs_quantity * cs_list_price) sales
                    FROM
                        catalog_sales
                        ,customer
                        ,date_dim
                    WHERE
                        d_year = 1999
                        AND d_moy = 6
                        AND cs_sold_date_sk = d_date_sk
                        AND cs_item_sk IN (
                            SELECT
                                    item_sk
                                FROM
                                    frequent_ss_items
                        )
                        AND cs_bill_customer_sk IN (
                            SELECT
                                    c_customer_sk
                                FROM
                                    best_ss_customer
                        )
                        AND cs_bill_customer_sk = c_customer_sk
                    GROUP BY
                        c_last_name
                        ,c_first_name
            )
    UNION ALL
    (
        SELECT
                c_last_name
                ,c_first_name
                ,SUM(ws_quantity * ws_list_price) sales
            FROM
                web_sales
                ,customer
                ,date_dim
            WHERE
                d_year = 1999
                AND d_moy = 6
                AND ws_sold_date_sk = d_date_sk
                AND ws_item_sk IN (
                    SELECT
                            item_sk
                        FROM
                            frequent_ss_items
                )
                AND ws_bill_customer_sk IN (
                    SELECT
                            c_customer_sk
                        FROM
                            best_ss_customer
                )
                AND ws_bill_customer_sk = c_customer_sk
            GROUP BY
                c_last_name
                ,c_first_name
    )
        ) y
    ORDER BY
        c_last_name
        ,c_first_name
        ,sales;
--end query 23 using template query23.tpl
--start query 24 using template query24.tpl
-- @private, @projection(name), @condition(customer_address)
WITH ssales AS (
        SELECT
                c_last_name
                ,c_first_name
                ,s_store_name
                ,ca_state
                ,s_state
                ,i_color
                ,i_current_price
                ,i_manager_id
                ,i_units
                ,i_size
                ,SUM(ss_ext_sales_price) netpaid
            FROM
                store_sales
                ,store_returns
                ,store
                ,item
                ,customer
                ,customer_address
            WHERE
                ss_ticket_number = sr_ticket_number
                AND ss_item_sk = sr_item_sk
                AND ss_customer_sk = c_customer_sk
                AND ss_item_sk = i_item_sk
                AND ss_store_sk = s_store_sk
                AND c_birth_country = UPPER(ca_country)
                AND s_zip = ca_zip
                AND s_market_id = 8
            GROUP BY
                c_last_name
                ,c_first_name
                ,s_store_name
                ,ca_state
                ,s_state
                ,i_color
                ,i_current_price
                ,i_manager_id
                ,i_units
                ,i_size
) SELECT
        c_last_name
        ,c_first_name
        ,s_store_name
        ,SUM(netpaid) paid
    FROM
        ssales
    WHERE
        i_color = 'lawn'
    GROUP BY
        c_last_name
        ,c_first_name
        ,s_store_name
    HAVING
        SUM(netpaid) > (
            SELECT
                    0.05 * AVG(netpaid)
                FROM
                    ssales
        );
WITH ssales AS (
        SELECT
                c_last_name
                ,c_first_name
                ,s_store_name
                ,ca_state
                ,s_state
                ,i_color
                ,i_current_price
                ,i_manager_id
                ,i_units
                ,i_size
                ,SUM(ss_ext_sales_price) netpaid
            FROM
                store_sales
                ,store_returns
                ,store
                ,item
                ,customer
                ,customer_address
            WHERE
                ss_ticket_number = sr_ticket_number
                AND ss_item_sk = sr_item_sk
                AND ss_customer_sk = c_customer_sk
                AND ss_item_sk = i_item_sk
                AND ss_store_sk = s_store_sk
                AND c_birth_country = UPPER(ca_country)
                AND s_zip = ca_zip
                AND s_market_id = 8
            GROUP BY
                c_last_name
                ,c_first_name
                ,s_store_name
                ,ca_state
                ,s_state
                ,i_color
                ,i_current_price
                ,i_manager_id
                ,i_units
                ,i_size
) SELECT
        c_last_name
        ,c_first_name
        ,s_store_name
        ,SUM(netpaid) paid
    FROM
        ssales
    WHERE
        i_color = 'coral'
    GROUP BY
        c_last_name
        ,c_first_name
        ,s_store_name
    HAVING
        SUM(netpaid) > (
            SELECT
                    0.05 * AVG(netpaid)
                FROM
                    ssales
        );
--end query 24 using template query24.tpl
--start query 25 using template query25.tpl
-- @public
SELECT
        top 100 i_item_id
        ,i_item_desc
        ,s_store_id
        ,s_store_name
        ,MAX(ss_net_profit) AS store_sales_profit
        ,MAX(sr_net_loss) AS store_returns_loss
        ,MAX(cs_net_profit) AS catalog_sales_profit
    FROM
        store_sales
        ,store_returns
        ,catalog_sales
        ,date_dim d1
        ,date_dim d2
        ,date_dim d3
        ,store
        ,item
    WHERE
        d1.d_moy = 4
        AND d1.d_year = 2001
        AND d1.d_date_sk = ss_sold_date_sk
        AND i_item_sk = ss_item_sk
        AND s_store_sk = ss_store_sk
        AND ss_customer_sk = sr_customer_sk
        AND ss_item_sk = sr_item_sk
        AND ss_ticket_number = sr_ticket_number
        AND sr_returned_date_sk = d2.d_date_sk
        AND d2.d_moy BETWEEN 4 AND 10
        AND d2.d_year = 2001
        AND sr_customer_sk = cs_bill_customer_sk
        AND sr_item_sk = cs_item_sk
        AND cs_sold_date_sk = d3.d_date_sk
        AND d3.d_moy BETWEEN 4 AND 10
        AND d3.d_year = 2001
    GROUP BY
        i_item_id
        ,i_item_desc
        ,s_store_id
        ,s_store_name
    ORDER BY
        i_item_id
        ,i_item_desc
        ,s_store_id
        ,s_store_name;
--end query 25 using template query25.tpl
--start query 26 using template query26.tpl
--@private, @condition(demographics)
SELECT
        top 100 i_item_id
        ,AVG(cs_quantity) agg1
        ,AVG(cs_list_price) agg2
        ,AVG(cs_coupon_amt) agg3
        ,AVG(cs_sales_price) agg4
    FROM
        catalog_sales
        ,customer_demographics
        ,date_dim
        ,item
        ,promotion
    WHERE
        cs_sold_date_sk = d_date_sk
        AND cs_item_sk = i_item_sk
        AND cs_bill_cdemo_sk = cd_demo_sk
        AND cs_promo_sk = p_promo_sk
        AND cd_gender = 'M'
        AND cd_marital_status = 'D'
        AND cd_education_status = 'College'
        AND (
            p_channel_email = 'N'
            OR p_channel_event = 'N'
        )
        AND d_year = 2001
    GROUP BY
        i_item_id
    ORDER BY
        i_item_id;
--end query 26 using template query26.tpl
--start query 27 using template query27.tpl
-- @private, @condition(demographics)
SELECT
        top 100 i_item_id
        ,s_state
        ,grouping(s_state) g_state
        ,AVG(ss_quantity) agg1
        ,AVG(ss_list_price) agg2
        ,AVG(ss_coupon_amt) agg3
        ,AVG(ss_sales_price) agg4
    FROM
        store_sales
        ,customer_demographics
        ,date_dim
        ,store
        ,item
    WHERE
        ss_sold_date_sk = d_date_sk
        AND ss_item_sk = i_item_sk
        AND ss_store_sk = s_store_sk
        AND ss_cdemo_sk = cd_demo_sk
        AND cd_gender = 'F'
        AND cd_marital_status = 'U'
        AND cd_education_status = 'Secondary'
        AND d_year = 1999
        AND s_state IN (
            'TN'
            ,'TN'
            ,'TN'
            ,'TN'
            ,'TN'
            ,'TN'
        )
    GROUP BY
        rollup(
            i_item_id
            ,s_state
        )
    ORDER BY
        i_item_id
        ,s_state;
--end query 27 using template query27.tpl
--start query 28 using template query28.tpl
--@public
SELECT
        top 100 *
    FROM
        (
            SELECT
                    AVG(ss_list_price) B1_LP
                    ,COUNT(ss_list_price) B1_CNT
                    ,COUNT(DISTINCT ss_list_price) B1_CNTD
                FROM
                    store_sales
                WHERE
                    ss_quantity BETWEEN 0 AND 5
                    AND (
                        ss_list_price BETWEEN 51 AND 51+10
                        OR ss_coupon_amt BETWEEN 12565 AND 12565+1000
                        OR ss_wholesale_cost BETWEEN 52 AND 52+20
                    )
        ) B1
        ,(
            SELECT
                    AVG(ss_list_price) B2_LP
                    ,COUNT(ss_list_price) B2_CNT
                    ,COUNT(DISTINCT ss_list_price) B2_CNTD
                FROM
                    store_sales
                WHERE
                    ss_quantity BETWEEN 6 AND 10
                    AND (
                        ss_list_price BETWEEN 135 AND 135+10
                        OR ss_coupon_amt BETWEEN 3897 AND 3897+1000
                        OR ss_wholesale_cost BETWEEN 79 AND 79+20
                    )
        ) B2
        ,(
            SELECT
                    AVG(ss_list_price) B3_LP
                    ,COUNT(ss_list_price) B3_CNT
                    ,COUNT(DISTINCT ss_list_price) B3_CNTD
                FROM
                    store_sales
                WHERE
                    ss_quantity BETWEEN 11 AND 15
                    AND (
                        ss_list_price BETWEEN 106 AND 106+10
                        OR ss_coupon_amt BETWEEN 10740 AND 10740+1000
                        OR ss_wholesale_cost BETWEEN 16 AND 16+20
                    )
        ) B3
        ,(
            SELECT
                    AVG(ss_list_price) B4_LP
                    ,COUNT(ss_list_price) B4_CNT
                    ,COUNT(DISTINCT ss_list_price) B4_CNTD
                FROM
                    store_sales
                WHERE
                    ss_quantity BETWEEN 16 AND 20
                    AND (
                        ss_list_price BETWEEN 16 AND 16+10
                        OR ss_coupon_amt BETWEEN 13313 AND 13313+1000
                        OR ss_wholesale_cost BETWEEN 8 AND 8+20
                    )
        ) B4
        ,(
            SELECT
                    AVG(ss_list_price) B5_LP
                    ,COUNT(ss_list_price) B5_CNT
                    ,COUNT(DISTINCT ss_list_price) B5_CNTD
                FROM
                    store_sales
                WHERE
                    ss_quantity BETWEEN 21 AND 25
                    AND (
                        ss_list_price BETWEEN 153 AND 153+10
                        OR ss_coupon_amt BETWEEN 4490 AND 4490+1000
                        OR ss_wholesale_cost BETWEEN 14 AND 14+20
                    )
        ) B5
        ,(
            SELECT
                    AVG(ss_list_price) B6_LP
                    ,COUNT(ss_list_price) B6_CNT
                    ,COUNT(DISTINCT ss_list_price) B6_CNTD
                FROM
                    store_sales
                WHERE
                    ss_quantity BETWEEN 26 AND 30
                    AND (
                        ss_list_price BETWEEN 111 AND 111+10
                        OR ss_coupon_amt BETWEEN 8115 AND 8115+1000
                        OR ss_wholesale_cost BETWEEN 35 AND 35+20
                    )
        ) B6;
--end query 28 using template query28.tpl
--start query 29 using template query29.tpl
-- @public
SELECT
        top 100 i_item_id
        ,i_item_desc
        ,s_store_id
        ,s_store_name
        ,MIN(ss_quantity) AS store_sales_quantity
        ,MIN(sr_return_quantity) AS store_returns_quantity
        ,MIN(cs_quantity) AS catalog_sales_quantity
    FROM
        store_sales
        ,store_returns
        ,catalog_sales
        ,date_dim d1
        ,date_dim d2
        ,date_dim d3
        ,store
        ,item
    WHERE
        d1.d_moy = 4
        AND d1.d_year = 1999
        AND d1.d_date_sk = ss_sold_date_sk
        AND i_item_sk = ss_item_sk
        AND s_store_sk = ss_store_sk
        AND ss_customer_sk = sr_customer_sk
        AND ss_item_sk = sr_item_sk
        AND ss_ticket_number = sr_ticket_number
        AND sr_returned_date_sk = d2.d_date_sk
        AND d2.d_moy BETWEEN 4 AND 4 + 3
        AND d2.d_year = 1999
        AND sr_customer_sk = cs_bill_customer_sk
        AND sr_item_sk = cs_item_sk
        AND cs_sold_date_sk = d3.d_date_sk
        AND d3.d_year IN (
            1999
            ,1999+1
            ,1999+2
        )
    GROUP BY
        i_item_id
        ,i_item_desc
        ,s_store_id
        ,s_store_name
    ORDER BY
        i_item_id
        ,i_item_desc
        ,s_store_id
        ,s_store_name;
--end query 29 using template query29.tpl
--start query 30 using template query30.tpl
--@private, @projection(customer)
WITH customer_total_return AS (
        SELECT
                wr_returning_customer_sk AS ctr_customer_sk
                ,ca_state AS ctr_state
                ,SUM(wr_return_amt) AS ctr_total_return
            FROM
                web_returns
                ,date_dim
                ,customer_address
            WHERE
                wr_returned_date_sk = d_date_sk
                AND d_year = 2000
                AND wr_returning_addr_sk = ca_address_sk
            GROUP BY
                wr_returning_customer_sk
                ,ca_state
) SELECT
        top 100 c_customer_id
        ,c_salutation
        ,c_first_name
        ,c_last_name
        ,c_preferred_cust_flag
        ,c_birth_day
        ,c_birth_month
        ,c_birth_year
        ,c_birth_country
        ,c_login
        ,c_email_address
        ,c_last_review_date
        ,ctr_total_return
    FROM
        customer_total_return ctr1
        ,customer_address
        ,customer
    WHERE
        ctr1.ctr_total_return > (
            SELECT
                    AVG(ctr_total_return) * 1.2
                FROM
                    customer_total_return ctr2
                WHERE
                    ctr1.ctr_state = ctr2.ctr_state
        )
        AND ca_address_sk = c_current_addr_sk
        AND ca_state = 'IL'
        AND ctr1.ctr_customer_sk = c_customer_sk
    ORDER BY
        c_customer_id
        ,c_salutation
        ,c_first_name
        ,c_last_name
        ,c_preferred_cust_flag
        ,c_birth_day
        ,c_birth_month
        ,c_birth_year
        ,c_birth_country
        ,c_login
        ,c_email_address
        ,c_last_review_date
        ,ctr_total_return;
--end query 30 using template query30.tpl
--start query 31 using template query31.tpl
-- @private, @condition(customer_address)
WITH ss AS (
        SELECT
                ca_county
                ,d_qoy
                ,d_year
                ,SUM(ss_ext_sales_price) AS store_sales
            FROM
                store_sales
                ,date_dim
                ,customer_address
            WHERE
                ss_sold_date_sk = d_date_sk
                AND ss_addr_sk = ca_address_sk
            GROUP BY
                ca_county
                ,d_qoy
                ,d_year
)
,ws AS (
    SELECT
            ca_county
            ,d_qoy
            ,d_year
            ,SUM(ws_ext_sales_price) AS web_sales
        FROM
            web_sales
            ,date_dim
            ,customer_address
        WHERE
            ws_sold_date_sk = d_date_sk
            AND ws_bill_addr_sk = ca_address_sk
        GROUP BY
            ca_county
            ,d_qoy
            ,d_year
) SELECT
        /* tt */
        ss1.ca_county
        ,ss1.d_year
        ,ws2.web_sales / ws1.web_sales web_q1_q2_increase
        ,ss2.store_sales / ss1.store_sales store_q1_q2_increase
        ,ws3.web_sales / ws2.web_sales web_q2_q3_increase
        ,ss3.store_sales / ss2.store_sales store_q2_q3_increase
    FROM
        ss ss1
        ,ss ss2
        ,ss ss3
        ,ws ws1
        ,ws ws2
        ,ws ws3
    WHERE
        ss1.d_qoy = 1
        AND ss1.d_year = 2000
        AND ss1.ca_county = ss2.ca_county
        AND ss2.d_qoy = 2
        AND ss2.d_year = 2000
        AND ss2.ca_county = ss3.ca_county
        AND ss3.d_qoy = 3
        AND ss3.d_year = 2000
        AND ss1.ca_county = ws1.ca_county
        AND ws1.d_qoy = 1
        AND ws1.d_year = 2000
        AND ws1.ca_county = ws2.ca_county
        AND ws2.d_qoy = 2
        AND ws2.d_year = 2000
        AND ws1.ca_county = ws3.ca_county
        AND ws3.d_qoy = 3
        AND ws3.d_year = 2000
        AND CASE
            WHEN ws1.web_sales > 0 THEN ws2.web_sales / ws1.web_sales
            ELSE null
        END > CASE
            WHEN ss1.store_sales > 0 THEN ss2.store_sales / ss1.store_sales
            ELSE null
        END
        AND CASE
            WHEN ws2.web_sales > 0 THEN ws3.web_sales / ws2.web_sales
            ELSE null
        END > CASE
            WHEN ss2.store_sales > 0 THEN ss3.store_sales / ss2.store_sales
            ELSE null
        END
    ORDER BY
        web_q2_q3_increase;
--end query 31 using template query31.tpl
--start query 32 using template query32.tpl
-- @public
SELECT
        top 100 SUM(cs_ext_discount_amt) AS "excess discount amount"
    FROM
        catalog_sales
        ,item
        ,date_dim
    WHERE
        i_manufact_id = 291
        AND i_item_sk = cs_item_sk
        AND d_date BETWEEN '2000-03-22' AND (
            cast(
                '2000-03-22' AS DATE
            ) + 90 days
        )
        AND d_date_sk = cs_sold_date_sk
        AND cs_ext_discount_amt > (
            SELECT
                    1.3 * AVG(cs_ext_discount_amt)
                FROM
                    catalog_sales
                    ,date_dim
                WHERE
                    cs_item_sk = i_item_sk
                    AND d_date BETWEEN '2000-03-22' AND (
                        cast(
                            '2000-03-22' AS DATE
                        ) + 90 days
                    )
                    AND d_date_sk = cs_sold_date_sk
        );
--end query 32 using template query32.tpl
--start query 33 using template query33.tpl
-- @public
WITH ss AS (
        SELECT
                i_manufact_id
                ,SUM(ss_ext_sales_price) total_sales
            FROM
                store_sales
                ,date_dim
                ,customer_address
                ,item
            WHERE
                i_manufact_id IN (
                    SELECT
                            i_manufact_id
                        FROM
                            item
                        WHERE
                            i_category IN ('Home')
                )
                AND ss_item_sk = i_item_sk
                AND ss_sold_date_sk = d_date_sk
                AND d_year = 2000
                AND d_moy = 2
                AND ss_addr_sk = ca_address_sk
                AND ca_gmt_offset = -5
            GROUP BY
                i_manufact_id
)
,cs AS (
    SELECT
            i_manufact_id
            ,SUM(cs_ext_sales_price) total_sales
        FROM
            catalog_sales
            ,date_dim
            ,customer_address
            ,item
        WHERE
            i_manufact_id IN (
                SELECT
                        i_manufact_id
                    FROM
                        item
                    WHERE
                        i_category IN ('Home')
            )
            AND cs_item_sk = i_item_sk
            AND cs_sold_date_sk = d_date_sk
            AND d_year = 2000
            AND d_moy = 2
            AND cs_bill_addr_sk = ca_address_sk
            AND ca_gmt_offset = -5
        GROUP BY
            i_manufact_id
)
,ws AS (
    SELECT
            i_manufact_id
            ,SUM(ws_ext_sales_price) total_sales
        FROM
            web_sales
            ,date_dim
            ,customer_address
            ,item
        WHERE
            i_manufact_id IN (
                SELECT
                        i_manufact_id
                    FROM
                        item
                    WHERE
                        i_category IN ('Home')
            )
            AND ws_item_sk = i_item_sk
            AND ws_sold_date_sk = d_date_sk
            AND d_year = 2000
            AND d_moy = 2
            AND ws_bill_addr_sk = ca_address_sk
            AND ca_gmt_offset = -5
        GROUP BY
            i_manufact_id
) SELECT
        top 100 i_manufact_id
        ,SUM(total_sales) total_sales
    FROM
        (
            SELECT
                    *
                FROM
                    ss
            UNION ALL
            SELECT
                    *
                FROM
                    cs
            UNION ALL
            SELECT
                    *
                FROM
                    ws
        ) tmp1
    GROUP BY
        i_manufact_id
    ORDER BY
        total_sales;
--end query 33 using template query33.tpl
--start query 34 using template query34.tpl
-- @private, @projection(name), @condition(demographics)
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
                    AND (
                        date_dim.d_dom BETWEEN 1 AND 3
                        OR date_dim.d_dom BETWEEN 25 AND 28
                    )
                    AND (
                        household_demographics.hd_buy_potential = '501-1000'
                        OR household_demographics.hd_buy_potential = '0-500'
                    )
                    AND household_demographics.hd_vehicle_count > 0
                    AND (
                        CASE
                            WHEN household_demographics.hd_vehicle_count > 0 THEN household_demographics.hd_dep_count / household_demographics.hd_vehicle_count
                            ELSE null
                        END
                    ) > 1.2
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
                        ,'Williamson County'
                        ,'Williamson County'
                        ,'Williamson County'
                        ,'Williamson County'
                    )
                GROUP BY
                    ss_ticket_number
                    ,ss_customer_sk
        ) dn
        ,customer
    WHERE
        ss_customer_sk = c_customer_sk
        AND cnt BETWEEN 15 AND 20
    ORDER BY
        c_last_name
        ,c_first_name
        ,c_salutation
        ,c_preferred_cust_flag DESC;
--end query 34 using template query34.tpl
--start query 35 using template query35.tpl
-- @private, @projection(demographics)
SELECT
        top 100 ca_state
        ,cd_gender
        ,cd_marital_status
        ,COUNT(*) cnt1
        ,stddev_samp(cd_dep_count)
        ,SUM(cd_dep_count)
        ,MIN(cd_dep_count)
        ,cd_dep_employed_count
        ,COUNT(*) cnt2
        ,stddev_samp(cd_dep_employed_count)
        ,SUM(cd_dep_employed_count)
        ,MIN(cd_dep_employed_count)
        ,cd_dep_college_count
        ,COUNT(*) cnt3
        ,stddev_samp(cd_dep_college_count)
        ,SUM(cd_dep_college_count)
        ,MIN(cd_dep_college_count)
    FROM
        customer c
        ,customer_address ca
        ,customer_demographics
    WHERE
        c.c_current_addr_sk = ca.ca_address_sk
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
                    AND d_year = 2001
                    AND d_qoy < 4
        )
        AND (
            EXISTS (
                SELECT
                        *
                    FROM
                        web_sales
                        ,date_dim
                    WHERE
                        c.c_customer_sk = ws_bill_customer_sk
                        AND ws_sold_date_sk = d_date_sk
                        AND d_year = 2001
                        AND d_qoy < 4
            )
            OR EXISTS (
                SELECT
                        *
                    FROM
                        catalog_sales
                        ,date_dim
                    WHERE
                        c.c_customer_sk = cs_ship_customer_sk
                        AND cs_sold_date_sk = d_date_sk
                        AND d_year = 2001
                        AND d_qoy < 4
            )
        )
    GROUP BY
        ca_state
        ,cd_gender
        ,cd_marital_status
        ,cd_dep_count
        ,cd_dep_employed_count
        ,cd_dep_college_count
    ORDER BY
        ca_state
        ,cd_gender
        ,cd_marital_status
        ,cd_dep_count
        ,cd_dep_employed_count
        ,cd_dep_college_count;
--end query 35 using template query35.tpl
--start query 36 using template query36.tpl
-- public
SELECT
        top 100 SUM(ss_net_profit) / SUM(ss_ext_sales_price) AS gross_margin
        ,i_category
        ,i_class
        ,grouping(i_category) + grouping(i_class) AS lochierarchy
        ,rank() OVER (
            PARTITION BY
                grouping(i_category) + grouping(i_class)
                ,CASE
                    WHEN grouping(i_class) = 0 THEN i_category
                END
            ORDER BY
                SUM(ss_net_profit) / SUM(ss_ext_sales_price) ASC
        ) AS rank_within_parent
    FROM
        store_sales
        ,date_dim d1
        ,item
        ,store
    WHERE
        d1.d_year = 2001
        AND d1.d_date_sk = ss_sold_date_sk
        AND i_item_sk = ss_item_sk
        AND s_store_sk = ss_store_sk
        AND s_state IN (
            'TN'
            ,'TN'
            ,'TN'
            ,'TN'
            ,'TN'
            ,'TN'
            ,'TN'
            ,'TN'
        )
    GROUP BY
        rollup(
            i_category
            ,i_class
        )
    ORDER BY
        lochierarchy DESC
        ,CASE
            WHEN lochierarchy = 0 THEN i_category
        END
        ,rank_within_parent;
--end query 36 using template query36.tpl
--start query 37 using template query37.tpl
--@public
SELECT
        top 100 i_item_id
        ,i_item_desc
        ,i_current_price
    FROM
        item
        ,inventory
        ,date_dim
        ,catalog_sales
    WHERE
        i_current_price BETWEEN 42 AND 42 + 30
        AND inv_item_sk = i_item_sk
        AND d_date_sk = inv_date_sk
        AND d_date BETWEEN cast(
            '2002-01-18' AS DATE
        ) AND (
            cast(
                '2002-01-18' AS DATE
            ) + 60 days
        )
        AND i_manufact_id IN (
            744
            ,691
            ,853
            ,946
        )
        AND inv_quantity_on_hand BETWEEN 100 AND 500
        AND cs_item_sk = i_item_sk
    GROUP BY
        i_item_id
        ,i_item_desc
        ,i_current_price
    ORDER BY
        i_item_id;
--end query 37 using template query37.tpl
--start query 38 using template query38.tpl
-- @private, @projection(name)
SELECT
        top 100 COUNT(*)
    FROM
        (
            SELECT
                    DISTINCT c_last_name
                    ,c_first_name
                    ,d_date
                FROM
                    store_sales
                    ,date_dim
                    ,customer
                WHERE
                    store_sales.ss_sold_date_sk = date_dim.d_date_sk
                    AND store_sales.ss_customer_sk = customer.c_customer_sk
                    AND d_month_seq BETWEEN 1191 AND 1191 + 11
            INTERSECT
            SELECT
                    DISTINCT c_last_name
                    ,c_first_name
                    ,d_date
                FROM
                    catalog_sales
                    ,date_dim
                    ,customer
                WHERE
                    catalog_sales.cs_sold_date_sk = date_dim.d_date_sk
                    AND catalog_sales.cs_bill_customer_sk = customer.c_customer_sk
                    AND d_month_seq BETWEEN 1191 AND 1191 + 11
            INTERSECT
            SELECT
                    DISTINCT c_last_name
                    ,c_first_name
                    ,d_date
                FROM
                    web_sales
                    ,date_dim
                    ,customer
                WHERE
                    web_sales.ws_sold_date_sk = date_dim.d_date_sk
                    AND web_sales.ws_bill_customer_sk = customer.c_customer_sk
                    AND d_month_seq BETWEEN 1191 AND 1191 + 11
        ) hot_cust;
--end query 38 using template query38.tpl
--start query 39 using template query39.tpl
-- @public
WITH inv AS (
        SELECT
                w_warehouse_name
                ,w_warehouse_sk
                ,i_item_sk
                ,d_moy
                ,stdev
                ,mean
                ,CASE mean
                    WHEN 0 THEN null
                    ELSE stdev / mean
                END cov
            FROM
                (
                    SELECT
                            w_warehouse_name
                            ,w_warehouse_sk
                            ,i_item_sk
                            ,d_moy
                            ,stddev_samp(inv_quantity_on_hand) stdev
                            ,AVG(inv_quantity_on_hand) mean
                        FROM
                            inventory
                            ,item
                            ,warehouse
                            ,date_dim
                        WHERE
                            inv_item_sk = i_item_sk
                            AND inv_warehouse_sk = w_warehouse_sk
                            AND inv_date_sk = d_date_sk
                            AND d_year = 1999
                        GROUP BY
                            w_warehouse_name
                            ,w_warehouse_sk
                            ,i_item_sk
                            ,d_moy
                ) foo
            WHERE
                CASE mean
                    WHEN 0 THEN 0
                    ELSE stdev / mean
                END > 1
) SELECT
        inv1.w_warehouse_sk
        ,inv1.i_item_sk
        ,inv1.d_moy
        ,inv1.mean
        ,inv1.cov
        ,inv2.w_warehouse_sk
        ,inv2.i_item_sk
        ,inv2.d_moy
        ,inv2.mean
        ,inv2.cov
    FROM
        inv inv1
        ,inv inv2
    WHERE
        inv1.i_item_sk = inv2.i_item_sk
        AND inv1.w_warehouse_sk = inv2.w_warehouse_sk
        AND inv1.d_moy = 1
        AND inv2.d_moy = 1+1
    ORDER BY
        inv1.w_warehouse_sk
        ,inv1.i_item_sk
        ,inv1.d_moy
        ,inv1.mean
        ,inv1.cov
        ,inv2.d_moy
        ,inv2.mean
        ,inv2.cov;
WITH inv AS (
        SELECT
                w_warehouse_name
                ,w_warehouse_sk
                ,i_item_sk
                ,d_moy
                ,stdev
                ,mean
                ,CASE mean
                    WHEN 0 THEN null
                    ELSE stdev / mean
                END cov
            FROM
                (
                    SELECT
                            w_warehouse_name
                            ,w_warehouse_sk
                            ,i_item_sk
                            ,d_moy
                            ,stddev_samp(inv_quantity_on_hand) stdev
                            ,AVG(inv_quantity_on_hand) mean
                        FROM
                            inventory
                            ,item
                            ,warehouse
                            ,date_dim
                        WHERE
                            inv_item_sk = i_item_sk
                            AND inv_warehouse_sk = w_warehouse_sk
                            AND inv_date_sk = d_date_sk
                            AND d_year = 1999
                        GROUP BY
                            w_warehouse_name
                            ,w_warehouse_sk
                            ,i_item_sk
                            ,d_moy
                ) foo
            WHERE
                CASE mean
                    WHEN 0 THEN 0
                    ELSE stdev / mean
                END > 1
) SELECT
        inv1.w_warehouse_sk
        ,inv1.i_item_sk
        ,inv1.d_moy
        ,inv1.mean
        ,inv1.cov
        ,inv2.w_warehouse_sk
        ,inv2.i_item_sk
        ,inv2.d_moy
        ,inv2.mean
        ,inv2.cov
    FROM
        inv inv1
        ,inv inv2
    WHERE
        inv1.i_item_sk = inv2.i_item_sk
        AND inv1.w_warehouse_sk = inv2.w_warehouse_sk
        AND inv1.d_moy = 1
        AND inv2.d_moy = 1+1
        AND inv1.cov > 1.5
    ORDER BY
        inv1.w_warehouse_sk
        ,inv1.i_item_sk
        ,inv1.d_moy
        ,inv1.mean
        ,inv1.cov
        ,inv2.d_moy
        ,inv2.mean
        ,inv2.cov;
--end query 39 using template query39.tpl
--start query 40 using template query40.tpl
--@public
SELECT
        top 100 w_state
        ,i_item_id
        ,SUM(CASE
            WHEN (
                cast(
                    d_date AS DATE
                ) < cast(
                    '1998-03-08' AS DATE
                )
            ) THEN cs_sales_price - COALESCE(cr_refunded_cash, 0)
            ELSE 0
        END) AS sales_before
        ,SUM(CASE
            WHEN (
                cast(
                    d_date AS DATE
                ) >= cast(
                    '1998-03-08' AS DATE
                )
            ) THEN cs_sales_price - COALESCE(cr_refunded_cash, 0)
            ELSE 0
        END) AS sales_after
    FROM
        catalog_sales
            LEFT OUTER JOIN catalog_returns
                ON (
                    cs_order_number = cr_order_number
                    AND cs_item_sk = cr_item_sk
                )
        ,warehouse
        ,item
        ,date_dim
    WHERE
        i_current_price BETWEEN 0.99 AND 1.49
        AND i_item_sk = cs_item_sk
        AND cs_warehouse_sk = w_warehouse_sk
        AND cs_sold_date_sk = d_date_sk
        AND d_date BETWEEN (
            cast(
                '1998-03-08' AS DATE
            ) - 30 days
        ) AND (
            cast(
                '1998-03-08' AS DATE
            ) + 30 days
        )
    GROUP BY
        w_state
        ,i_item_id
    ORDER BY
        w_state
        ,i_item_id;
--end query 40 using template query40.tpl