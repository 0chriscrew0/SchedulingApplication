package controller;

import DBAccess.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportOne implements Initializable {
    public ComboBox<String> typeSelector;
    public ComboBox<Integer> monthSelector;
    public Label resultLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            typeSelector.setItems(DBAppointment.getAppointmentTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int i = 1;
        while(i <= 12) {
            monthSelector.getItems().add(i);
            i++;
        }

        typeSelector.getSelectionModel().selectFirst();
        monthSelector.getSelectionModel().selectFirst();

        typeSelector.valueProperty().addListener(cd -> {
            try {
                resultLabel.setText(String.valueOf(DBAppointment.getNumberAppointmentsByTypeAndMonth(typeSelector.getValue(), monthSelector.getSelectionModel().getSelectedItem())));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        monthSelector.valueProperty().addListener(cd -> {
            try {
                resultLabel.setText(String.valueOf(DBAppointment.getNumberAppointmentsByTypeAndMonth(typeSelector.getValue(), monthSelector.getSelectionModel().getSelectedItem())));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
