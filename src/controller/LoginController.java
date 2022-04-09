package controller;

import crypt.AES;
import data.CSVReader;
import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginController {
    private Main model;
    private ArrayList<User> users;
    private AES aes;

    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPwd;

    @FXML
    void login(ActionEvent event) {
        CSVReader reader = new CSVReader();
        try {
            if (!inputPwd.getText().isBlank() && !inputUsername.getText().isBlank()) {
                users = reader.readUsers("users.enc");

                for (User user : users) {
                    System.out.println(user.toString());
                    aes.decrypt(user.getPwd(), user.getKey(), user.getEncryptionCipher());
                }


                /*if (users.get(inputUsername.getText()).equals(aes.decrypt(inputPwd.getText()))) {
                    model.launchApplication();
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void register(ActionEvent event) {
        try {
            model.loadRegister();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setModel(Main model) {
        this.model = model;
    }


    public void setAES(AES aes) {
        this.aes = aes;
    }
}
