SELECT c_customer_id AS customer_id
	,CONCAT (
		c_last_name
		,','
		,c_first_name
		) AS customername
FROM customer
JOIN customer_address
JOIN customer_demographics
JOIN household_demographics
JOIN income_band
JOIN store_returns
WHERE ca_city = 'Antioch'
	AND c_current_addr_sk = ca_address_sk
	AND ib_lower_bound >= 9901
	AND ib_upper_bound <= 9901 + 50000
	AND ib_income_band_sk = hd_income_band_sk
	AND cd_demo_sk = c_current_cdemo_sk
	AND hd_demo_sk = c_current_hdemo_sk
	AND sr_cdemo_sk = cd_demo_sk
ORDER BY c_customer_id;
