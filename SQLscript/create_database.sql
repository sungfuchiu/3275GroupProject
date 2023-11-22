-- @block
CREATE DATABASE IF NOT EXISTS TechAssist;
USE TechAssist;
CREATE TABLE Service_Field(
    id bigint(20) PRIMARY KEY AUTO_INCREMENT,
    field char(50) NOT NULL
);
INSERT INTO Service_Field (field)
VALUES
	("IT Support"),
	("Home Maintenance"),
	("Electrical Services"),
	("Appliance Repair"),
	("Home Maintenance"),
	("Automotive Services"),
	("Plumbing");
CREATE TABLE Time_Slot(
    id bigint(20) PRIMARY KEY AUTO_INCREMENT,
    duration int NOT NULL UNIQUE
);
INSERT Time_Slot(duration)
    VALUES 
            (15),
            (30),
            (45),
            (60);
CREATE TABLE Client(
    id bigint(20) PRIMARY KEY AUTO_INCREMENT
);
INSERT INTO Client(id) 
	VALUES (NULL),(NULL),(NULL),(NULL),(NULL)
    		,(NULL),(NULL),(NULL),(NULL),(NULL)
            ,(NULL),(NULL),(NULL),(NULL),(NULL)
            ,(NULL),(NULL),(NULL),(NULL),(NULL);
