package controller;

import DBAccess.DBUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;

import java.net.URL;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {
    private ObservableList<User> userList;
    public TextField userNameField;
    public TextField passwordField;
    public Label userLocation;
    public Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userList = DBUser.getAllUsers();

        ZoneId z = ZoneId.systemDefault();
        userLocation.setText(z.getId());
    }

    public void login(ActionEvent actionEvent) {
        boolean validLogin = false;
        for(User U : userList) {
            if(U.getUsername().equals(userNameField.getText()) && U.getPassword().equals(passwordField.getText())) {
                validLogin = true;
                break;
            }
        }
        if(validLogin) {
            System.out.println("Login successful!");
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Login Failed");
            errorAlert.setContentText("Username and password combination not found.\nPlease try again.");
            errorAlert.showAndWait();
        }
    }
}
