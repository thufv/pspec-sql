--start query 1 using template query1.tpl
-- @public
WITH customer_total_return AS (
        SELECT
                sr_customer_sk AS ctr_customer_sk
                ,sr_store_sk AS ctr_store_sk
                ,SUM(SR_REVERSED_CHARGE) AS ctr_total_return
            FROM
                store_returns
                ,date_dim
            WHERE
                sr_returned_date_sk = d_date_sk
                AND d_year = 1998
            GROUP BY
                sr_customer_sk
                ,sr_store_sk
) SELECT
        top 100 c_customer_id
    FROM
        customer_total_return ctr1
        ,store
        ,customer
    WHERE
        ctr1.ctr_total_return > (
            SELECT
                    AVG(ctr_total_return) * 1.2
                FROM
                    customer_total_return ctr2
                WHERE
                    ctr1.ctr_store_sk = ctr2.ctr_store_sk
        )
        AND s_store_sk = ctr1.ctr_store_sk
        AND s_state = 'TN'
        AND ctr1.ctr_customer_sk = c_customer_sk
    ORDER BY
        c_customer_id;
--end query 1 using template query1.tpl

--start query 2 using template query2.tpl
--@public
WITH wscs AS (
        SELECT
                sold_date_sk
                ,sales_price
            FROM
                (
                    SELECT
                            ws_sold_date_sk sold_date_sk
                            ,ws_ext_sales_price sales_price
                        FROM
                            web_sales
                ) x
    UNION ALL
    (
        SELECT
                cs_sold_date_sk sold_date_sk
                ,cs_ext_sales_price sales_price
            FROM
                catalog_sales
    )
)
,wswscs AS (
    SELECT
            d_week_seq
            ,SUM(CASE
                WHEN (d_day_name = 'Sunday') THEN sales_price
                ELSE null
            END) sun_sales
            ,SUM(CASE
                WHEN (d_day_name = 'Monday') THEN sales_price
                ELSE null
            END) mon_sales
            ,SUM(CASE
                WHEN (d_day_name = 'Tuesday') THEN sales_price
                ELSE null
            END) tue_sales
            ,SUM(CASE
                WHEN (d_day_name = 'Wednesday') THEN sales_price
                ELSE null
            END) wed_sales
            ,SUM(CASE
                WHEN (d_day_name = 'Thursday') THEN sales_price
                ELSE null
            END) thu_sales
            ,SUM(CASE
                WHEN (d_day_name = 'Friday') THEN sales_price
                ELSE null
            END) fri_sales
            ,SUM(CASE
                WHEN (d_day_name = 'Saturday') THEN sales_price
                ELSE null
            END) sat_sales
        FROM
            wscs
            ,date_dim
        WHERE
            d_date_sk = sold_date_sk
        GROUP BY
            d_week_seq
) SELECT
        d_week_seq1
        ,ROUND(sun_sales1 / sun_sales2, 2)
        ,ROUND(mon_sales1 / mon_sales2, 2)
        ,ROUND(tue_sales1 / tue_sales2, 2)
        ,ROUND(wed_sales1 / wed_sales2, 2)
        ,ROUND(thu_sales1 / thu_sales2, 2)
        ,ROUND(fri_sales1 / fri_sales2, 2)
        ,ROUND(sat_sales1 / sat_sales2, 2)
    FROM
        (
            SELECT
                    wswscs.d_week_seq d_week_seq1
                    ,sun_sales sun_sales1
                    ,mon_sales mon_sales1
                    ,tue_sales tue_sales1
                    ,wed_sales wed_sales1
                    ,thu_sales thu_sales1
                    ,fri_sales fri_sales1
                    ,sat_sales sat_sales1
                FROM
                    wswscs
                    ,date_dim
                WHERE
                    date_dim.d_week_seq = wswscs.d_week_seq
                    AND d_year = 2001
        ) y
        ,(
            SELECT
                    wswscs.d_week_seq d_week_seq2
                    ,sun_sales sun_sales2
                    ,mon_sales mon_sales2
                    ,tue_sales tue_sales2
                    ,wed_sales wed_sales2
                    ,thu_sales thu_sales2
                    ,fri_sales fri_sales2
                    ,sat_sales sat_sales2
                FROM
                    wswscs
                    ,date_dim
                WHERE
                    date_dim.d_week_seq = wswscs.d_week_seq
                    AND d_year = 2001+1
        ) z
    WHERE
        d_week_seq1 = d_week_seq2 -53
    ORDER BY
        d_week_seq1;
--end query 2 using template query2.tpl

--start query 3 using template query3.tpl
-- @public
SELECT
        top 100 dt.d_year
        ,item.i_brand_id brand_id
        ,item.i_brand brand
        ,SUM(ss_ext_discount_amt) sum_agg
    FROM
        date_dim dt
        ,store_sales
        ,item
    WHERE
        dt.d_date_sk = store_sales.ss_sold_date_sk
        AND store_sales.ss_item_sk = item.i_item_sk
        AND item.i_manufact_id = 783
        AND dt.d_moy = 11
    GROUP BY
        dt.d_year
        ,item.i_brand
        ,item.i_brand_id
    ORDER BY
        dt.d_year
        ,sum_agg DESC
        ,brand_id;

--end query 3 using template query3.tpl

