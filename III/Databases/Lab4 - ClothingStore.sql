--- LAB. 4 --- Testing (time) ----------------------------------------------------------------------------------------------------------------


USE ClothingStore
GO

--- SCRIPT ---

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestRunTables_Tables]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)

ALTER TABLE [TestRunTables] DROP CONSTRAINT FK_TestRunTables_Tables

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestTables_Tables]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)

ALTER TABLE [TestTables] DROP CONSTRAINT FK_TestTables_Tables

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestRunTables_TestRuns]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)

ALTER TABLE [TestRunTables] DROP CONSTRAINT FK_TestRunTables_TestRuns

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestRunViews_TestRuns]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)

ALTER TABLE [TestRunViews] DROP CONSTRAINT FK_TestRunViews_TestRuns

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestTables_Tests]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)

ALTER TABLE [TestTables] DROP CONSTRAINT FK_TestTables_Tests

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestViews_Tests]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)

ALTER TABLE [TestViews] DROP CONSTRAINT FK_TestViews_Tests

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestRunViews_Views]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)

ALTER TABLE [TestRunViews] DROP CONSTRAINT FK_TestRunViews_Views

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestViews_Views]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)

ALTER TABLE [TestViews] DROP CONSTRAINT FK_TestViews_Views

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[Tables]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)

drop table [Tables]

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[TestRunTables]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)

drop table [TestRunTables]

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[TestRunViews]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)

drop table [TestRunViews]

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[TestRuns]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)

drop table [TestRuns]

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[TestTables]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)

drop table [TestTables]

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[TestViews]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)

drop table [TestViews]

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[Tests]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)

drop table [Tests]

GO



if exists (select * from dbo.sysobjects where id = object_id(N'[Views]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)

drop table [Views]

GO



CREATE TABLE [Tables] (

	[TableID] [int] IDENTITY (1, 1) NOT NULL ,

	[Name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 

) ON [PRIMARY]

GO



CREATE TABLE [TestRunTables] (

	[TestRunID] [int] NOT NULL ,

	[TableID] [int] NOT NULL ,

	[StartAt] [datetime] NOT NULL ,

	[EndAt] [datetime] NOT NULL 

) ON [PRIMARY]

GO



CREATE TABLE [TestRunViews] (

	[TestRunID] [int] NOT NULL ,

	[ViewID] [int] NOT NULL ,

	[StartAt] [datetime] NOT NULL ,

	[EndAt] [datetime] NOT NULL 

) ON [PRIMARY]

GO



CREATE TABLE [TestRuns] (

	[TestRunID] [int] IDENTITY (1, 1) NOT NULL ,

	[Description] [nvarchar] (2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,

	[StartAt] [datetime] NULL ,

	[EndAt] [datetime] NULL 

) ON [PRIMARY]

GO



CREATE TABLE [TestTables] (

	[TestID] [int] NOT NULL ,

	[TableID] [int] NOT NULL ,

	[NoOfRows] [int] NOT NULL ,

	[Position] [int] NOT NULL 

) ON [PRIMARY]

GO



CREATE TABLE [TestViews] (

	[TestID] [int] NOT NULL ,

	[ViewID] [int] NOT NULL 

) ON [PRIMARY]

GO



CREATE TABLE [Tests] (

	[TestID] [int] IDENTITY (1, 1) NOT NULL ,

	[Name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 

) ON [PRIMARY]

GO



CREATE TABLE [Views] (

	[ViewID] [int] IDENTITY (1, 1) NOT NULL ,

	[Name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 

) ON [PRIMARY]

GO



ALTER TABLE [Tables] WITH NOCHECK ADD 

	CONSTRAINT [PK_Tables] PRIMARY KEY  CLUSTERED 

	(

		[TableID]

	)  ON [PRIMARY] 

GO



ALTER TABLE [TestRunTables] WITH NOCHECK ADD 

	CONSTRAINT [PK_TestRunTables] PRIMARY KEY  CLUSTERED 

	(

		[TestRunID],

		[TableID]

	)  ON [PRIMARY] 

GO



ALTER TABLE [TestRunViews] WITH NOCHECK ADD 

	CONSTRAINT [PK_TestRunViews] PRIMARY KEY  CLUSTERED 

	(

		[TestRunID],

		[ViewID]

	)  ON [PRIMARY] 

GO



ALTER TABLE [TestRuns] WITH NOCHECK ADD 

	CONSTRAINT [PK_TestRuns] PRIMARY KEY  CLUSTERED 

	(

		[TestRunID]

	)  ON [PRIMARY] 

GO



ALTER TABLE [TestTables] WITH NOCHECK ADD 

	CONSTRAINT [PK_TestTables] PRIMARY KEY  CLUSTERED 

	(

		[TestID],

		[TableID]

	)  ON [PRIMARY] 

GO



ALTER TABLE [TestViews] WITH NOCHECK ADD 

	CONSTRAINT [PK_TestViews] PRIMARY KEY  CLUSTERED 

	(

		[TestID],

		[ViewID]

	)  ON [PRIMARY] 

GO



ALTER TABLE [Tests] WITH NOCHECK ADD 

	CONSTRAINT [PK_Tests] PRIMARY KEY  CLUSTERED 

	(

		[TestID]

	)  ON [PRIMARY] 

GO



ALTER TABLE [Views] WITH NOCHECK ADD 

	CONSTRAINT [PK_Views] PRIMARY KEY  CLUSTERED 

	(

		[ViewID]

	)  ON [PRIMARY] 

GO



ALTER TABLE [TestRunTables] ADD 

	CONSTRAINT [FK_TestRunTables_Tables] FOREIGN KEY 

	(

		[TableID]

	) REFERENCES [Tables] (

		[TableID]

	) ON DELETE CASCADE  ON UPDATE CASCADE ,

	CONSTRAINT [FK_TestRunTables_TestRuns] FOREIGN KEY 

	(

		[TestRunID]

	) REFERENCES [TestRuns] (

		[TestRunID]

	) ON DELETE CASCADE  ON UPDATE CASCADE 

GO



ALTER TABLE [TestRunViews] ADD 

	CONSTRAINT [FK_TestRunViews_TestRuns] FOREIGN KEY 

	(

		[TestRunID]

	) REFERENCES [TestRuns] (

		[TestRunID]

	) ON DELETE CASCADE  ON UPDATE CASCADE ,

	CONSTRAINT [FK_TestRunViews_Views] FOREIGN KEY 

	(

		[ViewID]

	) REFERENCES [Views] (

		[ViewID]

	) ON DELETE CASCADE  ON UPDATE CASCADE 

GO



ALTER TABLE [TestTables] ADD 

	CONSTRAINT [FK_TestTables_Tables] FOREIGN KEY 

	(

		[TableID]

	) REFERENCES [Tables] (

		[TableID]

	) ON DELETE CASCADE  ON UPDATE CASCADE ,

	CONSTRAINT [FK_TestTables_Tests] FOREIGN KEY 

	(

		[TestID]

	) REFERENCES [Tests] (

		[TestID]

	) ON DELETE CASCADE  ON UPDATE CASCADE 

GO



ALTER TABLE [TestViews] ADD 

	CONSTRAINT [FK_TestViews_Tests] FOREIGN KEY 

	(

		[TestID]

	) REFERENCES [Tests] (

		[TestID]

	),

	CONSTRAINT [FK_TestViews_Views] FOREIGN KEY 

	(

		[ViewID]

	) REFERENCES [Views] (

		[ViewID]

	)

GO

--- SCRIPT END ---


-- TABLE Tables

DELETE FROM Tables

SET IDENTITY_INSERT Tables ON

INSERT INTO Tables(TableID, Name) VALUES
	(1, 'Warehouse'),
	(2, 'Product'),
	(3, 'Stock')

SET IDENTITY_INSERT Tables OFF

SELECT * FROM Tables



-- TABLE Views

DELETE FROM Views

SET IDENTITY_INSERT Views ON

INSERT INTO Views(ViewID, Name) VALUES
	(1, 'View1'),
	(2, 'View2'),
	(3, 'View3')

SET IDENTITY_INSERT Views OFF

SELECT * FROM Views

GO

CREATE VIEW View1 
AS
SELECT * FROM Warehouse
GO

CREATE VIEW View2
AS
SELECT P.Description, S.Quantity
FROM Product P INNER JOIN Stock S ON P.Pid = S.Pid
GO


CREATE VIEW View3
AS
SELECT S.Pid, S.Quantity, S.Wid
FROM Stock S INNER JOIN Warehouse W ON S.Wid = W.Wid
WHERE S.Quantity > 1000
GROUP BY S.Quantity, S.Pid, S.Wid
GO



-- TABLE Tests

DELETE FROM Tests

SET IDENTITY_INSERT Tests ON

INSERT INTO Tests(TestID, Name) VALUES
	(1, 'deleteFromTable'),
	(2, 'insertIntoTable'),
	(3, 'runView')

SET IDENTITY_INSERT Tests OFF

SELECT * FROM Tests



-- TABLE TestViews

DELETE FROM TestViews

INSERT INTO TestViews(TestID, ViewID) VALUES
	(3, 1),
	(3, 2),
	(3, 3)

SELECT * FROM TestViews



-- TABLE TestTables

DELETE FROM TestTables

INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES
	(1, 1, 1000, 1),
	(1, 2, 1000, 2),
	(1, 3, 1000, 3),
	(2, 1, 1000, 1),
	(2, 2, 1000, 2),
	(2, 3, 1000, 3)

SELECT * FROM TestTables


--- PROCEDURES

GO


-- INSERT PROCEDURES


CREATE PROC insertIntoTable1
AS
BEGIN
	DECLARE @iteration INT = 0
	DECLARE @nrOfRows INT = (SELECT T.NoOfRows FROM TestTables T WHERE T.TableID = 1 AND T.TestID = 2)

	DECLARE @wid INT = (SELECT MAX(Wid) FROM Warehouse)
	DECLARE @address VARCHAR(50)
	DECLARE @capacity INT = 1000000
	DECLARE @occupancy DECIMAL(4,2) = 50.00

	WHILE @iteration < @nrOfRows
	BEGIN
		SET @wid = @wid + 1
		SET @address = CONCAT('address', @wid)
		INSERT INTO Warehouse(Wid, Address, Capacity, Occupancy)
					VALUES (@wid, @address, @capacity, @occupancy)

		SET @iteration = @iteration + 1
	END
END

GO


CREATE PROC insertIntoTable2
AS
BEGIN
	DECLARE @iteration INT = 0
	DECLARE @nrOfRows INT = (SELECT T.NoOfRows FROM TestTables T WHERE T.TableID = 2 AND T.TestID = 2)

	DECLARE @pid INT = (SELECT MAX(Pid) FROM Product)
	DECLARE @description VARCHAR(50)
	DECLARE @cid INT = (SELECT TOP(1) Cid FROM Collection)
	DECLARE @cathegory VARCHAR(50)
	DECLARE @gender VARCHAR(50)
	DECLARE @price INT = 1000

	WHILE @iteration < @nrOfRows
	BEGIN
		SET @pid = @pid + 1
		SET @description = CONCAT('description', @pid)
		SET @cathegory = CONCAT('cathegory', @pid)
		SET @gender = CONCAT('gender', @pid)
		INSERT INTO Product(Pid, Description, Cid, Cathegory, Gender, Price)
					VALUES (@pid, @description, @cid, @cathegory, @gender, @price)

		SET @iteration = @iteration + 1
	END
END

GO


CREATE PROC insertIntoTable3
AS 
BEGIN
	DECLARE @iteration INT = 0
	DECLARE @nrOfRows INT = (SELECT T.NoOfRows FROM TestTables T WHERE T.TableID = 3 AND T.TestID = 2)

	DECLARE @pid INT = (SELECT MAX(Pid) FROM Product)
	DECLARE @wid INT = (SELECT MAX(Wid) FROM Warehouse)
	DECLARE @quantity INT = 5000

	WHILE @iteration < @nrOfRows
	BEGIN
		INSERT INTO Stock(Pid, Wid, Quantity) 
					VALUES (@pid, @wid, @quantity)
		SET @pid = @pid - 1
		SET @wid = @wid - 1

		SET @iteration = @iteration + 1
	END
END

GO


-- DELETE PROCEDURES

CREATE PROC deleteFromTable1 
AS
BEGIN
	DECLARE @widMax INT = (SELECT MAX(Wid) FROM Warehouse)
	DECLARE @widMin INT = @widMax - (SELECT T.NoOfRows FROM TestTables T WHERE T.TableID = 1 AND T.TestID = 1)
	DELETE Warehouse WHERE Wid <= @widMax AND Wid > @widMin
END

GO


CREATE PROC deleteFromTable2
AS
BEGIN
	DECLARE @pidMax INT = (SELECT MAX(Pid) FROM Product)
	DECLARE @pidMin INT = @pidMax - (SELECT T.NoOfRows FROM TestTables T WHERE T.TableID = 2 AND T.TestID = 1)
	DELETE Product WHERE Pid <= @pidMax AND Pid > @pidMin
END

GO


CREATE PROC deleteFromTable3
AS
BEGIN
	DECLARE @widMax INT = (SELECT MAX(Wid) FROM Stock)
	DECLARE @widMin INT = @widMax - (SELECT T.NoOfRows FROM TestTables T WHERE T.TableID = 3 AND T.TestID = 1)
	DELETE Stock WHERE Wid <= @widMax AND Wid > @widMin
END

GO


-- RUN VIEW PROCEDURES


CREATE PROC runView1
AS
BEGIN
	SELECT * FROM View1
END

GO


CREATE PROC runView2
AS
BEGIN
	SELECT * FROM View2
END

GO


CREATE PROC runView3
AS
BEGIN
	SELECT * FROM View3
END

GO



-- RUN TEST PROCEDURE


CREATE PROCEDURE testRun @testRunId INT 
AS
BEGIN
	DECLARE @beforeTable DATETIME,
			@afterTableBeforeView DATETIME,
			@afterView DATETIME
	DECLARE @insertCommand VARCHAR(50) = CONCAT('insertIntoTable', @testRunId),
			@deleteCommand VARCHAR(50) = CONCAT('deleteFromTable', @testRunId),
			@viewCommand VARCHAR(50) = CONCAT('runView', @testRunId)


	SET NOCOUNT ON

	SET @beforeTable = GETDATE()
	EXEC @deleteCommand
	EXEC @insertCommand

	SET @afterTableBeforeView = GETDATE()
	EXEC @viewCommand

	SET @afterView = GETDATE()
	

	DECLARE @nextTestRunId INT 
	SET @nextTestRunId = ISNULL((SELECT MAX(TestRunID) FROM TestRuns) + 1, 1)

	SET IDENTITY_INSERT TestRuns ON
	INSERT INTO TestRuns(TestRunID, Description, StartAt, EndAt)
				VALUES ( @nextTestRunId,
						 CONCAT('table + view ', @testRunId),
						 @beforeTable,
						 @afterView
					   )
	SET IDENTITY_INSERT TestRuns OFF

	INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) 
				VALUES ( @nextTestRunId,
						 @testRunId,
						 @beforeTable,
						 @afterTableBeforeView
					   )

	INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt)
				VALUES ( @nextTestRunId, 
						 @testRunId,
						 @afterTableBeforeView,
						 @afterView
					   )

	SET NOCOUNT OFF


	PRINT 'Test time: ' + CONVERT(VARCHAR(100), DATEDIFF(millisecond, @beforeTable, @afterView))
	PRINT 'Test for table time: ' + CONVERT(VARCHAR(100), DATEDIFF(millisecond, @beforeTable, @afterTableBeforeView))
	PRINT 'Test for view time: ' + CONVERT(VARCHAR(100), DATEDIFF(millisecond, @afterTableBeforeView, @afterView))
END

GO



-- RUN TESTS HERE!


SELECT * FROM Warehouse
SELECT * FROM Product
SELECT * FROM Stock


EXEC insertIntoTable1
EXEC insertIntoTable2
EXEC insertIntoTable3

EXEC testRun 3

EXEC deleteFromTable3
EXEC deleteFromTable2
EXEC deleteFromTable1