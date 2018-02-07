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

/**
 *
 * @author dboro
 */
public class StorageIndex extends Application implements NexusPlugin {
    
    @Override
    public void start(Stage primaryStage) {
        
        StackPane index = StorageIndex();
        Scene scene = new Scene(index, 600, 400);
        
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public StackPane StorageIndex (){
        
        TableView<SkydiveSystem> indexStore = new TableView<SkydiveSystem>();
        //Столбцы
        TableColumn <SkydiveSystem, String> systemCode = new TableColumn("Код системы");
        TableColumn <SkydiveSystem, String> systemModel = new TableColumn("Модель системы");
        TableColumn <SkydiveSystem, String> canopy = new TableColumn("Основной парашют");
        TableColumn <SkydiveSystem, String> canopyModel = new TableColumn("Модель");
        TableColumn <SkydiveSystem, Integer> canopySize = new TableColumn("Размер, кв.фут");
        canopy.getColumns().addAll(canopyModel,canopySize);
        TableColumn <SkydiveSystem, String> reserve = new TableColumn("Запасной парашют");
        TableColumn <SkydiveSystem, String> reserveModel = new TableColumn("Модель");
        TableColumn <SkydiveSystem, Integer> reserveSize = new TableColumn("Размер, кв.фут");
        reserve.getColumns().addAll(reserveModel,reserveSize);
        TableColumn <SkydiveSystem, String> aadModel = new TableColumn("Модель AAD");
        TableColumn <SkydiveSystem, Integer> canopyJumps = new TableColumn("Прыжков на куполе");
        TableColumn <SkydiveSystem, String> reservePackDate = new TableColumn("Дата переукладки");
        //Добавляем столбцы в таблицу
        indexStore.getColumns().addAll(systemCode,systemModel,canopy,reserve,aadModel,canopyJumps,reservePackDate);
        
        //Устанавливаем тип и значение которое должно хранится в колонке
        systemCode.setCellValueFactory(new PropertyValueFactory<>("systemCode"));
        systemModel.setCellValueFactory(new PropertyValueFactory<>("systemModel"));
        canopyModel.setCellValueFactory(new PropertyValueFactory<>("canopyModel"));    
        canopySize.setCellValueFactory(new PropertyValueFactory<>("canopySize"));
        reserveModel.setCellValueFactory(new PropertyValueFactory<>("reserveModel"));
        reserveSize.setCellValueFactory(new PropertyValueFactory<>("reserveSize"));
        aadModel.setCellValueFactory(new PropertyValueFactory<>("aadModel"));        
        canopyJumps.setCellValueFactory(new PropertyValueFactory<>("systemcanopyJumpsCode"));        
        reservePackDate.setCellValueFactory(new PropertyValueFactory<>("reservePackDate"));        
        
        
        
        StackPane index = new StackPane();
        index.getChildren().add(indexStore);
        
        return index;
    }
    
    @Override
    public void invoke(){
        
    }
    
    
    /*private ObservableList<UserAccount> getUserList() {
 
      UserAccount user1 = new UserAccount(1L, "smith", "smith@gmail.com", //
              "Susan", "Smith", true);
      UserAccount user2 = new UserAccount(2L, "mcneil", "mcneil@gmail.com", //
              "Anne", "McNeil", true);
      UserAccount user3 = new UserAccount(3L, "white", "white@gmail.com", //
              "Kenvin", "White", false);
 
      ObservableList<UserAccount> list = FXCollections.observableArrayList(user1, user2, user3);
      return list;*/
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
