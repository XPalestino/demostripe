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

CREATE
    TABLE
        permissions(
            id INT UNSIGNED NOT NULL AUTO_INCREMENT,
            keycode VARCHAR(25) NOT NULL,
            name VARCHAR(100) NOT NULL,
            PRIMARY KEY(id),
            UNIQUE INDEX permissions_keycode_uc(keycode)
        );

CREATE
    TABLE
        users_permissions(
            users_id INT UNSIGNED NOT NULL,
            permissions_id INT UNSIGNED NOT NULL,
            PRIMARY KEY(
                users_id,
                permissions_id
            ),
            CONSTRAINT users_permissions_users_id_fk FOREIGN KEY(users_id) REFERENCES users(id) ON
            DELETE
                CASCADE,
                CONSTRAINT users_permissions_permissions_id_fk FOREIGN KEY(permissions_id) REFERENCES permissions(id) ON
                DELETE
                    CASCADE
        );
