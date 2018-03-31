/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author d.borodin
 */
public class Logger {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss ");
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
    private final DateTimeFormatter zoneDateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
    private final String logDir=System.getProperty("user.dir");
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
        try (FileWriter logger = new FileWriter(logFile,true)) {
            logger.write(logRecord.concat(System.lineSeparator()));
            logger.flush();
            logger.close();
        }
        lastLogRecord=logRecord;
    }
    public String readLastLog() throws Exception{
        String logs;
        String logRecord = null;
        File logFile = new File(logDir.concat("\\log.txt"));
        try (FileReader readLogFile = new FileReader (logFile); BufferedReader buffReader = new BufferedReader(readLogFile)) {
            while ((logs = buffReader.readLine())!= null){
                logRecord = logs;
            }
        buffReader.close();
        readLogFile.close();
        }
        return logRecord;
    }
    public String readNLog(int n) throws Exception{
        ArrayList <String> logRecordListFull = new ArrayList<>();
        String logs;
        String logRecordN = null;
        File logFile = new File(logDir.concat("\\log.txt"));
        try (FileReader readLogFile = new FileReader (logFile); BufferedReader buffReader = new BufferedReader(readLogFile)) {
            while ((logs = buffReader.readLine()) != null){
                logRecordListFull.add(logs);
            }
            buffReader.close();
            readLogFile.close();
            logRecordN = logRecordListFull.get(logRecordListFull.size()-n-1);
//            System.out.println(logRecordListFull.get(logRecordListFull.size()-n-1));
            }
        return logRecordN;
    }
    //Main method for class tests
    public static void main(String[] args) throws IOException, Exception {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter zoneDateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
        System.out.println(dateTimeFormat.format(ZonedDateTime.now()));
        System.out.println(zoneDateTimeFormat.format(ZonedDateTime.now()));
        Logger log = new Logger();
        log.writeLog(Level.FATAL,"This is FATAL log record example");
        log.writeLog(Level.SEVERE, "This is SEVERE log record example");
        log.writeLog(Level.WARNING, "This is WARNING log record example");
        log.writeLog(Level.INFO, "This is INFO log record example");
        log.writeLog(Level.CONFIG, "This is CONFIG log record example");
        log.writeLog(Level.SUCCESS, "This is SUCCESS log record example");
        log.writeLog(Level.DEBUG, "This is DEBUG log record example");
        System.out.println(log.readLastLog());
        for (int i=10; i>0; i--){
            System.out.println(log.readNLog(i));
        }
    }
}