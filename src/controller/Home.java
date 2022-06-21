package controller;

import DBAccess.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class implements the functionality and logic for the main form of the scheduling application.
 */
public class Home implements Initializable {

    /**
     * Navigates the user to the Add Customer form
     */
    public Button addCustomerButton;

    /**
     * A table that displays all of the customer information from the DB
     */
    public TableView customerTable;

    /**
     * Table column for the customer ID
     */
    public TableColumn customerIdColumn;

    /**
     * Table column for the customer name
     */
    public TableColumn customerNameColumn;

    /**
     * Table column for the customer address
     */
    public TableColumn customerAddressColumn;

    /**
     * Table column for the customer postal code
     */
    public TableColumn customerPostalCodeColumn;

    /**
     * Table column for the customer phone number
     */
    public TableColumn customerPhoneColumn;

    /**
     * Table column for the customer division
     */
    public TableColumn<Customer, String> customerDivisionColumn;

    /**
     * Navigates the user to the Update Customer form
     */
    public Button updateCustomerButton;

    /**
     * Deletes the customer that is currently selected from the customer table
     */
    public Button deleteCustomerButton;

    /**
     * A table to hold all of the appointment information from the DB
     */
    public TableView appointmentsTable;

    /**
     * Table column for the appointment ID
     */
    public TableColumn appointmentIDColumn;

    /**
     * Table column for the appointment title
     */
    public TableColumn appointmentTitleColumn;

    /**
     * Table column for the appointment description
     */
    public TableColumn appointmentDescriptionColumn;

    /**
     * Table column for the location of the appointment
     */
    public TableColumn appointmentLocationColumn;

    /**
     * Table column for the contact of the appointment
     */
    public TableColumn<Appointment, String> appointmentContactColumn;

    /**
     * Table column for the type of the appointment
     */
    public TableColumn appointmentTypeColumn;

    /**
     * Table column for the start date and time of the appointment
     */
    public TableColumn appointmentStartColumn;

    /**
     * Table column for the end date and time of the appointment
     */
    public TableColumn appointmentEndColumn;

    /**
     * Table column for the ID of the customer for the appointment
     */
    public TableColumn<Appointment, String> appointmentCustomerIDColumn;

    /**
     * Table column for the ID of the user for the appointment
     */
    public TableColumn<Appointment, String> appointmentUserIDColumn;

    /**
     * Navigates the user to the Create Appointment form
     */
    public Button createAppointmentButton;

    /**
     * Navigates the user to the Update Appointment form
     */
    public Button updateAppointmentButton;

    /**
     * Navigates the user to the Update Appointment form
     */
    public Button cancelAppointmentButton;

    /**
     * Static variable that holds the customer currently selected in the table view
     */
    private static Customer selectedCustomer = null;

    /**
     * Static variable that holds the appointment currently selected in the table view
     */
    private static Appointment selectedAppointment = null;

    /**
     * Radio button used to filter appointment table to all appointments
     */
    public RadioButton allApointmentsSelection;

    /**
     * Radio button used to filter appointment table to the current month
     */
    public RadioButton monthAppointmentSelection;

    /**
     * Radio button used to filter appointment table to the current week
     */
    public RadioButton weekAppointmentSelection;

    /**
     * This static method can be used to retrieve the selected customer info within a different form.
     * @return The currently selected customer as a customer object
     */
    public static Customer getSelectedCustomer() { return selectedCustomer; }

    /**
     * This static method can be used to retrieve the selected appointment info within a different form.
     * @return The currently selected appointment as an appointment object
     */
    public static Appointment getSelectedAppointment() { return selectedAppointment; }

    /**
     * This method populates the customer and appointment tables with all of the relevant information from the DB.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        customerTable.setItems(DBCustomer.getAllCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionColumn.setCellValueFactory(cd -> {
            int divisionID = cd.getValue().getDivisionID();
            FirstLevelDivision f = new FirstLevelDivision(1,"",1);
            try {
                f = DBFirstLevelDivision.getDivision(divisionID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return new ReadOnlyStringWrapper(f.getDivision());
        });

        appointmentsTable.setItems(DBAppointment.getAllAppointments());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        appointmentContactColumn.setCellValueFactory(cd -> {
            int contactID = cd.getValue().getContactID();
            Contact c = new Contact(1, "", "");
            try {
                c = DBContact.select(contactID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return new ReadOnlyStringWrapper(c.getName());
        });
    }

    /**
     * Navigates the user to the Add Customer form.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddCustomerButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates the user to the Update Customer form.
     * @param actionEvent
     * @throws IOException
     */
    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 630);
            stage.setTitle("Update Customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Removes the selected user from the DB and updates the customer table.
     * @param actionEvent
     * @throws SQLException
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setHeaderText("You are about to delete this customer");
            alert.setContentText("Are you sure you want to delete this customer?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DBCustomer.delete(selectedCustomer.getID());
                customerTable.setItems(DBCustomer.getAllCustomers());
                appointmentsTable.setItems(DBAppointment.getAllAppointments());
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Customer Deleted");
                alert.setHeaderText("You successfully deleted the customer");
                alert.setContentText("ID: " + selectedCustomer.getID() + "\nType: " + selectedCustomer.getName());
                Optional<ButtonType> confirmationResult = alert.showAndWait();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    /**
     * Navigates the user to the Create Appointment form.
     * @param actionEvent
     * @throws IOException
     */
    public void onCreateAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CreateAppointment.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Create Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates the user to the Update Appointment form.
     * @param actionEvent
     * @throws IOException
     */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 630);
            stage.setTitle("Update Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Removes the currently selected appointment from the DB and updates the appointment table.
     * @param actionEvent
     * @throws SQLException
     */
    public void onCancelAppointment(ActionEvent actionEvent) throws SQLException {
        selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel Appointment");
            alert.setHeaderText("You are about to cancel this appointment");
            alert.setContentText("Are you sure you want to cancel this appointment?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DBAppointment.delete(selectedAppointment.getID());
                appointmentsTable.setItems(DBAppointment.getAllAppointments());
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Canceled");
                alert.setHeaderText("You successfully canceled the appointment");
                alert.setContentText("ID: " + selectedAppointment.getID() + "\nType: " + selectedAppointment.getType());
                Optional<ButtonType> confirmationResult = alert.showAndWait();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    /**
     * Filters the appointment table to show all appointments.
     * @param actionEvent
     */
    public void onAllApointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(DBAppointment.getAllAppointments());
    }

    /**
     * Filters the appointment table to show appointments within the current month.
     * @param actionEvent
     * @throws SQLException
     */
    public void onMonthAppointments(ActionEvent actionEvent) throws SQLException {
        appointmentsTable.setItems(DBAppointment.getAppointmentsWithinMonth());
    }

    /**
     * Filters the appointment table to show appointments within the current week.
     * @param actionEvent
     * @throws SQLException
     */
    public void onWeekAppointments(ActionEvent actionEvent) throws SQLException {
        appointmentsTable.setItems(DBAppointment.getAppointmentsWithinWeek());
    }

    /**
     * Navigates the user to the first report.
     * @param actionEvent
     * @throws IOException
     */
    public void toReportOne(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportOne.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Report One");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates the user to the second report.
     * @param actionEvent
     * @throws IOException
     */
    public void toReportTwo(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportTwo.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Report Two");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates the user to the third report.
     * @param actionEvent
     * @throws IOException
     */
    public void toReportThree(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportThree.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Report Three");
        stage.setScene(scene);
        stage.show();
    }
}
