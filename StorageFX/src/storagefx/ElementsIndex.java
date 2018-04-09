/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

/**
 *
 * @author dboro
 */
public class ElementsIndex extends Application {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private int stockID;
    private int status;
    @Override
    public void start(Stage primaryStage) throws SQLException {
        
        BorderPane index = ElementsIndex();
        Scene scene = new Scene(index);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public BorderPane ElementsIndex(){
        BorderPane index = new BorderPane();
        HBox topMenu = new HBox();
        DataRelay dr = new DataRelay();
        ComboBox <String> elementsBox = new ComboBox<>();
        ArrayList<String> indexList = new ArrayList<>();
        indexList.add("Ранцы");
        indexList.add("Основные парашюты");
        indexList.add("Запасные парашюты");
        indexList.add("Страхующие приборы");
        ObservableList<String> elementsList = FXCollections.observableList(indexList);
        elementsBox.setItems(elementsList);
        elementsBox.getSelectionModel().select(0);
        Button refreshBtn = new Button();
        refreshBtn.setText("Обновить");
        refreshBtn.setOnAction((ActionEvent event) -> {
            //Refreshing indexList - in process
            System.out.println("Идет обновление списка");
            //Some code here
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
        topMenu.getChildren().addAll(elementsBox, new Label("Элементы системы"), stockBox, new Label("Склад"), statusBox, new Label("Статус системы"), refreshBtn);
        index.setTop(topMenu);
        switch (elementsBox.getSelectionModel().getSelectedItem()){
            case "Ранцы":
                index.setCenter(ContainerTable());
                break;
            case "Основные парашюты":
                index.setCenter(CanopyTable());
                break;
            case "Запасные парашюты":
                index.setCenter(ReserveTable());
                break;
            case "Страхующие приборы": 
                index.setCenter(AADTable());
                break;
        }
        
        return index;
    }
    
    public StackPane ContainerTable(){
        StackPane index = new StackPane();
        DataRelay dr = new DataRelay();
        TableView<SkydiveSystem> containerTable = new TableView<>();
        //Columns
        TableColumn <SkydiveSystem, String> systemCode = new TableColumn<>("Код системы");
        TableColumn <SkydiveSystem, String> systemModel = new TableColumn<>("Модель системы");
        TableColumn <SkydiveSystem, String> systemSN = new TableColumn<>("Серийный номер");
        TableColumn <SkydiveSystem, LocalDate> systemDOM = new TableColumn<>("Дата производства");
        TableColumn <SkydiveSystem, String> systemManufacturerName = new TableColumn<>("Производитель");
        //Adding columns into TableView
        containerTable.getColumns().addAll(systemCode,systemModel,systemSN,systemDOM,systemManufacturerName);
        //Getting values and format from class variables
        systemCode.setCellValueFactory(new PropertyValueFactory<>("systemCode"));
        systemModel.setCellValueFactory(new PropertyValueFactory<>("systemModel"));
        systemSN.setCellValueFactory(new PropertyValueFactory<>("systemSN"));    
        systemDOM.setCellValueFactory(new PropertyValueFactory<>("systemDOM"));
        systemManufacturerName.setCellValueFactory(new PropertyValueFactory<>("systemManufacturerName"));
        //Rendering date cell
        systemDOM.setCellFactory(column -> {
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
        ObservableList<SkydiveSystem> indexList = dr.getContainersList();
        containerTable.setItems(indexList);
        containerTable.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView SkydiveSystem object
                SkydiveSystem currentSystem = containerTable.getSelectionModel().getSelectedItem();
                //TODO list
//                System.out.println("Выбрана система "+currentSystem.getSystemCode()+"!");
//                SystemDetails detail = new SystemDetails(containerTable.getSelectionModel().getSelectedItem(), false);
//                Stage detailStage = new Stage();
//                detailStage.initModality(Modality.WINDOW_MODAL);
//                detailStage.initOwner(index.getScene().getWindow());
//                detail.start(detailStage);
            }
        });
        ContextMenu containerContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        refreshList.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Идет обновление списка");
            //Some code here
            System.out.println("Обновление списка завершено");
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            SkydiveSystem currentSystem = containerTable.getSelectionModel().getSelectedItem();
            System.out.println("Редактируем систему "+currentSystem.getSystemCode()+"?");
            SystemDetails detail = new SystemDetails(containerTable.getSelectionModel().getSelectedItem(), true);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить систему?");
            SystemDetails detail = new SystemDetails(stockID);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            SkydiveSystem currentSystem = containerTable.getSelectionModel().getSelectedItem();
            System.out.println("Удалить систему "+currentSystem.getSystemCode()+"?");
        });
        containerContextMenu.getItems().addAll(refreshList, new SeparatorMenuItem(), addItem, editItem, deleteItem);
        containerTable.setOnContextMenuRequested((ContextMenuEvent event) -> {
            containerContextMenu.show(containerTable, event.getScreenX(), event.getScreenY());
        });
        return index;
    }
    
