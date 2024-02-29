CREATE TABLE `yacht_images` (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              image_key VARCHAR(255) NOT NULL,
                              yacht_id BIGINT,
                              FOREIGN KEY (yacht_id) REFERENCES yachts(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO