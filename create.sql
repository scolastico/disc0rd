CREATE TABLE IF NOT EXISTS `pr0user` (
	`id` int NOT NULL auto_increment,
	`username` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `server` (
	`id` int NOT NULL auto_increment,
	`serverid` int NOT NULL,
	`settings` text,
	PRIMARY KEY( `id`)
);

CREATE TABLE IF NOT EXISTS `sub` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`server` INT NOT NULL,
	`pr0user` INT NOT NULL,
	`channel` INT NOT NULL,
	PRIMARY KEY (`id`),
    FOREIGN KEY (`server`) REFERENCES server(id),
    FOREIGN KEY (`pr0user`) REFERENCES pr0user(id)
);