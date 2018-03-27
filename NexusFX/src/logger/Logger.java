/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author d.borodin
 */
public class Logger {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss ");
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
    private final DateTimeFormatter zoneDateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
    private final String logDir="C:\\Users\\dboro\\Desktop\\Nexus\\NexusFX";
    private String lastLogRecord;
    private String logRecord;
    
    public String getLastLogRecord() {
        return lastLogRecord;
    }
    
    public void writeLog(Level level, String logText) throws IOException{
        //Date Time Level:Text
        logRecord=zoneDateTimeFormat.format(ZonedDateTime.now()).concat(" ").concat(level.getLevelName()).concat(": ").concat(logText);
        File logFile = new File(logDir.concat("\\log.txt"));
        if (!logFile.exists()){
            logFile.createNewFile();
        }
        FileWriter logger = new FileWriter(logFile);
        logger.write(logRecord.concat("/n"));
        logger.close();
        lastLogRecord=logRecord;
    }
    public void readLog() throws Exception{
        File logFile = new File(logDir.concat("\\log.txt"));
        FileReader readLogFile = new FileReader (logFile);
        Scanner scan = new Scanner(readLogFile);
        
    }
    
    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter zoneDateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
        System.out.println(dateTimeFormat.format(ZonedDateTime.now()));
        System.out.println(zoneDateTimeFormat.format(ZonedDateTime.now()));

    }
    
}
