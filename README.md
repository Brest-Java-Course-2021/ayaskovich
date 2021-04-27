#Project "Human Resources"  
Author: Aleh Yaskovich  
Contact: Aleh_Yaskovich@epam.com 
***
##Short description:  
The project is a program for keeping track of employees. 
It consists of two parts - the "Departments" part 
and the "Employees" part.
***
##System requirements:  
- IntelliJ Idea Community Edition or another IDE  
- Maven 3+  
- Java 15+
***
##Application launch  
The project consists of two applications - 
server-application and client-application.  
To run the project you need to follow these steps:  
In Terminal execute the command in the root folder of the project  
```sh
mvn clean install 
``` 
After that, the directory "rest-app" will have the jar-archive 
"target/rest-app-1.0-SNAPSHOT.jar", 
and the directory "web-app" will have the jar-archive
"target/web-app-1.0-SNAPSHOT.jar"

To run the server-application you need to execute the command in Terminal  
```sh
java -jar rest-app-1.0-SNAPSHOT.jar  
```
The server-application runs on the port 8088: http://localhost:8088/departments

To run the client-application you need to execute the command in Terminal  
```sh
java -jar web-app-1.0-SNAPSHOT.jar  
```
he web-application runs on the port 8080: http://localhost:8080/

**IMPORTANT: both applications must be running for the project to work.**
***
##Description

###The “Departments” part
- The “Departments” part is a table of departments of the organization 
with the fields: "Name" and "AvgSalary" (Average salary of employees). 
The application can create, edit and delete fields of departments.

- To create a new field of the department, click the "+ Add" link, 
the data in the opened form and click the "Save" button to save or "Cancel" to cancel.
  
- To edit a field of the department, click the "Edit" link, 
after which an editing form with the current data should open. 
After editing, you must click the "Save" button to save or "Cancel" to cancel.
  
- To delete a field of the department, click “Delete” link, 
and then confirm or cancel deletion in the opened modal window.  
**IMPORTANT: you can delete a department only by first deleting all 
employees related to it.**

###The “Employees” part
- The “Employees” part is a table of employees of the organization with the fields: 
"Name", “Email”,  "Salary" and “Hired”. The application can create, edit 
and delete fields of employees, and also select employees by hiring date.

- To create a new field of the employee, click the "+ Add" link, enter the data 
in the opened form and click the "Save" button to save or "Cancel" to cancel.

- To edit a field of the employee, click the "Edit" link, after which an editing 
form with the current data should open. After editing, you must click the "Save" 
button to save or "Cancel" to cancel.

- To delete a field of the employee, click “Delete” link, and then confirm 
or cancel deletion in the opened modal window.

- To search by the hiring date, click the link "Find by date", after that a 
form with the fields "First date" and "Second date" will open in a modal window. 
In these fields you need to enter data manually or using a pop-up calendar. 
Then you need to click the "Search" button to search or "Cancel" to cancel.
  
