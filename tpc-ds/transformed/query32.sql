SELECT SUM(cs_ext_discount_amt)
FROM catalog_sales
JOIN item
JOIN date_dim
JOIN (
	SELECT 1.3 * AVG(cs_ext_discount_amt) AS tmp0
	FROM catalog_sales
	JOIN date_dim
	JOIN item
	WHERE cs_item_sk = i_item_sk
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('2000-03-22', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2000-06-22', 'yyyy-MM-dd')
		AND d_date_sk = cs_sold_date_sk
	) tmp1
WHERE i_manufact_id = 291
	AND i_item_sk = cs_item_sk
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('2000-03-22', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('2000-06-22', 'yyyy-MM-dd')
	AND d_date_sk = cs_sold_date_sk
	AND cs_ext_discount_amt > tmp0;
