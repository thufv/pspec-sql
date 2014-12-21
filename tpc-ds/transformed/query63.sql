SELECT *
FROM (
	SELECT i_manager_id
		,SUM(ss_sales_price) sum_sales
		,AVG(SUM(ss_sales_price)) AS avg_monthly_sales
	FROM item
	JOIN store_sales
	JOIN date_dim
	JOIN store
	WHERE ss_item_sk = i_item_sk
		AND ss_sold_date_sk = d_date_sk
		AND ss_store_sk = s_store_sk
		AND d_month_seq IN (
			1206
			,1206 + 1
			,1206 + 2
			,1206 + 3
			,1206 + 4
			,1206 + 5
			,1206 + 6
			,1206 + 7
			,1206 + 8
			,1206 + 9
			,1206 + 10
			,1206 + 11
			)
		AND (
			(
				i_category IN (
					'Books'
					,'Children'
					,'Electronics'
					)
				AND i_class IN (
					'personal'
					,'portable'
					,'refernece'
					,'self-help'
					)
				AND i_brand IN (
					'scholaramalgamalg #14'
					,'scholaramalgamalg #7'
					,'exportiunivamalg #9'
					,'scholaramalgamalg #9'
					)
				)
			OR (
				i_category IN (
					'Women'
					,'Music'
					,'Men'
					)
				AND i_class IN (
					'accessories'
					,'classical'
					,'fragrances'
					,'pants'
					)
				AND i_brand IN (
					'amalgimporto #1'
					,'edu packscholar #1'
					,'exportiimporto #1'
					,'importoamalg #1'
					)
				)
			)
	GROUP BY i_manager_id
		,d_moy
	) tmp1
WHERE CASE 
		WHEN avg_monthly_sales > 0
			THEN ABS(sum_sales - avg_monthly_sales) / avg_monthly_sales
		ELSE NULL
		END > 0.1
ORDER BY i_manager_id
	,avg_monthly_sales
	,sum_sales;
