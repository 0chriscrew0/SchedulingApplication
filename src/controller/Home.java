package controller;

import DBAccess.DBCustomer;
import DBAccess.DBFirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public Button addCustomerButton;
    public TableView customerTable;
    public TableColumn customerIdColumn;
    public TableColumn customerNameColumn;
    public TableColumn customerAddressColumn;
    public TableColumn customerPostalCodeColumn;
    public TableColumn customerPhoneColumn;
    public TableColumn customerDivisionColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        customerTable.setItems(DBCustomer.getAllCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    public void onAddCustomerButton(ActionEvent actionEvent) throws IOException {



        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 630);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }
}
