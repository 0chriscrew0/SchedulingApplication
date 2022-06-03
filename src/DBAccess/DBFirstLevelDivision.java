package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.*;

public class DBFirstLevelDivision {

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
