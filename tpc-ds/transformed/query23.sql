SELECT c_last_name
	,c_first_name
	,sales
FROM (
	SELECT c_last_name
		,c_first_name
		,SUM(cs_quantity * cs_list_price) sales
	FROM catalog_sales
	JOIN customer
	JOIN date_dim
	JOIN (
		SELECT SUBSTR(i_item_desc, 1, 30) itemdesc
			,i_item_sk item_sk
			,d_date solddate
			,COUNT(*) cnt
		FROM store_sales
		JOIN date_dim
		JOIN item
		WHERE ss_sold_date_sk = d_date_sk
			AND ss_item_sk = i_item_sk
			AND d_year IN (
				1999
				,1999 + 1
				,1999 + 2
				,1999 + 3
				)
		GROUP BY SUBSTR(i_item_desc, 1, 30)
			,i_item_sk
			,d_date
		HAVING COUNT(*) > 4
		) frequent_ss_items ON cs_item_sk = frequent_ss_items.item_sk
	JOIN (
		SELECT c_customer_sk
			,SUM(ss_quantity * ss_sales_price) ssales
			,tpcds_cmax
		FROM store_sales
		JOIN customer
		JOIN (
			SELECT MAX(csales) tpcds_cmax
			FROM (
				SELECT c_customer_sk
					,SUM(ss_quantity * ss_sales_price) csales
				FROM store_sales
				JOIN customer
				JOIN date_dim
				WHERE ss_customer_sk = customer.c_customer_sk
					AND ss_sold_date_sk = d_date_sk
					AND d_year IN (
						1999
						,1999 + 1
						,1999 + 2
						,1999 + 3
						)
				GROUP BY customer.c_customer_sk
				) x
			) max_store_sales
		WHERE ss_customer_sk = customer.c_customer_sk
		GROUP BY customer.c_customer_sk
		HAVING SUM(ss_quantity * ss_sales_price) > (95 / 100.0) * tpcds_cmax
		) best_ss_customer ON cs_bill_customer_sk = best_ss_customer.c_customer_sk
	WHERE d_year = 1999
		AND d_moy = 6
		AND cs_sold_date_sk = d_date_sk
		AND cs_bill_customer_sk = customer.c_customer_sk
	GROUP BY c_last_name
		,c_first_name
	
	UNION ALL
	
	SELECT c_last_name
		,c_first_name
		,SUM(ws_quantity * ws_list_price) sales
	FROM web_sales
	JOIN customer
	JOIN date_dim
	JOIN (
		SELECT SUBSTR(i_item_desc, 1, 30) itemdesc
			,i_item_sk item_sk
			,d_date solddate
			,COUNT(*) cnt
		FROM store_sales
		JOIN date_dim
		JOIN item
		WHERE ss_sold_date_sk = d_date_sk
			AND ss_item_sk = i_item_sk
			AND d_year IN (
				1999
				,1999 + 1
				,1999 + 2
				,1999 + 3
				)
		GROUP BY SUBSTR(i_item_desc, 1, 30)
			,i_item_sk
			,d_date
		HAVING COUNT(*) > 4
		) frequent_ss_items ON ws_item_sk = frequent_ss_items.item_sk
	JOIN (
		SELECT c_customer_sk
			,SUM(ss_quantity * ss_sales_price) ssales
			,tpcds_cmax
		FROM store_sales
		JOIN customer
		JOIN (
			SELECT MAX(csales) tpcds_cmax
			FROM (
				SELECT c_customer_sk
					,SUM(ss_quantity * ss_sales_price) csales
				FROM store_sales
				JOIN customer
				JOIN date_dim
				WHERE ss_customer_sk = customer.c_customer_sk
					AND ss_sold_date_sk = d_date_sk
					AND d_year IN (
						1999
						,1999 + 1
						,1999 + 2
						,1999 + 3
						)
				GROUP BY customer.c_customer_sk
				) x
			) max_store_sales
		WHERE ss_customer_sk = customer.c_customer_sk
		GROUP BY customer.c_customer_sk
		HAVING SUM(ss_quantity * ss_sales_price) > (95 / 100.0) * tpcds_cmax
		) best_ss_customer ON ws_bill_customer_sk = best_ss_customer.c_customer_sk
	WHERE d_year = 1999
		AND d_moy = 6
		AND ws_sold_date_sk = d_date_sk
		AND ws_bill_customer_sk = customer.c_customer_sk
	GROUP BY c_last_name
		,c_first_name
	) y
ORDER BY c_last_name
	,c_first_name
	,sales;
