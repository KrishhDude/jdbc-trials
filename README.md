# JDBC

## Description
I'm experimenting with JDBC (Java Database Connectivity) programs to fulfill a requirement. I developed these programs using IntelliJ IDEA as my integrated development environment (IDE). In this repository, I've included the `Main.java` file, even though the Maven project I used for this experiment has many other dependencies.

## Requirements
To run this JDBC program, you'll need the following:

- An Integrated Development Environment (IDE), such as IntelliJ IDEA.
- Java installed on your system.
- A running MySQL server with the following information:
  - JDBC URL: jdbc:mysql://localhost:3306/emp
  - Username: mysql_username
  - Password: mysql_password

**Note:** Ensure that you are using the correct class name for the MySQL JDBC driver. In your code, make sure you are loading the driver class like this:

```java
Class.forName("com.mysql.cj.jdbc.Driver");
