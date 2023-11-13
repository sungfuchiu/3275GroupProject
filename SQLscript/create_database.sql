-- @block
CREATE TABLE Service_Field(
    id INT PRIMARY KEY AUTO_INCREMENT,
    field char(50) NOT NULL
);
CREATE TABLE Time_Slot(
    id INT PRIMARY KEY AUTO_INCREMENT,
    duration int NOT NULL UNIQUE
);
INSERT Time_Slot(duration)
    VALUES 
            (15),
            (30),
            (45),
            (60);
CREATE TABLE Client(
    id INT PRIMARY KEY AUTO_INCREMENT
);
CREATE TABLE Technician(
    id INT PRIMARY KEY AUTO_INCREMENT,
    job_description VARCHAR(300) NOT NULL,
    rate FLOAT NOT NULL,
    service_field_id INT NOT NULL,
    image_url VARCHAR(100),
    FOREIGN KEY (service_field_id) REFERENCES Service_Field(id)
);
CREATE TABLE User(
    username  VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    client_id INT UNIQUE,
    technician_id INT UNIQUE,
    FOREIGN KEY (client_id) REFERENCES Client(id),
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
CREATE TABLE Phone_Call(
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_time DATETIME NOT NULL,
    rating INT,
    review VARCHAR(200),
    cost FLOAT NOT NULL,
    duration_slot INT NOT NULL,
    start_slot INT NOT NULL,
    client_id INT NOT NULL,
    technician_id INT NOT NULL,
    FOREIGN KEY (duration_slot) REFERENCES time_slot(id),
    FOREIGN KEY (start_slot) REFERENCES time_slot(id),
    FOREIGN KEY (client_id) REFERENCES Client(id),
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
CREATE TABLE Available_Time(
    id INT PRIMARY KEY AUTO_INCREMENT,
    week_day int NOT NULL CHECK (week_day >= 1 AND week_day <= 7),
    hour int NOT NULL CHECK (hour >= 0 AND hour <= 23),
    duration_slot INT NOT NULL,
    start_slot INT NOT NULL,
    technician_id INT NOT NULL,
    FOREIGN KEY (duration_slot) REFERENCES time_slot(id),
    FOREIGN KEY (start_slot) REFERENCES time_slot(id),
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
CREATE TABLE Technician_Certificate(
    id INT PRIMARY KEY AUTO_INCREMENT,
    certificate_url VARCHAR(200) NOT NULL,
    technician_id INT NOT NULL,
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);
CREATE TABLE Technician_Experience(
    id INT PRIMARY KEY AUTO_INCREMENT,
    experience_description VARCHAR(200) NOT NULL,
    start_year INT NOT NULL,
    year INT NOT NULL,
    technician_id INT NOT NULL,
    FOREIGN KEY (technician_id) REFERENCES Technician(id)
);