SELECT i_brand_id brand_id
	,i_brand brand
	,SUM(ss_ext_sales_price) ext_price
FROM date_dim
JOIN store_sales
JOIN item
WHERE d_date_sk = ss_sold_date_sk
	AND ss_item_sk = i_item_sk
	AND i_manager_id = 40
	AND d_moy = 12
	AND d_year = 2001
GROUP BY i_brand
	,i_brand_id
ORDER BY ext_price DESC
	,i_brand_id;
