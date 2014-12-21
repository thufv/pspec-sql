SELECT w_state
	,i_item_id
	,SUM(CASE 
			WHEN unix_timestamp(d_date, 'yyyy-MM-dd') < unix_timestamp('1998-03-08', 'yyyy-MM-dd')
				THEN cs_sales_price - COALESCE(cr_refunded_cash, 0)
			ELSE 0
			END) AS sales_before
	,SUM(CASE 
			WHEN unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-03-08', 'yyyy-MM-dd')
				THEN cs_sales_price - COALESCE(cr_refunded_cash, 0)
			ELSE 0
			END) AS sales_after
FROM catalog_sales
LEFT OUTER JOIN catalog_returns ON (
		cs_order_number = cr_order_number
		AND cs_item_sk = cr_item_sk
		)
JOIN warehouse
JOIN item
JOIN date_dim
WHERE i_current_price BETWEEN 0.99
		AND 1.49
	AND i_item_sk = cs_item_sk
	AND cs_warehouse_sk = w_warehouse_sk
	AND cs_sold_date_sk = d_date_sk
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-02-08', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1998-04-18', 'yyyy-MM-dd')
GROUP BY w_state
	,i_item_id
ORDER BY w_state
	,i_item_id;
