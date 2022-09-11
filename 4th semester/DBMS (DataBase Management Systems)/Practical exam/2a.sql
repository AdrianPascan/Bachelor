DROP DATABASE FilmFestival
GO

CREATE DATABASE FilmFestival
GO

USE FilmFestival
GO

--- Producer:Movie relationship is 1:m,
--- i.e. a movie has one producer and a producer can produce multiple movies,
--- that is why we hold a reference to the producer for each movie

--- Cathegory:Movie relationship is 1:m,
--- i.e. a movie has one cathegory and a to a cathegory may belong multiple movies,
--- that is why we hold a reference to the cathegory for each movie

--- Cathegory:Movie relationship is 1:m,
--- i.e. a movie has one cathegory and a to a cathegory may belong multiple movies,
--- that is why we hold a reference to the cathegory for each movie

--- Movie:Schedule relationship is 1:m,
--- i.e. a movie can be scheduled in multiple schedules and a schedule can have one movie
--- that is why we hold a reference to the movie for each schedule

--- Cinema:Schedule relationship is 1:m,
--- i.e. a cinema can be in multiple schedules and a schedule can be scheduled for one cinema
--- that is why we hold a reference to the cinema for each schedule

--- Schedules table has the tuple (Cinema, Time) as a PK
--- because a cinema can be scheduled only once for a given time


--- The database design is in 3NF because it is in 2NF and 
--- there is no non-prime attribute which is transitively dependent on any key in the relation


CREATE TABLE Cathegories(
    CathegoryID INT PRIMARY KEY IDENTITY(1,1),
	CathegoryName VARCHAR(50),
	CathegoryDescription VARCHAR(500),
)

CREATE TABLE Producers(
    ProducerID INT PRIMARY KEY IDENTITY(1,1),
	ProducerFirstName VARCHAR(50),
	ProducerLastName VARCHAR(50),
	ProducerNationality VARCHAR(50),
	ProducerNoOfAwards SMALLINT,
)

CREATE TABLE Movies(
    MovieID INT PRIMARY KEY IDENTITY(1,1),
	MovieTitle VARCHAR(50),
	ProducerID INT REFERENCES Producers(ProducerID),
	CathegoryID INT REFERENCES Cathegories(CathegoryID)
)

CREATE TABLE Cinemas(
	CinemaID INT PRIMARY KEY IDENTITY(1, 1),
	CinemaName VARCHAR(50),
	CinemaLocation VARCHAR(100),
	CinemaNoOfSeats SMALLINT
)

CREATE TABLE Schedules(
	MovieID INT REFERENCES Movies(MovieID),
	CinemaID INT REFERENCES Cinemas(CinemaID),
	ScheduleTime TIME,
	ScheduleNoOfTickets SMALLINT
	PRIMARY KEY(CinemaID, ScheduleTime)
)

GO





INSERT Producers VALUES ('FN1', 'LN1', 'N1', 1), ('FN2', 'LN2', 'N2', 2)
INSERT Cathegories VALUES ('N1', 'D1');
INSERT Movies VALUES ('T1', 1, 1), ('T2', 2, 1), ('T3', 2, 1)
GO

SELECT * FROM Producers
SELECT * FROM Cathegories
SELECT * FROM Movies
GO