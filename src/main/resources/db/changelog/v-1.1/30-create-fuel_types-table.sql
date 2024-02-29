CREATE TABLE `fuel_types` (
                             `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                             `name` varchar(40) NOT NULL UNIQUE,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO