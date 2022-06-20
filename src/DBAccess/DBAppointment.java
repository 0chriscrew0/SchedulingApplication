package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class is used to retrieve and update Appointment information from the database
 */
public class DBAppointment {

    /**
     * Gets all of the appointments in the database
     * @return An observable list of every appointment in the DB
     */
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

    /**
     * Gets all of the appointments of a user
     * @param userID ID of the user to search appointments by
     * @return A list of all of the appointments in the DB for the given user
     */
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

    /**
     * Gets all of the appointments of a contact
     * @param contactID the ID of the contact to search appointments by
     * @return A list of all of the appointments in the DB for the given contact
     */
    public static ObservableList<Appointment> getAppointmentsByContact(int contactID) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE Contact_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, contactID);
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

    /**
     * Gets all of the appointments of a customer
     * @param customerID the ID of the customer to search appointments by
     * @return A list of appointments in the DB for the given customer
     */
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

    /**
     * Gets all of the appointments for a given customer, excluding the appointment with a given ID
     * @param customerID the ID of the customer to search appointments by
     * @param appointmentID the ID of the appointment to exclude from the list
     * @return a list of appointments in the DB for the given customer, excluding the given appointment
     */
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

    /**
     * Gets all of the appointments within the current week
     * @return a list of appointments from the DB within the current week
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsWithinWeek() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE Start BETWEEN DATE_ADD(CURDATE(), INTERVAL - WEEKDAY(CURDATE()) DAY) AND NOW()";
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
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment a = new Appointment(ID, title, description, location, type, start.toLocalDateTime(), end.toLocalDateTime(), ID, userID, contactID);
                appointments.add(a);
            }
        } catch (SQLException throwables)  {
            throwables.printStackTrace();
        }

        return appointments;

    }

    /**
     * Gets all of the appointments within the current month
     * @return a list of appointments from the DB within the current month
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsWithinMonth() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE month(Start) = month(current_date()) and year(Start) = year(current_date())";
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
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment a = new Appointment(ID, title, description, location, type, start.toLocalDateTime(), end.toLocalDateTime(), ID, userID, contactID);
                appointments.add(a);
            }
        } catch (SQLException throwables)  {
            throwables.printStackTrace();
        }

        return appointments;

    }

    /**
     * Inserts a new appointment into the DB
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param type the type of the appointment
     * @param start the start of the appointment
     * @param end the end of the appointment
     * @param customerID the ID of the customer for the appointment
     * @param userID the ID of the user for the appointment
     * @param contactID the ID of the contact for the appointment
     * @return a number that reperesents the number of rows affected in the DB
     * @throws SQLException
     */
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

    /**
     * Updates an existing appointment in the DB
     * @param ID the ID of the existing appointment
     * @param title the new title of the appointment
     * @param description the new description of the appointment
     * @param location the new location of the appointment
     * @param type the new type of the appointment
     * @param start the new start of the appointment
     * @param end the new end of the appointment
     * @param customerID the ID of the new customer for the appointment
     * @param userID the ID of the new user for the appointment
     * @param contactID the ID of the new contact for the appointment
     * @return a number that represents the number of rows affected in the DB
     * @throws SQLException
     */
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

    /**
     * Removes an existing appointment from the DB
     * @param ID the ID of the appointment to be removed
     * @return a number that represents the number of rows affected in the DB
     * @throws SQLException
     */
    public static int delete(int ID) throws SQLException {
        String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, ID);
        return ps.executeUpdate();
    }

    /**
     * Gets a list of all of the types of appointments in the DB
     * @return a distinct list of types of appointments
     * @throws SQLException
     */

    public static ObservableList<String> getAppointmentTypes() throws SQLException {
        String sql =  "SELECT DISTINCT Type From Appointments";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ObservableList<String> types = FXCollections.observableArrayList();
        while(rs.next()) {
            types.add(rs.getString("Type"));
        }
        return types;
    }

    /**
     * Gets the number of appointments of a given type within a given month
     * @param type the type of appointment
     * @param appointmentMonth the month that the appointment is scheduled in
     * @return a list of appointments from the DB with the given type and in the given month
     * @throws SQLException
     */
    public static int getNumberAppointmentsByTypeAndMonth(String type, int appointmentMonth) throws SQLException {
        String sql = "SELECT COUNT(Type) AS Total FROM Appointments WHERE Type = ? AND month(start) = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2, appointmentMonth);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Total");
    }
}