--start query 5 using template query5.tpl
--@public
WITH ssr AS (
        SELECT
                s_store_id
                ,SUM(sales_price) AS sales
                ,SUM(profit) AS profit
                ,SUM(return_amt) AS returns
                ,SUM(net_loss) AS profit_loss
            FROM
                (
                    SELECT
                            ss_store_sk AS store_sk
                            ,ss_sold_date_sk AS date_sk
                            ,ss_ext_sales_price AS sales_price
                            ,ss_net_profit AS profit
                            ,cast(
                                0 AS DECIMAL(7, 2)
                            ) AS return_amt
                            ,cast(
                                0 AS DECIMAL(7, 2)
                            ) AS net_loss
                        FROM
                            store_sales
                    UNION ALL
                    SELECT
                            sr_store_sk AS store_sk
                            ,sr_returned_date_sk AS date_sk
                            ,cast(
                                0 AS DECIMAL(7, 2)
                            ) AS sales_price
                            ,cast(
                                0 AS DECIMAL(7, 2)
                            ) AS profit
                            ,sr_return_amt AS return_amt
                            ,sr_net_loss AS net_loss
                        FROM
                            store_returns
                ) salesreturns
                ,date_dim
                ,store
            WHERE
                date_sk = d_date_sk
                AND d_date BETWEEN cast(
                    '2001-08-21' AS DATE
                ) AND (
                    cast(
                        '2001-08-21' AS DATE
                    ) + 14 days
                )
                AND store_sk = s_store_sk
            GROUP BY
                s_store_id
)
,csr AS (
    SELECT
            cp_catalog_page_id
            ,SUM(sales_price) AS sales
            ,SUM(profit) AS profit
            ,SUM(return_amt) AS returns
            ,SUM(net_loss) AS profit_loss
        FROM
            (
                SELECT
                        cs_catalog_page_sk AS page_sk
                        ,cs_sold_date_sk AS date_sk
                        ,cs_ext_sales_price AS sales_price
                        ,cs_net_profit AS profit
                        ,cast(
                            0 AS DECIMAL(7, 2)
                        ) AS return_amt
                        ,cast(
                            0 AS DECIMAL(7, 2)
                        ) AS net_loss
                    FROM
                        catalog_sales
                UNION ALL
                SELECT
                        cr_catalog_page_sk AS page_sk
                        ,cr_returned_date_sk AS date_sk
                        ,cast(
                            0 AS DECIMAL(7, 2)
                        ) AS sales_price
                        ,cast(
                            0 AS DECIMAL(7, 2)
                        ) AS profit
                        ,cr_return_amount AS return_amt
                        ,cr_net_loss AS net_loss
                    FROM
                        catalog_returns
            ) salesreturns
            ,date_dim
            ,catalog_page
        WHERE
            date_sk = d_date_sk
            AND d_date BETWEEN cast(
                '2001-08-21' AS DATE
            ) AND (
                cast(
                    '2001-08-21' AS DATE
                ) + 14 days
            )
            AND page_sk = cp_catalog_page_sk
        GROUP BY
            cp_catalog_page_id
)
,wsr AS (
    SELECT
            web_site_id
            ,SUM(sales_price) AS sales
            ,SUM(profit) AS profit
            ,SUM(return_amt) AS returns
            ,SUM(net_loss) AS profit_loss
        FROM
            (
                SELECT
                        ws_web_site_sk AS wsr_web_site_sk
                        ,ws_sold_date_sk AS date_sk
                        ,ws_ext_sales_price AS sales_price
                        ,ws_net_profit AS profit
                        ,cast(
                            0 AS DECIMAL(7, 2)
                        ) AS return_amt
                        ,cast(
                            0 AS DECIMAL(7, 2)
                        ) AS net_loss
                    FROM
                        web_sales
                UNION ALL
                SELECT
                        ws_web_site_sk AS wsr_web_site_sk
                        ,wr_returned_date_sk AS date_sk
                        ,cast(
                            0 AS DECIMAL(7, 2)
                        ) AS sales_price
                        ,cast(
                            0 AS DECIMAL(7, 2)
                        ) AS profit
                        ,wr_return_amt AS return_amt
                        ,wr_net_loss AS net_loss
                    FROM
                        web_returns
                            LEFT OUTER JOIN web_sales
                                ON (
                                    wr_item_sk = ws_item_sk
                                    AND wr_order_number = ws_order_number
                                )
            ) salesreturns
    ,date_dim
    ,web_site
WHERE
    date_sk = d_date_sk
    AND d_date BETWEEN cast(
        '2001-08-21' AS DATE
    ) AND (
        cast(
            '2001-08-21' AS DATE
        ) + 14 days
    )
    AND wsr_web_site_sk = web_site_sk
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
                    ,'store' || s_store_id AS id
                    ,sales
                    ,returns
                    ,(profit - profit_loss) AS profit
                FROM
                    ssr
            UNION ALL
            SELECT
                    'catalog channel' AS channel
                    ,'catalog_page' || cp_catalog_page_id AS id
                    ,sales
                    ,returns
                    ,(profit - profit_loss) AS profit
                FROM
                    csr
            UNION ALL
            SELECT
                    'web channel' AS channel
                    ,'web_site' || web_site_id AS id
                    ,sales
                    ,returns
                    ,(profit - profit_loss) AS profit
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
--end query 5 using template query5.tpl

--start query 9 using template query9.tpl
-- @public
SELECT
        CASE
            WHEN (
                SELECT
                        COUNT(*)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 1 AND 20
            ) > 30992 THEN (
                SELECT
                        AVG(ss_ext_sales_price)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 1 AND 20
            )
            ELSE (
                SELECT
                        AVG(ss_net_paid)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 1 AND 20
            )
        END bucket1
        ,CASE
            WHEN (
                SELECT
                        COUNT(*)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 21 AND 40
            ) > 25740 THEN (
                SELECT
                        AVG(ss_ext_sales_price)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 21 AND 40
            )
            ELSE (
                SELECT
                        AVG(ss_net_paid)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 21 AND 40
            )
        END bucket2
        ,CASE
            WHEN (
                SELECT
                        COUNT(*)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 41 AND 60
            ) > 20311 THEN (
                SELECT
                        AVG(ss_ext_sales_price)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 41 AND 60
            )
            ELSE (
                SELECT
                        AVG(ss_net_paid)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 41 AND 60
            )
        END bucket3
        ,CASE
            WHEN (
                SELECT
                        COUNT(*)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 61 AND 80
            ) > 21635 THEN (
                SELECT
                        AVG(ss_ext_sales_price)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 61 AND 80
            )
            ELSE (
                SELECT
                        AVG(ss_net_paid)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 61 AND 80
            )
        END bucket4
        ,CASE
            WHEN (
                SELECT
                        COUNT(*)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 81 AND 100
            ) > 20532 THEN (
                SELECT
                        AVG(ss_ext_sales_price)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 81 AND 100
            )
            ELSE (
                SELECT
                        AVG(ss_net_paid)
                    FROM
                        store_sales
                    WHERE
                        ss_quantity BETWEEN 81 AND 100
            )
        END bucket5
    FROM
        reason
    WHERE
        r_reason_sk = 1;
--end query 9 using template query9.tpl

--start query 11 using template query11.tpl
-- @public
WITH year_total AS (
        SELECT
                c_customer_id customer_id
                ,c_first_name customer_first_name
                ,c_last_name customer_last_name
                ,c_preferred_cust_flag customer_preferred_cust_flag
                ,c_birth_country customer_birth_country
                ,c_login customer_login
                ,c_email_address customer_email_address
                ,d_year dyear
                ,SUM(ss_ext_list_price - ss_ext_discount_amt) year_total
                ,'s' sale_type
            FROM
                customer
                ,store_sales
                ,date_dim
            WHERE
                c_customer_sk = ss_customer_sk
                AND ss_sold_date_sk = d_date_sk
            GROUP BY
                c_customer_id
                ,c_first_name
                ,c_last_name
                ,d_year
                ,c_preferred_cust_flag
                ,c_birth_country
                ,c_login
                ,c_email_address
                ,d_year
    UNION ALL
    SELECT
            c_customer_id customer_id
            ,c_first_name customer_first_name
            ,c_last_name customer_last_name
            ,c_preferred_cust_flag customer_preferred_cust_flag
            ,c_birth_country customer_birth_country
            ,c_login customer_login
            ,c_email_address customer_email_address
            ,d_year dyear
            ,SUM(ws_ext_list_price - ws_ext_discount_amt) year_total
            ,'w' sale_type
        FROM
            customer
            ,web_sales
            ,date_dim
        WHERE
            c_customer_sk = ws_bill_customer_sk
            AND ws_sold_date_sk = d_date_sk
        GROUP BY
            c_customer_id
            ,c_first_name
            ,c_last_name
            ,c_preferred_cust_flag
            ,c_birth_country
            ,c_login
            ,c_email_address
            ,d_year
) SELECT
        top 100 t_s_secyear.customer_login
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
        AND t_s_firstyear.dyear = 2001
        AND t_s_secyear.dyear = 2001+1
        AND t_w_firstyear.dyear = 2001
        AND t_w_secyear.dyear = 2001+1
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
        t_s_secyear.customer_login;
--end query 11 using template query11.tpl

--start query 12 using template query12.tpl
-- @public
SELECT
        top 100 i_item_desc
        ,i_category
        ,i_class
        ,i_current_price
        ,SUM(ws_ext_sales_price) AS itemrevenue
        ,SUM(ws_ext_sales_price) * 100 / SUM(SUM(ws_ext_sales_price)) OVER (
            PARTITION BY
                i_class
        ) AS revenueratio
    FROM
        web_sales
        ,item
        ,date_dim
    WHERE
        ws_item_sk = i_item_sk
        AND i_category IN (
            'Children'
            ,'Sports'
            ,'Music'
        )
        AND ws_sold_date_sk = d_date_sk
        AND d_date BETWEEN cast(
            '2002-04-01' AS DATE
        ) AND (
            cast(
                '2002-04-01' AS DATE
            ) + 30 days
        )
    GROUP BY
        i_item_id
        ,i_item_desc
        ,i_category
        ,i_class
        ,i_current_price
    ORDER BY
        i_category
        ,i_class
        ,i_item_id
        ,i_item_desc
        ,revenueratio;
--end query 12 using template query12.tpl

--start query 17 using template query17.tpl
-- @public
SELECT
        top 100 i_item_id
        ,i_item_desc
        ,s_state
        ,COUNT(ss_quantity) AS store_sales_quantitycount
        ,AVG(ss_quantity) AS store_sales_quantityave
        ,stddev_samp(ss_quantity) AS store_sales_quantitystdev
        ,stddev_samp(ss_quantity) / AVG(ss_quantity) AS store_sales_quantitycov
        ,COUNT(sr_return_quantity) as_store_returns_quantitycount
        ,AVG(sr_return_quantity) as_store_returns_quantityave
        ,stddev_samp(sr_return_quantity) as_store_returns_quantitystdev
        ,stddev_samp(sr_return_quantity) / AVG(sr_return_quantity) AS store_returns_quantitycov
        ,COUNT(cs_quantity) AS catalog_sales_quantitycount
        ,AVG(cs_quantity) AS catalog_sales_quantityave
        ,stddev_samp(cs_quantity) / AVG(cs_quantity) AS catalog_sales_quantitystdev
        ,stddev_samp(cs_quantity) / AVG(cs_quantity) AS catalog_sales_quantitycov
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
        d1.d_quarter_name = '2001Q1'
        AND d1.d_date_sk = ss_sold_date_sk
        AND i_item_sk = ss_item_sk
        AND s_store_sk = ss_store_sk
        AND ss_customer_sk = sr_customer_sk
        AND ss_item_sk = sr_item_sk
        AND ss_ticket_number = sr_ticket_number
        AND sr_returned_date_sk = d2.d_date_sk
        AND d2.d_quarter_name IN (
            '2001Q1'
            ,'2001Q2'
            ,'2001Q3'
        )
        AND sr_customer_sk = cs_bill_customer_sk
        AND sr_item_sk = cs_item_sk
        AND cs_sold_date_sk = d3.d_date_sk
        AND d3.d_quarter_name IN (
            '2001Q1'
            ,'2001Q2'
            ,'2001Q3'
        )
    GROUP BY
        i_item_id
        ,i_item_desc
        ,s_state
    ORDER BY
        i_item_id
        ,i_item_desc
        ,s_state;
--end query 17 using template query17.tpl

--start query 19 using template query19.tpl
-- products bought by out of zip code customers for a given year, month, and manager.
-- @public
SELECT
        top 100 i_brand_id brand_id
        ,i_brand brand
        ,i_manufact_id
        ,i_manufact
        ,SUM(ss_ext_sales_price) ext_price
    FROM
        date_dim
        ,store_sales
        ,item
        ,customer
        ,customer_address
        ,store
    WHERE
        d_date_sk = ss_sold_date_sk
        AND ss_item_sk = i_item_sk
        AND i_manager_id = 91
        AND d_moy = 12
        AND d_year = 2002
        AND ss_customer_sk = c_customer_sk
        AND c_current_addr_sk = ca_address_sk
        AND SUBSTR(ca_zip, 1, 5) <> SUBSTR(s_zip, 1, 5)
        AND ss_store_sk = s_store_sk
    GROUP BY
        i_brand
        ,i_brand_id
        ,i_manufact_id
        ,i_manufact
    ORDER BY
        ext_price DESC
        ,i_brand
        ,i_brand_id
        ,i_manufact_id
        ,i_manufact;
--end query 19 using template query19.tpl

--start query 20 using template query20.tpl
-- @public
SELECT
        top 100 i_item_desc
        ,i_category
        ,i_class
        ,i_current_price
        ,SUM(cs_ext_sales_price) AS itemrevenue
        ,SUM(cs_ext_sales_price) * 100 / SUM(SUM(cs_ext_sales_price)) OVER (
            PARTITION BY
                i_class
        ) AS revenueratio
    FROM
        catalog_sales
        ,item
        ,date_dim
    WHERE
        cs_item_sk = i_item_sk
        AND i_category IN (
            'Shoes'
            ,'Women'
            ,'Music'
        )
        AND cs_sold_date_sk = d_date_sk
        AND d_date BETWEEN cast(
            '1999-06-03' AS DATE
        ) AND (
            cast(
                '1999-06-03' AS DATE
            ) + 30 days
        )
    GROUP BY
        i_item_id
        ,i_item_desc
        ,i_category
        ,i_class
        ,i_current_price
    ORDER BY
        i_category
        ,i_class
        ,i_item_id
        ,i_item_desc
        ,revenueratio;
--end query 20 using template query20.tpl;

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

--start query 36 using template query36.tpl
-- @public
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

--end query 40 using template query40.tpl--start query 41 using template query41.tpl
-- @public
SELECT
        top 100 DISTINCT (i_product_name)
    FROM
        item i1
    WHERE
        i_manufact_id BETWEEN 770 AND 770+40
        AND (
            SELECT
                    COUNT(*) AS item_cnt
                FROM
                    item
                WHERE
                    (
                        i_manufact = i1.i_manufact
                        AND (
                            (
                                i_category = 'Women'
                                AND (
                                    i_color = 'olive'
                                    OR i_color = 'grey'
                                )
                                AND (
                                    i_units = 'Bundle'
                                    OR i_units = 'Cup'
                                )
                                AND (
                                    i_size = 'petite'
                                    OR i_size = 'small'
                                )
                            )
                            OR (
                                i_category = 'Women'
                                AND (
                                    i_color = 'hot'
                                    OR i_color = 'thistle'
                                )
                                AND (
                                    i_units = 'Box'
                                    OR i_units = 'Each'
                                )
                                AND (
                                    i_size = 'large'
                                    OR i_size = 'medium'
                                )
                            )
                            OR (
                                i_category = 'Men'
                                AND (
                                    i_color = 'chiffon'
                                    OR i_color = 'yellow'
                                )
                                AND (
                                    i_units = 'Carton'
                                    OR i_units = 'Dozen'
                                )
                                AND (
                                    i_size = 'N/A'
                                    OR i_size = 'extra large'
                                )
                            )
                            OR (
                                i_category = 'Men'
                                AND (
                                    i_color = 'bisque'
                                    OR i_color = 'turquoise'
                                )
                                AND (
                                    i_units = 'Case'
                                    OR i_units = 'Tsp'
                                )
                                AND (
                                    i_size = 'petite'
                                    OR i_size = 'small'
                                )
                            )
                        )
                    )
                    OR (
                        i_manufact = i1.i_manufact
                        AND (
                            (
                                i_category = 'Women'
                                AND (
                                    i_color = 'chocolate'
                                    OR i_color = 'lemon'
                                )
                                AND (
                                    i_units = 'Unknown'
                                    OR i_units = 'Oz'
                                )
                                AND (
                                    i_size = 'petite'
                                    OR i_size = 'small'
                                )
                            )
                            OR (
                                i_category = 'Women'
                                AND (
                                    i_color = 'light'
                                    OR i_color = 'ivory'
                                )
                                AND (
                                    i_units = 'Ounce'
                                    OR i_units = 'Ton'
                                )
                                AND (
                                    i_size = 'large'
                                    OR i_size = 'medium'
                                )
                            )
                            OR (
                                i_category = 'Men'
                                AND (
                                    i_color = 'rose'
                                    OR i_color = 'sandy'
                                )
                                AND (
                                    i_units = 'Pound'
                                    OR i_units = 'Lb'
                                )
                                AND (
                                    i_size = 'N/A'
                                    OR i_size = 'extra large'
                                )
                            )
                            OR (
                                i_category = 'Men'
                                AND (
                                    i_color = 'wheat'
                                    OR i_color = 'burnished'
                                )
                                AND (
                                    i_units = 'Dram'
                                    OR i_units = 'Pallet'
                                )
                                AND (
                                    i_size = 'petite'
                                    OR i_size = 'small'
                                )
                            )
                        )
                    )
        ) > 0
    ORDER BY
        i_product_name;
--end query 41 using template query41.tpl

--start query 42 using template query42.tpl
--@public
SELECT
        top 100 dt.d_year
        ,item.i_category_id
        ,item.i_category
        ,SUM(ss_ext_sales_price)
    FROM
        date_dim dt
        ,store_sales
        ,item
    WHERE
        dt.d_date_sk = store_sales.ss_sold_date_sk
        AND store_sales.ss_item_sk = item.i_item_sk
        AND item.i_manager_id = 1
        AND dt.d_moy = 11
        AND dt.d_year = 2001
    GROUP BY
        dt.d_year
        ,item.i_category_id
        ,item.i_category
    ORDER BY
        SUM(ss_ext_sales_price) DESC
        ,dt.d_year
        ,item.i_category_id
        ,item.i_category;
--end query 42 using template query42.tpl

--start query 43 using template query43.tpl
--@public
SELECT
        top 100 s_store_name
        ,s_store_id
        ,SUM(CASE
            WHEN (d_day_name = 'Sunday') THEN ss_sales_price
            ELSE null
        END) sun_sales
        ,SUM(CASE
            WHEN (d_day_name = 'Monday') THEN ss_sales_price
            ELSE null
        END) mon_sales
        ,SUM(CASE
            WHEN (d_day_name = 'Tuesday') THEN ss_sales_price
            ELSE null
        END) tue_sales
        ,SUM(CASE
            WHEN (d_day_name = 'Wednesday') THEN ss_sales_price
            ELSE null
        END) wed_sales
        ,SUM(CASE
            WHEN (d_day_name = 'Thursday') THEN ss_sales_price
            ELSE null
        END) thu_sales
        ,SUM(CASE
            WHEN (d_day_name = 'Friday') THEN ss_sales_price
            ELSE null
        END) fri_sales
        ,SUM(CASE
            WHEN (d_day_name = 'Saturday') THEN ss_sales_price
            ELSE null
        END) sat_sales
    FROM
        date_dim
        ,store_sales
        ,store
    WHERE
        d_date_sk = ss_sold_date_sk
        AND s_store_sk = ss_store_sk
        AND s_gmt_offset = -5
        AND d_year = 2000
    GROUP BY
        s_store_name
        ,s_store_id
    ORDER BY
        s_store_name
        ,s_store_id
        ,sun_sales
        ,mon_sales
        ,tue_sales
        ,wed_sales
        ,thu_sales
        ,fri_sales
        ,sat_sales;
--end query 43 using template query43.tpl

--start query 44 using template query44.tpl
-- list best nd worst performing products measured by net profit.
-- @public
SELECT
        top 100 asceding.rnk
        ,i1.i_product_name best_performing
        ,i2.i_product_name worst_performing
    FROM
        (
            SELECT
                    *
                FROM
                    (
                        SELECT
                                item_sk
                                ,rank() OVER (
                                ORDER BY
                                    rank_col ASC
                                ) rnk
                            FROM
                                (
                                    SELECT
                                            ss_item_sk item_sk
                                            ,AVG(ss_net_profit) rank_col
                                        FROM
                                            store_sales ss1
                                        WHERE
                                            ss_store_sk = 6
                                        GROUP BY
                                            ss_item_sk
                                        HAVING
                                            AVG(ss_net_profit) > 0.9 * (
                                                SELECT
                                                        AVG(ss_net_profit) rank_col
                                                    FROM
                                                        store_sales
                                                    WHERE
                                                        ss_store_sk = 6
                                                        AND ss_hdemo_sk IS null
                                                    GROUP BY
                                                        ss_store_sk
                                            )
                                ) V1
                    ) V11
                WHERE
                    rnk < 11
        ) asceding
        ,(
            SELECT
                    *
                FROM
                    (
                        SELECT
                                item_sk
                                ,rank() OVER (
                                ORDER BY
                                    rank_col DESC
                                ) rnk
                            FROM
                                (
                                    SELECT
                                            ss_item_sk item_sk
                                            ,AVG(ss_net_profit) rank_col
                                        FROM
                                            store_sales ss1
                                        WHERE
                                            ss_store_sk = 6
                                        GROUP BY
                                            ss_item_sk
                                        HAVING
                                            AVG(ss_net_profit) > 0.9 * (
                                                SELECT
                                                        AVG(ss_net_profit) rank_col
                                                    FROM
                                                        store_sales
                                                    WHERE
                                                        ss_store_sk = 6
                                                        AND ss_hdemo_sk IS null
                                                    GROUP BY
                                                        ss_store_sk
                                            )
                                ) V2
                    ) V21
                WHERE
                    rnk < 11
        ) descending
        ,item i1
        ,item i2
    WHERE
        asceding.rnk = descending.rnk
        AND i1.i_item_sk = asceding.item_sk
        AND i2.i_item_sk = descending.item_sk
    ORDER BY
        asceding.rnk;
--end query 44 using template query44.tpl

--start query 47 using template query47.tpl
--@public
WITH v1 AS (
        SELECT
                i_category
                ,i_brand
                ,s_store_name
                ,s_company_name
                ,d_year
                ,d_moy
                ,SUM(ss_sales_price) sum_sales
                ,AVG(SUM(ss_sales_price)) OVER (
                    PARTITION BY
                        i_category
                        ,i_brand
                        ,s_store_name
                        ,s_company_name
                        ,d_year
                ) avg_monthly_sales
                ,rank() OVER (
                    PARTITION BY
                        i_category
                        ,i_brand
                        ,s_store_name
                        ,s_company_name
                    ORDER BY
                        d_year
                        ,d_moy
                ) rn
            FROM
                item
                ,store_sales
                ,date_dim
                ,store
            WHERE
                ss_item_sk = i_item_sk
                AND ss_sold_date_sk = d_date_sk
                AND ss_store_sk = s_store_sk
                AND (
                    d_year = 2000
                    OR (
                        d_year = 2000-1
                        AND d_moy = 12
                    )
                    OR (
                        d_year = 2000+1
                        AND d_moy = 1
                    )
                )
            GROUP BY
                i_category
                ,i_brand
                ,s_store_name
                ,s_company_name
                ,d_year
                ,d_moy
)
,v2 AS (
    SELECT
            v1.s_store_name
            ,v1.s_company_name
            ,v1.d_year
            ,v1.avg_monthly_sales
            ,v1.sum_sales
            ,v1_lag.sum_sales psum
            ,v1_lead.sum_sales nsum
        FROM
            v1
            ,v1 v1_lag
            ,v1 v1_lead
        WHERE
            v1.i_category = v1_lag.i_category
            AND v1.i_category = v1_lead.i_category
            AND v1.i_brand = v1_lag.i_brand
            AND v1.i_brand = v1_lead.i_brand
            AND v1.s_store_name = v1_lag.s_store_name
            AND v1.s_store_name = v1_lead.s_store_name
            AND v1.s_company_name = v1_lag.s_company_name
            AND v1.s_company_name = v1_lead.s_company_name
            AND v1.rn = v1_lag.rn + 1
            AND v1.rn = v1_lead.rn - 1
) SELECT
        top 100 *
    FROM
        v2
    WHERE
        d_year = 2000
        AND avg_monthly_sales > 0
        AND CASE
            WHEN avg_monthly_sales > 0 THEN ABS(sum_sales - avg_monthly_sales) / avg_monthly_sales
            ELSE null
        END > 0.1
    ORDER BY
        sum_sales - avg_monthly_sales
        ,3;
--end query 47 using template query47.tpl

--start query 50 using template query50.tpl
--@public
SELECT
        top 100 s_store_name
        ,s_company_id
        ,s_street_number
        ,s_street_name
        ,s_street_type
        ,s_suite_number
        ,s_city
        ,s_county
        ,s_state
        ,s_zip
        ,SUM(CASE
            WHEN (sr_returned_date_sk - ss_sold_date_sk <= 30) THEN 1
            ELSE 0
        END) AS "30 days"
        ,SUM(CASE
            WHEN (sr_returned_date_sk - ss_sold_date_sk > 30)
            AND (sr_returned_date_sk - ss_sold_date_sk <= 60) THEN 1
            ELSE 0
        END) AS "31-60 days"
        ,SUM(CASE
            WHEN (sr_returned_date_sk - ss_sold_date_sk > 60)
            AND (sr_returned_date_sk - ss_sold_date_sk <= 90) THEN 1
            ELSE 0
        END) AS "61-90 days"
        ,SUM(CASE
            WHEN (sr_returned_date_sk - ss_sold_date_sk > 90)
            AND (sr_returned_date_sk - ss_sold_date_sk <= 120) THEN 1
            ELSE 0
        END) AS "91-120 days"
        ,SUM(CASE
            WHEN (sr_returned_date_sk - ss_sold_date_sk > 120) THEN 1
            ELSE 0
        END) AS ">120 days"
    FROM
        store_sales
        ,store_returns
        ,store
        ,date_dim d1
        ,date_dim d2
    WHERE
        d2.d_year = 2000
        AND d2.d_moy = 8
        AND ss_ticket_number = sr_ticket_number
        AND ss_item_sk = sr_item_sk
        AND ss_sold_date_sk = d1.d_date_sk
        AND sr_returned_date_sk = d2.d_date_sk
        AND ss_customer_sk = sr_customer_sk
        AND ss_store_sk = s_store_sk
    GROUP BY
        s_store_name
        ,s_company_id
        ,s_street_number
        ,s_street_name
        ,s_street_type
        ,s_suite_number
        ,s_city
        ,s_county
        ,s_state
        ,s_zip
    ORDER BY
        s_store_name
        ,s_company_id
        ,s_street_number
        ,s_street_name
        ,s_street_type
        ,s_suite_number
        ,s_city
        ,s_county
        ,s_state
        ,s_zip;
--end query 50 using template query50.tpl

--start query 51 using template query51.tpl
-- @public
WITH web_v1 AS (
        SELECT
                ws_item_sk item_sk
                ,d_date
                ,SUM(SUM(ws_sales_price)) OVER (
                    PARTITION BY
                        ws_item_sk
                    ORDER BY
                        d_date ROWS BETWEEN unbounded preceding AND CURRENT ROW
                ) cume_sales
            FROM
                web_sales
                ,date_dim
            WHERE
                ws_sold_date_sk = d_date_sk
                AND d_month_seq BETWEEN 1200 AND 1200+11
                AND ws_item_sk IS NOT NULL
            GROUP BY
                ws_item_sk
                ,d_date
)
,store_v1 AS (
    SELECT
            ss_item_sk item_sk
            ,d_date
            ,SUM(SUM(ss_sales_price)) OVER (
                PARTITION BY
                    ss_item_sk
                ORDER BY
                    d_date ROWS BETWEEN unbounded preceding AND CURRENT ROW
            ) cume_sales
        FROM
            store_sales
            ,date_dim
        WHERE
            ss_sold_date_sk = d_date_sk
            AND d_month_seq BETWEEN 1200 AND 1200+11
            AND ss_item_sk IS NOT NULL
        GROUP BY
            ss_item_sk
            ,d_date
) SELECT
        top 100 *
    FROM
        (
            SELECT
                    item_sk
                    ,d_date
                    ,web_sales
                    ,store_sales
                    ,MAX(web_sales) OVER (
                        PARTITION BY
                            item_sk
                        ORDER BY
                            d_date ROWS BETWEEN unbounded preceding AND CURRENT ROW
                    ) web_cumulative
                    ,MAX(store_sales) OVER (
                        PARTITION BY
                            item_sk
                        ORDER BY
                            d_date ROWS BETWEEN unbounded preceding AND CURRENT ROW
                    ) store_cumulative
                FROM
                    (
                        SELECT
                                CASE
                                    WHEN web.item_sk IS NOT null THEN web.item_sk
                                    ELSE store.item_sk
                                END item_sk
                                ,CASE
                                    WHEN web.d_date IS NOT null THEN web.d_date
                                    ELSE store.d_date
                                END d_date
                                ,web.cume_sales web_sales
                                ,store.cume_sales store_sales
                            FROM
                                web_v1 web full OUTER
                                    JOIN store_v1 store
                                        ON (
                                            web.item_sk = store.item_sk
                                            AND web.d_date = store.d_date
                                        )
                    ) x
        ) y
WHERE
web_cumulative > store_cumulative
ORDER BY
item_sk
,d_date;
--end query 51 using template query51.tpl

--start query 52 using template query52.tpl
--@public
SELECT
        top 100 dt.d_year
        ,item.i_brand_id brand_id
        ,item.i_brand brand
        ,SUM(ss_ext_sales_price) ext_price
    FROM
        date_dim dt
        ,store_sales
        ,item
    WHERE
        dt.d_date_sk = store_sales.ss_sold_date_sk
        AND store_sales.ss_item_sk = item.i_item_sk
        AND item.i_manager_id = 1
        AND dt.d_moy = 12
        AND dt.d_year = 2000
    GROUP BY
        dt.d_year
        ,item.i_brand
        ,item.i_brand_id
    ORDER BY
        dt.d_year
        ,ext_price DESC
        ,brand_id;
--end query 52 using template query52.tpl

--start query 53 using template query53.tpl
-- @public
SELECT
        top 100 *
    FROM
        (
            SELECT
                    i_manufact_id
                    ,SUM(ss_sales_price) sum_sales
                    ,AVG(SUM(ss_sales_price)) OVER (
                        PARTITION BY
                            i_manufact_id
                    ) avg_quarterly_sales
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
                        1195
                        ,1195+1
                        ,1195+2
                        ,1195+3
                        ,1195+4
                        ,1195+5
                        ,1195+6
                        ,1195+7
                        ,1195+8
                        ,1195+9
                        ,1195+10
                        ,1195+11
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
                                ,'reference'
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
                    i_manufact_id
                    ,d_qoy
        ) tmp1
    WHERE
        CASE
            WHEN avg_quarterly_sales > 0 THEN ABS(sum_sales - avg_quarterly_sales) / avg_quarterly_sales
            ELSE null
        END > 0.1
    ORDER BY
        avg_quarterly_sales
        ,sum_sales
        ,i_manufact_id;
--end query 53 using template query53.tpl

--start query 55 using template query55.tpl
--@public
SELECT
        top 100 i_brand_id brand_id
        ,i_brand brand
        ,SUM(ss_ext_sales_price) ext_price
    FROM
        date_dim
        ,store_sales
        ,item
    WHERE
        d_date_sk = ss_sold_date_sk
        AND ss_item_sk = i_item_sk
        AND i_manager_id = 40
        AND d_moy = 12
        AND d_year = 2001
    GROUP BY
        i_brand
        ,i_brand_id
    ORDER BY
        ext_price DESC
        ,i_brand_id;
--end query 55 using template query55.tpl

--start query 57 using template query57.tpl
--@public
WITH v1 AS (
        SELECT
                i_category
                ,i_brand
                ,cc_name
                ,d_year
                ,d_moy
                ,SUM(cs_sales_price) sum_sales
                ,AVG(SUM(cs_sales_price)) OVER (
                    PARTITION BY
                        i_category
                        ,i_brand
                        ,cc_name
                        ,d_year
                ) avg_monthly_sales
                ,rank() OVER (
                    PARTITION BY
                        i_category
                        ,i_brand
                        ,cc_name
                    ORDER BY
                        d_year
                        ,d_moy
                ) rn
            FROM
                item
                ,catalog_sales
                ,date_dim
                ,call_center
            WHERE
                cs_item_sk = i_item_sk
                AND cs_sold_date_sk = d_date_sk
                AND cc_call_center_sk = cs_call_center_sk
                AND (
                    d_year = 1999
                    OR (
                        d_year = 1999-1
                        AND d_moy = 12
                    )
                    OR (
                        d_year = 1999+1
                        AND d_moy = 1
                    )
                )
            GROUP BY
                i_category
                ,i_brand
                ,cc_name
                ,d_year
                ,d_moy
)
,v2 AS (
    SELECT
            v1.i_category
            ,v1.i_brand
            ,v1.d_year
            ,v1.d_moy
            ,v1.avg_monthly_sales
            ,v1.sum_sales
            ,v1_lag.sum_sales psum
            ,v1_lead.sum_sales nsum
        FROM
            v1
            ,v1 v1_lag
            ,v1 v1_lead
        WHERE
            v1.i_category = v1_lag.i_category
            AND v1.i_category = v1_lead.i_category
            AND v1.i_brand = v1_lag.i_brand
            AND v1.i_brand = v1_lead.i_brand
            AND v1.cc_name = v1_lag.cc_name
            AND v1.cc_name = v1_lead.cc_name
            AND v1.rn = v1_lag.rn + 1
            AND v1.rn = v1_lead.rn - 1
) SELECT
        top 100 *
    FROM
        v2
    WHERE
        d_year = 1999
        AND avg_monthly_sales > 0
        AND CASE
            WHEN avg_monthly_sales > 0 THEN ABS(sum_sales - avg_monthly_sales) / avg_monthly_sales
            ELSE null
        END > 0.1
    ORDER BY
        sum_sales - avg_monthly_sales
        ,3;
--end query 57 using template query57.tpl

--start query 58 using template query58.tpl
-- @public
WITH ss_items AS (
        SELECT
                i_item_id item_id
                ,SUM(ss_ext_sales_price) ss_item_rev
            FROM
                store_sales
                ,item
                ,date_dim
            WHERE
                ss_item_sk = i_item_sk
                AND d_date IN (
                    SELECT
                            d_date
                        FROM
                            date_dim
                        WHERE
                            d_week_seq = (
                                SELECT
                                        d_week_seq
                                    FROM
                                        date_dim
                                    WHERE
                                        d_date = '2002-03-09'
                            )
                )
                AND ss_sold_date_sk = d_date_sk
            GROUP BY
                i_item_id
)
,cs_items AS (
    SELECT
            i_item_id item_id
            ,SUM(cs_ext_sales_price) cs_item_rev
        FROM
            catalog_sales
            ,item
            ,date_dim
        WHERE
            cs_item_sk = i_item_sk
            AND d_date IN (
                SELECT
                        d_date
                    FROM
                        date_dim
                    WHERE
                        d_week_seq = (
                            SELECT
                                    d_week_seq
                                FROM
                                    date_dim
                                WHERE
                                    d_date = '2002-03-09'
                        )
            )
            AND cs_sold_date_sk = d_date_sk
        GROUP BY
            i_item_id
)
,ws_items AS (
    SELECT
            i_item_id item_id
            ,SUM(ws_ext_sales_price) ws_item_rev
        FROM
            web_sales
            ,item
            ,date_dim
        WHERE
            ws_item_sk = i_item_sk
            AND d_date IN (
                SELECT
                        d_date
                    FROM
                        date_dim
                    WHERE
                        d_week_seq = (
                            SELECT
                                    d_week_seq
                                FROM
                                    date_dim
                                WHERE
                                    d_date = '2002-03-09'
                        )
            )
            AND ws_sold_date_sk = d_date_sk
        GROUP BY
            i_item_id
) SELECT
        top 100 ss_items.item_id
        ,ss_item_rev
        ,ss_item_rev / (ss_item_rev + cs_item_rev + ws_item_rev) / 3 * 100 ss_dev
        ,cs_item_rev
        ,cs_item_rev / (ss_item_rev + cs_item_rev + ws_item_rev) / 3 * 100 cs_dev
        ,ws_item_rev
        ,ws_item_rev / (ss_item_rev + cs_item_rev + ws_item_rev) / 3 * 100 ws_dev
        ,(ss_item_rev + cs_item_rev + ws_item_rev) / 3 average
    FROM
        ss_items
        ,cs_items
        ,ws_items
    WHERE
        ss_items.item_id = cs_items.item_id
        AND ss_items.item_id = ws_items.item_id
        AND ss_item_rev BETWEEN 0.9 * cs_item_rev AND 1.1 * cs_item_rev
        AND ss_item_rev BETWEEN 0.9 * ws_item_rev AND 1.1 * ws_item_rev
        AND cs_item_rev BETWEEN 0.9 * ss_item_rev AND 1.1 * ss_item_rev
        AND cs_item_rev BETWEEN 0.9 * ws_item_rev AND 1.1 * ws_item_rev
        AND ws_item_rev BETWEEN 0.9 * ss_item_rev AND 1.1 * ss_item_rev
        AND ws_item_rev BETWEEN 0.9 * cs_item_rev AND 1.1 * cs_item_rev
    ORDER BY
        item_id
        ,ss_item_rev;
--end query 58 using template query58.tpl

--start query 59 using template query59.tpl
--@public
WITH wss AS (
        SELECT
                d_week_seq
                ,ss_store_sk
                ,SUM(CASE
                    WHEN (d_day_name = 'Sunday') THEN ss_sales_price
                    ELSE null
                END) sun_sales
                ,SUM(CASE
                    WHEN (d_day_name = 'Monday') THEN ss_sales_price
                    ELSE null
                END) mon_sales
                ,SUM(CASE
                    WHEN (d_day_name = 'Tuesday') THEN ss_sales_price
                    ELSE null
                END) tue_sales
                ,SUM(CASE
                    WHEN (d_day_name = 'Wednesday') THEN ss_sales_price
                    ELSE null
                END) wed_sales
                ,SUM(CASE
                    WHEN (d_day_name = 'Thursday') THEN ss_sales_price
                    ELSE null
                END) thu_sales
                ,SUM(CASE
                    WHEN (d_day_name = 'Friday') THEN ss_sales_price
                    ELSE null
                END) fri_sales
                ,SUM(CASE
                    WHEN (d_day_name = 'Saturday') THEN ss_sales_price
                    ELSE null
                END) sat_sales
            FROM
                store_sales
                ,date_dim
            WHERE
                d_date_sk = ss_sold_date_sk
            GROUP BY
                d_week_seq
                ,ss_store_sk
) SELECT
        top 100 s_store_name1
        ,s_store_id1
        ,d_week_seq1
        ,sun_sales1 / sun_sales2
        ,mon_sales1 / mon_sales2
        ,tue_sales1 / tue_sales2
        ,wed_sales1 / wed_sales2
        ,thu_sales1 / thu_sales2
        ,fri_sales1 / fri_sales2
        ,sat_sales1 / sat_sales2
    FROM
        (
            SELECT
                    s_store_name s_store_name1
                    ,wss.d_week_seq d_week_seq1
                    ,s_store_id s_store_id1
                    ,sun_sales sun_sales1
                    ,mon_sales mon_sales1
                    ,tue_sales tue_sales1
                    ,wed_sales wed_sales1
                    ,thu_sales thu_sales1
                    ,fri_sales fri_sales1
                    ,sat_sales sat_sales1
                FROM
                    wss
                    ,store
                    ,date_dim d
                WHERE
                    d.d_week_seq = wss.d_week_seq
                    AND ss_store_sk = s_store_sk
                    AND d_month_seq BETWEEN 1184 AND 1184 + 11
        ) y
        ,(
            SELECT
                    s_store_name s_store_name2
                    ,wss.d_week_seq d_week_seq2
                    ,s_store_id s_store_id2
                    ,sun_sales sun_sales2
                    ,mon_sales mon_sales2
                    ,tue_sales tue_sales2
                    ,wed_sales wed_sales2
                    ,thu_sales thu_sales2
                    ,fri_sales fri_sales2
                    ,sat_sales sat_sales2
                FROM
                    wss
                    ,store
                    ,date_dim d
                WHERE
                    d.d_week_seq = wss.d_week_seq
                    AND ss_store_sk = s_store_sk
                    AND d_month_seq BETWEEN 1184+ 12 AND 1184 + 23
        ) x
    WHERE
        s_store_id1 = s_store_id2
        AND d_week_seq1 = d_week_seq2 -52
    ORDER BY
        s_store_name1
        ,s_store_id1
        ,d_week_seq1;
--end query 59 using template query59.tpl

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

--start query 82 using template query82.tpl
-- @public
SELECT
        top 100 i_item_id
        ,i_item_desc
        ,i_current_price
    FROM
        item
        ,inventory
        ,date_dim
        ,store_sales
    WHERE
        i_current_price BETWEEN 38 AND 38+30
        AND inv_item_sk = i_item_sk
        AND d_date_sk = inv_date_sk
        AND d_date BETWEEN cast(
            '1998-01-06' AS DATE
        ) AND (
            cast(
                '1998-01-06' AS DATE
            ) + 60 days
        )
        AND i_manufact_id IN (
            198
            ,999
            ,168
            ,196
        )
        AND inv_quantity_on_hand BETWEEN 100 AND 500
        AND ss_item_sk = i_item_sk
    GROUP BY
        i_item_id
        ,i_item_desc
        ,i_current_price
    ORDER BY
        i_item_id;
--end query 82 using template query82.tpl

--start query 83 using template query83.tpl
--@public
WITH sr_items AS (
        SELECT
                i_item_id item_id
                ,SUM(sr_return_quantity) sr_item_qty
            FROM
                store_returns
                ,item
                ,date_dim
            WHERE
                sr_item_sk = i_item_sk
                AND d_date IN (
                    SELECT
                            d_date
                        FROM
                            date_dim
                        WHERE
                            d_week_seq IN (
                                SELECT
                                        d_week_seq
                                    FROM
                                        date_dim
                                    WHERE
                                        d_date IN (
                                            '1999-04-17'
                                            ,'1999-10-04'
                                            ,'1999-11-10'
                                        )
                            )
                )
                AND sr_returned_date_sk = d_date_sk
            GROUP BY
                i_item_id
)
,cr_items AS (
    SELECT
            i_item_id item_id
            ,SUM(cr_return_quantity) cr_item_qty
        FROM
            catalog_returns
            ,item
            ,date_dim
        WHERE
            cr_item_sk = i_item_sk
            AND d_date IN (
                SELECT
                        d_date
                    FROM
                        date_dim
                    WHERE
                        d_week_seq IN (
                            SELECT
                                    d_week_seq
                                FROM
                                    date_dim
                                WHERE
                                    d_date IN (
                                        '1999-04-17'
                                        ,'1999-10-04'
                                        ,'1999-11-10'
                                    )
                        )
            )
            AND cr_returned_date_sk = d_date_sk
        GROUP BY
            i_item_id
)
,wr_items AS (
    SELECT
            i_item_id item_id
            ,SUM(wr_return_quantity) wr_item_qty
        FROM
            web_returns
            ,item
            ,date_dim
        WHERE
            wr_item_sk = i_item_sk
            AND d_date IN (
                SELECT
                        d_date
                    FROM
                        date_dim
                    WHERE
                        d_week_seq IN (
                            SELECT
                                    d_week_seq
                                FROM
                                    date_dim
                                WHERE
                                    d_date IN (
                                        '1999-04-17'
                                        ,'1999-10-04'
                                        ,'1999-11-10'
                                    )
                        )
            )
            AND wr_returned_date_sk = d_date_sk
        GROUP BY
            i_item_id
) SELECT
        top 100 sr_items.item_id
        ,sr_item_qty
        ,sr_item_qty / (sr_item_qty + cr_item_qty + wr_item_qty) / 3.0 * 100 sr_dev
        ,cr_item_qty
        ,cr_item_qty / (sr_item_qty + cr_item_qty + wr_item_qty) / 3.0 * 100 cr_dev
        ,wr_item_qty
        ,wr_item_qty / (sr_item_qty + cr_item_qty + wr_item_qty) / 3.0 * 100 wr_dev
        ,(sr_item_qty + cr_item_qty + wr_item_qty) / 3.0 average
    FROM
        sr_items
        ,cr_items
        ,wr_items
    WHERE
        sr_items.item_id = cr_items.item_id
        AND sr_items.item_id = wr_items.item_id
    ORDER BY
        sr_items.item_id
        ,sr_item_qty;
--end query 83 using template query83.tpl

--start query 86 using template query86.tpl
--@public
SELECT
        top 100 SUM(ws_net_paid) AS total_sum
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
                SUM(ws_net_paid) DESC
        ) AS rank_within_parent
    FROM
        web_sales
        ,date_dim d1
        ,item
    WHERE
        d1.d_month_seq BETWEEN 1211 AND 1211+11
        AND d1.d_date_sk = ws_sold_date_sk
        AND i_item_sk = ws_item_sk
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
--end query 86 using template query86.tpl

--start query 87 using template query87.tpl
--@public
SELECT
        COUNT(*)
    FROM
        (
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
                        AND d_month_seq BETWEEN 1214 AND 1214+11
            )
            EXCEPT
            (
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
                        AND d_month_seq BETWEEN 1214 AND 1214+11
            )
            EXCEPT
            (
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
                        AND d_month_seq BETWEEN 1214 AND 1214+11
            )
        ) cool_cust;
--end query 87 using template query87.tpl

--start query 89 using template query89.tpl
--@public
SELECT
        top 100 *
    FROM
        (
            SELECT
                    i_category
                    ,i_class
                    ,i_brand
                    ,s_store_name
                    ,s_company_name
                    ,d_moy
                    ,SUM(ss_sales_price) sum_sales
                    ,AVG(SUM(ss_sales_price)) OVER (
                        PARTITION BY
                            i_category
                            ,i_brand
                            ,s_store_name
                            ,s_company_name
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
                    AND d_year IN (2002)
                    AND (
                        (
                            i_category IN (
                                'Jewelry'
                                ,'Women'
                                ,'Shoes'
                            )
                            AND i_class IN (
                                'mens watch'
                                ,'dresses'
                                ,'mens'
                            )
                        )
                        OR (
                            i_category IN (
                                'Men'
                                ,'Sports'
                                ,'Music'
                            )
                            AND i_class IN (
                                'sports-apparel'
                                ,'sailing'
                                ,'pop'
                            )
                        )
                    )
                GROUP BY
                    i_category
                    ,i_class
                    ,i_brand
                    ,s_store_name
                    ,s_company_name
                    ,d_moy
        ) tmp1
    WHERE
        CASE
            WHEN (avg_monthly_sales <> 0) THEN (
                ABS(sum_sales - avg_monthly_sales) / avg_monthly_sales
            )
            ELSE null
        END > 0.1
    ORDER BY
        sum_sales - avg_monthly_sales
        ,s_store_name;
--end query 89 using template query89.tpl

--start query 92 using template query92.tpl
--@public
SELECT
        top 100 SUM(ws_ext_discount_amt) AS "Excess Discount Amount"
    FROM
        web_sales
        ,item
        ,date_dim
    WHERE
        i_manufact_id = 248
        AND i_item_sk = ws_item_sk
        AND d_date BETWEEN '2000-02-02' AND (
            cast(
                '2000-02-02' AS DATE
            ) + 90 days
        )
        AND d_date_sk = ws_sold_date_sk
        AND ws_ext_discount_amt > (
            SELECT
                    1.3 * AVG(ws_ext_discount_amt)
                FROM
                    web_sales
                    ,date_dim
                WHERE
                    ws_item_sk = i_item_sk
                    AND d_date BETWEEN '2000-02-02' AND (
                        cast(
                            '2000-02-02' AS DATE
                        ) + 90 days
                    )
                    AND d_date_sk = ws_sold_date_sk
        )
    ORDER BY
        SUM(ws_ext_discount_amt);
--end query 92 using template query92.tpl

--start query 94 using template query94.tpl
-- @public
SELECT
        top 100 COUNT(DISTINCT ws_order_number) AS "order count"
        ,SUM(ws_ext_ship_cost) AS "total shipping cost"
        ,SUM(ws_net_profit) AS "total net profit"
    FROM
        web_sales ws1
        ,date_dim
        ,customer_address
        ,web_site
    WHERE
        d_date BETWEEN '2002-4-01' AND (
            cast(
                '2002-4-01' AS DATE
            ) + 60 days
        )
        AND ws1.ws_ship_date_sk = d_date_sk
        AND ws1.ws_ship_addr_sk = ca_address_sk
        AND ca_state = 'MN'
        AND ws1.ws_web_site_sk = web_site_sk
        AND web_company_name = 'pri'
        AND EXISTS (
            SELECT
                    *
                FROM
                    web_sales ws2
                WHERE
                    ws1.ws_order_number = ws2.ws_order_number
                    AND ws1.ws_warehouse_sk <> ws2.ws_warehouse_sk
        )
        AND NOT EXISTS (
            SELECT
                    *
                FROM
                    web_returns wr1
                WHERE
                    ws1.ws_order_number = wr1.wr_order_number
        )
    ORDER BY
        COUNT(DISTINCT ws_order_number);
--end query 94 using template query94.tpl

--start query 97 using template query97.tpl
--@public
WITH ssci AS (
        SELECT
                ss_customer_sk customer_sk
                ,ss_item_sk item_sk
            FROM
                store_sales
                ,date_dim
            WHERE
                ss_sold_date_sk = d_date_sk
                AND d_month_seq BETWEEN 1202 AND 1202 + 11
            GROUP BY
                ss_customer_sk
                ,ss_item_sk
)
,csci AS (
    SELECT
            cs_bill_customer_sk customer_sk
            ,cs_item_sk item_sk
        FROM
            catalog_sales
            ,date_dim
        WHERE
            cs_sold_date_sk = d_date_sk
            AND d_month_seq BETWEEN 1202 AND 1202 + 11
        GROUP BY
            cs_bill_customer_sk
            ,cs_item_sk
) SELECT
        top 100 SUM(CASE
            WHEN ssci.customer_sk IS NOT null
            AND csci.customer_sk IS null THEN 1
            ELSE 0
        END) store_only
        ,SUM(CASE
            WHEN ssci.customer_sk IS null
            AND csci.customer_sk IS NOT null THEN 1
            ELSE 0
        END) catalog_only
        ,SUM(CASE
            WHEN ssci.customer_sk IS NOT null
            AND csci.customer_sk IS NOT null THEN 1
            ELSE 0
        END) store_and_catalog
    FROM
        ssci full OUTER
            JOIN csci
                ON (
                    ssci.customer_sk = csci.customer_sk
                    AND ssci.item_sk = csci.item_sk
                );
--end query 97 using template query97.tpl

--start query 98 using template query98.tpl
--@public
SELECT
        i_item_desc
        ,i_category
        ,i_class
        ,i_current_price
        ,SUM(ss_ext_sales_price) AS itemrevenue
        ,SUM(ss_ext_sales_price) * 100 / SUM(SUM(ss_ext_sales_price)) OVER (
            PARTITION BY
                i_class
        ) AS revenueratio
    FROM
        store_sales
        ,item
        ,date_dim
    WHERE
        ss_item_sk = i_item_sk
        AND i_category IN (
            'Music'
            ,'Jewelry'
            ,'Women'
        )
        AND ss_sold_date_sk = d_date_sk
        AND d_date BETWEEN cast(
            '1999-04-26' AS DATE
        ) AND (
            cast(
                '1999-04-26' AS DATE
            ) + 30 days
        )
    GROUP BY
        i_item_id
        ,i_item_desc
        ,i_category
        ,i_class
        ,i_current_price
    ORDER BY
        i_category
        ,i_class
        ,i_item_id
        ,i_item_desc
        ,revenueratio;
--end query 98 using template query98.tpl

