CREATE VIEW Product
AS
   SELECT ProductID, ProductName
     FROM Products
    WHERE Discontinued = No;

SELECT * FROM Product, Other
