# Employee & Inventory Management System

A Java-based project repository containing two management applications developed using Java, Spring Boot, REST APIs, JDBC, and MySQL.

## Projects Included

### 1. Employee Management API

A RESTful API developed using Spring Boot to manage employee information.

**Technologies Used:**

* Java
* Spring Boot
* Spring Web
* REST API
* Maven
* Postman

**Features:**

* Get all employees
* Search employee by ID
* Add a new employee
* Update employee details
* Delete employee
* Handle employee records through REST endpoints

**API Endpoints:**

| Method | Endpoint          | Description        |
| ------ | ----------------- | ------------------ |
| GET    | `/employees`      | Get all employees  |
| GET    | `/employees/{id}` | Get employee by ID |
| POST   | `/employees`      | Add new employee   |
| PUT    | `/employees/{id}` | Update employee    |
| DELETE | `/employees/{id}` | Delete employee    |

**Base URL:**

```text
http://localhost:8080
```

**How to Run:**

1. Open the EmployeeManagementAPI project in IntelliJ IDEA.
2. Configure the Java and Maven environment.
3. Run `EmployeeApplication.java`.
4. Open Postman.
5. Test the API endpoints using the URLs mentioned above.

**Note:** Employee records are currently stored in an in-memory ArrayList.

---

### 2. Inventory Management JDBC

A Java-based inventory management application developed using JDBC and MySQL.

**Technologies Used:**

* Java
* JDBC
* MySQL
* Maven
* SQL

**Features:**

* Database connectivity using JDBC
* Inventory data management
* SQL-based database operations
* MySQL configuration

**How to Run:**

1. Open the InventoryManagementJDBC project in IntelliJ IDEA.
2. Create the required MySQL database.
3. Execute the SQL script available in the project.
4. Configure database credentials in `db.properties`.
5. Run `InventoryManagementApp.java`.

---

## Project Structure

```text
Employee_Management_System/
│
├── EmployeeManagement/
│   ├── E
```
