--- LAB. 5 --- Indexes ----------------------------------------------------------------------------------------------------------------------



USE ClothingStore
GO


-- Add column OnlineId to table Product with unique constraint

SELECT * FROM Product

ALTER TABLE Product
ADD OnlineId INT

DECLARE @currentId INT = 0
DECLARE @lastId INT = (SELECT MAX(Pid) FROM Product)
WHILE @currentId <= @lastId BEGIN
	UPDATE Product
	SET OnlineId = @currentId + 1000
	WHERE Pid = @currentId
	SET @currentId = @currentId + 1
END

SELECT * FROM Product

ALTER TABLE Product
ADD CONSTRAINT UNIQUE(OnlineId)

INSERT INTO Product(Pid, OnlineId) VALUES (25, 1005)

GO



-- Add column Age in table Client (+ populate it)

ALTER TABLE Client
ADD Age TINYINT

INSERT INTO Client(Cid, FirstName, LastName, Email, Age) VALUES 
	(1, 'Daniela', 'Popescu', 'dp@example.com', 20),
	(2, 'Olga', 'Mihaila', 'om@example.com', 35),
	(3, 'Ariana', 'Muntean', 'am@example.com', 18),
	(4, 'Cristian', 'Popescu', 'cp@example.com', 24),
	(5, 'Viorel', 'Cosma', 'vc@example.com', 28),
	(6, 'Andrei', 'Cosma', 'ac@example.com', 42),
	(7, 'Ioana', 'Pintilie', 'ip@example.com', 33),
	(8, 'Maria', 'Noureanu', 'mn@example.com', 29),
	(9, 'Sabina', 'Albota', 'sa@example.com', 32),
	(10, 'Bogdan', 'Chiperi', 'bc@example.com', 27)

SELECT * FROM Client

GO



-- Populate table ClientOrders

INSERT INTO ClientOrders(COid, Pid, Cid, Quantity, Status) VALUES
	(1, 1, 1, 1, 'confirmed'),
	(2, 5, 2, 2, 'delivered'),
	(3, 2, 10, 1, 'delivered'),
	(4, 7, 1, 1, 'confirmed'),
	(5, 3, 4, 3, 'confirmed'),
	(6, 1, 4, 2, 'in transit'),
	(7, 9, 3, 1, 'delivered'),
	(8, 4, 8, 1, 'delivered'),
	(9, 1, 4, 1, 'in transit'),
	(10, 6, 6, 1, 'delivered')

SELECT * FROM ClientOrders

GO



--- A

-- clustered index scan

SELECT Pid, OnlineId, Description, Gender FROM PRODUCT
ORDER BY Pid


-- clustered index seek

SELECT Pid, OnlineId, Description FROM Product
WHERE Pid = 6


-- nonclustered index scan

SELECT * FROM Product
ORDER BY OnlineId


-- nonclustered index seek

SELECT OnlineId FROM Product
WHERE OnlineId BETWEEN 1006 AND 1009 


-- key lookup 

SELECT Pid, OnlineId, Description FROM Product
ORDER BY OnlineId



--- B

SET NOCOUNT ON
GO
SET SHOWPLAN_ALL ON
GO
-----------------------
SELECT OnlineId, Price
FROM Product
WHERE Pid = 10
-----------------------
GO
SET SHOWPLAN_ALL OFF
GO

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'NCIndex_ProductPrice') 
	DROP INDEX NCIndex_ProductPrice ON Product
CREATE NONCLUSTERED INDEX NCIndex_ProductPrice ON Product(Price)

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'NCIndex_ProductPid') 
	DROP INDEX NCIndex_ProductPid ON Product
CREATE NONCLUSTERED INDEX NCIndex_ProductPid ON Product(Pid)

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'NCIndex_ProductDescription') 
	DROP INDEX NCIndex_ProductDescription ON Product
CREATE NONCLUSTERED INDEX NCIndex_ProductDescription ON Product(Description)

GO



--- C
CREATE VIEW ViewLab5
AS
SELECT C.FirstName, C.LastName, P.Description, P.Price
FROM Client C INNER JOIN ClientOrders CO ON C.Cid = CO.Cid INNER JOIN Product P ON CO.Pid = P.Pid
WHERE P.Price > 1000 AND P.Gender = 'female'
GO

SELECT * FROM ViewLab5

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'NCIndex_ClientOrdersPid') 
	DROP INDEX NCIndex_ClientOrdersPid ON ClientOrders
CREATE NONCLUSTERED INDEX NCIndex_ClientOrdersPid ON ClientOrders(Pid)

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'NCIndex_ClientOrdersCid') 
	DROP INDEX NCIndex_ClientOrdersCid ON ClientOrders
CREATE NONCLUSTERED INDEX NCIndex_ClientOrdersCid ON ClientOrders(Cid)
