package controller;

import crypt.SHA256Hasher;
import data.ObjectReader;
import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Main;

import java.util.HashMap;

public class LoginController {
    private Main model;
    private HashMap<String, User> users;

    @FXML
    private TextField inputUsername;

    @FXML
    private PasswordField inputPwd;

    @FXML
    void login(ActionEvent event) {
        ObjectReader reader = new ObjectReader();
        SHA256Hasher sha256Hasher = new SHA256Hasher();
        try {
            if (!inputPwd.getText().isBlank() && !inputUsername.getText().isBlank()) {
                users = reader.readUsers("files/users.enc");

                for (User value : users.values()) {
                    System.out.println(value.toString());
                }

                if (users.containsKey(inputUsername.getText())) {
                    if (checkPasswordsMatching(sha256Hasher)) {

                        model.launchApplication();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkPasswordsMatching(SHA256Hasher sha256Hasher) throws Exception {
        boolean matching = false;
        String salt = users.get(inputUsername.getText()).getSalt();

        String userInput = sha256Hasher.toHexString(sha256Hasher.getSHA(inputPwd.getText(), salt));
        String pwd = users.get(inputUsername.getText()).getPwd();

        if (pwd.equals(userInput))
            if (users.get(inputUsername.getText()).isRegistered())
                matching = true;
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "user has no permission to access this file!", ButtonType.APPLY);
                alert.showAndWait();
            }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong password or username", ButtonType.APPLY);
            alert.showAndWait();
        }
        return matching;
    }

    @FXML
    void register(ActionEvent event) {
        model.loadRegister();
    }

    public void setModel(Main model) {
        this.model = model;
    }

}
