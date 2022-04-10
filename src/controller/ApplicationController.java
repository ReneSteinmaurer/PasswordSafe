package controller;

import data.ObjectWriter;
import data.SavedEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Main;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    private final String FILE_PATH = "files/data.obj";
    private boolean isNew = false;

    private Main model;
    private HashMap<String, SavedEntry> savedEntries = new HashMap<>();

    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private ListProperty<String> listProperty = new SimpleListProperty<>();

    private ObjectWriter writer = new ObjectWriter();

    @FXML
    private CheckBox specialChars;

    @FXML
    private ListView<String> scrollView;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField pwdField;

    @FXML
    private TextField urlField;

    @FXML
    private TextField lengthField;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonGenerate;

    @FXML
    private Button buttonChangeVisibility;

    @FXML
    private Button buttonApply;

    @FXML
    void addEntry(ActionEvent event) {
        clearFields();
        enableFields();
        isNew = true;

        buttonAdd.setDisable(true);
        buttonEdit.setDisable(true);
        buttonDelete.setDisable(true);
        buttonApply.setDisable(false);
        buttonChangeVisibility.setDisable(false);
        buttonGenerate.setDisable(false);
        specialChars.setDisable(false);
        scrollView.setDisable(true);
    }

    @FXML
    void apply(ActionEvent event) {
        String name;
        if (!isNew) name = scrollView.getSelectionModel().getSelectedItem();
        else name = "";

        if (checkInput()) {
            if (isNew) {
                putEntry(nameField.getText());
                isNew = false;
            } else if (savedEntries.containsKey(name)) {
                editEntry();
            }
        }

        buttonAdd.setDisable(false);
        buttonEdit.setDisable(false);
        buttonDelete.setDisable(false);
        buttonApply.setDisable(true);
        buttonChangeVisibility.setDisable(true);
        buttonGenerate.setDisable(true);
        specialChars.setDisable(true);
        scrollView.setDisable(false);
        disableFields();
    }

    private void editEntry() {
        String name = scrollView.getSelectionModel().getSelectedItem();
        if (!name.isEmpty()) {
            savedEntries.remove(name);

            savedEntries.put(nameField.getText(),
                    new SavedEntry(nameField.getText(), usernameField.getText(), pwdField.getText(), urlField.getText()));

            writer.writeEntries(savedEntries, FILE_PATH);

            observableList.remove(name);
            observableList.add(nameField.getText());
            listProperty.set(FXCollections.observableArrayList(observableList));
        }
    }


    private void putEntry(String name) {
        SavedEntry entry = null;
        if (!name.isBlank())
            entry = new SavedEntry(nameField.getText(), usernameField.getText(), pwdField.getText(), urlField.getText());
        if (entry != null) {
            savedEntries.put(name, entry);

            writer.writeEntries(savedEntries, FILE_PATH);
            observableList.add(name);
            listProperty.set(FXCollections.observableArrayList(observableList));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You need to fill out the fields", ButtonType.APPLY);
            alert.showAndWait();
        }


    }

    private void clearFields() {
        nameField.clear();
        usernameField.clear();
        pwdField.clear();
        urlField.clear();
        lengthField.clear();
    }

    private boolean checkInput() {
        return (!nameField.getText().isBlank() && !usernameField.getText().isBlank() &&
                !pwdField.getText().isBlank());
    }

    @FXML
    void changePwdVisibility(ActionEvent event) {

    }

    @FXML
    void copyToClipboard(ActionEvent event) {
        String pwd;
        StringSelection ss;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        if (scrollView.getSelectionModel().getSelectedItem() != null) {
            pwd = savedEntries.get(scrollView.getSelectionModel().getSelectedItem()).getPassword();

            ss = new StringSelection(pwd);
            clipboard.setContents(ss, null);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "something went wrong", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void deleteCurrentEntry(ActionEvent event) {
        if (scrollView.getSelectionModel().getSelectedItem() != null) {
            observableList.remove(scrollView.getSelectionModel().getSelectedItem());
            savedEntries.remove(scrollView.getSelectionModel().getSelectedItem());
            writer.writeEntries(savedEntries, FILE_PATH);
            clearFields();
        }

        listProperty.set(FXCollections.observableArrayList(observableList));
    }

    @FXML
    void editCurrentEntry(ActionEvent event) {
        String name = scrollView.getSelectionModel().getSelectedItem();
        if (savedEntries.containsKey(name))
            pwdField.setText(savedEntries.get(name).getPassword());

        enableFields();
        isNew = false;
        buttonApply.setDisable(false);
        buttonAdd.setDisable(true);
        buttonEdit.setDisable(true);
        buttonDelete.setDisable(true);
        scrollView.setDisable(true);
        buttonGenerate.setDisable(false);
        buttonChangeVisibility.setDisable(false);
        specialChars.setDisable(false);

    }

    @FXML
    void generatePwd(ActionEvent event) {
        int length;
        if (lengthField.getText().matches("[0-9]+") || lengthField.getText().isBlank()) {
            if (lengthField.getText().isBlank()) length = 15;
            else length = Integer.parseInt(lengthField.getText());

            if (specialChars.isSelected()) generateWithSpecialChars(length);
            else generateWithoutSpecialChars(length);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "only numbers in length field!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void generateWithoutSpecialChars(int length) {
        String key = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqerstuvwxyz123456789";
        char[] chars = key.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(chars[r.nextInt(key.length())]);
        }
        pwdField.setText(sb.toString());
    }

    private void generateWithSpecialChars(int length) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char) ((char) r.nextInt(126 - 33) + 33);
            sb.append(c);
        }

        pwdField.setText(sb.toString());
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        SavedEntry entry = savedEntries.get(scrollView.getSelectionModel().getSelectedItem());

        if (entry != null) {
            nameField.setText(entry.getName());
            usernameField.setText(entry.getUsername());
            pwdField.setText("********");
            urlField.setText(entry.getUrl());
            disableFields();
        } else {
            clearFields();
        }
    }

    private void disableFields() {
        nameField.setDisable(true);
        usernameField.setDisable(true);
        pwdField.setDisable(true);
        urlField.setDisable(true);
        lengthField.setDisable(true);
    }

    private void enableFields() {
        nameField.setDisable(false);
        usernameField.setDisable(false);
        pwdField.setDisable(false);
        urlField.setDisable(false);
        lengthField.setDisable(false);
    }


    public void setModel(Main model) {
        this.model = model;
    }

    public void setSavedEntries(HashMap<String, SavedEntry> entries) {
        this.savedEntries = entries;
        loadData();
    }

    private void loadData() {
        for (String s : savedEntries.keySet()) {
            observableList.add(s);
        }
        listProperty.set(FXCollections.observableArrayList(observableList));
    }

    @FXML
    void onMessages(ActionEvent event) throws IOException {
        model.loadMessages();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listProperty.set(FXCollections.observableArrayList(observableList));
        scrollView.itemsProperty().bind(listProperty);

        buttonGenerate.setDisable(true);
        buttonApply.setDisable(true);
        buttonChangeVisibility.setDisable(true);
        disableFields();
    }
}
