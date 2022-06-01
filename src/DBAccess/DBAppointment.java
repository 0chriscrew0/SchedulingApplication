package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

public class DBAppointment {

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Date start = rs.getDate("Start");
                Date end = rs.getDate("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Timestamp timeStart = new Timestamp(start.getTime());
                Timestamp timeEnd = new Timestamp(start.getTime());

                Appointment a = new Appointment(ID, title, description, location, type, timeStart.toLocalDateTime(), timeEnd.toLocalDateTime(), customerID, userID, contactID);
                appointmentList.add(a);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }
}
