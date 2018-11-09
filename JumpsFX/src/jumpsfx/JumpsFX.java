/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jumpsfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author dboro
 */
public class JumpsFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        BorderPane root = jumpsWindow();
//        root.getChildren().add(btn);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public BorderPane jumpsWindow(){
        BorderPane window = new BorderPane();
        //ButtonPane
        Accordion menu = new Accordion(); 
        TitledPane jumps = new TitledPane();
        jumps.setText("Запись/редактирование прыжков");
        
        TitledPane reports = new TitledPane();
        reports.setText("Отчеты");
        
        TitledPane graphics = new TitledPane();
        graphics.setText("Графики");
        
        
        menu.getPanes().addAll(jumps, reports, graphics);
        //CentralPane
        AnchorPane welcomePane = new AnchorPane();
        welcomePane.getChildren().add(new Label("This is default welcome text"));
        
        window.setCenter(welcomePane);
        window.setLeft(menu);
        return window;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
