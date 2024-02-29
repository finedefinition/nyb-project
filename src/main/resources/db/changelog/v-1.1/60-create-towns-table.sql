CREATE TABLE `towns` (
                       `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                       `name` VARCHAR(40) NOT NULL,
                       `country_id` BIGINT NOT NULL,
                       UNIQUE (`name`, `country_id`),
                       FOREIGN KEY (`country_id`) REFERENCES `countries`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO