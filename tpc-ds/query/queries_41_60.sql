--start query 41 using template query41.tpl
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
--start query 45 using template query45.tpl
-- @private, @projection(customer_address)
SELECT
        top 100 ca_zip
        ,ca_state
        ,SUM(ws_sales_price)
    FROM
        web_sales
        ,customer
        ,customer_address
        ,date_dim
        ,item
    WHERE
        ws_bill_customer_sk = c_customer_sk
        AND c_current_addr_sk = ca_address_sk
        AND ws_item_sk = i_item_sk
        AND (
            SUBSTR(ca_zip, 1, 5) IN (
                '85669'
                ,'86197'
                ,'88274'
                ,'83405'
                ,'86475'
                ,'85392'
                ,'85460'
                ,'80348'
                ,'81792'
            )
            OR i_item_id IN (
                SELECT
                        i_item_id
                    FROM
                        item
                    WHERE
                        i_item_sk IN (
                            2
                            ,3
                            ,5
                            ,7
                            ,11
                            ,13
                            ,17
                            ,19
                            ,23
                            ,29
                        )
            )
        )
        AND ws_sold_date_sk = d_date_sk
        AND d_qoy = 2
        AND d_year = 1999
    GROUP BY
        ca_zip
        ,ca_state
    ORDER BY
        ca_zip
        ,ca_state;
