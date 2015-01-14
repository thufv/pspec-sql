--start query 81 using template query81.tpl
--@private, @projection(customer, customer_address)
WITH customer_total_return AS (
        SELECT
                cr_returning_customer_sk AS ctr_customer_sk
                ,ca_state AS ctr_state
                ,SUM(cr_return_amt_inc_tax) AS ctr_total_return
            FROM
                catalog_returns
                ,date_dim
                ,customer_address
            WHERE
                cr_returned_date_sk = d_date_sk
                AND d_year = 1998
                AND cr_returning_addr_sk = ca_address_sk
            GROUP BY
                cr_returning_customer_sk
                ,ca_state
) SELECT
        top 100 c_customer_id
        ,c_salutation
        ,c_first_name
        ,c_last_name
        ,ca_street_number
        ,ca_street_name
        ,ca_street_type
        ,ca_suite_number
        ,ca_city
        ,ca_county
        ,ca_state
        ,ca_zip
        ,ca_country
        ,ca_gmt_offset
        ,ca_location_type
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
        AND ca_state = 'TN'
        AND ctr1.ctr_customer_sk = c_customer_sk
    ORDER BY
        c_customer_id
        ,c_salutation
        ,c_first_name
        ,c_last_name
        ,ca_street_number
        ,ca_street_name
        ,ca_street_type
        ,ca_suite_number
        ,ca_city
        ,ca_county
        ,ca_state
        ,ca_zip
        ,ca_country
        ,ca_gmt_offset
        ,ca_location_type
        ,ctr_total_return;
--end query 81 using template query81.tpl
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
--start query 84 using template query84.tpl
-- @private, @projection(customer), @condition(demographics, customer_address)
SELECT
        c_customer_id AS customer_id
        ,c_last_name || ', ' || c_first_name AS customername
    FROM
        customer
        ,customer_address
        ,customer_demographics
        ,household_demographics
        ,income_band
        ,store_returns
    WHERE
        ca_city = 'Antioch'
        AND c_current_addr_sk = ca_address_sk
        AND ib_lower_bound >= 9901
        AND ib_upper_bound <= 9901 + 50000
        AND ib_income_band_sk = hd_income_band_sk
        AND cd_demo_sk = c_current_cdemo_sk
        AND hd_demo_sk = c_current_hdemo_sk
        AND sr_cdemo_sk = cd_demo_sk
    ORDER BY
        c_customer_id;
--end query 84 using template query84.tpl
--start query 85 using template query85.tpl
--@private, @condition(customer_address, demographics)
SELECT
        top 100 SUBSTR(r_reason_desc, 1, 20)
        ,AVG(ws_quantity)
        ,AVG(wr_refunded_cash)
        ,AVG(wr_fee)
    FROM
        web_sales
        ,web_returns
        ,web_page
        ,customer_demographics cd1
        ,customer_demographics cd2
        ,customer_address
        ,date_dim
        ,reason
    WHERE
        ws_web_page_sk = wp_web_page_sk
        AND ws_item_sk = wr_item_sk
        AND ws_order_number = wr_order_number
        AND ws_sold_date_sk = d_date_sk
        AND d_year = 2002
        AND cd1.cd_demo_sk = wr_refunded_cdemo_sk
        AND cd2.cd_demo_sk = wr_returning_cdemo_sk
        AND ca_address_sk = wr_refunded_addr_sk
        AND r_reason_sk = wr_reason_sk
        AND (
            (
                cd1.cd_marital_status = 'S'
                AND cd1.cd_marital_status = cd2.cd_marital_status
                AND cd1.cd_education_status = '4 yr Degree'
                AND cd1.cd_education_status = cd2.cd_education_status
                AND ws_sales_price BETWEEN 100.00 AND 150.00
            )
            OR (
                cd1.cd_marital_status = 'M'
                AND cd1.cd_marital_status = cd2.cd_marital_status
                AND cd1.cd_education_status = 'Primary'
                AND cd1.cd_education_status = cd2.cd_education_status
                AND ws_sales_price BETWEEN 50.00 AND 100.00
            )
            OR (
                cd1.cd_marital_status = 'U'
                AND cd1.cd_marital_status = cd2.cd_marital_status
                AND cd1.cd_education_status = '2 yr Degree'
                AND cd1.cd_education_status = cd2.cd_education_status
                AND ws_sales_price BETWEEN 150.00 AND 200.00
            )
        )
        AND (
            (
                ca_country = 'United States'
                AND ca_state IN (
                    'IL'
                    ,'MT'
                    ,'AR'
                )
                AND ws_net_profit BETWEEN 100 AND 200
            )
            OR (
                ca_country = 'United States'
                AND ca_state IN (
                    'WI'
                    ,'TX'
                    ,'GA'
                )
                AND ws_net_profit BETWEEN 150 AND 300
            )
            OR (
                ca_country = 'United States'
                AND ca_state IN (
                    'RI'
                    ,'KY'
                    ,'IN'
                )
                AND ws_net_profit BETWEEN 50 AND 250
            )
        )
    GROUP BY
        r_reason_desc
    ORDER BY
        SUBSTR(r_reason_desc, 1, 20)
        ,AVG(ws_quantity)
        ,AVG(wr_refunded_cash)
        ,AVG(wr_fee);
