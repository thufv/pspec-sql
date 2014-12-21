SELECT ws_ext_discount_amt
FROM web_sales
JOIN item
JOIN date_dim
JOIN (
	SELECT 1.3 * AVG(ws_ext_discount_amt) AS tmp0
	FROM web_sales
	JOIN date_dim
	JOIN item
	WHERE ws_item_sk = i_item_sk
		AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('2000-02-02', 'yyyy-MM-dd')
		AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2000-05-02', 'yyyy-MM-dd')
		AND d_date_sk = ws_sold_date_sk
	) tmp1
WHERE i_manufact_id = 248
	AND i_item_sk = ws_item_sk
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('2000-02-02', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2000-05-02', 'yyyy-MM-dd')
	AND d_date_sk = ws_sold_date_sk
	AND ws_ext_discount_amt > tmp0
ORDER BY ws_ext_discount_amt;

