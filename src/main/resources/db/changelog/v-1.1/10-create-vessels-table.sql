CREATE TABLE `vessels` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `make` varchar(255) NOT NULL,
                           `model` varchar(255) NOT NULL,
                           `price` decimal(10,2) NOT NULL,
                           `year` int NOT NULL,
                           `location_country` varchar(255) NOT NULL,
                           `location_state` varchar(255) NOT NULL,
                           `loa` decimal(6,2) NOT NULL,
                           `beam` decimal(6,2) NOT NULL,
                           `draft` decimal(6,2) NOT NULL,
                           `cabin` int NOT NULL,
                           `berth` int NOT NULL,
                           `engines` int NOT NULL,
                           `description` text NOT NULL,
                           `created_at` datetime NOT NULL,
                           `image_key` varchar(255) NOT NULL,
                           `featured` tinyint(1) NOT NULL,
                           `fuel` varchar(255) DEFAULT NULL,
                           `keel` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO