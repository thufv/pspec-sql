SELECT *
FROM (
	SELECT item_sk
		,d_date
		,web_sales
		,store_sales
		,MAX(web_sales) AS web_cumulative
		,MAX(store_sales) AS store_cumulative
	FROM (
		SELECT CASE 
				WHEN web.item_sk IS NOT NULL
					THEN web.item_sk
				ELSE store.item_sk
				END item_sk
			,CASE 
				WHEN web.d_date IS NOT NULL
					THEN web.d_date
				ELSE store.d_date
				END d_date
			,web.cume_sales web_sales
			,store.cume_sales store_sales
		FROM (
			SELECT ws_item_sk item_sk
				,d_date
				,SUM(SUM(ws_sales_price)) AS cume_sales
			FROM web_sales
			JOIN date_dim
			WHERE ws_sold_date_sk = d_date_sk
				AND d_month_seq BETWEEN 1200
					AND 1200 + 11
				AND ws_item_sk IS NOT NULL
			GROUP BY ws_item_sk
				,d_date
			) web
		FULL OUTER JOIN (
			SELECT ss_item_sk item_sk
				,d_date
				,SUM(SUM(ss_sales_price)) AS cume_sales
			FROM store_sales
			JOIN date_dim
			WHERE ss_sold_date_sk = d_date_sk
				AND d_month_seq BETWEEN 1200
					AND 1200 + 11
				AND ss_item_sk IS NOT NULL
			GROUP BY ss_item_sk
				,d_date
			) store ON (
				web.item_sk = store.item_sk
				AND web.d_date = store.d_date
				)
		) x
	) y
WHERE web_cumulative > store_cumulative
ORDER BY item_sk
	,d_date;
