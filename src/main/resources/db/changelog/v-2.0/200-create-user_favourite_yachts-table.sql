CREATE TABLE user_favourite_yachts (
                                       user_id INT NOT NULL,
                                       yacht_id INT NOT NULL,
                                       PRIMARY KEY (user_id, yacht_id),
                                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                       FOREIGN KEY (yacht_id) REFERENCES yachts(id) ON DELETE CASCADE
);