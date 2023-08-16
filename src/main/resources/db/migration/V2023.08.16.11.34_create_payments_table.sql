CREATE
    TABLE
        users(
            id INT UNSIGNED NOT NULL AUTO_INCREMENT,
            username VARCHAR(100) NOT NULL,
            password VARCHAR(150) NOT NULL,
            full_name VARCHAR(150) NOT NULL,
            email VARCHAR(100) NOT NULL,
            is_active BIT(1) NOT NULL DEFAULT b'1',
            PRIMARY KEY(id),
            UNIQUE INDEX users_username_uc(username),
            UNIQUE INDEX users_email_uc(email)
        );