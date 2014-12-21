SELECT channel,
         id,
         SUM(sales) AS sales,
         SUM(RETURNS) AS RETURNS,
         SUM(profit) AS profit
    FROM (SELECT 'store channel' AS channel,
                 CONCAT('store', s_store_id) AS id,
                 sales,
                 RETURNS,
                 (profit - profit_loss) AS profit
            FROM (  SELECT s_store_id,
                          SUM(sales_price) AS sales,
                           SUM(profit) AS profit,
                           SUM(return_amt) AS RETURNS,
                           SUM(net_loss) AS profit_loss
                      FROM (SELECT ss_store_sk AS store_sk,
                                   ss_sold_date_sk AS date_sk,
                                   ss_ext_sales_price AS sales_price,
                                   ss_net_profit AS profit,
                                   0 AS return_amt,
                                   0 AS net_loss
                              FROM store_sales
                            UNION ALL
                            SELECT sr_store_sk AS store_sk,
                                   sr_returned_date_sk AS date_sk,
                                   0 AS sales_price,
                                   0 AS profit,
                                   sr_return_amt AS return_amt,
                                   sr_net_loss AS net_loss
                              FROM store_returns) salesreturns
                           JOIN date_dim JOIN store
                     WHERE     date_sk = d_date_sk
                           AND d_date BETWEEN '2001-08-21' AND '2001-09-04'
                           AND store_sk = s_store_sk
                  GROUP BY s_store_id) ssr
          UNION ALL
          SELECT 'catalog channel' AS channel,
                 CONCAT('catalog_page', cp_catalog_page_id) AS id,
                 sales,
                 RETURNS,
                 (profit - profit_loss) AS profit
            FROM (  SELECT cp_catalog_page_id,
                           SUM(sales_price) AS sales,
                           SUM(profit) AS profit,
                           SUM(return_amt) AS RETURNS,
                           SUM(net_loss) AS profit_loss
                      FROM (SELECT cs_catalog_page_sk AS page_sk,
                                   cs_sold_date_sk AS date_sk,
                                   cs_ext_sales_price AS sales_price,
                                   cs_net_profit AS profit,
                                   0 AS return_amt,
                                   0 AS net_loss
                              FROM catalog_sales
                            UNION ALL
                            SELECT cr_catalog_page_sk AS page_sk,
                                   cr_returned_date_sk AS date_sk,
                                   0 AS sales_price,
                                   0 AS profit,
                                   cr_return_amount AS return_amt,
                                   cr_net_loss AS net_loss
                              FROM catalog_returns) salesreturns
                           JOIN date_dim JOIN catalog_page
                     WHERE     date_sk = d_date_sk
                           AND d_date BETWEEN '2001-08-21' AND '2001-09-04'
                           AND page_sk = cp_catalog_page_sk
                  GROUP BY cp_catalog_page_id) csr
          UNION ALL
          SELECT 'web channel' AS channel,
                 CONCAT('web_site', web_site_id) AS id,
                 sales,
                 RETURNS,
                 (profit - profit_loss) AS profit
            FROM (  SELECT web_site_id,
                           SUM(sales_price) AS sales,
                           SUM(profit) AS profit,
                           SUM(return_amt) AS RETURNS,
                           SUM(net_loss) AS profit_loss
                      FROM (SELECT ws_web_site_sk AS wsr_web_site_sk,
                                   ws_sold_date_sk AS date_sk,
                                   ws_ext_sales_price AS sales_price,
                                   ws_net_profit AS profit,
                                   0 AS return_amt,
                                   0 AS net_loss
                              FROM web_sales
                            UNION ALL
                            SELECT ws_web_site_sk AS wsr_web_site_sk,
                                   wr_returned_date_sk AS date_sk,
                                   0 AS sales_price,
                                   0 AS profit,
                                   wr_return_amt AS return_amt,
                                   wr_net_loss AS net_loss
                              FROM web_returns
                                   LEFT OUTER JOIN web_sales
                                      ON (    wr_item_sk = ws_item_sk
                                          AND wr_order_number = ws_order_number))
                           salesreturns
                           JOIN date_dim JOIN web_site
                     WHERE     date_sk = d_date_sk
                           AND d_date BETWEEN '2001-08-21' AND '2001-09-04'
                           AND wsr_web_site_sk = web_site_sk
                  GROUP BY web_site_id) wsr) x
GROUP BY channel, id
ORDER BY channel, id;
