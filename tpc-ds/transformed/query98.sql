SELECT i_item_desc
	,i_category
	,i_class
	,i_current_price
	,SUM(ss_ext_sales_price) AS itemrevenue
	,SUM(ss_ext_sales_price) * 100 / SUM(SUM(ss_ext_sales_price)) AS revenueratio
FROM store_sales
JOIN item
JOIN date_dim
WHERE ss_item_sk = i_item_sk
	AND i_category IN (
		'Music'
		,'Jewelry'
		,'Women'
		)
	AND ss_sold_date_sk = d_date_sk
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1999-04-26', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1999-05-26', 'yyyy-MM-dd')
GROUP BY i_item_id
	,i_item_desc
	,i_category
	,i_class
	,i_current_price
ORDER BY i_category
	,i_class
	,i_item_id
	,i_item_desc
	,revenueratio;
