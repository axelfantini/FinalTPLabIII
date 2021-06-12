package main;

import enums.BedsEnum;
import enums.ErrorEnum;
import helpers.SaveInfoHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Booking;
import models.ErrorResponse;
import models.Hotel;
import models.User;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Main extends Application {
    private static Stage primaryStage;
    private static Hotel actualHotel;
    private static User actualUser;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = loadHotel() ? FXMLLoader.load(getClass().getResource("/views/Login.fxml")) : FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
        primaryStage.setTitle("Hotel Assistant");
        primaryStage.setScene(new Scene(root, 879, 586));
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static void changeStage(String stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(stage));
        primaryStage.setScene(new Scene(root, 879, 586));
    }

    public static <T> ErrorResponse<T> openFile(FileChooser fileChooser, Class<T> tClass)
    {
        ErrorResponse<T> errorResponse = new ErrorResponse<>();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            errorResponse = SaveInfoHelper.readFile(file.getAbsolutePath(), tClass);
        }
        else {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.OPEN_FILE_ERROR);
        }
        return errorResponse;
    }

    public static <T> ErrorResponse<T> saveFile(T item, FileChooser fileChooser)
    {
        ErrorResponse<T> errorResponse = new ErrorResponse<>();
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            errorResponse = SaveInfoHelper.saveFile(item, file.getAbsolutePath());
        }
        else {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.SAVE_FILE_ERROR);
        }
        return errorResponse;
    }

    private static Boolean loadHotel()
    {
        Boolean response = false;
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        String path = fw.getDefaultDirectory() + "\\HotelManager\\save.json";
        ErrorResponse<Hotel> errorResponse = SaveInfoHelper.readFile(path, Hotel.class);
        if(errorResponse.getSuccess())
        {
            Main.setActualHotel(errorResponse.getBody());
            response = true;
        }
        return response;
    }

    public static Boolean deleteFile(){
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        File file = new File(fw.getDefaultDirectory() +"\\HotelManager\\save.json");
        Boolean response = false;
        try
        {
            if(file.exists()){
                response = file.delete();
                System.out.println("archivo borrado");
            }
        }
        catch (Exception exception){
            System.out.println("ando pal culo");
        }
        return response;
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
