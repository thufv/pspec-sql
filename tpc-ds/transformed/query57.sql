SELECT *
FROM (
	SELECT v1.i_category
		,v1.i_brand
		,v1.d_year
		,v1.d_moy
		,v1.avg_monthly_sales
		,v1.sum_sales
		,v1_lag.sum_sales psum
		,v1_lead.sum_sales nsum
	FROM (
		SELECT i_category
			,i_brand
			,cc_name
			,d_year
			,d_moy
			,SUM(cs_sales_price) sum_sales
			,AVG(SUM(cs_sales_price)) AS avg_monthly_sales
		FROM item
		JOIN catalog_sales
		JOIN date_dim
		JOIN call_center
		WHERE cs_item_sk = i_item_sk
			AND cs_sold_date_sk = d_date_sk
			AND cc_call_center_sk = cs_call_center_sk
			AND (
				d_year = 1999
				OR (
					d_year = 1999 - 1
					AND d_moy = 12
					)
				OR (
					d_year = 1999 + 1
					AND d_moy = 1
					)
				)
		GROUP BY i_category
			,i_brand
			,cc_name
			,d_year
			,d_moy
		) v1
	JOIN (
		SELECT i_category
			,i_brand
			,cc_name
			,d_year
			,d_moy
			,SUM(cs_sales_price) sum_sales
			,AVG(SUM(cs_sales_price)) AS avg_monthly_sales
		FROM item
		JOIN catalog_sales
		JOIN date_dim
		JOIN call_center
		WHERE cs_item_sk = i_item_sk
			AND cs_sold_date_sk = d_date_sk
			AND cc_call_center_sk = cs_call_center_sk
			AND (
				d_year = 1999
				OR (
					d_year = 1999 - 1
					AND d_moy = 12
					)
				OR (
					d_year = 1999 + 1
					AND d_moy = 1
					)
				)
		GROUP BY i_category
			,i_brand
			,cc_name
			,d_year
			,d_moy
		) v1_lag
	JOIN (
		SELECT i_category
			,i_brand
			,cc_name
			,d_year
			,d_moy
			,SUM(cs_sales_price) sum_sales
			,AVG(SUM(cs_sales_price)) AS avg_monthly_sales
		FROM item
		JOIN catalog_sales
		JOIN date_dim
		JOIN call_center
		WHERE cs_item_sk = i_item_sk
			AND cs_sold_date_sk = d_date_sk
			AND cc_call_center_sk = cs_call_center_sk
			AND (
				d_year = 1999
				OR (
					d_year = 1999 - 1
					AND d_moy = 12
					)
				OR (
					d_year = 1999 + 1
					AND d_moy = 1
					)
				)
		GROUP BY i_category
			,i_brand
			,cc_name
			,d_year
			,d_moy
		) v1_lead
	WHERE v1.i_category = v1_lag.i_category
		AND v1.i_category = v1_lead.i_category
		AND v1.i_brand = v1_lag.i_brand
		AND v1.i_brand = v1_lead.i_brand
		AND v1.cc_name = v1_lag.cc_name
		AND v1.cc_name = v1_lead.cc_name
	) v2
WHERE d_year = 1999
	AND avg_monthly_sales > 0
	AND CASE 
		WHEN avg_monthly_sales > 0
			THEN ABS(sum_sales - avg_monthly_sales) / avg_monthly_sales
		ELSE NULL
		END > 0.1
ORDER BY sum_sales - avg_monthly_sales
	,3;
