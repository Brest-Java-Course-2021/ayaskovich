DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS DEPARTMENT;

CREATE TABLE DEPARTMENT
(
    DEPARTMENT_ID INT NOT NULL AUTO_INCREMENT,
    DEPARTMENT_NAME VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT DEPARTMENT_PK PRIMARY KEY (DEPARTMENT_ID)
);

CREATE TABLE EMPLOYEE (
	EMPLOYEE_ID INT NOT NULL AUTO_INCREMENT,
	FIRSTNAME VARCHAR(255) NOT NULL,
	LASTNAME VARCHAR(255) NOT NULL,
	EMAIL VARCHAR(255) NOT NULL UNIQUE,
	SALARY INT NOT NULL,
	DEPARTMENT_ID INT NOT NULL,
	HARED DATE NOT NULL,
	CONSTRAINT EMPLOYEE_PK PRIMARY KEY (EMPLOYEE_ID),
	CONSTRAINT EMPLOYEE_DEPARTMENT_FK FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT(DEPARTMENT_ID)
);