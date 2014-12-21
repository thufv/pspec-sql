SELECT SUM(ss_net_profit) AS total_sum
	,s_state
	,s_county
	,s_state + s_county AS lochierarchy
FROM store_sales
JOIN date_dim d1
JOIN store
JOIN (
	SELECT s_state AS tmp2
	FROM store_sales
		JOIN store
		JOIN date_dim
	WHERE d_month_seq BETWEEN 1181
			AND 1181 + 11
		AND d_date_sk = ss_sold_date_sk
		AND s_store_sk = ss_store_sk
	GROUP BY s_state
	) tmp1 ON s_state = tmp2
WHERE d1.d_month_seq BETWEEN 1181
		AND 1181 + 11
	AND d1.d_date_sk = ss_sold_date_sk
	AND s_store_sk = ss_store_sk
GROUP BY s_state
	,s_county
ORDER BY lochierarchy DESC
	,CASE 
		WHEN lochierarchy = 0
			THEN s_state
		END;
