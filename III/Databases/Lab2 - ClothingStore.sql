-- LAB. 2 --- Insert, Update, Delete ---------------------------------------------------------------------------------------------------------


USE ClothingStore
GO	


-- Insert statement that violates referential integrity constraints
	INSERT INTO Stock(Pid, Wid, Quantity) VALUES (11, 2, 500)



-- UPDATES


-- Update the year to 2021 of the Spring-Summer 2020 BALENCIAGA collection
UPDATE Collection
SET Year = 2021
WHERE Did = 'BB' AND NOT Season = 'SS' AND Year = 2020 
SELECT * FROM Collection


-- Update the quantity to 1500 pieces of the product with id 1 in the warehouses with id's 2,3
UPDATE Stock
SET Quantity = 1500
WHERE Quantity IS NOT NULL AND Wid in (2,3)  AND Pid = 1
SELECT * FROM Stock


-- Update the price to 850 euros of the products that have the price in the range 800-899
UPDATE Product
SET Price = 850
WHERE Price BETWEEN 800 AND 899
SELECT * FROM Product


-- Update the price of bags by adding 100 euros
UPDATE Product
SET Price = Price + 100
WHERE  Description LIKE '%bag'
SELECT * FROM Product



-- DELETE

INSERT INTO Collection(Cid, Did, Season, Year) VALUES
	(11, 'LV', 'AW', 2022),
	(12, 'BB', 'SS', 2023)


-- Delete the Louis Vuitton 2022 collections
DELETE FROM Collection 
WHERE Did = 'LV' and Year = 2022


-- Delete the Spring-Summer 2023 collections
DELETE FROM Collection 
WHERE Season = 'SS' and Year = 2023

SELECT * FROM Collection



-- SELECT QUERIES

-- (A) UNION


-- Select the products suitable for men or both
SELECT *
FROM Product
WHERE Gender = 'male' 
UNION
SELECT *
FROM Product
WHERE Gender = 'unisex'


-- Select the shoes and accessories
SELECT *
FROM Product P
WHERE P.Cathegory = 'accessories' OR P.Cathegory = 'footwear'



-- (B) INTERSECTION


-- Select the female accessories
SELECT *
FROM Product P
WHERE P.Cathegory = 'accessories'
INTERSECT
SELECT *
FROM Product P
WHERE P.Gender = 'female'


-- Select the dresses that have of at least 1000 euros
SELECT *
FROM Product P
WHERE P.Price >= 1000 AND P.Description LIKE '%dress'
ORDER BY P.Price



-- (C) DIFFERENCE


-- Select the Spring-Summer Versace collections
SELECT *
FROM Collection C
WHERE C.Did = 'VRSC'
EXCEPT
SELECT *
FROM Collection C
WHERE C.Season = 'SS'


-- Select the Spring-Summer collections that are not signed by BALENCIAGA
SELECT *
FROM Collection C
WHERE C.Season = 'SS' AND C.Did NOT IN(SELECT C.Did
										FROM Collection C
										WHERE C.Did = 'BB'
										)



-- (D) JOINS


-- Select the name of the designers, the season and the year of their collections
SELECT D.Name, C.Season, C.Year
FROM Collection C INNER JOIN Designer D
ON C.Did = D.Did


-- Select the products that have a stock of at least 2000 pieces in a warehouse
SELECT P.Description, P.Cid, W.Wid, S.Quantity
FROM Product P FULL OUTER JOIN Stock S ON  P.Pid = S.Pid
			   FULL OUTER JOIN Warehouse W ON S.Wid = W.Wid
WHERE S.Quantity > 2000


-- Select the products belonging to a Autumn-Winter collection
SELECT *
FROM Product P LEFT OUTER JOIN Collection C
	 ON P.Cid = C.Cid
WHERE C.Season = 'AW'


-- Select the description of a product, the collection to which it belongs and the quantity in each warehouse
SELECT P.Description, P.Cid, S.Quantity
FROM Product P RIGHT OUTER JOIN Stock S 
	 ON P.Pid = S.Pid



-- (E) WHERE [] IN []


-- Select the products belonging to BALENCIAGA collections
SELECT P.Cid, P.Description
FROM Product P
WHERE P.Cid IN (SELECT C.Cid
				FROM Collection C
				WHERE C.Did IN (SELECT D.Did
								FROM Designer D
								WHERE D.Name = 'BALENCIAGA'
								)
				)



-- Select the products that are stored in warehouse 2
SELECT *
FROM Product P
WHERE P.Pid in (SELECT S.Pid
				FROM Stock S
				WHERE S.Wid IN (2)
				)


-- (F) WHERE [] EXISTS []


-- Select the warehouses that are have more than 80% of their capacity occupied, the last being greater than 1 million cube meters
SELECT * 
FROM Warehouse W
WHERE W.Occupancy > 80 AND EXISTS (SELECT *
								   FROM Warehouse W
								   WHERE W.Capacity > 1000000)


-- Select the products for which there are at most 1000 pieces in warehouse 1
SELECT *
FROM Stock S
WHERE S.Wid = 1 AND EXISTS (SELECT *
							FROM Stock S
							WHERE S.Quantity <= 1000)



-- (G) FROM [SUBQUERY]


-- Select the unisex accessories
SELECT P.Description, P.Price
FROM (SELECT *
	  FROM Product P
	  WHERE P.Cathegory = 'accessories' AND P.Gender = 'unisex'
	  ) P


-- Select the Spring-Summer collections
SELECT C.Did
FROM (SELECT *
	  FROM Collection C
	  WHERE C.Season = 'SS'
	 ) C



-- (H) GROUP BY [+ HAVING]


-- Select the designers along with their collections
SELECT D.Name, C.Season, C.Year
FROM Designer D INNER JOIN Collection C on D.Did = C.Did
GROUP BY D.Name, C.Season, C.Year


-- Select the products having the average stock of at least 1000 pieces / warehouse
SELECT P.Description, AVG(S.Quantity) as Average
FROM Stock S INNER JOIN Product P on S.Pid = P.Pid
GROUP BY P.Description
HAVING AVG(S.Quantity) > 1000


-- Select the collections of the Japanese designers
SELECT D.Name, C.Season, C.Year
FROM Designer D INNER JOIN Collection C on D.Did = C.Did
GROUP BY D.Name, C.Season, C.Year
HAVING D.Name in (SELECT D.Name
				  FROM Designer D
				  WHERE D.CountryOfOrigin = 'Japan')


-- Select the products having an average stock greater than the average stock of all products
SELECT P.Description, AVG(S.Quantity) as TotalQuantity  
FROM Product P INNER JOIN Stock S on P.Pid = S.Pid
GROUP BY P.Description
HAVING AVG(S.Quantity) > (SELECT AVG(S.Quantity)
						  FROM Stock S
						  )


-- (I) WHERE [] [ ANY / ALL ] []


-- Select the stock which are greater than the maximum stock of warehouse 1
SELECT S.Pid, S.Wid, S.Quantity
FROM Stock S
WHERE S.Quantity > ALL (SELECT S1.Quantity
						FROM Stock S1
						WHERE S1.Wid = 1)


-- Select the stock that are higher than the average stock
SELECT S.Pid, S.Wid, S.Quantity
FROM Stock S
WHERE S.Quantity > ALL (SELECT AVG(S1.Quantity)
						FROM Stock S1)


-- Select the collections that appeared in the years following the one of the last BALENCIAGA collection 
SELECT C.Did, C.Season, C.Year
FROM Collection C
WHERE C.Year > ANY (SELECT C.Year
					FROM Collection
					WHERE C.Did = 'BB')


-- Select the products that have the price higher than the one of the cheapest accessory
SELECT *
FROM Product P
WHERE P.Price > ANY (SELECT P.Price
					 FROM Product P
					 WHERE P.Cathegory = 'accessories'
					 )
