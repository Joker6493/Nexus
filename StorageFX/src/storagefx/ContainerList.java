/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

/**
 *
 * @author dboro
 */
public class ContainerList extends Application {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private int stockID;
    private int status;
    private boolean assembleInProcess = false;

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
    @Override
    public void start(Stage primaryStage) throws SQLException {
        StackPane index = ContainerTable(false);
        Scene scene = new Scene(index);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public StackPane ContainerTable(boolean closeOnSelect){
        StackPane index = new StackPane();
        DataRelay dr = new DataRelay();
        dr.setStatus(getStatus());
        dr.setStock(getStockID());
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
        containerTable.setItems(dr.getContainersList());
        containerTable.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView SkydiveSystem object
                SkydiveSystem currentSystem = containerTable.getSelectionModel().getSelectedItem();
                //TODO list
                System.out.println("Выбран ранец "+currentSystem.getSystemCode()+"!");
                if (closeOnSelect == true) {
                    index.getScene().getWindow().hide();
                }else{
                ElementDetails detail = new ElementDetails(currentSystem, false);
                detail.setStatus(getStatus());
                detail.setStockID(getStockID());
                Stage detailStage = new Stage();
                detailStage.initModality(Modality.WINDOW_MODAL);
                detailStage.initOwner(index.getScene().getWindow());
                detail.start(detailStage);
                }
            }
        });
        ContextMenu containerContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        refreshList.setOnAction((ActionEvent e) -> {
            System.out.println("Идет обновление списка");
            containerTable.getItems().clear();
            containerTable.setItems(dr.getContainersList());
            System.out.println("Обновление списка завершено");
        });
        MenuItem viewItem = new MenuItem("Просмотр");
        viewItem.setOnAction((ActionEvent e) -> {
            SkydiveSystem currentSystem = containerTable.getSelectionModel().getSelectedItem();
            System.out.println("информация о ранце "+currentSystem.getSystemCode());
            ElementDetails detail = new ElementDetails(currentSystem, false);
            detail.setStatus(getStatus());
            detail.setStockID(getStockID());
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            SkydiveSystem currentSystem = containerTable.getSelectionModel().getSelectedItem();
            System.out.println("Редактируем ранец "+currentSystem.getSystemCode()+"?");
            ElementDetails detail = new ElementDetails(currentSystem, true);
            detail.setStatus(getStatus());
            detail.setStockID(getStockID());
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem assembleItem = new MenuItem("Собрать систему");
        assembleItem.setOnAction((ActionEvent e) -> {
            SkydiveSystem currentSystem = containerTable.getSelectionModel().getSelectedItem();
            System.out.println("Начата сборка системы " + currentSystem.getSystemCode());
            SystemDetails detail = new SystemDetails(currentSystem, true);
            detail.setStatus(getStatus());
            detail.setStockID(getStockID());
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
    //Need to remade
            System.out.println("Добавить ранец?");
            ElementDetails detail = new ElementDetails("container",stockID);
            detail.setStatus(getStatus());
            detail.setStockID(getStockID());
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            SkydiveSystem currentSystem = containerTable.getSelectionModel().getSelectedItem();
            System.out.println("Удалить ранец "+currentSystem.getSystemCode()+"?");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение изменений");
            confirm.setHeaderText("Удалить ранец " + currentSystem.getSystemCode() +" ?");
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == null) {
                } else if (option.get() == yes) {
                    dr.deleteContainer(currentSystem);
                    containerTable.getItems().clear();
                    containerTable.setItems(dr.getContainersList());
                } else if (option.get() == no) {

                } else {

                }
        });
        containerContextMenu.getItems().addAll(refreshList, viewItem, new SeparatorMenuItem(), addItem, editItem, assembleItem, deleteItem);
        containerTable.setOnContextMenuRequested((ContextMenuEvent event) -> {
            containerContextMenu.show(containerTable, event.getScreenX(), event.getScreenY());
        });
        index.getChildren().add(containerTable);
        return index;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
