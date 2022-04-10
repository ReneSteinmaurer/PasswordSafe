package controller;

import crypt.FileEncoder;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class LoginController {
    private final String FILE_PATH = "files/data.obj";
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

                if (users.containsKey(inputUsername.getText())) {
                    if (checkPasswordsMatching(sha256Hasher)) {
                        decryptData();
                        model.launchApplication();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "wrong username or password!",ButtonType.OK);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "username and password are required!",ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decryptData() {
        try {
            // Display message
            System.out.println("********* Decrypting file...");

            // Placing the PDF path
            String pFileName = FILE_PATH;
            String cFileName = "files/data.enc";

            // Placing the PDF name
            String decFileName = FILE_PATH;

            // Creating cipher key 56 bit key length
            byte[] cipher_key = "10375678801234561234557690120456".getBytes("UTF-8");
            FileEncoder.decryptEcb(cFileName, decFileName, cipher_key);

        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPasswordsMatching(SHA256Hasher sha256Hasher) throws Exception {
        boolean matching = false;
        String salt = users.get(inputUsername.getText()).getSalt();

        String userInput = sha256Hasher.toHexString(sha256Hasher.getSHA(inputPwd.getText(), salt));
        String pwd = users.get(inputUsername.getText()).getPwd();

        if (pwd.equals(userInput)) {
            if (users.get(inputUsername.getText()).isRegistered())
                matching = true;
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "user has no permission to access this file!", ButtonType.APPLY);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "wrong password or username", ButtonType.APPLY);
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
