package controller;

import crypt.SHA256Hasher;
import crypt.Salter;
import data.ObjectReader;
import data.ObjectWriter;
import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Main;

import java.io.File;
import java.util.HashMap;

public class RegisterController {
    private Main model;
    private HashMap<String, User> map;
    private File file;
    private ObjectReader csvReader;
    private ObjectWriter objectWriter;
    private SHA256Hasher sha256Hasher;
    private Salter salter;

    @FXML
    private TextField inputUsername;

    @FXML
    private PasswordField inputPwd1;

    @FXML
    private PasswordField inputPwd2;

    @FXML
    void register(ActionEvent event) {
        try {
            objectWriter = new ObjectWriter();
            csvReader = new ObjectReader();
            file = new File("files/users.enc");
            map = new HashMap<>();
            sha256Hasher = new SHA256Hasher();
            salter = new Salter();

            if (checkInput()) {
                fetchData();
                storeData(sha256Hasher);
                model.loadLogin();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "please check if the passwords are matching!", ButtonType.FINISH);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchData() {
        if (file.length() != 0)
            map = csvReader.readUsers("files/users.enc");
    }

    private void storeData(SHA256Hasher sha256Hasher) throws Exception {
        //TODO: Später isRegistered auf false setzen!
        String salt = salter.retrieveCustomSalt();
        User u = new User(inputUsername.getText(),
                sha256Hasher.toHexString(sha256Hasher.getSHA(inputPwd1.getText(), salt)), salt, false);

        map.put(u.getName(), u);
        objectWriter.appendUser(map,"files/users.enc");

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "New user registered! \uD83D\uDE01\t", ButtonType.FINISH);
        alert.showAndWait();
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

}
