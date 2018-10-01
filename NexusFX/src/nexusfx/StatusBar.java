/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import logger.Logger;
import utils.ConnectionCheck;

/**
 *
 * @author d.borodin
 */
public class StatusBar {
    private Logger logger = new Logger();
//Task Bar
    private HBox taskBar = new HBox(5);
//Status Bar
    public GridPane init(){
        GridPane statusBar = new GridPane();

        ConnectionCheck connStatus = new ConnectionCheck();
        connStatus.bindToTime();
        Label statusLabel = new Label(logger.readLastLog());
        statusLabel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue.equalsIgnoreCase(logger.readLastLog())){
                statusLabel.setText(oldValue);
            }else{
                statusLabel.setText(newValue); 
            }
        });
        Button menuButton = new Button("Menu");
        statusBar.add(menuButton, 0, 0);
        statusBar.add(taskBar, 1, 0);
        statusBar.add(statusLabel, 2, 0);
        statusBar.add(connStatus, 3, 0);
        
        ColumnConstraints menuCol = new ColumnConstraints(Control.USE_COMPUTED_SIZE);
        ColumnConstraints buttonCol = new ColumnConstraints();
        buttonCol.setPercentWidth(70);
        buttonCol.setHgrow(Priority.ALWAYS);
        ColumnConstraints logCol = new ColumnConstraints();
        logCol.setPercentWidth(20);
        logCol.setHgrow(Priority.ALWAYS);
        ColumnConstraints statusCol = new ColumnConstraints(Control.USE_COMPUTED_SIZE);
        statusBar.getColumnConstraints().addAll(menuCol, buttonCol, logCol, statusCol);
        
        return statusBar;
    }
    public void AddTask(String name, Class cls, Object obj, BorderPane parent, Node scene){
        int id = 0;
        Button btn = new Button(name);
        btn.setOnAction((ActionEvent event) -> {
            parent.setCenter(scene);
            
        });
        taskBar.getChildren().add(btn);
        taskBar.getChildren().indexOf(btn);
        id = taskBar.getChildren().indexOf(btn);
        try {
        //Call a method dynamically (Reflection)
                Class params[] = {int.class};
                Method RemoveMethod = cls.getDeclaredMethod("setBtnID", params);
                RemoveMethod.invoke(obj, id);
            }catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                System.out.println("Ошибка: " + e.getMessage());
                e.printStackTrace();
            }
    }
    public void RemoveTask(int id){
        taskBar.getChildren().remove(id);
    }
}
