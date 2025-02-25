DROP DATABASE IF EXISTS chattingstorydb;

CREATE DATABASE chattingstorydb DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;

USE chattingstorydb;

SET
    SESSION storage_engine = InnoDB;

SET
    SESSION AUTO_INCREMENT_INCREMENT = 1;

CREATE TABLE
    TBL_USER (
        user_id BIGINT NOT NULL AUTO_INCREMENT,
        user_login VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(1023) NOT NULL,
        user_type VARCHAR(255) NOT NULL,
        PRIMARY KEY (user_id),
        UNIQUE KEY UK_USER_EMAIL (email),
        UNIQUE INDEX UK_USER_TYPE_USER_NAME (user_type, user_login)
    );