SELECT promotions
	,total
	,promotions / total * 100
FROM (
	SELECT SUM(ss_ext_sales_price) promotions
	FROM store_sales
	JOIN store
	JOIN promotion
	JOIN date_dim
	JOIN customer
	JOIN customer_address
	JOIN item
	WHERE ss_sold_date_sk = d_date_sk
		AND ss_store_sk = s_store_sk
		AND ss_promo_sk = p_promo_sk
		AND ss_customer_sk = c_customer_sk
		AND ca_address_sk = c_current_addr_sk
		AND ss_item_sk = i_item_sk
		AND ca_gmt_offset = - 6
		AND i_category = 'Jewelry'
		AND (
			p_channel_dmail = 'Y'
			OR p_channel_email = 'Y'
			OR p_channel_tv = 'Y'
			)
		AND s_gmt_offset = - 6
		AND d_year = 2000
		AND d_moy = 11
	) promotional_sales
JOIN (
	SELECT SUM(ss_ext_sales_price) total
	FROM store_sales
	JOIN store
	JOIN date_dim
	JOIN customer
	JOIN customer_address
	JOIN item
	WHERE ss_sold_date_sk = d_date_sk
		AND ss_store_sk = s_store_sk
		AND ss_customer_sk = c_customer_sk
		AND ca_address_sk = c_current_addr_sk
		AND ss_item_sk = i_item_sk
		AND ca_gmt_offset = - 6
		AND i_category = 'Jewelry'
		AND s_gmt_offset = - 6
		AND d_year = 2000
		AND d_moy = 11
	) all_sales
ORDER BY promotions
	,total;
