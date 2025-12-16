CREATE TABLE types (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE pokemons (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description VARCHAR(100),
power DECIMAL(10,2) NOT NULL,
type_id BIGINT NOT NULL,
FOREIGN KEY (type_id) REFERENCES types(id)
);

CREATE TABLE trainers (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
surname VARCHAR(100) NOT NULL
);

CREATE TABLE experiences (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
exp_time INT UNIQUE
);

CREATE TABLE profiles (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
age INT,
country VARCHAR(100),
experience BIGINT,
trainer_id BIGINT UNIQUE,
FOREIGN KEY (trainer_id) REFERENCES trainers(id),
FOREIGN KEY (experience) REFERENCES experiences(id)
);

CREATE TABLE pokedex (
trainer_id BIGINT,
pokemon_id BIGINT,
PRIMARY KEY (trainer_id, pokemon_id),
FOREIGN KEY (trainer_id) REFERENCES trainers(id),
FOREIGN KEY (pokemon_id) REFERENCES pokemons(id)
);