--end query 45 using template query45.tpl
--start query 46 using template query46.tpl
-- @private, @projection(name,customer_address), @condition(demographic)
SELECT
        top 100 c_last_name
        ,c_first_name
        ,ca_city
        ,bought_city
        ,ss_ticket_number
        ,amt
        ,profit
    FROM
        (
            SELECT
                    ss_ticket_number
                    ,ss_customer_sk
                    ,ca_city bought_city
                    ,SUM(ss_coupon_amt) amt
                    ,SUM(ss_net_profit) profit
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
                    AND (
                        household_demographics.hd_dep_count = 8
                        OR household_demographics.hd_vehicle_count = -1
                    )
                    AND date_dim.d_dow IN (
                        6
                        ,0
                    )
                    AND date_dim.d_year IN (
                        2000
                        ,2000+1
                        ,2000+2
                    )
                    AND store.s_city IN (
                        'Midway'
                        ,'Fairview'
                        ,'Fairview'
                        ,'Fairview'
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
        ,c_first_name
        ,ca_city
        ,bought_city
        ,ss_ticket_number;
--end query 46 using template query46.tpl
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
--start query 48 using template query48.tpl
--@private, @condition(demographics, customer_address)
SELECT
        SUM(ss_quantity)
    FROM
        store_sales
        ,store
        ,customer_demographics
        ,customer_address
        ,date_dim
    WHERE
        s_store_sk = ss_store_sk
        AND ss_sold_date_sk = d_date_sk
        AND d_year = 1998
        AND (
            (
                cd_demo_sk = ss_cdemo_sk
                AND cd_marital_status = 'S'
                AND cd_education_status = '4 yr Degree'
                AND ss_sales_price BETWEEN 100.00 AND 150.00
            )
            OR (
                cd_demo_sk = ss_cdemo_sk
                AND cd_marital_status = 'S'
                AND cd_education_status = '4 yr Degree'
                AND ss_sales_price BETWEEN 50.00 AND 100.00
            )
            OR (
                cd_demo_sk = ss_cdemo_sk
                AND cd_marital_status = 'S'
                AND cd_education_status = '4 yr Degree'
                AND ss_sales_price BETWEEN 150.00 AND 200.00
            )
        )
        AND (
            (
                ss_addr_sk = ca_address_sk
                AND ca_country = 'United States'
                AND ca_state IN (
                    'AK'
                    ,'IA'
                    ,'NE'
                )
                AND ss_net_profit BETWEEN 0 AND 2000
            )
            OR (
                ss_addr_sk = ca_address_sk
                AND ca_country = 'United States'
                AND ca_state IN (
                    'NY'
                    ,'VA'
                    ,'AR'
                )
                AND ss_net_profit BETWEEN 150 AND 3000
            )
            OR (
                ss_addr_sk = ca_address_sk
                AND ca_country = 'United States'
                AND ca_state IN (
                    'AZ'
                    ,'MI'
                    ,'NC'
                )
                AND ss_net_profit BETWEEN 50 AND 25000
            )
        );
--end query 48 using template query48.tpl
--start query 49 using template query49.tpl
--@
SELECT
        top 100 'web' AS channel
        ,web.item
        ,web.return_ratio
        ,web.return_rank
        ,web.currency_rank
    FROM
        (
            SELECT
                    item
                    ,return_ratio
                    ,currency_ratio
                    ,rank() OVER (
                    ORDER BY
                        return_ratio
                    ) AS return_rank
                    ,rank() OVER (
                    ORDER BY
                        currency_ratio
                    ) AS currency_rank
                FROM
                    (
                        SELECT
                                ws.ws_item_sk AS item
                                ,(
                                    cast(
                                        SUM(COALESCE(wr.wr_return_quantity, 0)) AS DEC(15, 4)
                                    ) / cast(
                                        SUM(COALESCE(ws.ws_quantity, 0)) AS DEC(15, 4)
                                    )
                                ) AS return_ratio
                                ,(
                                    cast(
                                        SUM(COALESCE(wr.wr_return_amt, 0)) AS DEC(15, 4)
                                    ) / cast(
                                        SUM(COALESCE(ws.ws_net_paid, 0)) AS DEC(15, 4)
                                    )
                                ) AS currency_ratio
                            FROM
                                web_sales ws
                                    LEFT OUTER JOIN web_returns wr
                                        ON (
                                            ws.ws_order_number = wr.wr_order_number
                                            AND ws.ws_item_sk = wr.wr_item_sk
                                        )
                                ,date_dim
                            WHERE
                                wr.wr_return_amt > 10000
                                AND ws.ws_net_profit > 1
                                AND ws.ws_net_paid > 0
                                AND ws.ws_quantity > 0
                                AND ws_sold_date_sk = d_date_sk
                                AND d_year = 2000
                                AND d_moy = 12
                            GROUP BY
                                ws.ws_item_sk
                    ) in_web
        ) web
    WHERE
        (
            web.return_rank <= 10
            OR web.currency_rank <= 10
        )
UNION
SELECT
        'catalog' AS channel
        ,catalog.item
        ,catalog.return_ratio
        ,catalog.return_rank
        ,catalog.currency_rank
    FROM
        (
            SELECT
                    item
                    ,return_ratio
                    ,currency_ratio
                    ,rank() OVER (
                    ORDER BY
                        return_ratio
                    ) AS return_rank
                    ,rank() OVER (
                    ORDER BY
                        currency_ratio
                    ) AS currency_rank
                FROM
                    (
                        SELECT
                                cs.cs_item_sk AS item
                                ,(
                                    cast(
                                        SUM(COALESCE(cr.cr_return_quantity, 0)) AS DEC(15, 4)
                                    ) / cast(
                                        SUM(COALESCE(cs.cs_quantity, 0)) AS DEC(15, 4)
                                    )
                                ) AS return_ratio
                                ,(
                                    cast(
                                        SUM(COALESCE(cr.cr_return_amount, 0)) AS DEC(15, 4)
                                    ) / cast(
                                        SUM(COALESCE(cs.cs_net_paid, 0)) AS DEC(15, 4)
                                    )
                                ) AS currency_ratio
                            FROM
                                catalog_sales cs
                                    LEFT OUTER JOIN catalog_returns cr
                                        ON (
                                            cs.cs_order_number = cr.cr_order_number
                                            AND cs.cs_item_sk = cr.cr_item_sk
                                        )
                                ,date_dim
                            WHERE
                                cr.cr_return_amount > 10000
                                AND cs.cs_net_profit > 1
                                AND cs.cs_net_paid > 0
                                AND cs.cs_quantity > 0
                                AND cs_sold_date_sk = d_date_sk
                                AND d_year = 2000
                                AND d_moy = 12
                            GROUP BY
                                cs.cs_item_sk
                    ) in_cat
        ) catalog
    WHERE
        (
            catalog.return_rank <= 10
            OR catalog.currency_rank <= 10
        )
UNION
SELECT
        'store' AS channel
        ,store.item
        ,store.return_ratio
        ,store.return_rank
        ,store.currency_rank
    FROM
        (
            SELECT
                    item
                    ,return_ratio
                    ,currency_ratio
                    ,rank() OVER (
                    ORDER BY
                        return_ratio
                    ) AS return_rank
                    ,rank() OVER (
                    ORDER BY
                        currency_ratio
                    ) AS currency_rank
                FROM
                    (
                        SELECT
                                sts.ss_item_sk AS item
                                ,(
                                    cast(
                                        SUM(COALESCE(sr.sr_return_quantity, 0)) AS DEC(15, 4)
                                    ) / cast(
                                        SUM(COALESCE(sts.ss_quantity, 0)) AS DEC(15, 4)
                                    )
                                ) AS return_ratio
                                ,(
                                    cast(
                                        SUM(COALESCE(sr.sr_return_amt, 0)) AS DEC(15, 4)
                                    ) / cast(
                                        SUM(COALESCE(sts.ss_net_paid, 0)) AS DEC(15, 4)
                                    )
                                ) AS currency_ratio
                            FROM
                                store_sales sts
                                    LEFT OUTER JOIN store_returns sr
                                        ON (
                                            sts.ss_ticket_number = sr.sr_ticket_number
                                            AND sts.ss_item_sk = sr.sr_item_sk
                                        )
                                ,date_dim
                            WHERE
                                sr.sr_return_amt > 10000
                                AND sts.ss_net_profit > 1
                                AND sts.ss_net_paid > 0
                                AND sts.ss_quantity > 0
                                AND ss_sold_date_sk = d_date_sk
                                AND d_year = 2000
                                AND d_moy = 12
                            GROUP BY
                                sts.ss_item_sk
                    ) in_store
        ) store
    WHERE
        (
            store.return_rank <= 10
            OR store.currency_rank <= 10
        )
    ORDER BY
        1
        ,4
        ,5;
--end query 49 using template query49.tpl
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
--start query 54 using template query54.tpl
--@private, @condition(customer, customer_address)
WITH my_customers AS (
        SELECT
                DISTINCT c_customer_sk
                ,c_current_addr_sk
            FROM
                (
                    SELECT
                            cs_sold_date_sk sold_date_sk
                            ,cs_bill_customer_sk customer_sk
                            ,cs_item_sk item_sk
                        FROM
                            catalog_sales
                    UNION ALL
                    SELECT
                            ws_sold_date_sk sold_date_sk
                            ,ws_bill_customer_sk customer_sk
                            ,ws_item_sk item_sk
                        FROM
                            web_sales
                ) cs_or_ws_sales
                ,item
                ,date_dim
                ,customer
            WHERE
                sold_date_sk = d_date_sk
                AND item_sk = i_item_sk
                AND i_category = 'Children'
                AND i_class = 'toddlers'
                AND c_customer_sk = cs_or_ws_sales.customer_sk
                AND d_moy = 5
                AND d_year = 2001
)
,my_revenue AS (
    SELECT
            c_customer_sk
            ,SUM(ss_ext_sales_price) AS revenue
        FROM
            my_customers
            ,store_sales
            ,customer_address
            ,store
            ,date_dim
        WHERE
            c_current_addr_sk = ca_address_sk
            AND ca_county = s_county
            AND ca_state = s_state
            AND ss_sold_date_sk = d_date_sk
            AND c_customer_sk = ss_customer_sk
            AND d_month_seq BETWEEN (
                SELECT
                        DISTINCT d_month_seq +1
                    FROM
                        date_dim
                    WHERE
                        d_year = 2001 AND d_moy = 5
            )
            AND (
                SELECT
                        DISTINCT d_month_seq +3
                    FROM
                        date_dim
                    WHERE
                        d_year = 2001
                        AND d_moy = 5
            )
        GROUP BY
            c_customer_sk
)
,segments AS (
    SELECT
            cast(
                (revenue / 50) AS INT
            ) AS segment
        FROM
            my_revenue
) SELECT
        top 100 segment
        ,COUNT(*) AS num_customers
        ,segment * 50 AS segment_base
    FROM
        segments
    GROUP BY
        segment
    ORDER BY
        segment
        ,num_customers;
--end query 54 using template query54.tpl
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
--start query 56 using template query56.tpl
--@private, @condition(customer_address)
WITH ss AS (
        SELECT
                i_item_id
                ,SUM(ss_ext_sales_price) total_sales
            FROM
                store_sales
                ,date_dim
                ,customer_address
                ,item
            WHERE
                i_item_id IN (
                    SELECT
                            i_item_id
                        FROM
                            item
                        WHERE
                            i_color IN (
                                'almond'
                                ,'dodger'
                                ,'dim'
                            )
                )
                AND ss_item_sk = i_item_sk
                AND ss_sold_date_sk = d_date_sk
                AND d_year = 1998
                AND d_moy = 1
                AND ss_addr_sk = ca_address_sk
                AND ca_gmt_offset = -5
            GROUP BY
                i_item_id
)
,cs AS (
    SELECT
            i_item_id
            ,SUM(cs_ext_sales_price) total_sales
        FROM
            catalog_sales
            ,date_dim
            ,customer_address
            ,item
        WHERE
            i_item_id IN (
                SELECT
                        i_item_id
                    FROM
                        item
                    WHERE
                        i_color IN (
                            'almond'
                            ,'dodger'
                            ,'dim'
                        )
            )
            AND cs_item_sk = i_item_sk
            AND cs_sold_date_sk = d_date_sk
            AND d_year = 1998
            AND d_moy = 1
            AND cs_bill_addr_sk = ca_address_sk
            AND ca_gmt_offset = -5
        GROUP BY
            i_item_id
)
,ws AS (
    SELECT
            i_item_id
            ,SUM(ws_ext_sales_price) total_sales
        FROM
            web_sales
            ,date_dim
            ,customer_address
            ,item
        WHERE
            i_item_id IN (
                SELECT
                        i_item_id
                    FROM
                        item
                    WHERE
                        i_color IN (
                            'almond'
                            ,'dodger'
                            ,'dim'
                        )
            )
            AND ws_item_sk = i_item_sk
            AND ws_sold_date_sk = d_date_sk
            AND d_year = 1998
            AND d_moy = 1
            AND ws_bill_addr_sk = ca_address_sk
            AND ca_gmt_offset = -5
        GROUP BY
            i_item_id
) SELECT
        top 100 i_item_id
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
        i_item_id
    ORDER BY
        total_sales;
--end query 56 using template query56.tpl
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
--start query 60 using template query60.tpl
--@private, @condition(customer_address)
WITH ss AS (
        SELECT
                i_item_id
                ,SUM(ss_ext_sales_price) total_sales
            FROM
                store_sales
                ,date_dim
                ,customer_address
                ,item
            WHERE
                i_item_id IN (
                    SELECT
                            i_item_id
                        FROM
                            item
                        WHERE
                            i_category IN ('Children')
                )
                AND ss_item_sk = i_item_sk
                AND ss_sold_date_sk = d_date_sk
                AND d_year = 1998
                AND d_moy = 8
                AND ss_addr_sk = ca_address_sk
                AND ca_gmt_offset = -5
            GROUP BY
                i_item_id
)
,cs AS (
    SELECT
            i_item_id
            ,SUM(cs_ext_sales_price) total_sales
        FROM
            catalog_sales
            ,date_dim
            ,customer_address
            ,item
        WHERE
            i_item_id IN (
                SELECT
                        i_item_id
                    FROM
                        item
                    WHERE
                        i_category IN ('Children')
            )
            AND cs_item_sk = i_item_sk
            AND cs_sold_date_sk = d_date_sk
            AND d_year = 1998
            AND d_moy = 8
            AND cs_bill_addr_sk = ca_address_sk
            AND ca_gmt_offset = -5
        GROUP BY
            i_item_id
)
,ws AS (
    SELECT
            i_item_id
            ,SUM(ws_ext_sales_price) total_sales
        FROM
            web_sales
            ,date_dim
            ,customer_address
            ,item
        WHERE
            i_item_id IN (
                SELECT
                        i_item_id
                    FROM
                        item
                    WHERE
                        i_category IN ('Children')
            )
            AND ws_item_sk = i_item_sk
            AND ws_sold_date_sk = d_date_sk
            AND d_year = 1998
            AND d_moy = 8
            AND ws_bill_addr_sk = ca_address_sk
            AND ca_gmt_offset = -5
        GROUP BY
            i_item_id
) SELECT
        top 100 i_item_id
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
        i_item_id
    ORDER BY
        i_item_id
        ,total_sales;
--end query 60 using template query60.tpl