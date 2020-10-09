package gui;

import commandline.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static DictionaryCommandline testing = new DictionaryCommandline();

    public static void main(String[] args) {
        testing.insertFromDatabase();
        launch(args);
        System.out.println(testing.dictionaryLookup("dog"));
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
            primaryStage.setTitle("Dictionary");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
