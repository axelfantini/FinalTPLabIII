package controllers;

import enums.ErrorEnum;
import enums.RoleEnum;
import enums.RoomStatusEnum;
import helpers.SaveInfoHelper;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import main.Main;
import models.*;
import requests.CreateRoomTypeRequest;
import requests.CreateRoomRequest;
import requests.CreateUserRequest;

import javax.management.relation.Role;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    public FileChooser fileChooser = new FileChooser();
    public Label labelError;
    public Pane paneLabelError;

    public Button homeBtnCreate;
    public Button homeBtnLoad;
    public TextField setupTxtName;
    public TextField setupTxtAddress;
    public Slider setupSliderStars;
    public Button setupBtnCreate;
    public Button setupBtnBack;

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
    public TableView<User> setupStep3TableView;
    private List<User> setupStep3TableViewData = new ArrayList<>();

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
    public TableColumn setupStep5TableColumnRoomType;
    public TableColumn setupStep5TableColumnStatus;
    private List<Room> setupStep5TableViewData = new ArrayList<>();

    public Label loginLabelWelcome;
    public TextField loginTxtDni;
    public Button loginBtn;
    public PasswordField loginTxtPassword;
    public ImageView loginStar1;
    public ImageView loginStar2;
    public ImageView loginStar3;
    public ImageView loginStar4;
    public ImageView loginStar5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switch (location.toString().split("/views/")[1])
        {
            case "SetupStep5.fxml":
                loadTableViewsStep5();
                break;
            case "Login.fxml":
                loadLogin();
                break;
        }
    }

    private Boolean isInt(String string)
    {
        Boolean response;
        try {
            Integer d = Integer.parseInt(string);
            response = true;
        } catch (NumberFormatException e) {
            response = false;
        }
        return response;
    }

    private Boolean isDouble(String string)
    {
        Boolean response;
        try {
            Double d = Double.parseDouble(string);
            response = true;
        } catch (NumberFormatException e) {
            response = false;
        }
        return response;
    }

    private void timer(Runnable func, int seconds)
    {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(e -> {
            func.run();
        });
        pause.play();
    }

    public void loadHotelBackup()
    {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        Main.openFile(fileChooser, Hotel.class);
    }

    public void saveHotelBackup(Hotel hotel)
    {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        Main.saveFile(hotel,fileChooser);
    }

    public void saveHotel()
    {
        Hotel hotel = Main.getActualHotel();
        if(hotel != null)
        {
            JFileChooser fr = new JFileChooser();
            FileSystemView fw = fr.getFileSystemView();
            String path = fw.getDefaultDirectory() + "\\HotelManager\\save.json";
            ErrorResponse<Hotel> errorResponse = SaveInfoHelper.saveFile(hotel, path);
            if(!errorResponse.getSuccess())
                showError(errorResponse.getError().getFancyError(), 1);
        }
        else
            showError("Error guardando el hotel", 1);
    }

    private void showError(String text, Integer seconds)
    {
        labelError.setText(text);
        paneLabelError.setVisible(true);
        labelError.setVisible(true);
        timer(() -> {
            labelError.setVisible(false); paneLabelError.setVisible(false);
        }, seconds);
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

    public void toLogin(MouseEvent mouseEvent) {
        if(setupStep5TableViewData.size() > 0) {
            saveHotel();
            toLoginScene();
        }
        else
            showError("Debes cargar alguna habitacion", 1);
    }

    public void toLoginScene() {
        try {
            Main.changeStage("/views/Login.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toDashboardHome(MouseEvent mouseEvent) {
        try {
            Main.changeStage("/views/DashboardHome.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toDashboardBookings(MouseEvent mouseEvent) {
        try {
            Main.changeStage("/views/DashboardBookings.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toDashboardUsers(MouseEvent mouseEvent) {
        try {
            Main.changeStage("/views/DashboardUsers.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toAdminPanel(MouseEvent mouseEvent) {
        try {
            Main.changeStage("/views/AdminPanel.fxml");
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

    public void loadRoomStatus(MouseEvent mouseEvent)
    {
        if(setupStep5ComboStatus.getItems().size()  == 0)
        {
            setupStep5ComboStatus.setConverter(new StringConverter<RoomStatusEnum>() {
                @Override
                public String toString(RoomStatusEnum roomStatusEnum) {
                    return roomStatusEnum.getName();
                }

                @Override
                public RoomStatusEnum fromString(String string) {
                    return null;
                }
            });
            setupStep5ComboStatus.getItems().addAll(
                    RoomStatusEnum.NOT_AVAILABLE,
                    RoomStatusEnum.OCCUPIED,
                    RoomStatusEnum.UNOCCUPIED);
        }

    }

    public void loadTableViewsStep5()
    {
        setupStep5TableColumnRoomType.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Room, RoomType>, ObservableValue<String>>) p -> {
            // p.getValue() returns the PersonType instance for a particular TableView row
            if (p.getValue() != null && p.getValue().getRoomType() != null) {
                return new SimpleStringProperty(p.getValue().getRoomType().getName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        setupStep5TableColumnStatus.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Room, RoomStatusEnum>, ObservableValue<String>>) p -> {
            // p.getValue() returns the PersonType instance for a particular TableView row
            if (p.getValue() != null && p.getValue().getStatus() != null) {
                return new SimpleStringProperty(p.getValue().getStatus().getName());
            } else {
                return new SimpleStringProperty("");
            }
        });
    }


    public void loadLogin()
    {
        Hotel actualHotel = Main.getActualHotel();
        if(actualHotel != null)
        {
            ImageView[] stars = {loginStar1, loginStar2, loginStar3, loginStar4, loginStar5};
            for(int i = 0; i < 5; i++)
            {
                if(i >= actualHotel.getStars())
                    stars[i].setEffect(new ColorAdjust(0.0, 0.0, -0.3, -1.0));
                stars[i].setVisible(true);
            }
            loginLabelWelcome.setText("Bienvenido a " + actualHotel.getName());
        }
    }

    public void login()
    {
        String dni = loginTxtDni.getText();
        String pass = loginTxtPassword.getText();
        if(checkLogin(dni, pass))
        {
            User user = Main.getActualUser();
            if(user != null)
            {
                if(user.getRole() != RoleEnum.USER)
                {
                    toDashboardHome(null);
                }
                else
                {
                    toDashboardBookings(null);
                }
            }
        }
    }

    public void logout(MouseEvent mouseEvent)
    {
        Main.setActualUser(null);
        toLoginScene();
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
            CreateRoomRequest room = new CreateRoomRequest(new Integer(num), status, reason, roomType);
            ErrorResponse<Room> response = Main.getActualHotel().createRoom(room);
            if(response.getSuccess())
            {
                setupStep5TableViewData.add(response.getBody());
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

    private Boolean checkLogin(String dni, String pass)
    {
        Boolean response = false;
        Hotel hotel = Main.getActualHotel();
        if(hotel != null)
        {
            ErrorResponse<User> errorResponse = hotel.getUser(dni);
            if(errorResponse.getSuccess())
            {
                User user = errorResponse.getBody();
                if(user.checkPassword(pass))
                {
                    Main.setActualUser(user);
                    response = true;
                }
            }
            else
            {
                showError(errorResponse.getError().getFancyError(), 1);
            }
        }
        return response;
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
        if(!isInt(num))
        {
            showError("El numero de habitacion no es valido.", 1);
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
        if(!isInt(capacity))
        {
            showError("La capacidad no es valida.", 1);
            response = false;
        }
        if(price.isEmpty())
        {
            showError("Debes ingresar un precio.", 1);
            response = false;
        }
        if(!isDouble(price))
        {
            showError("El precio no es valido.", 1);
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
