CREATE TABLE `t_bookings`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `date` TIME NOT NULL,
    `phone` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `surname` varchar(255) NOT NULL,
    `property_id` bigint(20),
    PRIMARY KEY (`id`)
);