package controller;

import DBAccess.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This class implements the logic and functionality of the Create Appointment form.
 */
public class CreateAppointment implements Initializable {

    /**
     * Text field for the title of the appointment
     */
    public TextField titleField;

    /**
     * Text field for the location of the appointment
     */
    public TextField locationField;

    /**
     * Text field for the description of the appointment
     */
    public TextArea descriptionField;

    /**
     * Text field for the type of the appointment
     */
    public TextField typeField;

    /**
     * Date picker for the start date of the appointment
     */
    public DatePicker startDateField;

    /**
     * Date picker for the end date of the appointment
     */
    public DatePicker endDateField;

    /**
     * Contact selection for the appointment
     */
    public ComboBox<Contact> contactSelection;

    /**
     * Customer selection for the appointment
     */
    public ComboBox<Customer> customerSelection;

    /**
     * User selection for the appointment
     */
    public ComboBox<User> userSelection;

    /**
     * Start time selection for the appointment
     */
    public ComboBox<LocalTime> startTimeSelection;

    /**
     * End time selection for the appointment
     */
    public ComboBox<LocalTime> endTimeSelection;

    /**
     * This method prepares the form for user input and populates the User, Contact, and Customer combo boxes with the appropriate selections.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startDateField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        endDateField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 || startDateField.getValue() == null || !date.isEqual(startDateField.getValue()));
            }
        });

        LocalDate easternDate = LocalDate.of(2000, 01, 01);
        LocalTime easternOpenTime = LocalTime.of(8, 00);
        ZonedDateTime openEastern = ZonedDateTime.of(easternDate, easternOpenTime, ZoneId.of("America/New_York"));
        ZonedDateTime open = openEastern.withZoneSameInstant(ZoneId.of(TimeZone.getDefault().getID()));
        LocalTime easternCloseTime = LocalTime.of(22, 00);
        ZonedDateTime closeEastern = ZonedDateTime.of(easternDate, easternCloseTime, ZoneId.of("America/New_York"));
        ZonedDateTime close = closeEastern.withZoneSameInstant(ZoneId.of(TimeZone.getDefault().getID()));

        while(open.isBefore(close)) {
            startTimeSelection.getItems().add(open.toLocalTime());
            endTimeSelection.getItems().add(open.toLocalTime());
            open = open.plusMinutes(30);
        }

        startTimeSelection.getSelectionModel().selectFirst();
        endTimeSelection.getSelectionModel().selectFirst();

        startTimeSelection.valueProperty().addListener(((observable, oldValue, newValue) -> {
            ObservableList<LocalTime> localTimes = FXCollections.observableArrayList();
            LocalTime i = newValue;
            while(i.isBefore(close.toLocalTime())) {
                localTimes.add(i);
                i = i.plusMinutes(30);
            }
            endTimeSelection.setItems(localTimes);
            endTimeSelection.getSelectionModel().selectFirst();
        }));

        try {
            contactSelection.setItems(DBContact.getAllContacts());
            customerSelection.setItems(DBCustomer.getAllCustomers());
            userSelection.setItems(DBUser.getAllUsers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        class ContactListCell extends ListCell<Contact> {

            @Override
            public void updateItem(Contact item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                }
                else {
                    setText(null);
                }
            }
        }

        class CustomerListCell extends ListCell<Customer> {

            @Override
            public void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                }
                else {
                    setText(null);
                }
            }
        }

        class UserListCell extends ListCell<User> {

            @Override
            public void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getUsername());
                }
                else {
                    setText(null);
                }
            }
        }

        contactSelection.setCellFactory(listView -> new ContactListCell());
        contactSelection.setButtonCell(new ContactListCell());
        contactSelection.getSelectionModel().selectFirst();

        customerSelection.setCellFactory(listView -> new CustomerListCell());
        customerSelection.setButtonCell(new CustomerListCell());
        customerSelection.getSelectionModel().selectFirst();

        userSelection.setCellFactory(listView -> new UserListCell());
        userSelection.setButtonCell(new UserListCell());
        userSelection.getSelectionModel().selectFirst();
    }

    /**
     * Navigates the user back to the main form.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1660, 630);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Does error checking for the current values in the form and schedules a new appointment if valid.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onSchedule(ActionEvent actionEvent) throws SQLException, IOException {

        String title = titleField.getText();
        if(title.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Title Field Invalid");
            alert.setContentText("Title field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        String description  = descriptionField.getText();
        if(description.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Description Field Invalid");
            alert.setContentText("Description field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        String location = locationField.getText();
        if(location.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Location Field Invalid");
            alert.setContentText("Location field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        String type = typeField.getText();
        if(type.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Type Field Invalid");
            alert.setContentText("Type field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        if(startDateField.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Start date Field Invalid");
            alert.setContentText("Please pick a start date");

            alert.showAndWait();
            return;
        }

        if(endDateField.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("End date Field Invalid");
            alert.setContentText("Please pick a end date");

            alert.showAndWait();
            return;
        }

        LocalDateTime start = LocalDateTime.of(startDateField.getValue(), startTimeSelection.getValue());
        LocalDateTime end = LocalDateTime.of(endDateField.getValue(), endTimeSelection.getValue());

        ObservableList<Appointment> customerAppointments = DBAppointment.getAppointments(customerSelection.getValue().getID());
        boolean overlap = false;
        for(Appointment A : customerAppointments) {
            LocalDateTime oldStart = A.getStart();
            LocalDateTime oldEnd = A.getEnd();

            System.out.println(oldStart);
            System.out.println(oldEnd);

            if((start.isAfter(oldStart) || start.isEqual(oldStart)) && start.isBefore(oldEnd)) {
                overlap = true;
            }

            if(end.isAfter(oldStart) && (end.isBefore(oldEnd) || end.isEqual(oldEnd))) {
                overlap = true;
            }

            if((start.isBefore(oldStart) || start.isEqual(oldStart)) && (end.isAfter(oldEnd) || end.isEqual(oldEnd))) {
                overlap = true;
            }
        }

        if(overlap) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Appointment Overlap");
            alert.setContentText("Please ensure that the selected customer has no other appointments during this time.");

            alert.showAndWait();
            return;
        }

        int customerID = customerSelection.getValue().getID();
        int userID = userSelection.getValue().getUserId();
        int contactID = contactSelection.getValue().getID();

        int rowsAffected = DBAppointment.insert(title, description, location, type, start, end, customerID, userID, contactID);

        if(rowsAffected != 0) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1660, 630);
            stage.setTitle("Scheduling Application");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Insert Failed");
            errorAlert.setContentText("There was a problem and the appointment was not added.\nPlease try again.");
        }
    }
}
