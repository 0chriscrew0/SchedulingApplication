package controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportTwo implements Initializable {
    public TableView reportTable;
    public TableColumn idColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerIDColumn;
    public ComboBox<Contact> contactSelection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contactSelection.setItems(DBContact.getAllContacts());
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

        contactSelection.setCellFactory(listView -> new ContactListCell());
        contactSelection.setButtonCell(new ContactListCell());
        contactSelection.getSelectionModel().selectFirst();

        reportTable.setItems(DBAppointment.getAppointmentsByContact(contactSelection.getValue().getID()));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));

        contactSelection.valueProperty().addListener(cd -> {
            reportTable.setItems(DBAppointment.getAppointmentsByContact(contactSelection.getValue().getID()));
        });
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1660, 800);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }
}
