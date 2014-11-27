--1. 去掉所有的top
--2. with clause 合并到join中去
--3. where subquery 合并到join中去
--4. 去掉group by中的roll up
--5. window function


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
SELECT dt.d_year
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
--start query 4 using template query4.tpl
-- @private, @condition(customer)
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
                ,SUM(((ss_ext_list_price - ss_ext_wholesale_cost - ss_ext_discount_amt) + ss_ext_sales_price) / 2) year_total
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
            ,SUM((((cs_ext_list_price - cs_ext_wholesale_cost - cs_ext_discount_amt) + cs_ext_sales_price) / 2)) year_total
            ,'c' sale_type
        FROM
            customer
            ,catalog_sales
            ,date_dim
        WHERE
            c_customer_sk = cs_bill_customer_sk
            AND cs_sold_date_sk = d_date_sk
        GROUP BY
            c_customer_id
            ,c_first_name
            ,c_last_name
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
            ,SUM((((ws_ext_list_price - ws_ext_wholesale_cost - ws_ext_discount_amt) + ws_ext_sales_price) / 2)) year_total
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
        ,year_total t_c_firstyear
        ,year_total t_c_secyear
        ,year_total t_w_firstyear
        ,year_total t_w_secyear
    WHERE
        t_s_secyear.customer_id = t_s_firstyear.customer_id
        AND t_s_firstyear.customer_id = t_c_secyear.customer_id
        AND t_s_firstyear.customer_id = t_c_firstyear.customer_id
        AND t_s_firstyear.customer_id = t_w_firstyear.customer_id
        AND t_s_firstyear.customer_id = t_w_secyear.customer_id
        AND t_s_firstyear.sale_type = 's'
        AND t_c_firstyear.sale_type = 'c'
        AND t_w_firstyear.sale_type = 'w'
        AND t_s_secyear.sale_type = 's'
        AND t_c_secyear.sale_type = 'c'
        AND t_w_secyear.sale_type = 'w'
        AND t_s_firstyear.dyear = 1999
        AND t_s_secyear.dyear = 1999+1
        AND t_c_firstyear.dyear = 1999
        AND t_c_secyear.dyear = 1999+1
        AND t_w_firstyear.dyear = 1999
        AND t_w_secyear.dyear = 1999+1
        AND t_s_firstyear.year_total > 0
        AND t_c_firstyear.year_total > 0
        AND t_w_firstyear.year_total > 0
        AND CASE
            WHEN t_c_firstyear.year_total > 0 THEN t_c_secyear.year_total / t_c_firstyear.year_total
            ELSE null
        END > CASE
            WHEN t_s_firstyear.year_total > 0 THEN t_s_secyear.year_total / t_s_firstyear.year_total
            ELSE null
        END
        AND CASE
            WHEN t_c_firstyear.year_total > 0 THEN t_c_secyear.year_total / t_c_firstyear.year_total
            ELSE null
        END > CASE
            WHEN t_w_firstyear.year_total > 0 THEN t_w_secyear.year_total / t_w_firstyear.year_total
            ELSE null
        END
    ORDER BY
        t_s_secyear.customer_login;
--end query 4 using template query4.tpl
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
--start query 6 using template query6.tpl
-- @private, @projection(customer_address)
SELECT
        top 100 a.ca_state state
        ,COUNT(*) cnt
    FROM
        customer_address a
        ,customer c
        ,store_sales s
        ,date_dim d
        ,item i
    WHERE
        a.ca_address_sk = c.c_current_addr_sk
        AND c.c_customer_sk = s.ss_customer_sk
        AND s.ss_sold_date_sk = d.d_date_sk
        AND s.ss_item_sk = i.i_item_sk
        AND d.d_month_seq = (
            SELECT
                    DISTINCT (d_month_seq)
                FROM
                    date_dim
                WHERE
                    d_year = 1998
                    AND d_moy = 5
        )
        AND i.i_current_price > 1.2 * (
            SELECT
                    AVG(j.i_current_price)
                FROM
                    item j
                WHERE
                    j.i_category = i.i_category
        )
    GROUP BY
        a.ca_state
    HAVING
        COUNT(*) >= 10
    ORDER BY
        cnt;
