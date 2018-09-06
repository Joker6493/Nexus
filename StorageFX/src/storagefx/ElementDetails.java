/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author dboro
 */
public class ElementDetails extends Application {
    private SkydiveSystem selectedSystem;
    private Canopy selectedCanopy;
    private Reserve selectedReserve;
    private AAD selectedAAD;
    private Stock selectedStock;
    private Manufacturer selectedManufacturer;
    private String elementType;
    private boolean editStatus;
    private String stageTitle;
    private Scene scene;
    private boolean assemble = false;
    private String updParams;
    private DataRelay dr = new DataRelay();
    private int stockID;
    private int status;
    
    public int getStockID() {
        return stockID;
    }
    public void setStockID(int stockID) {
        this.stockID = stockID;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getUpdParams() {
        return updParams;
    }

    public void setUpdParams(String updParams) {
        this.updParams = updParams;
    }
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter mySQLFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ElementDetails (SkydiveSystem selectedSystem, boolean editStatus){
        this.selectedSystem = selectedSystem;
        this.stageTitle = "Система "+selectedSystem.getSystemCode();
        this.setStatus(getStatus());
        this.setStockID(getStockID());
        this.editStatus = editStatus;
        this.scene = new Scene(containerDetail(selectedSystem));
        this.assemble = true;
    }
    ElementDetails (Canopy selectedCanopy, boolean editStatus){
        this.selectedCanopy = selectedCanopy;
        this.stageTitle = "Основной парашют "+selectedCanopy.getCanopyModel()+"-"+selectedCanopy.getCanopySize();
        this.setStatus(getStatus());
        this.setStockID(getStockID());
        this.editStatus = editStatus;
        this.scene = new Scene(canopyDetail(selectedCanopy));
    }
    ElementDetails (Reserve selectedReserve, boolean editStatus){
        this.selectedReserve = selectedReserve;
        this.stageTitle = "Запасной парашют "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize();
        this.setStatus(getStatus());
        this.setStockID(getStockID());
        this.editStatus = editStatus;
        this.scene = new Scene(reserveDetail(selectedReserve));
    }
    ElementDetails (AAD selectedAAD, boolean editStatus){
        this.selectedAAD = selectedAAD;
        this.stageTitle = "Страхующий прибор "+selectedAAD.getAadModel()+" "+selectedAAD.getAadSN();
        this.setStatus(getStatus());
        this.setStockID(getStockID());
        this.editStatus = editStatus;
        this.scene = new Scene(aadDetail(selectedAAD));
    }
    ElementDetails (Stock selectedStock, boolean editStatus){
        this.selectedStock = selectedStock;
        this.stageTitle = "Склад "+selectedStock.getStockName();
        this.setStatus(getStatus());
        this.setStockID(getStockID());
        this.editStatus = editStatus;
        this.scene = new Scene(stockDetail(selectedStock));
    }
    ElementDetails (Manufacturer selectedManufacturer, boolean editStatus){
        this.selectedManufacturer = selectedManufacturer;
        this.stageTitle = "Производитель "+selectedManufacturer.getManufacturerName();
        this.editStatus = editStatus;this.setStatus(getStatus());
        this.setStockID(getStockID());
        this.setStatus(getStatus());
        this.setStockID(getStockID());
        this.scene = new Scene(manufacturerDetail(selectedManufacturer));
    }
    ElementDetails (String elementType, int stockID){
        this.elementType = elementType;
        this.editStatus = true;
        switch (elementType) {
            case "container":
                this.selectedSystem = new SkydiveSystem(0, "", "", "", LocalDate.now(), 0, "", stockID);
                this.stageTitle = "Добавление нового ранца";
                this.scene = new Scene(containerDetail(selectedSystem));
                this.assemble = true;
                break;
            case "canopy":
                this.selectedCanopy = new Canopy(0, 0, "", 0, "", LocalDate.now(), 0, 0, "", stockID);
                this.stageTitle = "Добавление нового основного парашюта";
                this.scene = new Scene(canopyDetail(selectedCanopy));
                break;
            case "reserve":
                this.selectedReserve = new Reserve(0, 0, "", 0, "", LocalDate.now(), 0, LocalDate.now(), 0, "", stockID);
                this.stageTitle = "Добавление нового запасного парашюта";
                this.scene = new Scene(reserveDetail(selectedReserve));
                break;
            case "aad": 
                this.selectedAAD = new AAD(0, 0, "", "", LocalDate.now(), 0, LocalDate.now(), 0, 0, "", stockID);
                this.stageTitle = "Добавление нового страхующего прибора";
                this.scene = new Scene(aadDetail(selectedAAD));
                break;
            case "stock":
                this.selectedStock = new Stock("");
                this.stageTitle = "Добавление нового склада";
                this.scene = new Scene(stockDetail(selectedStock));
                break;
            case "manufacturer": 
                this.selectedManufacturer = new Manufacturer("", "", "", "");
                this.stageTitle = "Добавление нового производителя";
                this.scene = new Scene(manufacturerDetail(selectedManufacturer));
                break;    
        }
    }
        
    private GridPane containerDetail(SkydiveSystem ss){
        GridPane details = new GridPane();
        
        Label containerGridName = new Label("Ранец и подвесная система");
        TextField sCode = new TextField(ss.getSystemCode());
        sCode.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>6) {
                sCode.setText(oldValue);
            }
        });
        sCode.setEditable(editStatus);
        Label sModelLabel = new Label("Модель: ");
        TextField sModel = new TextField(ss.getSystemModel());
        sModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                sModel.setText(oldValue);
            }
        });
        sModel.setEditable(editStatus);
        Label sSNLabel = new Label("Серийный номер: ");
        TextField sSN = new TextField(ss.getSystemSN());
        sSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                sSN.setText(oldValue);
            }
        });
        sSN.setEditable(editStatus);
        Label sDOMLabel = new Label("Дата производства: ");
        DatePicker sDOM = new DatePicker(ss.getSystemDOM());
        sDOM.setShowWeekNumbers(true);
        sDOM.setEditable(editStatus);
        sDOM.setOnMouseClicked(e -> {
            if(!sDOM.isEditable()){
                sDOM.hide();
            }
        });
        
        Label sManufacturerNameLabel = new Label("Производитель: ");
        ComboBox <Manufacturer> sManufacturerName = new ComboBox<>();
        ObservableList<Manufacturer> manufacturerList = dr.getManufactirerList();
        sManufacturerName.setItems(manufacturerList);
        sManufacturerName.setCellFactory(p -> new ListCell <Manufacturer> () {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getManufacturerName());
                } else {
                    setText(null);
                }
            }
        });
        sManufacturerName.setButtonCell(new ListCell <Manufacturer> () {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getManufacturerName());
                } else {
                    setText(null);
                }
            }
        });
        sManufacturerName.getSelectionModel().select(selectedSystem.getSystemManufacturerID()-1);
        sManufacturerName.setDisable(!editStatus);
        details.add(containerGridName, 0, 0);
        details.add(sCode, 1, 0);
        details.add(sModelLabel, 0, 1);
        details.add(sModel, 1, 1);
        details.add(sSNLabel, 0, 2);
        details.add(sSN, 1, 2);
        details.add(sDOMLabel, 0, 3);
        details.add(sDOM, 1, 3);
        details.add(sManufacturerNameLabel, 0, 4);
        details.add(sManufacturerName, 1, 4);
        details.setPadding(new Insets(5));
        
        Button saveBtn = new Button("Сохранить");
        saveBtn.setDisable(!editStatus);
        Button assembleBtn = new Button ("Собрать систему");
        assembleBtn.setDisable(!assemble);
        Button cancelBtn = new Button("Отменить");
        cancelBtn.setDisable(!editStatus);
        cancelBtn.setOnAction((ActionEvent event) -> {
        //Container
            sCode.setText(selectedSystem.getSystemCode());
            sModel.setText(selectedSystem.getSystemModel());
            sSN.setText(selectedSystem.getSystemSN());
            sDOM.setValue(selectedSystem.getSystemDOM());
            sManufacturerName.getSelectionModel().select(selectedSystem.getSystemManufacturerID()-1);
        });
        saveBtn.setOnAction((ActionEvent event) -> {
        //check fields for changing, creating update query
        boolean emptyErr = true;
            if (sCode.getText().isEmpty()){
                emptyErr = false;
                sCode.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (sModel.getText().isEmpty()){
                emptyErr = false;
                sModel.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (sSN.getText().isEmpty()){
                emptyErr = false;
                sSN.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (sDOM.getValue().toString().isEmpty()){
                emptyErr = false;
                sDOM.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (sManufacturerName.getSelectionModel().isEmpty()){
                emptyErr = false;
                sManufacturerName.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (emptyErr){
            //Container
                ArrayList <String> systemNewParams = new ArrayList<>();
                if (!sCode.getText().equals(selectedSystem.getSystemCode())){
                    systemNewParams.add("system_code = "+"\""+sCode.getText()+"\"");
                }
                if (!sModel.getText().equals(selectedSystem.getSystemModel())){
                    systemNewParams.add("system_model = "+"\""+sModel.getText()+"\"");
                }
                if (!sSN.getText().equals(selectedSystem.getSystemSN())){
                    systemNewParams.add("system_sn = "+"\""+sSN.getText()+"\"");
                }
                if (!sDOM.getValue().equals(selectedSystem.getSystemDOM())){
                    systemNewParams.add("system_dom = "+"\'"+mySQLFormat.format(sDOM.getValue())+"\'");
                }
                if (sManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=selectedSystem.getSystemManufacturerID()){
                    systemNewParams.add("manufacturerid = "+sManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID());
                }
            //Apply changes    
                if (systemNewParams.isEmpty()) {
                    //Ничего не меялось
                    Alert noChange = new Alert(AlertType.INFORMATION);
                    noChange.setTitle("Внимание!");
                    noChange.setHeaderText(null);
                    noChange.setContentText("Изменений в параметрах нет!");
                    noChange.showAndWait();
                }else{
                    updParams = systemNewParams.get(0);
                    int i = systemNewParams.size()-1;
                    while (i>0){
                        updParams = updParams.concat(", ").concat(systemNewParams.get(i--));
                    }
                    Alert confirm = new Alert(AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Сохранить изменения в выбраном элементе?");
                    confirm.setContentText("Ранец " + selectedSystem.getSystemCode());
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Параметры не изменены!");
                        noChange.showAndWait();
                    } else if (option.get() == yes) {
                        dr.editSkydiveSystem(ss, updParams);
                        systemNewParams.clear();
                    } else if (option.get() == no) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    } else {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    }
                }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Одно или несколько обязательных значений не заполнены. \nПроверьте правильность заполнения значений и повторите попытку.");
                emptyWarn.showAndWait();
            }
            
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            if (editStatus == true){
                boolean changed = false;
                if (!sCode.getText().equals(selectedSystem.getSystemCode())){
                    changed = true;
                }
                if (!sModel.getText().equals(selectedSystem.getSystemModel())){
                    changed = true;
                }
                if (!sSN.getText().equals(selectedSystem.getSystemSN())){
                    changed = true;
                }
                if (!sDOM.getValue().equals(selectedSystem.getSystemDOM())){
                    changed = true;
                }
                if (sManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=selectedSystem.getSystemManufacturerID()){
                    changed = true;
                }
                if (changed){
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Закрыть окно?");
                    confirm.setHeaderText("Не сохраненные изменения будут потеряны");
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        } else if (option.get() == yes) {
                            details.getScene().getWindow().hide();
                        } else if (option.get() == no) {
                        } else {
                    }
                }
            }else{
                details.getScene().getWindow().hide();                
            }
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, assembleBtn, cancelBtn);
        details.add(buttonPane, 1, 5);
        
        return details;
    }
    
    private GridPane canopyDetail(Canopy c){
        GridPane details = new GridPane();
        
        Label canopyGridName = new Label("Основной парашют");
        Label cModelLabel = new Label("Модель: ");
        TextField cModel = new TextField(c.getCanopyModel());
        cModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                cModel.setText(oldValue);
            }
        });
        cModel.setEditable(editStatus);
        Label cSizeLabel = new Label ("Размер купола, кв.фут: ");
        TextField cSize = new TextField (Integer.toString(c.getCanopySize()));
        cSize.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                cSize.setText(oldValue);
            }
        });
        cSize.setEditable(editStatus);
        Label cSNLabel = new Label("Серийный номер: ");
        TextField cSN = new TextField(c.getCanopySN());
        cSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>20) {
                cSN.setText(oldValue);
            }
        });
        cSN.setEditable(editStatus);
        Label cDOMLabel = new Label("Дата производства: ");
        DatePicker cDOM = new DatePicker(c.getCanopyDOM());
        cDOM.setShowWeekNumbers(true);
        cDOM.setEditable(editStatus);
        cDOM.setOnMouseClicked(e -> {
            if(!cDOM.isEditable()){
                cDOM.hide();
            }
        });
        
        Label cManufacturerNameLabel = new Label("Производитель: ");
        ComboBox <Manufacturer> cManufacturerName = new ComboBox<>();
        ObservableList<Manufacturer> manufacturerList = dr.getManufactirerList();
        cManufacturerName.setItems(manufacturerList);
        cManufacturerName.setCellFactory(p -> new ListCell <Manufacturer> () {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getManufacturerName());
                } else {
                    setText(null);
                }
            }
        });
        cManufacturerName.setButtonCell(new ListCell <Manufacturer> () {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getManufacturerName());
                } else {
                    setText(null);
                }
            }
        });
        cManufacturerName.getSelectionModel().select(c.getCanopyManufacturerID()-1);
        cManufacturerName.setDisable(!editStatus);
        Label cJumpsLabel = new Label ("Прыжков: ");
        TextField cJumps = new TextField (Integer.toString(c.getCanopyJumps()));
        cJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,4}")) {
                cJumps.setText(oldValue);
            }
        });
        
        cJumps.setEditable(editStatus);
        details.add(canopyGridName, 0, 0);
        details.add(cModelLabel, 0, 1);
        details.add(cModel, 1, 1);
        details.add(cSizeLabel, 0, 2);
        details.add(cSize, 1, 2);
        details.add(cSNLabel, 0, 3);
        details.add(cSN, 1, 3);
        details.add(cDOMLabel, 0, 4);
        details.add(cDOM, 1, 4);
        details.add(cManufacturerNameLabel, 0, 5);
        details.add(cManufacturerName, 1, 5);
        details.add(cJumpsLabel, 0, 6);
        details.add(cJumps, 1, 6);
        details.setPadding(new Insets(5));
        
        Button saveBtn = new Button("Сохранить");
        saveBtn.setDisable(!editStatus);
        Button cancelBtn = new Button("Отменить");
        cancelBtn.setDisable(!editStatus);
        cancelBtn.setOnAction((ActionEvent event) -> {
        //Canopy
            cModel.setText(c.getCanopyModel());
            cSize.setText(Integer.toString(c.getCanopySize()));
            cSN.setText(c.getCanopySN());
            cDOM.setValue(c.getCanopyDOM());
            cJumps.setText(Integer.toString(c.getCanopyJumps()));
            cManufacturerName.getSelectionModel().select(c.getCanopyManufacturerID()-1);
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            
            boolean emptyErr = true;
            if (!cModel.getText().isEmpty()){
                emptyErr = false;
                cModel.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!cSize.getText().isEmpty()){
                emptyErr = false;
                cSize.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!cSN.getText().isEmpty()){
                emptyErr = false;
                cSN.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!cDOM.getValue().toString().isEmpty()){
                emptyErr = false;
                cDOM.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (cManufacturerName.getSelectionModel().isEmpty()){
                emptyErr = false;
                cManufacturerName.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!cJumps.getText().isEmpty()){
                emptyErr = false;
                cJumps.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (emptyErr){
            //Canopy
                ArrayList <String> canopyNewParams = new ArrayList<>();
                if (!cModel.getText().equals(c.getCanopyModel())){
                    canopyNewParams.add("canopy_model = "+"\""+cModel.getText()+"\"");
                }
                if (!cSize.getText().equals(Integer.toString(c.getCanopySize()))){
                    canopyNewParams.add("canopy_size = "+cSize.getText());
                }
                if (!cSN.getText().equals(c.getCanopySN())){
                    canopyNewParams.add("canopy_sn = "+"\""+cSN.getText()+"\"");
                }
                if (!cDOM.getValue().equals(c.getCanopyDOM())){
                    canopyNewParams.add("canopy_dom = "+"\'"+mySQLFormat.format(cDOM.getValue())+"\'");
                }
                if (cManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=c.getCanopyManufacturerID()){
                    canopyNewParams.add("manufacturerid = "+cManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID());
                }
                if (!cJumps.getText().equals(Integer.toString(c.getCanopyJumps()))){
                    canopyNewParams.add("canopy_jumps = "+cJumps.getText());
                }
            //Apply changes    
                if (canopyNewParams.isEmpty()) {
                    //Ничего не меялось
                    Alert noChange = new Alert(AlertType.INFORMATION);
                    noChange.setTitle("Внимание!");
                    noChange.setHeaderText(null);
                    noChange.setContentText("Изменений в параметрах нет!");
                    noChange.showAndWait();
                }else{
                    updParams = canopyNewParams.get(0);
                    int i = canopyNewParams.size()-1;
                    while (i>0){
                        updParams = updParams.concat(", ").concat(canopyNewParams.get(i--));
                    }
                    Alert confirm = new Alert(AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Сохранить изменения в выбраном элементе?");
                    confirm.setContentText("Купол " + c.getCanopyModel()+"-"+c.getCanopySize());
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Параметры не изменены!");
                        noChange.showAndWait();
                    } else if (option.get() == yes) {
                        dr.editCanopy(c, updParams);
                        canopyNewParams.clear();
                    } else if (option.get() == no) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    } else {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    }
                }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Одно или несколько обязательных значений не заполнены. \nПроверьте правильность заполнения значений и повторите попытку.");
                emptyWarn.showAndWait();
            }
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            if (editStatus == true){
                boolean changed = false;
                if (!cModel.getText().equals(c.getCanopyModel())){
                    changed = true;
                }
                if (!cSize.getText().equals(Integer.toString(c.getCanopySize()))){
                    changed = true;
                }
                if (!cSN.getText().equals(c.getCanopySN())){
                    changed = true;
                }
                if (!cDOM.getValue().equals(c.getCanopyDOM())){
                    changed = true;
                }
                if (cManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=c.getCanopyManufacturerID()){
                    changed = true;
                }
                if (!cJumps.getText().equals(Integer.toString(c.getCanopyJumps()))){
                    changed = true;
                }
                if (changed){
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Закрыть окно?");
                    confirm.setHeaderText("Не сохраненные изменения будут потеряны");
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        } else if (option.get() == yes) {
                            details.getScene().getWindow().hide();
                        } else if (option.get() == no) {
                        } else {
                    }
                }
            }else{
                details.getScene().getWindow().hide();                
            }
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, cancelBtn);
        details.add(buttonPane, 1, 7);
      
        return details;
    }
    
    private GridPane reserveDetail(Reserve r){
        GridPane details = new GridPane();
        
        Label reserveGridName = new Label("Запасной парашют");
        Label rModelLabel = new Label("Модель: ");
        TextField rModel = new TextField(r.getReserveModel());
        rModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                rModel.setText(oldValue);
            }
        });
        rModel.setEditable(editStatus);
        Label rSizeLabel = new Label ("Размер купола, кв.фут: ");
        TextField rSize = new TextField (Integer.toString(r.getReserveSize()));
        rSize.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                rSize.setText(oldValue);
            }
        });
        rSize.setEditable(editStatus);
        Label rSNLabel = new Label("Серийный номер: ");
        TextField rSN = new TextField(r.getReserveSN());
        rSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>20) {
                rSN.setText(oldValue);
            }
        });
        rSN.setEditable(editStatus);
        Label rDOMLabel = new Label("Дата производства: ");
        DatePicker rDOM = new DatePicker(r.getReserveDOM());
        rDOM.setShowWeekNumbers(true);
        rDOM.setEditable(editStatus);
        rDOM.setOnMouseClicked(e -> {
            if(!rDOM.isEditable()){
                rDOM.hide();
            }
        });
        
        Label rManufacturerNameLabel = new Label("Производитель: ");
        ComboBox <Manufacturer> rManufacturerName = new ComboBox<>();
        ObservableList<Manufacturer> manufacturerList = dr.getManufactirerList();
        rManufacturerName.setItems(manufacturerList);
        rManufacturerName.setCellFactory(p -> new ListCell <Manufacturer> () {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getManufacturerName());
                } else {
                    setText(null);
                }
            }
        });
        rManufacturerName.setButtonCell(new ListCell <Manufacturer> () {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getManufacturerName());
                } else {
                    setText(null);
                }
            }
        });
        rManufacturerName.getSelectionModel().select(r.getReserveManufacturerID()-1);
        rManufacturerName.setDisable(!editStatus);
        Label rJumpsLabel = new Label ("Применений: ");
        TextField rJumps = new TextField (Integer.toString(r.getReserveJumps()));
        rJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,4}")) {
                rJumps.setText(oldValue);
            }
        });
        rJumps.setEditable(editStatus);
        Label rPackDateLabel = new Label ("Дата укладки: ");
        DatePicker rPackDate = new DatePicker(r.getReservePackDate());
        rPackDate.setShowWeekNumbers(true);
        rPackDate.setEditable(editStatus);
        rPackDate.setOnMouseClicked(e -> {
            if(!rPackDate.isEditable()){
                rPackDate.hide();
            }
        });
                
        details.add(reserveGridName, 0, 0);
        details.add(rModelLabel, 0, 1);
        details.add(rModel, 1, 1);
        details.add(rSizeLabel, 0, 2);
        details.add(rSize, 1, 2);
        details.add(rSNLabel, 0, 3);
        details.add(rSN, 1, 3);
        details.add(rDOMLabel, 0, 4);
        details.add(rDOM, 1, 4);
        details.add(rManufacturerNameLabel, 0, 5);
        details.add(rManufacturerName, 1, 5);
        details.add(rJumpsLabel, 0, 6);
        details.add(rJumps, 1, 6);
        details.add(rPackDateLabel, 0, 7);
        details.add(rPackDate, 1, 7);
        details.setPadding(new Insets(5));
        
        Button saveBtn = new Button("Сохранить");
        saveBtn.setDisable(!editStatus);
        Button cancelBtn = new Button("Отменить");
        cancelBtn.setDisable(!editStatus);
        cancelBtn.setOnAction((ActionEvent event) -> {
        //Reserve
            rModel.setText(r.getReserveModel());
            rSize.setText(Integer.toString(r.getReserveSize()));
            rSN.setText(r.getReserveSN());
            rDOM.setValue(r.getReserveDOM());
            rManufacturerName.getSelectionModel().select(r.getReserveManufacturerID()-1); 
            rJumps.setText(Integer.toString(r.getReserveJumps()));
            rPackDate.setValue(r.getReservePackDate()); 
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            
            boolean emptyErr = true;
        //Reserve
            if (!rModel.getText().isEmpty()){
                emptyErr = false;
                rModel.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!rSize.getText().isEmpty()){
                emptyErr = false;
                rSize.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!rSN.getText().isEmpty()){
                emptyErr = false;
                rSN.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!rDOM.getValue().toString().isEmpty()){
                emptyErr = false;
                rDOM.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (rManufacturerName.getSelectionModel().isEmpty()){
                emptyErr = false;
                rManufacturerName.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!rJumps.getText().isEmpty()){
                emptyErr = false;
                rJumps.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!rPackDate.getValue().toString().isEmpty()){
                emptyErr = false;
                rPackDate.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (emptyErr){
            //Reserve
                ArrayList <String> reserveNewParams = new ArrayList<>();
                if (!rModel.getText().equals(r.getReserveModel())){
                    reserveNewParams.add("reserve_model = "+"\""+rModel.getText()+"\"");
                }
                if (!rSize.getText().equals(Integer.toString(r.getReserveSize()))){
                    reserveNewParams.add("reserve_size = "+rSize.getText());
                }
                if (!rSN.getText().equals(r.getReserveSN())){
                    reserveNewParams.add("reserve_sn = "+"\""+rSN.getText()+"\"");
                }
                if (!rDOM.getValue().equals(r.getReserveDOM())){
                    reserveNewParams.add("reserve_dom = "+"\'"+mySQLFormat.format(rDOM.getValue())+"\'");
                }
                if (rManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=r.getReserveManufacturerID()){
                    reserveNewParams.add("manufacturerid = "+rManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID());
                }
                if (!rJumps.getText().equals(Integer.toString(r.getReserveJumps()))){
                    reserveNewParams.add("reserve_jumps = "+rJumps.getText());
                }
                if (!rPackDate.getValue().equals(r.getReservePackDate())){
                    reserveNewParams.add("reserve_packdate = "+"\'"+mySQLFormat.format(rPackDate.getValue())+"\'");
                }
            //Apply changes    
                if (reserveNewParams.isEmpty()) {
                    //Ничего не меялось
                    Alert noChange = new Alert(AlertType.INFORMATION);
                    noChange.setTitle("Внимание!");
                    noChange.setHeaderText(null);
                    noChange.setContentText("Изменений в параметрах нет!");
                    noChange.showAndWait();
                }else{
                    updParams = reserveNewParams.get(0);
                    int i = reserveNewParams.size()-1;
                    while (i>0){
                        updParams = updParams.concat(", ").concat(reserveNewParams.get(i--));
                    }
                    Alert confirm = new Alert(AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Сохранить изменения в выбраном элементе?");
                    confirm.setContentText("ПЗ " + r.getReserveModel()+"-"+r.getReserveSize());
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Параметры не изменены!");
                        noChange.showAndWait();
                    } else if (option.get() == yes) {
                        dr.editReserve(r, updParams);
                        reserveNewParams.clear();
                    } else if (option.get() == no) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    } else {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    }
                }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Одно или несколько обязательных значений не заполнены. \nПроверьте правильность заполнения значений и повторите попытку.");
                emptyWarn.showAndWait();
            }
        });
        
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            if (editStatus == true){
                boolean changed = false;
                if (!rModel.getText().equals(r.getReserveModel())){
                    changed = true;
                }
                if (!rSize.getText().equals(Integer.toString(r.getReserveSize()))){
                    changed = true;
                }
                if (!rSN.getText().equals(r.getReserveSN())){
                    changed = true;
                }
                if (!rDOM.getValue().equals(r.getReserveDOM())){
                    changed = true;
                }
                if (rManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=r.getReserveManufacturerID()){
                    changed = true;
                }
                if (!rJumps.getText().equals(Integer.toString(r.getReserveJumps()))){
                    changed = true;
                }
                if (!rPackDate.getValue().equals(r.getReservePackDate())){
                    changed = true;
                }
                if (changed){
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Закрыть окно?");
                    confirm.setHeaderText("Не сохраненные изменения будут потеряны");
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        } else if (option.get() == yes) {
                            details.getScene().getWindow().hide();
                        } else if (option.get() == no) {
                        } else {
                    }
                }
            }else{
                details.getScene().getWindow().hide();                
            }
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, cancelBtn);
        details.add(buttonPane, 1, 8);
        
        return details;
    }
    
    private GridPane aadDetail(AAD a){
        GridPane details = new GridPane();
        
        Label aadGridName = new Label("Страхующий прибор");
        Label aModelLabel = new Label("Модель: ");
        TextField aModel = new TextField(a.getAadModel());
        aModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                aModel.setText(oldValue);
            }
        });
        aModel.setEditable(editStatus);
        Label aSNLabel = new Label("Серийный номер: ");
        TextField aSN = new TextField(a.getAadSN());
        aSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>20) {
                aSN.setText(oldValue);
            }
        });
        aSN.setEditable(editStatus);
        Label aDOMLabel = new Label("Дата производства: ");
        DatePicker aDOM = new DatePicker(a.getAadDOM());
        aDOM.setShowWeekNumbers(true);
        aDOM.setEditable(editStatus);
        aDOM.setOnMouseClicked(e -> {
            if(!aDOM.isEditable()){
                aDOM.hide();
            }
        });
        
        Label aManufacturerNameLabel = new Label("Производитель: ");
        ComboBox <Manufacturer> aManufacturerName = new ComboBox<>();
        ObservableList<Manufacturer> manufacturerList = dr.getManufactirerList();
        aManufacturerName.setItems(manufacturerList);
        aManufacturerName.setCellFactory(p -> new ListCell <Manufacturer> () {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getManufacturerName());
                } else {
                    setText(null);
                }
            }
        });
        aManufacturerName.setButtonCell(new ListCell <Manufacturer> () {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getManufacturerName());
                } else {
                    setText(null);
                }
            }
        });
        aManufacturerName.getSelectionModel().select(a.getAadManufacturerID()-1);
        aManufacturerName.setDisable(!editStatus);
        Label aJumpsLabel = new Label ("Прыжков: ");
        TextField aJumps = new TextField (Integer.toString(a.getAadJumps()));
        aJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,4}")) {
                aJumps.setText(oldValue);
            }
        });
        aJumps.setEditable(editStatus);
        Label aNextReglLabel = new Label ("Дата следующего регламента: ");
        DatePicker aNextRegl = new DatePicker(a.getAadNextRegl());
        aNextRegl.setShowWeekNumbers(true);
        aNextRegl.setEditable(editStatus);
        aNextRegl.setOnMouseClicked(e -> {
            if(!aNextRegl.isEditable()){
                aNextRegl.hide();
            }
        });
        
        Label aSavedLabel = new Label ("Количество применений: ");
        TextField aSaved = new TextField (Integer.toString(a.getAadSaved()));
        aSaved.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,4}")) {
                aSaved.setText(oldValue);
            }
        });
        
        aSaved.setEditable(editStatus);
        details.add(aadGridName, 0, 0);
        details.add(aModelLabel, 0, 1);
        details.add(aModel, 1, 1);
        details.add(aSNLabel, 0, 2);
        details.add(aSN, 1, 2);
        details.add(aDOMLabel, 0, 3);
        details.add(aDOM, 1, 3);
        details.add(aManufacturerNameLabel, 0, 4);
        details.add(aManufacturerName, 1, 4);
        details.add(aJumpsLabel, 0, 5);
        details.add(aJumps, 1, 5);
        details.add(aNextReglLabel, 0, 6);
        details.add(aNextRegl, 1, 6);
        details.add(aSavedLabel, 0, 7);
        details.add(aSaved, 1, 7);
        details.setPadding(new Insets(5));
        
        Button saveBtn = new Button("Сохранить");
        saveBtn.setDisable(!editStatus);
        Button cancelBtn = new Button("Отменить");
        cancelBtn.setDisable(!editStatus);
        cancelBtn.setOnAction((ActionEvent event) -> {
        //AAD
            aModel.setText(a.getAadModel());
            aSN.setText(a.getAadSN());
            aDOM.setValue(a.getAadDOM());
            aManufacturerName.getSelectionModel().select(a.getAadManufacturerID()-1);
            aJumps.setText(Integer.toString(a.getAadJumps()));
            aNextRegl.setValue(a.getAadNextRegl());
            aSaved.setText(Integer.toString(a.getAadSaved()));
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            
            boolean emptyErr = true;
        //AAD
            if (!aModel.getText().isEmpty()){
                emptyErr = false;
                aModel.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!aSN.getText().isEmpty()){
                emptyErr = false;
                aSN.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!aDOM.getValue().toString().isEmpty()){
                emptyErr = false;
                aDOM.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (aManufacturerName.getSelectionModel().isEmpty()){
                emptyErr = false;
                aManufacturerName.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!aJumps.getText().isEmpty()){
                emptyErr = false;
                aJumps.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!aNextRegl.getValue().toString().isEmpty()){
                emptyErr = false;
                aNextRegl.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (!aSaved.getText().isEmpty()){
                emptyErr = false;
                aSaved.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (emptyErr){
            //AAD
                ArrayList <String> aadNewParams = new ArrayList<>();
                if (!aModel.getText().equals(a.getAadModel())){
                    aadNewParams.add("aad_model = "+"\""+aModel.getText()+"\"");
                }
                if (!aSN.getText().equals(a.getAadSN())){
                    aadNewParams.add("aad_sn = "+"\""+aSN.getText()+"\"");
                }
                if (!aDOM.getValue().equals(a.getAadDOM())){
                    aadNewParams.add("aad_dom = "+"\'"+mySQLFormat.format(aDOM.getValue())+"\'");
                }
                if (aManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=a.getAadManufacturerID()){
                    aadNewParams.add("manufacturerid = "+aManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID());
                }
                if (!aJumps.getText().equals(Integer.toString(a.getAadJumps()))){
                    aadNewParams.add("aad_jumps = "+aJumps.getText());
                }
                if (!aNextRegl.getValue().equals(a.getAadNextRegl())){
                    aadNewParams.add("aad_nextregl = "+"\'"+mySQLFormat.format(aNextRegl.getValue())+"\'");
                }
                if (!aSaved.getText().equals(Integer.toString(a.getAadSaved()))){
                    aadNewParams.add("aad_saved = "+aSaved.getText());
                }
            //Apply changes    
                if (aadNewParams.isEmpty()) {
                    //Ничего не меялось
                    Alert noChange = new Alert(AlertType.INFORMATION);
                    noChange.setTitle("Внимание!");
                    noChange.setHeaderText(null);
                    noChange.setContentText("Изменений в параметрах нет!");
                    noChange.showAndWait();
                }else{
                    updParams = aadNewParams.get(0);
                    int i = aadNewParams.size()-1;
                    while (i>0){
                        updParams = updParams.concat(", ").concat(aadNewParams.get(i--));
                    }
                    Alert confirm = new Alert(AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Сохранить изменения в выбраном элементе?");
                    confirm.setContentText("ПЗ " + a.getAadModel()+" № "+a.getAadSN());
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Параметры не изменены!");
                        noChange.showAndWait();
                    } else if (option.get() == yes) {
                        dr.editAAD(a, updParams);
                        aadNewParams.clear();
                    } else if (option.get() == no) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    } else {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    }
                }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Одно или несколько обязательных значений не заполнены. \nПроверьте правильность заполнения значений и повторите попытку.");
                emptyWarn.showAndWait();
            }
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            if (editStatus == true){
                boolean changed = false;
                if (!aModel.getText().equals(a.getAadModel())){
                    changed = true;
                }
                if (!aSN.getText().equals(a.getAadSN())){
                    changed = true;
                }
                if (!aDOM.getValue().equals(a.getAadDOM())){
                    changed = true;
                }
                if (aManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=a.getAadManufacturerID()){
                    changed = true;
                }
                if (!aJumps.getText().equals(Integer.toString(a.getAadJumps()))){
                    changed = true;
                }
                if (!aNextRegl.getValue().equals(a.getAadNextRegl())){
                    changed = true;
                }
                if (!aSaved.getText().equals(Integer.toString(a.getAadSaved()))){
                    changed = true;
                }
                if (changed){
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Закрыть окно?");
                    confirm.setHeaderText("Не сохраненные изменения будут потеряны");
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        } else if (option.get() == yes) {
                            details.getScene().getWindow().hide();
                        } else if (option.get() == no) {
                        } else {
                    }
                }
            }else{
                details.getScene().getWindow().hide();                
            }
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, cancelBtn);
        details.add(buttonPane, 1, 8);
        
        return details;
    }
    
    private GridPane stockDetail(Stock stock){
        GridPane details = new GridPane();
        
        Label stockNameLabel = new Label("Наименование: ");
        TextField stockName = new TextField(stock.getStockName());
        stockName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                stockName.setText(oldValue);
            }
        });
        stockName.setEditable(editStatus);
        details.add(stockNameLabel, 0, 1);
        details.add(stockName, 1, 1);
        details.setPadding(new Insets(5));
        
        Button saveBtn = new Button("Сохранить");
        saveBtn.setDisable(!editStatus);
        Button cancelBtn = new Button("Отменить");
        cancelBtn.setDisable(!editStatus);
        cancelBtn.setOnAction((ActionEvent event) -> {
        //Stock
            stockName.setText(stock.getStockName());
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            
            boolean emptyErr = true;
            if (stockName.getText().isEmpty()){
                emptyErr = false;
                stockName.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (emptyErr){
            //Stock
                ArrayList <String> stockNewParams = new ArrayList<>();
                if (!stockName.getText().equals(stock.getStockName())){
                    stockNewParams.add("stock_name = "+"\""+stockName.getText()+"\"");
                }
            //Apply changes    
                if (stockNewParams.isEmpty()) {
                    //Ничего не меялось
                    Alert noChange = new Alert(AlertType.INFORMATION);
                    noChange.setTitle("Внимание!");
                    noChange.setHeaderText(null);
                    noChange.setContentText("Изменений в параметрах нет!");
                    noChange.showAndWait();
                }else{
                    updParams = stockNewParams.get(0);
                    int i = stockNewParams.size()-1;
                    while (i>0){
                        updParams = updParams.concat(", ").concat(stockNewParams.get(i--));
                    }
                    Alert confirm = new Alert(AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Сохранить изменения в выбраном элементе?");
                    confirm.setContentText("Склад " + stock.getStockName());
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Параметры не изменены!");
                        noChange.showAndWait();
                    } else if (option.get() == yes) {
                        dr.editStock(stock, updParams);
                        stockNewParams.clear();
                    } else if (option.get() == no) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    } else {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    }
                }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Одно или несколько обязательных значений не заполнены. \nПроверьте правильность заполнения значений и повторите попытку.");
                emptyWarn.showAndWait();
            }
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            if (editStatus == true){
                boolean changed = false;
                if (!stockName.getText().equals(stock.getStockName())){
                    changed = true;
                }
                if (changed){
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Закрыть окно?");
                    confirm.setHeaderText("Не сохраненные изменения будут потеряны");
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        } else if (option.get() == yes) {
                            details.getScene().getWindow().hide();
                        } else if (option.get() == no) {
                        } else {
                    }
                }
            }else{
                details.getScene().getWindow().hide();                
            }
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, cancelBtn);
        details.add(buttonPane, 1, 2);
        
        return details;
    }
    
    private GridPane manufacturerDetail(Manufacturer man){
        GridPane details = new GridPane();
//        String manufacturerName, String manufacturerCountry, String manufacturerTelephone, String manufacturerEmail
        Label manufacturerNameLabel = new Label("Модель: ");
        TextField manufacturerName = new TextField(man.getManufacturerName());
        manufacturerName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                manufacturerName.setText(oldValue);
            }
        });
        manufacturerName.setEditable(editStatus);
        Label manufacturerCountryLabel = new Label("Серийный номер: ");
        TextField manufacturerCountry = new TextField(man.getManufacturerCountry());
        manufacturerCountry.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                manufacturerCountry.setText(oldValue);
            }
        });
        manufacturerCountry.setEditable(editStatus);
        Label manufacturerTelephoneLabel = new Label("Модель: ");
        TextField manufacturerTelephone = new TextField(man.getManufacturerTelephone());
        manufacturerTelephone.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                manufacturerTelephone.setText(oldValue);
            }
        });
        manufacturerTelephone.setEditable(editStatus);
        Label manufacturerEmailLabel = new Label("Серийный номер: ");
        TextField manufacturerEmail = new TextField(man.getManufacturerEmail());
        manufacturerEmail.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                manufacturerEmail.setText(oldValue);
            }
        });
        manufacturerEmail.setEditable(editStatus);
        
        details.add(manufacturerNameLabel, 0, 1);
        details.add(manufacturerName, 1, 1);
        details.add(manufacturerCountryLabel, 0, 2);
        details.add(manufacturerCountry, 1, 2);
        details.add(manufacturerTelephoneLabel, 0, 3);
        details.add(manufacturerTelephone, 1, 3);
        details.add(manufacturerEmailLabel, 0, 4);
        details.add(manufacturerEmail, 1, 4);
        details.setPadding(new Insets(5));
        
        Button saveBtn = new Button("Сохранить");
        saveBtn.setDisable(!editStatus);
        Button cancelBtn = new Button("Отменить");
        cancelBtn.setDisable(!editStatus);
        cancelBtn.setOnAction((ActionEvent event) -> {
        //Manufacturer
            manufacturerName.setText(man.getManufacturerName());
            manufacturerCountry.setText(man.getManufacturerCountry());
            manufacturerTelephone.setText(man.getManufacturerTelephone());
            manufacturerEmail.setText(man.getManufacturerEmail());
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            boolean emptyErr = true;
            if (manufacturerName.getText().isEmpty()){
                emptyErr = false;
                manufacturerName.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (manufacturerCountry.getText().isEmpty()){
                emptyErr = false;
                manufacturerCountry.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (manufacturerTelephone.getText().isEmpty()){
                emptyErr = false;
                manufacturerTelephone.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (manufacturerEmail.getText().isEmpty()){
                emptyErr = false;
                manufacturerEmail.setStyle(" -fx-background-color: #ff0000, -fx-border-color: #ff0000");
            }
            if (emptyErr){
            //Manufacturer
                ArrayList <String> manufacturerNewParams = new ArrayList<>();
                if (!manufacturerName.getText().equals(man.getManufacturerName())){
                    manufacturerNewParams.add("manufacturer_name = "+"\""+manufacturerName.getText()+"\"");
                }
                if (!manufacturerCountry.getText().equals(man.getManufacturerCountry())){
                    manufacturerNewParams.add("manufacturer_country = "+"\""+manufacturerCountry.getText()+"\"");
                }
                if (!manufacturerTelephone.getText().equals(man.getManufacturerTelephone())){
                    manufacturerNewParams.add("manufacturer_telephone = "+manufacturerTelephone.getText());
                }
                if (!manufacturerEmail.getText().equals(man.getManufacturerEmail())){
                    manufacturerNewParams.add("manufacturer_email = "+manufacturerEmail.getText());
                }
            //Apply changes    
                if (manufacturerNewParams.isEmpty()) {
                    //Ничего не меялось
                    Alert noChange = new Alert(AlertType.INFORMATION);
                    noChange.setTitle("Внимание!");
                    noChange.setHeaderText(null);
                    noChange.setContentText("Изменений в параметрах нет!");
                    noChange.showAndWait();
                }else{
                    updParams = manufacturerNewParams.get(0);
                    int i = manufacturerNewParams.size()-1;
                    while (i>0){
                        updParams = updParams.concat(", ").concat(manufacturerNewParams.get(i--));
                    }
                    Alert confirm = new Alert(AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Сохранить изменения в выбраном элементе?");
                    confirm.setContentText("Склад " + man.getManufacturerName());
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Параметры не изменены!");
                        noChange.showAndWait();
                    } else if (option.get() == yes) {
                        dr.editManufacturer(man, updParams);
                        manufacturerNewParams.clear();
                    } else if (option.get() == no) {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    } else {
                        Alert noChange = new Alert(AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    }
                }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Одно или несколько обязательных значений не заполнены. \nПроверьте правильность заполнения значений и повторите попытку.");
                emptyWarn.showAndWait();
            }
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            if (editStatus == true){
                boolean changed = false;
                if (!manufacturerName.getText().equals(man.getManufacturerName())){
                    changed = true;
                }
                if (!manufacturerCountry.getText().equals(man.getManufacturerCountry())){
                    changed = true;
                }
                if (!manufacturerTelephone.getText().equals(man.getManufacturerTelephone())){
                    changed = true;
                }
                if (!manufacturerEmail.getText().equals(man.getManufacturerEmail())){
                    changed = true;
                }
                if (changed){
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Закрыть окно?");
                    confirm.setHeaderText("Не сохраненные изменения будут потеряны");
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                        } else if (option.get() == yes) {
                            details.getScene().getWindow().hide();
                        } else if (option.get() == no) {
                        } else {
                    }
                }
            }else{
                details.getScene().getWindow().hide();                
            }
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, cancelBtn);
        details.add(buttonPane, 1, 5);
        
        return details;
    }
    
    @Override
    public void start(Stage detailStage) {
        //SkydiveSystem (int systemID, String systemCode, String systemModel, String systemSN, String systemDOM, int systemManufacturerID, String systemManufacturerName, int canopyID, String canopyModel, int canopySize, String canopySN, String canopyDOM, int canopyJumps, int canopyManufacturerID, String canopyManufacturerName, int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int reserveJumps, String reservePackDate, int reserveManufacturerID, String reserveManufacturerName, int aadID, String aadModel, String aadSN, String aadDOM, int aadJumps, String aadNextRegl, boolean aadFired, int aadManufacturerID, String aadManufacturerName)
        detailStage.setTitle(stageTitle);
        detailStage.setScene(scene);
        detailStage.show(); 

    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
