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
import javafx.scene.control.ToggleButton;
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
    private String stageTitle;
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
    private String updParams;
    private DataRelay dr = new DataRelay();
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
    }
    
    SystemDetails (int stockID){
        this.selectedSystem = new SkydiveSystem(0, "", "", "", LocalDate.now(), 0, "", stockID, 0, "", 0, "", LocalDate.now(), 0, 0, "", 0, "", 0, "", LocalDate.now(), 0, LocalDate.now(), 0, "", 0, "", "", LocalDate.now(), 0, LocalDate.now(), 0, 0, "");
        this.stageTitle = "Добавление новой системы";
        this.editStatus = true;
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
            if (newValue.length()>6) {
                sCode.setText(oldValue);
            }
        });
        sCode.setEditable(editStatus);
        Label sModelLabel = new Label("Модель: ");
        TextField sModel = new TextField(selectedSystem.getSystemModel());
        sModel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length()>50) {
                sModel.setText(oldValue);
            }
        });
        sModel.setEditable(editStatus);
        Label sSNLabel = new Label("Серийный номер: ");
        TextField sSN = new TextField(selectedSystem.getSystemSN());
        sSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
            if (newValue.length()>50) {
                cModel.setText(oldValue);
            }
        });
        cModel.setEditable(editStatus);
        Label cSizeLabel = new Label ("Размер купола, кв.фут: ");
        TextField cSize = new TextField (Integer.toString(sCanopy.getCanopySize()));
        cSize.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                cSize.setText(oldValue);
            }
        });
        cSize.setEditable(editStatus);
        Label cSNLabel = new Label("Серийный номер: ");
        TextField cSN = new TextField(sCanopy.getCanopySN());
        cSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
        cManufacturerName.getSelectionModel().select(sCanopy.getCanopyManufacturerID()-1);
        cManufacturerName.setDisable(!editStatus);
        Label cJumpsLabel = new Label ("Прыжков: ");
        TextField cJumps = new TextField (Integer.toString(sCanopy.getCanopyJumps()));
        cJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
            Scene cList = new Scene(cl.CanopyTable(true));
            chooseWindow.setScene(cList);
            
            chooseWindow.initModality(Modality.WINDOW_MODAL);
            chooseWindow.initOwner(detailStage.getScene().getWindow());
            chooseWindow.showAndWait();
            if (cl.getSelectedCanopy() != null){
                Canopy newCanopy = cl.getSelectedCanopy();
                cModel.setText(newCanopy.getCanopyModel());
                cSize.setText(Integer.toString(newCanopy.getCanopySize()));
                cSN.setText(newCanopy.getCanopySN());
                cDOM.setValue(newCanopy.getCanopyDOM());
                cJumps.setText(Integer.toString(newCanopy.getCanopyJumps()));
                cManufacturerName.getSelectionModel().select(newCanopy.getCanopyManufacturerID()-1);
            //Updating canopy data in skydive system
                selectedSystem.setCanopyModel(newCanopy.getCanopyModel());
                selectedSystem.setCanopySize(newCanopy.getCanopySize());
                selectedSystem.setCanopySN(newCanopy.getCanopySN());
                selectedSystem.setCanopyDOM(newCanopy.getCanopyDOM());
                selectedSystem.setCanopyJumps(newCanopy.getCanopyJumps());
                selectedSystem.setCanopyManufacturerID(newCanopy.getCanopyManufacturerID());
                selectedSystem.setCanopyManufacturerName(newCanopy.getCanopyManufacturerName());
                sCanopy.setCanopyModel(newCanopy.getCanopyModel());
                sCanopy.setCanopySize(newCanopy.getCanopySize());
                sCanopy.setCanopySN(newCanopy.getCanopySN());
                sCanopy.setCanopyDOM(newCanopy.getCanopyDOM());
                sCanopy.setCanopyJumps(newCanopy.getCanopyJumps());
                sCanopy.setCanopyManufacturerID(newCanopy.getCanopyManufacturerID());
                sCanopy.setCanopyManufacturerName(newCanopy.getCanopyManufacturerName());
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
            if (newValue.length()>50) {
                rModel.setText(oldValue);
            }
        });
        rModel.setEditable(editStatus);
        Label rSizeLabel = new Label ("Размер купола, кв.фут: ");
        TextField rSize = new TextField (Integer.toString(sReserve.getReserveSize()));
        rSize.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                rSize.setText(oldValue);
            }
        });
        rSize.setEditable(editStatus);
        Label rSNLabel = new Label("Серийный номер: ");
        TextField rSN = new TextField(sReserve.getReserveSN());
        rSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
        rManufacturerName.getSelectionModel().select(sReserve.getReserveManufacturerID()-1);
        rManufacturerName.setDisable(!editStatus);
        Label rJumpsLabel = new Label ("Применений: ");
        TextField rJumps = new TextField (Integer.toString(sReserve.getReserveJumps()));
        rJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
            Scene rList = new Scene(rl.ReserveTable(true));
            chooseWindow.setScene(rList);
            
            chooseWindow.initModality(Modality.WINDOW_MODAL);
            chooseWindow.initOwner(detailStage.getScene().getWindow());
            chooseWindow.showAndWait();
            if (rl.getSelectedReserve() != null){
                Reserve newReserve = rl.getSelectedReserve();
                rModel.setText(newReserve.getReserveModel());
                rSize.setText(Integer.toString(newReserve.getReserveSize()));
                rSN.setText(newReserve.getReserveSN());
                rDOM.setValue(newReserve.getReserveDOM());
                rManufacturerName.getSelectionModel().select(newReserve.getReserveManufacturerID()-1); 
                rJumps.setText(Integer.toString(newReserve.getReserveJumps()));
                rPackDate.setValue(newReserve.getReservePackDate());  
            //Updating reserve data in skydive system
                selectedSystem.setReserveModel(newReserve.getReserveModel());
                selectedSystem.setReserveSize(newReserve.getReserveSize());
                selectedSystem.setReserveSN(newReserve.getReserveSN());
                selectedSystem.setReserveDOM(newReserve.getReserveDOM());
                selectedSystem.setReserveJumps(newReserve.getReserveJumps());
                selectedSystem.setReservePackDate(newReserve.getReservePackDate());
                selectedSystem.setReserveManufacturerID(newReserve.getReserveManufacturerID());
                selectedSystem.setReserveManufacturerName(newReserve.getReserveManufacturerName());
                sReserve.setReserveModel(newReserve.getReserveModel());
                sReserve.setReserveSize(newReserve.getReserveSize());
                sReserve.setReserveSN(newReserve.getReserveSN());
                sReserve.setReserveDOM(newReserve.getReserveDOM());
                sReserve.setReserveJumps(newReserve.getReserveJumps());
                sReserve.setReservePackDate(newReserve.getReservePackDate());
                sReserve.setReserveManufacturerID(newReserve.getReserveManufacturerID());
                sReserve.setReserveManufacturerName(newReserve.getReserveManufacturerName());
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
            if (newValue.length()>50) {
                aModel.setText(oldValue);
            }
        });
        aModel.setEditable(editStatus);
        Label aSNLabel = new Label("Серийный номер: ");
        TextField aSN = new TextField(sAAD.getAadSN());
        aSN.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
        aManufacturerName.getSelectionModel().select(sAAD.getAadManufacturerID()-1);
        aManufacturerName.setDisable(!editStatus);
        Label aJumpsLabel = new Label ("Прыжков: ");
        TextField aJumps = new TextField (Integer.toString(sAAD.getAadJumps()));
        aJumps.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
            if(!aNextRegl.isEditable()){
                aNextRegl.hide();
            }
        });
        
        Label aSavedLabel = new Label ("Количество применений: ");
        TextField aSaved = new TextField (Integer.toString(sAAD.getAadSaved()));
        aSaved.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
            Scene aList = new Scene(al.AADTable(true));
            chooseWindow.setScene(aList);
            
            chooseWindow.initModality(Modality.WINDOW_MODAL);
            chooseWindow.initOwner(detailStage.getScene().getWindow());
            chooseWindow.showAndWait();
            if (al.getSelectedAAD() != null){
                AAD newAAD = al.getSelectedAAD();
                aModel.setText(newAAD.getAadModel());
                aSN.setText(newAAD.getAadSN());
                aDOM.setValue(newAAD.getAadDOM());
                aManufacturerName.getSelectionModel().select(newAAD.getAadManufacturerID()-1);
                aJumps.setText(Integer.toString(newAAD.getAadJumps()));
                aNextRegl.setValue(newAAD.getAadNextRegl());
                aSaved.setText(Integer.toString(newAAD.getAadSaved()));
            //Updating aad data in skydive system
                selectedSystem.setAadModel(newAAD.getAadModel());
                selectedSystem.setAadSN(newAAD.getAadSN());
                selectedSystem.setAadDOM(newAAD.getAadDOM());
                selectedSystem.setAadJumps(newAAD.getAadJumps());
                selectedSystem.setAadNextRegl(newAAD.getAadNextRegl());
                selectedSystem.setAadSaved(newAAD.getAadSaved());
                selectedSystem.setAadManufacturerID(newAAD.getAadManufacturerID());
                selectedSystem.setAadManufacturerName(newAAD.getAadManufacturerName());
                sAAD.setAadModel(newAAD.getAadModel());
                sAAD.setAadSN(newAAD.getAadSN());
                sAAD.setAadDOM(newAAD.getAadDOM());
                sAAD.setAadJumps(newAAD.getAadJumps());
                sAAD.setAadNextRegl(newAAD.getAadNextRegl());
                sAAD.setAadSaved(newAAD.getAadSaved());
                sAAD.setAadManufacturerID(newAAD.getAadManufacturerID());
                sAAD.setAadManufacturerName(newAAD.getAadManufacturerName());
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

        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setCancelButton(true);
        closeBtn.setOnAction((ActionEvent event) -> {
            if (editStatus == true){
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
