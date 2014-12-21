SELECT  ws1.ws_order_number
	,SUM(ws1.ws_ext_ship_cost)
	,SUM(ws1.ws_net_profit)
FROM web_sales ws1
JOIN date_dim
JOIN customer_address
JOIN web_site
JOIN web_sales ws2 ON (
		ws1.ws_order_number = ws2.ws_order_number
		AND ws1.ws_warehouse_sk <> ws2.ws_warehouse_sk
		)
LEFT OUTER JOIN web_returns wr1 ON (ws1.ws_order_number = wr1.wr_order_number)
WHERE 
	wr_order_number IS NULL
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('2002-4-01', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2002-6-01', 'yyyy-MM-dd')
	AND ws1.ws_ship_date_sk = d_date_sk
	AND ws1.ws_ship_addr_sk = ca_address_sk
	AND ca_state = 'MN'
	AND ws1.ws_web_site_sk = web_site_sk
	AND web_company_name = 'pri'
ORDER BY  ws1.ws_order_number;