--end query 6 using template query6.tpl
--start query 7 using template query7.tpl
-- @private, @condition(demographics)
SELECT
        i_item_id
        ,AVG(ss_quantity) agg1
        ,AVG(ss_list_price) agg2
        ,AVG(ss_coupon_amt) agg3
        ,AVG(ss_sales_price) agg4
    FROM
        store_sales
        ,customer_demographics
        ,date_dim
        ,item
        ,promotion
    WHERE
        ss_sold_date_sk = d_date_sk
        AND ss_item_sk = i_item_sk
        AND ss_cdemo_sk = cd_demo_sk
        AND ss_promo_sk = p_promo_sk
        AND cd_gender = 'M'
        AND cd_marital_status = 'M'
        AND cd_education_status = '4 yr Degree'
        AND (
            p_channel_email = 'N'
            OR p_channel_event = 'N'
        )
        AND d_year = 2001
    GROUP BY
        i_item_id
    ORDER BY
        i_item_id;
--end query 7 using template query7.tpl
--start query 8 using template query8.tpl
--@private, @condition(customer_address)
SELECT
        top 100 s_store_name
        ,SUM(ss_net_profit)
    FROM
        store_sales
        ,date_dim
        ,store
        ,(
            SELECT
                    ca_zip
                FROM
                    (
                        (
                            SELECT
                                    SUBSTR(ca_zip, 1, 5) ca_zip
                                FROM
                                    customer_address
                                WHERE
                                    SUBSTR(ca_zip, 1, 5) IN (
                                        '16733'
                                        ,'50732'
                                        ,'51878'
                                        ,'16933'
                                        ,'33177'
                                        ,'55974'
                                        ,'21338'
                                        ,'90455'
                                        ,'63106'
                                        ,'78712'
                                        ,'45114'
                                        ,'51090'
                                        ,'44881'
                                        ,'35526'
                                        ,'91360'
                                        ,'34986'
                                        ,'31893'
                                        ,'28853'
                                        ,'84061'
                                        ,'25483'
                                        ,'84541'
                                        ,'39275'
                                        ,'56211'
                                        ,'51199'
                                        ,'85189'
                                        ,'24292'
                                        ,'27477'
                                        ,'46388'
                                        ,'77218'
                                        ,'21137'
                                        ,'43660'
                                        ,'36509'
                                        ,'77925'
                                        ,'11691'
                                        ,'26790'
                                        ,'35256'
                                        ,'59221'
                                        ,'42491'
                                        ,'39214'
                                        ,'35273'
                                        ,'27293'
                                        ,'74258'
                                        ,'68798'
                                        ,'50936'
                                        ,'19136'
                                        ,'25240'
                                        ,'89163'
                                        ,'21667'
                                        ,'30941'
                                        ,'61680'
                                        ,'10425'
                                        ,'96787'
                                        ,'84569'
                                        ,'37596'
                                        ,'84291'
                                        ,'44843'
                                        ,'31487'
                                        ,'24949'
                                        ,'31269'
                                        ,'62115'
                                        ,'79494'
                                        ,'32194'
                                        ,'62531'
                                        ,'61655'
                                        ,'40724'
                                        ,'29091'
                                        ,'81608'
                                        ,'77126'
                                        ,'32704'
                                        ,'79045'
                                        ,'19008'
                                        ,'81581'
                                        ,'59693'
                                        ,'24689'
                                        ,'79355'
                                        ,'19635'
                                        ,'52025'
                                        ,'83585'
                                        ,'56103'
                                        ,'80150'
                                        ,'26203'
                                        ,'81571'
                                        ,'85657'
                                        ,'39672'
                                        ,'62868'
                                        ,'33498'
                                        ,'69453'
                                        ,'25748'
                                        ,'44145'
                                        ,'35695'
                                        ,'57860'
                                        ,'59532'
                                        ,'76967'
                                        ,'81235'
                                        ,'22004'
                                        ,'34487'
                                        ,'48499'
                                        ,'47318'
                                        ,'63039'
                                        ,'77728'
                                        ,'89774'
                                        ,'91640'
                                        ,'76501'
                                        ,'70137'
                                        ,'37512'
                                        ,'48507'
                                        ,'51980'
                                        ,'34851'
                                        ,'54884'
                                        ,'30905'
                                        ,'12745'
                                        ,'60630'
                                        ,'42798'
                                        ,'39923'
                                        ,'47591'
                                        ,'82518'
                                        ,'32982'
                                        ,'14233'
                                        ,'56444'
                                        ,'79278'
                                        ,'57791'
                                        ,'37395'
                                        ,'93812'
                                        ,'14062'
                                        ,'21556'
                                        ,'58923'
                                        ,'13595'
                                        ,'87261'
                                        ,'79484'
                                        ,'24492'
                                        ,'10389'
                                        ,'89526'
                                        ,'21733'
                                        ,'85078'
                                        ,'35187'
                                        ,'68025'
                                        ,'45624'
                                        ,'25243'
                                        ,'42027'
                                        ,'50749'
                                        ,'13870'
                                        ,'47072'
                                        ,'17847'
                                        ,'46413'
                                        ,'11259'
                                        ,'20221'
                                        ,'32961'
                                        ,'14173'
                                        ,'96788'
                                        ,'77001'
                                        ,'65695'
                                        ,'52542'
                                        ,'39550'
                                        ,'21651'
                                        ,'68063'
                                        ,'48779'
                                        ,'55702'
                                        ,'16612'
                                        ,'15953'
                                        ,'22707'
                                        ,'83997'
                                        ,'61460'
                                        ,'18919'
                                        ,'27616'
                                        ,'55164'
                                        ,'54421'
                                        ,'47268'
                                        ,'66355'
                                        ,'86846'
                                        ,'74968'
                                        ,'95883'
                                        ,'92832'
                                        ,'37009'
                                        ,'66903'
                                        ,'38063'
                                        ,'95421'
                                        ,'45640'
                                        ,'55118'
                                        ,'22721'
                                        ,'54787'
                                        ,'29772'
                                        ,'79121'
                                        ,'85462'
                                        ,'28380'
                                        ,'34680'
                                        ,'85831'
                                        ,'60615'
                                        ,'60763'
                                        ,'87605'
                                        ,'10096'
                                        ,'69252'
                                        ,'28329'
                                        ,'68812'
                                        ,'47734'
                                        ,'36851'
                                        ,'24290'
                                        ,'39067'
                                        ,'32242'
                                        ,'97396'
                                        ,'45999'
                                        ,'37157'
                                        ,'88891'
                                        ,'71571'
                                        ,'17941'
                                        ,'12910'
                                        ,'28800'
                                        ,'47548'
                                        ,'11514'
                                        ,'49224'
                                        ,'50161'
                                        ,'27249'
                                        ,'29522'
                                        ,'50098'
                                        ,'20810'
                                        ,'23683'
                                        ,'51862'
                                        ,'57007'
                                        ,'43224'
                                        ,'98002'
                                        ,'65238'
                                        ,'30719'
                                        ,'15735'
                                        ,'70127'
                                        ,'33927'
                                        ,'96245'
                                        ,'56649'
                                        ,'44640'
                                        ,'34914'
                                        ,'18833'
                                        ,'72797'
                                        ,'18380'
                                        ,'17256'
                                        ,'75124'
                                        ,'36114'
                                        ,'44696'
                                        ,'35472'
                                        ,'76579'
                                        ,'52537'
                                        ,'82424'
                                        ,'44424'
                                        ,'32748'
                                        ,'77516'
                                        ,'17985'
                                        ,'57725'
                                        ,'34893'
                                        ,'42886'
                                        ,'98097'
                                        ,'62869'
                                        ,'24984'
                                        ,'80539'
                                        ,'19716'
                                        ,'87183'
                                        ,'63517'
                                        ,'60342'
                                        ,'42577'
                                        ,'88040'
                                        ,'46167'
                                        ,'75108'
                                        ,'41469'
                                        ,'73674'
                                        ,'13253'
                                        ,'66716'
                                        ,'36315'
                                        ,'16812'
                                        ,'85084'
                                        ,'70345'
                                        ,'16291'
                                        ,'84204'
                                        ,'38177'
                                        ,'41416'
                                        ,'75043'
                                        ,'85969'
                                        ,'52544'
                                        ,'13572'
                                        ,'21899'
                                        ,'22356'
                                        ,'16473'
                                        ,'25488'
                                        ,'46385'
                                        ,'18400'
                                        ,'17159'
                                        ,'74763'
                                        ,'34719'
                                        ,'18588'
                                        ,'39471'
                                        ,'47156'
                                        ,'28837'
                                        ,'84535'
                                        ,'69380'
                                        ,'54019'
                                        ,'57251'
                                        ,'51378'
                                        ,'43170'
                                        ,'51671'
                                        ,'40569'
                                        ,'81767'
                                        ,'59720'
                                        ,'68739'
                                        ,'28324'
                                        ,'24144'
                                        ,'96283'
                                        ,'53359'
                                        ,'11880'
                                        ,'52839'
                                        ,'13744'
                                        ,'21434'
                                        ,'24927'
                                        ,'99581'
                                        ,'87926'
                                        ,'93557'
                                        ,'34275'
                                        ,'12144'
                                        ,'82294'
                                        ,'39717'
                                        ,'28926'
                                        ,'89184'
                                        ,'29862'
                                        ,'38378'
                                        ,'91135'
                                        ,'17811'
                                        ,'57160'
                                        ,'74994'
                                        ,'34074'
                                        ,'51040'
                                        ,'69828'
                                        ,'65826'
                                        ,'84570'
                                        ,'24660'
                                        ,'15444'
                                        ,'62133'
                                        ,'83549'
                                        ,'15555'
                                        ,'80929'
                                        ,'27543'
                                        ,'86821'
                                        ,'98908'
                                        ,'89602'
                                        ,'68316'
                                        ,'69972'
                                        ,'40191'
                                        ,'97204'
                                        ,'42699'
                                        ,'56262'
                                        ,'69604'
                                        ,'44040'
                                        ,'48466'
                                        ,'55692'
                                        ,'14302'
                                        ,'38041'
                                        ,'33734'
                                        ,'47513'
                                        ,'46513'
                                        ,'16039'
                                        ,'81050'
                                        ,'34048'
                                        ,'30741'
                                        ,'18213'
                                        ,'99574'
                                        ,'27215'
                                        ,'60005'
                                        ,'47953'
                                        ,'29145'
                                        ,'14682'
                                        ,'50833'
                                        ,'74174'
                                        ,'86506'
                                        ,'57452'
                                        ,'92971'
                                        ,'70344'
                                        ,'66483'
                                        ,'99501'
                                        ,'78134'
                                        ,'79445'
                                        ,'82179'
                                        ,'44114'
                                        ,'19591'
                                        ,'20096'
                                        ,'85999'
                                        ,'52672'
                                        ,'47030'
                                        ,'74464'
                                        ,'30215'
                                        ,'59015'
                                        ,'42068'
                                        ,'25463'
                                        ,'26536'
                                        ,'53394'
                                        ,'43508'
                                        ,'41140'
                                        ,'29335'
                                        ,'37130'
                                        ,'43967'
                                        ,'22686'
                                        ,'78500'
                                        ,'70281'
                                        ,'20148'
                                        ,'54335'
                                        ,'31575'
                                        ,'79592'
                                        ,'16787'
                                    )
                        )
                        INTERSECT
                        (
                            SELECT
                                    ca_zip
                                FROM
                                    (
                                        SELECT
                                                SUBSTR(ca_zip, 1, 5) ca_zip
                                                ,COUNT(*) cnt
                                            FROM
                                                customer_address
                                                ,customer
                                            WHERE
                                                ca_address_sk = c_current_addr_sk
                                                AND c_preferred_cust_flag = 'Y'
                                            GROUP BY
                                                ca_zip
                                            HAVING
                                                COUNT(*) > 10
                                    ) A1
                        )
                    ) A2
        ) V1
    WHERE
        ss_store_sk = s_store_sk
        AND ss_sold_date_sk = d_date_sk
        AND d_qoy = 1
        AND d_year = 2001
        AND (
            SUBSTR(s_zip, 1, 2) = SUBSTR(V1.ca_zip, 1, 2)
        )
    GROUP BY
        s_store_name
    ORDER BY
        s_store_name;
--end query 8 using template query8.tpl
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
--start query 10 using template query10.tpl
-- @private, @projection(demographics), @condition(customer_address)
SELECT
        top 100 cd_gender
        ,cd_marital_status
        ,cd_education_status
        ,COUNT(*) cnt1
        ,cd_purchase_estimate
        ,COUNT(*) cnt2
        ,cd_credit_rating
        ,COUNT(*) cnt3
        ,cd_dep_count
        ,COUNT(*) cnt4
        ,cd_dep_employed_count
        ,COUNT(*) cnt5
        ,cd_dep_college_count
        ,COUNT(*) cnt6
    FROM
        customer c
        ,customer_address ca
        ,customer_demographics
    WHERE
        c.c_current_addr_sk = ca.ca_address_sk
        AND ca_county IN (
            'Yellowstone County'
            ,'Montgomery County'
            ,'Divide County'
            ,'Cedar County'
            ,'Manassas Park city'
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
                    AND d_year = 2000
                    AND d_moy BETWEEN 2 AND 2+3
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
                        AND d_year = 2000
                        AND d_moy BETWEEN 2 AND 2+3
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
                        AND d_year = 2000
                        AND d_moy BETWEEN 2 AND 2+3
            )
        )
    GROUP BY
        cd_gender
        ,cd_marital_status
        ,cd_education_status
        ,cd_purchase_estimate
        ,cd_credit_rating
        ,cd_dep_count
        ,cd_dep_employed_count
        ,cd_dep_college_count
    ORDER BY
        cd_gender
        ,cd_marital_status
        ,cd_education_status
        ,cd_purchase_estimate
        ,cd_credit_rating
        ,cd_dep_count
        ,cd_dep_employed_count
        ,cd_dep_college_count;
