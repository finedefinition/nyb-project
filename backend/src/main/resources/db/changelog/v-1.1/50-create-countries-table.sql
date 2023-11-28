CREATE TABLE `countries` (
                         `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                         `name` VARCHAR(40) NOT NULL,
                         UNIQUE (name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO
