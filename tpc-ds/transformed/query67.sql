SELECT *
FROM (
	SELECT i_category
		,i_class
		,i_brand
		,i_product_name
		,d_year
		,d_qoy
		,d_moy
		,s_store_id
		,sumsales
	FROM (
		SELECT i_category
			,i_class
			,i_brand
			,i_product_name
			,d_year
			,d_qoy
			,d_moy
			,s_store_id
			,SUM(COALESCE(ss_sales_price * ss_quantity, 0)) sumsales
		FROM store_sales
			JOIN date_dim
			JOIN store
			JOIN item
		WHERE ss_sold_date_sk = d_date_sk
			AND ss_item_sk = i_item_sk
			AND ss_store_sk = s_store_sk
			AND d_month_seq BETWEEN 1214
				AND 1214 + 11
		GROUP BY i_category, i_class, i_brand, i_product_name, d_year, d_qoy, d_moy, s_store_id
		) dw1
	) dw2
ORDER BY i_category
	,i_class
	,i_brand
	,i_product_name
	,d_year
	,d_qoy
	,d_moy
	,s_store_id
	,sumsales;
