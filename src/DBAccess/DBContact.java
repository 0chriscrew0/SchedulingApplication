package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to retrieve and update contact information from the database
 */
public class DBContact {

    /**
     * Gets all of the contacts from the DB
     * @return a list of all contacts from the DB
     * @throws SQLException
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {

        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Contacts";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int ID = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact c = new Contact(ID, name, email);
            contacts.add(c);
        }

        return contacts;
    }

    /**
     * Gets a specific contact from the DB
     * @param contactID the ID of the contact to be returned
     * @return the contact in the DB of the given ID
     * @throws SQLException
     */
    public static Contact select(int contactID) throws SQLException {
        String sql = "SELECT * FROM Contacts WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String name = rs.getString("Contact_Name");
        String email = rs.getString("Email");

        Contact c = new Contact(contactID, name, email);
        return c;
    }
}
