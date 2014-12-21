SELECT i_brand_id brand_id
	,i_brand brand
	,i_manufact_id
	,i_manufact
	,SUM(ss_ext_sales_price) ext_price
FROM date_dim
JOIN store_sales
JOIN item
JOIN customer
JOIN customer_address
JOIN store
WHERE d_date_sk = ss_sold_date_sk
	AND ss_item_sk = i_item_sk
	AND i_manager_id = 91
	AND d_moy = 12
	AND d_year = 2002
	AND ss_customer_sk = c_customer_sk
	AND c_current_addr_sk = ca_address_sk
	AND SUBSTR(ca_zip, 1, 5) <> SUBSTR(s_zip, 1, 5)
	AND ss_store_sk = s_store_sk
GROUP BY i_brand
	,i_brand_id
	,i_manufact_id
	,i_manufact
ORDER BY ext_price DESC
	,i_brand
	,i_brand_id
	,i_manufact_id
	,i_manufact;
