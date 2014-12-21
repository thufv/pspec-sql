SELECT SUM(ss_quantity)
FROM store_sales
JOIN store
JOIN customer_demographics
JOIN customer_address
JOIN date_dim
WHERE s_store_sk = ss_store_sk
	AND ss_sold_date_sk = d_date_sk
	AND d_year = 1998
	AND (
		(
			cd_demo_sk = ss_cdemo_sk
			AND cd_marital_status = 'S'
			AND cd_education_status = '4 yr Degree'
			AND ss_sales_price BETWEEN 100.00
				AND 150.00
			)
		OR (
			cd_demo_sk = ss_cdemo_sk
			AND cd_marital_status = 'S'
			AND cd_education_status = '4 yr Degree'
			AND ss_sales_price BETWEEN 50.00
				AND 100.00
			)
		OR (
			cd_demo_sk = ss_cdemo_sk
			AND cd_marital_status = 'S'
			AND cd_education_status = '4 yr Degree'
			AND ss_sales_price BETWEEN 150.00
				AND 200.00
			)
		)
	AND (
		(
			ss_addr_sk = ca_address_sk
			AND ca_country = 'United States'
			AND ca_state IN (
				'AK'
				,'IA'
				,'NE'
				)
			AND ss_net_profit BETWEEN 0
				AND 2000
			)
		OR (
			ss_addr_sk = ca_address_sk
			AND ca_country = 'United States'
			AND ca_state IN (
				'NY'
				,'VA'
				,'AR'
				)
			AND ss_net_profit BETWEEN 150
				AND 3000
			)
		OR (
			ss_addr_sk = ca_address_sk
			AND ca_country = 'United States'
			AND ca_state IN (
				'AZ'
				,'MI'
				,'NC'
				)
			AND ss_net_profit BETWEEN 50
				AND 25000
			)
		);
