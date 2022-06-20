package controller;

import DBAccess.DBAppointment;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

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
}
