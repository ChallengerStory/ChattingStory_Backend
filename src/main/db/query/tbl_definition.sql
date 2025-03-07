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
        user_type VARCHAR(255) NOT NULL,
        user_identifier VARCHAR(1023) NOT NULL,
        user_name VARCHAR(1023) NOT NULL,
        profile_url VARCHAR(1023),
        password VARCHAR(1023),
        PRIMARY KEY (user_id),
        UNIQUE INDEX UK_USER_TYPE_USER_NAME (user_type, user_identifier)
    );