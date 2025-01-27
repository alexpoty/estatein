CREATE TABLE `t_properties`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `location` varchar(255) DEFAULT NULL,
    `price` decimal(19,2),
    `area` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);