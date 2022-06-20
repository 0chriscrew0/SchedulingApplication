package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to retrieve and update customer information from the database
 */
public class DBCustomer {

    /**
     * Gets the division ID of a specific customer
     * @param customerID the ID of the customer
     * @return the ID of the division of the given customer
     * @throws SQLException
     */
    public static int getDivisionID(int customerID) throws SQLException {
        String sql = "SELECT Division_ID FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Division_ID");
    }

    /**
     * Gets a specific customer from the DB
     * @param customerID the ID of the customer to retrieve
     * @return the customer of the given ID
     * @throws SQLException
     */
    public static Customer select(int customerID) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String name = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postalCode = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        int divisionID = rs.getInt("Division_ID");
        String division = DBFirstLevelDivision.getDivision(divisionID).getDivision();

        Customer c = new Customer(customerID, name, address, postalCode, phone, divisionID);
        return c;
    }

    /**
     * Gets all of the customer from the DB
     * @return a list of all the customer in the DB
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String division = DBFirstLevelDivision.getDivision(divisionID).getDivision();

                Customer c = new Customer(customerID, name, address, postalCode, phone, divisionID);
                customerList.add(c);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

    /**
     * Inserts a new customer into the DB
     * @param name the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param divisionID the ID of the division of the customer
     * @return a number the represents the number of rows affected in the DB
     * @throws SQLException
     */
    public static int insert(String name, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an exisiting customer in the DB
     * @param customerID the existing ID of the customer
     * @param name the new name of the customer
     * @param address the new address of the customer
     * @param postalCode the new postal code of the customer
     * @param phone the new phone number of the customer
     * @param divisionID the ID of the new division of the customer
     * @return a number the represents the number of rows affected in the DB
     * @throws SQLException
     */
    public static int update(int customerID, String name, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String sql = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Removes a customer from the DB
     * @param customerID the ID of the customer to be removed
     * @return a number the represents the number of rows affected in the DB
     * @throws SQLException
     */
    public static int delete(int customerID) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);

        String sql2 = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps2 = DBConnection.getConnection().prepareStatement(sql2);
        ps2.setInt(1, customerID);
        ps2.executeUpdate();

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Gets the number of customer within a given division
     * @param divisionID the division to count customers from
     * @return the number of customers within the given divisino
     * @throws SQLException
     */
    public static int getNumberCustomersByDivision(int divisionID) throws SQLException {
        String sql = "SELECT COUNT(Division_ID) AS Total FROM Customers WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Total");
    }
}
