package controllers;

import enums.RoomStatusEnum;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import main.Main;
import models.*;

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
    public Label setupLabelError;
    public Label setupStep2LabelError;
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
    public TextField setupStep4TxtNum;
    public TextField setupStep4ComboReason;
    public ComboBox<RoomStatusEnum> setupStep4ComboStatus;
    public Button setupStep4BtnEnd;
    public Button setupStep4BtnAdd;
    public Label setupStep4LabelError;
    public TableView<Room> setupStep4TableView;
    private List<Room> setupStep4TableViewData = new ArrayList<>();
    public TableView<User> setupStep3TableView;
    private List<User> setupStep3TableViewData = new ArrayList<>();
    public Label setupStep3LabelError;

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

    public void toSetup(MouseEvent mouseEvent) throws IOException {
        Main.changeStage("/views/SetupStep1.fxml");
    }

    public void toSetupStep2(MouseEvent mouseEvent) throws IOException {
        Main.changeStage("/views/SetupStep2.fxml");
    }

    public void toSetupStep3(MouseEvent mouseEvent) throws IOException {
        Main.changeStage("/views/SetupStep3.fxml");
    }

    public void toSetupStep4(MouseEvent mouseEvent) throws IOException {
        Main.changeStage("/views/SetupStep4.fxml");
    }

    public void toHome(MouseEvent mouseEvent) throws IOException {
        Main.changeStage("/views/Home.fxml");
    }


    public void createHotel(MouseEvent mouseEvent) throws IOException {
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

    public void createAdmin(MouseEvent mouseEvent) throws IOException {
        String name = setupStep2TxtName.getText();
        String dni = setupStep2TxtDNI.getText();
        String country = setupStep2TxtCountry.getText();
        String address = setupStep2TxtAddress.getText();
        String password = setupStep2TxtPassword.getText();
        if(checkUser(name, dni, country, address, password, setupStep2LabelError))
        {
            Employee employee = new Employee(name, dni, country, address, password, true);
            ErrorResponse response = Main.getActualHotel().createUser(employee);
            if(response.getSuccess())
                toSetupStep3(null);
            else
            {
                setupStep2LabelError.setText(response.getError().getFancyError());
                setupStep2LabelError.setVisible(true);
                timer(() -> setupStep2LabelError.setVisible(false), 1);
            }
        }

    }

    public void createReceptionist(MouseEvent mouseEvent) throws IOException {
        String name = setupStep3TxtName.getText();
        String dni = setupStep3TxtDNI.getText();
        String country = setupStep3TxtCountry.getText();
        String address = setupStep3TxtAddress.getText();
        String password = setupStep3TxtPassword.getText();
        if(checkUser(name, dni, country, address, password, setupStep3LabelError))
        {
            Employee employee = new Employee(name, dni, country, address, password, false);
            ErrorResponse response = Main.getActualHotel().createUser(employee);
            if(response.getSuccess())
            {
                setupStep3TableViewData.add(employee);
                setupStep3TableView.setItems(FXCollections.observableArrayList(setupStep3TableViewData));
                setupStep3TableView.setVisible(true);
            }
            else
            {
                setupStep3LabelError.setText(response.getError().getFancyError());
                setupStep3LabelError.setVisible(true);
                timer(() -> setupStep3LabelError.setVisible(false), 1);
            }
        }

    }

    public void createRoom(MouseEvent mouseEvent) throws IOException {
        String num = setupStep4TxtNum.getText();
        String reason = setupStep4ComboReason.getText();
        RoomStatusEnum status = setupStep4ComboStatus.getValue();
        if(checkRoom(num, reason, status))
        {
            Room room = new Room(new Integer(num), status, reason);
            ErrorResponse response = Main.getActualHotel().createRoom(room);
            if(response.getSuccess())
            {
                setupStep4TableViewData.add(room);
                setupStep4TableView.setItems(FXCollections.observableArrayList(setupStep4TableViewData));
                setupStep4TableView.setVisible(true);
            }
            else
            {
                setupStep4LabelError.setText(response.getError().getFancyError());
                setupStep4LabelError.setVisible(true);
                timer(() -> setupStep4LabelError.setVisible(false), 1);
            }
        }

    }

    private Boolean checkHotel(String name, String address)
    {
        Boolean response = true;
        if(name.isEmpty())
        {
            setupLabelError.setText("Debes ingresar un nombre.");
            setupLabelError.setVisible(true);
            timer(() -> setupLabelError.setVisible(false), 1);
            response = false;
        }
        if(address.isEmpty())
        {
            setupLabelError.setText("Debes ingresar una direccion.");
            setupLabelError.setVisible(true);
            timer(() -> setupLabelError.setVisible(false), 1);
            response = false;
        }
        return response;
    }

    private Boolean checkUser(String name, String dni, String country, String address, String password, Label errorLabel)
    {
        Boolean response = true;
        if(name.isEmpty())
        {
            errorLabel.setText("Debes ingresar un nombre.");
            errorLabel.setVisible(true);
            timer(() -> errorLabel.setVisible(false), 1);
            response = false;
        }
        if(dni.isEmpty())
        {
            errorLabel.setText("Debes ingresar un DNI.");
            errorLabel.setVisible(true);
            timer(() -> errorLabel.setVisible(false), 1);
            response = false;
        }
        if(country.isEmpty())
        {
            errorLabel.setText("Debes ingresar un pais.");
            errorLabel.setVisible(true);
            timer(() -> errorLabel.setVisible(false), 1);
            response = false;
        }
        if(address.isEmpty())
        {
            errorLabel.setText("Debes ingresar una direccion.");
            errorLabel.setVisible(true);
            timer(() -> errorLabel.setVisible(false), 1);
            response = false;
        }
        if(password.isEmpty())
        {
            errorLabel.setText("Debes ingresar una contraseña.");
            errorLabel.setVisible(true);
            timer(() -> errorLabel.setVisible(false), 1);
            response = false;
        }
        return response;
    }

    private Boolean checkRoom(String num, String reason, RoomStatusEnum status)
    {
        Boolean response = true;
        if(num.isEmpty())
        {
            setupStep4LabelError.setText("Debes ingresar un numero de habitacion.");
            setupStep4LabelError.setVisible(true);
            timer(() -> setupStep4LabelError.setVisible(false), 1);
            response = false;
        }
        if(status == RoomStatusEnum.NOT_AVAILABLE && reason.isEmpty())
        {
            setupStep4LabelError.setText("Debes ingresar una razon.");
            setupStep4LabelError.setVisible(true);
            timer(() -> setupStep4LabelError.setVisible(false), 1);
            response = false;
        }
        return response;
    }

    public void toggleReason(ActionEvent actionEvent) {
        if(setupStep4ComboStatus.getValue() == RoomStatusEnum.NOT_AVAILABLE)
            setupStep4ComboReason.setDisable(false);
        else
            setupStep4ComboReason.setDisable(true);
    }
}
