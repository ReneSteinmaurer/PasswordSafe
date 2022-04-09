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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Main;

import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
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
        String name = scrollView.getSelectionModel().getSelectedItem();
        if (checkInput()) {
            if (savedEntries.containsKey(name)) {
                SavedEntry entry = new SavedEntry(nameField.getText(),usernameField.getText(),pwdField.getText(),urlField.getText());
                savedEntries.put(nameField.getText(), entry);
                savedEntries.remove(name);

                writer.writeEntries(savedEntries,"files/data.obj");
                observableList.set(scrollView.getSelectionModel().getSelectedIndex(), nameField.getText());
                listProperty.set(FXCollections.observableArrayList(observableList));
            } else saveData();
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

    private void clearFields() {
        nameField.clear();
        usernameField.clear();
        pwdField.clear();
        urlField.clear();
        lengthField.clear();
    }

    private void saveData() {
        SavedEntry savedEntry = new SavedEntry(nameField.getText(), usernameField.getText(), pwdField.getText(), urlField.getText());
        savedEntries.put(nameField.getText(), savedEntry);

        writer.writeEntries(savedEntries,"files/data.obj");

        observableList.add(nameField.getText());
        listProperty.set(FXCollections.observableArrayList(observableList));
    }

    private boolean checkInput() {
        return (!nameField.getText().isBlank() && !usernameField.getText().isBlank() &&
                !pwdField.getText().isBlank());
    }

    @FXML
    void changePwdVisibility(ActionEvent event) {

    }

    @FXML
    void deleteCurrentEntry(ActionEvent event) {
        if (scrollView.getSelectionModel().getSelectedItem() != null) {
            observableList.remove(scrollView.getSelectionModel().getSelectedItem());
            savedEntries.remove(scrollView.getSelectionModel().getSelectedItem());
        }

        writer.writeEntries(savedEntries, "files/data.obj");
        listProperty.set(FXCollections.observableArrayList(observableList));
    }

    @FXML
    void editCurrentEntry(ActionEvent event) {
        enableFields();
        buttonApply.setDisable(false);
        buttonAdd.setDisable(true);
        buttonEdit.setDisable(true);
        buttonDelete.setDisable(true);
    }

    @FXML
    void generatePwd(ActionEvent event) {
        int length;
        if (lengthField.getText().isBlank() || (length = Integer.parseInt(lengthField.getText())) < 1)
            length = 15;

        if (specialChars.isSelected()) generateWithSpecialChars(length);
        else generateWithoutSpecialChars(length);
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
        System.out.println(sb.toString());
    }

    private void generateWithSpecialChars(int length) {
        char[] chars = new char[length];
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char) ((char) r.nextInt(126-33)+33);
            sb.append(c);
        }

        pwdField.setText(sb.toString());

        System.out.println(sb);
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        SavedEntry entry = savedEntries.get(scrollView.getSelectionModel().getSelectedItem());

        nameField.setText(entry.getName());
        usernameField.setText(entry.getUsername());
        pwdField.setText(entry.getPassword());
        urlField.setText(entry.getUrl());
        disableFields();
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
