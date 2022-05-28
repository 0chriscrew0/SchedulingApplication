package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountries {

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
}
