SELECT channel
	,id
	,SUM(sales) AS sales
	,SUM(RETURNS) AS
RETURNS
	,SUM(profit) AS profit
FROM (
	SELECT 'store channel' AS channel
		,ss.s_store_sk AS id
		,sales
		,COALESCE(RETURNS, 0) AS
	RETURNS
		,(profit - COALESCE(profit_loss, 0)) AS profit
	FROM (
		SELECT s_store_sk
			,SUM(ss_ext_sales_price) AS sales
			,SUM(ss_net_profit) AS profit
		FROM store_sales
		JOIN date_dim
		JOIN store
		WHERE ss_sold_date_sk = d_date_sk
			AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-08-14', 'yyyy-MM-dd')
			AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1998-09-14', 'yyyy-MM-dd')
			AND ss_store_sk = s_store_sk
		GROUP BY s_store_sk
		) ss
	LEFT JOIN (
		SELECT s_store_sk
			,SUM(sr_return_amt) AS
		RETURNS
			,SUM(sr_net_loss) AS profit_loss
		FROM store_returns
		JOIN date_dim
		JOIN store
		WHERE sr_returned_date_sk = d_date_sk
			AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-08-14', 'yyyy-MM-dd')
			AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1998-09-14', 'yyyy-MM-dd')
			AND sr_store_sk = s_store_sk
		GROUP BY s_store_sk
		) sr ON ss.s_store_sk = sr.s_store_sk
	
	UNION ALL
	
	SELECT 'catalog channel' AS channel
		,cs_call_center_sk AS id
		,sales
		,
	RETURNS
		,(profit - profit_loss) AS profit
	FROM (
		SELECT cs_call_center_sk
			,SUM(cs_ext_sales_price) AS sales
			,SUM(cs_net_profit) AS profit
		FROM catalog_sales
		JOIN date_dim
		WHERE cs_sold_date_sk = d_date_sk
			AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-08-14', 'yyyy-MM-dd')
			AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1998-09-14', 'yyyy-MM-dd')
		GROUP BY cs_call_center_sk
		) cs
	JOIN (
		SELECT SUM(cr_return_amount) AS
		RETURNS
			,SUM(cr_net_loss) AS profit_loss
		FROM catalog_returns
		JOIN date_dim
		WHERE cr_returned_date_sk = d_date_sk
			AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-08-14', 'yyyy-MM-dd')
			AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1998-09-14', 'yyyy-MM-dd')
		) cr
	
	UNION ALL
	
	SELECT 'web channel' AS channel
		,ws.wp_web_page_sk AS id
		,sales
		,COALESCE(RETURNS, 0)
	RETURNS
		,(profit - COALESCE(profit_loss, 0)) AS profit
	FROM (
		SELECT wp_web_page_sk
			,SUM(ws_ext_sales_price) AS sales
			,SUM(ws_net_profit) AS profit
		FROM web_sales
		JOIN date_dim
		JOIN web_page
		WHERE ws_sold_date_sk = d_date_sk
			AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-08-14', 'yyyy-MM-dd')
			AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1998-09-14', 'yyyy-MM-dd')
			AND ws_web_page_sk = wp_web_page_sk
		GROUP BY wp_web_page_sk
		) ws
	LEFT JOIN (
		SELECT wp_web_page_sk
			,SUM(wr_return_amt) AS
		RETURNS
			,SUM(wr_net_loss) AS profit_loss
		FROM web_returns
		JOIN date_dim
		JOIN web_page
		WHERE wr_returned_date_sk = d_date_sk
			AND unix_timestamp(d_date, 'yyyy-MM-dd') >= unix_timestamp('1998-08-14', 'yyyy-MM-dd')
			AND unix_timestamp(d_date, 'yyyy-MM-dd') <= unix_timestamp('1998-09-14', 'yyyy-MM-dd')
			AND wr_web_page_sk = wp_web_page_sk
		GROUP BY wp_web_page_sk
		) wr ON ws.wp_web_page_sk = wr.wp_web_page_sk
	) x
GROUP BY channel
	,id
ORDER BY channel
	,id;
