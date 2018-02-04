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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
        TableView indexStore = new TableView();
        //Столбцы
        TableColumn systemCode = new TableColumn("Код системы");
        TableColumn systemModel = new TableColumn("Модель системы");
        TableColumn canopy = new TableColumn("Основной парашют");
        TableColumn canopyModel = new TableColumn("Модель");
        TableColumn canopySize = new TableColumn("Размер, кв.фут");
        canopy.getColumns().addAll(canopyModel,canopySize);
        TableColumn reserve = new TableColumn("Запасной парашют");
        TableColumn reserveModel = new TableColumn("Модель");
        TableColumn reserveSize = new TableColumn("Размер, кв.фут");
        reserve.getColumns().addAll(reserveModel,reserveSize);
        TableColumn aadModel = new TableColumn("Модель AAD");
        TableColumn canopyJumps = new TableColumn("Прыжков на куполе");
        TableColumn reservePackDate = new TableColumn("Дата переукладки");
        //Добавили в таблицу
        indexStore.getColumns().addAll(systemCode,systemModel,canopy,reserve,aadModel,canopyJumps,reservePackDate);
        
        StackPane index = new StackPane();
        index.getChildren().add(indexStore);
        
        return index;
    }
    
    @Override
    public void invoke(){
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
