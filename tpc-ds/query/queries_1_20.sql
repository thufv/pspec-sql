--start query 1 using template query1.tpl
 -- public
 WITH customer_total_return AS(
    SELECT
        sr_customer_sk AS ctr_customer_sk ,
        sr_store_sk AS ctr_store_sk ,
        SUM(SR_REVERSED_CHARGE) AS ctr_total_return
    FROM
        store_returns ,
        date_dim
    WHERE
        sr_returned_date_sk = d_date_sk
        AND d_year = 1998
    GROUP BY
        sr_customer_sk ,
        sr_store_sk
) SELECT
    top 100 c_customer_id
FROM
    customer_total_return ctr1 ,
    store ,
    customer
WHERE
    ctr1.ctr_total_return >(
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
    c_customer_id
; --end query 1 using template query1.tpl
 --start query 2 using template query2.tpl
 --@public
 WITH wscs AS(
    SELECT
        sold_date_sk ,
        sales_price
    FROM
        (
            SELECT
                ws_sold_date_sk sold_date_sk ,
                ws_ext_sales_price sales_price
            FROM
                web_sales
        ) x
UNION ALL(
        SELECT
            cs_sold_date_sk sold_date_sk ,
            cs_ext_sales_price sales_price
        FROM
            catalog_sales
    )
) ,
wswscs AS(
    SELECT
        d_week_seq ,
        SUM(
            CASE
                WHEN(
                    d_day_name = 'Sunday'
                )
                THEN sales_price
                ELSE NULL
            END
        ) sun_sales ,
        SUM(
            CASE
                WHEN(
                    d_day_name = 'Monday'
                )
                THEN sales_price
                ELSE NULL
            END
        ) mon_sales ,
        SUM(
            CASE
                WHEN(
                    d_day_name = 'Tuesday'
                )
                THEN sales_price
                ELSE NULL
            END
        ) tue_sales ,
        SUM(
            CASE
                WHEN(
                    d_day_name = 'Wednesday'
                )
                THEN sales_price
                ELSE NULL
            END
        ) wed_sales ,
        SUM(
            CASE
                WHEN(
                    d_day_name = 'Thursday'
                )
                THEN sales_price
                ELSE NULL
            END
        ) thu_sales ,
        SUM(
            CASE
                WHEN(
                    d_day_name = 'Friday'
                )
                THEN sales_price
                ELSE NULL
            END
        ) fri_sales ,
        SUM(
            CASE
                WHEN(
                    d_day_name = 'Saturday'
                )
                THEN sales_price
                ELSE NULL
            END
        ) sat_sales
    FROM
        wscs ,
        date_dim
    WHERE
        d_date_sk = sold_date_sk
    GROUP BY
        d_week_seq
) SELECT
    d_week_seq1 ,
    ROUND( sun_sales1 / sun_sales2 ,2 ) ,
    ROUND( mon_sales1 / mon_sales2 ,2 ) ,
    ROUND( tue_sales1 / tue_sales2 ,2 ) ,
    ROUND( wed_sales1 / wed_sales2 ,2 ) ,
    ROUND( thu_sales1 / thu_sales2 ,2 ) ,
    ROUND( fri_sales1 / fri_sales2 ,2 ) ,
    ROUND( sat_sales1 / sat_sales2 ,2 )
FROM
    (
        SELECT
            wswscs.d_week_seq d_week_seq1 ,
            sun_sales sun_sales1 ,
            mon_sales mon_sales1 ,
            tue_sales tue_sales1 ,
            wed_sales wed_sales1 ,
            thu_sales thu_sales1 ,
            fri_sales fri_sales1 ,
            sat_sales sat_sales1
        FROM
            wswscs ,
            date_dim
        WHERE
            date_dim.d_week_seq = wswscs.d_week_seq
            AND d_year = 2001
    ) y ,
    (
        SELECT
            wswscs.d_week_seq d_week_seq2 ,
            sun_sales sun_sales2 ,
            mon_sales mon_sales2 ,
            tue_sales tue_sales2 ,
            wed_sales wed_sales2 ,
            thu_sales thu_sales2 ,
            fri_sales fri_sales2 ,
            sat_sales sat_sales2
        FROM
            wswscs ,
            date_dim
        WHERE
            date_dim.d_week_seq = wswscs.d_week_seq
            AND d_year = 2001 + 1
    ) z
WHERE
    d_week_seq1 = d_week_seq2 - 53
ORDER BY
    d_week_seq1
; --end query 2 using template query2.tpl
 --start query 3 using template query3.tpl
 -- @public
 SELECT
    dt.d_year ,
    item.i_brand_id brand_id ,
    item.i_brand brand ,
    SUM(ss_ext_discount_amt) sum_agg
FROM
    date_dim dt ,
    store_sales ,
    item
WHERE
    dt.d_date_sk = store_sales.ss_sold_date_sk
    AND store_sales.ss_item_sk = item.i_item_sk
    AND item.i_manufact_id = 783
    AND dt.d_moy = 11
GROUP BY
    dt.d_year ,
    item.i_brand ,
    item.i_brand_id
ORDER BY
    dt.d_year ,
    sum_agg DESC ,
    brand_id
; --end query 3 using template query3.tpl
 --start query 4 using template query4.tpl
 -- @private, @condition(customer)
 WITH year_total AS(
    SELECT
        c_customer_id customer_id ,
        c_first_name customer_first_name ,
        c_last_name customer_last_name ,
        c_preferred_cust_flag customer_preferred_cust_flag ,
        c_birth_country customer_birth_country ,
        c_login customer_login ,
        c_email_address customer_email_address ,
        d_year dyear ,
        SUM(
            (
                (
                    ss_ext_list_price - ss_ext_wholesale_cost - ss_ext_discount_amt
                ) + ss_ext_sales_price
            ) / 2
        ) year_total ,
        's' sale_type
    FROM
        customer ,
        store_sales ,
        date_dim
    WHERE
        c_customer_sk = ss_customer_sk
        AND ss_sold_date_sk = d_date_sk
    GROUP BY
        c_customer_id ,
        c_first_name ,
        c_last_name ,
        c_preferred_cust_flag ,
        c_birth_country ,
        c_login ,
        c_email_address ,
        d_year
UNION ALL SELECT
        c_customer_id customer_id ,
        c_first_name customer_first_name ,
        c_last_name customer_last_name ,
        c_preferred_cust_flag customer_preferred_cust_flag ,
        c_birth_country customer_birth_country ,
        c_login customer_login ,
        c_email_address customer_email_address ,
        d_year dyear ,
        SUM(
            (
                (
                    (
                        cs_ext_list_price - cs_ext_wholesale_cost - cs_ext_discount_amt
                    ) + cs_ext_sales_price
                ) / 2
            )
        ) year_total ,
        'c' sale_type
    FROM
        customer ,
        catalog_sales ,
        date_dim
    WHERE
        c_customer_sk = cs_bill_customer_sk
        AND cs_sold_date_sk = d_date_sk
    GROUP BY
        c_customer_id ,
        c_first_name ,
        c_last_name ,
        c_preferred_cust_flag ,
        c_birth_country ,
        c_login ,
        c_email_address ,
        d_year
UNION ALL SELECT
        c_customer_id customer_id ,
        c_first_name customer_first_name ,
        c_last_name customer_last_name ,
        c_preferred_cust_flag customer_preferred_cust_flag ,
        c_birth_country customer_birth_country ,
        c_login customer_login ,
        c_email_address customer_email_address ,
        d_year dyear ,
        SUM(
            (
                (
                    (
                        ws_ext_list_price - ws_ext_wholesale_cost - ws_ext_discount_amt
                    ) + ws_ext_sales_price
                ) / 2
            )
        ) year_total ,
        'w' sale_type
    FROM
        customer ,
        web_sales ,
        date_dim
    WHERE
        c_customer_sk = ws_bill_customer_sk
        AND ws_sold_date_sk = d_date_sk
    GROUP BY
        c_customer_id ,
        c_first_name ,
        c_last_name ,
        c_preferred_cust_flag ,
        c_birth_country ,
        c_login ,
        c_email_address ,
        d_year
) SELECT
    top 100 t_s_secyear.customer_login
FROM
    year_total t_s_firstyear ,
    year_total t_s_secyear ,
    year_total t_c_firstyear ,
    year_total t_c_secyear ,
    year_total t_w_firstyear ,
    year_total t_w_secyear
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
    AND t_s_secyear.dyear = 1999 + 1
    AND t_c_firstyear.dyear = 1999
    AND t_c_secyear.dyear = 1999 + 1
    AND t_w_firstyear.dyear = 1999
    AND t_w_secyear.dyear = 1999 + 1
    AND t_s_firstyear.year_total > 0
    AND t_c_firstyear.year_total > 0
    AND t_w_firstyear.year_total > 0
    AND CASE
        WHEN t_c_firstyear.year_total > 0
        THEN t_c_secyear.year_total / t_c_firstyear.year_total
        ELSE NULL
    END > CASE
        WHEN t_s_firstyear.year_total > 0
        THEN t_s_secyear.year_total / t_s_firstyear.year_total
        ELSE NULL
    END
    AND CASE
        WHEN t_c_firstyear.year_total > 0
        THEN t_c_secyear.year_total / t_c_firstyear.year_total
        ELSE NULL
    END > CASE
        WHEN t_w_firstyear.year_total > 0
        THEN t_w_secyear.year_total / t_w_firstyear.year_total
        ELSE NULL
    END
ORDER BY
    t_s_secyear.customer_login
; --end query 4 using template query4.tpl
 --start query 5 using template query5.tpl
 --@public
 WITH ssr AS(
    SELECT
        s_store_id ,
        SUM(sales_price) AS sales ,
        SUM(profit) AS profit ,
        SUM(return_amt) AS returns ,
        SUM(net_loss) AS profit_loss
    FROM
        (
            SELECT
                ss_store_sk AS store_sk ,
                ss_sold_date_sk AS date_sk ,
                ss_ext_sales_price AS sales_price ,
                ss_net_profit AS profit ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS return_amt ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS net_loss
            FROM
                store_sales
        UNION ALL SELECT
                sr_store_sk AS store_sk ,
                sr_returned_date_sk AS date_sk ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS sales_price ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS profit ,
                sr_return_amt AS return_amt ,
                sr_net_loss AS net_loss
            FROM
                store_returns
        ) salesreturns ,
        date_dim ,
        store
    WHERE
        date_sk = d_date_sk
        AND d_date BETWEEN CAST(
            '2001-08-21' AS DATE
        ) AND(
            CAST(
                '2001-08-21' AS DATE
            ) + 14 days
        )
        AND store_sk = s_store_sk
    GROUP BY
        s_store_id
) ,
csr AS(
    SELECT
        cp_catalog_page_id ,
        SUM(sales_price) AS sales ,
        SUM(profit) AS profit ,
        SUM(return_amt) AS returns ,
        SUM(net_loss) AS profit_loss
    FROM
        (
            SELECT
                cs_catalog_page_sk AS page_sk ,
                cs_sold_date_sk AS date_sk ,
                cs_ext_sales_price AS sales_price ,
                cs_net_profit AS profit ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS return_amt ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS net_loss
            FROM
                catalog_sales
        UNION ALL SELECT
                cr_catalog_page_sk AS page_sk ,
                cr_returned_date_sk AS date_sk ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS sales_price ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS profit ,
                cr_return_amount AS return_amt ,
                cr_net_loss AS net_loss
            FROM
                catalog_returns
        ) salesreturns ,
        date_dim ,
        catalog_page
    WHERE
        date_sk = d_date_sk
        AND d_date BETWEEN CAST(
            '2001-08-21' AS DATE
        ) AND(
            CAST(
                '2001-08-21' AS DATE
            ) + 14 days
        )
        AND page_sk = cp_catalog_page_sk
    GROUP BY
        cp_catalog_page_id
) ,
wsr AS(
    SELECT
        web_site_id ,
        SUM(sales_price) AS sales ,
        SUM(profit) AS profit ,
        SUM(return_amt) AS returns ,
        SUM(net_loss) AS profit_loss
    FROM
        (
            SELECT
                ws_web_site_sk AS wsr_web_site_sk ,
                ws_sold_date_sk AS date_sk ,
                ws_ext_sales_price AS sales_price ,
                ws_net_profit AS profit ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS return_amt ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS net_loss
            FROM
                web_sales
        UNION ALL SELECT
                ws_web_site_sk AS wsr_web_site_sk ,
                wr_returned_date_sk AS date_sk ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS sales_price ,
                CAST(
                    0 AS DECIMAL(
                        7 ,
                        2
                    )
                ) AS profit ,
                wr_return_amt AS return_amt ,
                wr_net_loss AS net_loss
            FROM
                web_returns LEFT OUTER JOIN web_sales
                    ON(
                    wr_item_sk = ws_item_sk
                    AND wr_order_number = ws_order_number
                )
        ) salesreturns ,
        date_dim ,
        web_site
    WHERE
        date_sk = d_date_sk
        AND d_date BETWEEN CAST(
            '2001-08-21' AS DATE
        ) AND(
            CAST(
                '2001-08-21' AS DATE
            ) + 14 days
        )
        AND wsr_web_site_sk = web_site_sk
    GROUP BY
        web_site_id
) SELECT
    top 100 channel ,
    id ,
    SUM(sales) AS sales ,
    SUM(returns) AS returns ,
    SUM(profit) AS profit
FROM
    (
        SELECT
            'store channel' AS channel ,
            'store' || s_store_id AS id ,
            sales ,
            returns ,
            (
                profit - profit_loss
            ) AS profit
        FROM
            ssr
    UNION ALL SELECT
            'catalog channel' AS channel ,
            'catalog_page' || cp_catalog_page_id AS id ,
            sales ,
            returns ,
            (
                profit - profit_loss
            ) AS profit
        FROM
            csr
    UNION ALL SELECT
            'web channel' AS channel ,
            'web_site' || web_site_id AS id ,
            sales ,
            returns ,
            (
                profit - profit_loss
            ) AS profit
        FROM
            wsr
    ) x
GROUP BY
    rollup(
        channel ,
        id
    )
ORDER BY
    channel ,
    id
; --end query 5 using template query5.tpl
 --start query 6 using template query6.tpl
 -- @private, @projection(customer_address)
 SELECT
    a.ca_state state ,
    COUNT(*) cnt
FROM
    customer_address a ,
    customer c ,
    store_sales s ,
    date_dim d ,
    item i
WHERE
    a.ca_address_sk = c.c_current_addr_sk
    AND c.c_customer_sk = s.ss_customer_sk
    AND s.ss_sold_date_sk = d.d_date_sk
    AND s.ss_item_sk = i.i_item_sk
    AND d.d_month_seq =(
        SELECT
            DISTINCT(d_month_seq)
        FROM
            date_dim
        WHERE
            d_year = 1998
            AND d_moy = 5
    )
    AND i.i_current_price > 1.2 *(
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
    cnt
; --end query 6 using template query6.tpl
 --start query 7 using template query7.tpl
 -- @private, @condition(demographics)
 SELECT
    i_item_id ,
    AVG(ss_quantity) agg1 ,
    AVG(ss_list_price) agg2 ,
    AVG(ss_coupon_amt) agg3 ,
    AVG(ss_sales_price) agg4
FROM
    store_sales ,
    customer_demographics ,
    date_dim ,
    item ,
    promotion
WHERE
    ss_sold_date_sk = d_date_sk
    AND ss_item_sk = i_item_sk
    AND ss_cdemo_sk = cd_demo_sk
    AND ss_promo_sk = p_promo_sk
    AND cd_gender = 'M'
    AND cd_marital_status = 'M'
    AND cd_education_status = '4 yr Degree'
    AND(
        p_channel_email = 'N'
        OR p_channel_event = 'N'
    )
    AND d_year = 2001
GROUP BY
    i_item_id
ORDER BY
    i_item_id
; --end query 7 using template query7.tpl
 --start query 8 using template query8.tpl
 --@private, @condition(customer_address)
 SELECT
    top 100 s_store_name ,
    SUM(ss_net_profit)
FROM
    store_sales ,
    date_dim ,
    store ,
    (
        SELECT
            ca_zip
        FROM
            (
                (
                    SELECT
                        SUBSTR(
                            ca_zip ,
                            1 ,
                            5
                        ) ca_zip
                    FROM
                        customer_address
                    WHERE
                        SUBSTR(
                            ca_zip ,
                            1 ,
                            5
                        ) IN(
                            '16733' ,
                            '50732' ,
                            '51878' ,
                            '16933' ,
                            '33177' ,
                            '55974' ,
                            '21338' ,
                            '90455' ,
                            '63106' ,
                            '78712' ,
                            '45114' ,
                            '51090' ,
                            '44881' ,
                            '35526' ,
                            '91360' ,
                            '34986' ,
                            '31893' ,
                            '28853' ,
                            '84061' ,
                            '25483' ,
                            '84541' ,
                            '39275' ,
                            '56211' ,
                            '51199' ,
                            '85189' ,
                            '24292' ,
                            '27477' ,
                            '46388' ,
                            '77218' ,
                            '21137' ,
                            '43660' ,
                            '36509' ,
                            '77925' ,
                            '11691' ,
                            '26790' ,
                            '35256' ,
                            '59221' ,
                            '42491' ,
                            '39214' ,
                            '35273' ,
                            '27293' ,
                            '74258' ,
                            '68798' ,
                            '50936' ,
                            '19136' ,
                            '25240' ,
                            '89163' ,
                            '21667' ,
                            '30941' ,
                            '61680' ,
                            '10425' ,
                            '96787' ,
                            '84569' ,
                            '37596' ,
                            '84291' ,
                            '44843' ,
                            '31487' ,
                            '24949' ,
                            '31269' ,
                            '62115' ,
                            '79494' ,
                            '32194' ,
                            '62531' ,
                            '61655' ,
                            '40724' ,
                            '29091' ,
                            '81608' ,
                            '77126' ,
                            '32704' ,
                            '79045' ,
                            '19008' ,
                            '81581' ,
                            '59693' ,
                            '24689' ,
                            '79355' ,
                            '19635' ,
                            '52025' ,
                            '83585' ,
                            '56103' ,
                            '80150' ,
                            '26203' ,
                            '81571' ,
                            '85657' ,
                            '39672' ,
                            '62868' ,
                            '33498' ,
                            '69453' ,
                            '25748' ,
                            '44145' ,
                            '35695' ,
                            '57860' ,
                            '59532' ,
                            '76967' ,
                            '81235' ,
                            '22004' ,
                            '34487' ,
                            '48499' ,
                            '47318' ,
                            '63039' ,
                            '77728' ,
                            '89774' ,
                            '91640' ,
                            '76501' ,
                            '70137' ,
                            '37512' ,
                            '48507' ,
                            '51980' ,
                            '34851' ,
                            '54884' ,
                            '30905' ,
                            '12745' ,
                            '60630' ,
                            '42798' ,
                            '39923' ,
                            '47591' ,
                            '82518' ,
                            '32982' ,
                            '14233' ,
                            '56444' ,
                            '79278' ,
                            '57791' ,
                            '37395' ,
                            '93812' ,
                            '14062' ,
                            '21556' ,
                            '58923' ,
                            '13595' ,
                            '87261' ,
                            '79484' ,
                            '24492' ,
                            '10389' ,
                            '89526' ,
                            '21733' ,
                            '85078' ,
                            '35187' ,
                            '68025' ,
                            '45624' ,
                            '25243' ,
                            '42027' ,
                            '50749' ,
                            '13870' ,
                            '47072' ,
                            '17847' ,
                            '46413' ,
                            '11259' ,
                            '20221' ,
                            '32961' ,
                            '14173' ,
                            '96788' ,
                            '77001' ,
                            '65695' ,
                            '52542' ,
                            '39550' ,
                            '21651' ,
                            '68063' ,
                            '48779' ,
                            '55702' ,
                            '16612' ,
                            '15953' ,
                            '22707' ,
                            '83997' ,
                            '61460' ,
                            '18919' ,
                            '27616' ,
                            '55164' ,
                            '54421' ,
                            '47268' ,
                            '66355' ,
                            '86846' ,
                            '74968' ,
                            '95883' ,
                            '92832' ,
                            '37009' ,
                            '66903' ,
                            '38063' ,
                            '95421' ,
                            '45640' ,
                            '55118' ,
                            '22721' ,
                            '54787' ,
                            '29772' ,
                            '79121' ,
                            '85462' ,
                            '28380' ,
                            '34680' ,
                            '85831' ,
                            '60615' ,
                            '60763' ,
                            '87605' ,
                            '10096' ,
                            '69252' ,
                            '28329' ,
                            '68812' ,
                            '47734' ,
                            '36851' ,
                            '24290' ,
                            '39067' ,
                            '32242' ,
                            '97396' ,
                            '45999' ,
                            '37157' ,
                            '88891' ,
                            '71571' ,
                            '17941' ,
                            '12910' ,
                            '28800' ,
                            '47548' ,
                            '11514' ,
                            '49224' ,
                            '50161' ,
                            '27249' ,
                            '29522' ,
                            '50098' ,
                            '20810' ,
                            '23683' ,
                            '51862' ,
                            '57007' ,
                            '43224' ,
                            '98002' ,
                            '65238' ,
                            '30719' ,
                            '15735' ,
                            '70127' ,
                            '33927' ,
                            '96245' ,
                            '56649' ,
                            '44640' ,
                            '34914' ,
                            '18833' ,
                            '72797' ,
                            '18380' ,
                            '17256' ,
                            '75124' ,
                            '36114' ,
                            '44696' ,
                            '35472' ,
                            '76579' ,
                            '52537' ,
                            '82424' ,
                            '44424' ,
                            '32748' ,
                            '77516' ,
                            '17985' ,
                            '57725' ,
                            '34893' ,
                            '42886' ,
                            '98097' ,
                            '62869' ,
                            '24984' ,
                            '80539' ,
                            '19716' ,
                            '87183' ,
                            '63517' ,
                            '60342' ,
                            '42577' ,
                            '88040' ,
                            '46167' ,
                            '75108' ,
                            '41469' ,
                            '73674' ,
                            '13253' ,
                            '66716' ,
                            '36315' ,
                            '16812' ,
                            '85084' ,
                            '70345' ,
                            '16291' ,
                            '84204' ,
                            '38177' ,
                            '41416' ,
                            '75043' ,
                            '85969' ,
                            '52544' ,
                            '13572' ,
                            '21899' ,
                            '22356' ,
                            '16473' ,
                            '25488' ,
                            '46385' ,
                            '18400' ,
                            '17159' ,
                            '74763' ,
                            '34719' ,
                            '18588' ,
                            '39471' ,
                            '47156' ,
                            '28837' ,
                            '84535' ,
                            '69380' ,
                            '54019' ,
                            '57251' ,
                            '51378' ,
                            '43170' ,
                            '51671' ,
                            '40569' ,
                            '81767' ,
                            '59720' ,
                            '68739' ,
                            '28324' ,
                            '24144' ,
                            '96283' ,
                            '53359' ,
                            '11880' ,
                            '52839' ,
                            '13744' ,
                            '21434' ,
                            '24927' ,
                            '99581' ,
                            '87926' ,
                            '93557' ,
                            '34275' ,
                            '12144' ,
                            '82294' ,
                            '39717' ,
                            '28926' ,
                            '89184' ,
                            '29862' ,
                            '38378' ,
                            '91135' ,
                            '17811' ,
                            '57160' ,
                            '74994' ,
                            '34074' ,
                            '51040' ,
                            '69828' ,
                            '65826' ,
                            '84570' ,
                            '24660' ,
                            '15444' ,
                            '62133' ,
                            '83549' ,
                            '15555' ,
                            '80929' ,
                            '27543' ,
                            '86821' ,
                            '98908' ,
                            '89602' ,
                            '68316' ,
                            '69972' ,
                            '40191' ,
                            '97204' ,
                            '42699' ,
                            '56262' ,
                            '69604' ,
                            '44040' ,
                            '48466' ,
                            '55692' ,
                            '14302' ,
                            '38041' ,
                            '33734' ,
                            '47513' ,
                            '46513' ,
                            '16039' ,
                            '81050' ,
                            '34048' ,
                            '30741' ,
                            '18213' ,
                            '99574' ,
                            '27215' ,
                            '60005' ,
                            '47953' ,
                            '29145' ,
                            '14682' ,
                            '50833' ,
                            '74174' ,
                            '86506' ,
                            '57452' ,
                            '92971' ,
                            '70344' ,
                            '66483' ,
                            '99501' ,
                            '78134' ,
                            '79445' ,
                            '82179' ,
                            '44114' ,
                            '19591' ,
                            '20096' ,
                            '85999' ,
                            '52672' ,
                            '47030' ,
                            '74464' ,
                            '30215' ,
                            '59015' ,
                            '42068' ,
                            '25463' ,
                            '26536' ,
                            '53394' ,
                            '43508' ,
                            '41140' ,
                            '29335' ,
                            '37130' ,
                            '43967' ,
                            '22686' ,
                            '78500' ,
                            '70281' ,
                            '20148' ,
                            '54335' ,
                            '31575' ,
                            '79592' ,
                            '16787'
                        )
                )
        INTERSECT(
                SELECT
                    ca_zip
                FROM
                    (
                        SELECT
                            SUBSTR(
                                ca_zip ,
                                1 ,
                                5
                            ) ca_zip ,
                            COUNT(*) cnt
                        FROM
                            customer_address ,
                            customer
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
    AND(
        SUBSTR(
            s_zip ,
            1 ,
            2
        ) = SUBSTR(
            V1.ca_zip ,
            1 ,
            2
        )
    )
GROUP BY
    s_store_name
ORDER BY
    s_store_name
; --end query 8 using template query8.tpl
 --start query 9 using template query9.tpl
 -- @public
 SELECT
    CASE
        WHEN(
            SELECT
                COUNT(*)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 1 AND 20
        ) > 30992
        THEN(
            SELECT
                AVG(ss_ext_sales_price)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 1 AND 20
        )
        ELSE(
            SELECT
                AVG(ss_net_paid)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 1 AND 20
        )
    END bucket1 ,
    CASE
        WHEN(
            SELECT
                COUNT(*)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 21 AND 40
        ) > 25740
        THEN(
            SELECT
                AVG(ss_ext_sales_price)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 21 AND 40
        )
        ELSE(
            SELECT
                AVG(ss_net_paid)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 21 AND 40
        )
    END bucket2 ,
    CASE
        WHEN(
            SELECT
                COUNT(*)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 41 AND 60
        ) > 20311
        THEN(
            SELECT
                AVG(ss_ext_sales_price)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 41 AND 60
        )
        ELSE(
            SELECT
                AVG(ss_net_paid)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 41 AND 60
        )
    END bucket3 ,
    CASE
        WHEN(
            SELECT
                COUNT(*)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 61 AND 80
        ) > 21635
        THEN(
            SELECT
                AVG(ss_ext_sales_price)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 61 AND 80
        )
        ELSE(
            SELECT
                AVG(ss_net_paid)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 61 AND 80
        )
    END bucket4 ,
    CASE
        WHEN(
            SELECT
                COUNT(*)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 81 AND 100
        ) > 20532
        THEN(
            SELECT
                AVG(ss_ext_sales_price)
            FROM
                store_sales
            WHERE
                ss_quantity BETWEEN 81 AND 100
        )
        ELSE(
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
    r_reason_sk = 1
; --end query 9 using template query9.tpl
 --start query 10 using template query10.tpl
 -- @private, @projection(demographics), @condition(customer_address)
 SELECT
    top 100 cd_gender ,
    cd_marital_status ,
    cd_education_status ,
    COUNT(*) cnt1 ,
    cd_purchase_estimate ,
    COUNT(*) cnt2 ,
    cd_credit_rating ,
    COUNT(*) cnt3 ,
    cd_dep_count ,
    COUNT(*) cnt4 ,
    cd_dep_employed_count ,
    COUNT(*) cnt5 ,
    cd_dep_college_count ,
    COUNT(*) cnt6
FROM
    customer c ,
    customer_address ca ,
    customer_demographics
WHERE
    c.c_current_addr_sk = ca.ca_address_sk
    AND ca_county IN(
        'Yellowstone County' ,
        'Montgomery County' ,
        'Divide County' ,
        'Cedar County' ,
        'Manassas Park city'
    )
    AND cd_demo_sk = c.c_current_cdemo_sk
    AND EXISTS(
        SELECT
            *
        FROM
            store_sales ,
            date_dim
        WHERE
            c.c_customer_sk = ss_customer_sk
            AND ss_sold_date_sk = d_date_sk
            AND d_year = 2000
            AND d_moy BETWEEN 2 AND 2 + 3
    )
    AND(
        EXISTS(
            SELECT
                *
            FROM
                web_sales ,
                date_dim
            WHERE
                c.c_customer_sk = ws_bill_customer_sk
                AND ws_sold_date_sk = d_date_sk
                AND d_year = 2000
                AND d_moy BETWEEN 2 AND 2 + 3
        )
        OR EXISTS(
            SELECT
                *
            FROM
                catalog_sales ,
                date_dim
            WHERE
                c.c_customer_sk = cs_ship_customer_sk
                AND cs_sold_date_sk = d_date_sk
                AND d_year = 2000
                AND d_moy BETWEEN 2 AND 2 + 3
        )
    )
GROUP BY
    cd_gender ,
    cd_marital_status ,
    cd_education_status ,
    cd_purchase_estimate ,
    cd_credit_rating ,
    cd_dep_count ,
    cd_dep_employed_count ,
    cd_dep_college_count
ORDER BY
    cd_gender ,
    cd_marital_status ,
    cd_education_status ,
    cd_purchase_estimate ,
    cd_credit_rating ,
    cd_dep_count ,
    cd_dep_employed_count ,
    cd_dep_college_count
; --end query 10 using template query10.tpl
 --start query 11 using template query11.tpl
 -- @public
 WITH year_total AS(
    SELECT
        c_customer_id customer_id ,
        c_first_name customer_first_name ,
        c_last_name customer_last_name ,
        c_preferred_cust_flag customer_preferred_cust_flag ,
        c_birth_country customer_birth_country ,
        c_login customer_login ,
        c_email_address customer_email_address ,
        d_year dyear ,
        SUM(
            ss_ext_list_price - ss_ext_discount_amt
        ) year_total ,
        's' sale_type
    FROM
        customer ,
        store_sales ,
        date_dim
    WHERE
        c_customer_sk = ss_customer_sk
        AND ss_sold_date_sk = d_date_sk
    GROUP BY
        c_customer_id ,
        c_first_name ,
        c_last_name ,
        d_year ,
        c_preferred_cust_flag ,
        c_birth_country ,
        c_login ,
        c_email_address ,
        d_year
UNION ALL SELECT
        c_customer_id customer_id ,
        c_first_name customer_first_name ,
        c_last_name customer_last_name ,
        c_preferred_cust_flag customer_preferred_cust_flag ,
        c_birth_country customer_birth_country ,
        c_login customer_login ,
        c_email_address customer_email_address ,
        d_year dyear ,
        SUM(
            ws_ext_list_price - ws_ext_discount_amt
        ) year_total ,
        'w' sale_type
    FROM
        customer ,
        web_sales ,
        date_dim
    WHERE
        c_customer_sk = ws_bill_customer_sk
        AND ws_sold_date_sk = d_date_sk
    GROUP BY
        c_customer_id ,
        c_first_name ,
        c_last_name ,
        c_preferred_cust_flag ,
        c_birth_country ,
        c_login ,
        c_email_address ,
        d_year
) SELECT
    top 100 t_s_secyear.customer_login
FROM
    year_total t_s_firstyear ,
    year_total t_s_secyear ,
    year_total t_w_firstyear ,
    year_total t_w_secyear
WHERE
    t_s_secyear.customer_id = t_s_firstyear.customer_id
    AND t_s_firstyear.customer_id = t_w_secyear.customer_id
    AND t_s_firstyear.customer_id = t_w_firstyear.customer_id
    AND t_s_firstyear.sale_type = 's'
    AND t_w_firstyear.sale_type = 'w'
    AND t_s_secyear.sale_type = 's'
    AND t_w_secyear.sale_type = 'w'
    AND t_s_firstyear.dyear = 2001
    AND t_s_secyear.dyear = 2001 + 1
    AND t_w_firstyear.dyear = 2001
    AND t_w_secyear.dyear = 2001 + 1
    AND t_s_firstyear.year_total > 0
    AND t_w_firstyear.year_total > 0
    AND CASE
        WHEN t_w_firstyear.year_total > 0
        THEN t_w_secyear.year_total / t_w_firstyear.year_total
        ELSE NULL
    END > CASE
        WHEN t_s_firstyear.year_total > 0
        THEN t_s_secyear.year_total / t_s_firstyear.year_total
        ELSE NULL
    END
ORDER BY
    t_s_secyear.customer_login
; --end query 11 using template query11.tpl
 --start query 12 using template query12.tpl
 -- @public
 SELECT
    top 100 i_item_desc ,
    i_category ,
    i_class ,
    i_current_price ,
    SUM(ws_ext_sales_price) AS itemrevenue ,
    SUM(ws_ext_sales_price) * 100 / SUM(
        SUM(ws_ext_sales_price)
    ) OVER(
        PARTITION BY i_class
    ) AS revenueratio
FROM
    web_sales ,
    item ,
    date_dim
WHERE
    ws_item_sk = i_item_sk
    AND i_category IN(
        'Children' ,
        'Sports' ,
        'Music'
    )
    AND ws_sold_date_sk = d_date_sk
    AND d_date BETWEEN CAST(
        '2002-04-01' AS DATE
    ) AND(
        CAST(
            '2002-04-01' AS DATE
        ) + 30 days
    )
GROUP BY
    i_item_id ,
    i_item_desc ,
    i_category ,
    i_class ,
    i_current_price
ORDER BY
    i_category ,
    i_class ,
    i_item_id ,
    i_item_desc ,
    revenueratio
; --end query 12 using template query12.tpl
 --start query 13 using template query13.tpl
 -- @private, @condition(demographics)
 SELECT
    AVG(ss_quantity) ,
    AVG(ss_ext_sales_price) ,
    AVG(ss_ext_wholesale_cost) ,
    SUM(ss_ext_wholesale_cost)
FROM
    store_sales ,
    store ,
    customer_demographics ,
    household_demographics ,
    customer_address ,
    date_dim
WHERE
    s_store_sk = ss_store_sk
    AND ss_sold_date_sk = d_date_sk
    AND d_year = 2001
    AND(
        (
            ss_hdemo_sk = hd_demo_sk
            AND cd_demo_sk = ss_cdemo_sk
            AND cd_marital_status = 'M'
            AND cd_education_status = '2 yr Degree'
            AND ss_sales_price BETWEEN 100.00 AND 150.00
            AND hd_dep_count = 3
        )
        OR(
            ss_hdemo_sk = hd_demo_sk
            AND cd_demo_sk = ss_cdemo_sk
            AND cd_marital_status = 'U'
            AND cd_education_status = '4 yr Degree'
            AND ss_sales_price BETWEEN 50.00 AND 100.00
            AND hd_dep_count = 1
        )
        OR(
            ss_hdemo_sk = hd_demo_sk
            AND cd_demo_sk = ss_cdemo_sk
            AND cd_marital_status = 'D'
            AND cd_education_status = 'Advanced Degree'
            AND ss_sales_price BETWEEN 150.00 AND 200.00
            AND hd_dep_count = 1
        )
    )
    AND(
        (
            ss_addr_sk = ca_address_sk
            AND ca_country = 'United States'
            AND ca_state IN(
                'ND' ,
                'IL' ,
                'AL'
            )
            AND ss_net_profit BETWEEN 100 AND 200
        )
        OR(
            ss_addr_sk = ca_address_sk
            AND ca_country = 'United States'
            AND ca_state IN(
                'MS' ,
                'OH' ,
                'NV'
            )
            AND ss_net_profit BETWEEN 150 AND 300
        )
        OR(
            ss_addr_sk = ca_address_sk
            AND ca_country = 'United States'
            AND ca_state IN(
                'MN' ,
                'IA' ,
                'OK'
            )
            AND ss_net_profit BETWEEN 50 AND 250
        )
    )
; --end query 13 using template query13.tpl
 --start query 14 using template query14.tpl
 WITH cross_items AS(
    SELECT
        i_item_sk ss_item_sk
    FROM
        item ,
        (
            SELECT
                iss.i_brand_id brand_id ,
                iss.i_class_id class_id ,
                iss.i_category_id category_id
            FROM
                store_sales ,
                item iss ,
                date_dim d1
            WHERE
                ss_item_sk = iss.i_item_sk
                AND ss_sold_date_sk = d1.d_date_sk
                AND d1.d_year BETWEEN 1999 AND 1999 + 2
        INTERSECT SELECT
                ics.i_brand_id ,
                ics.i_class_id ,
                ics.i_category_id
            FROM
                catalog_sales ,
                item ics ,
                date_dim d2
            WHERE
                cs_item_sk = ics.i_item_sk
                AND cs_sold_date_sk = d2.d_date_sk
                AND d2.d_year BETWEEN 1999 AND 1999 + 2
        INTERSECT SELECT
                iws.i_brand_id ,
                iws.i_class_id ,
                iws.i_category_id
            FROM
                web_sales ,
                item iws ,
                date_dim d3
            WHERE
                ws_item_sk = iws.i_item_sk
                AND ws_sold_date_sk = d3.d_date_sk
                AND d3.d_year BETWEEN 1999 AND 1999 + 2
        ) x
    WHERE
        i_brand_id = brand_id
        AND i_class_id = class_id
        AND i_category_id = category_id
) ,
avg_sales AS(
    SELECT
        AVG(
            quantity * list_price
        ) average_sales
    FROM
        (
            SELECT
                ss_quantity quantity ,
                ss_list_price list_price
            FROM
                store_sales ,
                date_dim
            WHERE
                ss_sold_date_sk = d_date_sk
                AND d_year BETWEEN 1999 AND 2001
        UNION ALL SELECT
                cs_quantity quantity ,
                cs_list_price list_price
            FROM
                catalog_sales ,
                date_dim
            WHERE
                cs_sold_date_sk = d_date_sk
                AND d_year BETWEEN 2000 AND 2000 + 2
        UNION ALL SELECT
                ws_quantity quantity ,
                ws_list_price list_price
            FROM
                web_sales ,
                date_dim
            WHERE
                ws_sold_date_sk = d_date_sk
                AND d_year BETWEEN 2000 AND 2000 + 2
        ) x
) SELECT
    top 100 channel ,
    i_brand_id ,
    i_class_id ,
    i_category_id ,
    SUM(sales) ,
    SUM(number_sales)
FROM
    (
        SELECT
            'store' channel ,
            i_brand_id ,
            i_class_id ,
            i_category_id ,
            SUM(
                ss_quantity * ss_list_price
            ) sales ,
            COUNT(*) number_sales
        FROM
            store_sales ,
            item ,
            date_dim
        WHERE
            ss_item_sk IN(
                SELECT
                    ss_item_sk
                FROM
                    cross_items
            )
            AND ss_item_sk = i_item_sk
            AND ss_sold_date_sk = d_date_sk
            AND d_year = 2000 + 2
            AND d_moy = 11
        GROUP BY
            i_brand_id ,
            i_class_id ,
            i_category_id
        HAVING
            SUM(
                ss_quantity * ss_list_price
            ) >(
                SELECT
                    average_sales
                FROM
                    avg_sales
            )
    UNION ALL SELECT
            'catalog' channel ,
            i_brand_id ,
            i_class_id ,
            i_category_id ,
            SUM(
                cs_quantity * cs_list_price
            ) sales ,
            COUNT(*) number_sales
        FROM
            catalog_sales ,
            item ,
            date_dim
        WHERE
            cs_item_sk IN(
                SELECT
                    ss_item_sk
                FROM
                    cross_items
            )
            AND cs_item_sk = i_item_sk
            AND cs_sold_date_sk = d_date_sk
            AND d_year = 2000 + 2
            AND d_moy = 11
        GROUP BY
            i_brand_id ,
            i_class_id ,
            i_category_id
        HAVING
            SUM(
                cs_quantity * cs_list_price
            ) >(
                SELECT
                    average_sales
                FROM
                    avg_sales
            )
    UNION ALL SELECT
            'web' channel ,
            i_brand_id ,
            i_class_id ,
            i_category_id ,
            SUM(
                ws_quantity * ws_list_price
            ) sales ,
            COUNT(*) number_sales
        FROM
            web_sales ,
            item ,
            date_dim
        WHERE
            ws_item_sk IN(
                SELECT
                    ss_item_sk
                FROM
                    cross_items
            )
            AND ws_item_sk = i_item_sk
            AND ws_sold_date_sk = d_date_sk
            AND d_year = 2000 + 2
            AND d_moy = 11
        GROUP BY
            i_brand_id ,
            i_class_id ,
            i_category_id
        HAVING
            SUM(
                ws_quantity * ws_list_price
            ) >(
                SELECT
                    average_sales
                FROM
                    avg_sales
            )
    ) y
GROUP BY
    rollup(
        channel ,
        i_brand_id ,
        i_class_id ,
        i_category_id
    )
ORDER BY
    channel ,
    i_brand_id ,
    i_class_id ,
    i_category_id
; WITH cross_items AS(
    SELECT
        i_item_sk ss_item_sk
    FROM
        item ,
        (
            SELECT
                iss.i_brand_id brand_id ,
                iss.i_class_id class_id ,
                iss.i_category_id category_id
            FROM
                store_sales ,
                item iss ,
                date_dim d1
            WHERE
                ss_item_sk = iss.i_item_sk
                AND ss_sold_date_sk = d1.d_date_sk
                AND d1.d_year BETWEEN 1999 AND 1999 + 2
        INTERSECT SELECT
                ics.i_brand_id ,
                ics.i_class_id ,
                ics.i_category_id
            FROM
                catalog_sales ,
                item ics ,
                date_dim d2
            WHERE
                cs_item_sk = ics.i_item_sk
                AND cs_sold_date_sk = d2.d_date_sk
                AND d2.d_year BETWEEN 1999 AND 1999 + 2
        INTERSECT SELECT
                iws.i_brand_id ,
                iws.i_class_id ,
                iws.i_category_id
            FROM
                web_sales ,
                item iws ,
                date_dim d3
            WHERE
                ws_item_sk = iws.i_item_sk
                AND ws_sold_date_sk = d3.d_date_sk
                AND d3.d_year BETWEEN 1999 AND 1999 + 2
        ) x
    WHERE
        i_brand_id = brand_id
        AND i_class_id = class_id
        AND i_category_id = category_id
) ,
avg_sales AS(
    SELECT
        AVG(
            quantity * list_price
        ) average_sales
    FROM
        (
            SELECT
                ss_quantity quantity ,
                ss_list_price list_price
            FROM
                store_sales ,
                date_dim
            WHERE
                ss_sold_date_sk = d_date_sk
                AND d_year BETWEEN 2000 AND 2000 + 2
        UNION ALL SELECT
                cs_quantity quantity ,
                cs_list_price list_price
            FROM
                catalog_sales ,
                date_dim
            WHERE
                cs_sold_date_sk = d_date_sk
                AND d_year BETWEEN 2000 AND 2000 + 2
        UNION ALL SELECT
                ws_quantity quantity ,
                ws_list_price list_price
            FROM
                web_sales ,
                date_dim
            WHERE
                ws_sold_date_sk = d_date_sk
                AND d_year BETWEEN 2000 AND 2000 + 2
        ) x
) SELECT
    top 100 *
FROM
    (
        SELECT
            'store' channel ,
            i_brand_id ,
            i_class_id ,
            i_category_id ,
            SUM(
                ss_quantity * ss_list_price
            ) sales ,
            COUNT(*) number_sales
        FROM
            store_sales ,
            item ,
            date_dim
        WHERE
            ss_item_sk IN(
                SELECT
                    ss_item_sk
                FROM
                    cross_items
            )
            AND ss_item_sk = i_item_sk
            AND ss_sold_date_sk = d_date_sk
            AND d_week_seq =(
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
            i_brand_id ,
            i_class_id ,
            i_category_id
        HAVING
            SUM(
                ss_quantity * ss_list_price
            ) >(
                SELECT
                    average_sales
                FROM
                    avg_sales
            )
    ) this_year ,
    (
        SELECT
            'store' channel ,
            i_brand_id ,
            i_class_id ,
            i_category_id ,
            SUM(
                ss_quantity * ss_list_price
            ) sales ,
            COUNT(*) number_sales
        FROM
            store_sales ,
            item ,
            date_dim
        WHERE
            ss_item_sk IN(
                SELECT
                    ss_item_sk
                FROM
                    cross_items
            )
            AND ss_item_sk = i_item_sk
            AND ss_sold_date_sk = d_date_sk
            AND d_week_seq =(
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
            i_brand_id ,
            i_class_id ,
            i_category_id
        HAVING
            SUM(
                ss_quantity * ss_list_price
            ) >(
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
    this_year.channel ,
    this_year.i_brand_id ,
    this_year.i_class_id ,
    this_year.i_category_id
; --end query 14 using template query14.tpl
 --start query 15 using template query15.tpl
 -- @private, @project(customer_address), @condition(customer_address)
 SELECT
    top 100 ca_zip ,
    SUM(cs_sales_price)
FROM
    catalog_sales ,
    customer ,
    customer_address ,
    date_dim
WHERE
    cs_bill_customer_sk = c_customer_sk
    AND c_current_addr_sk = ca_address_sk
    AND(
        SUBSTR(
            ca_zip ,
            1 ,
            5
        ) IN(
            '85669' ,
            '86197' ,
            '88274' ,
            '83405' ,
            '86475' ,
            '85392' ,
            '85460' ,
            '80348' ,
            '81792'
        )
        OR ca_state IN(
            'CA' ,
            'WA' ,
            'GA'
        )
        OR cs_sales_price > 500
    )
    AND cs_sold_date_sk = d_date_sk
    AND d_qoy = 2
    AND d_year = 1998
GROUP BY
    ca_zip
ORDER BY
    ca_zip
; --end query 15 using template query15.tpl
 --start query 16 using template query16.tpl
 -- @private, @condition(customer_address)
 SELECT
    top 100 COUNT(
        DISTINCT cs_order_number
    ) AS "order count" ,
    SUM(cs_ext_ship_cost) AS "total shipping cost" ,
    SUM(cs_net_profit) AS "total net profit"
FROM
    catalog_sales cs1 ,
    date_dim ,
    customer_address ,
    call_center
WHERE
    d_date BETWEEN '2000-3-01' AND(
        CAST(
            '2000-3-01' AS DATE
        ) + 60 days
    )
    AND cs1.cs_ship_date_sk = d_date_sk
    AND cs1.cs_ship_addr_sk = ca_address_sk
    AND ca_state = 'MN'
    AND cs1.cs_call_center_sk = cc_call_center_sk
    AND cc_county IN(
        'Williamson County' ,
        'Williamson County' ,
        'Williamson County' ,
        'Williamson County' ,
        'Williamson County'
    )
    AND EXISTS(
        SELECT
            *
        FROM
            catalog_sales cs2
        WHERE
            cs1.cs_order_number = cs2.cs_order_number
            AND cs1.cs_warehouse_sk <> cs2.cs_warehouse_sk
    )
    AND NOT EXISTS(
        SELECT
            *
        FROM
            catalog_returns cr1
        WHERE
            cs1.cs_order_number = cr1.cr_order_number
    )
ORDER BY
    COUNT(
        DISTINCT cs_order_number
    )
; --end query 16 using template query16.tpl
 --start query 17 using template query17.tpl
 -- @public
 SELECT
    top 100 i_item_id ,
    i_item_desc ,
    s_state ,
    COUNT(ss_quantity) AS store_sales_quantitycount ,
    AVG(ss_quantity) AS store_sales_quantityave ,
    stddev_samp(ss_quantity) AS store_sales_quantitystdev ,
    stddev_samp(ss_quantity) / AVG(ss_quantity) AS store_sales_quantitycov ,
    COUNT(sr_return_quantity) as_store_returns_quantitycount ,
    AVG(sr_return_quantity) as_store_returns_quantityave ,
    stddev_samp(sr_return_quantity) as_store_returns_quantitystdev ,
    stddev_samp(sr_return_quantity) / AVG(sr_return_quantity) AS store_returns_quantitycov ,
    COUNT(cs_quantity) AS catalog_sales_quantitycount ,
    AVG(cs_quantity) AS catalog_sales_quantityave ,
    stddev_samp(cs_quantity) / AVG(cs_quantity) AS catalog_sales_quantitystdev ,
    stddev_samp(cs_quantity) / AVG(cs_quantity) AS catalog_sales_quantitycov
FROM
    store_sales ,
    store_returns ,
    catalog_sales ,
    date_dim d1 ,
    date_dim d2 ,
    date_dim d3 ,
    store ,
    item
WHERE
    d1.d_quarter_name = '2001Q1'
    AND d1.d_date_sk = ss_sold_date_sk
    AND i_item_sk = ss_item_sk
    AND s_store_sk = ss_store_sk
    AND ss_customer_sk = sr_customer_sk
    AND ss_item_sk = sr_item_sk
    AND ss_ticket_number = sr_ticket_number
    AND sr_returned_date_sk = d2.d_date_sk
    AND d2.d_quarter_name IN(
        '2001Q1' ,
        '2001Q2' ,
        '2001Q3'
    )
    AND sr_customer_sk = cs_bill_customer_sk
    AND sr_item_sk = cs_item_sk
    AND cs_sold_date_sk = d3.d_date_sk
    AND d3.d_quarter_name IN(
        '2001Q1' ,
        '2001Q2' ,
        '2001Q3'
    )
GROUP BY
    i_item_id ,
    i_item_desc ,
    s_state
ORDER BY
    i_item_id ,
    i_item_desc ,
    s_state
; --end query 17 using template query17.tpl
 --start query 18 using template query18.tpl
 -- @private, @projection(customer_address, customer), @condition(demographics)
 SELECT
    top 100 i_item_id ,
    ca_country ,
    ca_state ,
    ca_county ,
    AVG(
        CAST(
            cs_quantity AS NUMERIC(
                12 ,
                2
            )
        )
    ) agg1 ,
    AVG(
        CAST(
            cs_list_price AS NUMERIC(
                12 ,
                2
            )
        )
    ) agg2 ,
    AVG(
        CAST(
            cs_coupon_amt AS NUMERIC(
                12 ,
                2
            )
        )
    ) agg3 ,
    AVG(
        CAST(
            cs_sales_price AS NUMERIC(
                12 ,
                2
            )
        )
    ) agg4 ,
    AVG(
        CAST(
            cs_net_profit AS NUMERIC(
                12 ,
                2
            )
        )
    ) agg5 ,
    AVG(
        CAST(
            c_birth_year AS NUMERIC(
                12 ,
                2
            )
        )
    ) agg6 ,
    AVG(
        CAST(
            cd1.cd_dep_count AS NUMERIC(
                12 ,
                2
            )
        )
    ) agg7
FROM
    catalog_sales ,
    customer_demographics cd1 ,
    customer_demographics cd2 ,
    customer ,
    customer_address ,
    date_dim ,
    item
WHERE
    cs_sold_date_sk = d_date_sk
    AND cs_item_sk = i_item_sk
    AND cs_bill_cdemo_sk = cd1.cd_demo_sk
    AND cs_bill_customer_sk = c_customer_sk
    AND cd1.cd_gender = 'F'
    AND cd1.cd_education_status = '4 yr Degree'
    AND c_current_cdemo_sk = cd2.cd_demo_sk
    AND c_current_addr_sk = ca_address_sk
    AND c_birth_month IN(
        6 ,
        5 ,
        12 ,
        4 ,
        3 ,
        7
    )
    AND d_year = 2001
    AND ca_state IN(
        'TN' ,
        'IL' ,
        'GA' ,
        'MO' ,
        'CO' ,
        'OH' ,
        'NM'
    )
GROUP BY
    rollup(
        i_item_id ,
        ca_country ,
        ca_state ,
        ca_county
    )
ORDER BY
    ca_country ,
    ca_state ,
    ca_county ,
    i_item_id
; --end query 18 using template query18.tpl
 --start query 19 using template query19.tpl
 -- products bought by out of zip code customers for a given year, month, and manager.
 -- @public
 SELECT
    top 100 i_brand_id brand_id ,
    i_brand brand ,
    i_manufact_id ,
    i_manufact ,
    SUM(ss_ext_sales_price) ext_price
FROM
    date_dim ,
    store_sales ,
    item ,
    customer ,
    customer_address ,
    store
WHERE
    d_date_sk = ss_sold_date_sk
    AND ss_item_sk = i_item_sk
    AND i_manager_id = 91
    AND d_moy = 12
    AND d_year = 2002
    AND ss_customer_sk = c_customer_sk
    AND c_current_addr_sk = ca_address_sk
    AND SUBSTR(
        ca_zip ,
        1 ,
        5
    ) <> SUBSTR(
        s_zip ,
        1 ,
        5
    )
    AND ss_store_sk = s_store_sk
GROUP BY
    i_brand ,
    i_brand_id ,
    i_manufact_id ,
    i_manufact
ORDER BY
    ext_price DESC ,
    i_brand ,
    i_brand_id ,
    i_manufact_id ,
    i_manufact
; --end query 19 using template query19.tpl
 --start query 20 using template query20.tpl
 -- @public
 SELECT
    top 100 i_item_desc ,
    i_category ,
    i_class ,
    i_current_price ,
    SUM(cs_ext_sales_price) AS itemrevenue ,
    SUM(cs_ext_sales_price) * 100 / SUM(
        SUM(cs_ext_sales_price)
    ) OVER(
        PARTITION BY i_class
    ) AS revenueratio
FROM
    catalog_sales ,
    item ,
    date_dim
WHERE
    cs_item_sk = i_item_sk
    AND i_category IN(
        'Shoes' ,
        'Women' ,
        'Music'
    )
    AND cs_sold_date_sk = d_date_sk
    AND d_date BETWEEN CAST(
        '1999-06-03' AS DATE
    ) AND(
        CAST(
            '1999-06-03' AS DATE
        ) + 30 days
    )
GROUP BY
    i_item_id ,
    i_item_desc ,
    i_category ,
    i_class ,
    i_current_price
ORDER BY
    i_category ,
    i_class ,
    i_item_id ,
    i_item_desc ,
    revenueratio
; --end query 20 using template query20.tpl;

