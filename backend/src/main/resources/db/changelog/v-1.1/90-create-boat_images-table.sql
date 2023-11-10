CREATE TABLE `boat_images` (
                               `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                               `image_key` varchar(255) NOT NULL,
                               `main` tinyint(1) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO