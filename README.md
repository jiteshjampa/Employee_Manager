Here’s an updated GitHub README based on your latest `docker-compose.yml` and Dockerfile setup, incorporating the environment variables and changes:

---

# 🧑‍💼 Employee Manager System

Welcome to the **Employee Manager System** repository! This is a **Spring Boot** application designed to manage **Employees**, **Departments**, and **Tasks**. It leverages **Docker** for containerization, making it easier to set up and run the application locally or in any environment.

---

## 🚀 Features

- **Employee Management**: Add, update, and delete employees.
- **Department Management**: Create and manage departments.
- **Task Management**: Assign and track tasks for employees.
- **Database Integration**: Uses MySQL to store employee, department, and task data.
- **Dockerized**: Fully containerized using Docker for easy setup and execution.

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
SPRING_DATASOURCE_URL: jdbc:mysql://employeemanager_db:3306/employee_manager
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
- **Task** has a **many-to-one** relationship with **Employee**.

---

## 📝 Entity Classes

### `Department.java`
```java
@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
```

### `Employee.java`
```java
@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
}
```

### `Task.java`
```java
@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
```

---

## 🎯 Contributions

Feel free to fork this repository and contribute by creating issues, submitting pull requests, and suggesting improvements!

---

## 📧 Contact

For any questions or suggestions, reach out to [jitesh@example.com](mailto:jitesh@example.com).

---

### Enjoy using the Employee Manager! 😊

---

This README provides clear steps for setting up the application with Docker, including error troubleshooting and a UML diagram for visual representation of the database entities.
