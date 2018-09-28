/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 *
 * @author dboro
 */
public class ElementsIndex extends Application {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
    private StackPane table;
    private DataRelay dr;
    @Override
    public void start(Stage primaryStage) throws SQLException {
        BorderPane index = ElementsIndex();
        Scene scene = new Scene(index);
        primaryStage.setTitle("Элементы систем");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public BorderPane ElementsIndex(){
        BorderPane index = new BorderPane();
        HBox topMenu = new HBox();
        //default
        setStatus(0);
        setStockID(2);
        DataRelay dr = new DataRelay();
        dr.setStatus(getStatus());
        dr.setStock(getStockID());
        ComboBox <String> elementsBox = new ComboBox<>();
        ArrayList<String> indexList = new ArrayList<>();
        indexList.add("Ранцы");
        indexList.add("Основные парашюты");
        indexList.add("Запасные парашюты");
        indexList.add("Страхующие приборы");
        elementsBox.setItems(FXCollections.observableList(indexList));
        elementsBox.getSelectionModel().select(0);
        Button refreshBtn = new Button();
        refreshBtn.setText("Обновить");
        refreshBtn.setOnAction((ActionEvent event) -> {
            //Refreshing indexList - in process
            System.out.println("Идет обновление списка");
            //Some code here
            table.getChildren().clear();
            switch (elementsBox.getSelectionModel().getSelectedItem()){
                case "Ранцы":
                    ContainerList col = new ContainerList();
                    col.setStatus(getStatus());
                    col.setStockID(getStockID());
                    table = col.ContainerTable(false);
                    break;
                case "Основные парашюты":
                    CanopyList cl = new CanopyList();
                    cl.setStatus(getStatus());
                    cl.setStockID(getStockID());
                    table = cl.CanopyTable(false);
                    break;
                case "Запасные парашюты":
                    ReserveList rl = new ReserveList();
                    rl.setStatus(getStatus());
                    rl.setStockID(getStockID());
                    table = rl.ReserveTable(false);
                    break;
                case "Страхующие приборы":
                    AADList al = new AADList();
                    al.setStatus(getStatus());
                    al.setStockID(getStockID());
                    table = al.AADTable(false);
                    break;
            }
            index.setCenter(table);
            System.out.println("Обновление списка завершено");
        });
        
        ComboBox <Stock> stockBox = new ComboBox<>();
        stockBox.setItems(dr.getStockList());
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
        stockBox.getSelectionModel().select(getStockID()-1);
        setStockID(stockBox.getSelectionModel().getSelectedItem().getStockID());
        stockBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //TODO code
            setStockID(stockBox.getSelectionModel().getSelectedItem().getStockID());
            dr.setStock(getStockID());
            System.out.println("Выбран склад "+ stockBox.getSelectionModel().getSelectedItem().getStockName() +"!");
            table.getChildren().clear();
            switch (elementsBox.getSelectionModel().getSelectedItem()){
                case "Ранцы":
                    ContainerList col = new ContainerList();
                    col.setStatus(getStatus());
                    col.setStockID(getStockID());
                    table = col.ContainerTable(false);
                    break;
                case "Основные парашюты":
                    CanopyList cl = new CanopyList();
                    cl.setStatus(getStatus());
                    cl.setStockID(getStockID());
                    table = cl.CanopyTable(false);
                    break;
                case "Запасные парашюты":
                    ReserveList rl = new ReserveList();
                    rl.setStatus(getStatus());
                    rl.setStockID(getStockID());
                    table = rl.ReserveTable(false);
                    break;
                case "Страхующие приборы":
                    AADList al = new AADList();
                    al.setStatus(getStatus());
                    al.setStockID(getStockID());
                    table = al.AADTable(false);
                    break;
            }
        });
        
        ComboBox <Status> statusBox = new ComboBox<>();
        statusBox.setItems(dr.getStatusList());
        statusBox.getSelectionModel().select(getStatus());
        setStatus(statusBox.getSelectionModel().getSelectedItem().getStatusID());
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
            setStatus(statusBox.getSelectionModel().getSelectedItem().getStatusID());
            dr.setStatus(getStatus());
            System.out.println("Выбран статус "+ statusBox.getSelectionModel().getSelectedItem().getStatusName() +"!");
            table.getChildren().clear();
            switch (elementsBox.getSelectionModel().getSelectedItem()){
                case "Ранцы":
                    ContainerList col = new ContainerList();
                    col.setStatus(getStatus());
                    col.setStockID(getStockID());
                    table = col.ContainerTable(false);
                    break;
                case "Основные парашюты":
                    CanopyList cl = new CanopyList();
                    cl.setStatus(getStatus());
                    cl.setStockID(getStockID());
                    table = cl.CanopyTable(false);
                    break;
                case "Запасные парашюты":
                    ReserveList rl = new ReserveList();
                    rl.setStatus(getStatus());
                    rl.setStockID(getStockID());
                    table = rl.ReserveTable(false);
                    break;
                case "Страхующие приборы":
                    AADList al = new AADList();
                    al.setStatus(getStatus());
                    al.setStockID(getStockID());
                    table = al.AADTable(false);
                    break;
            }
        });
        topMenu.getChildren().addAll(elementsBox, new Label("Элементы системы"), stockBox, new Label("Склад"), statusBox, new Label("Статус системы"), refreshBtn);
        index.setTop(topMenu);
        switch (elementsBox.getSelectionModel().getSelectedItem()){
            case "Ранцы":
                    ContainerList col = new ContainerList();
                    col.setStatus(getStatus());
                    col.setStockID(getStockID());
                    table = col.ContainerTable(false);
                    break;
            case "Основные парашюты":
                    CanopyList cl = new CanopyList();
                    cl.setStatus(getStatus());
                    cl.setStockID(getStockID());
                    table = cl.CanopyTable(false);
                    break;
            case "Запасные парашюты":
                    ReserveList rl = new ReserveList();
                    rl.setStatus(getStatus());
                    rl.setStockID(getStockID());
                    table = rl.ReserveTable(false);
                    break;
            case "Страхующие приборы":
                    AADList al = new AADList();
                    al.setStatus(getStatus());
                    al.setStockID(getStockID());
                    table = al.AADTable(false);
                    break;
        }
        index.setCenter(table);
        return index;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
