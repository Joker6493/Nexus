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
public class CanopyList extends Application {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private int stockID;
    private int status;
    private Canopy selectedCanopy;
    private Canopy oldCanopy;
    private SkydiveSystem selectedSystem;

    public Canopy getSelectedCanopy() {
        return selectedCanopy;
    }
    public void setSelectedCanopy(Canopy selectedCanopy) {
        this.selectedCanopy = selectedCanopy;
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
    public Canopy getOldCanopy() {
        return oldCanopy;
    }
    public void setOldCanopy(Canopy oldCanopy) {
        this.oldCanopy = oldCanopy;
    }
    public SkydiveSystem getSelectedSystem() {
        return selectedSystem;
    }
    public void setSelectedSystem(SkydiveSystem selectedSystem) {
        this.selectedSystem = selectedSystem;
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
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение изменений");
                    confirm.setHeaderText("Вы уверены, что хотите провести замену куполов в ранце "+ selectedSystem.getSystemCode() +"?");
                    confirm.setContentText("Текущий купол: " + oldCanopy.getCanopyModel() +"-"+ oldCanopy.getCanopySize() +"/n"+ "Новый купол: " + selectedCanopy.getCanopyModel() +"-"+ selectedCanopy.getCanopySize());
                    ButtonType yes = new ButtonType("Да");
                    ButtonType no = new ButtonType("Нет");
                    confirm.getButtonTypes().clear();
                    confirm.getButtonTypes().addAll(yes, no);
                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
                        dr.replaceCanopy(oldCanopy, selectedCanopy);
                        index.getScene().getWindow().hide();
                    } else if (option.get() == no) {
                        setSelectedCanopy(oldCanopy);
                        Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                        index.getScene().getWindow().hide();
                    } else {
                        setSelectedCanopy(oldCanopy);
                        Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                        noChange.setTitle("Внимание!");
                        noChange.setHeaderText(null);
                        noChange.setContentText("Изменения не сохранены!");
                        noChange.showAndWait();
                    }
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
            canopyTable.getItems().clear();
            canopyTable.setItems(dr.getCanopyList());
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
        MenuItem moveItem = new MenuItem("Переместить");
        moveItem.setOnAction((ActionEvent e) -> {
            setSelectedCanopy(canopyTable.getSelectionModel().getSelectedItem());
            System.out.println("Переместить систему "+selectedCanopy.getCanopyModel()+"-"+selectedCanopy.getCanopySize()+"?");
            Stage chooseWindow = new Stage();
            chooseWindow.setTitle("Выберите склад");
            //TODO - transmit to modal window stock and current canopy
            StockList sl = new StockList();
            Scene sList = new Scene(sl.StockList(true));
            chooseWindow.setScene(sList);
            
            chooseWindow.initModality(Modality.WINDOW_MODAL);
            chooseWindow.initOwner(index.getScene().getWindow());
            chooseWindow.showAndWait();
            if (sl.getSelectedStock() != null){
                Stock newStock = sl.getSelectedStock();
                String stockNew = "stockid = "+ newStock.getStockID();
                dr.editCanopy(selectedCanopy, stockNew);
                System.out.println("Система перемещена!");
            //Updating skydive system list
                selectedCanopy.setStockID(newStock.getStockID());
                canopyTable.getItems().clear();
                canopyTable.setItems(dr.getCanopyList());
            }
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
