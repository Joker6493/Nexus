/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author d.borodin
 */
public class StockList extends Application {
        
    private int status;
    private Stock selectedStock;

    public Stock getSelectedStock() {
        return selectedStock;
    }
    public void setSelectedStock(Stock selectedStock) {
        this.selectedStock = selectedStock;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane index = StockList(false);
        Scene scene = new Scene(index);
        
        primaryStage.setTitle("Список складов");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public BorderPane StockList (boolean closeOnSelect) {
        BorderPane index = new BorderPane();
        DataRelay dr = new DataRelay();
        dr.setStatus(getStatus());
        ListView<Stock> stockList = new ListView<>();
        stockList.setCellFactory(view -> new ListCell<Stock>(){
            @Override
            protected void updateItem(Stock stock, boolean empty) {
                super.updateItem(stock, empty);
                if (empty || stock == null || stock.getStockName() == null) {
                    setText(null);
                }else{
                    setText(stock.getStockName());
                }
            }
        });
        //Adding data and create scene
        ObservableList<Stock> indexList = dr.getStockList();
        stockList.setItems(indexList);
        stockList.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView SkydiveSystem object
                setSelectedStock(stockList.getSelectionModel().getSelectedItem());
                //TODO list
                System.out.println("Выбран склад "+selectedStock.getStockName() + "!");
                if (closeOnSelect == true) {
                    index.getScene().getWindow().hide();
                }    
            }
        });
        
        Button refreshBtn = new Button();
        refreshBtn.setText("Обновить");
        refreshBtn.setOnAction((ActionEvent event) -> {
            //Refreshing indexList - in process
            System.out.println("Идет обновление списка");
            //Some code here
            stockList.getItems().clear();
            stockList.setItems(dr.getStockList());
            //indexStore.refresh();
            System.out.println("Обновление списка завершено");
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
        
        HBox storageBar = new HBox();
        storageBar.getChildren().addAll(statusBox, new Label("Статус склада"), refreshBtn);
        storageBar.setPadding(new Insets(10));
        index.setTop(storageBar);
        
        ContextMenu storageContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        refreshList.setOnAction((ActionEvent e) -> {
            System.out.println("Идет обновление списка");
            stockList.getItems().clear();
            stockList.setItems(dr.getStockList());
            System.out.println("Обновление списка завершено");
        });
        MenuItem editItem = new MenuItem("Переименовать");
        editItem.setOnAction((ActionEvent e) -> {
            Stock selectedStock = stockList.getSelectionModel().getSelectedItem();
            System.out.println("Редактируем склад "+ selectedStock.getStockName() + "?");
            ElementDetails detail = new ElementDetails(selectedStock, true);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
            
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить склад?");
            ElementDetails detail = new ElementDetails("stock",0);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            Stock selectedStock = stockList.getSelectionModel().getSelectedItem();
            System.out.println("Удалить склад "+ selectedStock.getStockName() + "?");
            dr.setStatusStock(selectedStock,1);
            System.out.println("Склад удален!");
            stockList.getItems().clear();
            stockList.setItems(dr.getStockList());
        });
        MenuItem restoreItem = new MenuItem("Восстановить");
        restoreItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            Stock selectedStock = stockList.getSelectionModel().getSelectedItem();
            System.out.println("Восстановить склад "+ selectedStock.getStockName() + "?");
            dr.setStatusStock(selectedStock,0);
            System.out.println("Склад восстановлен!");
            stockList.getItems().clear();
            stockList.setItems(dr.getStockList());
        });
        storageContextMenu.getItems().addAll(refreshList, new SeparatorMenuItem(), addItem, editItem);
        switch (getStatus()){
            case 0:
                storageContextMenu.getItems().add(deleteItem);
                break;
            case 1:
                storageContextMenu.getItems().add(restoreItem);
                break;
        }
        stockList.setOnContextMenuRequested((ContextMenuEvent event) -> {
            storageContextMenu.show(stockList, event.getScreenX(), event.getScreenY());
        });
        
        index.setCenter(stockList);
        
        Button closeBtn = new Button();
        closeBtn.setText("Закрыть");
        closeBtn.setOnAction((ActionEvent event) -> {
            //Some code here
            index.getScene().getWindow().hide();
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
