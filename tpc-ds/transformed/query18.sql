SELECT i_item_id
	,ca_country
	,ca_state
	,ca_county
	,AVG(cs_quantity) agg1
	,AVG(cs_list_price) agg2
	,AVG(cs_coupon_amt) agg3
	,AVG(cs_sales_price) agg4
	,AVG(cs_net_profit) agg5
	,AVG(c_birth_year) agg6
	,AVG(cd1.cd_dep_count) agg7
FROM catalog_sales
JOIN customer_demographics cd1
JOIN customer_demographics cd2
JOIN customer
JOIN customer_address
JOIN date_dim
JOIN item
WHERE cs_sold_date_sk = d_date_sk
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
GROUP BY i_item_id
	,ca_country
	,ca_state
	,ca_county
ORDER BY ca_country
	,ca_state
	,ca_county
	,i_item_id;
