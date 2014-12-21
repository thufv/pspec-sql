SELECT inv1.w_warehouse_sk
	,inv1.i_item_sk
	,inv1.d_moy
	,inv1.mean
	,inv2.w_warehouse_sk
	,inv2.i_item_sk
	,inv2.d_moy
	,inv2.mean
FROM (
	SELECT w_warehouse_name
		,w_warehouse_sk
		,i_item_sk
		,d_moy
		,stdev
		,mean
	FROM (
		SELECT w_warehouse_name
			,w_warehouse_sk
			,i_item_sk
			,d_moy
			,stddev_samp(inv_quantity_on_hand) stdev
			,AVG(inv_quantity_on_hand) mean
		FROM inventory
		JOIN item
		JOIN warehouse
		JOIN date_dim
		WHERE inv_item_sk = i_item_sk
			AND inv_warehouse_sk = w_warehouse_sk
			AND inv_date_sk = d_date_sk
			AND d_year = 1999
		GROUP BY w_warehouse_name
			,w_warehouse_sk
			,i_item_sk
			,d_moy
		) foo
	WHERE CASE mean
			WHEN 0
				THEN 0
			ELSE stdev / mean
			END > 1
	) inv1
JOIN (
	SELECT w_warehouse_name
		,w_warehouse_sk
		,i_item_sk
		,d_moy
		,stdev
		,mean
	FROM (
		SELECT w_warehouse_name
			,w_warehouse_sk
			,i_item_sk
			,d_moy
			,stddev_samp(inv_quantity_on_hand) stdev
			,AVG(inv_quantity_on_hand) mean
		FROM inventory
		JOIN item
		JOIN warehouse
		JOIN date_dim
		WHERE inv_item_sk = i_item_sk
			AND inv_warehouse_sk = w_warehouse_sk
			AND inv_date_sk = d_date_sk
			AND d_year = 1999
		GROUP BY w_warehouse_name
			,w_warehouse_sk
			,i_item_sk
			,d_moy
		) foo
	WHERE CASE mean
			WHEN 0
				THEN 0
			ELSE stdev / mean
			END > 1
	) inv2
WHERE inv1.i_item_sk = inv2.i_item_sk
	AND inv1.w_warehouse_sk = inv2.w_warehouse_sk
	AND inv1.d_moy = 1
	AND inv2.d_moy = 1 + 1
ORDER BY inv1.w_warehouse_sk
	,inv1.i_item_sk
	,inv1.d_moy
	,inv1.mean
	,inv2.d_moy
	,inv2.mean
;