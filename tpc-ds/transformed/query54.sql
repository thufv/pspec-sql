SELECT segment
	,COUNT(*) AS num_customers
	,segment * 50 AS segment_base
FROM (
	SELECT (revenue / 50) AS segment
	FROM (
		SELECT c_customer_sk
			,SUM(ss_ext_sales_price) AS revenue
		FROM (
			SELECT c_customer_sk
				,c_current_addr_sk
			FROM (
				SELECT cs_sold_date_sk sold_date_sk
					,cs_bill_customer_sk customer_sk
					,cs_item_sk item_sk
				FROM catalog_sales
				
				UNION ALL
				
				SELECT ws_sold_date_sk sold_date_sk
					,ws_bill_customer_sk customer_sk
					,ws_item_sk item_sk
				FROM web_sales
				) cs_or_ws_sales
			JOIN item
			JOIN date_dim
			JOIN customer
			WHERE sold_date_sk = d_date_sk
				AND item_sk = i_item_sk
				AND i_category = 'Children'
				AND i_class = 'toddlers'
				AND c_customer_sk = cs_or_ws_sales.customer_sk
				AND d_moy = 5
				AND d_year = 2001
			) my_customers
		JOIN store_sales
		JOIN customer_address
		JOIN store
		JOIN date_dim
		JOIN (
			SELECT d_month_seq + 1 AS tmp0
			FROM date_dim
			WHERE d_year = 2001
				AND d_moy = 5
			) tmp1
		JOIN (
			SELECT d_month_seq + 3 AS tmp2
			FROM date_dim
			WHERE d_year = 2001
				AND d_moy = 5
			) tmp3
		WHERE c_current_addr_sk = ca_address_sk
			AND ca_county = s_county
			AND ca_state = s_state
			AND ss_sold_date_sk = d_date_sk
			AND c_customer_sk = ss_customer_sk
			AND d_month_seq BETWEEN tmp0
				AND tmp2
		GROUP BY c_customer_sk
		) my_revenue
	) segments
GROUP BY segment
ORDER BY segment
	,num_customers;
