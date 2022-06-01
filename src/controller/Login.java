package controller;

import DBAccess.DBUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;

import java.net.URL;
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
    }

    public void login(ActionEvent actionEvent) {
        for(User U : userList) {
            if(U.getUsername().equals(userNameField.getText()) && U.getPassword().equals(passwordField.getText())) {
                System.out.println("login successful!");
            } else {
                System.out.println("Login failed...");
            }
        }
    }
}
