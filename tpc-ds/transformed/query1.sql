SELECT c_customer_id
    FROM (  SELECT sr_customer_sk AS ctr_customer_sk,
                   sr_store_sk AS ctr_store_sk,
                   SUM(SR_REVERSED_CHARGE) AS ctr_total_return
              FROM store_returns JOIN date_dim
             WHERE sr_returned_date_sk = d_date_sk AND d_year = 1998
          GROUP BY sr_customer_sk, sr_store_sk) ctr1
         JOIN store
         JOIN customer
         JOIN
         (SELECT AVG(ctr_total_return) * 1.2 AS tmp0
            FROM (  SELECT sr_customer_sk AS ctr_customer_sk,
                           sr_store_sk AS ctr_store_sk,
                           SUM(SR_REVERSED_CHARGE) AS ctr_total_return
                      FROM store_returns JOIN date_dim
                     WHERE sr_returned_date_sk = d_date_sk AND d_year = 1998
                  GROUP BY sr_customer_sk, sr_store_sk) ctr2) tmp1
   WHERE     ctr1.ctr_total_return > tmp0
         AND s_store_sk = ctr1.ctr_store_sk
         AND s_state = 'TN'
         AND ctr1.ctr_customer_sk = c_customer_sk
ORDER BY c_customer_id;