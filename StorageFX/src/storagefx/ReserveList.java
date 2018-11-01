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
public class ReserveList extends Application {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    
    private int stockID;
    private int status;
    private Reserve selectedReserve;
    private Reserve oldReserve;
    private SkydiveSystem selectedSystem;
    private boolean assembleInProcess = false;
    private boolean newSystem = false;

    public boolean isNewSystem() {
        return newSystem;
    }
    public void setNewSystem(boolean newSystem) {
        this.newSystem = newSystem;
    }
    public boolean isAssembleInProcess() {
        return assembleInProcess;
    }
    public void setAssembleInProcess(boolean assembleInProcess) {
        this.assembleInProcess = assembleInProcess;
    }
    public Reserve getSelectedReserve() {
        return selectedReserve;
    }
    public void setSelectedReserve(Reserve selectedReserve) {
        this.selectedReserve = selectedReserve;
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
    public Reserve getOldReserve() {
        return oldReserve;
    }
    public void setOldReserve(Reserve oldReserve) {
        this.oldReserve = oldReserve;
    }
    public SkydiveSystem getSelectedSystem() {
        return selectedSystem;
    }
    public void setSelectedSystem(SkydiveSystem selectedSystem) {
        this.selectedSystem = selectedSystem;
    }
    
    @Override
    public void start(Stage primaryStage) throws SQLException {
        StackPane index = ReserveTable(false);
        Scene scene = new Scene(index);
        primaryStage.setTitle("Список запасных парашютов");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public StackPane ReserveTable(boolean closeOnSelect){
        StackPane index = new StackPane();
        DataRelay dr = new DataRelay();
        dr.setStatus(getStatus());
        dr.setStock(getStockID());
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
                    if (!isAssembleInProcess() && !isNewSystem()){
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Подтверждение изменений");
                        confirm.setHeaderText("Вы уверены, что хотите провести замену куполов ПЗ в ранце "+ selectedSystem.getSystemCode() +"?");
                        confirm.setContentText("Текущий купол: " + oldReserve.getReserveModel() +"-"+ oldReserve.getReserveSize() +"\n"+ "Новый купол: " + selectedSystem.getReserveModel() +"-"+ selectedSystem.getReserveSize());
                        ButtonType yes = new ButtonType("Да");
                        ButtonType no = new ButtonType("Нет");
                        confirm.getButtonTypes().clear();
                        confirm.getButtonTypes().addAll(yes, no);
                        Optional<ButtonType> option = confirm.showAndWait();
                        if (option.get() == null) {
                        } else if (option.get() == yes) {
                            dr.replaceReserve(oldReserve, selectedReserve);
                            index.getScene().getWindow().hide();
                        } else if (option.get() == no) {
                            setSelectedReserve(oldReserve);
                            Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                            noChange.setTitle("Внимание!");
                            noChange.setHeaderText(null);
                            noChange.setContentText("Изменения не сохранены!");
                            noChange.showAndWait();
                            index.getScene().getWindow().hide();
                        } else {
                            setSelectedReserve(oldReserve);
                            Alert noChange = new Alert(Alert.AlertType.INFORMATION);
                            noChange.setTitle("Внимание!");
                            noChange.setHeaderText(null);
                            noChange.setContentText("Изменения не сохранены!");
                            noChange.showAndWait();
                        }
                    }else if (isNewSystem()){
                        //
                        index.getScene().getWindow().hide();
                    }else{
                        index.getScene().getWindow().hide();
                    }
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
            System.out.println("Идет обновление списка");
            reserveTable.getItems().clear();
            reserveTable.setItems(dr.getReserveList());
            System.out.println("Обновление списка завершено");
        });
        MenuItem viewItem = new MenuItem("Просмотр");
        viewItem.setOnAction((ActionEvent e) -> {
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            if (getSelectedReserve()!=null){
                System.out.println("Просмотр информации о куполе "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize());
                ElementDetails detail = new ElementDetails(selectedReserve, false);
                detail.setStatus(getStatus());
                detail.setStockID(getStockID());
                Stage detailStage = new Stage();
                detailStage.initModality(Modality.WINDOW_MODAL);
                detailStage.initOwner(index.getScene().getWindow());
                detail.start(detailStage);
            }else{
                Alert emptyWarn = new Alert(Alert.AlertType.WARNING);
                emptyWarn.setTitle("Внимание!");
                emptyWarn.setHeaderText(null);
                emptyWarn.setContentText("Внимание! Ничего не выделено! Выделите элемент и повторите попытку");
                emptyWarn.showAndWait(); 
            }
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            if (getSelectedReserve()!=null){
                System.out.println("Редактировать купол "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize()+"?");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждение изменений");
                confirm.setHeaderText("Редактировать купол ПЗ "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize()+"?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
                        ElementDetails detail = new ElementDetails(selectedReserve, true);
                        detail.setStatus(getStatus());
                        detail.setStockID(getStockID());
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
        MenuItem moveItem = new MenuItem("Переместить");
        moveItem.setOnAction((ActionEvent e) -> {
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            if (getSelectedReserve()!=null){
                System.out.println("Переместить купол ПЗ "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize()+"?");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждение изменений");
                confirm.setHeaderText("Переместить купол ПЗ "+selectedReserve.getReserveModel()+"-"+selectedReserve.getReserveSize()+"?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
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
                            selectedReserve.setStockID(newStock.getStockID());
                            dr.editReserve(selectedReserve);
                            Alert info = new Alert(Alert.AlertType.INFORMATION);
                            info.setTitle("Внимание!");
                            info.setHeaderText(null);
                            info.setContentText("Купол ПЗ перемещен!");
                            info.showAndWait();
                            System.out.println("Купол ПЗ перемещен!");
                        //Updating skydive system list
                            reserveTable.getItems().clear();
                            reserveTable.setItems(dr.getReserveList());
                        }
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
            System.out.println("Добавить купол ПЗ?");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение изменений");
            confirm.setHeaderText("Добавить купол ПЗ?");
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == null) {
                } else if (option.get() == yes) {
                    ElementDetails detail = new ElementDetails("reserve",stockID);
                    detail.setStatus(getStatus());
                    detail.setStockID(getStockID());
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
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            if (getSelectedReserve()!=null){
                System.out.println("Удалить купол "+getSelectedReserve().getReserveModel()+"-"+getSelectedReserve().getReserveSize()+"?");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждение изменений");
                confirm.setHeaderText("Удалить купол ПЗ " + getSelectedReserve().getReserveModel() +"-"+ getSelectedReserve().getReserveSize()+" ?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
                        dr.setStatusReserve(getSelectedReserve(),1);
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Внимание!");
                        info.setHeaderText(null);
                        info.setContentText("Купол ПЗ удален!");
                        info.showAndWait();
                        System.out.println("Купол ПЗ удален!");
                        reserveTable.getItems().clear();
                        reserveTable.setItems(dr.getReserveList());
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
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            if (getSelectedReserve()!=null){
                System.out.println("Восстановить купол "+getSelectedReserve().getReserveModel()+"-"+getSelectedReserve().getReserveSize()+"?");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждение изменений");
                confirm.setHeaderText("Восстановить купол ПЗ " + getSelectedReserve().getReserveModel() +"-"+ getSelectedReserve().getReserveSize()+" ?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
                        dr.setStatusReserve(getSelectedReserve(),0);
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Внимание!");
                        info.setHeaderText(null);
                        info.setContentText("Купол ПЗ восстановлен!");
                        info.showAndWait();
                        System.out.println("Купол ПЗ восстановлен!");
                        reserveTable.getItems().clear();
                        reserveTable.setItems(dr.getReserveList());
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
        MenuItem repairItem = new MenuItem("В ремонт");
        repairItem.setOnAction((ActionEvent e) -> {
            setSelectedReserve(reserveTable.getSelectionModel().getSelectedItem());
            if (getSelectedReserve()!=null){
                System.out.println("Передать купол "+getSelectedReserve().getReserveModel()+"-"+getSelectedReserve().getReserveSize()+" в ремонт?");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждение изменений");
                confirm.setHeaderText("Передать купол ПЗ " + getSelectedReserve().getReserveModel() +"-"+ getSelectedReserve().getReserveSize()+" в ремонт?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                    if (option.get() == null) {
                    } else if (option.get() == yes) {
                        dr.setStatusReserve(getSelectedReserve(),2);
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Внимание!");
                        info.setHeaderText(null);
                        info.setContentText("Купол ПЗ передан в ремонт!");
                        info.showAndWait();
                        System.out.println("Купол ПЗ передан в ремонт!");
                        reserveTable.getItems().clear();
                        reserveTable.setItems(dr.getReserveList());
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
        reserveContextMenu.getItems().addAll(refreshList, viewItem, new SeparatorMenuItem(), addItem, editItem, moveItem);
        switch (getStatus()){
            case 0:
                reserveContextMenu.getItems().addAll(deleteItem,repairItem);
                break;
            case 1:
                reserveContextMenu.getItems().addAll(restoreItem,repairItem);
                break;
            case 2:
                reserveContextMenu.getItems().addAll(deleteItem,restoreItem);
                break;
        }
        reserveTable.setContextMenu(reserveContextMenu);
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
