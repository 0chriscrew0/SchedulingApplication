package controller;

import DBAccess.DBCustomer;
import DBAccess.DBFirstLevelDivision;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    private static Customer selectedCustomer = null;


    public static Customer getSelectedCustomer() { return selectedCustomer; }

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

    public void onDeleteCustomer(ActionEvent actionEvent) {
    }
}
