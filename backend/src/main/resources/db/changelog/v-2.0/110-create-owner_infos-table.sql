CREATE TABLE owner_infos (
                             id BIGSERIAL PRIMARY KEY,
                             first_name VARCHAR(255) NOT NULL,
                             last_name VARCHAR(255) NOT NULL,
                             telephone VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL,
                             created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                             updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
                             UNIQUE (email, telephone)
);
