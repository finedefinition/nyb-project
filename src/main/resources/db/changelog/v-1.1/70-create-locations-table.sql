CREATE TABLE `locations` (
                          `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                          `country_id` BIGINT NOT NULL,
                          `town_id` BIGINT,
                          FOREIGN KEY (`country_id`) REFERENCES `countries`(`id`),
                          FOREIGN KEY (`town_id`) REFERENCES `towns`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;