--end query 10 using template query10.tpl
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
--start query 13 using template query13.tpl
-- @private, @condition(demographics)
SELECT
        AVG(ss_quantity)
        ,AVG(ss_ext_sales_price)
        ,AVG(ss_ext_wholesale_cost)
        ,SUM(ss_ext_wholesale_cost)
    FROM
        store_sales
        ,store
        ,customer_demographics
        ,household_demographics
        ,customer_address
        ,date_dim
    WHERE
        s_store_sk = ss_store_sk
        AND ss_sold_date_sk = d_date_sk
        AND d_year = 2001
        AND (
            (
                ss_hdemo_sk = hd_demo_sk
                AND cd_demo_sk = ss_cdemo_sk
                AND cd_marital_status = 'M'
                AND cd_education_status = '2 yr Degree'
                AND ss_sales_price BETWEEN 100.00 AND 150.00
                AND hd_dep_count = 3
            )
            OR (
                ss_hdemo_sk = hd_demo_sk
                AND cd_demo_sk = ss_cdemo_sk
                AND cd_marital_status = 'U'
                AND cd_education_status = '4 yr Degree'
                AND ss_sales_price BETWEEN 50.00 AND 100.00
                AND hd_dep_count = 1
            )
            OR (
                ss_hdemo_sk = hd_demo_sk
                AND cd_demo_sk = ss_cdemo_sk
                AND cd_marital_status = 'D'
                AND cd_education_status = 'Advanced Degree'
                AND ss_sales_price BETWEEN 150.00 AND 200.00
                AND hd_dep_count = 1
            )
        )
        AND (
            (
                ss_addr_sk = ca_address_sk
                AND ca_country = 'United States'
                AND ca_state IN (
                    'ND'
                    ,'IL'
                    ,'AL'
                )
                AND ss_net_profit BETWEEN 100 AND 200
            )
            OR (
                ss_addr_sk = ca_address_sk
                AND ca_country = 'United States'
                AND ca_state IN (
                    'MS'
                    ,'OH'
                    ,'NV'
                )
                AND ss_net_profit BETWEEN 150 AND 300
            )
            OR (
                ss_addr_sk = ca_address_sk
                AND ca_country = 'United States'
                AND ca_state IN (
                    'MN'
                    ,'IA'
                    ,'OK'
                )
                AND ss_net_profit BETWEEN 50 AND 250
            )
        );
