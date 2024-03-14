CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(30) NOT NULL,
                       last_name VARCHAR(30) NOT NULL,
                       email VARCHAR(30) UNIQUE NOT NULL,
                       user_roles user_roles NOT NULL
);