package com.mycompany.testg2store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 800, 400);
        stage.setScene(scene);
        stage.setTitle("Đăng nhập");
        stage.centerOnScreen();
        stage.initStyle(StageStyle.DECORATED);
        stage.show();

    }

    static void setRoot(String fxml, int width, int height, String title) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle(title);
//        stage.initStyle(StageStyle.DECORATED);
        stage.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
