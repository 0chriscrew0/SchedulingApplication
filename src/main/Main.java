package main;

import DBAccess.DBCountries;
import Database.DBConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Country;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

        ObservableList<Country> countriesList = DBCountries.getAllCountries();
        for (Country C : countriesList) {
            System.out.println("Country ID: " + C.getId() + " Country Name: " + C.getName());
        }
    }

    public static void main(String[] args) {
        // open JDBC connection
        DBConnection.openConnection();

        launch(args);

        // close JDBC connection
        DBConnection.closeConnection();
    }
}
