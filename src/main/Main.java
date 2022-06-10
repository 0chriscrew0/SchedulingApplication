package main;

import DBAccess.DBCountry;
import DBAccess.DBCustomer;
import DBAccess.DBUser;
import Database.DBConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customer;
import model.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        // open JDBC connection
        DBConnection.openConnection();

        launch(args);

        // close JDBC connection
        DBConnection.closeConnection();
    }
}
