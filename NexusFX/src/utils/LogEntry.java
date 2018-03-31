/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;


/**
 *
 * @author dboro
 */
public class LogEntry extends Label {
    private static String statusConn = "Off-line";
    SAMConn db = new SAMConn();
    Connection dbconn =db.connectDatabase();
    public LogEntry()
    {
        bindToTime();
    }

    public void bindToTime() {
        this.setText("Статус: "+statusConn);
        Timeline timeline = new Timeline(new KeyFrame (
        Duration.seconds(5),
        ae -> { try {
            if (dbconn.isValid(10)) {
                statusConn = "On-line";
                this.setText("Статус: "+statusConn);
            }
            } catch (SQLException ex) {
                System.out.println("Ошибка " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    ));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
