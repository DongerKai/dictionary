/*
	dictionary 数据库数据
	数据库 编码 utf8mb4  排序规则 utf8mb4_unicode_ci
*/
DROP TABLE IF EXISTS user;
CREATE TABLE user (
	id VARCHAR (32) NOT NULL COMMENT "主键",  
	account VARCHAR (16) NOT NULL COMMENT "账户名",
	name VARCHAR (16) NOT NULL COMMENT "用户名",
	e_mail VARCHAR (32) NOT NULL COMMENT "电子邮件",
	PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = "dictionary-user";