    public StackPane CanopyTable(){
        StackPane index = new StackPane();
        DataRelay dr = new DataRelay();
        TableView<Canopy> canopyTable = new TableView<>();
        //Columns
        TableColumn <Canopy, String> canopy = new TableColumn<>("Основной парашют");
        TableColumn <Canopy, String> canopyModel = new TableColumn<>("Модель");
        TableColumn <Canopy, Integer> canopySize = new TableColumn<>("Размер, кв.фут");
        canopy.getColumns().addAll(canopyModel,canopySize);
        TableColumn <Canopy, String> canopySN = new TableColumn<>("Серийный номер");
        TableColumn <Canopy, LocalDate> canopyDOM = new TableColumn<>("Дата производства");
        TableColumn <Canopy, Integer> canopyJumps = new TableColumn<>("Прыжков на куполе");
        TableColumn <Canopy, String> canopyManufacturerName = new TableColumn<>("Производитель");
        //Adding columns into TableView
        canopyTable.getColumns().addAll(canopy,canopySN,canopyDOM,canopyJumps,canopyManufacturerName);
        //Getting values and format from class variables
        canopyModel.setCellValueFactory(new PropertyValueFactory<>("canopyModel"));    
        canopySize.setCellValueFactory(new PropertyValueFactory<>("canopySize"));
        canopySN.setCellValueFactory(new PropertyValueFactory<>("canopySN"));
        canopyDOM.setCellValueFactory(new PropertyValueFactory<>("canopyDOM"));      
        canopyJumps.setCellValueFactory(new PropertyValueFactory<>("canopyJumps"));        
        canopyManufacturerName.setCellValueFactory(new PropertyValueFactory<>("canopyManufacturerName"));
        //Rendering date cell
        canopyDOM.setCellFactory(column -> {
            return new TableCell<Canopy, LocalDate>() {
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
        ObservableList<Canopy> indexList = dr.getCanopyList();
        canopyTable.setItems(indexList);
        canopyTable.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView Canopy object
                Canopy currentCanopy = canopyTable.getSelectionModel().getSelectedItem();
                //TODO list
//                System.out.println("Выбрана система "+currentCanopy.getSystemCode()+"!");
//                SystemDetails detail = new SystemDetails(indexStore.getSelectionModel().getSelectedItem(), false);
//                Stage detailStage = new Stage();
//                detailStage.initModality(Modality.WINDOW_MODAL);
//                detailStage.initOwner(index.getScene().getWindow());
//                detail.start(detailStage);
            }
        });
        ContextMenu canopyContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        refreshList.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Идет обновление списка");
            //Some code here
            System.out.println("Обновление списка завершено");
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            Canopy currentCanopy = canopyTable.getSelectionModel().getSelectedItem();
            System.out.println("Редактируем купол "+currentCanopy.getCanopyModel()+"-"+currentCanopy.getCanopySize()+"?");
//            SystemDetails detail = new SystemDetails(canopyTable.getSelectionModel().getSelectedItem(), true);
//            Stage detailStage = new Stage();
//            detailStage.initModality(Modality.WINDOW_MODAL);
//            detailStage.initOwner(index.getScene().getWindow());
//            detail.start(detailStage);
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить купол?");
            SystemDetails detail = new SystemDetails(stockID);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            Canopy currentCanopy = canopyTable.getSelectionModel().getSelectedItem();
            System.out.println("Удалить купол "+currentCanopy.getCanopyModel()+"-"+currentCanopy.getCanopySize()+"?");
        });
        canopyContextMenu.getItems().addAll(refreshList, new SeparatorMenuItem(), addItem, editItem, deleteItem);
        canopyTable.setOnContextMenuRequested((ContextMenuEvent event) -> {
            canopyContextMenu.show(canopyTable, event.getScreenX(), event.getScreenY());
        });
        return index;
    }
    
    public StackPane ReserveTable(){
        StackPane index = new StackPane();
        DataRelay dr = new DataRelay();
        TableView<Reserve> reserveTable = new TableView<>();
        //Columns
        //Reserve(systemID, reserveID, reserveModel, reserveSize, reserveSN, reserveDOM, reserveJumps, reservePackDate, reserveManufacturerID, reserveManufacturerName, stockID)
        TableColumn <Reserve, String> reserve = new TableColumn<>("Запасной парашют");
        TableColumn <Reserve, String> reserveModel = new TableColumn<>("Модель");
        TableColumn <Reserve, Integer> reserveSize = new TableColumn<>("Размер, кв.фут");
        reserve.getColumns().addAll(reserveModel,reserveSize);
        TableColumn <Reserve, String> reserveSN = new TableColumn<>("Серийный номер");
        TableColumn <Reserve, LocalDate> reserveDOM = new TableColumn<>("Дата производства");
        TableColumn <Reserve, Integer> reserveJumps = new TableColumn<>("Прыжков на куполе");
        TableColumn <Reserve, String> reserveManufacturerName = new TableColumn<>("Производитель");
        //Adding columns into TableView
        reserveTable.getColumns().addAll(reserve,reserveModel,reserveSN,reserveJumps,reserveManufacturerName);
        //Getting values and format from class variables
        reserveModel.setCellValueFactory(new PropertyValueFactory<>("canopyModel"));    
        reserveSize.setCellValueFactory(new PropertyValueFactory<>("canopySize"));
        reserveSN.setCellValueFactory(new PropertyValueFactory<>("canopySN"));
        reserveDOM.setCellValueFactory(new PropertyValueFactory<>("canopyDOM"));      
        reserveJumps.setCellValueFactory(new PropertyValueFactory<>("canopyJumps"));        
        reserveManufacturerName.setCellValueFactory(new PropertyValueFactory<>("canopyManufacturerName"));
        //Rendering date cell
        reserveDOM.setCellFactory(column -> {
            return new TableCell<Reserve, LocalDate>() {
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
        ObservableList<Reserve> indexList = dr.getReserveList();
        reserveTable.setItems(indexList);
        reserveTable.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView Canopy object
                Reserve currentReserve = reserveTable.getSelectionModel().getSelectedItem();
                //TODO list
//                System.out.println("Выбрана система "+currentCanopy.getSystemCode()+"!");
//                SystemDetails detail = new SystemDetails(indexStore.getSelectionModel().getSelectedItem(), false);
//                Stage detailStage = new Stage();
//                detailStage.initModality(Modality.WINDOW_MODAL);
//                detailStage.initOwner(index.getScene().getWindow());
//                detail.start(detailStage);
            }
        });
        ContextMenu reserveContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        refreshList.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Идет обновление списка");
            //Some code here
            System.out.println("Обновление списка завершено");
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            Reserve currentReserve = reserveTable.getSelectionModel().getSelectedItem();
            System.out.println("Редактируем купол "+currentReserve.getReserveModel()+"-"+currentReserve.getReserveSize()+"?");
//            SystemDetails detail = new SystemDetails(canopyTable.getSelectionModel().getSelectedItem(), true);
//            Stage detailStage = new Stage();
//            detailStage.initModality(Modality.WINDOW_MODAL);
//            detailStage.initOwner(index.getScene().getWindow());
//            detail.start(detailStage);
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить купол?");
            SystemDetails detail = new SystemDetails(stockID);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            Reserve currentReserve = reserveTable.getSelectionModel().getSelectedItem();
            System.out.println("Удалить купол "+currentReserve.getReserveModel()+"-"+currentReserve.getReserveSize()+"?");
        });
        reserveContextMenu.getItems().addAll(refreshList, new SeparatorMenuItem(), addItem, editItem, deleteItem);
        reserveTable.setOnContextMenuRequested((ContextMenuEvent event) -> {
            reserveContextMenu.show(reserveTable, event.getScreenX(), event.getScreenY());
        });
        return index;
    }
    
    public StackPane AADTable(){
        StackPane index = new StackPane();
        DataRelay dr = new DataRelay();
        TableView<AAD> aadTable = new TableView<>();
        //Columns
        //AAD(systemID, aadID, aadModel, aadSN, aadDOM, aadJumps, aadNextRegl, aadSaved, aadManufacturerID, aadManufacturerName, stockID)
        TableColumn <AAD, String> aadModel = new TableColumn<>("Модель");
        TableColumn <AAD, String> aadSN = new TableColumn<>("Серийный номер");
        TableColumn <AAD, LocalDate> aadDOM = new TableColumn<>("Дата производства");
        TableColumn <AAD, Integer> aadJumps = new TableColumn<>("Прыжков на приборе");
        TableColumn <AAD, LocalDate> aadNextRegl = new TableColumn<>("Дата следующего регламента");
        TableColumn <AAD, Integer> aadSaved = new TableColumn<>("Применений");
        TableColumn <AAD, String> aadManufacturerName = new TableColumn<>("Производитель");
        //Adding columns into TableView
        aadTable.getColumns().addAll(aadModel,aadSN,aadDOM,aadJumps,aadNextRegl,aadSaved,aadManufacturerName);
        //Getting values and format from class variables
        aadModel.setCellValueFactory(new PropertyValueFactory<>("aadModel"));    
        aadSN.setCellValueFactory(new PropertyValueFactory<>("aadSN"));
        aadDOM.setCellValueFactory(new PropertyValueFactory<>("aadDOM"));
        aadJumps.setCellValueFactory(new PropertyValueFactory<>("aadJumps"));      
        aadNextRegl.setCellValueFactory(new PropertyValueFactory<>("aadNextRegl"));        
        aadSaved.setCellValueFactory(new PropertyValueFactory<>("aadSaved"));
        aadManufacturerName.setCellValueFactory(new PropertyValueFactory<>("aadManufacturerName"));
        //Rendering date cell
        aadDOM.setCellFactory(column -> {
            return new TableCell<AAD, LocalDate>() {
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
        aadNextRegl.setCellFactory(column -> {
            return new TableCell<AAD, LocalDate>() {
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
        ObservableList<AAD> indexList = dr.getAadList();
        aadTable.setItems(indexList);
        aadTable.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView Canopy object
                AAD currentAAD = aadTable.getSelectionModel().getSelectedItem();
                //TODO list
//                System.out.println("Выбрана система "+currentCanopy.getSystemCode()+"!");
//                SystemDetails detail = new SystemDetails(indexStore.getSelectionModel().getSelectedItem(), false);
//                Stage detailStage = new Stage();
//                detailStage.initModality(Modality.WINDOW_MODAL);
//                detailStage.initOwner(index.getScene().getWindow());
//                detail.start(detailStage);
            }
        });
        ContextMenu aadContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        refreshList.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Идет обновление списка");
            //Some code here
            System.out.println("Обновление списка завершено");
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            AAD currentAAD = aadTable.getSelectionModel().getSelectedItem();
            System.out.println("Редактируем прибор "+currentAAD.getAadModel()+"?");
//            SystemDetails detail = new SystemDetails(canopyTable.getSelectionModel().getSelectedItem(), true);
//            Stage detailStage = new Stage();
//            detailStage.initModality(Modality.WINDOW_MODAL);
//            detailStage.initOwner(index.getScene().getWindow());
//            detail.start(detailStage);
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить прибор?");
            SystemDetails detail = new SystemDetails(stockID);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            AAD currentAAD = aadTable.getSelectionModel().getSelectedItem();
            System.out.println("Удалить прибор "+currentAAD.getAadModel()+"?");
        });
        aadContextMenu.getItems().addAll(refreshList, new SeparatorMenuItem(), addItem, editItem, deleteItem);
        aadTable.setOnContextMenuRequested((ContextMenuEvent event) -> {
            aadContextMenu.show(aadTable, event.getScreenX(), event.getScreenY());
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
