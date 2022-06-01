package main;

import DBAccess.DBCountry;
import DBAccess.DBUser;
import Database.DBConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

//        ObservableList<Country> countriesList = DBCountry.getAllCountries();
//        for (Country C : countriesList) {
//            System.out.println("Country ID: " + C.getId() + " Country Name: " + C.getName());
//        }

//        DBCountry.checkDateConversion();

        ObservableList<User> userList = DBUser.getAllUsers();
        for(User U : userList) {
            System.out.println("User ID: " + U.getUserId() + " Username: " + U.getUsername() + " Password: " + U.getPassword());
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
