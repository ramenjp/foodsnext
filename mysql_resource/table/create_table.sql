-- USE
USE dev_training

-- CREATE
CREATE TABLE `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(15) NOT NULL,
  `password` char(60) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(255) NOT NULL,
  `self_introduction` text NOT NULL,
  `created_tms` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_tms` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
