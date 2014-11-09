-- 
-- Legal Notice 
-- 
-- This document and associated source code (the "Work") is a part of a 
-- benchmark specification maintained by the TPC. 
-- 
-- The TPC reserves all right, title, and interest to the Work as provided 
-- under U.S. and international laws, including without limitation all patent 
-- and trademark rights therein. 
-- 
-- No Warranty 
-- 
-- 1.1 TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, THE INFORMATION 
--     CONTAINED HEREIN IS PROVIDED "AS IS" AND WITH ALL FAULTS, AND THE 
--     AUTHORS AND DEVELOPERS OF THE WORK HEREBY DISCLAIM ALL OTHER 
--     WARRANTIES AND CONDITIONS, EITHER EXPRESS, IMPLIED OR STATUTORY, 
--     INCLUDING, BUT NOT LIMITED TO, ANY (IF ANY) IMPLIED WARRANTIES, 
--     DUTIES OR CONDITIONS OF MERCHANTABILITY, OF FITNESS FOR A PARTICULAR 
--     PURPOSE, OF ACCURACY OR COMPLETENESS OF RESPONSES, OF RESULTS, OF 
--     WORKMANLIKE EFFORT, OF LACK OF VIRUSES, AND OF LACK OF NEGLIGENCE. 
--     ALSO, THERE IS NO WARRANTY OR CONDITION OF TITLE, QUIET ENJOYMENT, 
--     QUIET POSSESSION, CORRESPONDENCE TO DESCRIPTION OR NON-INFRINGEMENT 
--     WITH REGARD TO THE WORK. 
-- 1.2 IN NO EVENT WILL ANY AUTHOR OR DEVELOPER OF THE WORK BE LIABLE TO 
--     ANY OTHER PARTY FOR ANY DAMAGES, INCLUDING BUT NOT LIMITED TO THE 
--     COST OF PROCURING SUBSTITUTE GOODS OR SERVICES, LOST PROFITS, LOSS 
--     OF USE, LOSS OF DATA, OR ANY INCIDENTAL, CONSEQUENTIAL, DIRECT, 
--     INDIRECT, OR SPECIAL DAMAGES WHETHER UNDER CONTRACT, TORT, WARRANTY,
--     OR OTHERWISE, ARISING IN ANY WAY OUT OF THIS OR ANY OTHER AGREEMENT 
--     RELATING TO THE WORK, WHETHER OR NOT SUCH AUTHOR OR DEVELOPER HAD 
--     ADVANCE NOTICE OF THE POSSIBILITY OF SUCH DAMAGES. 
-- 
-- Contributors:
-- Gradient Systems
--
use tpc;
CREATE
    TABLE dbgen_version(
        dv_version VARCHAR(16)
        ,dv_create_date DATE
        ,dv_create_time TIMESTAMP
        ,dv_cmdline_args VARCHAR(200)
    );
CREATE
    TABLE customer_address(
        ca_address_sk INT
        ,ca_address_id VARCHAR(16)
        ,ca_street_number VARCHAR(10)
        ,ca_street_name VARCHAR(60)
        ,ca_street_type VARCHAR(15)
        ,ca_suite_number VARCHAR(10)
        ,ca_city VARCHAR(60)
        ,ca_county VARCHAR(30)
        ,ca_state VARCHAR(2)
        ,ca_zip VARCHAR(10)
        ,ca_country VARCHAR(20)
        ,ca_gmt_offset DECIMAL
        ,ca_location_type VARCHAR(20)
    );
CREATE
    TABLE customer_demographics(
        cd_demo_sk INT
        ,cd_gender VARCHAR(1)
        ,cd_marital_status VARCHAR(1)
        ,cd_education_status VARCHAR(20)
        ,cd_purchase_estimate INT
        ,cd_credit_rating VARCHAR(10)
        ,cd_dep_count INT
        ,cd_dep_employed_count INT
        ,cd_dep_college_count INT
    );
