SELECT SUM(ws_net_paid) AS total_sum
	,i_category
	,i_class
	,i_category + i_class AS lochierarchy
FROM web_sales
JOIN date_dim d1
JOIN item
WHERE d1.d_month_seq BETWEEN 1211
		AND 1211 + 11
	AND d1.d_date_sk = ws_sold_date_sk
	AND i_item_sk = ws_item_sk
GROUP BY i_category
	,i_class
ORDER BY lochierarchy DESC
	,CASE 
		WHEN lochierarchy = 0
			THEN i_category
		END;
