CREATE TABLE countries (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(40) NOT NULL,
                           created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                           updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
                           UNIQUE (name)
);
