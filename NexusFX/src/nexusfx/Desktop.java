/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import logger.Level;
import logger.Logger;
import utils.ConnectionCheck;
import utils.DraggingTabPaneSupport;

/**
 *
 * @author d.borodin
 */
public class Desktop {
    private Logger logger = new Logger();
//Windows list
    private TabPane mainTab = new TabPane();
    private DraggingTabPaneSupport draggingTabPane = new DraggingTabPaneSupport();
    private Map<String,Tab> tabList = new LinkedHashMap<>();
    public TabPane getMainTab() {
        return mainTab;
    }
    public void setMainTab(TabPane mainTab) {
        this.mainTab = mainTab;
    }
    public TabPane mainWindow(){
        draggingTabPane.addSupport(mainTab);
        Tab welcomeTab = new Tab();
        welcomeTab.setText("Welcome");
        HBox welcomeBox = new HBox();
        welcomeBox.getChildren().add(new Label("Some welcome text here"));
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeTab.setContent(welcomeBox);
        getMainTab().getTabs().add(welcomeTab);
        return getMainTab();
    }
    public void addTab (String name, Node scene){
        if (tabList.containsKey(name)){
            getMainTab().getSelectionModel().select(tabList.get(name));
        }else{
            Tab tab = new Tab();
            tab.setText(name);
            tab.setContent(scene);
            tab.setOnCloseRequest((Event e) -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Подтверждите закрытия вкладки");
                confirm.setHeaderText("Вы уверены, что хотите закрыть вкладку \"" + name +"\"?");
                ButtonType yes = new ButtonType("Да");
                ButtonType no = new ButtonType("Нет");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(yes, no);
                Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == null) {
                } else if (option.get() == yes) {
                    getMainTab().getTabs().remove(tab);
                    tabList.remove(name);
                } else if (option.get() == no) {
                } else {
                }
            });
            tab.setOnClosed((Event e) -> {
                logger.writeLog(Level.INFO, "Владка \"" + name +"\" закрыта!");
            });
            getMainTab().getTabs().add(tab);
            getMainTab().getSelectionModel().select(tab);
            tabList.put(name, tab);
        }
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
        statusBar.add(statusLabel, 1, 0);
        statusBar.add(connStatus, 2, 0);
        
        ColumnConstraints menuCol = new ColumnConstraints(Control.USE_COMPUTED_SIZE);
        ColumnConstraints logCol = new ColumnConstraints();
        logCol.setHgrow(Priority.ALWAYS);
        ColumnConstraints statusCol = new ColumnConstraints(Control.USE_COMPUTED_SIZE);
        statusBar.getColumnConstraints().addAll(menuCol, logCol, statusCol);
        
        return statusBar;
    }
    
}
