SELECT *
FROM (
	SELECT AVG(ss_list_price) B1_LP
		,COUNT(ss_list_price) B1_CNT
		,COUNT(DISTINCT ss_list_price) B1_CNTD
	FROM store_sales
	WHERE ss_quantity BETWEEN 0
			AND 5
		AND (
			ss_list_price BETWEEN 51
				AND 51 + 10
			OR ss_coupon_amt BETWEEN 12565
				AND 12565 + 1000
			OR ss_wholesale_cost BETWEEN 52
				AND 52 + 20
			)
	) B1
JOIN (
	SELECT AVG(ss_list_price) B2_LP
		,COUNT(ss_list_price) B2_CNT
		,COUNT(DISTINCT ss_list_price) B2_CNTD
	FROM store_sales
	WHERE ss_quantity BETWEEN 6
			AND 10
		AND (
			ss_list_price BETWEEN 135
				AND 135 + 10
			OR ss_coupon_amt BETWEEN 3897
				AND 3897 + 1000
			OR ss_wholesale_cost BETWEEN 79
				AND 79 + 20
			)
	) B2
JOIN (
	SELECT AVG(ss_list_price) B3_LP
		,COUNT(ss_list_price) B3_CNT
		,COUNT(DISTINCT ss_list_price) B3_CNTD
	FROM store_sales
	WHERE ss_quantity BETWEEN 11
			AND 15
		AND (
			ss_list_price BETWEEN 106
				AND 106 + 10
			OR ss_coupon_amt BETWEEN 10740
				AND 10740 + 1000
			OR ss_wholesale_cost BETWEEN 16
				AND 16 + 20
			)
	) B3
JOIN (
	SELECT AVG(ss_list_price) B4_LP
		,COUNT(ss_list_price) B4_CNT
		,COUNT(DISTINCT ss_list_price) B4_CNTD
	FROM store_sales
	WHERE ss_quantity BETWEEN 16
			AND 20
		AND (
			ss_list_price BETWEEN 16
				AND 16 + 10
			OR ss_coupon_amt BETWEEN 13313
				AND 13313 + 1000
			OR ss_wholesale_cost BETWEEN 8
				AND 8 + 20
			)
	) B4
JOIN (
	SELECT AVG(ss_list_price) B5_LP
		,COUNT(ss_list_price) B5_CNT
		,COUNT(DISTINCT ss_list_price) B5_CNTD
	FROM store_sales
	WHERE ss_quantity BETWEEN 21
			AND 25
		AND (
			ss_list_price BETWEEN 153
				AND 153 + 10
			OR ss_coupon_amt BETWEEN 4490
				AND 4490 + 1000
			OR ss_wholesale_cost BETWEEN 14
				AND 14 + 20
			)
	) B5
JOIN (
	SELECT AVG(ss_list_price) B6_LP
		,COUNT(ss_list_price) B6_CNT
		,COUNT(DISTINCT ss_list_price) B6_CNTD
	FROM store_sales
	WHERE ss_quantity BETWEEN 26
			AND 30
		AND (
			ss_list_price BETWEEN 111
				AND 111 + 10
			OR ss_coupon_amt BETWEEN 8115
				AND 8115 + 1000
			OR ss_wholesale_cost BETWEEN 35
				AND 35 + 20
			)
	) B6;