CREATE
    TABLE date_dim(
        d_date_sk INT
        ,d_date_id VARCHAR(16)
        ,d_date TIMESTAMP
        ,d_month_seq INT
        ,d_week_seq INT
        ,d_quarter_seq INT
        ,d_year INT
        ,d_dow INT
        ,d_moy INT
        ,d_dom INT
        ,d_qoy INT
        ,d_fy_year INT
        ,d_fy_quarter_seq INT
        ,d_fy_week_seq INT
        ,d_day_name VARCHAR(9)
        ,d_quarter_name VARCHAR(6)
        ,d_holiday VARCHAR(1)
        ,d_weekend VARCHAR(1)
        ,d_following_holiday VARCHAR(1)
        ,d_first_dom INT
        ,d_last_dom INT
        ,d_same_day_ly INT
        ,d_same_day_lq INT
        ,d_current_day VARCHAR(1)
        ,d_current_week VARCHAR(1)
        ,d_current_month VARCHAR(1)
        ,d_current_quarter VARCHAR(1)
        ,d_current_year VARCHAR(1)
    );
CREATE
    TABLE warehouse(
        w_warehouse_sk INT
        ,w_warehouse_id VARCHAR(16)
        ,w_warehouse_name VARCHAR(20)
        ,w_warehouse_sq_ft INT
        ,w_street_number VARCHAR(10)
        ,w_street_name VARCHAR(60)
        ,w_street_type VARCHAR(15)
        ,w_suite_number VARCHAR(10)
        ,w_city VARCHAR(60)
        ,w_county VARCHAR(30)
        ,w_state VARCHAR(2)
        ,w_zip VARCHAR(10)
        ,w_country VARCHAR(20)
        ,w_gmt_offset DECIMAL
    );
CREATE
    TABLE ship_mode(
        sm_ship_mode_sk INT
        ,sm_ship_mode_id VARCHAR(16)
        ,sm_type VARCHAR(30)
        ,sm_code VARCHAR(10)
        ,sm_carrier VARCHAR(20)
        ,sm_contract VARCHAR(20)
    );
CREATE
    TABLE time_dim(
        t_time_sk INT
        ,t_time_id VARCHAR(16)
        ,t_time INT
        ,t_hour INT
        ,t_minute INT
        ,t_second INT
        ,t_am_pm VARCHAR(2)
        ,t_shift VARCHAR(20)
        ,t_sub_shift VARCHAR(20)
        ,t_meal_time VARCHAR(20)
    );
CREATE
    TABLE reason(
        r_reason_sk INT
        ,r_reason_id VARCHAR(16)
        ,r_reason_desc VARCHAR(100)
    );
CREATE
    TABLE income_band(
        ib_income_band_sk INT
        ,ib_lower_bound INT
        ,ib_upper_bound INT
    );
CREATE
    TABLE item(
        i_item_sk INT
        ,i_item_id VARCHAR(16)
        ,i_rec_start_date DATE
        ,i_rec_end_date DATE
        ,i_item_desc VARCHAR(200)
        ,i_current_price DECIMAL
        ,i_wholesale_cost DECIMAL
        ,i_brand_id INT
        ,i_brand VARCHAR(50)
        ,i_class_id INT
        ,i_class VARCHAR(50)
        ,i_category_id INT
        ,i_category VARCHAR(50)
        ,i_manufact_id INT
        ,i_manufact VARCHAR(50)
        ,i_size VARCHAR(20)
        ,i_formulation VARCHAR(20)
        ,i_color VARCHAR(20)
        ,i_units VARCHAR(10)
        ,i_container VARCHAR(10)
        ,i_manager_id INT
        ,i_product_name VARCHAR(50)
    );
