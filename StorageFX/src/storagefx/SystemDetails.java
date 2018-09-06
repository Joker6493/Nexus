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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author dboro
 */
public class SystemDetails extends Application {
    private final SkydiveSystem selectedSystem;
    private boolean editStatus;
    private String updParams;
    private DataRelay dr = new DataRelay();
    private String stageTitle;
    private int stockID;
    private int status;
    private boolean assembleInProcess = false;
    private boolean newSystem;
    private Canopy newCanopy;
    private Reserve newReserve;
    private AAD newAAD;

    public boolean isNewSystem() {
        return newSystem;
    }
    public void setNewSystem(boolean newSystem) {
        this.newSystem = newSystem;
    }
    public Canopy getNewCanopy() {
        return newCanopy;
    }
    public void setNewCanopy(Canopy newCanopy) {
        this.newCanopy = newCanopy;
    }
    public Reserve getNewReserve() {
        return newReserve;
    }
    public void setNewReserve(Reserve newReserve) {
        this.newReserve = newReserve;
    }
    public AAD getNewAAD() {
        return newAAD;
    }
    public void setNewAAD(AAD newAAD) {
        this.newAAD = newAAD;
    }
    public boolean isAssembleInProcess() {
        return assembleInProcess;
    }
    public void setAssembleInProcess(boolean assembleInProcess) {
        this.assembleInProcess = assembleInProcess;
    }
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
    SystemDetails (SkydiveSystem selectedSystem, boolean editStatus){
        this.selectedSystem = selectedSystem;
        this.stageTitle = "Система "+selectedSystem.getSystemCode();
        this.editStatus = editStatus;
        this.newSystem = false;
    }
    
    SystemDetails (int stockID){
        this.selectedSystem = new SkydiveSystem(0, "", "", "", LocalDate.now(), 0, "", stockID, 0, "", 0, "", LocalDate.now(), 0, 0, "", 0, "", 0, "", LocalDate.now(), 0, LocalDate.now(), 0, "", 0, "", "", LocalDate.now(), 0, LocalDate.now(), 0, 0, "");
        this.stageTitle = "Добавление новой системы";
        this.editStatus = true;
        this.newSystem = true;
    }
        
