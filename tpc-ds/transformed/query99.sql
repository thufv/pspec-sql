SELECT w_warehouse_name
	,sm_type
	,cc_name
	,SUM(CASE 
			WHEN (cs_ship_date_sk - cs_sold_date_sk <= 30)
				THEN 1
			ELSE 0
			END)
	,SUM(CASE 
			WHEN (cs_ship_date_sk - cs_sold_date_sk > 30)
				AND (cs_ship_date_sk - cs_sold_date_sk <= 60)
				THEN 1
			ELSE 0
			END)
	,SUM(CASE 
			WHEN (cs_ship_date_sk - cs_sold_date_sk > 60)
				AND (cs_ship_date_sk - cs_sold_date_sk <= 90)
				THEN 1
			ELSE 0
			END)
	,SUM(CASE 
			WHEN (cs_ship_date_sk - cs_sold_date_sk > 90)
				AND (cs_ship_date_sk - cs_sold_date_sk <= 120)
				THEN 1
			ELSE 0
			END)
	,SUM(CASE 
			WHEN (cs_ship_date_sk - cs_sold_date_sk > 120)
				THEN 1
			ELSE 0
			END)
FROM catalog_sales
JOIN warehouse
JOIN ship_mode
JOIN call_center
JOIN date_dim
WHERE d_month_seq BETWEEN 1183
		AND 1183 + 11
	AND cs_ship_date_sk = d_date_sk
	AND cs_warehouse_sk = w_warehouse_sk
	AND cs_ship_mode_sk = sm_ship_mode_sk
	AND cs_call_center_sk = cc_call_center_sk
GROUP BY w_warehouse_name
	,sm_type
	,cc_name
ORDER BY w_warehouse_name
	,sm_type
	,cc_name;
