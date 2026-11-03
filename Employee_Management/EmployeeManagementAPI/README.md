# Employee Management REST API — MSE-2 Q2

Open this folder as a Maven project in IntelliJ IDEA. Use JDK 17+, allow Maven to load dependencies, then run `com.kiet.employee.EmployeeApplication`.

API base URL: `http://localhost:8080`

- GET `http://localhost:8080/employees`
- GET `http://localhost:8080/employees/1`
- POST `http://localhost:8080/employees`
- PUT `http://localhost:8080/employees/1`
- DELETE `http://localhost:8080/employees/3`

POST JSON:
```json
{"id":4,"name":"Neha Gupta","department":"IT","salary":55000.0}
```
PUT JSON:
```json
{"id":1,"name":"Amit Sharma","department":"Finance","salary":65000.0}
```
The API uses an in-memory list; records reset when the app restarts.
