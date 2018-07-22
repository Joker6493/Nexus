/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

/**
 *
 * @author dboro
 */
public class StorageIndex extends Application {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private int stockID;
    private int status;
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane index = StorageIndex();
        Scene scene = new Scene(index);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public BorderPane StorageIndex () {
        BorderPane index = new BorderPane();
        DataRelay dr = new DataRelay();
        TableView<SkydiveSystem> indexStore = new TableView<>();
        //Columns
        TableColumn <SkydiveSystem, String> systemCode = new TableColumn<>("Код системы");
        TableColumn <SkydiveSystem, String> systemModel = new TableColumn<>("Модель системы");
        TableColumn <SkydiveSystem, String> canopy = new TableColumn<>("Основной парашют");
        TableColumn <SkydiveSystem, String> canopyModel = new TableColumn<>("Модель");
        TableColumn <SkydiveSystem, Integer> canopySize = new TableColumn<>("Размер, кв.фут");
        canopy.getColumns().addAll(canopyModel,canopySize);
        TableColumn <SkydiveSystem, String> reserve = new TableColumn<>("Запасной парашют");
        TableColumn <SkydiveSystem, String> reserveModel = new TableColumn<>("Модель");
        TableColumn <SkydiveSystem, Integer> reserveSize = new TableColumn<>("Размер, кв.фут");
        reserve.getColumns().addAll(reserveModel,reserveSize);
        TableColumn <SkydiveSystem, String> aadModel = new TableColumn<>("Модель AAD");
        TableColumn <SkydiveSystem, Integer> canopyJumps = new TableColumn<>("Прыжков на куполе");
        TableColumn <SkydiveSystem, LocalDate> reservePackDate = new TableColumn<>("Дата переукладки");
        //Adding columns into TableView
        indexStore.getColumns().addAll(systemCode,systemModel,canopy,reserve,aadModel,canopyJumps,reservePackDate);
        //Getting values and format from class variables
        systemCode.setCellValueFactory(new PropertyValueFactory<>("systemCode"));
        systemModel.setCellValueFactory(new PropertyValueFactory<>("systemModel"));
        canopyModel.setCellValueFactory(new PropertyValueFactory<>("canopyModel"));    
        canopySize.setCellValueFactory(new PropertyValueFactory<>("canopySize"));
        reserveModel.setCellValueFactory(new PropertyValueFactory<>("reserveModel"));
        reserveSize.setCellValueFactory(new PropertyValueFactory<>("reserveSize"));
        aadModel.setCellValueFactory(new PropertyValueFactory<>("aadModel"));        
        canopyJumps.setCellValueFactory(new PropertyValueFactory<>("canopyJumps"));        
        reservePackDate.setCellValueFactory(new PropertyValueFactory<>("reservePackDate"));
        // Custom rendering of the table cell.
        reservePackDate.setCellFactory(column -> {
            return new TableCell<SkydiveSystem, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(dateFormat.format(item));
                    }
                }
            };
        });
        //Adding data and create scene
        ObservableList<SkydiveSystem> indexList = dr.getSystemsList();
        indexStore.setItems(indexList);
        indexStore.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView SkydiveSystem object
                SkydiveSystem currentSystem = indexStore.getSelectionModel().getSelectedItem();
                //TODO list
                System.out.println("Выбрана система "+currentSystem.getSystemCode()+"!");
                SystemDetails detail = new SystemDetails(indexStore.getSelectionModel().getSelectedItem(), false);
                Stage detailStage = new Stage();
                detailStage.initModality(Modality.WINDOW_MODAL);
                detailStage.initOwner(index.getScene().getWindow());
                detail.start(detailStage);
            }
        });
        
        Button refreshBtn = new Button();
        refreshBtn.setText("Обновить");
        refreshBtn.setOnAction((ActionEvent event) -> {
            //Refreshing indexList - in process
            System.out.println("Идет обновление списка");
            //Some code here
            indexStore.getItems().clear();
            indexStore.setItems(dr.getSystemsList());
            //indexStore.refresh();
            System.out.println("Обновление списка завершено");
        });
        
        ComboBox <Stock> stockBox = new ComboBox<>();
        ObservableList<Stock> stockList = dr.getStockList();
        stockBox.setItems(stockList);
        stockBox.setCellFactory(p -> new ListCell <Stock> () {
            @Override
            protected void updateItem(Stock item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getStockName());
                } else {
                    setText(null);
                }
            }
        });
        stockBox.setButtonCell(new ListCell <Stock> () {
            @Override
            protected void updateItem(Stock item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getStockName());
                } else {
                    setText(null);
                }
            }
        });
        stockBox.getSelectionModel().select(1);
        stockID = stockBox.getSelectionModel().getSelectedItem().getStockID();
        stockBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //TODO code
            dr.setStock(stockBox.getSelectionModel().getSelectedItem().getStockID());
            stockID = stockBox.getSelectionModel().getSelectedItem().getStockID();
            System.out.println("Выбран склад "+ stockBox.getSelectionModel().getSelectedItem().getStockName() +"!");
        });
        
        ComboBox <Status> statusBox = new ComboBox<>();
        ObservableList<Status> statusList = dr.getStatusList();
        statusBox.setItems(statusList);
        statusBox.getSelectionModel().select(0);
        status = statusBox.getSelectionModel().getSelectedItem().getStatusID();
        statusBox.setCellFactory(p -> new ListCell <Status> () {
            @Override
            protected void updateItem(Status item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getStatusName());
                } else {
                    setText(null);
                }
            }
        });
        statusBox.setButtonCell(new ListCell <Status> () {
            @Override
            protected void updateItem(Status item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getStatusName());
                } else {
                    setText(null);
                }
            }
        });
        statusBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //TODO code
            dr.setStatus(statusBox.getSelectionModel().getSelectedItem().getStatusID());
            status = statusBox.getSelectionModel().getSelectedItem().getStatusID();
            System.out.println("Выбран статус "+ statusBox.getSelectionModel().getSelectedItem().getStatusName() +"!");
        });
        Button elementBtn = new Button("Элементы системы");
        elementBtn.setOnAction((ActionEvent event) -> {
            ElementsIndex detail = new ElementsIndex();
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            try {
                detail.start(detailStage);
            } catch (SQLException ex) {
            }
        });
        
        HBox storageBar = new HBox();
        storageBar.getChildren().addAll(stockBox, new Label("Склад"), statusBox, new Label("Статус системы"), refreshBtn, elementBtn);
        storageBar.setPadding(new Insets(20));
        index.setTop(storageBar);
        
        ContextMenu storageContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        refreshList.setOnAction((ActionEvent e) -> {
            System.out.println("Идет обновление списка");
            indexStore.getItems().clear();
            indexStore.setItems(dr.getSystemsList());
            System.out.println("Обновление списка завершено");
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            SkydiveSystem currentSystem = indexStore.getSelectionModel().getSelectedItem();
            System.out.println("Редактируем систему "+currentSystem.getSystemCode()+"?");
            SystemDetails detail = new SystemDetails(indexStore.getSelectionModel().getSelectedItem(), true);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem disassembleItem = new MenuItem("Разобрать");
        disassembleItem.setOnAction((ActionEvent e) -> {
            SkydiveSystem currentSystem = indexStore.getSelectionModel().getSelectedItem();
            System.out.println("Разобрать систему "+currentSystem.getSystemCode()+"?");
            dr.disassembleSkydiveSystem(currentSystem);
            System.out.println("Система разобрана!");
        });
        MenuItem moveItem = new MenuItem("Переместить");
        moveItem.setOnAction((ActionEvent e) -> {
            //in process
            SkydiveSystem currentSystem = indexStore.getSelectionModel().getSelectedItem();
            System.out.println("Переместить систему "+currentSystem.getSystemCode()+"?");
            String stockNew = "stockid = ";
            //dr.editSkydiveSystem(currentSystem, "");
            System.out.println("Система перемещена!");
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить систему?");
            SystemDetails detail = new SystemDetails(stockBox.getSelectionModel().getSelectedItem().getStockID());
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            SkydiveSystem currentSystem = indexStore.getSelectionModel().getSelectedItem();
            System.out.println("Удалить систему "+currentSystem.getSystemCode()+"?");
            dr.deleteSkydiveSystem(currentSystem);
            System.out.println("Система удалена!");
        });
        storageContextMenu.getItems().addAll(refreshList, new SeparatorMenuItem(), addItem, editItem, moveItem, disassembleItem, deleteItem);
        indexStore.setOnContextMenuRequested((ContextMenuEvent event) -> {
            storageContextMenu.show(indexStore, event.getScreenX(), event.getScreenY());
        });
        
        index.setCenter(indexStore);
        
        Button closeBtn = new Button();
        closeBtn.setText("Закрыть");
        closeBtn.setOnAction((ActionEvent event) -> {
            //Some code here
            System.out.println("Закрыть?");
        });
        
        return index;
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
