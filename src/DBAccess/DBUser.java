package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUser {

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");

                User u = new User(userID, username, password);
                userList.add(u);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return userList;
    }

    public static User select(int userID) throws SQLException {
        String sql = "SELECT * FROM Users WHERE User_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String username = rs.getString("User_Name");
        String password = rs.getString("Password");

        User u = new User(userID, username, password);
        return u;
    }
}