--end query 85 using template query85.tpl
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
--start query 88 using template query88.tpl
--@private, @condition(demographics)
SELECT
        *
    FROM
        (
            SELECT
                    COUNT(*) h8_30_to_9
                FROM
                    store_sales
                    ,household_demographics
                    ,time_dim
                    ,store
                WHERE
                    ss_sold_time_sk = time_dim.t_time_sk
                    AND ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND ss_store_sk = s_store_sk
                    AND time_dim.t_hour = 8
                    AND time_dim.t_minute >= 30
                    AND (
                        (
                            household_demographics.hd_dep_count = 4
                            AND household_demographics.hd_vehicle_count <= 4+2
                        )
                        OR (
                            household_demographics.hd_dep_count = -1
                            AND household_demographics.hd_vehicle_count <= -1+2
                        )
                        OR (
                            household_demographics.hd_dep_count = 0
                            AND household_demographics.hd_vehicle_count <= 0+2
                        )
                    )
                    AND store.s_store_name = 'ese'
        ) s1
        ,(
            SELECT
                    COUNT(*) h9_to_9_30
                FROM
                    store_sales
                    ,household_demographics
                    ,time_dim
                    ,store
                WHERE
                    ss_sold_time_sk = time_dim.t_time_sk
                    AND ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND ss_store_sk = s_store_sk
                    AND time_dim.t_hour = 9
                    AND time_dim.t_minute < 30
                    AND (
                        (
                            household_demographics.hd_dep_count = 4
                            AND household_demographics.hd_vehicle_count <= 4+2
                        )
                        OR (
                            household_demographics.hd_dep_count = -1
                            AND household_demographics.hd_vehicle_count <= -1+2
                        )
                        OR (
                            household_demographics.hd_dep_count = 0
                            AND household_demographics.hd_vehicle_count <= 0+2
                        )
                    )
                    AND store.s_store_name = 'ese'
        ) s2
        ,(
            SELECT
                    COUNT(*) h9_30_to_10
                FROM
                    store_sales
                    ,household_demographics
                    ,time_dim
                    ,store
                WHERE
                    ss_sold_time_sk = time_dim.t_time_sk
                    AND ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND ss_store_sk = s_store_sk
                    AND time_dim.t_hour = 9
                    AND time_dim.t_minute >= 30
                    AND (
                        (
                            household_demographics.hd_dep_count = 4
                            AND household_demographics.hd_vehicle_count <= 4+2
                        )
                        OR (
                            household_demographics.hd_dep_count = -1
                            AND household_demographics.hd_vehicle_count <= -1+2
                        )
                        OR (
                            household_demographics.hd_dep_count = 0
                            AND household_demographics.hd_vehicle_count <= 0+2
                        )
                    )
                    AND store.s_store_name = 'ese'
        ) s3
        ,(
            SELECT
                    COUNT(*) h10_to_10_30
                FROM
                    store_sales
                    ,household_demographics
                    ,time_dim
                    ,store
                WHERE
                    ss_sold_time_sk = time_dim.t_time_sk
                    AND ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND ss_store_sk = s_store_sk
                    AND time_dim.t_hour = 10
                    AND time_dim.t_minute < 30
                    AND (
                        (
                            household_demographics.hd_dep_count = 4
                            AND household_demographics.hd_vehicle_count <= 4+2
                        )
                        OR (
                            household_demographics.hd_dep_count = -1
                            AND household_demographics.hd_vehicle_count <= -1+2
                        )
                        OR (
                            household_demographics.hd_dep_count = 0
                            AND household_demographics.hd_vehicle_count <= 0+2
                        )
                    )
                    AND store.s_store_name = 'ese'
        ) s4
        ,(
            SELECT
                    COUNT(*) h10_30_to_11
                FROM
                    store_sales
                    ,household_demographics
                    ,time_dim
                    ,store
                WHERE
                    ss_sold_time_sk = time_dim.t_time_sk
                    AND ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND ss_store_sk = s_store_sk
                    AND time_dim.t_hour = 10
                    AND time_dim.t_minute >= 30
                    AND (
                        (
                            household_demographics.hd_dep_count = 4
                            AND household_demographics.hd_vehicle_count <= 4+2
                        )
                        OR (
                            household_demographics.hd_dep_count = -1
                            AND household_demographics.hd_vehicle_count <= -1+2
                        )
                        OR (
                            household_demographics.hd_dep_count = 0
                            AND household_demographics.hd_vehicle_count <= 0+2
                        )
                    )
                    AND store.s_store_name = 'ese'
        ) s5
        ,(
            SELECT
                    COUNT(*) h11_to_11_30
                FROM
                    store_sales
                    ,household_demographics
                    ,time_dim
                    ,store
                WHERE
                    ss_sold_time_sk = time_dim.t_time_sk
                    AND ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND ss_store_sk = s_store_sk
                    AND time_dim.t_hour = 11
                    AND time_dim.t_minute < 30
                    AND (
                        (
                            household_demographics.hd_dep_count = 4
                            AND household_demographics.hd_vehicle_count <= 4+2
                        )
                        OR (
                            household_demographics.hd_dep_count = -1
                            AND household_demographics.hd_vehicle_count <= -1+2
                        )
                        OR (
                            household_demographics.hd_dep_count = 0
                            AND household_demographics.hd_vehicle_count <= 0+2
                        )
                    )
                    AND store.s_store_name = 'ese'
        ) s6
        ,(
            SELECT
                    COUNT(*) h11_30_to_12
                FROM
                    store_sales
                    ,household_demographics
                    ,time_dim
                    ,store
                WHERE
                    ss_sold_time_sk = time_dim.t_time_sk
                    AND ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND ss_store_sk = s_store_sk
                    AND time_dim.t_hour = 11
                    AND time_dim.t_minute >= 30
                    AND (
                        (
                            household_demographics.hd_dep_count = 4
                            AND household_demographics.hd_vehicle_count <= 4+2
                        )
                        OR (
                            household_demographics.hd_dep_count = -1
                            AND household_demographics.hd_vehicle_count <= -1+2
                        )
                        OR (
                            household_demographics.hd_dep_count = 0
                            AND household_demographics.hd_vehicle_count <= 0+2
                        )
                    )
                    AND store.s_store_name = 'ese'
        ) s7
        ,(
            SELECT
                    COUNT(*) h12_to_12_30
                FROM
                    store_sales
                    ,household_demographics
                    ,time_dim
                    ,store
                WHERE
                    ss_sold_time_sk = time_dim.t_time_sk
                    AND ss_hdemo_sk = household_demographics.hd_demo_sk
                    AND ss_store_sk = s_store_sk
                    AND time_dim.t_hour = 12
                    AND time_dim.t_minute < 30
                    AND (
                        (
                            household_demographics.hd_dep_count = 4
                            AND household_demographics.hd_vehicle_count <= 4+2
                        )
                        OR (
                            household_demographics.hd_dep_count = -1
                            AND household_demographics.hd_vehicle_count <= -1+2
                        )
                        OR (
                            household_demographics.hd_dep_count = 0
                            AND household_demographics.hd_vehicle_count <= 0+2
                        )
                    )
                    AND store.s_store_name = 'ese'
        ) s8;
--end query 88 using template query88.tpl
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
--start query 90 using template query90.tpl
--@private, @condition(demographics)
SELECT
        top 100 cast(
            amc AS DECIMAL(15, 4)
        ) / cast(
            pmc AS DECIMAL(15, 4)
        ) am_pm_ratio
    FROM
        (
            SELECT
                    COUNT(*) amc
                FROM
                    web_sales
                    ,household_demographics
                    ,time_dim
                    ,web_page
                WHERE
                    ws_sold_time_sk = time_dim.t_time_sk
                    AND ws_ship_hdemo_sk = household_demographics.hd_demo_sk
                    AND ws_web_page_sk = web_page.wp_web_page_sk
                    AND time_dim.t_hour BETWEEN 11 AND 11+1
                    AND household_demographics.hd_dep_count = 8
                    AND web_page.wp_char_count BETWEEN 5000 AND 5200
        ) at
        ,(
            SELECT
                    COUNT(*) pmc
                FROM
                    web_sales
                    ,household_demographics
                    ,time_dim
                    ,web_page
                WHERE
                    ws_sold_time_sk = time_dim.t_time_sk
                    AND ws_ship_hdemo_sk = household_demographics.hd_demo_sk
                    AND ws_web_page_sk = web_page.wp_web_page_sk
                    AND time_dim.t_hour BETWEEN 13 AND 13+1
                    AND household_demographics.hd_dep_count = 8
                    AND web_page.wp_char_count BETWEEN 5000 AND 5200
        ) pt
    ORDER BY
        am_pm_ratio;
--end query 90 using template query90.tpl
--start query 91 using template query91.tpl
--@private, @condition(demographics)
SELECT
        cc_call_center_id Call_Center
        ,cc_name Call_Center_Name
        ,cc_manager Manager
        ,SUM(cr_net_loss) Returns_Loss
    FROM
        call_center
        ,catalog_returns
        ,date_dim
        ,customer
        ,customer_address
        ,customer_demographics
        ,household_demographics
    WHERE
        cr_call_center_sk = cc_call_center_sk
        AND cr_returned_date_sk = d_date_sk
        AND cr_returning_customer_sk = c_customer_sk
        AND cd_demo_sk = c_current_cdemo_sk
        AND hd_demo_sk = c_current_hdemo_sk
        AND ca_address_sk = c_current_addr_sk
        AND d_year = 2000
        AND d_moy = 12
        AND (
            (
                cd_marital_status = 'M'
                AND cd_education_status = 'Unknown'
            )
            OR (
                cd_marital_status = 'W'
                AND cd_education_status = 'Advanced Degree'
            )
        )
        AND hd_buy_potential LIKE '>10000%'
        AND ca_gmt_offset = -6
    GROUP BY
        cc_call_center_id
        ,cc_name
        ,cc_manager
        ,cd_marital_status
        ,cd_education_status
    ORDER BY
        SUM(cr_net_loss) DESC;
--end query 91 using template query91.tpl
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
--start query 93 using template query93.tpl
--@private, @projection(customer)
SELECT
        top 100 ss_customer_sk
        ,SUM(act_sales) sumsales
    FROM
        (
            SELECT
                    ss_item_sk
                    ,ss_ticket_number
                    ,ss_customer_sk
                    ,CASE
                        WHEN sr_return_quantity IS NOT null THEN (ss_quantity - sr_return_quantity) * ss_sales_price
                        ELSE (ss_quantity * ss_sales_price)
                    END act_sales
                FROM
                    store_sales
                        LEFT OUTER JOIN store_returns
                            ON (
                                sr_item_sk = ss_item_sk
                                AND sr_ticket_number = ss_ticket_number
                            )
                    ,reason
                WHERE
                    sr_reason_sk = r_reason_sk
                    AND r_reason_desc = 'reason 55'
        ) t
    GROUP BY
        ss_customer_sk
    ORDER BY
        sumsales
        ,ss_customer_sk;
--end query 93 using template query93.tpl
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
--start query 95 using template query95.tpl
--@private, @condition(customer_address)
WITH ws_wh AS (
        SELECT
                ws1.ws_order_number
                ,ws1.ws_warehouse_sk wh1
                ,ws2.ws_warehouse_sk wh2
            FROM
                web_sales ws1
                ,web_sales ws2
            WHERE
                ws1.ws_order_number = ws2.ws_order_number
                AND ws1.ws_warehouse_sk <> ws2.ws_warehouse_sk
) SELECT
        top 100 COUNT(DISTINCT ws_order_number) AS "order count"
        ,SUM(ws_ext_ship_cost) AS "total shipping cost"
        ,SUM(ws_net_profit) AS "total net profit"
    FROM
        web_sales ws1
        ,date_dim
        ,customer_address
        ,web_site
    WHERE
        d_date BETWEEN '2000-2-01' AND (
            cast(
                '2000-2-01' AS DATE
            ) + 60 days
        )
        AND ws1.ws_ship_date_sk = d_date_sk
        AND ws1.ws_ship_addr_sk = ca_address_sk
        AND ca_state = 'NE'
        AND ws1.ws_web_site_sk = web_site_sk
        AND web_company_name = 'pri'
        AND ws1.ws_order_number IN (
            SELECT
                    ws_order_number
                FROM
                    ws_wh
        )
        AND ws1.ws_order_number IN (
            SELECT
                    wr_order_number
                FROM
                    web_returns
                    ,ws_wh
                WHERE
                    wr_order_number = ws_wh.ws_order_number
        )
    ORDER BY
        COUNT(DISTINCT ws_order_number);
--end query 95 using template query95.tpl
--start query 96 using template query96.tpl
-- todo why they attach an "top 100" before count(*)?
--@private, @condition(demographics)
SELECT
        top 100 COUNT(*)
    FROM
        store_sales
        ,household_demographics
        ,time_dim
        ,store
    WHERE
        ss_sold_time_sk = time_dim.t_time_sk
        AND ss_hdemo_sk = household_demographics.hd_demo_sk
        AND ss_store_sk = s_store_sk
        AND time_dim.t_hour = 8
        AND time_dim.t_minute >= 30
        AND household_demographics.hd_dep_count = 5
        AND store.s_store_name = 'ese'
    ORDER BY
        COUNT(*);
--end query 96 using template query96.tpl
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
--start query 99 using template query99.tpl
--@public
SELECT
        top 100 SUBSTR(w_warehouse_name, 1, 20)
        ,sm_type
        ,cc_name
        ,SUM(CASE
            WHEN (cs_ship_date_sk - cs_sold_date_sk <= 30) THEN 1
            ELSE 0
        END) AS "30 days"
        ,SUM(CASE
            WHEN (cs_ship_date_sk - cs_sold_date_sk > 30)
            AND (cs_ship_date_sk - cs_sold_date_sk <= 60) THEN 1
            ELSE 0
        END) AS "31-60 days"
        ,SUM(CASE
            WHEN (cs_ship_date_sk - cs_sold_date_sk > 60)
            AND (cs_ship_date_sk - cs_sold_date_sk <= 90) THEN 1
            ELSE 0
        END) AS "61-90 days"
        ,SUM(CASE
            WHEN (cs_ship_date_sk - cs_sold_date_sk > 90)
            AND (cs_ship_date_sk - cs_sold_date_sk <= 120) THEN 1
            ELSE 0
        END) AS "91-120 days"
        ,SUM(CASE
            WHEN (cs_ship_date_sk - cs_sold_date_sk > 120) THEN 1
            ELSE 0
        END) AS ">120 days"
    FROM
        catalog_sales
        ,warehouse
        ,ship_mode
        ,call_center
        ,date_dim
    WHERE
        d_month_seq BETWEEN 1183 AND 1183 + 11
        AND cs_ship_date_sk = d_date_sk
        AND cs_warehouse_sk = w_warehouse_sk
        AND cs_ship_mode_sk = sm_ship_mode_sk
        AND cs_call_center_sk = cc_call_center_sk
    GROUP BY
        SUBSTR(w_warehouse_name, 1, 20)
        ,sm_type
        ,cc_name
    ORDER BY
        SUBSTR(w_warehouse_name, 1, 20)
        ,sm_type
        ,cc_name;
--end query 99 using template query99.tpl;
