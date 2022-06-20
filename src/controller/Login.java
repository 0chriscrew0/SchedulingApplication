package controller;

import DBAccess.DBAppointment;
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
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
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

    private static User currentUser = null;

    public static User getCurrentUser() { return currentUser; }

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
                currentUser = U;
                break;
            }
        }
        if(validLogin) {
            ObservableList<Appointment> userAppointments = DBAppointment.getAppointmentsByUser(Login.getCurrentUser().getUserId());

            for(Appointment A : userAppointments) {
                if(A.getStart().toLocalTime().isAfter(LocalTime.now()) && A.getStart().toLocalTime().isBefore(LocalTime.now().plusMinutes(15))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Upcoming Appointments");
                    alert.setHeaderText("You have the following appointment starting within 15 minutes");
                    alert.setContentText("ID: " + A.getID() + "\nStart Date: " + A.getStart().toLocalDate() + "\nStart Time: " + A.getStart().toLocalTime());
                    alert.showAndWait();
                }
            }

            Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1660, 800);
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
