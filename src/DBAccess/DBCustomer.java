package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomer {

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

                Customer c = new Customer(customerID, name, address, postalCode, phone, divisionID);
                customerList.add(c);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

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

    public static int update(int customerID, String name) throws SQLException {
        String sql = "UPDATE Customers SET Customer_Name = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, customerID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int customerID) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
