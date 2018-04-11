 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author dboro
 */
public class LoadCalcFX extends Application {
    
    @Override
    public void start(Stage CalcStage) {
    //Labels    
        Label WeightWEqLabel = new Label("Вес парашютиста \nсо снаряжением, кг.");
        WeightWEqLabel.setWrapText(true);
        Label WeightSysLabel = new Label("Вес системы, кг.");
        Label CanopySizeLabel = new Label("Площадь купола, \nкв. фут.");
        CanopySizeLabel.setWrapText(true);
    //TextFields
        TextField WeightWEq = new TextField();
        WeightWEq.setPromptText("Ваш вес со снаряжением");
        WeightWEq.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                WeightWEq.setText(oldValue);
            }
        });
        TextField WeightSys = new TextField();
        WeightSys.setPromptText("Вес Вашей системы");
        WeightSys.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                WeightSys.setText(oldValue);
            }
        });
        TextField CanopySize = new TextField();
        CanopySize.setPromptText("Площадь купола");
        CanopySize.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                CanopySize.setText(oldValue);
            }
        });
        TextField CanopyLoad = new TextField();
        CanopyLoad.setPromptText("Ваша загрузка");
        CanopyLoad.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                CanopyLoad.setText(oldValue);
            }
        });
    //Buttons    
        Button calcLoad = new Button();
        calcLoad.setText("Рассчитать загрузку");
        calcLoad.setOnAction((ActionEvent event) -> {
            double WWE, WS, CS, CL;
            WWE = Double.parseDouble(WeightWEq.getText());
            WS = Double.parseDouble(WeightSys.getText());
            CS = Double.parseDouble(CanopySize.getText());
            CL = new BigDecimal(((WWE+WS)*2.2)/CS).setScale(2, RoundingMode.CEILING).doubleValue();
            CanopyLoad.setText(String.valueOf(CL));
        });
        Button clear = new Button();
        clear.setText("Очистить");
        clear.setOnAction((ActionEvent event) -> {
            WeightWEq.clear();
            WeightSys.clear();
            CanopySize.clear();
            CanopyLoad.clear();
        });
        Button close = new Button();
        close.setText("Закрыть");
        close.setOnAction((ActionEvent event) -> {
            CalcStage.close();
        });
    //Window    
        GridPane root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setGridLinesVisible(false);
        root.add(WeightWEqLabel, 0, 0);
        root.add(WeightSysLabel, 0, 1);
        root.add(CanopySizeLabel, 0, 2);
        root.add(calcLoad, 0, 3);
        root.add(WeightWEq, 1, 0);
        root.add(WeightSys, 1, 1);
        root.add(CanopySize, 1, 2);
        root.add(CanopyLoad, 1, 3);
        root.add(clear, 0, 4);
        root.setHalignment(clear, HPos.RIGHT);
        root.add(close, 1, 4);
        root.setVgap(5);
        root.setHgap(10);
        
        //root.getChildren().add(clear);
        
        Scene scene = new Scene(root);
        
        CalcStage.setTitle("Расчет загрузки");
        CalcStage.setScene(scene);
        CalcStage.setResizable(false);
        CalcStage.show();
    }
    
}
