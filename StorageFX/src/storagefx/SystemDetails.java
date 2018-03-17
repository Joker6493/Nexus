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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author dboro
 */
public class SystemDetails extends Application {
    private final SkydiveSystem selectedSystem;
    private boolean editStatus;
    private String stageTitle;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    SystemDetails (SkydiveSystem selectedSystem, boolean editStatus){
        this.selectedSystem = selectedSystem;
        this.stageTitle = "Система "+selectedSystem.getSystemCode();
        this.editStatus = editStatus;
    }
    
    SystemDetails (){
        this.selectedSystem = new SkydiveSystem(0, "", "", "", LocalDate.now(), 0, "", 0, 0, "", 0, "", LocalDate.now(), 0, 0, "", 0, "", 0, "", LocalDate.now(), 0, LocalDate.now(), 0, "", 0, "", "", LocalDate.now(), 0, LocalDate.now(), 0, 0, "");
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
        Label sModelLabel = new Label("Модель: ");
        TextField sModel = new TextField(selectedSystem.getSystemModel());
        sModel.setEditable(editStatus);
        Label sSNLabel = new Label("Серийный номер: ");
        TextField sSN = new TextField(selectedSystem.getSystemSN());
        sSN.setEditable(editStatus);
        Label sDOMLabel = new Label("Дата производства: ");
        TextField sDOM = new TextField(dateFormat.format(selectedSystem.getSystemDOM()));
        sDOM.setEditable(editStatus);
        Label sManufacturerNameLabel = new Label("Производитель: ");
        TextField sManufacturerName = new TextField(selectedSystem.getSystemManufacturerName());
        sManufacturerName.setEditable(editStatus);
        containerGrid.add(containerGridName, 0, 0);
        containerGrid.add(sModelLabel, 0, 1);
        containerGrid.add(sModel, 1, 1);
        containerGrid.add(sSNLabel, 0, 2);
        containerGrid.add(sSN, 1, 2);
        containerGrid.add(sDOMLabel, 0, 3);
        containerGrid.add(sDOM, 1, 3);
        containerGrid.add(sManufacturerNameLabel, 0, 4);
        containerGrid.add(sManufacturerName, 1, 4);
        containerGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(10), new BorderWidths(1.5), new Insets(5))));        
        containerGrid.setPadding(new Insets(5));
        
        //Canopy
        GridPane canopyGrid = new GridPane();
        //String canopyModel, int canopySize, String canopySN, String canopyDOM, int canopyJumps, String canopyManufacturerName
        Label canopyGridName = new Label("Основной парашют");
        Label cModelLabel = new Label("Модель: ");
        TextField cModel = new TextField(selectedSystem.getCanopyModel());
        cModel.setEditable(editStatus);
        Label cSizeLabel = new Label ("Размер купола, кв.фут: ");
        TextField cSize = new TextField (Integer.toString(selectedSystem.getCanopySize()));
        cSize.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,4}")) {
                cSize.setText(oldValue);
            }
        });
        cSize.setEditable(editStatus);
        Label cSNLabel = new Label("Серийный номер: ");
        TextField cSN = new TextField(selectedSystem.getCanopySN());
        cSN.setEditable(editStatus);
        Label cDOMLabel = new Label("Дата производства: ");
        TextField cDOM = new TextField(dateFormat.format(selectedSystem.getCanopyDOM()));
        cDOM.setEditable(editStatus);
        Label cManufacturerNameLabel = new Label("Производитель: ");
        TextField cManufacturerName = new TextField(selectedSystem.getCanopyManufacturerName());
        cManufacturerName.setEditable(editStatus);
        Label cJumpsLabel = new Label ("Прыжков: ");
        TextField cJumps = new TextField (Integer.toString(selectedSystem.getCanopyJumps()));
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
        canopyGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(10), new BorderWidths(1.5), new Insets(5))));        
        canopyGrid.setPadding(new Insets(5));
        
        //Reserve
        GridPane reserveGrid = new GridPane();
        //int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int reserveJumps, String reservePackDate, int reserveManufacturerID, String reserveManufacturerName
        Label reserveGridName = new Label("Запасной парашют");
        Label rModelLabel = new Label("Модель: ");
        TextField rModel = new TextField(selectedSystem.getReserveModel());
        rModel.setEditable(editStatus);
        Label rSizeLabel = new Label ("Размер купола, кв.фут: ");
        TextField rSize = new TextField (Integer.toString(selectedSystem.getReserveSize()));
        rSize.setEditable(editStatus);
        Label rSNLabel = new Label("Серийный номер: ");
        TextField rSN = new TextField(selectedSystem.getReserveSN());
        rSN.setEditable(editStatus);
        Label rDOMLabel = new Label("Дата производства: ");
        TextField rDOM = new TextField(dateFormat.format(selectedSystem.getReserveDOM()));
        rDOM.setEditable(editStatus);
        Label rManufacturerNameLabel = new Label("Производитель: ");
        TextField rManufacturerName = new TextField(selectedSystem.getReserveManufacturerName());
        rManufacturerName.setEditable(editStatus);
        Label rJumpsLabel = new Label ("Применений: ");
        TextField rJumps = new TextField (Integer.toString(selectedSystem.getReserveJumps()));
        rJumps.setEditable(editStatus);
        Label rPackDateLabel = new Label ("Дата укладки: ");
        TextField rPackDate = new TextField (dateFormat.format(selectedSystem.getReservePackDate()));
        rPackDate.setEditable(editStatus);
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
        reserveGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(10), new BorderWidths(1.5), new Insets(5))));        
        reserveGrid.setPadding(new Insets(5));
        
        //AAD
        GridPane aadGrid = new GridPane();
        //int aadID, String aadModel, String aadSN, String aadDOM, int aadJumps, String aadNextRegl, boolean aadFired, int aadManufacturerID, String aadManufacturerName
        Label aadGridName = new Label("Страхующий прибор");
        Label aModelLabel = new Label("Модель: ");
        TextField aModel = new TextField(selectedSystem.getAadModel());
        aModel.setEditable(editStatus);
        Label aSNLabel = new Label("Серийный номер: ");
        TextField aSN = new TextField(selectedSystem.getAadSN());
        aSN.setEditable(editStatus);
        Label aDOMLabel = new Label("Дата производства: ");
        TextField aDOM = new TextField(dateFormat.format(selectedSystem.getAadDOM()));
        aDOM.setEditable(editStatus);
        Label aManufacturerNameLabel = new Label("Производитель: ");
        TextField aManufacturerName = new TextField(selectedSystem.getAadManufacturerName());
        aManufacturerName.setEditable(editStatus);
        Label aJumpsLabel = new Label ("Прыжков: ");
        TextField aJumps = new TextField (Integer.toString(selectedSystem.getAadJumps()));
        aJumps.setEditable(editStatus);
        Label aNextReglLabel = new Label ("Дата следующего регламента: ");
        TextField aNextRegl = new TextField (dateFormat.format(selectedSystem.getAadNextRegl()));
        aNextRegl.setEditable(editStatus);
        Label aFiredLabel = new Label ("Количество применений: ");
        TextField aFired = new TextField (Integer.toString(selectedSystem.getAadFired()));
        aFired.setEditable(editStatus);
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
        aadGrid.add(aFiredLabel, 0, 7);
        aadGrid.add(aFired, 1, 7);
        aadGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(10), new BorderWidths(1.5), new Insets(5))));        
        aadGrid.setPadding(new Insets(5));
        
        Button saveBtn = new Button("Сохранить");
        Button cancelBtn = new Button("Отменить");
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
                cModel.setEditable(editStatus);
                cSize.setEditable(editStatus);
                cSN.setEditable(editStatus);
                cDOM.setEditable(editStatus);
                cManufacturerName.setEditable(editStatus);
                cJumps.setEditable(editStatus);
                rModel.setEditable(editStatus);
                rSize.setEditable(editStatus);
                rSN.setEditable(editStatus);
                rDOM.setEditable(editStatus);
                rManufacturerName.setEditable(editStatus);
                rJumps.setEditable(editStatus);
                rPackDate.setEditable(editStatus);
                aModel.setEditable(editStatus);
                aSN.setEditable(editStatus);
                aDOM.setEditable(editStatus);
                aManufacturerName.setEditable(editStatus);
                aJumps.setEditable(editStatus);
                aNextRegl.setEditable(editStatus);
                aFired.setEditable(editStatus);
            }else{
                editStatus = false;
                sModel.setEditable(editStatus);
                sSN.setEditable(editStatus);
                sDOM.setEditable(editStatus);
                sManufacturerName.setEditable(editStatus);
                cModel.setEditable(editStatus);
                cSize.setEditable(editStatus);
                cSN.setEditable(editStatus);
                cDOM.setEditable(editStatus);
                cManufacturerName.setEditable(editStatus);
                cJumps.setEditable(editStatus);
                rModel.setEditable(editStatus);
                rSize.setEditable(editStatus);
                rSN.setEditable(editStatus);
                rDOM.setEditable(editStatus);
                rManufacturerName.setEditable(editStatus);
                rJumps.setEditable(editStatus);
                rPackDate.setEditable(editStatus);
                aModel.setEditable(editStatus);
                aSN.setEditable(editStatus);
                aDOM.setEditable(editStatus);
                aManufacturerName.setEditable(editStatus);
                aJumps.setEditable(editStatus);
                aNextRegl.setEditable(editStatus);
                aFired.setEditable(editStatus);
            }             
        });
        Button closeBtn = new Button("Закрыть");
        closeBtn.setOnAction((ActionEvent event) -> {
            //some code here, if there are some changes, ask for save them, then close window, if not - close window
        });
        
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(saveBtn, cancelBtn);
        
        details.add(editBtn, 1, 0);
        details.add(canopyGrid, 0, 1);
        details.add(reserveGrid, 0, 2);
        details.add(containerGrid, 1, 1);
        details.add(aadGrid, 1, 2);
        details.add(buttonPane, 0, 3);
        details.add(closeBtn, 1, 3);
        details.setVgap(5);
        details.setHgap(5);
        
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