--end query 13 using template query13.tpl
--start query 14 using template query14.tpl
WITH cross_items AS (
        SELECT
                i_item_sk ss_item_sk
            FROM
                item
                ,(
                    SELECT
                            iss.i_brand_id brand_id
                            ,iss.i_class_id class_id
                            ,iss.i_category_id category_id
                        FROM
                            store_sales
                            ,item iss
                            ,date_dim d1
                        WHERE
                            ss_item_sk = iss.i_item_sk
                            AND ss_sold_date_sk = d1.d_date_sk
                            AND d1.d_year BETWEEN 1999 AND 1999 + 2
                    INTERSECT
                    SELECT
                            ics.i_brand_id
                            ,ics.i_class_id
                            ,ics.i_category_id
                        FROM
                            catalog_sales
                            ,item ics
                            ,date_dim d2
                        WHERE
                            cs_item_sk = ics.i_item_sk
                            AND cs_sold_date_sk = d2.d_date_sk
                            AND d2.d_year BETWEEN 1999 AND 1999 + 2
                    INTERSECT
                    SELECT
                            iws.i_brand_id
                            ,iws.i_class_id
                            ,iws.i_category_id
                        FROM
                            web_sales
                            ,item iws
                            ,date_dim d3
                        WHERE
                            ws_item_sk = iws.i_item_sk
                            AND ws_sold_date_sk = d3.d_date_sk
                            AND d3.d_year BETWEEN 1999 AND 1999 + 2
                ) x
            WHERE
                i_brand_id = brand_id
                AND i_class_id = class_id
                AND i_category_id = category_id
)
,avg_sales AS (
    SELECT
            AVG(quantity * list_price) average_sales
        FROM
            (
                SELECT
                        ss_quantity quantity
                        ,ss_list_price list_price
                    FROM
                        store_sales
                        ,date_dim
                    WHERE
                        ss_sold_date_sk = d_date_sk
                        AND d_year BETWEEN 1999 AND 2001
                UNION ALL
                SELECT
                        cs_quantity quantity
                        ,cs_list_price list_price
                    FROM
                        catalog_sales
                        ,date_dim
                    WHERE
                        cs_sold_date_sk = d_date_sk
                        AND d_year BETWEEN 2000 AND 2000 + 2
                UNION ALL
                SELECT
                        ws_quantity quantity
                        ,ws_list_price list_price
                    FROM
                        web_sales
                        ,date_dim
                    WHERE
                        ws_sold_date_sk = d_date_sk
                        AND d_year BETWEEN 2000 AND 2000 + 2
            ) x
) SELECT
        top 100 channel
        ,i_brand_id
        ,i_class_id
        ,i_category_id
        ,SUM(sales)
        ,SUM(number_sales)
    FROM
        (
            SELECT
                    'store' channel
                    ,i_brand_id
                    ,i_class_id
                    ,i_category_id
                    ,SUM(ss_quantity * ss_list_price) sales
                    ,COUNT(*) number_sales
                FROM
                    store_sales
                    ,item
                    ,date_dim
                WHERE
                    ss_item_sk IN (
                        SELECT
                                ss_item_sk
                            FROM
                                cross_items
                    )
                    AND ss_item_sk = i_item_sk
                    AND ss_sold_date_sk = d_date_sk
                    AND d_year = 2000+2
                    AND d_moy = 11
                GROUP BY
                    i_brand_id
                    ,i_class_id
                    ,i_category_id
                HAVING
                    SUM(ss_quantity * ss_list_price) > (
                        SELECT
                                average_sales
                            FROM
                                avg_sales
                    )
            UNION ALL
            SELECT
                    'catalog' channel
                    ,i_brand_id
                    ,i_class_id
                    ,i_category_id
                    ,SUM(cs_quantity * cs_list_price) sales
                    ,COUNT(*) number_sales
                FROM
                    catalog_sales
                    ,item
                    ,date_dim
                WHERE
                    cs_item_sk IN (
                        SELECT
                                ss_item_sk
                            FROM
                                cross_items
                    )
                    AND cs_item_sk = i_item_sk
                    AND cs_sold_date_sk = d_date_sk
                    AND d_year = 2000+2
                    AND d_moy = 11
                GROUP BY
                    i_brand_id
                    ,i_class_id
                    ,i_category_id
                HAVING
                    SUM(cs_quantity * cs_list_price) > (
                        SELECT
                                average_sales
                            FROM
                                avg_sales
                    )
            UNION ALL
            SELECT
                    'web' channel
                    ,i_brand_id
                    ,i_class_id
                    ,i_category_id
                    ,SUM(ws_quantity * ws_list_price) sales
                    ,COUNT(*) number_sales
                FROM
                    web_sales
                    ,item
                    ,date_dim
                WHERE
                    ws_item_sk IN (
                        SELECT
                                ss_item_sk
                            FROM
                                cross_items
                    )
                    AND ws_item_sk = i_item_sk
                    AND ws_sold_date_sk = d_date_sk
                    AND d_year = 2000+2
                    AND d_moy = 11
                GROUP BY
                    i_brand_id
                    ,i_class_id
                    ,i_category_id
                HAVING
                    SUM(ws_quantity * ws_list_price) > (
                        SELECT
                                average_sales
                            FROM
                                avg_sales
                    )
        ) y
    GROUP BY
        rollup(
            channel
            ,i_brand_id
            ,i_class_id
            ,i_category_id
        )
    ORDER BY
        channel
        ,i_brand_id
        ,i_class_id
        ,i_category_id;
WITH cross_items AS (
        SELECT
                i_item_sk ss_item_sk
            FROM
                item
                ,(
                    SELECT
                            iss.i_brand_id brand_id
                            ,iss.i_class_id class_id
                            ,iss.i_category_id category_id
                        FROM
                            store_sales
                            ,item iss
                            ,date_dim d1
                        WHERE
                            ss_item_sk = iss.i_item_sk
                            AND ss_sold_date_sk = d1.d_date_sk
                            AND d1.d_year BETWEEN 1999 AND 1999 + 2
                    INTERSECT
                    SELECT
                            ics.i_brand_id
                            ,ics.i_class_id
                            ,ics.i_category_id
                        FROM
                            catalog_sales
                            ,item ics
                            ,date_dim d2
                        WHERE
                            cs_item_sk = ics.i_item_sk
                            AND cs_sold_date_sk = d2.d_date_sk
                            AND d2.d_year BETWEEN 1999 AND 1999 + 2
                    INTERSECT
                    SELECT
                            iws.i_brand_id
                            ,iws.i_class_id
                            ,iws.i_category_id
                        FROM
                            web_sales
                            ,item iws
                            ,date_dim d3
                        WHERE
                            ws_item_sk = iws.i_item_sk
                            AND ws_sold_date_sk = d3.d_date_sk
                            AND d3.d_year BETWEEN 1999 AND 1999 + 2
                ) x
            WHERE
                i_brand_id = brand_id
                AND i_class_id = class_id
                AND i_category_id = category_id
)
,avg_sales AS (
    SELECT
            AVG(quantity * list_price) average_sales
        FROM
            (
                SELECT
                        ss_quantity quantity
                        ,ss_list_price list_price
                    FROM
                        store_sales
                        ,date_dim
                    WHERE
                        ss_sold_date_sk = d_date_sk
                        AND d_year BETWEEN 2000 AND 2000 + 2
                UNION ALL
                SELECT
                        cs_quantity quantity
                        ,cs_list_price list_price
                    FROM
                        catalog_sales
                        ,date_dim
                    WHERE
                        cs_sold_date_sk = d_date_sk
                        AND d_year BETWEEN 2000 AND 2000 + 2
                UNION ALL
                SELECT
                        ws_quantity quantity
                        ,ws_list_price list_price
                    FROM
                        web_sales
                        ,date_dim
                    WHERE
                        ws_sold_date_sk = d_date_sk
                        AND d_year BETWEEN 2000 AND 2000 + 2
            ) x
) SELECT
        top 100 *
    FROM
        (
            SELECT
                    'store' channel
                    ,i_brand_id
                    ,i_class_id
                    ,i_category_id
                    ,SUM(ss_quantity * ss_list_price) sales
                    ,COUNT(*) number_sales
                FROM
                    store_sales
                    ,item
                    ,date_dim
                WHERE
                    ss_item_sk IN (
                        SELECT
                                ss_item_sk
                            FROM
                                cross_items
                    )
                    AND ss_item_sk = i_item_sk
                    AND ss_sold_date_sk = d_date_sk
                    AND d_week_seq = (
                        SELECT
                                d_week_seq
                            FROM
                                date_dim
                            WHERE
                                d_year = 2000 + 1
                                AND d_moy = 12
                                AND d_dom = 16
                    )
                GROUP BY
                    i_brand_id
                    ,i_class_id
                    ,i_category_id
                HAVING
                    SUM(ss_quantity * ss_list_price) > (
                        SELECT
                                average_sales
                            FROM
                                avg_sales
                    )
        ) this_year
        ,(
            SELECT
                    'store' channel
                    ,i_brand_id
                    ,i_class_id
                    ,i_category_id
                    ,SUM(ss_quantity * ss_list_price) sales
                    ,COUNT(*) number_sales
                FROM
                    store_sales
                    ,item
                    ,date_dim
                WHERE
                    ss_item_sk IN (
                        SELECT
                                ss_item_sk
                            FROM
                                cross_items
                    )
                    AND ss_item_sk = i_item_sk
                    AND ss_sold_date_sk = d_date_sk
                    AND d_week_seq = (
                        SELECT
                                d_week_seq
                            FROM
                                date_dim
                            WHERE
                                d_year = 2000
                                AND d_moy = 12
                                AND d_dom = 16
                    )
                GROUP BY
                    i_brand_id
                    ,i_class_id
                    ,i_category_id
                HAVING
                    SUM(ss_quantity * ss_list_price) > (
                        SELECT
                                average_sales
                            FROM
                                avg_sales
                    )
        ) last_year
    WHERE
        this_year.i_brand_id = last_year.i_brand_id
        AND this_year.i_class_id = last_year.i_class_id
        AND this_year.i_category_id = last_year.i_category_id
    ORDER BY
        this_year.channel
        ,this_year.i_brand_id
        ,this_year.i_class_id
        ,this_year.i_category_id;
