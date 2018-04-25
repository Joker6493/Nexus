/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private String elementType;
    private boolean editStatus;
    private String stageTitle;
    private Scene scene;
    private boolean assemble = false;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    ElementDetails (SkydiveSystem selectedSystem, boolean editStatus){
        this.selectedSystem = selectedSystem;
        this.stageTitle = "Система "+selectedSystem.getSystemCode();
        this.editStatus = editStatus;
        this.scene = new Scene(containerDetail(selectedSystem));
        this.assemble = true;
    }
    ElementDetails (Canopy selectedCanopy, boolean editStatus){
        this.selectedCanopy = selectedCanopy;
        this.stageTitle = "Основной парашют "+selectedCanopy.getCanopyModel()+"-"+selectedCanopy.getCanopySize();
        this.editStatus = editStatus;
        this.scene = new Scene(canopyDetail(selectedCanopy));
    }
    ElementDetails (Reserve selectedReserve, boolean editStatus){
        this.selectedReserve = selectedReserve;
        this.stageTitle = "Запасной парашют "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize();
        this.editStatus = editStatus;
        this.scene = new Scene(reserveDetail(selectedReserve));
    }
    ElementDetails (AAD selectedAAD, boolean editStatus){
        this.selectedAAD = selectedAAD;
        this.stageTitle = "Страхующий прибор "+selectedAAD.getAadModel()+" "+selectedAAD.getAadSN();
        this.editStatus = editStatus;
        this.scene = new Scene(aadDetail(selectedAAD));
    }
    ElementDetails (String elementType, int stockID){
        this.elementType = elementType;
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
        }
        this.editStatus = true;
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
        TextField sManufacturerName = new TextField(ss.getSystemManufacturerName());
        sManufacturerName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>100) {
                sManufacturerName.setText(oldValue);
            }
        });
        sManufacturerName.setEditable(editStatus);
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
            //some code here, rollback any changes
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            //some code here, rollback any changes
        });
        ToggleButton editBtn = new ToggleButton ("Редактировать");
        editBtn.setSelected(editStatus);
        editBtn.setOnAction((ActionEvent event) -> {
            //Allow editing, commiting next
            if (editStatus==false){
                editStatus = true;
                sModel.setEditable(editStatus);
                sSN.setEditable(editStatus);
                sDOM.setEditable(editStatus);
                sManufacturerName.setEditable(editStatus);
                saveBtn.setDisable(!editStatus);
                assembleBtn.setDisable(!editStatus);
                cancelBtn.setDisable(!editStatus);
            }else{
                editStatus = false;
                sModel.setEditable(editStatus);
                sSN.setEditable(editStatus);
                sDOM.setEditable(editStatus);
                sManufacturerName.setEditable(editStatus);
                saveBtn.setDisable(!editStatus);
                assembleBtn.setDisable(!editStatus);
                cancelBtn.setDisable(!editStatus);
            }             
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            //some code here, if there are some changes, ask for save them, then close window, if not - close window
            details.getScene().getWindow().hide();
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, assembleBtn, cancelBtn);
        details.add(buttonPane, 1, 4);
        
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
        TextField cManufacturerName = new TextField(c.getCanopyManufacturerName());
        cManufacturerName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>100) {
                cManufacturerName.setText(oldValue);
            }
        });
        cManufacturerName.setEditable(editStatus);
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
            //some code here, rollback any changes
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            //some code here, rollback any changes
        });
        ToggleButton editBtn = new ToggleButton ("Редактировать");
        editBtn.setSelected(editStatus);
        editBtn.setOnAction((ActionEvent event) -> {
            //Allow editing, commiting next
            if (editStatus==false){
                editStatus = true;
                cModel.setEditable(editStatus);
                cSize.setEditable(editStatus);
                cSN.setEditable(editStatus);
                cDOM.setEditable(editStatus);
                cManufacturerName.setEditable(editStatus);
                cJumps.setEditable(editStatus);
                saveBtn.setDisable(!editStatus);
                cancelBtn.setDisable(!editStatus);
            }else{
                editStatus = false;
                cModel.setEditable(editStatus);
                cSize.setEditable(editStatus);
                cSN.setEditable(editStatus);
                cDOM.setEditable(editStatus);
                cManufacturerName.setEditable(editStatus);
                cJumps.setEditable(editStatus);
                saveBtn.setDisable(!editStatus);
                cancelBtn.setDisable(!editStatus);
            }             
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            //some code here, if there are some changes, ask for save them, then close window, if not - close window
            details.getScene().getWindow().hide();
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
        TextField rManufacturerName = new TextField(r.getReserveManufacturerName());
        rManufacturerName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>100) {
                rManufacturerName.setText(oldValue);
            }
        });
        rManufacturerName.setEditable(editStatus);
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
            //some code here, rollback any changes
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            //some code here, rollback any changes
        });
        ToggleButton editBtn = new ToggleButton ("Редактировать");
        editBtn.setSelected(editStatus);
        editBtn.setOnAction((ActionEvent event) -> {
            //Allow editing, commiting next
            if (editStatus==false){
                editStatus = true;
                rModel.setEditable(editStatus);
                rSize.setEditable(editStatus);
                rSN.setEditable(editStatus);
                rDOM.setEditable(editStatus);
                rManufacturerName.setEditable(editStatus);
                rJumps.setEditable(editStatus);
                rPackDate.setEditable(editStatus);
                saveBtn.setDisable(!editStatus);
                cancelBtn.setDisable(!editStatus);
            }else{
                editStatus = false;
                rModel.setEditable(editStatus);
                rSize.setEditable(editStatus);
                rSN.setEditable(editStatus);
                rDOM.setEditable(editStatus);
                rManufacturerName.setEditable(editStatus);
                rJumps.setEditable(editStatus);
                rPackDate.setEditable(editStatus);
                saveBtn.setDisable(!editStatus);
                cancelBtn.setDisable(!editStatus);
            }             
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            //some code here, if there are some changes, ask for save them, then close window, if not - close window
            details.getScene().getWindow().hide();
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
        TextField aManufacturerName = new TextField(a.getAadManufacturerName());
        aManufacturerName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>100) {
                aManufacturerName.setText(oldValue);
            }
        });
        aManufacturerName.setEditable(editStatus);
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
            //some code here, rollback any changes
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            //some code here, rollback any changes
        });
        ToggleButton editBtn = new ToggleButton ("Редактировать");
        editBtn.setSelected(editStatus);
        editBtn.setOnAction((ActionEvent event) -> {
            //Allow editing, commiting next
            if (editStatus==false){
                editStatus = true;
                aModel.setEditable(editStatus);
                aSN.setEditable(editStatus);
                aDOM.setEditable(editStatus);
                aManufacturerName.setEditable(editStatus);
                aJumps.setEditable(editStatus);
                aNextRegl.setEditable(editStatus);
                aSaved.setEditable(editStatus);
                saveBtn.setDisable(!editStatus);
                cancelBtn.setDisable(!editStatus);
            }else{
                editStatus = false;
                aModel.setEditable(editStatus);
                aSN.setEditable(editStatus);
                aDOM.setEditable(editStatus);
                aManufacturerName.setEditable(editStatus);
                aJumps.setEditable(editStatus);
                aNextRegl.setEditable(editStatus);
                aSaved.setEditable(editStatus);
                saveBtn.setDisable(!editStatus);
                cancelBtn.setDisable(!editStatus);
            }             
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            //some code here, if there are some changes, ask for save them, then close window, if not - close window
            details.getScene().getWindow().hide();
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, cancelBtn);
        details.add(buttonPane, 1, 8);
        
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
