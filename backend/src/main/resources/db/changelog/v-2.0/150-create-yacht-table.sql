CREATE TABLE yachts (
                        id BIGSERIAL PRIMARY KEY,
                        featured BOOLEAN NOT NULL,
                        vat_included BOOLEAN NOT NULL DEFAULT FALSE,
                        price DECIMAL(10, 2) NOT NULL,
                        main_image_key VARCHAR(40),
                        yacht_model_id BIGINT,
                        country_id BIGINT,
                        town_id BIGINT,
                        yacht_detail_id BIGINT,
                        owner_info_id BIGINT,
                        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
                        FOREIGN KEY (yacht_model_id) REFERENCES yacht_models(id),
                        FOREIGN KEY (country_id) REFERENCES countries(id),
                        FOREIGN KEY (town_id) REFERENCES towns(id),
                        FOREIGN KEY (yacht_detail_id) REFERENCES yacht_details(id),
                        FOREIGN KEY (owner_info_id) REFERENCES owner_infos(id)
);