create table store
(
    s_store_sk                int               ,
    s_store_id                varchar(16)              ,
    s_rec_start_date          date                          ,
    s_rec_end_date            date                          ,
    s_closed_date_sk          int                       ,
    s_store_name              varchar(50)                   ,
    s_number_employees        int                       ,
    s_floor_space             int                       ,
    s_hours                   varchar(20)                      ,
    s_manager                 varchar(40)                   ,
    s_market_id               int                       ,
    s_geography_class         varchar(100)                  ,
    s_market_desc             varchar(100)                  ,
    s_market_manager          varchar(40)                   ,
    s_division_id             int                       ,
    s_division_name           varchar(50)                   ,
    s_company_id              int                       ,
    s_company_name            varchar(50)                   ,
    s_street_number           varchar(10)                   ,
    s_street_name             varchar(60)                   ,
    s_street_type             varchar(15)                      ,
    s_suite_number            varchar(10)                      ,
    s_city                    varchar(60)                   ,
    s_county                  varchar(30)                   ,
    s_state                   varchar(2)                       ,
    s_zip                     varchar(10)                      ,
    s_country                 varchar(20)                   ,
    s_gmt_offset              decimal                  ,
    s_tax_precentage          decimal                  
    
);
create table call_center
(
    cc_call_center_sk         int               ,
    cc_call_center_id         varchar(16)              ,
    cc_rec_start_date         date                          ,
    cc_rec_end_date           date                          ,
    cc_closed_date_sk         int                       ,
    cc_open_date_sk           int                       ,
    cc_name                   varchar(50)                   ,
    cc_class                  varchar(50)                   ,
    cc_employees              int                       ,
    cc_sq_ft                  int                       ,
    cc_hours                  varchar(20)                      ,
    cc_manager                varchar(40)                   ,
    cc_mkt_id                 int                       ,
    cc_mkt_class              varchar(50)                      ,
    cc_mkt_desc               varchar(100)                  ,
    cc_market_manager         varchar(40)                   ,
    cc_division               int                       ,
    cc_division_name          varchar(50)                   ,
    cc_company                int                       ,
    cc_company_name           varchar(50)                      ,
    cc_street_number          varchar(10)                      ,
    cc_street_name            varchar(60)                   ,
    cc_street_type            varchar(15)                      ,
    cc_suite_number           varchar(10)                      ,
    cc_city                   varchar(60)                   ,
    cc_county                 varchar(30)                   ,
    cc_state                  varchar(2)                       ,
    cc_zip                    varchar(10)                      ,
    cc_country                varchar(20)                   ,
    cc_gmt_offset             decimal                  ,
    cc_tax_percentage         decimal                  
    
);
create table customer
(
    c_customer_sk             int               ,
    c_customer_id             varchar(16)              ,
    c_current_cdemo_sk        int                       ,
    c_current_hdemo_sk        int                       ,
    c_current_addr_sk         int                       ,
    c_first_shipto_date_sk    int                       ,
    c_first_sales_date_sk     int                       ,
    c_salutation              varchar(10)                      ,
    c_first_name              varchar(20)                      ,
    c_last_name               varchar(30)                      ,
    c_preferred_cust_flag     varchar(1)                       ,
    c_birth_day               int                       ,
    c_birth_month             int                       ,
    c_birth_year              int                       ,
    c_birth_country           varchar(20)                   ,
    c_login                   varchar(13)                      ,
    c_email_address           varchar(50)                      ,
    c_last_review_date        varchar(10)                      
    
);
create table web_site
(
    web_site_sk               int               ,
    web_site_id               varchar(16)              ,
    web_rec_start_date        date                          ,
    web_rec_end_date          date                          ,
    web_name                  varchar(50)                   ,
    web_open_date_sk          int                       ,
    web_close_date_sk         int                       ,
    web_class                 varchar(50)                   ,
    web_manager               varchar(40)                   ,
    web_mkt_id                int                       ,
    web_mkt_class             varchar(50)                   ,
    web_mkt_desc              varchar(100)                  ,
    web_market_manager        varchar(40)                   ,
    web_company_id            int                       ,
    web_company_name          varchar(50)                      ,
    web_street_number         varchar(10)                      ,
    web_street_name           varchar(60)                   ,
    web_street_type           varchar(15)                      ,
    web_suite_number          varchar(10)                      ,
    web_city                  varchar(60)                   ,
    web_county                varchar(30)                   ,
    web_state                 varchar(2)                       ,
    web_zip                   varchar(10)                      ,
    web_country               varchar(20)                   ,
    web_gmt_offset            decimal                  ,
    web_tax_percentage        decimal                  
    
);
create table store_returns
(
    sr_returned_date_sk       int                       ,
    sr_return_time_sk         int                       ,
    sr_item_sk                int               ,
    sr_customer_sk            int                       ,
    sr_cdemo_sk               int                       ,
    sr_hdemo_sk               int                       ,
    sr_addr_sk                int                       ,
    sr_store_sk               int                       ,
    sr_reason_sk              int                       ,
    sr_ticket_number          int               ,
    sr_return_quantity        int                       ,
    sr_return_amt             decimal                  ,
    sr_return_tax             decimal                  ,
    sr_return_amt_inc_tax     decimal                  ,
    sr_fee                    decimal                  ,
    sr_return_ship_cost       decimal                  ,
    sr_refunded_cash          decimal                  ,
    sr_reversed_charge        decimal                  ,
    sr_store_credit           decimal                  ,
    sr_net_loss               decimal                  
    
);
create table household_demographics
(
    hd_demo_sk                int               ,
    hd_income_band_sk         int                       ,
    hd_buy_potential          varchar(15)                      ,
    hd_dep_count              int                       ,
    hd_vehicle_count          int                       
);
create table web_page
(
    wp_web_page_sk            int               ,
    wp_web_page_id            varchar(16)              ,
    wp_rec_start_date         date                          ,
    wp_rec_end_date           date                          ,
    wp_creation_date_sk       int                       ,
    wp_access_date_sk         int                       ,
    wp_autogen_flag           varchar(1)                       ,
    wp_customer_sk            int                       ,
    wp_url                    varchar(100)                  ,
    wp_type                   varchar(50)                      ,
    wp_char_count             int                       ,
    wp_link_count             int                       ,
    wp_image_count            int                       ,
    wp_max_ad_count           int                         
);
create table promotion
(
    p_promo_sk                int               ,
    p_promo_id                varchar(16)              ,
    p_start_date_sk           int                       ,
    p_end_date_sk             int                       ,
    p_item_sk                 int                       ,
    p_cost                    decimal                 ,
    p_response_target         int                       ,
    p_promo_name              varchar(50)                      ,
    p_channel_dmail           varchar(1)                       ,
    p_channel_email           varchar(1)                       ,
    p_channel_catalog         varchar(1)                       ,
    p_channel_tv              varchar(1)                       ,
    p_channel_radio           varchar(1)                       ,
    p_channel_press           varchar(1)                       ,
    p_channel_event           varchar(1)                       ,
    p_channel_demo            varchar(1)                       ,
    p_channel_details         varchar(100)                  ,
    p_purpose                 varchar(15)                      ,
    p_discount_active         varchar(1)                       
);
create table catalog_page
(
    cp_catalog_page_sk        int               ,
    cp_catalog_page_id        varchar(16)              ,
    cp_start_date_sk          int                       ,
    cp_end_date_sk            int                       ,
    cp_department             varchar(50)                   ,
    cp_catalog_number         int                       ,
    cp_catalog_page_number    int                       ,
    cp_description            varchar(100)                  ,
    cp_type                   varchar(100)                  
);
create table inventory
(
    inv_date_sk               int               ,
    inv_item_sk               int               ,
    inv_warehouse_sk          int               ,
    inv_quantity_on_hand      int                       
);
create table catalog_returns
(
    cr_returned_date_sk       int                       ,
    cr_returned_time_sk       int                       ,
    cr_item_sk                int               ,
    cr_refunded_customer_sk   int                       ,
    cr_refunded_cdemo_sk      int                       ,
    cr_refunded_hdemo_sk      int                       ,
    cr_refunded_addr_sk       int                       ,
    cr_returning_customer_sk  int                       ,
    cr_returning_cdemo_sk     int                       ,
    cr_returning_hdemo_sk     int                       ,
    cr_returning_addr_sk      int                       ,
    cr_call_center_sk         int                       ,
    cr_catalog_page_sk        int                       ,
    cr_ship_mode_sk           int                       ,
    cr_warehouse_sk           int                       ,
    cr_reason_sk              int                       ,
    cr_order_number           int               ,
    cr_return_quantity        int                       ,
    cr_return_amount          decimal                  ,
    cr_return_tax             decimal                  ,
    cr_return_amt_inc_tax     decimal                  ,
    cr_fee                    decimal                  ,
    cr_return_ship_cost       decimal                  ,
    cr_refunded_cash          decimal                  ,
    cr_reversed_charge        decimal                  ,
    cr_store_credit           decimal                  ,
    cr_net_loss               decimal                  
);
create table web_returns
(
    wr_returned_date_sk       int                       ,
    wr_returned_time_sk       int                       ,
    wr_item_sk                int               ,
    wr_refunded_customer_sk   int                       ,
    wr_refunded_cdemo_sk      int                       ,
    wr_refunded_hdemo_sk      int                       ,
    wr_refunded_addr_sk       int                       ,
    wr_returning_customer_sk  int                       ,
    wr_returning_cdemo_sk     int                       ,
    wr_returning_hdemo_sk     int                       ,
    wr_returning_addr_sk      int                       ,
    wr_web_page_sk            int                       ,
    wr_reason_sk              int                       ,
    wr_order_number           int               ,
    wr_return_quantity        int                       ,
    wr_return_amt             decimal                  ,
    wr_return_tax             decimal                  ,
    wr_return_amt_inc_tax     decimal                  ,
    wr_fee                    decimal                  ,
    wr_return_ship_cost       decimal                  ,
    wr_refunded_cash          decimal                  ,
    wr_reversed_charge        decimal                  ,
    wr_account_credit         decimal                  ,
    wr_net_loss               decimal                  
);
create table web_sales
(
    ws_sold_date_sk           int                       ,
    ws_sold_time_sk           int                       ,
    ws_ship_date_sk           int                       ,
    ws_item_sk                int               ,
    ws_bill_customer_sk       int                       ,
    ws_bill_cdemo_sk          int                       ,
    ws_bill_hdemo_sk          int                       ,
    ws_bill_addr_sk           int                       ,
    ws_ship_customer_sk       int                       ,
    ws_ship_cdemo_sk          int                       ,
    ws_ship_hdemo_sk          int                       ,
    ws_ship_addr_sk           int                       ,
    ws_web_page_sk            int                       ,
    ws_web_site_sk            int                       ,
    ws_ship_mode_sk           int                       ,
    ws_warehouse_sk           int                       ,
    ws_promo_sk               int                       ,
    ws_order_number           int               ,
    ws_quantity               int                       ,
    ws_wholesale_cost         decimal                  ,
    ws_list_price             decimal                  ,
    ws_sales_price            decimal                  ,
    ws_ext_discount_amt       decimal                  ,
    ws_ext_sales_price        decimal                  ,
    ws_ext_wholesale_cost     decimal                  ,
    ws_ext_list_price         decimal                  ,
    ws_ext_tax                decimal                  ,
    ws_coupon_amt             decimal                  ,
    ws_ext_ship_cost          decimal                  ,
    ws_net_paid               decimal                  ,
    ws_net_paid_inc_tax       decimal                  ,
    ws_net_paid_inc_ship      decimal                  ,
    ws_net_paid_inc_ship_tax  decimal                  ,
    ws_net_profit             decimal                  
);
create table catalog_sales
(
    cs_sold_date_sk           int                       ,
    cs_sold_time_sk           int                       ,
    cs_ship_date_sk           int                       ,
    cs_bill_customer_sk       int                       ,
    cs_bill_cdemo_sk          int                       ,
    cs_bill_hdemo_sk          int                       ,
    cs_bill_addr_sk           int                       ,
    cs_ship_customer_sk       int                       ,
    cs_ship_cdemo_sk          int                       ,
    cs_ship_hdemo_sk          int                       ,
    cs_ship_addr_sk           int                       ,
    cs_call_center_sk         int                       ,
    cs_catalog_page_sk        int                       ,
    cs_ship_mode_sk           int                       ,
    cs_warehouse_sk           int                       ,
    cs_item_sk                int               ,
    cs_promo_sk               int                       ,
    cs_order_number           int               ,
    cs_quantity               int                       ,
    cs_wholesale_cost         decimal                  ,
    cs_list_price             decimal                  ,
    cs_sales_price            decimal                  ,
    cs_ext_discount_amt       decimal                  ,
    cs_ext_sales_price        decimal                  ,
    cs_ext_wholesale_cost     decimal                  ,
    cs_ext_list_price         decimal                  ,
    cs_ext_tax                decimal                  ,
    cs_coupon_amt             decimal                  ,
    cs_ext_ship_cost          decimal                  ,
    cs_net_paid               decimal                  ,
    cs_net_paid_inc_tax       decimal                  ,
    cs_net_paid_inc_ship      decimal                  ,
    cs_net_paid_inc_ship_tax  decimal                  ,
    cs_net_profit             decimal                  
);
create table store_sales
(
    ss_sold_date_sk           int                       ,
    ss_sold_time_sk           int                       ,
    ss_item_sk                int               ,
    ss_customer_sk            int                       ,
    ss_cdemo_sk               int                       ,
    ss_hdemo_sk               int                       ,
    ss_addr_sk                int                       ,
    ss_store_sk               int                       ,
    ss_promo_sk               int                       ,
    ss_ticket_number          int               ,
    ss_quantity               int                       ,
    ss_wholesale_cost         decimal                  ,
    ss_list_price             decimal                  ,
    ss_sales_price            decimal                  ,
    ss_ext_discount_amt       decimal                  ,
    ss_ext_sales_price        decimal                  ,
    ss_ext_wholesale_cost     decimal                  ,
    ss_ext_list_price         decimal                  ,
    ss_ext_tax                decimal                  ,
    ss_coupon_amt             decimal                  ,
    ss_net_paid               decimal                  ,
    ss_net_paid_inc_tax       decimal                  ,
    ss_net_profit             decimal                  
);
