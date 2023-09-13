import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/emp";
        String username = "root";
        String password = "mysql";

        try {
            // Establish a connection to the MySQL database
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Display the table before any operations
            displayEmployeeTable(connection);

            // Insert a new employee
            insertEmployee(connection, "John Doe", "Manager");

            // Display the table after inserting
            displayEmployeeTable(connection);

            // Delete an employee by ID
            deleteEmployee(connection, 1);

            // Display the table after deleting
            displayEmployeeTable(connection);

            // Update the position to "asst. manager" where ID=0
            updateEmployeePosition(connection, 0, "asst. manager");

            // Display the table after updating the position
            displayEmployeeTable(connection);

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Display the "employee" table
    private static void displayEmployeeTable(Connection connection) throws SQLException {
        String sqlQuery = "SELECT id, name, position FROM employee";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println("Employee Table:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String position = resultSet.getString("position");

            System.out.println("ID: " + id + ", Name: " + name + ", Position: " + position);
        }

        System.out.println();
        resultSet.close();
        preparedStatement.close();
    }

    // Insert a new employee into the "employee" table
    private static void insertEmployee(Connection connection, String name, String position) throws SQLException {
        String sqlQuery = "INSERT INTO employee (name, position) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, position);
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int id = generatedKeys.getInt(1);
            System.out.println("Inserted employee: ID=" + id + ", Name=" + name + ", Position=" + position);
        }

        preparedStatement.close();
    }

    // Delete an employee from the "employee" table by ID
    private static void deleteEmployee(Connection connection, int id) throws SQLException {
        String sqlQuery = "DELETE FROM employee WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Deleted employee with ID: " + id);
        } else {
            System.out.println("No employee found with ID: " + id);
        }

        preparedStatement.close();
    }

    // Update an employee's position by ID
    private static void updateEmployeePosition(Connection connection, int id, String newPosition) throws SQLException {
        String sqlQuery = "UPDATE employee SET position = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, newPosition);
        preparedStatement.setInt(2, id);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Updated position for employee with ID " + id + " to " + newPosition);
        } else {
            System.out.println("No employee found with ID: " + id);
        }

        preparedStatement.close();
    }
}
