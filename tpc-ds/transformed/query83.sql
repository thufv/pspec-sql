SELECT sr_items.item_id
	,sr_item_qty
	,sr_item_qty / (sr_item_qty + cr_item_qty + wr_item_qty) / 3.0 * 100 sr_dev
	,cr_item_qty
	,cr_item_qty / (sr_item_qty + cr_item_qty + wr_item_qty) / 3.0 * 100 cr_dev
	,wr_item_qty
	,wr_item_qty / (sr_item_qty + cr_item_qty + wr_item_qty) / 3.0 * 100 wr_dev
	,(sr_item_qty + cr_item_qty + wr_item_qty) / 3.0 average
FROM (
	SELECT i_item_id item_id
		,SUM(sr_return_quantity) sr_item_qty
	FROM store_returns
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT d_date
		FROM date_dim
		JOIN (
			SELECT d_week_seq
			FROM date_dim
			WHERE d_date IN (
					'1999-04-17'
					,'1999-10-04'
					,'1999-11-10'
					)
			) tmp0
		) tmp1
	WHERE sr_item_sk = i_item_sk
		AND sr_returned_date_sk = d_date_sk
	GROUP BY i_item_id
	) sr_items
JOIN (
	SELECT i_item_id item_id
		,SUM(cr_return_quantity) cr_item_qty
	FROM catalog_returns
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT d_date
		FROM date_dim
		JOIN (
			SELECT d_week_seq
			FROM date_dim
			WHERE d_date IN (
					'1999-04-17'
					,'1999-10-04'
					,'1999-11-10'
					)
			) tmp0
		) tmp1
	WHERE cr_item_sk = i_item_sk
		AND cr_returned_date_sk = d_date_sk
	GROUP BY i_item_id
	) cr_items
JOIN (
	SELECT i_item_id item_id
		,SUM(wr_return_quantity) wr_item_qty
	FROM web_returns
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT d_date
		FROM date_dim
		JOIN (
			SELECT d_week_seq
			FROM date_dim
			WHERE d_date IN (
					'1999-04-17'
					,'1999-10-04'
					,'1999-11-10'
					)
			) tmp0
		) tmp1
	WHERE wr_item_sk = i_item_sk
		AND wr_returned_date_sk = d_date_sk
	GROUP BY i_item_id
	) wr_items
WHERE sr_items.item_id = cr_items.item_id
	AND sr_items.item_id = wr_items.item_id
ORDER BY sr_items.item_id
	,sr_item_qty;
