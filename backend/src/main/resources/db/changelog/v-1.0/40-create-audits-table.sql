CREATE TABLE IF NOT EXISTS `audits`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `created_at`       datetime(6)    DEFAULT NULL,
    `update_time_list` varbinary(255) DEFAULT NULL,
    `updated_at`       datetime(6)    DEFAULT NULL,
    `user`             varchar(255)   DEFAULT NULL,
    `abonent_id`       bigint         DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 18
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci

GO