--end query 14 using template query14.tpl
--start query 15 using template query15.tpl
-- @private, @project(customer_address), @condition(customer_address)
SELECT
        top 100 ca_zip
        ,SUM(cs_sales_price)
    FROM
        catalog_sales
        ,customer
        ,customer_address
        ,date_dim
    WHERE
        cs_bill_customer_sk = c_customer_sk
        AND c_current_addr_sk = ca_address_sk
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
            OR ca_state IN (
                'CA'
                ,'WA'
                ,'GA'
            )
            OR cs_sales_price > 500
        )
        AND cs_sold_date_sk = d_date_sk
        AND d_qoy = 2
        AND d_year = 1998
    GROUP BY
        ca_zip
    ORDER BY
        ca_zip;
--end query 15 using template query15.tpl
--start query 16 using template query16.tpl
-- @private, @condition(customer_address)
SELECT
        top 100 COUNT(DISTINCT cs_order_number) AS "order count"
        ,SUM(cs_ext_ship_cost) AS "total shipping cost"
        ,SUM(cs_net_profit) AS "total net profit"
    FROM
        catalog_sales cs1
        ,date_dim
        ,customer_address
        ,call_center
    WHERE
        d_date BETWEEN '2000-3-01' AND (
            cast(
                '2000-3-01' AS DATE
            ) + 60 days
        )
        AND cs1.cs_ship_date_sk = d_date_sk
        AND cs1.cs_ship_addr_sk = ca_address_sk
        AND ca_state = 'MN'
        AND cs1.cs_call_center_sk = cc_call_center_sk
        AND cc_county IN (
            'Williamson County'
            ,'Williamson County'
            ,'Williamson County'
            ,'Williamson County'
            ,'Williamson County'
        )
        AND EXISTS (
            SELECT
                    *
                FROM
                    catalog_sales cs2
                WHERE
                    cs1.cs_order_number = cs2.cs_order_number
                    AND cs1.cs_warehouse_sk <> cs2.cs_warehouse_sk
        )
        AND NOT EXISTS (
            SELECT
                    *
                FROM
                    catalog_returns cr1
                WHERE
                    cs1.cs_order_number = cr1.cr_order_number
        )
    ORDER BY
        COUNT(DISTINCT cs_order_number);
