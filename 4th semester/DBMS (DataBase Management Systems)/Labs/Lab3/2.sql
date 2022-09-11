USE ClothingStore
GO


ALTER PROCEDURE insertProductWithStock2 @productID INT, @collectionID INT, @description VARCHAR(200), @warehouseID INT, @quantity INT 
AS
BEGIN
	SET XACT_ABORT OFF
	SET NOCOUNT ON

	BEGIN TRAN
		BEGIN TRY
			INSERT INTO Product(Pid, Cid, Description)
				VALUES(@productID, @collectionID, @description)
		END TRY
		BEGIN CATCH
			PRINT 'An error occurred when inserting the product. Rolling back transaction..'
			ROLLBACK TRAN
			RETURN
		END CATCH

		SAVE TRAN InsertProduct

		BEGIN TRY
			INSERT INTO Stock(Pid, Wid, Quantity) 
				VALUES(@productID, @warehouseID, @quantity)
		END TRY
		BEGIN CATCH
			PRINT 'An error occurred when inserting the stock. Rolling back transaction up to product insertion..'
			ROLLBACK TRAN InsertProduct
		END CATCH

		PRINT 'Insert finished. Committing transaction..'
		COMMIT TRAN

	SET NOCOUNT OFF
END

GO



SELECT * FROM Product
SELECT * FROM Stock
SELECT * FROM Warehouse

-- product ID already existing
EXECUTE insertProductWithStock2 1, 1, 'handbag', 1, 10

-- collection ID non-existent
EXECUTE insertProductWithStock2 100, 100, 'handbag', 1, 10

-- warehouse ID non-existent
EXECUTE insertProductWithStock2 100, 1, 'handbag', 100, 10 

DELETE FROM Product
WHERE Pid = 100

-- valid insert
EXECUTE insertProductWithStock2 100, 1, 'handbag', 1, 10 

DELETE FROM Stock
WHERE Pid = 100 AND Wid = 1

DELETE FROM Product
WHERE Pid = 100
