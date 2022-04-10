package model;

import controller.ApplicationController;
import controller.LoginController;
import controller.MessageController;
import controller.RegisterController;
import crypt.FileEncoder;
import data.ObjectReader;
import data.SavedEntry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class Main extends Application {
    private final String FILE_PATH = "files/data.obj";
    private Stage stage;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;

    private LoginController loginController;
    private RegisterController registerController;
    private ApplicationController applicationController;
    private MessageController messageController;

    private HashMap<String, SavedEntry> entryHashMap = new HashMap<>();

    private ObjectReader reader = new ObjectReader();
    private File file;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        try {
            System.out.println("Stage is starting");
            // Display message
            System.out.println("/****AES Decryption*******/");

            // Placing the PDF path
            String pFileName = FILE_PATH;
            String cFileName = "files/data.enc";

            // Placing the PDF name
            String decFileName = FILE_PATH;

            // Creating cipher key 56 bit key length
            byte[] cipher_key = "12345678901234561234567890123456".getBytes("UTF-8");
            FileEncoder.decryptEcb(cFileName, decFileName, cipher_key);

            // Print and display the file credentials
            System.out.println(
                    "file of encryption: " + pFileName + "\n"
                            + "created encrypted file  : " + cFileName
                            + "\n"
                            + "created decrypted file  : " + decFileName);

            loadLogin();
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            System.out.println("Stage is closing");
            // Display message
            System.out.println("/****AES Encryption*******/");

            // Placing the PDF path
            String pFileName = FILE_PATH;
            String cFileName = "files/data.enc";

            // Placing the PDF name
            String decFileName = FILE_PATH;

            // Creating cipher key 56 bit key length
            byte[] cipher_key = "12345678901234561234567890123456".getBytes("UTF-8");
            FileEncoder.encryptEcb(pFileName, cFileName, cipher_key);

            Files.delete(Path.of(FILE_PATH));
            // Print and display the file credentials
            System.out.println(
                    "file of encryption: " + pFileName + "\n"
                            + "created encrypted file  : " + cFileName
                            + "\n"
                            + "created decrypted file  : " + decFileName);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

    }

    public void loadLogin() {
        try {
            stage.close();
            stage = new Stage();
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/login.fxml"));
            root = loader.load();
            stage.setTitle("Login");
            scene = new Scene(root);
            loginController = loader.getController();
            loginController.setModel(this);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRegister() {

        try {
            stage = new Stage();
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/register.fxml"));
            root = loader.load();
            stage.setTitle("Register");
            scene = new Scene(root);
            registerController = loader.getController();
            registerController.setModel(this);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void launchApplication() {

        try {
            stage.close();
            readDataFromFile();
            stage = new Stage();
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/application.fxml"));
            root = loader.load();
            stage.setTitle("Password Safe");
            scene = new Scene(root);
            applicationController = loader.getController();
            applicationController.setModel(this);
            applicationController.setSavedEntries(entryHashMap);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMessages() {

        try {
            Stage msgStage = new Stage();
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/messages.fxml"));
            root = loader.load();
            msgStage.setTitle("Messages");
            scene = new Scene(root);
            messageController = loader.getController();
            messageController.setModel(this);
            msgStage.setScene(scene);
            msgStage.setAlwaysOnTop(true);
            msgStage.initOwner(stage);
            msgStage.initModality(Modality.WINDOW_MODAL);
            msgStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readDataFromFile() {
        file = new File(FILE_PATH);
        if (file.length() != 0)
            entryHashMap = reader.readEntries(FILE_PATH);
    }


    public static void main(String[] args) {
        launch(args);
    }


}
