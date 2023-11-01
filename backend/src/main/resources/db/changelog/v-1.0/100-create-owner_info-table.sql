CREATE TABLE IF NOT EXISTS owner_info
(
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `first_name`             varchar(255),
    `last_name`             varchar(255),
    `telephone`             varchar(255),
    `email`             varchar(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 106
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
GO
