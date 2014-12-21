SELECT i_item_id
	,i_item_desc
	,i_current_price
FROM item
JOIN inventory
JOIN date_dim
JOIN catalog_sales
WHERE i_current_price BETWEEN 42
		AND 42 + 30
	AND inv_item_sk = i_item_sk
	AND d_date_sk = inv_date_sk
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('2002-01-18', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2002-03-18', 'yyyy-MM-dd')
	AND i_manufact_id IN (
		744
		,691
		,853
		,946
		)
	AND inv_quantity_on_hand BETWEEN 100
		AND 500
	AND cs_item_sk = i_item_sk
GROUP BY i_item_id
	,i_item_desc
	,i_current_price
ORDER BY i_item_id;