CREATE TABLE Technician(
    id bigint(20) PRIMARY KEY AUTO_INCREMENT,
    job_description VARCHAR(300) NOT NULL,
    rate FLOAT NOT NULL,
    service_field_id bigint(20) NOT NULL,
    image_url VARCHAR(100),
    FOREIGN KEY (service_field_id) REFERENCES Service_Field(id)
);
-- Insert dummy data for the technician table
INSERT INTO technician (job_description, rate, service_field_id, image_url)
VALUES
('IT Specialist', 40.0, 1, 'it_specialist.jpg'),
('Electrician', 35.0, 3, 'electrician.jpg'),
('Appliance Technician', 30.0, 4, 'appliance_technician.jpg'),
('Plumber', 32.0, 7, 'plumber.jpg'),
('Home Maintenance Expert', 45.0, 2, 'home_maintenance_expert.jpg'),
('Automotive Mechanic', 38.0, 6, 'automotive_mechanic.jpg'),
('IT Support Technician', 42.0, 1, 'it_support_technician.jpg'),
('Electric Appliance Repair', 33.0, 4, 'electric_appliance_repair.jpg'),
('Plumbing Specialist', 36.0, 7, 'plumbing_specialist.jpg'),
('Home Repair Specialist', 48.0, 2, 'home_repair_specialist.jpg'),
('Auto Technician', 40.0, 6, 'auto_technician.jpg'),
('IT Consultant', 45.0, 1, 'it_consultant.jpg'),
('Home Electrician', 37.0, 3, 'home_electrician.jpg'),
('Appliance Expert', 31.0, 4, 'appliance_expert.jpg'),
('Plumbing Expert', 39.0, 7, 'plumbing_expert.jpg'),
('Home Maintenance Technician', 43.0, 2, 'home_maintenance_technician.jpg'),
('Auto Repair Specialist', 44.0, 6, 'auto_repair_specialist.jpg'),
('IT Expert', 46.0, 1, 'it_expert.jpg'),
('Electrical Technician', 34.0, 3, 'electrical_technician.jpg'),
('Appliance Specialist', 29.0, 4, 'appliance_specialist.jpg');
CREATE TABLE User(
    username  VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    client_id bigint(20) UNIQUE,
    technician_id bigint(20) UNIQUE,
    FOREIGN KEY (client_id) REFERENCES Client(id),
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
-- Insert dummy data for the user table
INSERT INTO user (username, name, password, client_id, technician_id)
VALUES
('user1', 'User One', 'password1', 1, NULL),
('user2', 'User Two', 'password2', 2, NULL),
('user3', 'User Three', 'password3', 3, NULL),
('user4', 'User Four', 'password4', 4, NULL),
('user5', 'User Five', 'password5', 5, NULL),
('user6', 'User Six', 'password6', 6, NULL),
('user7', 'User Seven', 'password7', 7, NULL),
('user8', 'User Eight', 'password8', 8, NULL),
('user9', 'User Nine', 'password9', 9, NULL),
('user10', 'User Ten', 'password10', 10, NULL),
('user11', 'User Eleven', 'password11', 11, NULL),
('user12', 'User Twelve', 'password12', 12, NULL),
('user13', 'User Thirteen', 'password13', 13, NULL),
('user14', 'User Fourteen', 'password14', 14, NULL),
('user15', 'User Fifteen', 'password15', 15, NULL),
('user16', 'User Sixteen', 'password16', 16, NULL),
('user17', 'User Seventeen', 'password17', 17, NULL),
('user18', 'User Eighteen', 'password18', 18, NULL),
('user19', 'User Nineteen', 'password19', 19, NULL),
('user20', 'User Twenty', 'password20', 20, NULL),
('user21', 'User Twenty-One', 'password21', NULL, 1),
('user22', 'User Twenty-Two', 'password22', NULL, 2),
('user23', 'User Twenty-Three', 'password23', NULL, 3),
('user24', 'User Twenty-Four', 'password24', NULL, 4),
('user25', 'User Twenty-Five', 'password25', NULL, 5),
('user26', 'User Twenty-Six', 'password26', NULL, 6),
('user27', 'User Twenty-Seven', 'password27', NULL, 7),
('user28', 'User Twenty-Eight', 'password28', NULL, 8),
('user29', 'User Twenty-Nine', 'password29', NULL, 9),
('user30', 'User Thirty', 'password30', NULL, 10),
('user31', 'User Thirty-One', 'password31', NULL, 11),
('user32', 'User Thirty-Two', 'password32', NULL, 12),
('user33', 'User Thirty-Three', 'password33', NULL, 13),
('user34', 'User Thirty-Four', 'password34', NULL, 14),
('user35', 'User Thirty-Five', 'password35', NULL, 15),
('user36', 'User Thirty-Six', 'password36', NULL, 16),
('user37', 'User Thirty-Seven', 'password37', NULL, 17),
('user38', 'User Thirty-Eight', 'password38', NULL, 18),
('user39', 'User Thirty-Nine', 'password39', NULL, 19),
('user40', 'User Forty', 'password40', NULL, 20);
-- @block
CREATE TABLE Phone_Call(
    id bigint(20) PRIMARY KEY AUTO_INCREMENT,
    start_time DATETIME NOT NULL,
    rating INT,
    review VARCHAR(200),
    cost FLOAT NOT NULL,
    duration_slot bigint(20) NOT NULL,
    start_slot bigint(20) NOT NULL,
    client_id bigint(20) NOT NULL,
    technician_id bigint(20) NOT NULL,
    FOREIGN KEY (duration_slot) REFERENCES time_slot(id),
    FOREIGN KEY (start_slot) REFERENCES time_slot(id),
    FOREIGN KEY (client_id) REFERENCES Client(id),
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
-- Insert dummy data for the phonecall table with unique client_id and technician_id
INSERT INTO phone_call (start_time, rating, review, cost, duration_slot, start_slot, client_id, technician_id)
VALUES
-- Calls for the first 20 clients and technicians
('2023-11-05 10:00:00', 4, 'Good service', 30.0, 1, 1, 1, 2),
('2023-11-10 14:30:00', 5, 'Excellent service', 45.0, 2, 2, 2, 3),
('2023-11-15 08:45:00', 3, 'Average service', 25.0, 3, 3, 3, 4),
('2023-11-20 12:15:00', 4, 'Satisfied', 35.0, 4, 4, 4, 5),
('2023-11-25 09:30:00', 5, 'Great job', 50.0, 1, 1, 5, 6),
('2023-11-30 11:45:00', 3, 'Could be better', 28.0, 2, 2, 6, 7),
('2023-12-05 10:30:00', 4, 'Impressed', 32.0, 3, 3, 7, 8),
('2023-12-10 14:00:00', 5, 'Top-notch service', 48.0, 4, 4, 8, 9),
('2023-12-15 08:15:00', 3, 'Not bad', 27.0, 1, 1, 9, 10),
('2023-12-20 12:45:00', 4, 'Well done', 38.0, 2, 2, 10, 11),
('2023-12-25 09:00:00', 5, 'Excellent work', 55.0, 3, 3, 11, 12),
('2023-12-30 11:15:00', 3, 'Good experience', 30.0, 4, 4, 12, 13),
('2024-01-05 10:45:00', 4, 'Satisfied customer', 40.0, 1, 1, 13, 14),
('2024-01-10 13:30:00', 5, 'Professional service', 50.0, 2, 2, 14, 15),
('2024-01-15 08:00:00', 3, 'Average rating', 28.0, 3, 3, 15, 16),
('2024-01-20 12:30:00', 4, 'Good communication', 35.0, 4, 4, 16, 17),
('2024-01-25 09:45:00', 5, 'Impressive work', 48.0, 1, 1, 17, 18),
('2024-01-30 11:00:00', 3, 'Prompt service', 33.0, 2, 2, 18, 19),
('2024-02-05 10:15:00', 4, 'Well organized', 38.0, 3, 3, 19, 20),
('2024-02-10 13:45:00', 5, 'Very satisfied', 52.0, 4, 4, 20, 1),
('2023-11-05 10:00:00', 4, 'Good service', 30.0, 1, 1, 1, 2),
('2023-11-10 14:30:00', 5, 'Excellent service', 45.0, 2, 2, 2, 3),
('2023-11-15 08:45:00', 3, 'Average service', 25.0, 3, 3, 3, 4),
('2023-11-20 11:15:00', 4, 'Satisfied', 35.0, 4, 4, 4, 5),
('2023-11-25 13:30:00', 5, 'Great job', 50.0, 1, 1, 5, 6),
('2023-11-30 09:45:00', 3, 'Could be better', 28.0, 2, 2, 6, 7),
('2023-12-05 11:30:00', 4, 'Impressed', 40.0, 3, 3, 7, 8),
('2023-12-10 14:00:00', 5, 'Very professional', 55.0, 4, 4, 8, 9),
('2023-12-15 08:15:00', 3, 'On time and efficient', 32.0, 1, 1, 9, 10),
('2023-12-20 10:45:00', 4, 'Excellent workmanship', 38.0, 2, 2, 10, 11),
('2023-12-25 12:30:00', 5, 'Highly recommended', 48.0, 3, 3, 11, 12),
('2023-12-30 09:00:00', 3, 'Satisfactory', 33.0, 4, 4, 12, 13),
('2024-01-05 11:15:00', 4, 'Timely service', 42.0, 1, 1, 13, 14),
('2024-01-10 13:45:00', 5, 'Friendly technician', 52.0, 2, 2, 14, 15),
('2024-01-15 08:00:00', 3, 'Good communication', 29.0, 3, 3, 15, 16),
('2024-01-20 10:30:00', 4, 'Impressive skills', 36.0, 4, 4, 16, 17),
('2024-01-25 12:15:00', 5, 'Courteous service', 46.0, 1, 1, 17, 18),
('2024-01-30 09:30:00', 3, 'Overall good experience', 31.0, 2, 2, 18, 19),
('2024-02-05 11:45:00', 4, 'Professional attitude', 44.0, 3, 3, 19, 20),
('2024-02-10 13:00:00', 5, 'Excellent communication', 54.0, 4, 4, 20, 1),
('2023-11-05 10:00:00', 4, 'Good service', 30.0, 1, 1, 1, 2),
('2023-11-10 14:30:00', 5, 'Excellent service', 45.0, 2, 2, 2, 3),
('2023-11-15 08:45:00', 3, 'Average service', 25.0, 3, 3, 3, 4),
('2023-11-20 11:15:00', 4, 'Satisfied', 35.0, 4, 4, 4, 5),
('2023-11-25 13:30:00', 5, 'Great job', 50.0, 1, 1, 5, 6),
('2023-11-30 09:45:00', 3, 'Could be better', 28.0, 2, 2, 6, 7),
('2023-12-05 11:30:00', 4, 'Impressed', 40.0, 3, 3, 7, 8),
('2023-12-10 14:00:00', 5, 'Very professional', 55.0, 4, 4, 8, 9),
('2023-12-15 08:15:00', 3, 'On time and efficient', 32.0, 1, 1, 9, 10),
('2023-12-20 10:45:00', 4, 'Excellent workmanship', 38.0, 2, 2, 10, 11),
('2023-12-25 12:30:00', 5, 'Highly recommended', 48.0, 3, 3, 11, 12),
('2023-12-30 09:00:00', 3, 'Satisfactory', 33.0, 4, 4, 12, 13),
('2023-12-31 11:15:00', 4, 'Timely service', 42.0, 1, 1, 13, 14),
('2024-01-02 13:45:00', 5, 'Friendly technician', 52.0, 2, 2, 14, 15),
('2024-01-05 08:00:00', 3, 'Good communication', 29.0, 3, 3, 15, 16),
('2024-01-10 10:30:00', 4, 'Impressive skills', 36.0, 4, 4, 16, 17),
('2024-01-15 12:15:00', 5, 'Courteous service', 46.0, 1, 1, 17, 18),
('2024-01-20 09:30:00', 3, 'Overall good experience', 31.0, 2, 2, 18, 19),
('2024-01-25 11:45:00', 4, 'Professional attitude', 44.0, 3, 3, 19, 20),
('2024-01-30 13:00:00', 5, 'Excellent communication', 54.0, 4, 4, 20, 1);
CREATE TABLE Available_Time(
    id bigint(20) PRIMARY KEY AUTO_INCREMENT,
    week_day int NOT NULL CHECK (week_day >= 1 AND week_day <= 7),
    hour int NOT NULL CHECK (hour >= 0 AND hour <= 23),
    duration_slot bigint(20) NOT NULL,
    start_slot bigint(20) NOT NULL,
    technician_id bigint(20) NOT NULL,
    FOREIGN KEY (duration_slot) REFERENCES time_slot(id),
    FOREIGN KEY (start_slot) REFERENCES time_slot(id),
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
INSERT INTO Available_Time (week_day, hour, duration_slot, start_slot, technician_id)
VALUES
(1, 9, 2, 1, 1),
(3, 14, 3, 2, 1),
(5, 10, 1, 3, 1),
(2, 13, 4, 4, 2),
(4, 11, 2, 1, 2),
(6, 15, 3, 2, 2),
(1, 8, 1, 3, 3),
(3, 12, 4, 4, 3),
(5, 14, 2, 1, 3),
(2, 10, 3, 2, 4),
(4, 16, 1, 3, 4),
(6, 9, 4, 4, 4),
(1, 11, 3, 1, 5),
(3, 15, 2, 2, 5),
(5, 12, 4, 3, 5),
(2, 14, 1, 4, 6),
(4, 10, 3, 1, 6),
(6, 13, 2, 2, 6),
(1, 8, 4, 3, 7),
(3, 11, 1, 4, 7),
(5, 16, 3, 1, 7),
(2, 13, 2, 2, 8),
(4, 9, 4, 3, 8),
(6, 12, 1, 4, 8),
(1, 10, 3, 1, 9),
(3, 14, 2, 2, 9),
(5, 11, 4, 3, 9),
(2, 11, 4, 1, 10),
(4, 15, 1, 2, 10),
(6, 12, 3, 3, 10),
(1, 14, 2, 1, 11),
(3, 10, 4, 2, 11),
(5, 13, 1, 3, 11),
(2, 9, 3, 4, 12),
(4, 12, 2, 1, 12),
(6, 16, 4, 2, 12),
(1, 13, 1, 3, 13),
(3, 11, 3, 4, 13),
(5, 14, 2, 1, 13);
CREATE TABLE Technician_Certificate(
    id bigint(20) PRIMARY KEY AUTO_INCREMENT,
    certificate_url VARCHAR(200) NOT NULL,
    technician_id bigint(20) NOT NULL,
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
-- Insert dummy data for the technician_certificate table
INSERT INTO technician_certificate (certificate_url, technician_id)
VALUES
('certificate_url_1', 1),
('certificate_url_2', 2),
('certificate_url_3', 3),
('certificate_url_4', 4),
('certificate_url_5', 5),
('certificate_url_6', 6),
('certificate_url_7', 7),
('certificate_url_8', 8),
('certificate_url_9', 9),
('certificate_url_10', 10),
('certificate_url_11', 11),
('certificate_url_12', 12),
('certificate_url_13', 13),
('certificate_url_14', 14),
('certificate_url_15', 15),
('certificate_url_16', 16),
('certificate_url_17', 17),
('certificate_url_18', 18),
('certificate_url_19', 19),
('certificate_url_20', 20);
CREATE TABLE Technician_Experience(
    id bigint(20) PRIMARY KEY AUTO_INCREMENT,
    experience_description VARCHAR(200) NOT NULL,
    start_year INT NOT NULL,
    year INT NOT NULL,
    technician_id bigint(20) NOT NULL,
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
INSERT INTO Technician_Experience (experience_description, start_year, year, technician_id)
VALUES
('Experience Description 1', 2005, 5, 1),
('Experience Description 2', 2010, 7, 2),
('Experience Description 3', 2002, 12, 3),
('Experience Description 4', 2015, 4, 4),
('Experience Description 5', 2008, 9, 5),
('Experience Description 6', 2019, 3, 6),
('Experience Description 7', 2006, 8, 7),
('Experience Description 8', 2012, 6, 8),
('Experience Description 9', 2000, 15, 9),
('Experience Description 10', 2018, 5, 10),
('Experience Description 11', 2003, 10, 11),
('Experience Description 12', 2014, 8, 12),
('Experience Description 13', 2007, 11, 13),
('Experience Description 14', 2011, 7, 14),
('Experience Description 15', 2009, 13, 15),
('Experience Description 16', 2017, 6, 16),
('Experience Description 17', 2004, 14, 17),
('Experience Description 18', 2016, 9, 18),
('Experience Description 19', 2001, 12, 19),
('Experience Description 20', 2020, 4, 20);
