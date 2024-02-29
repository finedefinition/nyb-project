CREATE TABLE yachts (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        featured TINYINT(1) NOT NULL,
                        price DECIMAL(10, 2) NOT NULL,
                        main_image_key VARCHAR(40),
                        yacht_model_id BIGINT UNSIGNED,
                        country_id BIGINT,
                        town_id BIGINT,
                        yacht_detail_id BIGINT,
                        owner_info_id BIGINT,
                        created_at DATETIME NOT NULL,
                        FOREIGN KEY (yacht_model_id) REFERENCES yacht_models(id),
                        FOREIGN KEY (country_id) REFERENCES countries(id),
                        FOREIGN KEY (town_id) REFERENCES towns(id),
                        FOREIGN KEY (yacht_detail_id) REFERENCES yacht_details(id),
                        FOREIGN KEY (owner_info_id) REFERENCES owner_infos(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO
