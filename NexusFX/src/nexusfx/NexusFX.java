/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import java.lang.reflect.Method;
import java.sql.Connection;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logger.Level;
import logger.Logger;
import utils.ConnectionCheck;
import utils.SAMConn;
import utils.PluginLoader;

/**
 *
 * @author dboro
 */
public class NexusFX extends Application {
<<<<<<< HEAD
    String pluginPath = "C:\\Users\\dboro\\Desktop\\Nexus\\NexusFX\\plugins";
<<<<<<< HEAD
    PluginLoader loader = new PluginLoader();
        
=======
    PluginLoader classLoader = new PluginLoader(); 
    OracleConn oraconn = new OracleConn();
    Connection dbconn = oraconn.connectDatabase();
    String statusConn = null;
=======
    String pluginPath = System.getProperty("user.dir").concat("\\plugins");
    PluginLoader classLoader = new PluginLoader();
    Logger logger = new Logger();
    SAMConn mysqlconn = new SAMConn();
    Connection dbconn = mysqlconn.connectDatabase();
    String status = logger.readLastLog();
>>>>>>> Nexus_prealpha
    
>>>>>>> Nexus_prealpha
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        BorderPane root = new BorderPane();
        
        MenuBar MenuBarMain = new MenuBar();
        // --- Menu and elements
        Menu menuMain = new Menu("Меню");
        MenuItem storage = new MenuItem("Склад");
        storage.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        storage.setOnAction((ActionEvent t) -> {
            Class params[] = {};
            Object paramsObj[] = {};
            try {
                Class storageClass = classLoader.loadClass (pluginPath, "StorageFX", "storagefx.StorageIndex");
                Object storageObj = storageClass.newInstance();
                Method storageMethod = storageClass.getDeclaredMethod("StorageIndex", params);
                root.setCenter((BorderPane) storageMethod.invoke(storageObj, paramsObj));
            } catch (Exception e) {
            System.out.println("Ошибка " + e.getMessage());
            //e.printStackTrace();
        }
        });
        MenuItem clear = new MenuItem("Очистить");
        clear.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        clear.setOnAction((ActionEvent t) -> {
        });
        MenuItem exit = new MenuItem("Выход");
        clear.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        menuMain.getItems().addAll(storage, new SeparatorMenuItem(), clear, new SeparatorMenuItem(), exit);
        // --- Menu Edit
        Menu menuService = new Menu("Сервис");
        // --- Menu View
        Menu menuWindows = new Menu("Окно");
        // --- Menu View
        Menu menuHelp = new Menu("Помощь");
        MenuItem loadcalc = new MenuItem("Расчет загрузки купола");
        loadcalc.setOnAction((ActionEvent event) -> {
            LoadCalcFX calc = new LoadCalcFX();
            Stage calcStage = new Stage();
            calcStage.initModality(Modality.WINDOW_MODAL);
            calcStage.initOwner(primaryStage);
            calc.start(calcStage);
        });
        MenuItem testClass = new MenuItem("Проверка ClassLoader");
        testClass.setOnAction((ActionEvent event) -> {
<<<<<<< HEAD
            String fileName = "TestModule";
=======
            /*String fileName = "TestModule";
>>>>>>> Nexus_prealpha
            String className = "TestModule";
            
            loader.loadClass (pluginPath, fileName, className);
            Stage testStage = new Stage();
            testStage.initModality(Modality.WINDOW_MODAL);
            testStage.initOwner(primaryStage);
            clazz.start(testStage);*/
        });
        MenuItem about = new MenuItem("О программе...");
        menuHelp.getItems().addAll(loadcalc, new SeparatorMenuItem(), about, testClass);
        MenuBarMain.getMenus().addAll(menuMain, menuService, menuWindows, menuHelp);
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
            logger.writeLog(Level.SUCCESS, "Hello World!");
        });
        //Task Bar
        HBox taskBar = new HBox();
        taskBar.getChildren().addAll(new Button("Scene1"),new Button("Scene2"),new Button("Scene3"));
        //Status Bar
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
        
        ColumnConstraints columnButton = new ColumnConstraints();
        ColumnConstraints columnTask = new ColumnConstraints();
        columnTask.setPercentWidth(70);
        ColumnConstraints columnConnection = new ColumnConstraints();
        statusBar.getColumnConstraints().addAll(columnButton, columnTask, columnConnection);
        
        
        root.setTop(MenuBarMain);
        root.setLeft(/*new Button("Left")*/btn);
        root.setRight(new Button("Right"));
        root.setBottom(statusBar);
        //root.setCenter(new Button("Center"));
        //root.setCenter(btn);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
