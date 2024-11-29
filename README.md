# 🧑‍💼 Employee Manager System

This application is a **Spring Boot** based system that allows managing **Employees**, their **Departments**, and the **Tasks** assigned to them. It's designed to help companies streamline their employee data management, including tasks and department assignments.

---

## 🚀 Features

- **Employee Management**: Add, update, delete employees and assign them to departments.
- **Department Management**: Create and manage departments.
- **Task Assignment**: Assign tasks to employees and track their progress.
- **Database Integration**: MySQL for storing employee and department data.

---

## 🛠️ Installation Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/employee-manager.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd employee-manager
   ```

3. **Set up MySQL Database**:
   - Make sure you have MySQL installed on your local machine or use Docker for MySQL.
   - Create a database and user for the application.

4. **Configure Database in `application.properties`**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/employee_manager
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

5. **Build and Run the Application**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

## ⚠️ How to Fix "Connection Failed" Error in MySQL

If you're getting a `java.sql.SQLException: Access denied for user 'root'@'localhost'` error, follow these steps:

1. **Check MySQL User Permissions**:
   - Login to MySQL:
     ```bash
     mysql -u root -p
     ```
   - Grant all privileges to the user (replace `yourpassword` with your MySQL password):
     ```sql
     GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY 'yourpassword';
     FLUSH PRIVILEGES;
     ```

2. **Verify the Database Connection**:
   - Make sure MySQL is running and the port is correct. If using Docker, ensure MySQL container is running and accessible.

3. **Check for Database URL Configuration**:
   - Ensure your `application.properties` file has the correct database URL, username, and password.

4. **Restart the Application**:
   - After making the above changes, restart the Spring Boot application to reflect the new database configurations.

---

## 📊 UML Diagram

Below is the UML diagram representing the relationship between **Employee**, **Department**, and **Task** entities:

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

