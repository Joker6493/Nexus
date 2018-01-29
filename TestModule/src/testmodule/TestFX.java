package testmodule;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import api.NexusPlugin;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author d.borodin
 */
public class TestFX extends Application implements NexusPlugin {
    

    @Override
    public void start(Stage myStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Работает, мать твою!!!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        myStage.setTitle("Hello World!");
        myStage.setScene(scene);
        myStage.show();
    }

    @Override
    public void invoke() {
        System.out.println("Ежжиииии!");
        
    }
   
}
