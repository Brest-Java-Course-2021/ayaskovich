INSERT INTO DEPARTMENT (DEPARTMENT_ID, DEPARTMENT_NAME) VALUES ( 1, 'IT' );
INSERT INTO DEPARTMENT (DEPARTMENT_ID, DEPARTMENT_NAME) VALUES ( 2, 'SECURITY' );
INSERT INTO DEPARTMENT (DEPARTMENT_ID, DEPARTMENT_NAME) VALUES ( 3, 'MANAGEMENT' );

INSERT INTO EMPLOYEE (employee_id, firstname, lastname, email, salary, department_id, hired)
                VALUES (1, 'Ivan', 'Ivanov', 'ivanov@mail.com', 1000, 1, '2020-11-03');
INSERT INTO EMPLOYEE (employee_id, firstname, lastname, email, salary, department_id, hired)
                VALUES (2, 'Petr', 'Petrov', 'petrov@mail.com', 1150, 1, '2019-07-25');
INSERT INTO EMPLOYEE (employee_id, firstname, lastname, email, salary, department_id, hired)
                VALUES (3, 'John', 'Johnson', 'johnson@gmail.com', 1500, 2, '2020-01-10');
INSERT INTO EMPLOYEE (employee_id, firstname, lastname, email, salary, department_id, hired)
                VALUES (4, 'Pavel', 'Durov', 'durov@yandex.ru', 400, 2, '2021-02-22');
INSERT INTO EMPLOYEE (employee_id, firstname, lastname, email, salary, department_id, hired)
                VALUES (5, 'Ilon', 'Mask', 'ilon.mask@gmail.com', 670, 2, '2015-12-17');