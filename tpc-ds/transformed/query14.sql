SELECT channel
	,i_brand_id
	,i_class_id
	,i_category_id
	,SUM(sales)
	,SUM(number_sales)
FROM (
	SELECT 'store' channel
		,i_brand_id
		,i_class_id
		,i_category_id
		,SUM(ss_quantity * ss_list_price) sales
		,COUNT(*) number_sales
		,tmp0
	FROM store_sales
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT i_item_sk ss_item_sk
		FROM item
		JOIN (
			SELECT iss.i_brand_id brand_id
				,iss.i_class_id class_id
				,iss.i_category_id category_id
			FROM store_sales
			JOIN item iss
			JOIN date_dim d1
			WHERE ss_item_sk = iss.i_item_sk
				AND ss_sold_date_sk = d1.d_date_sk
				AND d1.d_year BETWEEN 1999
					AND 1999 + 2
			) table1 LEFT SEMI
		JOIN (
			SELECT ics.i_brand_id brand_id
				,ics.i_class_id class_id
				,ics.i_category_id category_id
			FROM catalog_sales
			JOIN item ics
			JOIN date_dim d2
			WHERE cs_item_sk = ics.i_item_sk
				AND cs_sold_date_sk = d2.d_date_sk
				AND d2.d_year BETWEEN 1999
					AND 1999 + 2
			) table2 ON (
				table1.brand_id = table2.brand_id
				AND table1.class_id = table2.class_id
				AND table1.category_id = table2.category_id
				) LEFT SEMI
		JOIN (
			SELECT iws.i_brand_id brand_id
				,iws.i_class_id class_id
				,iws.i_category_id category_id
			FROM web_sales
			JOIN item iws
			JOIN date_dim d3
			WHERE ws_item_sk = iws.i_item_sk
				AND ws_sold_date_sk = d3.d_date_sk
				AND d3.d_year BETWEEN 1999
					AND 1999 + 2
			) table3 ON (
				table1.brand_id = table3.brand_id
				AND table1.class_id = table3.class_id
				AND table1.category_id = table3.category_id
				)
		WHERE item.i_brand_id = table1.brand_id
			AND item.i_class_id = table1.class_id
			AND item.i_category_id = table1.category_id
		) cross_items
	JOIN (
		SELECT average_sales AS tmp0
		FROM (
			SELECT AVG(quantity * list_price) average_sales
			FROM (
				SELECT ss_quantity quantity
					,ss_list_price list_price
				FROM store_sales
				JOIN date_dim
				WHERE ss_sold_date_sk = d_date_sk
					AND d_year BETWEEN 1999
						AND 2001
				
				UNION ALL
				
				SELECT cs_quantity quantity
					,cs_list_price list_price
				FROM catalog_sales
				JOIN date_dim
				WHERE cs_sold_date_sk = d_date_sk
					AND d_year BETWEEN 2000
						AND 2000 + 2
				
				UNION ALL
				
				SELECT ws_quantity quantity
					,ws_list_price list_price
				FROM web_sales
				JOIN date_dim
				WHERE ws_sold_date_sk = d_date_sk
					AND d_year BETWEEN 2000
						AND 2000 + 2
				) x
			) avg_sales
		) tmp1
	WHERE store_sales.ss_item_sk = i_item_sk
		AND ss_sold_date_sk = d_date_sk
		AND d_year = 2000 + 2
		AND d_moy = 11
	GROUP BY i_brand_id
		,i_class_id
		,i_category_id
	HAVING SUM(ss_quantity * ss_list_price) > tmp0
	
	UNION ALL
	
	SELECT 'catalog' channel
		,i_brand_id
		,i_class_id
		,i_category_id
		,SUM(cs_quantity * cs_list_price) sales
		,COUNT(*) number_sales
		,tmp3
	FROM catalog_sales
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT i_item_sk ss_item_sk
		FROM item
		JOIN (
			SELECT iss.i_brand_id brand_id
				,iss.i_class_id class_id
				,iss.i_category_id category_id
			FROM store_sales
			JOIN item iss
			JOIN date_dim d1
			WHERE ss_item_sk = iss.i_item_sk
				AND ss_sold_date_sk = d1.d_date_sk
				AND d1.d_year BETWEEN 1999
					AND 1999 + 2
			) table1 LEFT SEMI
		JOIN (
			SELECT ics.i_brand_id brand_id
				,ics.i_class_id class_id
				,ics.i_category_id category_id
			FROM catalog_sales
			JOIN item ics
			JOIN date_dim d2
			WHERE cs_item_sk = ics.i_item_sk
				AND cs_sold_date_sk = d2.d_date_sk
				AND d2.d_year BETWEEN 1999
					AND 1999 + 2
			) table2 ON (
				table1.brand_id = table2.brand_id
				AND table1.class_id = table2.class_id
				AND table1.category_id = table2.category_id
				) LEFT SEMI
		JOIN (
			SELECT iws.i_brand_id brand_id
				,iws.i_class_id class_id
				,iws.i_category_id category_id
			FROM web_sales
			JOIN item iws
			JOIN date_dim d3
			WHERE ws_item_sk = iws.i_item_sk
				AND ws_sold_date_sk = d3.d_date_sk
				AND d3.d_year BETWEEN 1999
					AND 1999 + 2
			) table3 ON (
				table1.brand_id = table3.brand_id
				AND table1.class_id = table3.class_id
				AND table1.category_id = table3.category_id
				)
		WHERE item.i_brand_id = table1.brand_id
			AND item.i_class_id = table1.class_id
			AND item.i_category_id = table1.category_id
		) cross_items ON cs_item_sk = ss_item_sk
	JOIN (
		SELECT average_sales AS tmp3
		FROM (
			SELECT AVG(quantity * list_price) average_sales
			FROM (
				SELECT ss_quantity quantity
					,ss_list_price list_price
				FROM store_sales
				JOIN date_dim
				WHERE ss_sold_date_sk = d_date_sk
					AND d_year BETWEEN 1999
						AND 2001
				
				UNION ALL
				
				SELECT cs_quantity quantity
					,cs_list_price list_price
				FROM catalog_sales
				JOIN date_dim
				WHERE cs_sold_date_sk = d_date_sk
					AND d_year BETWEEN 2000
						AND 2000 + 2
				
				UNION ALL
				
				SELECT ws_quantity quantity
					,ws_list_price list_price
				FROM web_sales
				JOIN date_dim
				WHERE ws_sold_date_sk = d_date_sk
					AND d_year BETWEEN 2000
						AND 2000 + 2
				) x
			) avg_sales
		) tmp2
	WHERE cs_item_sk = i_item_sk
		AND cs_sold_date_sk = d_date_sk
		AND d_year = 2000 + 2
		AND d_moy = 11
	GROUP BY i_brand_id
		,i_class_id
		,i_category_id
	HAVING SUM(cs_quantity * cs_list_price) > tmp3
	
	UNION ALL
	
	SELECT 'web' channel
		,i_brand_id
		,i_class_id
		,i_category_id
		,SUM(ws_quantity * ws_list_price) sales
		,COUNT(*) number_sales
		,tmp5
	FROM web_sales
	JOIN item
	JOIN date_dim
	JOIN (
		SELECT i_item_sk ss_item_sk
		FROM item
		JOIN (
			SELECT iss.i_brand_id brand_id
				,iss.i_class_id class_id
				,iss.i_category_id category_id
			FROM store_sales
			JOIN item iss
			JOIN date_dim d1
			WHERE ss_item_sk = iss.i_item_sk
				AND ss_sold_date_sk = d1.d_date_sk
				AND d1.d_year BETWEEN 1999
					AND 1999 + 2
			) table1 LEFT SEMI
		JOIN (
			SELECT ics.i_brand_id brand_id
				,ics.i_class_id class_id
				,ics.i_category_id category_id
			FROM catalog_sales
			JOIN item ics
			JOIN date_dim d2
			WHERE cs_item_sk = ics.i_item_sk
				AND cs_sold_date_sk = d2.d_date_sk
				AND d2.d_year BETWEEN 1999
					AND 1999 + 2
			) table2 ON (
				table1.brand_id = table2.brand_id
				AND table1.class_id = table2.class_id
				AND table1.category_id = table2.category_id
				) LEFT SEMI
		JOIN (
			SELECT iws.i_brand_id brand_id
				,iws.i_class_id class_id
				,iws.i_category_id category_id
			FROM web_sales
			JOIN item iws
			JOIN date_dim d3
			WHERE ws_item_sk = iws.i_item_sk
				AND ws_sold_date_sk = d3.d_date_sk
				AND d3.d_year BETWEEN 1999
					AND 1999 + 2
			) table3 ON (
				table1.brand_id = table3.brand_id
				AND table1.class_id = table3.class_id
				AND table1.category_id = table3.category_id
				)
		WHERE item.i_brand_id = table1.brand_id
			AND item.i_class_id = table1.class_id
			AND item.i_category_id = table1.category_id
		) cross_items ON ws_item_sk = ss_item_sk
	JOIN (
		SELECT average_sales AS tmp5
		FROM (
			SELECT AVG(quantity * list_price) average_sales
			FROM (
				SELECT ss_quantity quantity
					,ss_list_price list_price
				FROM store_sales
				JOIN date_dim
				WHERE ss_sold_date_sk = d_date_sk
					AND d_year BETWEEN 1999
						AND 2001
				
				UNION ALL
				
				SELECT cs_quantity quantity
					,cs_list_price list_price
				FROM catalog_sales
				JOIN date_dim
				WHERE cs_sold_date_sk = d_date_sk
					AND d_year BETWEEN 2000
						AND 2000 + 2
				
				UNION ALL
				
				SELECT ws_quantity quantity
					,ws_list_price list_price
				FROM web_sales
				JOIN date_dim
				WHERE ws_sold_date_sk = d_date_sk
					AND d_year BETWEEN 2000
						AND 2000 + 2
				) x
			) avg_sales
		) tmp4
	WHERE ws_item_sk = i_item_sk
		AND ws_sold_date_sk = d_date_sk
		AND d_year = 2000 + 2
		AND d_moy = 11
	GROUP BY i_brand_id
		,i_class_id
		,i_category_id
	HAVING SUM(ws_quantity * ws_list_price) > tmp5
	) y
GROUP BY channel
	,i_brand_id
	,i_class_id
	,i_category_id
ORDER BY channel
	,i_brand_id
	,i_class_id
	,i_category_id;