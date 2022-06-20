package controller;

import DBAccess.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public Button addCustomerButton;
    public TableView customerTable;
    public TableColumn customerIdColumn;
    public TableColumn customerNameColumn;
    public TableColumn customerAddressColumn;
    public TableColumn customerPostalCodeColumn;
    public TableColumn customerPhoneColumn;
    public TableColumn<Customer, String> customerDivisionColumn;
    public Button updateCustomerButton;
    public Button deleteCustomerButton;
    public TableView appointmentsTable;
    public TableColumn appointmentIDColumn;
    public TableColumn appointmentTitleColumn;
    public TableColumn appointmentDescriptionColumn;
    public TableColumn appointmentLocationColumn;
    public TableColumn<Appointment, String> appointmentContactColumn;
    public TableColumn appointmentTypeColumn;
    public TableColumn appointmentStartColumn;
    public TableColumn appointmentEndColumn;
    public TableColumn<Appointment, String> appointmentCustomerIDColumn;
    public TableColumn<Appointment, String> appointmentUserIDColumn;
    public Button createAppointmentButton;
    public Button updateAppointmentButton;
    public Button cancelAppointmentButton;

    private static Customer selectedCustomer = null;
    private static Appointment selectedAppointment = null;

    public static Customer getSelectedCustomer() { return selectedCustomer; }
    public static Appointment getSelectedAppointment() { return selectedAppointment; }

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

    public void onAddCustomerButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }


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

    public void onCreateAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CreateAppointment.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Create Appointment");
        stage.setScene(scene);
        stage.show();
    }

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
}
