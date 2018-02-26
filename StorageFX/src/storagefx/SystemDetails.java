/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author dboro
 */
public class SystemDetails extends Application {
    private SkydiveSystem selectedSystem;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    SystemDetails (SkydiveSystem selectedSystem){
        this.selectedSystem = selectedSystem;
    }
    @Override
    public void start(Stage detailStage) {
        //SkydiveSystem (int systemID, String systemCode, String systemModel, String systemSN, String systemDOM, int systemManufacturerID, String systemManufacturerName, int canopyID, String canopyModel, int canopySize, String canopySN, String canopyDOM, int canopyJumps, int canopyManufacturerID, String canopyManufacturerName, int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int reserveJumps, String reservePackDate, int reserveManufacturerID, String reserveManufacturerName, int aadID, String aadModel, String aadSN, String aadDOM, int aadJumps, String aadNextRegl, boolean aadFired, int aadManufacturerID, String aadManufacturerName)
        //Container
        GridPane containerGrid = new GridPane();
        //String systemModel, String systemSN, String systemDOM, String systemManufacturerName
        Label containerGridName = new Label("Ранец и подвесная система");
        Label sModelLabel = new Label("Модель: ");
        Label sModel = new Label(selectedSystem.getSystemModel());
        Label sSNLabel = new Label("Серийный номер: ");
        Label sSN = new Label(selectedSystem.getSystemSN());
        Label sDOMLabel = new Label("Дата производства: ");
        Label sDOM = new Label(dateFormat.format(selectedSystem.getSystemDOM()));
        Label sManufacturerNameLabel = new Label("Производитель: ");
        Label sManufacturerName = new Label(selectedSystem.getSystemManufacturerName());
        containerGrid.setConstraints(containerGridName, 0, 0);
        containerGrid.setConstraints(sModelLabel, 0, 1);
        containerGrid.setConstraints(sModel, 1, 1);
        containerGrid.setConstraints(sSNLabel, 0, 2);
        containerGrid.setConstraints(sSN, 1, 2);
        containerGrid.setConstraints(sDOMLabel, 0, 3);
        containerGrid.setConstraints(sDOM, 1, 3);
        containerGrid.setConstraints(sManufacturerNameLabel, 0, 4);
        containerGrid.setConstraints(sManufacturerName, 1, 4);
        containerGrid.getChildren().addAll(containerGridName, sModelLabel, sModel, sSNLabel, sSN, sDOMLabel, sDOM, sManufacturerNameLabel, sManufacturerName);
        containerGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(15), new BorderWidths(1.5), new Insets(5))));        
        
        //Canopy
        GridPane canopyGrid = new GridPane();
        //String canopyModel, int canopySize, String canopySN, String canopyDOM, int canopyJumps, String canopyManufacturerName
        Label canopyGridName = new Label("Основной парашют");
        Label cModelLabel = new Label("Модель: ");
        Label cModel = new Label(selectedSystem.getCanopyModel());
        Label cSizeLabel = new Label ("Размер купола, кв.фут: ");
        Label cSize = new Label (Integer.toString(selectedSystem.getCanopySize()));
        Label cSNLabel = new Label("Серийный номер: ");
        Label cSN = new Label(selectedSystem.getCanopySN());
        Label cDOMLabel = new Label("Дата производства: ");
        Label cDOM = new Label(dateFormat.format(selectedSystem.getCanopyDOM()));
        Label cManufacturerNameLabel = new Label("Производитель: ");
        Label cManufacturerName = new Label(selectedSystem.getCanopyManufacturerName());
        Label cJumpsLabel = new Label ("Прыжков: ");
        Label cJumps = new Label (Integer.toString(selectedSystem.getCanopyJumps()));
        canopyGrid.setConstraints(canopyGridName, 0, 0);
        canopyGrid.setConstraints(cModelLabel, 0, 1);
        canopyGrid.setConstraints(cModel, 1, 1);
        canopyGrid.setConstraints(cSizeLabel, 0, 2);
        canopyGrid.setConstraints(cSize, 1, 2);
        canopyGrid.setConstraints(cSNLabel, 0, 3);
        canopyGrid.setConstraints(cSN, 1, 3);
        canopyGrid.setConstraints(cDOMLabel, 0, 4);
        canopyGrid.setConstraints(cDOM, 1, 4);
        canopyGrid.setConstraints(cManufacturerNameLabel, 0, 5);
        canopyGrid.setConstraints(cManufacturerName, 1, 5);
        canopyGrid.setConstraints(cJumpsLabel, 0, 6);
        canopyGrid.setConstraints(cJumps, 1, 6);
        canopyGrid.getChildren().addAll(canopyGridName, cModelLabel, cModel, cSizeLabel, cSize, cSNLabel, cSN, cDOMLabel, cDOM, cManufacturerNameLabel, cManufacturerName, cJumpsLabel, cJumps);
        
        //Reserve
        GridPane reserveGrid = new GridPane();
        //int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int reserveJumps, String reservePackDate, int reserveManufacturerID, String reserveManufacturerName
        Label reserveGridName = new Label("Запасной парашют");
        Label rModelLabel = new Label("Модель: ");
        Label rModel = new Label(selectedSystem.getReserveModel());
        Label rSizeLabel = new Label ("Размер купола, кв.фут: ");
        Label rSize = new Label (Integer.toString(selectedSystem.getReserveSize()));
        Label rSNLabel = new Label("Серийный номер: ");
        Label rSN = new Label(selectedSystem.getReserveSN());
        Label rDOMLabel = new Label("Дата производства: ");
        Label rDOM = new Label(dateFormat.format(selectedSystem.getReserveDOM()));
        Label rManufacturerNameLabel = new Label("Производитель: ");
        Label rManufacturerName = new Label(selectedSystem.getReserveManufacturerName());
        Label rJumpsLabel = new Label ("Применений: ");
        Label rJumps = new Label (Integer.toString(selectedSystem.getReserveJumps()));
        Label rPackDateLabel = new Label ("Дата укладки: ");
        Label rPackDate = new Label (dateFormat.format(selectedSystem.getReservePackDate()));
        reserveGrid.setConstraints(reserveGridName, 0, 0);
        reserveGrid.setConstraints(rModelLabel, 0, 1);
        reserveGrid.setConstraints(rModel, 1, 1);
        reserveGrid.setConstraints(rSizeLabel, 0, 2);
        reserveGrid.setConstraints(rSize, 1, 2);
        reserveGrid.setConstraints(rSNLabel, 0, 3);
        reserveGrid.setConstraints(rSN, 1, 3);
        reserveGrid.setConstraints(rDOMLabel, 0, 4);
        reserveGrid.setConstraints(rDOM, 1, 4);
        reserveGrid.setConstraints(rManufacturerNameLabel, 0, 5);
        reserveGrid.setConstraints(rManufacturerName, 1, 5);
        reserveGrid.setConstraints(rJumpsLabel, 0, 6);
        reserveGrid.setConstraints(rJumps, 1, 6);
        reserveGrid.setConstraints(rPackDateLabel, 0, 7);
        reserveGrid.setConstraints(rPackDate, 1, 7);
        reserveGrid.getChildren().addAll(reserveGridName, rModelLabel, rModel, rSizeLabel, rSize, rSNLabel, rSN, rDOMLabel, rDOM, rManufacturerNameLabel, rManufacturerName, rJumpsLabel, rJumps, rPackDateLabel, rPackDate);
        
        //AAD
        GridPane aadGrid = new GridPane();
        //int aadID, String aadModel, String aadSN, String aadDOM, int aadJumps, String aadNextRegl, boolean aadFired, int aadManufacturerID, String aadManufacturerName
        Label aadGridName = new Label("Страхующий прибор");
        Label aModelLabel = new Label("Модель: ");
        Label aModel = new Label(selectedSystem.getAadModel());
        Label aSNLabel = new Label("Серийный номер: ");
        Label aSN = new Label(selectedSystem.getAadSN());
        Label aDOMLabel = new Label("Дата производства: ");
        Label aDOM = new Label(dateFormat.format(selectedSystem.getAadDOM()));
        Label aManufacturerNameLabel = new Label("Производитель: ");
        Label aManufacturerName = new Label(selectedSystem.getAadManufacturerName());
        Label aJumpsLabel = new Label ("Прыжков: ");
        Label aJumps = new Label (Integer.toString(selectedSystem.getAadJumps()));
        Label aNextReglLabel = new Label ("Дата следующего регламента: ");
        Label aNextRegl = new Label (dateFormat.format(selectedSystem.getAadNextRegl()));
        aadGrid.setConstraints(aadGridName, 0, 0);
        aadGrid.setConstraints(aModelLabel, 0, 1);
        aadGrid.setConstraints(aModel, 1, 1);
        aadGrid.setConstraints(aSNLabel, 0, 2);
        aadGrid.setConstraints(aSN, 1, 2);
        aadGrid.setConstraints(aDOMLabel, 0, 3);
        aadGrid.setConstraints(aDOM, 1, 3);
        aadGrid.setConstraints(aManufacturerNameLabel, 0, 4);
        aadGrid.setConstraints(aManufacturerName, 1, 4);
        aadGrid.setConstraints(aJumpsLabel, 0, 5);
        aadGrid.setConstraints(aJumps, 1, 5);
        aadGrid.setConstraints(aNextReglLabel, 0, 6);
        aadGrid.setConstraints(aNextRegl, 1, 6);
        aadGrid.getChildren().addAll(aadGridName, aModelLabel, aModel, aSNLabel, aSN, aDOMLabel, aDOM, aManufacturerNameLabel, aManufacturerName, aJumpsLabel, aJumps, aNextReglLabel, aNextRegl);
        
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
            StorageIndex.launch();
        });
        GridPane details = new GridPane();
        details.setConstraints(canopyGrid, 0, 1);
        details.setConstraints(reserveGrid, 0, 2);
        details.setConstraints(containerGrid, 1, 1);
        details.setConstraints(aadGrid, 1, 2);
        details.getChildren().addAll(canopyGrid, reserveGrid, containerGrid, aadGrid);
        //details.setGridLinesVisible(true);
        details.setVgap(10);
        details.setHgap(10);
        
        Scene scene = new Scene(details);
        detailStage.setTitle("Система "+selectedSystem.getSystemCode());
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
