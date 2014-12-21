SELECT *
FROM (
	SELECT i_category
		,i_class
		,i_brand
		,s_store_name
		,s_company_name
		,d_moy
		,SUM(ss_sales_price) sum_sales
		,AVG(SUM(ss_sales_price)) AS avg_monthly_sales
	FROM item
	JOIN store_sales
	JOIN date_dim
	JOIN store
	WHERE ss_item_sk = i_item_sk
		AND ss_sold_date_sk = d_date_sk
		AND ss_store_sk = s_store_sk
		AND d_year IN (2002)
		AND (
			(
				i_category IN (
					'Jewelry'
					,'Women'
					,'Shoes'
					)
				AND i_class IN (
					'mens watch'
					,'dresses'
					,'mens'
					)
				)
			OR (
				i_category IN (
					'Men'
					,'Sports'
					,'Music'
					)
				AND i_class IN (
					'sports-apparel'
					,'sailing'
					,'pop'
					)
				)
			)
	GROUP BY i_category
		,i_class
		,i_brand
		,s_store_name
		,s_company_name
		,d_moy
	) tmp1
WHERE CASE 
		WHEN (avg_monthly_sales <> 0)
			THEN (ABS(sum_sales - avg_monthly_sales) / avg_monthly_sales)
		ELSE NULL
		END > 0.1
ORDER BY sum_sales - avg_monthly_sales
	,s_store_name;
