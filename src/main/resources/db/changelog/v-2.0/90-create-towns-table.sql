CREATE TABLE towns (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(40) NOT NULL,
                       country_id BIGINT NOT NULL,
                       created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                       updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
                       UNIQUE (name, country_id),
                       FOREIGN KEY (country_id) REFERENCES countries(id)
);
