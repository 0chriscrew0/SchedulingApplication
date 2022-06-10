package controller;

import DBAccess.DBContact;
import DBAccess.DBCountry;
import DBAccess.DBCustomer;
import DBAccess.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contact;
import model.Country;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CreateAppointment implements Initializable {
    public TextField titleField;
    public TextField locationField;
    public TextArea descriptionField;
    public TextField typeField;
    public DatePicker startDateField;
    public DatePicker endDateField;
    public ComboBox contactSelection;
    public ComboBox customerSelection;
    public ComboBox userSelection;
    public TextField startTimeField;
    public TextField endTimeField;

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

                setDisable(empty || date.compareTo(today) < 0 || startDateField.getValue() == null || date.isBefore(startDateField.getValue()));
            }
        });

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

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1660, 630);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    public void onSchedule(ActionEvent actionEvent) {

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

        String startDate;
        if(startDateField.getValue() != null) {
            startDate = startDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(startDate.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Input Error");
                alert.setHeaderText("Start Date Field Invalid");
                alert.setContentText("Be sure that the start date field is formatted correctly");

                alert.showAndWait();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Start Date Field Invalid");
            alert.setContentText("Start date field should contain a value");

            alert.showAndWait();
            return;
        }

        String endDate;
        if(endDateField.getValue() != null) {
            endDate = endDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(endDate.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Input Error");
                alert.setHeaderText("End Date Field Invalid");
                alert.setContentText("Be sure that the end date field is formatted correctly");

                alert.showAndWait();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("End Date Field Invalid");
            alert.setContentText("End date field should contain a value");

            alert.showAndWait();
            return;
        }

        String startTime = startTimeField.getText();
        if(startTime.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Start Time Field Invalid");
            alert.setContentText("Start time field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        if(!startTime.matches("^\\d{2}:\\d{2}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Start Time Field Invalid");
            alert.setContentText("Make sure time follows xx:xx format");

            alert.showAndWait();
            return;
        }

        String endTime = endTimeField.getText();
        if(endTime.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("End Time Field Invalid");
            alert.setContentText("End time field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        if(!endTime.matches("^\\d{2}:\\d{2}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("End Time Field Invalid");
            alert.setContentText("Make sure time follows xx:xx format");

            alert.showAndWait();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String startString = startDate + " " + startTime;
        LocalDateTime start = LocalDateTime.parse(startString, formatter);

        String endString = endDate + " " + endTime;
        LocalDateTime end = LocalDateTime.parse(endString, formatter);

        System.out.println("Start: " + start);
        System.out.println("End: " + end);

        if(start.isAfter(end)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Input Error");
            alert.setHeaderText("Time Fields Invalid");
            alert.setContentText("Make sure end time is after start time");

            alert.showAndWait();
            return;
        }
    }
}
