SELECT c_last_name
	,c_first_name
	,s_store_name
	,SUM(netpaid) paid
FROM (
	SELECT c_last_name
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
	FROM store_sales
	JOIN store_returns
	JOIN store
	JOIN item
	JOIN customer
	JOIN customer_address
	WHERE ss_ticket_number = sr_ticket_number
		AND ss_item_sk = sr_item_sk
		AND ss_customer_sk = c_customer_sk
		AND ss_item_sk = i_item_sk
		AND ss_store_sk = s_store_sk
		AND c_birth_country = UPPER(ca_country)
		AND s_zip = ca_zip
		AND s_market_id = 8
	GROUP BY c_last_name
		,c_first_name
		,s_store_name
		,ca_state
		,s_state
		,i_color
		,i_current_price
		,i_manager_id
		,i_units
		,i_size
	) ssales
JOIN (
	SELECT 0.05 * AVG(netpaid) AS tmp0
	FROM (
		SELECT c_last_name
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
		FROM store_sales
		JOIN store_returns
		JOIN store
		JOIN item
		JOIN customer
		JOIN customer_address
		WHERE ss_ticket_number = sr_ticket_number
			AND ss_item_sk = sr_item_sk
			AND ss_customer_sk = c_customer_sk
			AND ss_item_sk = i_item_sk
			AND ss_store_sk = s_store_sk
			AND c_birth_country = UPPER(ca_country)
			AND s_zip = ca_zip
			AND s_market_id = 8
		GROUP BY c_last_name
			,c_first_name
			,s_store_name
			,ca_state
			,s_state
			,i_color
			,i_current_price
			,i_manager_id
			,i_units
			,i_size
		) ssales
	) tmp1
WHERE i_color = 'lawn'
GROUP BY c_last_name
	,c_first_name
	,s_store_name
HAVING SUM(netpaid) > tmp1.tmp0;
