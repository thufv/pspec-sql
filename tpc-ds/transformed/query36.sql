SELECT SUM(ss_net_profit) / SUM(ss_ext_sales_price) AS gross_margin
	,i_category
	,i_class
	,i_category + i_class AS lochierarchy
FROM store_sales
JOIN date_dim d1
JOIN item
JOIN store
WHERE d1.d_year = 2001
	AND d1.d_date_sk = ss_sold_date_sk
	AND i_item_sk = ss_item_sk
	AND s_store_sk = ss_store_sk
	AND s_state IN (
		'TN'
		,'TN'
		,'TN'
		,'TN'
		,'TN'
		,'TN'
		,'TN'
		,'TN'
		)
GROUP BY i_category
	,i_class
ORDER BY lochierarchy DESC
	,CASE 
		WHEN lochierarchy = 0
			THEN i_category
		END;
