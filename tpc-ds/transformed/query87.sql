SELECT COUNT(*)
FROM (
	SELECT *
	FROM (
		SELECT DISTINCT c_last_name
			,c_first_name
			,d_date
		FROM store_sales
			JOIN date_dim
			JOIN customer
		WHERE store_sales.ss_sold_date_sk = date_dim.d_date_sk
			AND store_sales.ss_customer_sk = customer.c_customer_sk
			AND d_month_seq BETWEEN 1214
				AND 1214 + 11
		) table1
	LEFT OUTER JOIN (
		SELECT DISTINCT c_last_name
			,c_first_name
			,d_date
		FROM catalog_sales
			JOIN date_dim
			JOIN customer
		WHERE catalog_sales.cs_sold_date_sk = date_dim.d_date_sk
			AND catalog_sales.cs_bill_customer_sk = customer.c_customer_sk
			AND d_month_seq BETWEEN 1214
				AND 1214 + 11
		) table2 ON (
			table1.c_last_name = table2.c_last_name
			AND table1.c_first_name = table2.c_first_name
			AND table1.d_date = table2.d_date
			)
	LEFT OUTER JOIN (
		SELECT DISTINCT c_last_name
			,c_first_name
			,d_date
		FROM web_sales
			JOIN date_dim
			JOIN customer
		WHERE web_sales.ws_sold_date_sk = date_dim.d_date_sk
			AND web_sales.ws_bill_customer_sk = customer.c_customer_sk
			AND d_month_seq BETWEEN 1214
				AND 1214 + 11
		) table3 ON (
			table1.c_last_name = table3.c_last_name
			AND table1.c_first_name = table3.c_first_name
			AND table1.d_date = table3.d_date
			)
	WHERE table2.c_last_name IS NULL
		OR table2.c_first_name IS NULL
		OR table2.d_date IS NULL
		OR table3.c_last_name IS NULL
		OR table3.c_first_name IS NULL
		OR table3.d_date IS NULL
	) cool_cust;
