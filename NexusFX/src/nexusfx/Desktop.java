/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
public class Desktop {
    private Logger logger = new Logger();
//Task Bar
    private HBox taskBar = new HBox(5);
//Windows list
    private Map<String,AnchorPane> windowList = new LinkedHashMap<>();
    private Map<String,Integer> btnIDList = new LinkedHashMap<>();
    private AnchorPane currentWindow = new AnchorPane();
    public AnchorPane getCurrentWindow() {
        return currentWindow;
    }
    public void setCurrentWindow(AnchorPane currentWindow) {
        this.currentWindow = currentWindow;
    }
    
    public void addWindow(String name, Node scene, BorderPane parent){
        if (!windowList.containsKey(name)){
            AnchorPane newWindow = new AnchorPane();
            newWindow.getChildren().add(scene);
            windowList.put(name, newWindow);
            setCurrentWindow(newWindow);
            btnIDList.put(name, addTask(name, getCurrentWindow(), parent));
        }else{
            setCurrentWindow(windowList.get(name));
        }
    }
    public void closeWindow (String name){
        windowList.remove(name);
        removeTask(btnIDList.get(name));
        btnIDList.remove(name);
        Entry<String,AnchorPane>[] set = new Entry[windowList.size()];
        windowList.entrySet().toArray(set);
        setCurrentWindow(windowList.get(set[windowList.size()-1].getKey()));
    }
//Status Bar
    public GridPane statusBar(){
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
    public int addTask(String name, AnchorPane window, BorderPane parent){
        int id = 0;
        Button btn = new Button(name);
        btn.setOnAction((ActionEvent event) -> {
            parent.setCenter(window);
        });
        taskBar.getChildren().add(btn);
        id = taskBar.getChildren().indexOf(btn);
        return id;
    }
    public void removeTask(int id){
        taskBar.getChildren().remove(id);
    }
}
