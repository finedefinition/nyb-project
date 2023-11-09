CREATE TABLE `boat_models` (
                              `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                              `make` varchar(40) NOT NULL,
                              `model` varchar(40) NOT NULL,
                              `year` integer NOT NULL,
                              `length_overall` decimal(3, 2) NOT NULL,
                              `beam_width` decimal(2, 2) NOT NULL,
                              `draft_depth` decimal(2, 2) NOT NULL,
                              `keel_type_id` bigint UNSIGNED,
                              `fuel_type_id` bigint UNSIGNED,
                              PRIMARY KEY (`id`),
                              CONSTRAINT `fk_boat_model_keel_type` FOREIGN KEY (`keel_type_id`) REFERENCES `keel_type` (`id`),
                              CONSTRAINT `fk_boat_model_fuel_type` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO

