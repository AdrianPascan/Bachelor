-- LAB. 3 --- Version update / restore -------------------------------------------------------------------------------------------------------

USE ClothingStore
GO

-- (A) modify the type of a column

CREATE PROCEDURE version1
AS
BEGIN
	ALTER TABLE Product
	ALTER COLUMN Price INT
END
GO

CREATE PROCEDURE undoVersion1
AS
BEGIN
	ALTER TABLE Product
	ALTER COLUMN Price SMALLINT
END
GO


-- (B) add / remove a column

CREATE PROCEDURE version2
AS
BEGIN
	ALTER TABLE Product
	ADD Color VARCHAR(10)
END
GO

CREATE PROCEDURE undoVersion2
AS
BEGIN
	ALTER TABLE Product
	DROP COLUMN Color
END
GO


-- (C) add / remove a DEFAULT constraint

CREATE PROCEDURE version3
AS
BEGIN
	ALTER TABLE Product
	ADD CONSTRAINT df_ProductPrice DEFAULT 0 FOR Price
END
GO

CREATE PROCEDURE undoVersion3
AS
BEGIN
	ALTER TABLE Product
	DROP CONSTRAINT df_ProductPrice
END
GO


-- (D) add / remove a primary key

CREATE PROCEDURE version4
AS
BEGIN
	CREATE TABLE Employees(
		Eid INT,
		FirstName VARCHAR(50),
		LastName VARCHAR(50)
		CONSTRAINT pk_Employees PRIMARY KEY(Eid)
		)
END
GO

-- Or

CREATE PROCEDURE version4_2
AS
BEGIN
	CREATE TABLE Employees(
		Eid INT,
		FirstName VARCHAR(50),
		LastName VARCHAR(50)
		)
	ALTER TABLE Employees
	ADD CONSTRAINT pk_Employees PRIMARY KEY(Eid)
END
GO

-- Finally

CREATE PROCEDURE undoVersion4
AS
BEGIN
	ALTER TABLE Employees
	DROP CONSTRAINT pk_Employees
	DROP TABLE Employees
END
GO


-- (E) add / remove a candidate key

CREATE PROCEDURE version5
AS
BEGIN
	ALTER TABLE Warehouse
	ADD CONSTRAINT uq_WarehouseAddress UNIQUE(Address)
END
GO

CREATE PROCEDURE undoVersion5
AS
BEGIN
	ALTER TABLE Warehouse
	DROP CONSTRAINT uq_WarehouseAddress
END
GO


-- (F) add / remove a foreign key

CREATE PROCEDURE version6
AS
BEGIN
	ALTER TABLE Employees
	ADD Wid INT
	ALTER TABLE Employees
	ADD CONSTRAINT fk_WarehouseWid FOREIGN KEY(Wid) REFERENCES Warehouse(Wid)
END
GO

CREATE PROCEDURE undoVersion6
AS
BEGIN
	ALTER TABLE Employees
	DROP CONSTRAINT fk_WarehouseWid
	ALTER TABLE Employees
	DROP COLUMN Wid
END
GO


-- (G) create / remove a table

CREATE PROCEDURE version7
AS
BEGIN
	CREATE TABLE Preorders(
		POid INT PRIMARY KEY,
		Pid INT FOREIGN KEY REFERENCES Product(Pid),
		Cid INT FOREIGN KEY REFERENCES Client(Cid),
		Quantity INT,
		Status VARCHAR(20)
		)
END
GO

CREATE PROCEDURE undoVersion7
AS
BEGIN
	DROP TABLE Preorders
END
GO


-- change version

CREATE TABLE Versions(
	Vid INT PRIMARY KEY
	)
INSERT INTO Versions(Vid) VALUES (0)

GO
CREATE PROCEDURE changeVersion @version INT
AS
BEGIN
	IF @version > 7 OR @version < 0
	BEGIN
		RAISERROR('Invalid version value: must be in range [0,7] .', 1, 1)
		RETURN -1
	END

	DECLARE @currentVersion INT
	SET @currentVersion = (SELECT TOP 1 Vid FROM Versions)

	DECLARE @command VARCHAR(20)

	IF @currentVersion != @version
	BEGIN
		IF @currentVersion < @version
		BEGIN
			WHILE @currentVersion != @version
			BEGIN
				SET @currentVersion = @currentVersion + 1
				SET @command = CONCAT('version', @currentVersion)
				EXEC @command
			END
		END
		ELSE
		BEGIN
			WHILE @currentVersion != @version
			BEGIN
				SET @command = CONCAT('undoVersion', @currentVersion)
				EXEC @command
				SET @currentVersion = @currentVersion - 1
			END
		END

		DELETE FROM Versions
		INSERT INTO Versions Values (@version)
	END
END
GO


EXEC changeVersion 21
SELECT * FROM Versions
