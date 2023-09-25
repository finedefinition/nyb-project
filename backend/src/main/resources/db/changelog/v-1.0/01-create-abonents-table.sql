CREATE TABLE IF NOT EXISTS `abonents`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `email`             varchar(255)   DEFAULT NULL,
    `first_name`        varchar(255) NOT NULL,
    `gender`            varchar(255) NOT NULL,
    `last_name`         varchar(255) NOT NULL,
    `middle_name`       varchar(255) NOT NULL,
    `phone_number`      varchar(255)   DEFAULT NULL,
    `created_at`        datetime(6)    DEFAULT NULL,
    `updated_at`        datetime(6)    DEFAULT NULL,
    `update_timestamps` varbinary(255) DEFAULT NULL,
    `user`              varchar(255)   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 106
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci

GO


