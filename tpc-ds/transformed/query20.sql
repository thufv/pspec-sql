SELECT i_item_desc
	,i_category
	,i_class
	,i_current_price
	,SUM(cs_ext_sales_price) AS itemrevenue
	,SUM(cs_ext_sales_price) * 100 / SUM(SUM(cs_ext_sales_price)) AS revenueratio
FROM catalog_sales
JOIN item
JOIN date_dim
WHERE cs_item_sk = i_item_sk
	AND i_category IN (
		'Shoes'
		,'Women'
		,'Music'
		)
	AND cs_sold_date_sk = d_date_sk
	AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1999-06-03', 'yyyy-MM-dd')
	AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1999-06-03', 'yyyy-MM-dd')
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