--end query 16 using template query16.tpl
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
--start query 18 using template query18.tpl
-- @private, @projection(customer_address, customer), @condition(demographics)
SELECT
        top 100 i_item_id
        ,ca_country
        ,ca_state
        ,ca_county
        ,AVG(cast(
            cs_quantity AS NUMERIC(12, 2)
        )) agg1
        ,AVG(cast(
            cs_list_price AS NUMERIC(12, 2)
        )) agg2
        ,AVG(cast(
            cs_coupon_amt AS NUMERIC(12, 2)
        )) agg3
        ,AVG(cast(
            cs_sales_price AS NUMERIC(12, 2)
        )) agg4
        ,AVG(cast(
            cs_net_profit AS NUMERIC(12, 2)
        )) agg5
        ,AVG(cast(
            c_birth_year AS NUMERIC(12, 2)
        )) agg6
        ,AVG(cast(
            cd1.cd_dep_count AS NUMERIC(12, 2)
        )) agg7
    FROM
        catalog_sales
        ,customer_demographics cd1
        ,customer_demographics cd2
        ,customer
        ,customer_address
        ,date_dim
        ,item
    WHERE
        cs_sold_date_sk = d_date_sk
        AND cs_item_sk = i_item_sk
        AND cs_bill_cdemo_sk = cd1.cd_demo_sk
        AND cs_bill_customer_sk = c_customer_sk
        AND cd1.cd_gender = 'F'
        AND cd1.cd_education_status = '4 yr Degree'
        AND c_current_cdemo_sk = cd2.cd_demo_sk
        AND c_current_addr_sk = ca_address_sk
        AND c_birth_month IN (
            6
            ,5
            ,12
            ,4
            ,3
            ,7
        )
        AND d_year = 2001
        AND ca_state IN (
            'TN'
            ,'IL'
            ,'GA'
            ,'MO'
            ,'CO'
            ,'OH'
            ,'NM'
        )
    GROUP BY
        rollup(
            i_item_id
            ,ca_country
            ,ca_state
            ,ca_county
        )
    ORDER BY
        ca_country
        ,ca_state
        ,ca_county
        ,i_item_id;
--end query 18 using template query18.tpl
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
--end query 80 using template query80.tpl--start query 81 using template query81.tpl
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
        top 100 c_customer_id AS customer_id
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
