/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author d.borodin
 */
public class Logger {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss ");
    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
    DateTimeFormatter zoneDateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
    private ArrayList<String> logLevel = new ArrayList<>();
    
    
    
    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter zoneDateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
        System.out.println(dateTimeFormat.format(ZonedDateTime.now()));
        System.out.println(zoneDateTimeFormat.format(ZonedDateTime.now()));

    }
    
}
