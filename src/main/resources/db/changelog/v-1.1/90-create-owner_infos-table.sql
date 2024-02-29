CREATE TABLE `owner_infos` (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             first_name VARCHAR(255) NOT NULL,
                             last_name VARCHAR(255) NOT NULL,
                             telephone VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL,
                             UNIQUE (email, telephone)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO