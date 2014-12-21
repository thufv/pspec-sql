SELECT CASE 
		WHEN col1 > 30992
			THEN col2
		ELSE col3
		END bucket1
	,CASE 
		WHEN col4 > 25740
			THEN col5
		ELSE col6
		END bucket2
	,CASE 
		WHEN col7 > 20311
			THEN col8
		ELSE col9
		END bucket3
	,CASE 
		WHEN col10 > 21635
			THEN col11
		ELSE col12
		END bucket4
	,CASE 
		WHEN col13 > 20532
			THEN col14
		ELSE col15
		END bucket5
FROM reason
JOIN (
	SELECT COUNT(*) AS col1
	FROM store_sales
	WHERE ss_quantity BETWEEN 1
			AND 20
	) table1
JOIN (
	SELECT AVG(ss_ext_sales_price) AS col2
	FROM store_sales
	WHERE ss_quantity BETWEEN 1
			AND 20
	) table2
JOIN (
	SELECT AVG(ss_net_paid) AS col3
	FROM store_sales
	WHERE ss_quantity BETWEEN 1
			AND 20
	) table3
JOIN (
	SELECT COUNT(*) AS col4
	FROM store_sales
	WHERE ss_quantity BETWEEN 21
			AND 40
	) table4
JOIN (
	SELECT AVG(ss_ext_sales_price) AS col5
	FROM store_sales
	WHERE ss_quantity BETWEEN 21
			AND 40
	) table5
JOIN (
	SELECT AVG(ss_net_paid) AS col6
	FROM store_sales
	WHERE ss_quantity BETWEEN 21
			AND 40
	) table6
JOIN (
	SELECT COUNT(*) AS col7
	FROM store_sales
	WHERE ss_quantity BETWEEN 41
			AND 60
	) table7
JOIN (
	SELECT AVG(ss_ext_sales_price) AS col8
	FROM store_sales
	WHERE ss_quantity BETWEEN 41
			AND 60
	) table8
JOIN (
	SELECT AVG(ss_net_paid) AS col9
	FROM store_sales
	WHERE ss_quantity BETWEEN 41
			AND 60
	) table9
JOIN (
	SELECT COUNT(*) AS col10
	FROM store_sales
	WHERE ss_quantity BETWEEN 61
			AND 80
	) table10
JOIN (
	SELECT AVG(ss_ext_sales_price) AS col11
	FROM store_sales
	WHERE ss_quantity BETWEEN 61
			AND 80
	) table11
JOIN (
	SELECT AVG(ss_net_paid) AS col12
	FROM store_sales
	WHERE ss_quantity BETWEEN 61
			AND 80
	) table12
JOIN (
	SELECT COUNT(*) AS col13
	FROM store_sales
	WHERE ss_quantity BETWEEN 81
			AND 100
	) table13
JOIN (
	SELECT AVG(ss_ext_sales_price) AS col14
	FROM store_sales
	WHERE ss_quantity BETWEEN 81
			AND 100
	) table14
JOIN (
	SELECT AVG(ss_net_paid) AS col15
	FROM store_sales
	WHERE ss_quantity BETWEEN 81
			AND 100
	) table15
WHERE r_reason_sk = 1;
