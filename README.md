#🎥** Demo Video Link **
🔗 https://www.loom.com/share/8c8d1248f06646f8b89428de514a6ac7?sid=edb62fe0-eff4-4e99-882a-1f9b251cc6a9 
# 🧑‍💼 Employee Manager System

Welcome to the **Employee Manager System** repository! This **Spring Boot** application allows you to manage **Employees**, **Departments**, and **Tasks** using **CRUD operations**. The application is **Dockerized** for easier setup and execution.

---

## 🚀 Features

- **Employee Management**:
  - **Create** new employees.
  - **Read** employee details.
  - **Update** employee information.
  - **Delete** employees from the system.
  
- **Department Management**:
  - **Create** new departments.
  - **Read** department details.
  - **Update** department information.
  - **Delete** departments from the system.

- **Task Management**:
  - **Create** new tasks for employees.
  - **Read** task details.
  - **Update** task assignments or descriptions.
  - **Delete** tasks from the system.

- **Database Integration**:
  - Uses **MySQL** to store employee, department, and task data.

- **Dockerized**:
  - Fully containerized using Docker for easy setup and execution.

---

## 🛠️ Installation Steps

Follow these steps to get started with the **Employee Manager System**:

### 1. Clone the Repository
Clone the repository to your local machine:
```bash
git clone https://github.com/your-username/employee-manager.git
```

### 2. Set Up MySQL Database (via Docker)
The application uses MySQL as the database. Docker will automatically set up the MySQL service for you.

### 3. Configure the `docker-compose.yml`
Ensure the following changes are made in your `docker-compose.yml` to configure your application and MySQL database:

```yaml
version: '3.8'

services:
  employeemanager_db:
    image: mysql:8.0
    container_name: employeemanager_db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: yourrootpassword  # Root password for MySQL
      MYSQL_DATABASE: employee_manager      # The database for the app
    ports:
      - "3307:3306"  # Exposes MySQL on port 3307 on your host machine
    volumes:
      - db_data:/var/lib/mysql  # Persist MySQL data

  employeemanager_app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: employeemanager_app
    ports:
      - "8080:8080"  # Exposes Spring Boot app on port 8080
    depends_on:
      - employeemanager_db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://employeemanager_db:3306/employee_manager
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: yourrootpassword  # Same password as MYSQL_ROOT_PASSWORD

volumes:
  db_data:  # Define a volume to persist MySQL data
```

### 4. Build the Docker Containers
Now that everything is set up, run the following command to start the application and MySQL containers:

```bash
docker-compose up --build
```

This command will build and start the containers, setting up both the MySQL database and the Spring Boot application.

### 5. Access the Application
- The application will be accessible at `http://localhost:8080` on your machine.
- MySQL will be available at `localhost:3307` (for local development).

---

## ⚙️ CRUD Operations

The **Employee Manager System** supports **CRUD operations** for **Departments**, **Employees**, and **Tasks**. Here's a breakdown of the available operations for each entity:

### Department CRUD Operations

- **Create a Department**:
  - Send a `POST` request to `/api/departments` with department data in the request body.
  
  Example request:
  ```json
  {
    "name": "Sales"
  }
  ```

- **Get All Departments**:
  - Send a `GET` request to `/api/departments` to retrieve all departments.

- **Get a Single Department**:
  - Send a `GET` request to `/api/departments/{id}` to retrieve a department by its ID.

- **Update a Department**:
  - Send a `PUT` request to `/api/departments/{id}` with the updated department data.

  Example request:
  ```json
  {
    "name": "Marketing"
  }
  ```

- **Delete a Department**:
  - Send a `DELETE` request to `/api/departments/{id}` to delete a department.

---

### Employee CRUD Operations

- **Create an Employee**:
  - Send a `POST` request to `/api/employees` with employee data in the request body.

  Example request:
  ```json
  {
    "name": "John Doe",
    "role": "Developer",
    "departmentId": 1
  }
  ```

- **Get All Employees**:
  - Send a `GET` request to `/api/employees` to retrieve all employees.

- **Get a Single Employee**:
  - Send a `GET` request to `/api/employees/{id}` to retrieve an employee by its ID.

- **Update an Employee**:
  - Send a `PUT` request to `/api/employees/{id}` with the updated employee data.

  Example request:
  ```json
  {
    "name": "John Smith",
    "role": "Senior Developer",
    "departmentId": 1
  }
  ```

- **Delete an Employee**:
  - Send a `DELETE` request to `/api/employees/{id}` to delete an employee.

---

### Task CRUD Operations

- **Create a Task**:
  - Send a `POST` request to `/api/tasks` with task data in the request body.

  Example request:
  ```json
  {
    "description": "Complete the project documentation",
    "employeeId": 1
  }
  ```

- **Get All Tasks**:
  - Send a `GET` request to `/api/tasks` to retrieve all tasks.

- **Get a Single Task**:
  - Send a `GET` request to `/api/tasks/{id}` to retrieve a task by its ID.

- **Update a Task**:
  - Send a `PUT` request to `/api/tasks/{id}` with the updated task data.

  Example request:
  ```json
  {
    "description": "Complete the project documentation (updated)"
  }
  ```

- **Delete a Task**:
  - Send a `DELETE` request to `/api/tasks/{id}` to delete a task.

---

## ⚠️ How to Fix "Connection Failed" Error in MySQL

If you encounter a `java.sql.SQLException: Access denied for user 'root'@'localhost'` error, follow these troubleshooting steps:

### 1. **Verify MySQL Permissions**

Make sure MySQL is configured correctly. If using Docker, ensure that the `MYSQL_ROOT_PASSWORD` is correctly set in your `docker-compose.yml` file.

### 2. **Check MySQL Access**

If you are facing access issues, you can check MySQL container logs to debug:
```bash
docker logs employeemanager_db
```

### 3. **Ensure Correct Database URL Configuration**

In your `docker-compose.yml`, ensure the `SPRING_DATASOURCE_URL` is configured to point to the correct MySQL container:

```yaml
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/employee_manager}
```

- **`employeemanager_db`** is the name of the MySQL container, and **3306** is the default MySQL port inside the container.

### 4. **Grant Permissions to MySQL User**

If you manually need to grant permissions, follow these steps:
- Enter the MySQL container:
  ```bash
  docker exec -it employeemanager_db mysql -u root -p
  ```
- Run the following commands:
  ```sql
  GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'yourrootpassword';
  FLUSH PRIVILEGES;
  ```

---

## 📊 UML Diagram

Below is the UML diagram that shows the relationships between **Employee**, **Department**, and **Task** entities:

```plaintext
+-----------------+        +------------------+        +----------------+
|   Department   |        |     Employee     |        |     Task       |
+-----------------+        +------------------+        +----------------+
| - id: Long      | 1   *  | - id: Long       | 1   *  | - id: Long     |
| - name: String  |<------>| - name: String   |<------>| - description: |
|                 |        | - role: String   |        |   String       |
|                 |        | - department:    |        | - employee:    |
|                 |        |   Department     |        |   Employee     |
|                 |        | - tasks: List<Task>|        +----------------+
+-----------------+        +------------------+
```

- **Department** has a **one-to-many** relationship with **Employee**.
- **Employee** has a **many-to-one** relationship with **Department**.
- **Employee** has a **one-to-many** relationship with **Task**.
- **Task** has a

 **many-to-one** relationship with **Employee**.

---

## 👨‍💻 Contributing

We welcome contributions! If you have any ideas or improvements, feel free to fork the repository and submit a pull request.

---
