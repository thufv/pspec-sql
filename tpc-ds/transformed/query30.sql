SELECT c_customer_id
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
FROM (
	SELECT wr_returning_customer_sk AS ctr_customer_sk
		,ca_state AS ctr_state
		,SUM(wr_return_amt) AS ctr_total_return
	FROM web_returns
	JOIN date_dim
	JOIN customer_address
	WHERE wr_returned_date_sk = d_date_sk
		AND d_year = 2000
		AND wr_returning_addr_sk = ca_address_sk
	GROUP BY wr_returning_customer_sk
		,ca_state
	) ctr1
JOIN customer_address
JOIN customer
JOIN (
	SELECT AVG(ctr_total_return) * 1.2 AS tmp0
	FROM (
		SELECT wr_returning_customer_sk AS ctr_customer_sk
			,ca_state AS ctr_state
			,SUM(wr_return_amt) AS ctr_total_return
		FROM web_returns
		JOIN date_dim
		JOIN customer_address
		WHERE wr_returned_date_sk = d_date_sk
			AND d_year = 2000
			AND wr_returning_addr_sk = ca_address_sk
		GROUP BY wr_returning_customer_sk
			,ca_state
		) ctr2
	) tmp1
WHERE ctr1.ctr_total_return > tmp0
	AND ca_address_sk = c_current_addr_sk
	AND ca_state = 'IL'
	AND ctr1.ctr_customer_sk = c_customer_sk
ORDER BY c_customer_id
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
