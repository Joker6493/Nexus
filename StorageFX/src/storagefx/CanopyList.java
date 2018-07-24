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
public class CanopyList extends Application {
        
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
    private Canopy selectedCanopy;

    public Canopy getSelectedCanopy() {
        return selectedCanopy;
    }

    public void setSelectedCanopy(Canopy selectedCanopy) {
        this.selectedCanopy = selectedCanopy;
    }
    @Override
    public void start(Stage primaryStage) throws SQLException {
        StackPane index = CanopyTable(false);
        Scene scene = new Scene(index);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public StackPane CanopyTable(boolean closeOnSelect){
        StackPane index = new StackPane();
        DataRelay dr = new DataRelay();
        dr.setStatus(status);
        dr.setStock(stockID);
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
        canopyTable.setItems(dr.getCanopyList());
        canopyTable.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView Canopy object
                setSelectedCanopy(canopyTable.getSelectionModel().getSelectedItem());
                System.out.println("Выбран купол "+selectedCanopy.getCanopyModel()+"-"+selectedCanopy.getCanopySize()+"!");
                if (closeOnSelect == true) {
                    index.getScene().getWindow().hide();
                }else{
                ElementDetails detail = new ElementDetails(selectedCanopy, false);
                Stage detailStage = new Stage();
                detailStage.initModality(Modality.WINDOW_MODAL);
                detailStage.initOwner(index.getScene().getWindow());
                detail.start(detailStage);                    
                }
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
        MenuItem viewItem = new MenuItem("Просмотр");
        viewItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            setSelectedCanopy(canopyTable.getSelectionModel().getSelectedItem());
            System.out.println("информация о куполе "+selectedCanopy.getCanopyModel()+"-"+selectedCanopy.getCanopySize());
            ElementDetails detail = new ElementDetails(selectedCanopy, false);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            setSelectedCanopy(canopyTable.getSelectionModel().getSelectedItem());
            System.out.println("Редактируем купол "+selectedCanopy.getCanopyModel()+"-"+selectedCanopy.getCanopySize()+"?");
            ElementDetails detail = new ElementDetails(selectedCanopy, true);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить купол?");
            ElementDetails detail = new ElementDetails("canopy",stockID);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            setSelectedCanopy(canopyTable.getSelectionModel().getSelectedItem());
            System.out.println("Удалить купол "+selectedCanopy.getCanopyModel()+"-"+selectedCanopy.getCanopySize()+"?");
        });
        canopyContextMenu.getItems().addAll(refreshList, viewItem, new SeparatorMenuItem(), addItem, editItem, deleteItem);
        canopyTable.setOnContextMenuRequested((ContextMenuEvent event) -> {
            canopyContextMenu.show(canopyTable, event.getScreenX(), event.getScreenY());
        });
        index.getChildren().add(canopyTable);
        return index;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
