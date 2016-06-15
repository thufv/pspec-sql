SELECT c_first_name, c_last_name, ca_zip 
FROM customer JOIN customer_address ON ca_address_sk = c_current_addr_sk 
WHERE c_birth_country = 'US';


  SELECT a.ca_state STATE, COUNT(*) cnt
    FROM customer_address a
         JOIN customer c
         JOIN store_sales s
         JOIN date_dim d
         JOIN item i
         JOIN (SELECT AVG(j.i_current_price) AS tmp0
                 FROM item j) tmp1
         JOIN (SELECT DISTINCT (d_month_seq) AS tmp2
                 FROM date_dim
                WHERE d_year = 1998 AND d_moy = 5) tmp3
   WHERE     a.ca_address_sk = c.c_current_addr_sk
         AND c.c_customer_sk = s.ss_customer_sk
         AND s.ss_sold_date_sk = d.d_date_sk
         AND s.ss_item_sk = i.i_item_sk
         AND d.d_month_seq = tmp2
         AND i.i_current_price > 1.2 * tmp0
GROUP BY a.ca_state
  HAVING COUNT(*) >= 10
ORDER BY cnt;