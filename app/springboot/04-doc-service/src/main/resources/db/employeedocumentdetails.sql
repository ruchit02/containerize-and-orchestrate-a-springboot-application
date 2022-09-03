/*DROP DATABASE employeedocumentdetails;*/

CREATE DATABASE IF NOT EXISTS employeedocumentdetails;
USE employeedocumentdetails;

CREATE TABLE IF NOT EXISTS employeedetails(
emp_id SERIAL,
first_name VARCHAR(255),
last_name VARCHAR(255),
department VARCHAR(255),
expertise VARCHAR(255),
email VARCHAR(255),
PRIMARY KEY(emp_id)
);

CREATE TABLE IF NOT EXISTS employeedocuments(
document_id SERIAL,
doc_name VARCHAR(255),
doc_type VARCHAR(255),
doc_data BLOB,
emp_id BIGINT UNSIGNED NOT NULL,
FOREIGN KEY(emp_id) REFERENCES employeedetails(emp_id)
);

GRANT ALL PRIVILEGES ON employeedocumentdetails.* TO 'root'@'%';
FLUSH PRIVILEGES;

/*USE employeedocumentdetails;
select * from employeedocuments ;
select * from employeedetails;*/