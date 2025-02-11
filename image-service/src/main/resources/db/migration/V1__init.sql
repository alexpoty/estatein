CREATE TABLE `t_images` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `image_url` varchar(255) NOT NULL,
    `property_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`id`)
)