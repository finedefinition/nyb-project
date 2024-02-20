CREATE TABLE yacht_details (
                               id BIGSERIAL PRIMARY KEY,
                               cabin INTEGER,
                               berth INTEGER,
                               heads INTEGER,
                               shower INTEGER,
                               description VARCHAR(5000),
                               created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                               updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL
);
