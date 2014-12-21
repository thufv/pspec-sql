SELECT cc_call_center_id Call_Center
	,cc_name Call_Center_Name
	,cc_manager Manager
FROM call_center
JOIN catalog_returns
JOIN date_dim
JOIN customer
JOIN customer_address
JOIN customer_demographics
JOIN household_demographics
WHERE cr_call_center_sk = cc_call_center_sk
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
	AND ca_gmt_offset = - 6
GROUP BY cc_call_center_id
	,cc_name
	,cc_manager
	,cd_marital_status
	,cd_education_status;