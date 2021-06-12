package controllers;

import enums.BedsEnum;
import enums.ErrorEnum;
import enums.RoleEnum;
import enums.RoomStatusEnum;
import helpers.SaveInfoHelper;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import main.Main;
import models.*;
import requests.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ViewController implements Initializable {

    private static ViewParams params = new ViewParams();
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

    public Button btnMenuDashboardHome;
    public Button btnMenuDashboardBookings;
    public Button btnMenuDashboardUsers;
    public Button btnMenuAdminPanel;
    public TableView<Booking> dashboardTableView;
    public TableView<User> dashboardUsersTableView;
    public TableColumn dashboardTableColumnRoomNum;
    public TableColumn dashboardTableColumnCheckin;
    public TableColumn dashboardTableColumnCheckout;
    public TableColumn dashboardTableColumnStatus;
    public TableColumn dashboardTableColumnDetails;
    public TableColumn dashboardTableColumnRole;
    public ComboBox<RoleEnum> dashboardUsersCombo;
    public Button dashboardUsersCreateUser;

    public Button userDetailsBtnBack;
    public TextField userDetailsTxtName;
    public TextField userDetailsTxtDNI;
    public TextField userDetailsTxtCountry;
    public TextField userDetailsTxtAddress;
    public PasswordField userDetailsTxtPassword;
    public Button userDetailsBtnEdit;
    public Button userDetailsBtnBooking;
    public Button userDetailsBtnDelete;
    public ComboBox<RoleEnum> userDetailsComboRole;

    public Button bookingDetailsBtnBack;
    public Button bookingDetailsBtnEdit;
    public Button bookingDetailsBtnCancel;
    public Button bookingDetailsBtnFinish;
    public Button bookingDetailsBtnDelete;
    public DatePicker bookingDetailsDateStart;
    public DatePicker bookingDetailsDateEnd;
    public CheckBox bookingDetailsCheckLateCheckout;
    public ComboBox<Room> bookingDetailsComboRoomNum;
    public ComboBox<BedsEnum> bookingDetailsComboBedTypes;
    public Button bookingDetailsBtnConsumption;
    public TextField bookingDetailsTxtConsumption;

    public Button createBookingBtnBack;
    public Button createBookingBtnCreate;
    public DatePicker createBookingDateStart;
    public DatePicker createBookingDateEnd;
    public CheckBox createBookingCheckLateCheckout;
    public ComboBox<BedsEnum> createBookingComboBedsType;
    public TextField createBookingTxtPrice;
    public ComboBox<Room> createBookingComboRoomNum;
    public TextField createBookingTxtRoomType;
    public TextField createBookingTxtDni;

    public TableView<User> createUserTableView;
    public Button createUserBtnAdd;
    public Button createUserBtnNext;
    public TextField createUserTxtName;
    public TextField createUserTxtDNI;
    public TextField createUserTxtCountry;
    public TextField createUserTxtAddress;
    public PasswordField createUserTxtPassword;
    public ComboBox<RoleEnum> createUserComboRole;

    public Label bookingInfoTxtRoomNum;
    public Label bookingInfoTxtDays;
    public Label bookingInfoBedsType;
    public Label bookingInfoTxtLateCheckout;
    public Label bookingInfoTxtConsumption;
    public Label bookingInfoTxtPrice;

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
            case "DashboardBookings.fxml":
                initializeDashboardBookings();
                break;
            case "DashboardHome.fxml":
                initializeDashboardHome();
                break;
            case "DashboardUsers.fxml":
                initializeDashboardUsers();
                break;
            case "UserDetails.fxml":
                initializeUserDetails();
                break;
            case "BookingDetails.fxml":
                initializeBookingsDetails();
                break;
            case "CreateBooking.fxml":
                initializeCreateBooking();
                break;
            case "CreateUser.fxml":
                initializeCreateUser();
                break;
            case "BookingInfo.fxml":
                initializeBookingInfo();
                break;
        }
    }

    private void initializeBookingInfo()
    {
        UUID bookingId = UUID.fromString((params.getValue("bookingId")));
        ErrorResponse<Booking> errorResponse = Main.getActualHotel().getBooking(bookingId);
        if(errorResponse.getSuccess())
        {
            Booking booking = errorResponse.getBody();
            bookingInfoTxtRoomNum.setText(booking.getRoomId().toString());
            bookingInfoTxtDays.setText(ChronoUnit.DAYS.between(booking.getStartDate(), booking.getFinishDate()) + " días");
            bookingInfoBedsType.setText(booking.getBedTypes().getName());
            bookingInfoTxtLateCheckout.setText(booking.getLateCheckout() == true ? "Si" : "No");
            bookingInfoTxtConsumption.setText(booking.getExtraConsumption().toString());
            bookingInfoTxtPrice.setText(booking.getTotalPrice().toString());
        }
    }

    private void initializeCreateUser()
    {
        createUserTableView.getItems().setAll(Main.getActualHotel().getUsers(new GetUsersRequest()));
        createUserComboRole.setConverter(new StringConverter<RoleEnum>() {
            @Override
            public String toString(RoleEnum roleEnum) {
                return roleEnum.getName();
            }

            @Override
            public RoleEnum fromString(String string) {
                return null;
            }
        });
        createUserComboRole.getItems().addAll(
                RoleEnum.USER,
                RoleEnum.RECEPTIONIST,
                RoleEnum.ADMIN);
    }

    private void initializeCreateBooking()
    {
        String userDNI = params.getValue("userId");
        if(Main.getActualUser().getRole() == RoleEnum.USER || Main.getActualUser().getRole() == RoleEnum.RECEPTIONIST)
            btnMenuAdminPanel.setVisible(false);
        createBookingTxtDni.setText(userDNI);
        createBookingComboBedsType.setConverter(new StringConverter<BedsEnum>() {
            @Override
            public String toString(BedsEnum bedsEnum) {
                return bedsEnum.getName();
            }

            @Override
            public BedsEnum fromString(String string) {
                return null;
            }
        });
        createBookingComboBedsType.getItems().addAll(
                BedsEnum.DOUBLE_BED,
                BedsEnum.TWO_SINGLES,
                BedsEnum.DOUBLE_BED_AND_SINGLES,
                BedsEnum.FOUR_SINGLES);

        createBookingComboRoomNum.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room.getRoomNum().toString();
            }

            @Override
            public Room fromString(String string) {
                return null;
            }
        });
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
        ErrorResponse<Hotel> errorResponse = Main.openFile(fileChooser, Hotel.class);
        if(errorResponse.getSuccess())
            Main.setActualHotel(errorResponse.getBody());
        toLoginScene();
    }

    public void saveHotelBackup()
    {
        Hotel hotel = Main.getActualHotel();
        if (hotel != null) {
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            Main.saveFile(hotel,fileChooser);
            showSuccess("Datos guardados correctamente",1);
        }
        else
            showError("Error guardando el hotel", 1);
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

    private void showSuccess(String text, Integer seconds)
    {
        paneLabelError.setStyle("-fx-background-color: green");
        labelError.setText(text);
        paneLabelError.setVisible(true);
        labelError.setVisible(true);
        timer(() -> {
            labelError.setVisible(false);
            paneLabelError.setVisible(false);
            paneLabelError.setStyle("-fx-background-color: red");
        }, seconds);
    }

    public void checkout()
    {
        UUID bookingId = UUID.fromString((params.getValue("bookingId")));
        ErrorResponse<Booking> errorResponse = Main.getActualHotel().checkout(bookingId);
        if(errorResponse.getSuccess())
        {
            if(errorResponse.getBody().getFinished())
                toBookingInfo();
            else
                showSuccess("La reserva fue cancelada con exito", 1);
        }
        else
            showError(errorResponse.getError().getFancyError(), 1);
    }

    public void toBookingInfo()
    {
        try {
            Main.changeStage("/views/BookingInfo.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
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

    public void toDashboardBookings() {
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
    
    public void toUserDetails()
    {
        try {
            Main.changeStage("/views/UserDetails.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toBookingDetails()
    {
        try {
            Main.changeStage("/views/BookingDetails.fxml");
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

    public void loadTableViewDashboardBookings()
    {
        dashboardTableColumnRoomNum.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Booking, Room>, ObservableValue<String>>) p -> {
            if (p.getValue() != null && p.getValue().getRoomId() != null) {
                ErrorResponse<Room> errorResponse = Main.getActualHotel().getRoom(p.getValue().getRoomId());
                if(errorResponse.getSuccess())
                {
                    return new SimpleStringProperty(errorResponse.getBody().getRoomNum().toString());
                }
                else
                    return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty("");
            }
        });
        dashboardTableColumnStatus.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Booking, Boolean>, ObservableValue<String>>) p -> {
            if (p.getValue() != null) {
                if(p.getValue().getCanceled())
                    return new SimpleStringProperty("Cancelada");
                else if(p.getValue().getFinished())
                    return new SimpleStringProperty("Terminada");
                else
                    return new SimpleStringProperty("Pendiente/En curso");
            } else {
                return new SimpleStringProperty("");
            }
        });

        Callback<TableColumn<Booking, Void>, TableCell<Booking, Void>> cellFactory = new Callback<TableColumn<Booking, Void>, TableCell<Booking, Void>>() {
            @Override
            public TableCell<Booking, Void> call(final TableColumn<Booking, Void> param) {
                final TableCell<Booking, Void> cell = new TableCell<Booking, Void>() {
                    private final Button btn = new Button("Detalles");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Booking data = getTableView().getItems().get(getIndex());
                            params.setItem("bookingId", data.getId().toString());
                            if(data.getFinished())
                                toBookingInfo();
                            else if(data.getCanceled())
                                showError("Esa reserva fue cancelada", 1);
                            else
                                toBookingDetails();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        dashboardTableColumnDetails.setCellFactory(cellFactory);
    }

    public void loadTableViewDashboardUsers()
    {
        dashboardTableColumnRole.setCellValueFactory((Callback<TableColumn.CellDataFeatures<User, RoleEnum>, ObservableValue<String>>) p -> {
            if (p.getValue() != null && p.getValue().getRole() != null) {
                return new SimpleStringProperty(p.getValue().getRole().getName());
            } else {
                return new SimpleStringProperty("");
            }
        });

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                    private final Button btn = new Button("Detalles");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            User data = getTableView().getItems().get(getIndex());
                            params.setItem("userId", data.getId());
                            toUserDetails();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        dashboardTableColumnDetails.setCellFactory(cellFactory);
    }

    public void loadComboDashboardUsers(MouseEvent mouseEvent)
    {
        if(dashboardUsersCombo.getItems().size()  == 0)
        {
            dashboardUsersCombo.setConverter(new StringConverter<RoleEnum>() {
                @Override
                public String toString(RoleEnum roleEnum) {
                    return roleEnum.getName();
                }

                @Override
                public RoleEnum fromString(String string) {
                    return null;
                }
            });
            dashboardUsersCombo.getItems().addAll(
                    RoleEnum.USER,
                    RoleEnum.RECEPTIONIST,
                    RoleEnum.ADMIN);
        }

    }

    public void loadBookingsToTable(List<Booking> bookings)
    {
        dashboardTableView.getItems().addAll(bookings);
    }

    public void loadUsersToTable(List<User> users)
    {
        dashboardUsersTableView.getItems().addAll(users);
    }

    public void loadTableViewsStep5()
    {
        setupStep5TableColumnRoomType.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Room, RoomType>, ObservableValue<String>>) p -> {
            if (p.getValue() != null && p.getValue().getRoomType() != null) {
                return new SimpleStringProperty(p.getValue().getRoomType().getName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        setupStep5TableColumnStatus.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Room, RoomStatusEnum>, ObservableValue<String>>) p -> {
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
                    toDashboardBookings();
                }
            }
        }
    }

    public void logout(MouseEvent mouseEvent)
    {
        Main.setActualUser(null);
        toLoginScene();
    }

    public void toHome(){
        try {
            Main.changeStage("/views/Home.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toCreateBooking(MouseEvent mouseEvent){
        try {
            Main.changeStage("/views/CreateBooking.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toCreateUser(){
        try {
            Main.changeStage("/views/CreateUser.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toCreateRoom(){
        try {
            Main.changeStage("/views/CreateRoom.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void toCreateRoomType(){
        try {
            Main.changeStage("/views/CreateRoomType.fxml");
        } catch (IOException e) {
            showError(ErrorEnum.VIEW_NOT_FOUND.getFancyError(), 1);
        }
    }

    public void deleteUser()
    {
        String userId = params.getValue("userId");
        ErrorResponse<User> errorResponse = Main.getActualHotel().deleteUser(userId);
        if(errorResponse.getSuccess())
            toDashboardUsers(null);
        else
            showError("Error borrando el usuario", 1);
    }

    public void editUser()
    {
        String name = userDetailsTxtName.getText();
        String dni = userDetailsTxtDNI.getText();
        String country = userDetailsTxtCountry.getText();
        String address = userDetailsTxtAddress.getText();
        RoleEnum role = userDetailsComboRole.getValue();
        String password = userDetailsTxtPassword.getText();
        if(checkUserEdit(name, dni, country, address))
        {
            ErrorResponse<User> errorResponse = Main.getActualHotel().editUser(new SetUserRequest(dni, name, country, address, password, role));
            if (errorResponse.getSuccess())
                showSuccess("Usuario editado con exito.", 1);
            else
                showError("Error al editar el usuario", 1);
        }
    }

    public void createHotel() {
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

    public void createAdmin() {
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

    public void createReceptionist() {
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
                setupStep3TxtName.setText("");
                setupStep3TxtDNI.setText("");
                setupStep3TxtCountry.setText("");
                setupStep3TxtAddress.setText("");
                setupStep3TxtPassword.setText("");
            }
            else
                showError(response.getError().getFancyError(), 1);
        }

    }

    public void createUser() {
        String name = createUserTxtName.getText();
        String dni = createUserTxtDNI.getText();
        String country = createUserTxtCountry.getText();
        String address = createUserTxtAddress.getText();
        String password = createUserTxtPassword.getText();
        RoleEnum role = createUserComboRole.getValue();
        if(checkUser(name, dni, country, address, password))
        {
            CreateUserRequest user = new CreateUserRequest(name, dni, country, address, password, role != null ? role : RoleEnum.USER);
            ErrorResponse<User> response = Main.getActualHotel().createUser(user);
            if(response.getSuccess())
            {
                createUserTableView.getItems().add(response.getBody());
                createUserTxtName.setText("");
                createUserTxtDNI.setText("");
                createUserTxtCountry.setText("");
                createUserTxtAddress.setText("");
                createUserTxtPassword.setText("");
                createUserComboRole.setValue(null);
            }
            else
                showError(response.getError().getFancyError(), 1);
        }

    }

    public void createRoom() {

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
                setupStep5TxtNum.setText("");
                setupStep5ComboReason.setText("");
                setupStep5ComboStatus.setValue(null);
                setupStep5ComboType.setValue(null);
            }
            else
                showError(response.getError().getFancyError(), 1);
        }

    }

    public void createRoomType() {
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
                setupStep4TxtName.setText("");
                setupStep4TxtCapacity.setText("");
                setupStep4TxtPrice.setText("");
            }
            else
                showError(response.getError().getFancyError(), 1);
        }
    }

    public void editBooking() {
        LocalDate startDate = bookingDetailsDateStart.getValue();
        LocalDate endDate = bookingDetailsDateEnd.getValue();
        Room room = bookingDetailsComboRoomNum.getValue();
        Integer roomNum = null;
        if(room != null)
            roomNum = room.getRoomNum();
        Boolean lateCheckout = bookingDetailsCheckLateCheckout.isSelected();
        BedsEnum bedsType = bookingDetailsComboBedTypes.getValue();
        UUID bookingId = UUID.fromString((params.getValue("bookingId")));

        if(checkEditBooking(startDate, endDate, lateCheckout, roomNum, bedsType, bookingId))
        {
            SetBookingRequest booking = new SetBookingRequest(startDate, endDate, lateCheckout, roomNum, bedsType, bookingId);
            ErrorResponse<Booking> response = Main.getActualHotel().setBooking(booking);
            if(response.getSuccess())
            {
                showSuccess("Reserva editada con exito", 1);
            }
            else
                showError(response.getError().getFancyError(), 1);
        }
    }

    public void addConsumption()
    {
        UUID bookingId = UUID.fromString((params.getValue("bookingId")));
        String consumption = bookingDetailsTxtConsumption.getText();
        if(isDouble(consumption))
        {
            ErrorResponse errorResponse = Main.getActualHotel().addConsumption(bookingId, Double.parseDouble(consumption));
            if(errorResponse.getSuccess())
            {
                bookingDetailsTxtConsumption.setText("");
                showSuccess("La consumicion se añadio correctamente", 1);
            }
            else
                showError(errorResponse.getError().getFancyError(), 1);
        }
        else
            showError("El precio debe ser un numero", 1);
    }

    public void createBooking() {
        String dni = createBookingTxtDni.getText();
        LocalDate startDate = createBookingDateStart.getValue();
        LocalDate endDate = createBookingDateEnd.getValue();
        Room room = createBookingComboRoomNum.getValue();
        Integer roomNum = null;
        if(room != null)
            roomNum = room.getRoomNum();
        Boolean lateCheckout = createBookingCheckLateCheckout.isSelected();
        BedsEnum bedsType = createBookingComboBedsType.getValue();

        if(checkBooking(startDate, endDate, lateCheckout, roomNum, dni, bedsType))
        {
            CreateBookingRequest booking = new CreateBookingRequest(startDate, endDate, lateCheckout, roomNum, dni, bedsType);;
            ErrorResponse<Booking> response = Main.getActualHotel().createBooking(booking);
            if(response.getSuccess())
            {
                toDashboardBookings();
            }
            else
                showError(response.getError().getFancyError(), 1);
        }
    }

    private Boolean checkEditBooking(LocalDate startDate, LocalDate endDate, Boolean lateCheckout, Integer roomNum, BedsEnum bedsType, UUID id)
    {
        Boolean response = true;
        if(startDate == null)
        {
            showError("Fecha de inicio invalida", 1);
            response = false;
        }
        if(endDate == null)
        {
            showError("Fecha de finalizacion invalida", 1);
            response = false;
        }
        if(lateCheckout == null)
        {
            showError("Late checkout invalido", 1);
            response = false;
        }
        if(roomNum == null)
        {
            showError("Numero de habitacion invalido", 1);
            response = false;
        }
        if(bedsType == null)
        {
            showError("Tipo de camas invalido", 1);
            response = false;
        }
        if(id == null)
        {
            showError("Error de id", 1);
            response = false;
        }
        return response;
    }

    private Boolean checkBooking(LocalDate startDate, LocalDate endDate, Boolean lateCheckout, Integer roomNum, String dni, BedsEnum bedsType)
    {
        Boolean response = true;
        if(startDate == null)
        {
            showError("Fecha de inicio invalida", 1);
            response = false;
        }
        if(endDate == null)
        {
            showError("Fecha de finalizacion invalida", 1);
            response = false;
        }
        if(lateCheckout == null)
        {
            showError("Late checkout invalido", 1);
            response = false;
        }
        if(roomNum == null)
        {
            showError("Numero de habitacion invalido", 1);
            response = false;
        }
        if(dni == null || dni == "")
        {
            showError("DNI invalido", 1);
            response = false;
        }
        if(bedsType == null)
        {
            showError("Tipo de camas invalido", 1);
            response = false;
        }
        return response;
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

    private Boolean checkUserEdit(String name, String dni, String country, String address)
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

    public void checkBookingDates()
    {
        LocalDate startDate = createBookingDateStart.getValue();
        LocalDate endDate = createBookingDateEnd.getValue();
        if(startDate != null && endDate != null)
        {
            if(startDate.isAfter(endDate) ||
                    startDate.equals(endDate))
            {
                createBookingDateStart.setValue(null);
                createBookingDateEnd.setValue(null);
                showError("Fechas no validas.", 1);
                createBookingComboRoomNum.getItems().clear();
            }
            else
            {
                createBookingComboRoomNum.getItems().clear();
                List<Room> roomList = Main.getActualHotel().getAvailableRooms(startDate, endDate);
                createBookingComboRoomNum.getItems().addAll(roomList);
            }
        }
        else
            createBookingComboRoomNum.getItems().clear();

    }

    public void selectRoomNum()
    {
        Room room = createBookingComboRoomNum.getValue();
        if(room != null)
        {
            Long days = ChronoUnit.DAYS.between(createBookingDateStart.getValue(), createBookingDateEnd.getValue());
            createBookingTxtRoomType.setText(room.getRoomType().getName());
            createBookingTxtPrice.setText(String.valueOf((room.getRoomType().getPrice() * days)));
        }
    }

    public void toggleReason(ActionEvent actionEvent) {
        if(setupStep5ComboStatus.getValue() == RoomStatusEnum.NOT_AVAILABLE)
            setupStep5ComboReason.setDisable(false);
        else
            setupStep5ComboReason.setDisable(true);
    }

    public void initializeDashboardBookings(){
        if(Main.getActualUser().getRole() == RoleEnum.USER)
        {
            btnMenuDashboardHome.setDisable(true);
            btnMenuDashboardHome.setOpacity(0.75);
            btnMenuDashboardBookings.setVisible(false);
            btnMenuDashboardUsers.setVisible(false);
            btnMenuAdminPanel.setVisible(false);
        }
        else if(Main.getActualUser().getRole() == RoleEnum.RECEPTIONIST)
            btnMenuAdminPanel.setVisible(false);
        loadTableViewDashboardBookings();
        loadBookingsToTable(Main.getActualHotel().getBookings());
    }

    public void initializeDashboardHome(){
        loadTableViewDashboardBookings();
        User actualUser = Main.getActualUser();
        if(actualUser.getRole() == RoleEnum.USER)
            loadBookingsToTable(actualUser.getBookings(new GetBookingRequest()));
        else
            loadBookingsToTable(Main.getActualHotel().getBookings());
        if(Main.getActualUser().getRole() == RoleEnum.RECEPTIONIST)
            btnMenuAdminPanel.setVisible(false);
    }

    public void initializeDashboardUsers() {
        loadTableViewDashboardUsers();
        loadUsersToTable(Main.getActualHotel().getUsers(new GetUsersRequest()));
        if(Main.getActualUser().getRole() == RoleEnum.RECEPTIONIST)
            btnMenuAdminPanel.setVisible(false);
    }

    public void initializeUserDetails() {
        String userId = params.getValue("userId");
        ErrorResponse<User> errorResponse = Main.getActualHotel().getUser(userId);
        if(errorResponse.getSuccess())
        {
            User user = errorResponse.getBody();
            if(Main.getActualUser().getRole() == RoleEnum.RECEPTIONIST)
            {
                if(user.getRole() != RoleEnum.USER)
                {
                    userDetailsBtnDelete.setVisible(false);
                    userDetailsTxtPassword.setVisible(false);
                    userDetailsTxtName.setDisable(true);
                    userDetailsTxtAddress.setDisable((true));
                    userDetailsTxtCountry.setDisable(true);
                }

                userDetailsComboRole.setDisable(true);
                btnMenuAdminPanel.setVisible(false);
            }
            if(userId == Main.getActualUser().getId())
                userDetailsBtnDelete.setVisible(false);
            userDetailsTxtName.setText(user.getName());
            userDetailsTxtDNI.setText(user.getDni());
            userDetailsTxtCountry.setText(user.getCountry());
            userDetailsTxtAddress.setText(user.getAddress());
            userDetailsComboRole.setConverter(new StringConverter<RoleEnum>() {
                @Override
                public String toString(RoleEnum roleEnum) {
                    return roleEnum.getName();
                }

                @Override
                public RoleEnum fromString(String string) {
                    return null;
                }
            });
            userDetailsComboRole.getItems().addAll(
                    RoleEnum.USER,
                    RoleEnum.RECEPTIONIST,
                    RoleEnum.ADMIN);
            userDetailsComboRole.setValue(user.getRole());
        }
    }

    public void loadAvailableBookings()
    {
        LocalDate startDate = bookingDetailsDateStart.getValue();
        LocalDate endDate = bookingDetailsDateEnd.getValue();
        if(startDate != null && endDate != null)
        {
            if(startDate.isAfter(endDate) ||
                    startDate.equals(endDate))
            {
                bookingDetailsDateStart.setValue(null);
                bookingDetailsDateEnd.setValue(null);
                showError("Fechas no validas.", 1);
                bookingDetailsComboRoomNum.getItems().clear();
            }
            else
            {
                bookingDetailsComboRoomNum.getItems().clear();
                List<Room> roomList = Main.getActualHotel().getAvailableRooms(startDate, endDate);
                bookingDetailsComboRoomNum.getItems().addAll(roomList);
            }
        }
        else
            bookingDetailsComboRoomNum.getItems().clear();
    }

    public void initializeBookingsDetails(){
        UUID bookingId = UUID.fromString((params.getValue("bookingId")));
        ErrorResponse<Booking> errorResponse2 = Main.getActualHotel().getBooking(bookingId);
        if(errorResponse2.getSuccess())
        {
            Booking booking = errorResponse2.getBody();
            bookingDetailsDateStart.setValue(booking.getStartDate());
            bookingDetailsDateEnd.setValue(booking.getExpectedFinishDate());
            bookingDetailsCheckLateCheckout.setSelected(booking.getLateCheckout());
            bookingDetailsComboRoomNum.setConverter(new StringConverter<Room>() {
                @Override
                public String toString(Room room) {
                    return room.getRoomNum().toString();
                }

                @Override
                public Room fromString(String string) {
                    return null;
                }
            });
            loadAvailableBookings();
            ErrorResponse<Room> errorResponse = Main.getActualHotel().getRoom(booking.getRoomId());
            if(errorResponse.getSuccess())
                bookingDetailsComboRoomNum.setValue(errorResponse.getBody());
            bookingDetailsComboBedTypes.setConverter(new StringConverter<BedsEnum>() {
                @Override
                public String toString(BedsEnum bedsEnum) {
                    return bedsEnum.getName();
                }

                @Override
                public BedsEnum fromString(String string) {
                    return null;
                }
            });
            bookingDetailsComboBedTypes.getItems().addAll(
                    BedsEnum.DOUBLE_BED,
                    BedsEnum.TWO_SINGLES,
                    BedsEnum.DOUBLE_BED_AND_SINGLES,
                    BedsEnum.FOUR_SINGLES);
            bookingDetailsComboBedTypes.setValue(booking.getBedTypes());
        }
    }

    public void removeHotel(){
        Boolean response = Main.deleteFile();
        if(response)
            toHome();
    }
}
