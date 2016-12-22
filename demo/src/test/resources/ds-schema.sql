create schema if not exists test;

/* H2 doesn't support "ON UPDATE" */
CREATE TABLE if not exists `account` (
  `user_id` int unsigned NOT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  INDEX username_idx(`user_name`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists `follow` (
  `from_uid` int  NOT NULL,
  `to_uid` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`from_uid`,`to_uid`),
  unique index (to_uid,from_uid)
) ENGINE=InnoDB;

CREATE TABLE if not exists `target_like` (
  id int auto_increment,
  `target_id` int  NOT NULL,
  `type` smallint NOT NULL,
  `user_id` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  unique index (target_id,type,user_id)
) ENGINE=InnoDB;

