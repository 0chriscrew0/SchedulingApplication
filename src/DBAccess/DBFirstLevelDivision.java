package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.*;

/**
 * This class is used to retrieve and update division information from the database
 */
public class DBFirstLevelDivision {

    /**
     * Gets a division given the ID
     * @param divisionID the ID of the division to be returned
     * @return the division returned from the given ID
     * @throws SQLException
     */
    public static FirstLevelDivision getDivision(int divisionID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int ID = rs.getInt("Division_ID");
        String division = rs.getString("Division");
        int countryID = rs.getInt("Country_ID");

        FirstLevelDivision f = new FirstLevelDivision(ID, division, countryID);
        return f;
    }

    /**
     * Gets a list of divisions by a given country
     * @param countryID the ID of the country to search divisions by
     * @return a list of divisions of the given country
     */
    public static ObservableList<FirstLevelDivision> getFirstLevelDivisionsByCountry(int countryID) {
        ObservableList<FirstLevelDivision> firstLevelDivisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ID = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                FirstLevelDivision f = new FirstLevelDivision(ID, division, countryID);
                firstLevelDivisionList.add(f);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return firstLevelDivisionList;
    }

    /**
     * Gets all of the divisions from the DB
     * @return a list of all of the divisions from the DB
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivision> firstLevelDivisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");

                FirstLevelDivision f = new FirstLevelDivision(ID, division, countryID);
                firstLevelDivisionList.add(f);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return firstLevelDivisionList;
    }
}
