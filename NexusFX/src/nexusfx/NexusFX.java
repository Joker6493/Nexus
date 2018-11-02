/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logger.Level;
import logger.Logger;
import utils.SAMConn;
import utils.PluginLoader;

/**
 *
 * @author dboro
 */
public class NexusFX extends Application {
    String pluginPath = System.getProperty("user.dir").concat("\\plugins");
    PluginLoader classLoader = new PluginLoader();
    Logger logger = new Logger();
    SAMConn mysqlconn = new SAMConn();
    Desktop desktop = new Desktop();
    //StatusBar desktop = new StatusBar();
    Connection dbconn = mysqlconn.connectDatabase();
    String status = logger.readLastLog();
    
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
                desktop.addTab("Склад", (BorderPane) storageMethod.invoke(storageObj, paramsObj));
            } catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            System.out.println("Ошибка " + e.getMessage());
            //e.printStackTrace();
        }
        });
        MenuItem clear = new MenuItem("Очистить");
        clear.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        clear.setOnAction((ActionEvent t) -> {
//            root.getChildren().remove(root.getCenter().getClip().getId());
        });
        MenuItem exit = new MenuItem("Выход");
        clear.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
        exit.setOnAction((ActionEvent t) -> {
            logger.writeLog(Level.INFO, "Завершение программы");
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
            logger.writeLog(Level.INFO, "Загрузка калькулятора загрузки");
            LoadCalcFX calc = new LoadCalcFX();
            Stage calcStage = new Stage();
            calcStage.initModality(Modality.WINDOW_MODAL);
            calcStage.initOwner(primaryStage);
            calc.start(calcStage);
            logger.writeLog(Level.SUCCESS, "Калькулятор загружен!");
        });
        MenuItem testClass = new MenuItem("Проверка ClassLoader");
        testClass.setOnAction((ActionEvent event) -> {
            /*String fileName = "TestModule";
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
        
        root.setTop(MenuBarMain);
//        root.setLeft(/*new Button("Left")*/btn);
//        root.setRight(new Button("Right"));
        root.setBottom(desktop.statusBar());
        root.setCenter(desktop.mainWindow());
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
