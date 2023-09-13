import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/emp";
        String username = "root";
        String password = "mysql";

        Scanner scanner = new Scanner(System.in);

        try {
            // Establish a connection to the MySQL database
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

         
            //Menu For CRUD Operation
            while(true){

                System.out.println("\nSelect one from the following:\n1. Display\n2. Insert\n3. Delete\n4. Update\n5. Exit");
                System.out.println("\nEnter your choice:");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice){
                    case 1: 
                            // Display the table
                            displayEmployeeTable(connection);
                            break;
                    
                    case 2:
                            // Insert a new employee
                            insertEmployee(connection, scanner);
                            break;

                    case 3:
                            // Delete an employee by ID
                            deleteEmployee(connection, scanner);
                            break;

                    case 4:
                             // Update the position to new position where ID=id
                             updateEmployeePosition(connection, scanner);
                             break;

                    case 5:
                            //Exiting the program
                            System.out.println("\nExiting the Program!!");
                            connection.close();
                            return;

                    default:
                            System.out.println("\nInvalid Choice!!");
                            break;


                }

            }

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
    private static void insertEmployee(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nEnter name of the employee: ");
        String name = scanner.nextLine();
        System.out.println("\nEnter the position of the employee: ");
        String position = scanner.nextLine();
        
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
    private static void deleteEmployee(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nEnter the employee id of employee to be deleted: ");
        int id = scanner.nextInt();
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
    private static void updateEmployeePosition(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nEnter the new position of the employee: ");
        String newPosition = scanner.nextLine();
        System.out.println("\nEnter the employee id of employee to be updated: ");
        int id = scanner.nextInt();
        
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
