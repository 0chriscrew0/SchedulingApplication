package controller;

import DBAccess.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class implements the logic and functionality of the Report Three form.
 */
public class ReportThree implements Initializable {

    /**
     * The label for the result of the report
     */
    public Label resultLabel;

    /**
     * Selects the division for the report
     */
    public ComboBox<FirstLevelDivision> divisionSelection;

    /**
     * Prepares the form for user input and populates the division selector with the appropriate selections.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        divisionSelection.setItems(DBFirstLevelDivision.getAllFirstLevelDivisions());

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

        divisionSelection.setCellFactory(listView -> new DivisionListCell());
        divisionSelection.setButtonCell(new DivisionListCell());
        divisionSelection.getSelectionModel().selectFirst();

        try {
            resultLabel.setText(String.valueOf(DBCustomer.getNumberCustomersByDivision(divisionSelection.getValue().getDivisionID())));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        divisionSelection.valueProperty().addListener(cd -> {
            try {
                resultLabel.setText(String.valueOf(DBCustomer.getNumberCustomersByDivision(divisionSelection.getValue().getDivisionID())));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

    }

    /**
     * Navigates the user back to the main form.
     * @param actionEvent
     * @throws IOException
     */
    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1660, 800);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }
}