    @Override
    public void start(Stage detailStage) {
        //SkydiveSystem (int systemID, String systemCode, String systemModel, String systemSN, String systemDOM, int systemManufacturerID, String systemManufacturerName, int canopyID, String canopyModel, int canopySize, String canopySN, String canopyDOM, int canopyJumps, int canopyManufacturerID, String canopyManufacturerName, int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int reserveJumps, String reservePackDate, int reserveManufacturerID, String reserveManufacturerName, int aadID, String aadModel, String aadSN, String aadDOM, int aadJumps, String aadNextRegl, boolean aadFired, int aadManufacturerID, String aadManufacturerName)
        GridPane details = new GridPane();
    //Container
        GridPane containerGrid = new GridPane();
        //String systemModel, String systemSN, String systemDOM, String systemManufacturerName
        Label containerGridName = new Label("Ранец и подвесная система");
        TextField sCode = new TextField(selectedSystem.getSystemCode());
        sCode.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            sCode.getStyleClass().clear();
            if (newValue.length()>6) {
                sCode.setText(oldValue);
            }
        });
        sCode.setEditable(editStatus);
        Label sModelLabel = new Label("Модель: ");
        TextField sModel = new TextField(selectedSystem.getSystemModel());
        sModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            sModel.getStyleClass().clear();
            if (newValue.length()>50) {
                sModel.setText(oldValue);
            }
        });
        sModel.setEditable(editStatus);
        Label sSNLabel = new Label("Серийный номер: ");
        TextField sSN = new TextField(selectedSystem.getSystemSN());
        sSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            sSN.getStyleClass().clear();
            if (newValue.length()>50) {
                sSN.setText(oldValue);
            }
        });
        sSN.setEditable(editStatus);
        Label sDOMLabel = new Label("Дата производства: ");
        DatePicker sDOM = new DatePicker(selectedSystem.getSystemDOM());
        sDOM.setShowWeekNumbers(true);
        sDOM.setEditable(editStatus);
        sDOM.setOnMouseClicked(e -> {
            sDOM.getStyleClass().clear();
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
        sManufacturerName.setOnMouseClicked(e -> {
            sManufacturerName.getStyleClass().clear();
        });
        sManufacturerName.getSelectionModel().select(selectedSystem.getSystemManufacturerID()-1);
        sManufacturerName.setDisable(!editStatus);
        containerGrid.add(containerGridName, 0, 0);
        containerGrid.add(sCode, 1, 0);
        containerGrid.add(sModelLabel, 0, 1);
        containerGrid.add(sModel, 1, 1);
        containerGrid.add(sSNLabel, 0, 2);
        containerGrid.add(sSN, 1, 2);
        containerGrid.add(sDOMLabel, 0, 3);
        containerGrid.add(sDOM, 1, 3);
        containerGrid.add(sManufacturerNameLabel, 0, 4);
        containerGrid.add(sManufacturerName, 1, 4);
        containerGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(10), new BorderWidths(1.5), new Insets(1))));        
        containerGrid.setPadding(new Insets(5));
        
    //Canopy
        Canopy sCanopy = new Canopy(selectedSystem.getSystemID(), selectedSystem.getCanopyModel(), selectedSystem.getCanopySize(), selectedSystem.getCanopySN(), selectedSystem.getCanopyDOM(), selectedSystem.getCanopyJumps(), selectedSystem.getCanopyManufacturerID(), selectedSystem.getCanopyManufacturerName(), selectedSystem.getStockID());
        GridPane canopyGrid = new GridPane();
        Label canopyGridName = new Label("Основной парашют");
        Label cModelLabel = new Label("Модель: ");
        TextField cModel = new TextField(sCanopy.getCanopyModel());
        cModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            cModel.getStyleClass().clear();
            if (newValue.length()>50) {
                cModel.setText(oldValue);
            }
        });
        cModel.setEditable(editStatus);
        Label cSizeLabel = new Label ("Размер купола, кв.фут: ");
        TextField cSize = new TextField (Integer.toString(sCanopy.getCanopySize()));
        cSize.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            cSize.getStyleClass().clear();
            if (!newValue.matches("\\d{0,3}")) {
                cSize.setText(oldValue);
            }
        });
        cSize.setEditable(editStatus);
        Label cSNLabel = new Label("Серийный номер: ");
        TextField cSN = new TextField(sCanopy.getCanopySN());
        cSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            cSN.getStyleClass().clear();
            if (newValue.length()>20) {
                cSN.setText(oldValue);
            }
        });
        cSN.setEditable(editStatus);
        Label cDOMLabel = new Label("Дата производства: ");
        DatePicker cDOM = new DatePicker(sCanopy.getCanopyDOM());
        cDOM.setShowWeekNumbers(true);
        cDOM.setEditable(editStatus);
        cDOM.setOnMouseClicked(e -> {
            cDOM.getStyleClass().clear();
            if(!cDOM.isEditable()){
                cDOM.hide();
            }
        });
        
        Label cManufacturerNameLabel = new Label("Производитель: ");
        ComboBox <Manufacturer> cManufacturerName = new ComboBox<>();
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
        cManufacturerName.setOnMouseClicked(e -> {
            cManufacturerName.getStyleClass().clear();
            });
        cManufacturerName.getSelectionModel().select(sCanopy.getCanopyManufacturerID()-1);
        cManufacturerName.setDisable(!editStatus);
        Label cJumpsLabel = new Label ("Прыжков: ");
        TextField cJumps = new TextField (Integer.toString(sCanopy.getCanopyJumps()));
        cJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            cJumps.getStyleClass().clear();
            if (!newValue.matches("\\d{0,4}")) {
                cJumps.setText(oldValue);
            }
        });
        
        Button cChoose = new Button ("...");
        cChoose.setTooltip(new Tooltip("Выберите купол основного парашюта"));
        cChoose.setDisable(!editStatus);
        cChoose.setOnAction((ActionEvent event) -> {
            Stage chooseWindow = new Stage();
            chooseWindow.setTitle("Выберите купол основного парашюта");
            //TODO - transmit to modal window stock and current canopy
            CanopyList cl = new CanopyList();
            cl.setOldCanopy(sCanopy);
            cl.setSelectedSystem(selectedSystem);
            cl.setStatus(getStatus());
            cl.setStockID(getStockID());
            cl.setAssembleInProcess(isAssembleInProcess());
            cl.setNewSystem(isNewSystem());
            Scene cList = new Scene(cl.CanopyTable(true));
            chooseWindow.setScene(cList);
            
            chooseWindow.initModality(Modality.WINDOW_MODAL);
            chooseWindow.initOwner(detailStage.getScene().getWindow());
            chooseWindow.showAndWait();
            if (cl.getSelectedCanopy() != null){
                setNewCanopy(cl.getSelectedCanopy());
                cModel.setText(getNewCanopy().getCanopyModel());
                cSize.setText(Integer.toString(getNewCanopy().getCanopySize()));
                cSN.setText(getNewCanopy().getCanopySN());
                cDOM.setValue(getNewCanopy().getCanopyDOM());
                cJumps.setText(Integer.toString(getNewCanopy().getCanopyJumps()));
                cManufacturerName.getSelectionModel().select(getNewCanopy().getCanopyManufacturerID()-1);
                if (!isAssembleInProcess()){
            //Updating canopy data in skydive system
                    selectedSystem.setCanopyModel(getNewCanopy().getCanopyModel());
                    selectedSystem.setCanopySize(getNewCanopy().getCanopySize());
                    selectedSystem.setCanopySN(getNewCanopy().getCanopySN());
                    selectedSystem.setCanopyDOM(getNewCanopy().getCanopyDOM());
                    selectedSystem.setCanopyJumps(getNewCanopy().getCanopyJumps());
                    selectedSystem.setCanopyManufacturerID(getNewCanopy().getCanopyManufacturerID());
                    selectedSystem.setCanopyManufacturerName(getNewCanopy().getCanopyManufacturerName());
                    sCanopy.setCanopyModel(getNewCanopy().getCanopyModel());
                    sCanopy.setCanopySize(getNewCanopy().getCanopySize());
                    sCanopy.setCanopySN(getNewCanopy().getCanopySN());
                    sCanopy.setCanopyDOM(getNewCanopy().getCanopyDOM());
                    sCanopy.setCanopyJumps(getNewCanopy().getCanopyJumps());
                    sCanopy.setCanopyManufacturerID(getNewCanopy().getCanopyManufacturerID());
                    sCanopy.setCanopyManufacturerName(getNewCanopy().getCanopyManufacturerName());
                }
            }
        });
        
        cJumps.setEditable(editStatus);
        canopyGrid.add(canopyGridName, 0, 0);
        canopyGrid.add(cModelLabel, 0, 1);
        canopyGrid.add(cModel, 1, 1);
        canopyGrid.add(cSizeLabel, 0, 2);
        canopyGrid.add(cSize, 1, 2);
        canopyGrid.add(cSNLabel, 0, 3);
        canopyGrid.add(cSN, 1, 3);
        canopyGrid.add(cDOMLabel, 0, 4);
        canopyGrid.add(cDOM, 1, 4);
        canopyGrid.add(cManufacturerNameLabel, 0, 5);
        canopyGrid.add(cManufacturerName, 1, 5);
        canopyGrid.add(cJumpsLabel, 0, 6);
        canopyGrid.add(cJumps, 1, 6);
        canopyGrid.add(cChoose, 1, 7);
        canopyGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(10), new BorderWidths(1.5), new Insets(1))));        
        canopyGrid.setPadding(new Insets(5));
        
    //Reserve
        Reserve sReserve = new Reserve(selectedSystem.getSystemID(), selectedSystem.getReserveModel(), selectedSystem.getReserveSize(), selectedSystem.getReserveSN(), selectedSystem.getReserveDOM(), selectedSystem.getReserveJumps(), selectedSystem.getReservePackDate(), selectedSystem.getReserveManufacturerID(), selectedSystem.getReserveManufacturerName(), selectedSystem.getStockID());
        GridPane reserveGrid = new GridPane();
        Label reserveGridName = new Label("Запасной парашют");
        Label rModelLabel = new Label("Модель: ");
        TextField rModel = new TextField(sReserve.getReserveModel());
        rModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            rModel.getStyleClass().clear();
            if (newValue.length()>50) {
                rModel.setText(oldValue);
            }
        });
        rModel.setEditable(editStatus);
        Label rSizeLabel = new Label ("Размер купола, кв.фут: ");
        TextField rSize = new TextField (Integer.toString(sReserve.getReserveSize()));
        rSize.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            rSize.getStyleClass().clear();
            if (!newValue.matches("\\d{0,3}")) {
                rSize.setText(oldValue);
            }
        });
        rSize.setEditable(editStatus);
        Label rSNLabel = new Label("Серийный номер: ");
        TextField rSN = new TextField(sReserve.getReserveSN());
        rSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            rSN.getStyleClass().clear();
            if (newValue.length()>20) {
                rSN.setText(oldValue);
            }
        });
        rSN.setEditable(editStatus);
        Label rDOMLabel = new Label("Дата производства: ");
        DatePicker rDOM = new DatePicker(sReserve.getReserveDOM());
        rDOM.setShowWeekNumbers(true);
        rDOM.setEditable(editStatus);
        rDOM.setOnMouseClicked(e -> {
            rDOM.getStyleClass().clear();
            if(!rDOM.isEditable()){
                rDOM.hide();
            }
        });
        
        Label rManufacturerNameLabel = new Label("Производитель: ");
        ComboBox <Manufacturer> rManufacturerName = new ComboBox<>();
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
        rManufacturerName.setOnMouseClicked(e -> {
            rManufacturerName.getStyleClass().clear();
        });
        rManufacturerName.getSelectionModel().select(sReserve.getReserveManufacturerID()-1);
        rManufacturerName.setDisable(!editStatus);
        Label rJumpsLabel = new Label ("Применений: ");
        TextField rJumps = new TextField (Integer.toString(sReserve.getReserveJumps()));
        rJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            rJumps.getStyleClass().clear();
            if (!newValue.matches("\\d{0,4}")) {
                rJumps.setText(oldValue);
            }
        });
        rJumps.setEditable(editStatus);
        Label rPackDateLabel = new Label ("Дата укладки: ");
        DatePicker rPackDate = new DatePicker(sReserve.getReservePackDate());
        rPackDate.setShowWeekNumbers(true);
        rPackDate.setEditable(editStatus);
        rPackDate.setOnMouseClicked(e -> {
            rPackDate.getStyleClass().clear();
            if(!rPackDate.isEditable()){
                rPackDate.hide();
            }
        });
        Button rChoose = new Button ("...");
        rChoose.setTooltip(new Tooltip("Выберите купол запасного парашюта"));
        rChoose.setDisable(!editStatus);
        rChoose.setOnAction((ActionEvent event) -> {
            Stage chooseWindow = new Stage();
            chooseWindow.setTitle("Выберите купол запасного парашюта");
            //TODO - transmit to modal window stock and current canopy
            ReserveList rl = new ReserveList();
            rl.setOldReserve(sReserve);
            rl.setSelectedSystem(selectedSystem);
            rl.setStatus(getStatus());
            rl.setStockID(getStockID());
            rl.setAssembleInProcess(isAssembleInProcess());
            rl.setNewSystem(isNewSystem());
            Scene rList = new Scene(rl.ReserveTable(true));
            chooseWindow.setScene(rList);
            
            chooseWindow.initModality(Modality.WINDOW_MODAL);
            chooseWindow.initOwner(detailStage.getScene().getWindow());
            chooseWindow.showAndWait();
            if (rl.getSelectedReserve() != null){
                setNewReserve(rl.getSelectedReserve());
                rModel.setText(getNewReserve().getReserveModel());
                rSize.setText(Integer.toString(getNewReserve().getReserveSize()));
                rSN.setText(getNewReserve().getReserveSN());
                rDOM.setValue(getNewReserve().getReserveDOM());
                rManufacturerName.getSelectionModel().select(getNewReserve().getReserveManufacturerID()-1); 
                rJumps.setText(Integer.toString(getNewReserve().getReserveJumps()));
                rPackDate.setValue(getNewReserve().getReservePackDate());
                if (!isAssembleInProcess()){
            //Updating reserve data in skydive system
                    selectedSystem.setReserveModel(getNewReserve().getReserveModel());
                    selectedSystem.setReserveSize(getNewReserve().getReserveSize());
                    selectedSystem.setReserveSN(getNewReserve().getReserveSN());
                    selectedSystem.setReserveDOM(getNewReserve().getReserveDOM());
                    selectedSystem.setReserveJumps(getNewReserve().getReserveJumps());
                    selectedSystem.setReservePackDate(getNewReserve().getReservePackDate());
                    selectedSystem.setReserveManufacturerID(getNewReserve().getReserveManufacturerID());
                    selectedSystem.setReserveManufacturerName(getNewReserve().getReserveManufacturerName());
                    sReserve.setReserveModel(getNewReserve().getReserveModel());
                    sReserve.setReserveSize(getNewReserve().getReserveSize());
                    sReserve.setReserveSN(getNewReserve().getReserveSN());
                    sReserve.setReserveDOM(getNewReserve().getReserveDOM());
                    sReserve.setReserveJumps(getNewReserve().getReserveJumps());
                    sReserve.setReservePackDate(getNewReserve().getReservePackDate());
                    sReserve.setReserveManufacturerID(getNewReserve().getReserveManufacturerID());
                    sReserve.setReserveManufacturerName(getNewReserve().getReserveManufacturerName());
                }
            }
        });
        
        reserveGrid.add(reserveGridName, 0, 0);
        reserveGrid.add(rModelLabel, 0, 1);
        reserveGrid.add(rModel, 1, 1);
        reserveGrid.add(rSizeLabel, 0, 2);
        reserveGrid.add(rSize, 1, 2);
        reserveGrid.add(rSNLabel, 0, 3);
        reserveGrid.add(rSN, 1, 3);
        reserveGrid.add(rDOMLabel, 0, 4);
        reserveGrid.add(rDOM, 1, 4);
        reserveGrid.add(rManufacturerNameLabel, 0, 5);
        reserveGrid.add(rManufacturerName, 1, 5);
        reserveGrid.add(rJumpsLabel, 0, 6);
        reserveGrid.add(rJumps, 1, 6);
        reserveGrid.add(rPackDateLabel, 0, 7);
        reserveGrid.add(rPackDate, 1, 7);
        reserveGrid.add(rChoose, 1, 8);
        reserveGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(10), new BorderWidths(1.5), new Insets(1))));        
        reserveGrid.setPadding(new Insets(5));
        
    //AAD
        AAD sAAD = new AAD(selectedSystem.getSystemID(), selectedSystem.getAadModel(), selectedSystem.getAadSN(), selectedSystem.getAadDOM(), selectedSystem.getAadJumps(), selectedSystem.getAadNextRegl(), selectedSystem.getAadSaved(), selectedSystem.getAadManufacturerID(), selectedSystem.getAadManufacturerName(), selectedSystem.getStockID());
        GridPane aadGrid = new GridPane();
        Label aadGridName = new Label("Страхующий прибор");
        Label aModelLabel = new Label("Модель: ");
        TextField aModel = new TextField(sAAD.getAadModel());
        aModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            aModel.getStyleClass().clear();
            if (newValue.length()>50) {
                aModel.setText(oldValue);
            }
        });
        aModel.setEditable(editStatus);
        Label aSNLabel = new Label("Серийный номер: ");
        TextField aSN = new TextField(sAAD.getAadSN());
        aSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            aSN.getStyleClass().clear();
            if (newValue.length()>20) {
                aSN.setText(oldValue);
            }
        });
        aSN.setEditable(editStatus);
        Label aDOMLabel = new Label("Дата производства: ");
        DatePicker aDOM = new DatePicker(sAAD.getAadDOM());
        aDOM.setShowWeekNumbers(true);
        aDOM.setEditable(editStatus);
        aDOM.setOnMouseClicked(e -> {
            aDOM.getStyleClass().clear();
            if(!aDOM.isEditable()){
                aDOM.hide();
            }
        });
        
        Label aManufacturerNameLabel = new Label("Производитель: ");
        ComboBox <Manufacturer> aManufacturerName = new ComboBox<>();
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
        aManufacturerName.setOnMouseClicked(e -> {
            aManufacturerName.getStyleClass().clear();
        });
        aManufacturerName.getSelectionModel().select(sAAD.getAadManufacturerID()-1);
        aManufacturerName.setDisable(!editStatus);
        Label aJumpsLabel = new Label ("Прыжков: ");
        TextField aJumps = new TextField (Integer.toString(sAAD.getAadJumps()));
        aJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            aJumps.getStyleClass().clear();
            if (!newValue.matches("\\d{0,4}")) {
                aJumps.setText(oldValue);
            }
        });
        aJumps.setEditable(editStatus);
        Label aNextReglLabel = new Label ("Дата следующего регламента: ");
        DatePicker aNextRegl = new DatePicker(sAAD.getAadNextRegl());
        aNextRegl.setShowWeekNumbers(true);
        aNextRegl.setEditable(editStatus);
        aNextRegl.setOnMouseClicked(e -> {
            aNextRegl.getStyleClass().clear();
            if(!aNextRegl.isEditable()){
                aNextRegl.hide();
            }
        });
        
        Label aSavedLabel = new Label ("Количество применений: ");
        TextField aSaved = new TextField (Integer.toString(sAAD.getAadSaved()));
        aSaved.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            aSaved.getStyleClass().clear();
            if (!newValue.matches("\\d{0,4}")) {
                aSaved.setText(oldValue);
            }
        });
        Button aChoose = new Button ("...");
        aChoose.setTooltip(new Tooltip("Выберите страхующий прибор"));
        aChoose.setDisable(!editStatus);
        aChoose.setOnAction((ActionEvent event) -> {
            Stage chooseWindow = new Stage();
            chooseWindow.setTitle("Выберите страхующий прибор");
            //TODO - transmit to modal window stock and current canopy
            AADList al = new AADList();
            al.setOldAAD(sAAD);
            al.setSelectedSystem(selectedSystem);
            al.setStatus(getStatus());
            al.setStockID(getStockID());
            al.setAssembleInProcess(isAssembleInProcess());
            al.setNewSystem(isNewSystem());
            Scene aList = new Scene(al.AADTable(true));
            chooseWindow.setScene(aList);
            
            chooseWindow.initModality(Modality.WINDOW_MODAL);
            chooseWindow.initOwner(detailStage.getScene().getWindow());
            chooseWindow.showAndWait();
            if (al.getSelectedAAD() != null){
                setNewAAD(al.getSelectedAAD());
                aModel.setText(getNewAAD().getAadModel());
                aSN.setText(getNewAAD().getAadSN());
                aDOM.setValue(getNewAAD().getAadDOM());
                aManufacturerName.getSelectionModel().select(getNewAAD().getAadManufacturerID()-1);
                aJumps.setText(Integer.toString(getNewAAD().getAadJumps()));
                aNextRegl.setValue(getNewAAD().getAadNextRegl());
                aSaved.setText(Integer.toString(getNewAAD().getAadSaved()));
                if (!isAssembleInProcess()){
            //Updating aad data in skydive system
                    selectedSystem.setAadModel(getNewAAD().getAadModel());
                    selectedSystem.setAadSN(getNewAAD().getAadSN());
                    selectedSystem.setAadDOM(getNewAAD().getAadDOM());
                    selectedSystem.setAadJumps(getNewAAD().getAadJumps());
                    selectedSystem.setAadNextRegl(getNewAAD().getAadNextRegl());
                    selectedSystem.setAadSaved(getNewAAD().getAadSaved());
                    selectedSystem.setAadManufacturerID(getNewAAD().getAadManufacturerID());
                    selectedSystem.setAadManufacturerName(getNewAAD().getAadManufacturerName());
                    sAAD.setAadModel(getNewAAD().getAadModel());
                    sAAD.setAadSN(getNewAAD().getAadSN());
                    sAAD.setAadDOM(getNewAAD().getAadDOM());
                    sAAD.setAadJumps(getNewAAD().getAadJumps());
                    sAAD.setAadNextRegl(getNewAAD().getAadNextRegl());
                    sAAD.setAadSaved(getNewAAD().getAadSaved());
                    sAAD.setAadManufacturerID(getNewAAD().getAadManufacturerID());
                    sAAD.setAadManufacturerName(getNewAAD().getAadManufacturerName());
                }
            }
        });
        
        aSaved.setEditable(editStatus);
        aadGrid.add(aadGridName, 0, 0);
        aadGrid.add(aModelLabel, 0, 1);
        aadGrid.add(aModel, 1, 1);
        aadGrid.add(aSNLabel, 0, 2);
        aadGrid.add(aSN, 1, 2);
        aadGrid.add(aDOMLabel, 0, 3);
        aadGrid.add(aDOM, 1, 3);
        aadGrid.add(aManufacturerNameLabel, 0, 4);
        aadGrid.add(aManufacturerName, 1, 4);
        aadGrid.add(aJumpsLabel, 0, 5);
        aadGrid.add(aJumps, 1, 5);
        aadGrid.add(aNextReglLabel, 0, 6);
        aadGrid.add(aNextRegl, 1, 6);
        aadGrid.add(aSavedLabel, 0, 7);
        aadGrid.add(aSaved, 1, 7);
        aadGrid.add(aChoose, 1, 8);
        aadGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(10), new BorderWidths(1.5), new Insets(1))));        
        aadGrid.setPadding(new Insets(5));
        
        Button saveBtn = new Button("Сохранить");
        saveBtn.setDisable(!editStatus);
        Button cancelBtn = new Button("Отменить");
        cancelBtn.setDisable(!editStatus);
        cancelBtn.setOnAction((ActionEvent event) -> {
            //rollback any changes
        //Container
            sCode.setText(selectedSystem.getSystemCode());
            sModel.setText(selectedSystem.getSystemModel());
            sSN.setText(selectedSystem.getSystemSN());
            sDOM.setValue(selectedSystem.getSystemDOM());
            sManufacturerName.getSelectionModel().select(selectedSystem.getSystemManufacturerID()-1);
        //Canopy
            cModel.setText(sCanopy.getCanopyModel());
            cSize.setText(Integer.toString(sCanopy.getCanopySize()));
            cSN.setText(sCanopy.getCanopySN());
            cDOM.setValue(sCanopy.getCanopyDOM());
            cJumps.setText(Integer.toString(sCanopy.getCanopyJumps()));
            cManufacturerName.getSelectionModel().select(sCanopy.getCanopyManufacturerID()-1);
        //Reserve
            rModel.setText(sReserve.getReserveModel());
            rSize.setText(Integer.toString(sReserve.getReserveSize()));
            rSN.setText(sReserve.getReserveSN());
            rDOM.setValue(sReserve.getReserveDOM());
            rManufacturerName.getSelectionModel().select(sReserve.getReserveManufacturerID()-1); 
            rJumps.setText(Integer.toString(sReserve.getReserveJumps()));
            rPackDate.setValue(sReserve.getReservePackDate()); 
        //AAD
            aModel.setText(sAAD.getAadModel());
            aSN.setText(sAAD.getAadSN());
            aDOM.setValue(sAAD.getAadDOM());
            aManufacturerName.getSelectionModel().select(sAAD.getAadManufacturerID()-1);
            aJumps.setText(Integer.toString(sAAD.getAadJumps()));
            aNextRegl.setValue(sAAD.getAadNextRegl());
            aSaved.setText(Integer.toString(sAAD.getAadSaved()));
        });
        saveBtn.setOnAction((ActionEvent event) -> {
        //First - check on fill, if something empty - abort saving, alert user
        //Container
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
        //Canopy
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
                if (!isAssembleInProcess() && !isNewSystem()){
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Внести изменения в систему "+ selectedSystem.getSystemCode() +"?");
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                        if (option.get() == null) {
                        } else if (option.get() == yes) {
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
                        //Nothing changed, do nothing
                            }else{
                                updParams = systemNewParams.get(0);
                                int i = systemNewParams.size()-1;
                                while (i>0){
                                    updParams = updParams.concat(", ").concat(systemNewParams.get(i--));
                                }
                                dr.editSkydiveSystem(selectedSystem, updParams);
                            }
                            systemNewParams.clear();
                    //Canopy
                            ArrayList <String> canopyNewParams = new ArrayList<>();
                            if (!cModel.getText().equals(sCanopy.getCanopyModel())){
                                canopyNewParams.add("canopy_model = "+"\""+cModel.getText()+"\"");
                            }
                            if (!cSize.getText().equals(Integer.toString(sCanopy.getCanopySize()))){
                                canopyNewParams.add("canopy_size = "+cSize.getText());
                            }
                            if (!cSN.getText().equals(sCanopy.getCanopySN())){
                                canopyNewParams.add("canopy_sn = "+"\""+cSN.getText()+"\"");
                            }
                            if (!cDOM.getValue().equals(sCanopy.getCanopyDOM())){
                                canopyNewParams.add("canopy_dom = "+"\'"+mySQLFormat.format(cDOM.getValue())+"\'");
                            }
                            if (cManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=sCanopy.getCanopyManufacturerID()){
                                canopyNewParams.add("manufacturerid = "+cManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID());
                            }
                            if (!cJumps.getText().equals(Integer.toString(sCanopy.getCanopyJumps()))){
                                canopyNewParams.add("canopy_jumps = "+cJumps.getText());
                            }
                        //Apply changes    
                            if (canopyNewParams.isEmpty()) {
                        //Nothing changed, do nothing
                            }else{
                                updParams = canopyNewParams.get(0);
                                int i = canopyNewParams.size()-1;
                                while (i>0){
                                    updParams = updParams.concat(", ").concat(canopyNewParams.get(i--));
                                }
                                dr.editCanopy(sCanopy, updParams);
                            }
                            canopyNewParams.clear();
                    //Reserve
                            ArrayList <String> reserveNewParams = new ArrayList<>();
                            if (!rModel.getText().equals(sReserve.getReserveModel())){
                                reserveNewParams.add("reserve_model = "+"\""+rModel.getText()+"\"");
                            }
                            if (!rSize.getText().equals(Integer.toString(sReserve.getReserveSize()))){
                                reserveNewParams.add("reserve_size = "+rSize.getText());
                            }
                            if (!rSN.getText().equals(sReserve.getReserveSN())){
                                reserveNewParams.add("reserve_sn = "+"\""+rSN.getText()+"\"");
                            }
                            if (!rDOM.getValue().equals(sReserve.getReserveDOM())){
                                reserveNewParams.add("reserve_dom = "+"\'"+mySQLFormat.format(rDOM.getValue())+"\'");
                            }
                            if (rManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=sReserve.getReserveManufacturerID()){
                                reserveNewParams.add("manufacturerid = "+rManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID());
                            }
                            if (!rJumps.getText().equals(Integer.toString(sReserve.getReserveJumps()))){
                                reserveNewParams.add("reserve_jumps = "+rJumps.getText());
                            }
                            if (!rPackDate.getValue().equals(sReserve.getReservePackDate())){
                                reserveNewParams.add("reserve_packdate = "+"\'"+mySQLFormat.format(rPackDate.getValue())+"\'");
                            }
                        //Apply changes    
                            if (reserveNewParams.isEmpty()) {
                        //Nothing changed, do nothing
                            }else{
                                updParams = reserveNewParams.get(0);
                                int i = reserveNewParams.size()-1;
                                while (i>0){
                                    updParams = updParams.concat(", ").concat(reserveNewParams.get(i--));
                                }
                                dr.editReserve(sReserve, updParams);
                            }
                            reserveNewParams.clear();
                    //AAD
                            ArrayList <String> aadNewParams = new ArrayList<>();
                            if (!aModel.getText().equals(sAAD.getAadModel())){
                                aadNewParams.add("aad_model = "+"\""+aModel.getText()+"\"");
                            }
                            if (!aSN.getText().equals(sAAD.getAadSN())){
                                aadNewParams.add("aad_sn = "+"\""+aSN.getText()+"\"");
                            }
                            if (!aDOM.getValue().equals(sAAD.getAadDOM())){
                                aadNewParams.add("aad_dom = "+"\'"+mySQLFormat.format(aDOM.getValue())+"\'");
                            }
                            if (aManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=sAAD.getAadManufacturerID()){
                                aadNewParams.add("manufacturerid = "+aManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID());
                            }
                            if (!aJumps.getText().equals(Integer.toString(sAAD.getAadJumps()))){
                                aadNewParams.add("aad_jumps = "+aJumps.getText());
                            }
                            if (!aNextRegl.getValue().equals(sAAD.getAadNextRegl())){
                                aadNewParams.add("aad_nextregl = "+"\'"+mySQLFormat.format(aNextRegl.getValue())+"\'");
                            }
                            if (!aSaved.getText().equals(Integer.toString(sAAD.getAadSaved()))){
                                aadNewParams.add("aad_saved = "+aSaved.getText());
                            }
                        //Apply changes    
                            if (aadNewParams.isEmpty()) {
                        //Nothing changed, do nothing
                            }else{
                                updParams = aadNewParams.get(0);
                                int i = aadNewParams.size()-1;
                                while (i>0){
                                    updParams = updParams.concat(", ").concat(aadNewParams.get(i--));
                                }
                                dr.editAAD(sAAD, updParams);
                            }
                            aadNewParams.clear();
                        } else if (option.get() == no) {
                            Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                            noChange.setTitle("Внимание!");
                            noChange.setHeaderText(null);
                            noChange.setContentText("Изменения не сохранены!");
                            noChange.showAndWait();
                        } else {
                            Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                            noChange.setTitle("Внимание!");
                            noChange.setHeaderText(null);
                            noChange.setContentText("Изменения не сохранены!");
                            noChange.showAndWait();
                        }
                }else if (isAssembleInProcess()){
            //assemble system - need to add check all elements on the place and prevent any changes
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Собрать систему "+ selectedSystem.getSystemCode() +"?");
                    confirm.setContentText("Купол ОП: " + getNewCanopy().getCanopyModel() +"-"+ getNewCanopy().getCanopySize()+"\n"+
                                           "Купол ПЗ: " + getNewReserve().getReserveModel() +"-"+ getNewReserve().getReserveSize() +"\n"+
                                           "Прибор: " + getNewAAD().getAadModel() +" № "+ getNewAAD().getAadSN());
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                        if (option.get() == null) {
                        } else if (option.get() == yes) {
                            dr.assembleSkydiveSystem(selectedSystem, getNewCanopy(), getNewReserve(), getNewAAD());
                            details.getScene().getWindow().hide();
                        } else if (option.get() == no) {
                            Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                            noChange.setTitle("Внимание!");
                            noChange.setHeaderText(null);
                            noChange.setContentText("Изменения не сохранены!");
                            noChange.showAndWait();
                        } else {
                            Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                            noChange.setTitle("Внимание!");
                            noChange.setHeaderText(null);
                            noChange.setContentText("Изменения не сохранены!");
                            noChange.showAndWait();
                        }
                }else{
                //Add new system    
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Добавить систему "+ selectedSystem.getSystemCode() +"?");
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                        if (option.get() == null) {
                        } else if (option.get() == yes) {
                    //Add new system
                            
                            
                        } else if (option.get() == no) {
                            Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                            noChange.setTitle("Внимание!");
                            noChange.setHeaderText(null);
                            noChange.setContentText("Изменения не сохранены!");
                            noChange.showAndWait();
                        } else {
                            Alert noChange = new Alert(Alert.AlertType.INFORMATION);
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
                //Container
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
                //Canopy
                if (!cModel.getText().equals(sCanopy.getCanopyModel())){
                    changed = true;
                }
                if (!cSize.getText().equals(Integer.toString(sCanopy.getCanopySize()))){
                    changed = true;
                }
                if (!cSN.getText().equals(sCanopy.getCanopySN())){
                    changed = true;
                }
                if (!cDOM.getValue().equals(sCanopy.getCanopyDOM())){
                    changed = true;
                }
                if (cManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=sCanopy.getCanopyManufacturerID()){
                    changed = true;
                }
                if (!cJumps.getText().equals(Integer.toString(sCanopy.getCanopyJumps()))){
                    changed = true;
                }
                //Reserve
                if (!rModel.getText().equals(sReserve.getReserveModel())){
                    changed = true;
                }
                if (!rSize.getText().equals(Integer.toString(sReserve.getReserveSize()))){
                    changed = true;
                }
                if (!rSN.getText().equals(sReserve.getReserveSN())){
                    changed = true;
                }
                if (!rDOM.getValue().equals(sReserve.getReserveDOM())){
                    changed = true;
                }
                if (rManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=sReserve.getReserveManufacturerID()){
                    changed = true;
                }
                if (!rJumps.getText().equals(Integer.toString(sReserve.getReserveJumps()))){
                    changed = true;
                }
                if (!rPackDate.getValue().equals(sReserve.getReservePackDate())){
                    changed = true;
                }
                //AAD
                if (!aModel.getText().equals(sAAD.getAadModel())){
                    changed = true;
                }
                if (!aSN.getText().equals(sAAD.getAadSN())){
                    changed = true;
                }
                if (!aDOM.getValue().equals(sAAD.getAadDOM())){
                    changed = true;
                }
                if (aManufacturerName.getSelectionModel().getSelectedItem().getManufacturerID()!=sAAD.getAadManufacturerID()){
                    changed = true;
                }
                if (!aJumps.getText().equals(Integer.toString(sAAD.getAadJumps()))){
                    changed = true;
                }
                if (!aNextRegl.getValue().equals(sAAD.getAadNextRegl())){
                    changed = true;
                }
                if (!aSaved.getText().equals(Integer.toString(sAAD.getAadSaved()))){
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
        
        details.add(containerGrid, 0, 1);
        details.add(canopyGrid, 1, 1);
        details.add(aadGrid, 0, 2);
        details.add(reserveGrid, 1, 2);
        details.add(buttonPane, 0, 3);
        details.add(closeBtn, 1, 3);
        details.setVgap(2);
        details.setHgap(2);
        details.setPadding(new Insets(5));
        
        Scene scene = new Scene(details);
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
