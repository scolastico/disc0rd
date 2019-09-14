CREATE TABLE IF NOT EXISTS ` PREFIX settings` (
	`id` int NOT NULL auto_increment,
	`serverid` int NOT NULL,
	`allow_admin_rank` bit(1) NOT NULL DEFAULT `0`,
	PRIMARY KEY( `id`)
);

CREATE TABLE IF NOT EXISTS ` PREFIX sub` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`server` INT NOT NULL,
	`channel` INT NOT NULL,
	`pr0user` varchar(255) NOT NULL,
);