SELECT  ws1.ws_order_number
	,SUM(ws_ext_ship_cost) 
	,SUM(ws_net_profit) 
FROM web_sales ws1
JOIN date_dim
JOIN customer_address
JOIN web_site
JOIN (
	SELECT ws1.ws_order_number
		,ws1.ws_warehouse_sk wh1
		,ws2.ws_warehouse_sk wh2
	FROM web_sales ws1
	JOIN web_sales ws2
	WHERE ws1.ws_order_number = ws2.ws_order_number
		AND ws1.ws_warehouse_sk <> ws2.ws_warehouse_sk
	) ws_wh ON ws1.ws_order_number = ws_wh.ws_order_number
JOIN (
	SELECT wr_order_number
	FROM web_returns
	JOIN (
		SELECT ws1.ws_order_number
			,ws1.ws_warehouse_sk wh1
			,ws2.ws_warehouse_sk wh2
		FROM web_sales ws1
		JOIN web_sales ws2
		WHERE ws1.ws_order_number = ws2.ws_order_number
			AND ws1.ws_warehouse_sk <> ws2.ws_warehouse_sk
		) ws_wh
	) table1 ON ws1.ws_order_number = table1.wr_order_number
WHERE ws1.ws_ship_date_sk = d_date_sk
	AND ws1.ws_ship_addr_sk = ca_address_sk
	AND ca_state = 'NE'
	AND ws1.ws_web_site_sk = web_site_sk
	AND web_company_name = 'pri'
ORDER BY  ws1.ws_order_number;
