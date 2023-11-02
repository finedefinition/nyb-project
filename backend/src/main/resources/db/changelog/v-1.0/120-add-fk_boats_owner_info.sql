ALTER TABLE boats ADD CONSTRAINT `fk_boats_owner_info`
FOREIGN KEY (`owner_info_id`) REFERENCES `owner_info`(`id`)
ON DELETE SET NULL
GO