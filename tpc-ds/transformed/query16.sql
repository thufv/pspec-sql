SELECT cs1.cs_order_number
	,SUM(cs1.cs_ext_ship_cost)
	,SUM(cs1.cs_net_profit)
FROM catalog_sales cs1
JOIN date_dim
JOIN customer_address
JOIN call_center
JOIN catalog_sales cs2 ON (
		cs1.cs_order_number = cs2.cs_order_number
		AND cs1.cs_warehouse_sk <> cs2.cs_warehouse_sk
		)
LEFT OUTER JOIN catalog_returns cr1 ON (cs1.cs_order_number = cr1.cr_order_number)
WHERE cr1.cr_order_number IS NULL
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('2000-3-01', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2000-5-01', 'yyyy-MM-dd')
	AND cs1.cs_ship_date_sk = d_date_sk
	AND cs1.cs_ship_addr_sk = ca_address_sk
	AND ca_state = 'MN'
	AND cs1.cs_call_center_sk = cc_call_center_sk
	AND cc_county IN (
		'Williamson County'
		,'Williamson County'
		,'Williamson County'
		,'Williamson County'
		,'Williamson County'
		)
ORDER BY cs1.cs_order_number;
