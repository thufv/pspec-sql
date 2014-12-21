SELECT i1.i_product_name best_performing
	,i2.i_product_name worst_performing
FROM (
	SELECT *
	FROM (
		SELECT item_sk
		FROM (
			SELECT ss_item_sk item_sk
				,AVG(ss_net_profit) rank_col
			FROM store_sales ss1
			JOIN (
				SELECT AVG(ss_net_profit) rank_col
				FROM store_sales
				WHERE ss_store_sk = 6
					AND ss_hdemo_sk IS NULL
				GROUP BY ss_store_sk
				) table1
			WHERE ss_store_sk = 6
			GROUP BY ss_item_sk
			HAVING AVG(ss_net_profit) > 0.9 * rank_col
			) V1
		) V11
	) asceding
JOIN (
	SELECT *
	FROM (
		SELECT item_sk
		FROM (
			SELECT ss_item_sk item_sk
				,AVG(ss_net_profit) rank_col
			FROM store_sales ss1
			JOIN (
				SELECT AVG(ss_net_profit) rank_col
				FROM store_sales
				WHERE ss_store_sk = 6
					AND ss_hdemo_sk IS NULL
				GROUP BY ss_store_sk
				) table2
			WHERE ss_store_sk = 6
			GROUP BY ss_item_sk
			HAVING AVG(ss_net_profit) > 0.9 * rank_col
			) V2
		) V21
	) descending
JOIN item i1
JOIN item i2
WHERE i1.i_item_sk = asceding.item_sk
	AND i2.i_item_sk = descending.item_sk;
