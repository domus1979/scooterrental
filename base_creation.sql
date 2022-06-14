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
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE countrys(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    designation VARCHAR(4) NOT NULL
);

CREATE TABLE city_types(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,
    full_name VARCHAR(30)
);

CREATE TABLE cities(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    country_id INT NOT NULL,
    city_type_id INT NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countrys(id),
    FOREIGN KEY (city_type_id) REFERENCES city_types(id)
);

CREATE TABLE street_types(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,
    full_name VARCHAR(30)
);

CREATE TABLE streets(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    city_id INT NOT NULL,
    street_type_id INT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities(id),
    FOREIGN KEY (street_type_id) REFERENCES street_types(id)
);

CREATE TABLE houses(
    id INT PRIMARY KEY AUTO_INCREMENT,
    namber VARCHAR(10) NOT NULL,
    street_id INT NOT NULL,
    FOREIGN KEY (street_id) REFERENCES streets(id)
);

CREATE TABLE adresses(
    id INT PRIMARY KEY AUTO_INCREMENT,
    country_id INT NOT NULL,
    sity_id INT,
    street_id INT,
    house_id INT,
    FOREIGN KEY (country_id) REFERENCES countrys(id),
    FOREIGN KEY (sity_id) REFERENCES cities(id),
    FOREIGN KEY (street_id) REFERENCES streets(id),
    FOREIGN KEY (house_id) REFERENCES houses(id)
);

CREATE TABLE rental_points(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    adress_id INT NOT NULL,
    FOREIGN KEY (adress_id) REFERENCES adresses(id)
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
    model_id INT NOT NULL,
    manufacture_year INT,
    scooter_status SMALLINT(2) NOT NULL,
    FOREIGN KEY (model_id) REFERENCES scooter_models(id)
);

CREATE TABLE scooters_list(
    rental_point_id INT,
    scooter_id INT,
    PRIMARY KEY (rental_point_id, scooter_id),
    FOREIGN KEY (rental_point_id) REFERENCES rental_points(id),
    FOREIGN KEY (scooter_id) REFERENCES scooters(id)
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
    price_list_duration BIGINT NOT NULL,
    actual_duration BIGINT DEFAULT 0 NOT NULL,
    end_time DATE NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0.0 NOT NULL,
    cost DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (start_rental_point_id) REFERENCES rental_points(id),
    FOREIGN KEY (finish_rental_point_id) REFERENCES rental_points(id),
    FOREIGN KEY (scooter_id) REFERENCES scooters(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (price_list_id) REFERENCES price_list(id)
);
