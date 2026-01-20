CREATE USER 'skyuser'@'localhost' IDENTIFIED BY 'skypass';
GRANT ALL PRIVILEGES ON skybooking.* TO 'skyuser'@'localhost';
FLUSH PRIVILEGES;
