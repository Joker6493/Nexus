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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ManufacturerList extends Application {
        
    private int status;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane index = ManufacturerList(false);
        Scene scene = new Scene(index);
        
        primaryStage.setTitle("Список производителей");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public BorderPane ManufacturerList (boolean closeOnSelect) {
        BorderPane index = new BorderPane();
        DataRelay dr = new DataRelay();
        dr.setStatus(getStatus());
        TableView<Manufacturer> manufacturerList = new TableView<>();
        //Columns
//        String manufacturerName, String manufacturerCountry, String manufacturerTelephone, String manufacturerEmail
        TableColumn <Manufacturer, String> manufacturerName = new TableColumn<>("Наименование");
        TableColumn <Manufacturer, String> manufacturerCountry = new TableColumn<>("Страна");
        TableColumn <Manufacturer, String> manufacturerTelephone = new TableColumn<>("Телефон");
        TableColumn <Manufacturer, String> manufacturerEmail = new TableColumn<>("E-mail");
        //Adding columns into TableView
        manufacturerList.getColumns().addAll(manufacturerName,manufacturerCountry,manufacturerTelephone,manufacturerEmail);
        //Getting values and format from class variables
        manufacturerName.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        manufacturerCountry.setCellValueFactory(new PropertyValueFactory<>("manufacturerCountry"));
        manufacturerTelephone.setCellValueFactory(new PropertyValueFactory<>("manufacturerTelephone"));    
        manufacturerEmail.setCellValueFactory(new PropertyValueFactory<>("manufacturerEmail"));
        //Adding data and create scene
        ObservableList<Manufacturer> indexList = dr.getManufacturerList();
        manufacturerList.setItems(indexList);
        manufacturerList.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView SkydiveSystem object
                Manufacturer selectedManufacturer = manufacturerList.getSelectionModel().getSelectedItem();
                //TODO list
                System.out.println("Выбран производитель " + selectedManufacturer.getManufacturerName() + "!");
                ElementDetails detail = new ElementDetails(selectedManufacturer, false);
                Stage detailStage = new Stage();
                detailStage.initModality(Modality.WINDOW_MODAL);
                detailStage.initOwner(index.getScene().getWindow());
                detail.start(detailStage);
            }
        });
        
        Button refreshBtn = new Button();
        refreshBtn.setText("Обновить");
        refreshBtn.setOnAction((ActionEvent event) -> {
            System.out.println("Идет обновление списка");
            manufacturerList.getItems().clear();
            manufacturerList.setItems(dr.getManufacturerList());
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
            manufacturerList.getItems().clear();
            manufacturerList.setItems(dr.getManufacturerList());
        });
        
        HBox storageBar = new HBox();
        storageBar.getChildren().addAll(statusBox, new Label("Статус системы"), refreshBtn);
        storageBar.setPadding(new Insets(10));
        index.setTop(storageBar);
        
        ContextMenu manufacturerContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        refreshList.setOnAction((ActionEvent e) -> {
            System.out.println("Идет обновление списка");
            manufacturerList.getItems().clear();
            manufacturerList.setItems(dr.getManufacturerList());
            System.out.println("Обновление списка завершено");
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            Manufacturer selectedManufacturer = manufacturerList.getSelectionModel().getSelectedItem();
            System.out.println("Редактировать производителя " + selectedManufacturer.getManufacturerName() + "?");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение изменений");
            confirm.setHeaderText("Редактировать производителя " + selectedManufacturer.getManufacturerName() + "?");
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == null) {
                } else if (option.get() == yes) {
                    ElementDetails detail = new ElementDetails(selectedManufacturer, true);
                    Stage detailStage = new Stage();
                    detailStage.initModality(Modality.WINDOW_MODAL);
                    detailStage.initOwner(index.getScene().getWindow());
                    detail.start(detailStage);
                } else if (option.get() == no) {
                } else {
                }
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить производителя?");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение изменений");
            confirm.setHeaderText("Добавить производителя?");
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == null) {
                } else if (option.get() == yes) {
                    ElementDetails detail = new ElementDetails("manufacturer",0);
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
            Manufacturer selectedManufacturer = manufacturerList.getSelectionModel().getSelectedItem();
            System.out.println("Удалить производителя " + selectedManufacturer.getManufacturerName() + "?");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение изменений");
            confirm.setHeaderText("Удалить производителя " + selectedManufacturer.getManufacturerName() + "?");
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == null) {
                } else if (option.get() == yes) {
                    dr.setStatusManufacturer(selectedManufacturer,1);
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Внимание!");
                    info.setHeaderText(null);
                    info.setContentText("Производитель удален!");
                    info.showAndWait();
                    System.out.println("Производитель удален!");
                    manufacturerList.getItems().clear();
                    manufacturerList.setItems(dr.getManufacturerList());
                } else if (option.get() == no) {
                } else {
                }
        });
        MenuItem restoreItem = new MenuItem("Восстановить");
        restoreItem.setOnAction((ActionEvent e) -> {
            Manufacturer selectedManufacturer = manufacturerList.getSelectionModel().getSelectedItem();
            System.out.println("Восстановить производителя " + selectedManufacturer.getManufacturerName() + "?");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение изменений");
            confirm.setHeaderText("Восстановить производителя " + selectedManufacturer.getManufacturerName() + "?");
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == null) {
                } else if (option.get() == yes) {
                    dr.setStatusManufacturer(selectedManufacturer,1);
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Внимание!");
                    info.setHeaderText(null);
                    info.setContentText("Производитель восстановлен!");
                    info.showAndWait();
                    System.out.println("Производитель восстановлен!");
                    manufacturerList.getItems().clear();
                    manufacturerList.setItems(dr.getManufacturerList());
                } else if (option.get() == no) {
                } else {
                }
        });
        manufacturerContextMenu.getItems().addAll(refreshList, new SeparatorMenuItem(), addItem, editItem);
        switch (getStatus()){
            case 0:
                manufacturerContextMenu.getItems().add(deleteItem);
                break;
            case 1:
                manufacturerContextMenu.getItems().add(restoreItem);
                break;
        }
        manufacturerList.setOnContextMenuRequested((ContextMenuEvent event) -> {
            manufacturerContextMenu.show(manufacturerList, event.getScreenX(), event.getScreenY());
        });
        
        index.setCenter(manufacturerList);
        
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
