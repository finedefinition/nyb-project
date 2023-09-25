CREATE TABLE IF NOT EXISTS `users_roles`
(
    `role_id` bigint NOT NULL,
    `user_id` bigint NOT NULL,
    KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
    KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`),
    CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci

GO