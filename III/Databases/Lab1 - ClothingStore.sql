--- LAB. 1 --- Create database ---------------------------------------------------------------------------------------------------------------


DROP DATABASE ClothingStore

CREATE DATABASE ClothingStore
GO
USE ClothingStore
GO


CREATE TABLE Designer(
	Did VARCHAR(10) PRIMARY KEY,
	Name VARCHAR(50),
	CountryOfOrigin VARCHAR(20)
	)

INSERT INTO Designer(Did, Name, CountryOfOrigin) VALUES
	('BB', 'BALENCIAGA', 'Spain'),
	('LV', 'Louis Vuitton', 'France'),
	('VRSC', 'Versace', 'Italy'),
	('IM', 'Issey Miyake', 'Japan'), 
	('DK', 'DKNY', 'USA'),
	('KNZ', 'Kenzo', 'Japan'),
	('HRMS', 'Hermes', 'France')
	
	
CREATE TABLE Collection(
	Cid INT PRIMARY KEY,
	Did VARCHAR(10) FOREIGN KEY REFERENCES Designer(Did),
	Season VARCHAR(2),
	Year SMALLINT
	)

INSERT INTO Collection(Cid, Did, Season, Year) VALUES 
	(1, 'BB', 'SS', 2020),
	(2, 'IM', 'AW', 2020),
	(3, 'VRSC', 'AW', 2021),
	(4, 'BB', 'AW', 2020),
	(5, 'DK', 'AW', 2020),
	(6, 'VRSC', 'SS', 2021),
	(7, 'LV', 'SS', 2020),
	(8, 'HRMS', 'SS', 2021)


CREATE TABLE Product(
	Pid INT PRIMARY KEY,
	Description VARCHAR(200),
	Cid INT FOREIGN KEY REFERENCES Collection(Cid),
	Cathegory VARCHAR(50),
	Gender VARCHAR(20),
	Price SMALLINT,
	)


INSERT INTO Product(Pid, Description, Cid, Cathegory, Gender, Price) VALUES
	(1, 'Triple S sneakers', 4, 'footwear', 'male', 820),
	(2, 'linchpin dress', 6, 'clothing', 'female', 2680),
	(3, 'monogram belt', 7, 'accessories', 'unisex', 365),
	(4, 'pleated skirt', 2, 'clothing', 'female', 755),
	(5, 'puff jacket', 5, 'clothing', 'male', 290),
	(6, 'speed trainers', 1, 'footwear', 'unisex', 595),
	(7, 'monogram tote bag', 7, 'accessories', 'female', 2370),
	(8, 'voyage bag', 8, 'accessories', 'female', 4850),
	(9, 'oversized raincoat', 4, 'clothing', 'male', 1850),
	(10, 'baroque shirt', 6, 'clothing', 'male', 1195)


CREATE TABLE Warehouse(
	Wid INT PRIMARY KEY,
	Address VARCHAR(100),
	Capacity INT,
	Occupancy DECIMAL(4,2)
	)

INSERT INTO Warehouse(Wid, Address, Capacity, Occupancy) VALUES
	(1, 'Paris, France', 1000000, 54.10),
	(2, 'London, UK', 5000000, 86.40),
	(3, 'Amsterdam, Holland', 10000000, 75.50)


CREATE TABLE Stock(
	Pid INT FOREIGN KEY REFERENCES Product(Pid),
	Wid INT FOREIGN KEY REFERENCES Warehouse(Wid),
	Quantity INT,
	CONSTRAINT Sid PRIMARY KEY (Pid, Wid)
	)

INSERT INTO Stock(Pid, Wid, Quantity) VALUES
	(1, 2, 1000),
	(1, 1, 500),
	(5, 2, 4000),
	(1, 3, 2500),
	(7, 1, 400),
	(9, 3, 500),
	(4, 1, 900),
	(6, 3, 1500),
	(2, 1, 250),
	(6, 2, 1000)


CREATE TABLE Supplier(
	Sid INT PRIMARY KEY,
	Name VARCHAR(100),
	Address VARCHAR(100)
	)


CREATE TABLE SupplierOrders(
	SOid INT PRIMARY KEY,
	Pid INT FOREIGN KEY REFERENCES Product(Pid),
	Sid INT FOREIGN KEY REFERENCES Supplier(Sid),
	Quantity INT,
	Status VARCHAR(20),
	)


CREATE TABLE Client(
	Cid INT PRIMARY KEY,
	FirstName VARCHAR(50),
	LastName VARCHAR(50),
	Email VARCHAR(50)
	)


CREATE TABLE ClientOrders(
	COid INT PRIMARY KEY,
	Pid INT FOREIGN KEY REFERENCES Product(Pid),
	Cid INT FOREIGN KEY REFERENCES Client(Cid),
	Quantity INT,
	Status VARCHAR(20),
	)


CREATE TABLE SalesCampaign(
	SCid INT PRIMARY KEY,
	StartDate SMALLINT,
	StartHour SMALLINT,
	EndDate SMALLINT,
	EndHour SMALLINT
	)


CREATE TABLE Sales(
	Sid INT PRIMARY KEY,
	Pid INT FOREIGN KEY REFERENCES Product(Pid),
	Percentage DECIMAL(4,2),
	CampaignId INT FOREIGN KEY REFERENCES SalesCampaign(SCid)
	)











