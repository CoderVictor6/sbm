DROP DATABASE `demo`;

CREATE DATABASE `demo`;

USE `demo`;

CREATE TABLE `user` (
    `id`   BIGINT      NOT NULL PRIMARY KEY,
    `name` VARCHAR(20) NULL,
    `age`  INT         NULL
);

CREATE TABLE `user1` (
    `id`   BIGINT      NOT NULL PRIMARY KEY,
    `name` VARCHAR(20) NOT NULL
);

CREATE TABLE `user2` (
    `id`   BIGINT      NOT NULL PRIMARY KEY,
    `age`  INT         NOT NULL
);

TRUNCATE user;
TRUNCATE user1;
TRUNCATE user2;