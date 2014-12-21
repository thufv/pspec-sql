SELECT i_item_id
	,i_item_desc
	,i_current_price
FROM item
JOIN inventory
JOIN date_dim
JOIN store_sales
WHERE i_current_price BETWEEN 38
		AND 38 + 30
	AND inv_item_sk = i_item_sk
	AND d_date_sk = inv_date_sk
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-01-06', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1998-03-06', 'yyyy-MM-dd')
	AND i_manufact_id IN (
		198
		,999
		,168
		,196
		)
	AND inv_quantity_on_hand BETWEEN 100
		AND 500
	AND ss_item_sk = i_item_sk
GROUP BY i_item_id
	,i_item_desc
	,i_current_price
ORDER BY i_item_id;
