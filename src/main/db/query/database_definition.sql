drop database if exists chattingstorydb;  
create database chattingstorydb;  
CREATE USER 'chattingstory'@'%' IDENTIFIED BY 'chattingstory';  
  
GRANT ALL PRIVILEGES ON chattingstorydb.* TO 'chattingstory'@'%';  
  
SHOW GRANTS FOR 'chattingstory'@'%';
