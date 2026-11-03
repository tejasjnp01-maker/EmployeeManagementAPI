# MSE-2 Sample Questions — Complete Solutions

This ZIP contains both solutions from the uploaded sample question document.

## 1) InventoryManagementJDBC — Java JDBC + MySQL
Requirements: JDK 17+, MySQL Server, IntelliJ IDEA, Maven.
1. Run `InventoryManagementJDBC/database/retail_db.sql` in MySQL Workbench.
2. Edit `InventoryManagementJDBC/src/main/resources/db.properties` and set your MySQL password.
3. Open the `InventoryManagementJDBC` folder in IntelliJ as a Maven project.
4. Run `com.kiet.inventory.InventoryManagementApp`.

The menu supports insert product, category/quantity filter sorted by descending price, add stock, search/update price, inventory value, and delete only when quantity is zero.

## 2) EmployeeManagementAPI — Spring Boot REST API
Requirements: JDK 17+, IntelliJ IDEA, Maven.
1. Open `EmployeeManagementAPI` as a Maven project.
2. Run `com.kiet.employee.EmployeeApplication`.
3. Test with Postman at `http://localhost:8080`.

| Method | Endpoint | Action |
|---|---|---|
| GET | `/employees` | Get all employees |
| GET | `/employees/{id}` | Get by ID |
| POST | `/employees` | Add employee |
| PUT | `/employees/{id}` | Update employee |
| DELETE | `/employees/{id}` | Delete employee |

POST JSON example:
```json
{"id":4,"name":"Neha Gupta","department":"IT","salary":55000.0}
```
PUT JSON example:
```json
{"id":1,"name":"Amit Sharma","department":"Finance","salary":65000.0}
```

Employee records are stored in an in-memory list and reset when the app restarts. JDBC records are stored in MySQL. Never upload your real MySQL password to GitHub.
