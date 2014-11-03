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
        top 100 i_item_id
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

