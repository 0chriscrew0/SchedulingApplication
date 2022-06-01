package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.*;

public class DBFirstLevelDivision {

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
