SELECT (amc / pmc) AS am_pm_ratio
FROM (
	SELECT COUNT(*) amc
	FROM web_sales
	JOIN household_demographics
	JOIN time_dim
	JOIN web_page
	WHERE ws_sold_time_sk = time_dim.t_time_sk
		AND ws_ship_hdemo_sk = household_demographics.hd_demo_sk
		AND ws_web_page_sk = web_page.wp_web_page_sk
		AND time_dim.t_hour BETWEEN 11
			AND 11 + 1
		AND household_demographics.hd_dep_count = 8
		AND web_page.wp_char_count BETWEEN 5000
			AND 5200
	) bt
JOIN (
	SELECT COUNT(*) pmc
	FROM web_sales
	JOIN household_demographics
	JOIN time_dim
	JOIN web_page
	WHERE ws_sold_time_sk = time_dim.t_time_sk
		AND ws_ship_hdemo_sk = household_demographics.hd_demo_sk
		AND ws_web_page_sk = web_page.wp_web_page_sk
		AND time_dim.t_hour BETWEEN 13
			AND 13 + 1
		AND household_demographics.hd_dep_count = 8
		AND web_page.wp_char_count BETWEEN 5000
			AND 5200
	) pt
ORDER BY am_pm_ratio;
