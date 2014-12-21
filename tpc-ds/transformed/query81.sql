SELECT c_customer_id
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
FROM (
	SELECT cr_returning_customer_sk AS ctr_customer_sk
		,ca_state AS ctr_state
		,SUM(cr_return_amt_inc_tax) AS ctr_total_return
	FROM catalog_returns
	JOIN date_dim
	JOIN customer_address
	WHERE cr_returned_date_sk = d_date_sk
		AND d_year = 1998
		AND cr_returning_addr_sk = ca_address_sk
	GROUP BY cr_returning_customer_sk
		,ca_state
	) ctr1
JOIN customer_address
JOIN customer
JOIN (
	SELECT AVG(ctr_total_return) * 1.2 AS tmp0
	FROM (
		SELECT cr_returning_customer_sk AS ctr_customer_sk
			,ca_state AS ctr_state
			,SUM(cr_return_amt_inc_tax) AS ctr_total_return
		FROM catalog_returns
		JOIN date_dim
		JOIN customer_address
		WHERE cr_returned_date_sk = d_date_sk
			AND d_year = 1998
			AND cr_returning_addr_sk = ca_address_sk
		GROUP BY cr_returning_customer_sk
			,ca_state
		) ctr2
	
	) tmp1
WHERE ctr1.ctr_total_return > tmp0
	AND ca_address_sk = c_current_addr_sk
	AND ca_state = 'TN'
	AND ctr1.ctr_customer_sk = c_customer_sk
ORDER BY c_customer_id
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
