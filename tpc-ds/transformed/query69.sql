SELECT cd_gender
	,cd_marital_status
	,cd_education_status
	,COUNT(*) cnt1
	,cd_purchase_estimate
	,COUNT(*) cnt2
	,cd_credit_rating
	,COUNT(*) cnt3
FROM customer c
JOIN customer_address ca
JOIN customer_demographics
JOIN date_dim
JOIN store_sales ON (
		c.c_customer_sk = ss_customer_sk
		AND ss_sold_date_sk = d_date_sk
		AND d_year = 2004
		AND d_moy BETWEEN 3
			AND 3 + 2
		)
LEFT OUTER JOIN web_sales ON (
		c.c_customer_sk = ws_bill_customer_sk
		AND ws_sold_date_sk = d_date_sk
		AND d_year = 2004
		AND d_moy BETWEEN 3
			AND 3 + 2
		)
LEFT OUTER JOIN catalog_sales ON (
		c.c_customer_sk = cs_ship_customer_sk
		AND cs_sold_date_sk = d_date_sk
		AND d_year = 2004
		AND d_moy BETWEEN 3
			AND 3 + 2
		)
WHERE ws_bill_customer_sk IS NULL
	AND cs_ship_customer_sk IS NULL
	AND c.c_current_addr_sk = ca.ca_address_sk
	AND ca_state IN (
		'SD'
		,'KY'
		,'MO'
		)
	AND cd_demo_sk = c.c_current_cdemo_sk
GROUP BY cd_gender
	,cd_marital_status
	,cd_education_status
	,cd_purchase_estimate
	,cd_credit_rating
ORDER BY cd_gender
	,cd_marital_status
	,cd_education_status
	,cd_purchase_estimate
	,cd_credit_rating;
