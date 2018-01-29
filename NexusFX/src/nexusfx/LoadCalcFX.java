/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        Label WeightWEqLabel = new Label("Вес парашютиста со снаряжением, кг.");
        WeightWEqLabel.setWrapText(true);
        WeightWEqLabel.setPrefSize(115, 35);
        Label WeightSysLabel = new Label("Вес системы, кг.");
        WeightSysLabel.setPrefSize(115, 35);
        Label CanopySizeLabel = new Label("Площадь купола, кв. фут.");
        CanopySizeLabel.setPrefSize(115, 35);
        CanopySizeLabel.setWrapText(true);
    //TextFields
        TextField WeightWEq = new TextField();
        WeightWEq.setPrefWidth(115);
        WeightWEq.setPromptText("Ваш вес со снаряжением");
        WeightWEq.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    WeightWEq.setText(oldValue);
                }
            }
        });
        TextField WeightSys = new TextField();
        WeightSys.setPrefWidth(115);
        WeightSys.setPromptText("Вес Вашей системы");
        WeightSys.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                    WeightSys.setText(oldValue);
                }
            }
        });
        TextField CanopySize = new TextField();
        CanopySize.setPrefWidth(115);
        CanopySize.setPromptText("Площадь купола");
        CanopySize.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                    CanopySize.setText(oldValue);
                }
            }
        });
        TextField CanopyLoad = new TextField();
        CanopyLoad.setPrefWidth(115);
        CanopyLoad.setPromptText("Ваша загрузка");
        CanopyLoad.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                    CanopyLoad.setText(oldValue);
                }
            }
        });
    //Buttons    
        Button calcLoad = new Button();
        calcLoad.setText("Рассчитать загрузку");
        calcLoad.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                double WWE, WS, CS, CL;
                WWE = Double.parseDouble(WeightWEq.getText());
                WS = Double.parseDouble(WeightSys.getText());
                CS = Double.parseDouble(CanopySize.getText());
                CL = new BigDecimal(((WWE+WS)*2.2)/CS).setScale(2, RoundingMode.CEILING).doubleValue();
                CanopyLoad.setText(String.valueOf(CL));
            }
        });
        Button clear = new Button();
        clear.setPrefWidth(70);
        clear.setText("Очистить");
        clear.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                WeightWEq.clear();
                WeightSys.clear();
                CanopySize.clear();
                CanopyLoad.clear();
            }
        });
        Button close = new Button();
        close.setPrefWidth(70);
        close.setText("Закрыть");
        close.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                CalcStage.close();
            }
        });
    //Window    
        GridPane root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setGridLinesVisible(false);
        GridPane.setConstraints(WeightWEqLabel, 0, 0);
        GridPane.setConstraints(WeightSysLabel, 0, 1);
        GridPane.setConstraints(CanopySizeLabel, 0, 2);
        GridPane.setConstraints(calcLoad, 0, 3);
        GridPane.setConstraints(WeightWEq, 1, 0);
        GridPane.setConstraints(WeightSys, 1, 1);
        GridPane.setConstraints(CanopySize, 1, 2);
        GridPane.setConstraints(CanopyLoad, 1, 3);
        GridPane.setConstraints(clear, 0, 4);
        GridPane.setHalignment(clear, HPos.RIGHT);
        GridPane.setConstraints(close, 1, 4);
        root.getChildren().addAll(WeightWEqLabel, WeightSysLabel, CanopySizeLabel, calcLoad, WeightWEq, WeightSys, CanopySize, CanopyLoad, clear, close);
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
