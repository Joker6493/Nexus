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
public class AADList extends Application {
        
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private int stockID;
    private int status;
    private StackPane table;
    private DataRelay dr;
    @Override
    public void start(Stage primaryStage) throws SQLException {
        StackPane index = AADTable();
        Scene scene = new Scene(index);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public StackPane AADTable(){
        StackPane index = new StackPane();
        TableView<AAD> aadTable = new TableView<>();
        //Columns
        //AAD(systemID, aadID, aadModel, aadSN, aadDOM, aadJumps, aadNextRegl, aadSaved, aadManufacturerID, aadManufacturerName, stockID)
        TableColumn <AAD, String> aadModel = new TableColumn<>("Модель");
        TableColumn <AAD, String> aadSN = new TableColumn<>("Серийный номер");
        TableColumn <AAD, LocalDate> aadDOM = new TableColumn<>("Дата производства");
        TableColumn <AAD, Integer> aadJumps = new TableColumn<>("Прыжков на приборе");
        TableColumn <AAD, LocalDate> aadNextRegl = new TableColumn<>("Дата следующего регламента");
        TableColumn <AAD, Integer> aadSaved = new TableColumn<>("Применений");
        TableColumn <AAD, String> aadManufacturerName = new TableColumn<>("Производитель");
        //Adding columns into TableView
        aadTable.getColumns().addAll(aadModel,aadSN,aadDOM,aadJumps,aadNextRegl,aadSaved,aadManufacturerName);
        //Getting values and format from class variables
        aadModel.setCellValueFactory(new PropertyValueFactory<>("aadModel"));    
        aadSN.setCellValueFactory(new PropertyValueFactory<>("aadSN"));
        aadDOM.setCellValueFactory(new PropertyValueFactory<>("aadDOM"));
        aadJumps.setCellValueFactory(new PropertyValueFactory<>("aadJumps"));      
        aadNextRegl.setCellValueFactory(new PropertyValueFactory<>("aadNextRegl"));        
        aadSaved.setCellValueFactory(new PropertyValueFactory<>("aadSaved"));
        aadManufacturerName.setCellValueFactory(new PropertyValueFactory<>("aadManufacturerName"));
        //Rendering date cell
        aadDOM.setCellFactory(column -> {
            return new TableCell<AAD, LocalDate>() {
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
        aadNextRegl.setCellFactory(column -> {
            return new TableCell<AAD, LocalDate>() {
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
        aadTable.setItems(dr.getAadList());
        aadTable.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                //Get selected TableView Canopy object
                AAD currentAAD = aadTable.getSelectionModel().getSelectedItem();
//                System.out.println("Выбран прибор "+currentAAD.getAadModel()+" № "+currentAAD.getAadSN()+"!");
//                ElementDetails detail = new ElementDetails(currentAAD, false);
//                Stage detailStage = new Stage();
//                detailStage.initModality(Modality.WINDOW_MODAL);
//                detailStage.initOwner(index.getScene().getWindow());
//                detail.start(detailStage);
            }
        });
        ContextMenu aadContextMenu = new ContextMenu();
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
            AAD currentAAD = aadTable.getSelectionModel().getSelectedItem();
            System.out.println("информация о приборе "+currentAAD.getAadModel()+" № "+currentAAD.getAadSN());
            ElementDetails detail = new ElementDetails(currentAAD, false);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem editItem = new MenuItem("Редактировать");
        editItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            AAD currentAAD = aadTable.getSelectionModel().getSelectedItem();
            System.out.println("Редактируем прибор "+currentAAD.getAadModel()+" № "+currentAAD.getAadSN()+"?");
            ElementDetails detail = new ElementDetails(currentAAD, true);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem addItem = new MenuItem("Добавить");
        addItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            System.out.println("Добавить прибор?");
            ElementDetails detail = new ElementDetails("aad",stockID);
            Stage detailStage = new Stage();
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.initOwner(index.getScene().getWindow());
            detail.start(detailStage);
        });
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction((ActionEvent e) -> {
            //Refreshing indexList - in process
            AAD currentAAD = aadTable.getSelectionModel().getSelectedItem();
            System.out.println("Удалить прибор "+currentAAD.getAadModel()+" № "+currentAAD.getAadSN()+"?");
        });
        aadContextMenu.getItems().addAll(refreshList, viewItem, new SeparatorMenuItem(), addItem, editItem, deleteItem);
        aadTable.setOnContextMenuRequested((ContextMenuEvent event) -> {
            aadContextMenu.show(aadTable, event.getScreenX(), event.getScreenY());
        });
        index.getChildren().add(aadTable);
        return index;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
