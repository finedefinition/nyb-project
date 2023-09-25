CREATE TABLE IF NOT EXISTS `roles`
(
    `id`   bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci

GO

INSERT INTO `roles` (`id`,`name`)
VALUES (1,'ROLE_EMPLOYEE'), (2,'ROLE_MANAGER'), (3,'ROLE_ADMIN')

GO