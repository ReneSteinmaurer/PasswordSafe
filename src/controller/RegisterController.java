package controller;

import crypt.AES;
import data.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Main;

import java.util.ArrayList;

public class RegisterController {
    private Main model;
    private AES aes;

    @FXML
    private TextField inputUsername;

    @FXML
    private PasswordField inputPwd1;

    @FXML
    private PasswordField inputPwd2;

    @FXML
    void register(ActionEvent event) {
        try {
            Writer csvWriter = new CSVWriter();
            Reader csvReader = new CSVReader();
            ArrayList<User> list = new ArrayList<>();

            if (checkInput()) {
                if (csvReader.readUsers("users.enc") != null)
                    list = csvReader.readUsers("users.enc");

                User u = new User(inputUsername.getText(), aes.encrypt(inputPwd1.getText()), aes.getKey(), aes.getEncryptionCipher());
                list.add(u);
                csvWriter.appendUser(list,"users.enc");

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "New user registered! \uD83D\uDE01\t", ButtonType.FINISH);
                alert.showAndWait();

                model.loadLogin();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "please check if the passwords are matching!", ButtonType.FINISH);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkInput() {
        boolean isOk = false;
        if (!inputPwd1.getText().isBlank() && !inputPwd2.getText().isBlank() && !inputUsername.getText().isBlank()) {
            if (inputPwd1.getText().equals(inputPwd2.getText()))
                isOk = true;
        }
        return isOk;
    }

    public void setModel(Main model) {
        this.model = model;
    }

    public void setAES(AES aes) {
        this.aes = aes;
    }
}
