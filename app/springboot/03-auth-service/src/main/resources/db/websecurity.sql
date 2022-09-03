/*DROP DATABASE websecurity;*/
CREATE DATABASE IF NOT EXISTS websecurity;

USE websecurity;

CREATE TABLE IF NOT EXISTS users(
email VARCHAR(255),
pass_word VARCHAR(255),
PRIMARY KEY(email)
);

CREATE TABLE IF NOT EXISTS user_authorities(
email VARCHAR(255),
authority VARCHAR(255),
PRIMARY KEY(email,authority),
FOREIGN KEY(email) REFERENCES users(email)
);

GRANT ALL PRIVILEGES ON websecurity.* TO 'root'@'%';
FLUSH PRIVILEGES;

/*USE websecurity;
select * from users ;*/