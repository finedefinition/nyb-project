CREATE TABLE `yacht_models` (
                              `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                              `make` varchar(40) NOT NULL,
                              `model` varchar(40) NOT NULL,
                              `year` integer NOT NULL,
                              `length_overall` decimal(5, 2) NOT NULL,
                              `beam_width` decimal(5, 2) NOT NULL,
                              `draft_depth` decimal(5, 2) NOT NULL,
                              `keel_type_id` bigint UNSIGNED,
                              `fuel_type_id` bigint UNSIGNED,
                              PRIMARY KEY (`id`),
                              CONSTRAINT `fk_yacht_models_keel_types` FOREIGN KEY (`keel_type_id`) REFERENCES `keel_types` (`id`),
                              CONSTRAINT `fk_yacht_models_fuel_types` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO

