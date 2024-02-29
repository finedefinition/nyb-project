CREATE TABLE keel_types (
                            id BIGSERIAL NOT NULL,
                            name VARCHAR(40) NOT NULL UNIQUE,
                            created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                            updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
                            PRIMARY KEY (id)
);
