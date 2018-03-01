/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import api.NexusPlugin;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

/**
 *
 * @author dboro
 */
public class StorageIndex extends Application implements NexusPlugin {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Override
    public void start(Stage primaryStage) throws SQLException {
        
        StackPane index = StorageIndex();
        Scene scene = new Scene(index);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public StackPane StorageIndex () throws SQLException{
        StackPane index = new StackPane();
        TableView<SkydiveSystem> indexStore = new TableView<>();
        //Columns
        TableColumn <SkydiveSystem, String> systemCode = new TableColumn<>("Код системы");
        TableColumn <SkydiveSystem, String> systemModel = new TableColumn<>("Модель системы");
        TableColumn <SkydiveSystem, String> canopy = new TableColumn<>("Основной парашют");
        TableColumn <SkydiveSystem, String> canopyModel = new TableColumn<>("Модель");
        TableColumn <SkydiveSystem, Integer> canopySize = new TableColumn<>("Размер, кв.фут");
        canopy.getColumns().addAll(canopyModel,canopySize);
        TableColumn <SkydiveSystem, String> reserve = new TableColumn<>("Запасной парашют");
        TableColumn <SkydiveSystem, String> reserveModel = new TableColumn<>("Модель");
        TableColumn <SkydiveSystem, Integer> reserveSize = new TableColumn<>("Размер, кв.фут");
        reserve.getColumns().addAll(reserveModel,reserveSize);
        TableColumn <SkydiveSystem, String> aadModel = new TableColumn<>("Модель AAD");
        TableColumn <SkydiveSystem, Integer> canopyJumps = new TableColumn<>("Прыжков на куполе");
        TableColumn <SkydiveSystem, LocalDate> reservePackDate = new TableColumn<>("Дата переукладки");
        //Adding columns into TableView
        indexStore.getColumns().addAll(systemCode,systemModel,canopy,reserve,aadModel,canopyJumps,reservePackDate);
        //Getting values and format from class variables
        systemCode.setCellValueFactory(new PropertyValueFactory<>("systemCode"));
        systemModel.setCellValueFactory(new PropertyValueFactory<>("systemModel"));
        canopyModel.setCellValueFactory(new PropertyValueFactory<>("canopyModel"));    
        canopySize.setCellValueFactory(new PropertyValueFactory<>("canopySize"));
        reserveModel.setCellValueFactory(new PropertyValueFactory<>("reserveModel"));
        reserveSize.setCellValueFactory(new PropertyValueFactory<>("reserveSize"));
        aadModel.setCellValueFactory(new PropertyValueFactory<>("aadModel"));        
        canopyJumps.setCellValueFactory(new PropertyValueFactory<>("canopyJumps"));        
        reservePackDate.setCellValueFactory(new PropertyValueFactory<>("reservePackDate"));
        // Custom rendering of the table cell.
        reservePackDate.setCellFactory(column -> {
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
        DataRelay dr = new DataRelay();
        ObservableList<SkydiveSystem> indexList = dr.getSystemsList();
        indexStore.setItems(indexList);
        indexStore.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView SkydiveSystem object
                SkydiveSystem currentSystem = indexStore.getSelectionModel().getSelectedItem();
                //TODO list
                System.out.println("Выбрана система "+currentSystem.getSystemCode()+"!");
                SystemDetails detail = new SystemDetails(indexStore.getSelectionModel().getSelectedItem());
                Stage detailStage = new Stage();
                detailStage.initModality(Modality.WINDOW_MODAL);
                detailStage.initOwner(index.getScene().getWindow());
                detail.start(detailStage);
            }
        });
        
        ContextMenu storageContextMenu = new ContextMenu();
        MenuItem refreshList = new MenuItem("Обновить список");
        MenuItem editItem = new MenuItem("Обновить список");
        MenuItem addItem = new MenuItem("Обновить список");
        MenuItem deleteItem = new MenuItem("Обновить список");
        storageContextMenu.getItems().addAll(refreshList, new SeparatorMenuItem(), addItem, editItem, deleteItem);
//        indexStore.setOnContextMenuRequested((ContextMenuEvent event) -> {
//            storageContextMenu.show(storageContextMenu, event.getScreenX(), event.getScreenY());
//        });
        
        index.getChildren().add(indexStore);
        return index;
    }
    
    @Override
    public void invoke(){
        //plugin TODO - in process
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
