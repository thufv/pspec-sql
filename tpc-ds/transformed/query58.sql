SELECT ss_items.item_id
	,ss_item_rev
	,ss_item_rev / (ss_item_rev + cs_item_rev + ws_item_rev) / 3 * 100 ss_dev
	,cs_item_rev
	,cs_item_rev / (ss_item_rev + cs_item_rev + ws_item_rev) / 3 * 100 cs_dev
	,ws_item_rev
	,ws_item_rev / (ss_item_rev + cs_item_rev + ws_item_rev) / 3 * 100 ws_dev
	,(ss_item_rev + cs_item_rev + ws_item_rev) / 3 average
FROM (
	SELECT i_item_id item_id
		,SUM(ss_ext_sales_price) ss_item_rev
	FROM store_sales
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT d_date AS tmp3
		FROM date_dim
		JOIN (
			SELECT d_week_seq AS tmp0
			FROM date_dim
			WHERE unix_timestamp(d_date, 'yyyy-MM-dd') = unix_timestamp('2002-03-09', 'yyyy-MM-dd')
			) tmp1
		ON d_week_seq = tmp0
		) tmp2 ON d_date = tmp3
	WHERE ss_item_sk = i_item_sk
		AND ss_sold_date_sk = d_date_sk
	GROUP BY i_item_id
	) ss_items
JOIN (
	SELECT i_item_id item_id
		,SUM(cs_ext_sales_price) cs_item_rev
	FROM catalog_sales
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT d_date AS tmp3
		FROM date_dim
		JOIN (
			SELECT d_week_seq AS tmp0
			FROM date_dim
			WHERE unix_timestamp(d_date, 'yyyy-MM-dd') = unix_timestamp('2002-03-09', 'yyyy-MM-dd')
			) tmp1
		ON d_week_seq = tmp0
		) tmp2 ON d_date = tmp3
	WHERE cs_item_sk = i_item_sk
		AND cs_sold_date_sk = d_date_sk
	GROUP BY i_item_id
	) cs_items
JOIN (
	SELECT i_item_id item_id
		,SUM(ws_ext_sales_price) ws_item_rev
	FROM web_sales
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT d_date AS tmp3
		FROM date_dim
		JOIN (
			SELECT d_week_seq AS tmp0
			FROM date_dim
			WHERE unix_timestamp(d_date, 'yyyy-MM-dd') = unix_timestamp('2002-03-09', 'yyyy-MM-dd')
			) tmp1
		ON d_week_seq = tmp0
		) tmp2 ON d_date = tmp3
	WHERE ws_item_sk = i_item_sk
		AND ws_sold_date_sk = d_date_sk
	GROUP BY i_item_id
	) ws_items
WHERE ss_items.item_id = cs_items.item_id
	AND ss_items.item_id = ws_items.item_id
	AND ss_item_rev BETWEEN 0.9 * cs_item_rev
		AND 1.1 * cs_item_rev
	AND ss_item_rev BETWEEN 0.9 * ws_item_rev
		AND 1.1 * ws_item_rev
	AND cs_item_rev BETWEEN 0.9 * ss_item_rev
		AND 1.1 * ss_item_rev
	AND cs_item_rev BETWEEN 0.9 * ws_item_rev
		AND 1.1 * ws_item_rev
	AND ws_item_rev BETWEEN 0.9 * ss_item_rev
		AND 1.1 * ss_item_rev
	AND ws_item_rev BETWEEN 0.9 * cs_item_rev
		AND 1.1 * cs_item_rev
ORDER BY item_id
	,ss_item_rev;
