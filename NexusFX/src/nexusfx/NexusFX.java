/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pluginloader.PluginLoader;

/**
 *
 * @author dboro
 */
public class NexusFX extends Application {
    
    String pluginPath = "C:\\Users\\dboro\\Desktop\\Nexus\\NexusFX\\plugins";
    PluginLoader classLoader = new PluginLoader(); 
    
    @Override
    public void start(Stage primaryStage) {
        MenuBar MenuBarMain = new MenuBar();
        // --- Menu Меню и элементы
        Menu menuMain = new Menu("Меню");
        MenuItem clear = new MenuItem("Clear");
        clear.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        clear.setOnAction((ActionEvent t) -> {
            //vbox.setVisible(false);
        });
        MenuItem exit = new MenuItem("Выход");
        clear.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        menuMain.getItems().addAll(clear, new SeparatorMenuItem(), exit);
        
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
            String fileName = "TestModule";
            String className = "TestModule";
            
            /*loader.loadClass (pluginPath, fileName, className);
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
        });
        
        
        classLoader.fillLists(pluginPath);
        
        BorderPane root = new BorderPane();
        root.setTop(MenuBarMain);
        root.setLeft(new Button("Left"));
        root.setRight(new Button("Right"));
        root.setBottom(new Button("Bottom"));
        root.setCenter(new Button("Center"));
        root.setCenter(btn);
        
        Scene scene = new Scene(root, 600, 400);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
