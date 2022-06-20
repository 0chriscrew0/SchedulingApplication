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
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Timestamp timeStart = new Timestamp(start.getTime());
                Timestamp timeEnd = new Timestamp(end.getTime());

                Appointment a = new Appointment(ID, title, description, location, type, timeStart.toLocalDateTime(), timeEnd.toLocalDateTime(), customerID, userID, contactID);
                appointmentList.add(a);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    public static ObservableList<Appointment> getAppointmentsByUser(int userID) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE User_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int contactID = rs.getInt("Contact_ID");

                Timestamp timeStart = new Timestamp(start.getTime());
                Timestamp timeEnd = new Timestamp(end.getTime());

                Appointment a = new Appointment(ID, title, description, location, type, timeStart.toLocalDateTime(), timeEnd.toLocalDateTime(), customerID, userID, contactID);
                appointmentList.add(a);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    public static ObservableList<Appointment> getAppointments(int customerID) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Timestamp timeStart = new Timestamp(start.getTime());
                Timestamp timeEnd = new Timestamp(end.getTime());

                Appointment a = new Appointment(ID, title, description, location, type, timeStart.toLocalDateTime(), timeEnd.toLocalDateTime(), customerID, userID, contactID);
                appointmentList.add(a);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    public static ObservableList<Appointment> getAppointments(int customerID, int appointmentID) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE Customer_ID = ? AND Appointment_ID <> ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.setInt(2, appointmentID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Timestamp timeStart = new Timestamp(start.getTime());
                Timestamp timeEnd = new Timestamp(end.getTime());

                Appointment a = new Appointment(ID, title, description, location, type, timeStart.toLocalDateTime(), timeEnd.toLocalDateTime(), customerID, userID, contactID);
                appointmentList.add(a);
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    public static int insert(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO Appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);

        return ps.executeUpdate();
    }

    public static int update(int ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, Contact_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerID);
        ps.setInt(8, contactID);
        ps.setInt(9, userID);
        ps.setInt(10, ID);

        return ps.executeUpdate();
    }

    public static int delete(int ID) throws SQLException {
        String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, ID);
        return ps.executeUpdate();
    }
}
