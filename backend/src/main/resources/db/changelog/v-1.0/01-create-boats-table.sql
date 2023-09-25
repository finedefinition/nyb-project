CREATE TABLE IF NOT EXISTS boats
(
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `name`             varchar(255),    -- Assuming brand is a string with a maximum length of 255 characters
    `price`             decimal(10, 2),  -- Assuming price is a decimal with 2 decimal places
    `brand`             varchar(255),    -- Assuming brand is a string with a maximum length of 255 characters
    `year`              int,             -- Assuming year is an integer
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 106
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
GO


