package controller;

import DBAccess.DBCountry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddCustomer implements Initializable {
    public Button cancelButton;
    public Button addButton;
    public TextField idField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ComboBox<Country> countrySelection;
    public ComboBox firstLevelDivisionSelection;

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

        countrySelection.setCellFactory(listView -> new CountryListCell());
        countrySelection.setButtonCell(new CountryListCell());

        countrySelection.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && oldValue != newValue) {
                System.out.println(newValue.getName());
            }
        });
    }

    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onAddButton(ActionEvent actionEvent) {
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
//        int divisionID = firstLevelDivisionSelection.getSelectionModel();
    }

    public void onCountrySelect(ActionEvent actionEvent) {

    }
}
