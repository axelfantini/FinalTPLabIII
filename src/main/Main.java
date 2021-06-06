package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Hotel;
import models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main extends Application {
    private static Stage primaryStage;
    private static Hotel actualHotel;
    private static User actualUser;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
        primaryStage.setTitle("Hotel Assistant");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static void changeStage(String stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(stage));
        primaryStage.setScene(new Scene(root, 1280, 720));
    }

    public static void openFile(FileChooser fileChooser)
    {
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            // OPEN FILE HERE!
        }
    }

    public static void saveFile(FileChooser fileChooser)
    {
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            // SAVE FILE HERE!
        }
    }

    public static User getActualUser() {
        return actualUser;
    }

    public static Hotel getActualHotel() {
        return actualHotel;
    }

    public static void setActualHotel(Hotel actualHotel) {
        Main.actualHotel = actualHotel;
    }

    public static void setActualUser(User actualUser) {
        Main.actualUser = actualUser;
    }
}
