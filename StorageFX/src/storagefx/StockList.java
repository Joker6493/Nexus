/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
                if (closeOnSelect) {
                    System.out.println("Выбран склад "+selectedStock.getStockName() + "!");
                    index.getScene().getWindow().hide();
                }   
            }
        });
        
        Button refreshBtn = new Button();
        refreshBtn.setText("Обновить");
        refreshBtn.setOnAction((ActionEvent event) -> {
            System.out.println("Идет обновление списка");
            stockList.getItems().clear();
            stockList.setItems(dr.getStockList());
            System.out.println("Обновление списка завершено");
        });
        
        ComboBox <Status> statusBox = new ComboBox<>();
        ObservableList<Status> statusList = dr.getStatusListShort();
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
            stockList.getItems().clear();
            stockList.setItems(dr.getStockList());
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
            if (selectedStock!=null){
                System.out.println("Переименовать склад "+ selectedStock.getStockName() + "?");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждение изменений");
                confirm.setHeaderText("Переименовать склад "+ selectedStock.getStockName() + "?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
                        ElementDetails detail = new ElementDetails(selectedStock, true);
                        Stage detailStage = new Stage();
                        detailStage.initModality(Modality.WINDOW_MODAL);
                        detailStage.initOwner(index.getScene().getWindow());
                        detail.start(detailStage);
                    } else if (option.get() == no) {
                    } else {
                    }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Внимание! Ничего не выделено! Выделите элемент и повторите попытку");
                emptyWarn.showAndWait(); 
            }
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            System.out.println("Добавить склад?");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение изменений");
            confirm.setHeaderText("Добавить склад?");
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == null) {
                } else if (option.get() == yes) {
                    ElementDetails detail = new ElementDetails("stock",0);
                    Stage detailStage = new Stage();
                    detailStage.initModality(Modality.WINDOW_MODAL);
                    detailStage.initOwner(index.getScene().getWindow());
                    detail.start(detailStage);
                } else if (option.get() == no) {
                } else {
                }
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            Stock selectedStock = stockList.getSelectionModel().getSelectedItem();
            if (selectedStock!=null){
                System.out.println("Удалить склад "+ selectedStock.getStockName() + "?");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждение изменений");
                confirm.setHeaderText("Удалить склад "+ selectedStock.getStockName() + "?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
                        dr.setStatusStock(selectedStock,1);
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Внимание!");
                        info.setHeaderText(null);
                        info.setContentText("Склад удален!");
                        info.showAndWait();
                        System.out.println("Склад удален!");
                        stockList.getItems().clear();
                        stockList.setItems(dr.getStockList());
                    } else if (option.get() == no) {
                    } else {
                    }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Внимание! Ничего не выделено! Выделите элемент и повторите попытку");
                emptyWarn.showAndWait(); 
            }
        });
        MenuItem restoreItem = new MenuItem("Восстановить");
        restoreItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            Stock selectedStock = stockList.getSelectionModel().getSelectedItem();
            if (selectedStock!=null){
                System.out.println("Восстановить склад "+ selectedStock.getStockName() + "?");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждение изменений");
                confirm.setHeaderText("Восстановить склад "+ selectedStock.getStockName() + "?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
                        dr.setStatusStock(selectedStock,0);
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Внимание!");
                        info.setHeaderText(null);
                        info.setContentText("Склад восстановлен!");
                        info.showAndWait();
                        System.out.println("Склад восстановлен!");
                        stockList.getItems().clear();
                        stockList.setItems(dr.getStockList());
                    } else if (option.get() == no) {
                    } else {
                    }
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Внимание! Ничего не выделено! Выделите элемент и повторите попытку");
                emptyWarn.showAndWait(); 
            }
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
        stockList.setContextMenu(storageContextMenu);
        index.setCenter(stockList);
        
        Button closeBtn = new Button();
        closeBtn.setText("Закрыть");
        closeBtn.setOnAction((ActionEvent event) -> {
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
