CREATE TABLE `yacht_details` (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               cabin INTEGER,
                               berth INTEGER,
                               heads INTEGER,
                               shower INTEGER,
                               description VARCHAR(5000)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
GO