SELECT cd_gender
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
FROM customer c
JOIN customer_address ca
JOIN customer_demographics
JOIN catalog_sales
JOIN web_sales
JOIN store_sales
JOIN date_dim ON (
		c.c_customer_sk = ss_customer_sk
		AND (
			(
				c.c_customer_sk = cs_ship_customer_sk
				AND ws_sold_date_sk = d_date_sk
				)
			OR (
				c.c_customer_sk = ws_bill_customer_sk
				AND cs_sold_date_sk = d_date_sk
				)
			)
		AND ss_sold_date_sk = d_date_sk
		AND d_year = 2000
		AND d_moy BETWEEN 2
			AND 2 + 3
		)
WHERE c.c_current_addr_sk = ca.ca_address_sk
	AND ca_county IN (
		'Yellowstone County'
		,'Montgomery County'
		,'Divide County'
		,'Cedar County'
		,'Manassas Park city'
		)
	AND cd_demo_sk = c.c_current_cdemo_sk
GROUP BY cd_gender
	,cd_marital_status
	,cd_education_status
	,cd_purchase_estimate
	,cd_credit_rating
	,cd_dep_count
	,cd_dep_employed_count
	,cd_dep_college_count
ORDER BY cd_gender
	,cd_marital_status
	,cd_education_status
	,cd_purchase_estimate
	,cd_credit_rating
	,cd_dep_count
	,cd_dep_employed_count
	,cd_dep_college_count;
