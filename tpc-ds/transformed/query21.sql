SELECT *
FROM (
	SELECT w_warehouse_name
		,i_item_id
		,SUM(CASE 
				WHEN unix_timestamp(d_date, 'yyyy-MM-dd') < unix_timestamp('2001-03-14', 'yyyy-MM-dd') 
					THEN inv_quantity_on_hand
				ELSE 0
				END) AS inv_before
		,SUM(CASE 
				WHEN unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2001-03-14', 'yyyy-MM-dd') 
					THEN inv_quantity_on_hand
				ELSE 0
				END) AS inv_after
	FROM inventory
	JOIN warehouse
	JOIN item
	JOIN date_dim
	WHERE i_current_price BETWEEN 0.99
			AND 1.49
		AND i_item_sk = inv_item_sk
		AND inv_warehouse_sk = w_warehouse_sk
		AND inv_date_sk = d_date_sk
		AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('2001-02-14', 'yyyy-MM-dd')
		AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2001-04-14', 'yyyy-MM-dd')
	GROUP BY w_warehouse_name
		,i_item_id
	) x
WHERE (
		CASE 
			WHEN inv_before > 0
				THEN inv_after / inv_before
			ELSE NULL
			END
		) BETWEEN 2.0 / 3.0
		AND 3.0 / 2.0
ORDER BY w_warehouse_name
	,i_item_id;
