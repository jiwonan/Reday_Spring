DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS countries;

CREATE TABLE countries (
	id bigint(20) PRIMARY KEY AUTO_INCREMENT,
	country varchar(255) NOT NULL
);

CREATE TABLE user (
	id bigint(20) PRIMARY KEY AUTO_INCREMENT,
	user_name varchar(255),
	user_nickname varchar(255),
	password varchar(255),
	email varchar(255) UNIQUE NOT NULL
);

CREATE TABLE article (
	id bigint(20) PRIMARY KEY AUTO_INCREMENT,
	title varchar(255),
	contents varchar(255),
	heart int(11),
	file_location varchar(255),
	user_id bigint(20) NOT NULL,
	country_id bigint(20) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user (id)
       ON DELETE CASCADE
       ON UPDATE CASCADE,
    FOREIGN KEY (country_id) REFERENCES countries (id)
       ON DELETE CASCADE
       ON UPDATE CASCADE
);