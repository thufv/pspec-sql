SELECT DISTINCT (i_product_name)
FROM item i1
JOIN (
	SELECT COUNT(*) AS item_cnt
	FROM item
	WHERE (
			(
				(
					i_category = 'Women'
					AND (
						i_color = 'olive'
						OR i_color = 'grey'
						)
					AND (
						i_units = 'Bundle'
						OR i_units = 'Cup'
						)
					AND (
						i_size = 'petite'
						OR i_size = 'small'
						)
					)
				OR (
					i_category = 'Women'
					AND (
						i_color = 'hot'
						OR i_color = 'thistle'
						)
					AND (
						i_units = 'Box'
						OR i_units = 'Each'
						)
					AND (
						i_size = 'large'
						OR i_size = 'medium'
						)
					)
				OR (
					i_category = 'Men'
					AND (
						i_color = 'chiffon'
						OR i_color = 'yellow'
						)
					AND (
						i_units = 'Carton'
						OR i_units = 'Dozen'
						)
					AND (
						i_size = 'N/A'
						OR i_size = 'extra large'
						)
					)
				OR (
					i_category = 'Men'
					AND (
						i_color = 'bisque'
						OR i_color = 'turquoise'
						)
					AND (
						i_units = 'Case'
						OR i_units = 'Tsp'
						)
					AND (
						i_size = 'petite'
						OR i_size = 'small'
						)
					)
				)
			)
		OR (
			(
				(
					i_category = 'Women'
					AND (
						i_color = 'chocolate'
						OR i_color = 'lemon'
						)
					AND (
						i_units = 'Unknown'
						OR i_units = 'Oz'
						)
					AND (
						i_size = 'petite'
						OR i_size = 'small'
						)
					)
				OR (
					i_category = 'Women'
					AND (
						i_color = 'light'
						OR i_color = 'ivory'
						)
					AND (
						i_units = 'Ounce'
						OR i_units = 'Ton'
						)
					AND (
						i_size = 'large'
						OR i_size = 'medium'
						)
					)
				OR (
					i_category = 'Men'
					AND (
						i_color = 'rose'
						OR i_color = 'sandy'
						)
					AND (
						i_units = 'Pound'
						OR i_units = 'Lb'
						)
					AND (
						i_size = 'N/A'
						OR i_size = 'extra large'
						)
					)
				OR (
					i_category = 'Men'
					AND (
						i_color = 'wheat'
						OR i_color = 'burnished'
						)
					AND (
						i_units = 'Dram'
						OR i_units = 'Pallet'
						)
					AND (
						i_size = 'petite'
						OR i_size = 'small'
						)
					)
				)
			)
	) tmp
WHERE i_manufact_id BETWEEN 770
		AND 770 + 40
	AND item_cnt > 0
ORDER BY i_product_name;
