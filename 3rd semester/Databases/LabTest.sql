--- LAB. TEST --------------------------------------------------------------------------------------------------------------------------------

DROP DATABASE ChildrenGame
GO

CREATE DATABASE ChildrenGame
GO

USE ChildrenGame
GO


-- 1

CREATE TABLE Kindergarden(
	KId INT PRIMARY KEY, -- IDENTITY(1,1),
	Name VARCHAR(100), 
	City VARCHAR(100)
)

CREATE TABLE Teacher (
	TId INT PRIMARY KEY, -- IDENTITY(1,1),
	Name VARCHAR(100),
	Surname VARCHAR(100),
	KId INT FOREIGN KEY REFERENCES Kindergarden(KId)
)

CREATE TABLE Child (
	CId INT PRIMARY KEY, -- IDENTITY(1,1),
	Name VARCHAR(100),
	Surname VARCHAR(100),
	Gender VARCHAR(100),
	DOB DATE,
	KId INT FOREIGN KEY REFERENCES Kindergarden(KId)
)

CREATE TABLE Game (
	GId INT PRIMARY KEY, -- IDENTITY(1,1),
	Name VARCHAR(100) UNIQUE,
	DifficultyScore TINYINT CHECK (DifficultyScore BETWEEN 1 AND 5),
	RecommendedAge TINYINT
)

CREATE TABLE PlaySession (
	PSId INT, -- PRIMARY KEY, -- IDENTITY(1,1),
	GId INT FOREIGN KEY REFERENCES Game(GId),
	Date DATETIME,
	CId INT FOREIGN KEY REFERENCES Child(CId)
)


INSERT INTO Kindergarden(KId, Name, City) VALUES
	(1, 'name1', 'city1'),
	(2, 'name2', 'city2'),
	(3, 'name3', 'city3'),
	(4, 'name4', 'city4'),
	(5, 'name5', 'city5')


INSERT INTO Teacher(TId, Name, Surname, KId) VALUES
	(1, 'name1', 'surname1', 1),
	(2, 'name2', 'surname2', 1),
	(3, 'name3', 'surname3', 4),
	(4, 'name4', 'surname4', 3),
	(5, 'name5', 'surname5', 5)

INSERT INTO Child(CId, Name, Surname, Gender, DOB, KId) VALUES
	(1, 'name1', 'surname1', 'male', '2000-01-01', 1),
	(2, 'name2', 'surname2', 'female', '2001-08-20', 2),
	(3, 'name3', 'surname3', 'female', '2004-10-10', 3),
	(4, 'name4', 'surname4', 'male', '2006-12-12', 5),
	(5, 'name5', 'surname5', 'male', '2003-04-05', 5)

INSERT INTO Game(GId, Name, DifficultyScore, RecommendedAge) VALUES 
	(1, 'name1', 1, 3),
	(2, 'name2', 2, 4),
	(3, 'name3', 3, 4),
	(4, 'name4', 4, 5),
	(5, 'name5', 5, 5)
	 
INSERT INTO PlaySession(PSId, GId, Date, CId) VALUES 
	(1, 1, '2015-05-25 12:00:00', 4),
	(2, 1, '2015-05-25 12:00:00', 3),
	(3, 5, '2015-05-25 12:00:00', 4),
	(4, 4, '2015-05-25 12:00:00', 4),
	(5, 4, '2015-05-25 12:00:00', 1),

GO

DELETE FROM PlaySession
DELETE FROM Game
DELETE FROM Child
DELETE FROM Teacher
DELETE FROM Kindergarden



-- 2

CREATE PROCEDURE deleteGame @name VARCHAR(100) 
AS
	DECLARE @id INT = (SELECT GId FROM Game WHERE Name = @name);
	DELETE FROM PlaySession WHERE GId = @id
	DELETE FROM Game WHERE Name = @name
GO

SELECT * FROM Game
SELECT * FROM PlaySession

EXEC deleteGame 'name1'

GO



-- 3

CREATE OR ALTER VIEW viewGamePlayedByAll 
AS
	SELECT G.Name
	FROM Game G INNER JOIN PlaySession PS ON G.GId = PS.GId 
				INNER JOIN Child C ON C.CId = PS.CId
	GROUP BY G.Name
	HAVING COUNT(*) = (SELECT COUNT(*) FROM Child)
GO

SELECT * FROM viewGamePlayedByAll

GO



-- 4

CREATE FUNCTION kindergardensTeachers (@n INT)
RETURNS TABLE
AS
	RETURN SELECT K.Name
		   FROM Kindergarden K INNER JOIN Teacher T ON K.KId = T.KId
		   GROUP BY K.Name
		   HAVING COUNT(K.Name) >= @n
GO

SELECT * FROM kindergardensTeachers(2)