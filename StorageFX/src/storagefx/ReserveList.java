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
import javafx.event.ActionEvent;
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
public class ReserveList extends Application {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
    private int stockID;
    private int status;
    private Reserve selectedReserve;

    public Reserve getSelectedReserve() {
        return selectedReserve;
    }

    public void setSelectedReserve(Reserve selectedReserve) {
        this.selectedReserve = selectedReserve;
    }
    
    @Override
    public void start(Stage primaryStage) throws SQLException {
        StackPane index = ReserveTable(false);
        Scene scene = new Scene(index);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public StackPane ReserveTable(boolean closeOnSelect){
        StackPane index = new StackPane();
        DataRelay dr = new DataRelay();
        dr.setStatus(status);
        dr.setStock(stockID);
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
        reserveTable.getColumns().addAll(reserve,reserveSN,reserveDOM,reserveJumps,reserveManufacturerName);
        //Getting values and format from class variables
        reserveModel.setCellValueFactory(new PropertyValueFactory<>("reserveModel"));    
        reserveSize.setCellValueFactory(new PropertyValueFactory<>("reserveSize"));
        reserveSN.setCellValueFactory(new PropertyValueFactory<>("reserveSN"));
        reserveDOM.setCellValueFactory(new PropertyValueFactory<>("reserveDOM"));      
        reserveJumps.setCellValueFactory(new PropertyValueFactory<>("reserveJumps"));        
        reserveManufacturerName.setCellValueFactory(new PropertyValueFactory<>("reserveManufacturerName"));
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
        reserveTable.setItems(dr.getReserveList());
        reserveTable.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView Canopy object
                setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
                System.out.println("Выбран купол "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize()+"!");
                if (closeOnSelect == true) {
                    index.getScene().getWindow().hide();
                }else{
                ElementDetails detail = new ElementDetails(selectedReserve, false);
                Stage detailStage = new Stage();
                detailStage.initModality(Modality.WINDOW_MODAL);
                detailStage.initOwner(index.getScene().getWindow());
                detail.start(detailStage);
                }
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
        MenuItem viewItem = new MenuItem("Просмотр");
        viewItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            System.out.println("информация о куполе "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize());
            ElementDetails detail = new ElementDetails(selectedReserve, false);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            System.out.println("Редактируем купол "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize()+"?");
            ElementDetails detail = new ElementDetails(selectedReserve, true);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить купол?");
            ElementDetails detail = new ElementDetails("reserve",stockID);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            System.out.println("Удалить купол "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize()+"?");
        });
        reserveContextMenu.getItems().addAll(refreshList, viewItem, new SeparatorMenuItem(), addItem, editItem, deleteItem);
        reserveTable.setOnContextMenuRequested((ContextMenuEvent event) -> {
            reserveContextMenu.show(reserveTable, event.getScreenX(), event.getScreenY());
        });
        index.getChildren().add(reserveTable);
        return index;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
