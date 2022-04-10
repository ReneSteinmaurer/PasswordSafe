package controller;

import data.ObjectReader;
import data.ObjectWriter;
import data.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Main;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PermissionController implements Initializable {
    private final String FILE_PATH = "files/users.enc";
    private Main main;
    private HashMap<String, User> userHashMap;
    private ObservableList<String> list = FXCollections.observableArrayList();
    private ListProperty<String> listProperty = new SimpleListProperty<>();
    private ObjectReader objectReader;
    private ObjectWriter objectWriter;
    private File file;

    @FXML
    private ListView<String> usersView;

    @FXML
    private TextField permissionField;

    @FXML
    void togglePermission(ActionEvent event) {
        String name;
        if (usersView.getSelectionModel().getSelectedItem() != null) {
            name = usersView.getSelectionModel().getSelectedItem();
            if (userHashMap.containsKey(name)) {
                if (userHashMap.get(name).isRegistered()) {
                    userHashMap.get(name).setRegistered(false);
                    permissionField.setText("denied");
                } else {
                    userHashMap.get(name).setRegistered(true);
                    permissionField.setText("permitted");
                }
            }
        }
        objectWriter.writeUsers(userHashMap, FILE_PATH);
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        User entry = userHashMap.get(usersView.getSelectionModel().getSelectedItem());

        if (entry != null) {
            if (entry.isRegistered())
                permissionField.setText("permitted");

            else if (!(entry.isRegistered()))
                permissionField.setText("denied");


        } else permissionField.clear();


    }

    @FXML
    void deleteUser(ActionEvent event) {
        User entry = null;
        if (!usersView.getSelectionModel().getSelectedItem().isEmpty())
            entry = userHashMap.get(usersView.getSelectionModel().getSelectedItem());

        if (entry != null && userHashMap.containsKey(entry.getName()) && list.contains(entry.getName())) {
            userHashMap.remove(entry.getName());
            list.remove(entry.getName());

            listProperty.set(FXCollections.observableArrayList(list));
            objectWriter.writeUsers(userHashMap, FILE_PATH);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "something went wrong", ButtonType.OK);
            alert.showAndWait();
        }

    }

    private void fetchData() {
        userHashMap = new HashMap<>();
        objectReader = new ObjectReader();
        objectWriter = new ObjectWriter();
        file = new File("files/users.enc");
        if (file.length() != 0)
            userHashMap = objectReader.readUsers(FILE_PATH);

        list.addAll(userHashMap.keySet());
        listProperty.set(FXCollections.observableArrayList(list));
    }

    public void setModel(Main main) {
        this.main = main;
        fetchData();
        permissionField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listProperty.set(FXCollections.observableArrayList(list));
        usersView.itemsProperty().bind(listProperty);
    }
}
