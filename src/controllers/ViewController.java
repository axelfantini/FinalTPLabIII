package controllers;

import enums.ErrorEnum;
import enums.RoleEnum;
import enums.RoomStatusEnum;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import main.Main;
import models.*;
import requests.CreateRoomTypeRequest;
import requests.CreateUserRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewController {
    public Button homeBtnCreate;
    public Button homeBtnLoad;
    public TextField setupTxtName;
    public TextField setupTxtAddress;
    public Slider setupSliderStars;
    public Button setupBtnLoadData;
    public Button setupBtnCreate;
    public Button setupBtnBack;
    public Button setupBtnFileChooser;
    public FileChooser fileChooser = new FileChooser();
    public Label labelError;
    public TextField setupStep2TxtName;
    public TextField setupStep2TxtDNI;
    public TextField setupStep2TxtCountry;
    public TextField setupStep2TxtAddress;
    public PasswordField setupStep2TxtPassword;
    public Button setupStep2BtnNext;
    public TextField setupStep3TxtName;
    public TextField setupStep3TxtDNI;
    public TextField setupStep3TxtCountry;
    public TextField setupStep3TxtAddress;
    public PasswordField setupStep3TxtPassword;
    public Button setupStep3BtnNext;
    public Button setupStep3BtnAdd;
    public TextField setupStep4TxtName;
    public TextField setupStep4TxtCapacity;
    public TextField setupStep4TxtPrice;
    public Button setupStep4BtnNext;
    public Button setupStep4BtnAdd;
    public TableView<RoomType> setupStep4TableView;
    private static List<RoomType> setupStep4TableViewData = new ArrayList<>();
    public TextField setupStep5TxtNum;
    public TextField setupStep5ComboReason;
    public ComboBox<RoomStatusEnum> setupStep5ComboStatus;
    public ComboBox<RoomType> setupStep5ComboType;
    public Button setupStep5BtnEnd;
    public Button setupStep5BtnAdd;
    public TableView<Room> setupStep5TableView;
    private List<Room> setupStep5TableViewData = new ArrayList<>();
    public TableView<User> setupStep3TableView;
    private List<User> setupStep3TableViewData = new ArrayList<>();


    private void timer(Runnable func, int seconds)
    {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(e -> {
            func.run();
        });
        pause.play();
    }

    public void openFile(MouseEvent mouseEvent)
    {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        Main.openFile(fileChooser);
    }

    public void saveFile(MouseEvent mouseEvent)
    {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        Main.saveFile(fileChooser);
    }

    private void showError(String text, Integer seconds)
    {
        labelError.setText(text);
        labelError.setVisible(true);
        timer(() -> labelError.setVisible(false), seconds);
    }

    public void toSetup(MouseEvent mouseEvent){
        try {
            Main.changeStage("/views/SetupStep1.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toSetupStep2(MouseEvent mouseEvent) {
        try {
            Main.changeStage("/views/SetupStep2.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toSetupStep3(MouseEvent mouseEvent) {
        try {
            Main.changeStage("/views/SetupStep3.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toSetupStep4(MouseEvent mouseEvent) {
        try {
            if(setupStep3TableViewData.size() > 0)
                Main.changeStage("/views/SetupStep4.fxml");
            else
                showError("Debes cargar algun recepcionista", 1);
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toSetupStep5(MouseEvent mouseEvent) {
        try {
            if(setupStep4TableViewData.size() > 0)
                Main.changeStage("/views/SetupStep5.fxml");
            else
                showError("Debes cargar algun tipo de habitacion", 1);
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void loadRoomTypes(MouseEvent mouseEvent)
    {
        if(setupStep5ComboType.getItems().size()  == 0)
        {
            setupStep5ComboType.setConverter(new StringConverter<RoomType>() {
                @Override
                public String toString(RoomType roomType) {
                    return roomType.getName();
                }

                @Override
                public RoomType fromString(String string) {
                    return null;
                }
            });
            setupStep5ComboType.getItems().addAll(setupStep4TableViewData);
        }

    }

    public void toHome(MouseEvent mouseEvent){
        try {
            Main.changeStage("/views/Home.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }


    public void createHotel(MouseEvent mouseEvent) {
        String name = setupTxtName.getText();
        String address = setupTxtAddress.getText();
        Integer stars = (int) Math.round(setupSliderStars.getValue());
        if(checkHotel(name, address))
        {
            Hotel hotel = new Hotel(name, address, stars);
            Main.setActualHotel(hotel);
            toSetupStep2(null);
        }
    }

    public void createAdmin(MouseEvent mouseEvent) {
        String name = setupStep2TxtName.getText();
        String dni = setupStep2TxtDNI.getText();
        String country = setupStep2TxtCountry.getText();
        String address = setupStep2TxtAddress.getText();
        String password = setupStep2TxtPassword.getText();
        if(checkUser(name, dni, country, address, password))
        {
            CreateUserRequest user = new CreateUserRequest(name, dni, country, address, password, RoleEnum.ADMIN);
            ErrorResponse<User> response = Main.getActualHotel().createUser(user);
            if(response.getSuccess())
                toSetupStep3(null);
            else
                showError(response.getError().getFancyError(), 1);
        }

    }

    public void createReceptionist(MouseEvent mouseEvent) {
        String name = setupStep3TxtName.getText();
        String dni = setupStep3TxtDNI.getText();
        String country = setupStep3TxtCountry.getText();
        String address = setupStep3TxtAddress.getText();
        String password = setupStep3TxtPassword.getText();
        if(checkUser(name, dni, country, address, password))
        {
            CreateUserRequest user = new CreateUserRequest(name, dni, country, address, password, RoleEnum.RECEPTIONIST);
            ErrorResponse<User> response = Main.getActualHotel().createUser(user);
            if(response.getSuccess())
            {
                setupStep3TableViewData.add(response.getBody());
                setupStep3TableView.setItems(FXCollections.observableArrayList(setupStep3TableViewData));
                setupStep3TableView.setVisible(true);
            }
            else
                showError(response.getError().getFancyError(), 1);
        }

    }

    public void createRoom(MouseEvent mouseEvent) {
        String num = setupStep5TxtNum.getText();
        String reason = setupStep5ComboReason.getText();
        RoomStatusEnum status = setupStep5ComboStatus.getValue();
        RoomType roomType = setupStep5ComboType.getValue();
        if(checkRoom(num, reason, status, roomType))
        {
            Room room = new Room(new Integer(num), status, reason);
            ErrorResponse response = Main.getActualHotel().createRoom(room);
            if(response.getSuccess())
            {
                setupStep5TableViewData.add(room);
                setupStep5TableView.setItems(FXCollections.observableArrayList(setupStep5TableViewData));
                setupStep5TableView.setVisible(true);
            }
            else
                showError(response.getError().getFancyError(), 1);
        }

    }

    public void createRoomType(MouseEvent mouseEvent) {
        String name = setupStep4TxtName.getText();
        String capacity = setupStep4TxtCapacity.getText();
        String price = setupStep4TxtPrice.getText();
        if(checkRoomType(name, capacity, price))
        {
            CreateRoomTypeRequest roomType = new CreateRoomTypeRequest(name, new Integer(capacity), new Double(price));
            ErrorResponse<RoomType> response = Main.getActualHotel().createRoomType(roomType);
            if(response.getSuccess())
            {
                setupStep4TableViewData.add(response.getBody());
                setupStep4TableView.setItems(FXCollections.observableArrayList(setupStep4TableViewData));
                setupStep4TableView.setVisible(true);
            }
            else
                showError(response.getError().getFancyError(), 1);
        }

    }

    private Boolean checkHotel(String name, String address)
    {
        Boolean response = true;
        if(name.isEmpty())
        {
            showError("Debes ingresar un nombre.", 1);
            response = false;
        }
        if(address.isEmpty())
        {
            showError("Debes ingresar un nombre.", 1);
            response = false;
        }
        return response;
    }

    private Boolean checkUser(String name, String dni, String country, String address, String password)
    {
        Boolean response = true;
        if(name.isEmpty())
        {
            showError("Debes ingresar un nombre.", 1);
            response = false;
        }
        if(dni.isEmpty())
        {
            showError("Debes ingresar un DNI.", 1);
            response = false;
        }
        if(country.isEmpty())
        {
            showError("Debes ingresar un pais.", 1);
            response = false;
        }
        if(address.isEmpty())
        {
            showError("Debes ingresar una direccion.", 1);
            response = false;
        }
        if(password.isEmpty())
        {
            showError("Debes ingresar una contraseña.", 1);
            response = false;
        }
        return response;
    }

    private Boolean checkRoom(String num, String reason, RoomStatusEnum status, RoomType roomType)
    {
        Boolean response = true;
        if(num.isEmpty())
        {
            showError("Debes ingresar un numero de habitacion.", 1);
            response = false;
        }
        if(status == RoomStatusEnum.NOT_AVAILABLE && reason.isEmpty())
        {
            showError("Debes ingresar una razon.", 1);
            response = false;
        }
        if(roomType == null || roomType.getId() == null)
        {
            showError("Debes seleccionar un tipo de habitacion.", 1);
            response = false;
        }
        return response;
    }

    private Boolean checkRoomType(String name, String capacity, String price)
    {
        Boolean response = true;
        if(name.isEmpty())
        {
            showError("Debes ingresar un nombre.", 1);
            response = false;
        }
        if(capacity.isEmpty())
        {
            showError("Debes ingresar la capacidad.", 1);
            response = false;
        }
        if(price.isEmpty())
        {
            showError("Debes ingresar un precio.", 1);
            response = false;
        }
        return response;
    }

    public void toggleReason(ActionEvent actionEvent) {
        if(setupStep5ComboStatus.getValue() == RoomStatusEnum.NOT_AVAILABLE)
            setupStep5ComboReason.setDisable(false);
        else
            setupStep5ComboReason.setDisable(true);
    }
}
