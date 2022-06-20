package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class is used to retrieve and update country information from the database
 */
public class DBCountry {

    /**
     * Gets a list of all of the countries in the DB
     * @return a list of all of the countries in the DB
     */
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

    /**
     * Gets a specific country from the DB
     * @param countryID the ID of the country to be returned
     * @return the country of the given ID
     * @throws SQLException
     */
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
}
