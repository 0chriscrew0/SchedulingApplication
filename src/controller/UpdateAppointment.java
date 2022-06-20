package controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import DBAccess.DBCustomer;
import DBAccess.DBUser;
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
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class UpdateAppointment implements Initializable {

    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextArea descriptionField;
    public TextField typeField;
    public DatePicker startDateField;
    public DatePicker endDateField;
    public ComboBox<Contact> contactSelection;
    public ComboBox<Customer> customerSelection;
    public ComboBox<User> userSelection;
    public ComboBox<LocalTime> startTimeSelection;
    public ComboBox<LocalTime> endTimeSelection;

    private static Appointment oldAppointment = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        oldAppointment = Home.getSelectedAppointment();

        idField.setText(String.valueOf(oldAppointment.getID()));
        titleField.setText(oldAppointment.getTitle());
        descriptionField.setText(oldAppointment.getDescription());
        locationField.setText(oldAppointment.getLocation());
        typeField.setText(oldAppointment.getType());
        startDateField.setValue(oldAppointment.getStart().toLocalDate());
        endDateField.setValue(oldAppointment.getEnd().toLocalDate());

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

        startTimeSelection.getSelectionModel().select(oldAppointment.getStart().toLocalTime());
        endTimeSelection.getSelectionModel().select(oldAppointment.getEnd().toLocalTime());

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

        customerSelection.setCellFactory(listView -> new CustomerListCell());
        customerSelection.setButtonCell(new CustomerListCell());

        userSelection.setCellFactory(listView -> new UserListCell());
        userSelection.setButtonCell(new UserListCell());

        try {
            ObservableList<Contact> contacts = DBContact.getAllContacts();
            Contact oldContact = DBContact.select(oldAppointment.getContactID());
            contactSelection.setItems(contacts);
            for(Contact C : contacts) {
                if(oldContact.getID() == C.getID()) {
                    contactSelection.getSelectionModel().select(C);
                }
            }
            ObservableList<Customer> customers = DBCustomer.getAllCustomers();
            Customer oldCustomer = DBCustomer.select(oldAppointment.getUserID());
            customerSelection.setItems(customers);
            for(Customer C : customers) {
                if(oldCustomer.getID() == C.getID()) {
                    customerSelection.getSelectionModel().select(C);
                }
            }
            ObservableList<User> users = DBUser.getAllUsers();
            User oldUser = DBUser.select(oldAppointment.getUserID());
            userSelection.setItems(users);
            for(User U : users) {
                if(oldUser.getUserId() == U.getUserId()) {
                    userSelection.getSelectionModel().select(U);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1660, 800);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdate(ActionEvent actionEvent) throws SQLException, IOException {
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

        ObservableList<Appointment> customerAppointments = DBAppointment.getAppointments(customerSelection.getValue().getID(), oldAppointment.getID());
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

        int rowsAffected = DBAppointment.update(oldAppointment.getID(), title, description, location, type, start, end, customerID, userID, contactID);

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
