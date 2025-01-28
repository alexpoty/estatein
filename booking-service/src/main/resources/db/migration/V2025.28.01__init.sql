CREATE TABLE `t_properties`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `price` decimal(19,2) NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `t_bookings`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `date` TIME NOT NULL,
    `phone` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `surname` varchar(255) NOT NULL,
    `property_id` bigint(20),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`property_id`) REFERENCES t_properties(`id`)
);