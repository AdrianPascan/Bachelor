USE ClothingStore
GO


-- TRANSACTION 1


-- dirty reads

BEGIN TRAN
	UPDATE Collection
	SET Year=2021
	WHERE Did LIKE 'BB' AND Year=2020
ROLLBACK TRAN

SELECT * FROM Collection

PRINT @@TRANCOUNT



-- non-repeatable reads

BEGIN TRAN
	UPDATE Collection
	SET Year=2021
	WHERE Did LIKE 'BB' AND Year=2020
ROLLBACK TRAN

SELECT * FROM Collection



-- phantom reads

BEGIN TRAN
	INSERT INTO Collection
		VALUES(100, 'BB', 'SS', 2025)
ROLLBACK TRAN

SELECT * FROM Collection



-- deadlocks 

BEGIN TRAN
	UPDATE Collection
	SET Year=2021
	WHERE Cid = 1

	SELECT *
	FROM Collection
	WHERE Cid = 4
ROLLBACK TRAN

SELECT * FROM Collection