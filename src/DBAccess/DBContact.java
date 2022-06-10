package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContact {

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
}
