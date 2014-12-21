SELECT w_warehouse_name
	,sm_type
	,web_name
	,SUM(CASE 
			WHEN (ws_ship_date_sk - ws_sold_date_sk <= 30)
				THEN 1
			ELSE 0
			END)
	,SUM(CASE 
			WHEN (ws_ship_date_sk - ws_sold_date_sk > 30)
				AND (ws_ship_date_sk - ws_sold_date_sk <= 60)
				THEN 1
			ELSE 0
			END)
	,SUM(CASE 
			WHEN (ws_ship_date_sk - ws_sold_date_sk > 60)
				AND (ws_ship_date_sk - ws_sold_date_sk <= 90)
				THEN 1
			ELSE 0
			END)
	,SUM(CASE 
			WHEN (ws_ship_date_sk - ws_sold_date_sk > 90)
				AND (ws_ship_date_sk - ws_sold_date_sk <= 120)
				THEN 1
			ELSE 0
			END)
	,SUM(CASE 
			WHEN (ws_ship_date_sk - ws_sold_date_sk > 120)
				THEN 1
			ELSE 0
			END)
FROM web_sales
JOIN warehouse
JOIN ship_mode
JOIN web_site
JOIN date_dim
WHERE d_month_seq BETWEEN 1212
		AND 1212 + 11
	AND ws_ship_date_sk = d_date_sk
	AND ws_warehouse_sk = w_warehouse_sk
	AND ws_ship_mode_sk = sm_ship_mode_sk
	AND ws_web_site_sk = web_site_sk
GROUP BY w_warehouse_name
	,sm_type
	,web_name
ORDER BY w_warehouse_name
	,sm_type
	,web_name;
