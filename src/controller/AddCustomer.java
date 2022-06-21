package controller;

import DBAccess.DBCountry;
import DBAccess.DBCustomer;
import DBAccess.DBFirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class implements the logic and functionality for the Add Customer form.
 */
public class AddCustomer implements Initializable {
    /**
     * Button used to go back to the main form.
     */
    public Button cancelButton;

    /**
     * Submits form info and adds customer
     */
    public Button addButton;

    /**
     * Text field for the name of the customer
     */
    public TextField nameField;

    /**
     * Text field for the address of the customer
     */
    public TextField addressField;

    /**
     * Text field for the postal code of the customer
     */
    public TextField postalCodeField;

    /**
     * Text field for the phone number of the customer
     */
    public TextField phoneField;

    /**
     * Country selection for the customer
     */
    public ComboBox<Country> countrySelection;

    /**
     * Division selection for the customer
     */
    public ComboBox<FirstLevelDivision> firstLevelDivisionSelection;

    /**
     * Sets up the form for user input by populating the country and division combo boxes with the appropriate selections
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countrySelection.setItems(DBCountry.getAllCountries());

        class CountryListCell extends ListCell<Country> {

            @Override
            public void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                }
                else {
                    setText(null);
                }
            }
        }

        class DivisionListCell extends ListCell<FirstLevelDivision> {

            @Override
            public void updateItem(FirstLevelDivision item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getDivision());
                }
                else {
                    setText(null);
                }
            }
        }

        countrySelection.setCellFactory(listView -> new CountryListCell());
        countrySelection.setButtonCell(new CountryListCell());
        countrySelection.getSelectionModel().selectFirst();
        firstLevelDivisionSelection.setItems(DBFirstLevelDivision.getFirstLevelDivisionsByCountry(countrySelection.getValue().getId()));
        firstLevelDivisionSelection.getSelectionModel().selectFirst();
        firstLevelDivisionSelection.setCellFactory(listView -> new DivisionListCell());
        firstLevelDivisionSelection.setButtonCell(new DivisionListCell());

        countrySelection.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                firstLevelDivisionSelection.setItems(DBFirstLevelDivision.getFirstLevelDivisionsByCountry(newValue.getId()));
                firstLevelDivisionSelection.getSelectionModel().selectFirst();
            }
        });
    }

    /**
     * Navigates the user back to the main form
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1660, 630);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Does appropriate error checking of the form's current info and adds the new customer if valid
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onAddButton(ActionEvent actionEvent) throws SQLException, IOException {

        String name = nameField.getText();
        if(name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Input Error");
            alert.setHeaderText("Name Field Invalid");
            alert.setContentText("Name field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        String address = addressField.getText();
        if(address.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Input Error");
            alert.setHeaderText("Address Field Invalid");
            alert.setContentText("Address field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        String postalCode = postalCodeField.getText();
        if(postalCode.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Input Error");
            alert.setHeaderText("Postal Code Field Invalid");
            alert.setContentText("Postal code field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        String phone = phoneField.getText();
        if(phone.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Input Error");
            alert.setHeaderText("Phone Field Invalid");
            alert.setContentText("Phone field should contain a non-empty string");

            alert.showAndWait();
            return;
        }

        int divisionID = firstLevelDivisionSelection.getValue().getDivisionID();

        int rowsAffected = DBCustomer.insert(name, address, postalCode, phone, divisionID);

        if(rowsAffected != 0) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 630);
            stage.setTitle("Scheduling Application");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Insert Failed");
            errorAlert.setContentText("There was a problem and the customer was not added.\nPlease try again.");
        }
    }
}
