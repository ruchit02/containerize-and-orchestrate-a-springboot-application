CREATE USER 'root'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON employeedocumentdetails.* TO 'root'@'%';
FLUSH PRIVILEGES;