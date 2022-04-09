package model;

import controller.ApplicationController;
import controller.LoginController;
import controller.RegisterController;
import crypt.AES;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Main extends Application {
    private Stage stage;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;

    private LoginController loginController;
    private RegisterController registerController;
    private ApplicationController applicationController;

    private AES aes;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        aes = new AES();
        aes.init();
        loadLogin();
    }

    public void loadLogin() throws IOException {
        stage.close();
        stage = new Stage();
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/login.fxml"));
        root = loader.load();
        stage.setTitle("Login");
        scene = new Scene(root);
        loginController = loader.getController();
        loginController.setModel(this);
        loginController.setAES(aes);
        stage.setScene(scene);
        stage.show();
    }

    public void loadRegister() throws IOException {
        stage.close();
        stage = new Stage();
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/register.fxml"));
        root = loader.load();
        stage.setTitle("Register");
        scene = new Scene(root);
        registerController = loader.getController();
        registerController.setModel(this);
        registerController.setAES(aes);
        stage.setScene(scene);
        stage.show();
    }

    public void launchApplication() throws IOException {
        stage.close();
        stage = new Stage();
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/application.fxml"));
        root = loader.load();
        stage.setTitle("Password Safe");
        scene = new Scene(root);
        applicationController = loader.getController();
        applicationController.setModel(this);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
