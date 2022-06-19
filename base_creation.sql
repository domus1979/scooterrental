CREATE DATABASE IF NOT EXISTS scooter;

USE scooter;

CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(75) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(75) NOT NULL,
    last_name VARCHAR(75) NOT NULL,
    email VARCHAR(75) NOT NULL,
    age INT DEFAULT 18 CHECK(Age > 0 AND Age < 100)
);

CREATE TABLE roles(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE roles_list(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE rental_points(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    parent_id INT
);

CREATE TABLE scooter_models(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    max_speed INT,
    work_time INT,
    max_weight INT
);

CREATE TABLE scooters(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    scooter_model_id INT NOT NULL,
    manufacture_year INT,
    scooter_status SMALLINT(2) NOT NULL,
    rental_point_id INT NOT NULL,
    FOREIGN KEY (scooter_model_id) REFERENCES scooter_models(id),
    FOREIGN KEY (rental_point_id) REFERENCES rental_points(id)
);

CREATE TABLE price_types(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    duration_in_minutes BIGINT NOT NULL,
    season_ticket TINYINT(1) DEFAULT 0 NOT NULL
);

CREATE TABLE price_list(
    id INT PRIMARY KEY AUTO_INCREMENT,
    scooter_model_id INT NOT NULL,
    price_type_id INT NOT NULL,
    price DECIMAL(15,2) DEFAULT 0.0 NOT NULL,
    FOREIGN KEY (scooter_model_id) REFERENCES scooter_models(id),
    FOREIGN KEY (price_type_id) REFERENCES price_types(id)
);

CREATE TABLE orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_rental_point_id INT NOT NULL,
    scooter_id INT NOT NULL,
    user_id INT NOT NULL,
    price_list_id INT NOT NULL,
    order_status SMALLINT(2) DEFAULT 0 NOT NULL,
    finish_rental_point_id INT NOT NULL,
    begin_time DATE NOT NULL,
    actual_duration BIGINT DEFAULT 0 NOT NULL,
    end_time DATE NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    discount DOUBLE(5,2) DEFAULT 0.0 NOT NULL,
    cost DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (start_rental_point_id) REFERENCES rental_points(id),
    FOREIGN KEY (finish_rental_point_id) REFERENCES rental_points(id),
    FOREIGN KEY (scooter_id) REFERENCES scooters(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (price_list_id) REFERENCES price_list(id)
);
