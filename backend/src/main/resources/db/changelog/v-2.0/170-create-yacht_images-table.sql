CREATE TABLE yacht_images (
                              id BIGSERIAL PRIMARY KEY,
                              image_key VARCHAR(255) NOT NULL,
                              yacht_id BIGINT,
                              created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
                              FOREIGN KEY (yacht_id) REFERENCES yachts(id)
);
