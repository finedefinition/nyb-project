CREATE TABLE boat_model (
                            id bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                            make varchar(40) NOT NULL,
                            model varchar(40) NOT NULL,
                            year integer,
                            length_overall decimal(3, 2),
                            beam_width decimal(3, 2),
                            draft_depth decimal(3, 2),
                            keel_type varchar(40),
                            fuel_type varchar(40),
                            PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO

