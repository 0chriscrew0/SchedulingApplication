package controller;

import DBAccess.DBUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public Label loginLabel;
    public Label locationLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    private ObservableList<User> userList;
    public TextField userNameField;
    public TextField passwordField;
    public Label userLocation;
    public Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userList = DBUser.getAllUsers();

        if(Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());
            loginLabel.setText(rb.getString("Login"));
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginButton.setText(rb.getString("Login"));
            locationLabel.setText(rb.getString("Location"));
        }

        ZoneId z = ZoneId.systemDefault();
        userLocation.setText(z.getId());
    }

    public void login(ActionEvent actionEvent) throws IOException {
        boolean validLogin = false;
        for(User U : userList) {
            if(U.getUsername().equals(userNameField.getText()) && U.getPassword().equals(passwordField.getText())) {
                validLogin = true;
                break;
            }
        }
        if(validLogin) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 630);
            stage.setTitle("Appointment Scheduler");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);

            if(Locale.getDefault().getLanguage().equals("fr")) {
                ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());
                errorAlert.setHeaderText(rb.getString("Login Failed"));
                errorAlert.setContentText(rb.getString("Username and password combination not found.") + "\n" + rb.getString("Please try again."));
            } else {
                errorAlert.setHeaderText("Login Failed");
                errorAlert.setContentText("Username and password combination not found.\nPlease try again.");
            }

            errorAlert.showAndWait();
        }
    }
}
