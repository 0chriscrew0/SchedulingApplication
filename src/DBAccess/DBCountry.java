package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBCountry {

    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(countryID, countryName);
                countryList.add(c);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryList;
    }

    public static Country getCountry(int countryID) throws SQLException {
        String sql = "SELECT * FROM Countries WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int newID = rs.getInt("Country_ID");
        String country = rs.getString("Country");
        Country c = new Country(newID, country);
        return c;
    }

    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "SELECT Create_Date FROM Countries";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
