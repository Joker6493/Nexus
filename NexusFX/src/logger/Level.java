/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;


/**
 *
 * @author d.borodin
 */
public class Level {
    private int levelID;
    private String levelName;

    public int getLevelID() {
        return levelID;
    }
    public String getLevelName() {
        return levelName;
    }

    private Level (String levelName, int levelID){
        this.levelName = levelName;
        this.levelID = levelID;
    };
    
    public static final Level FATAL = new Level ("Fatal error",6);
    public static final Level SEVERE = new Level ("Severe error",5);
    public static final Level WARNING = new Level ("Warning log",4);
    public static final Level CONFIG = new Level ("Config log",3);
    public static final Level INFO = new Level ("Info log",2);
    public static final Level SUCCESS = new Level ("Success log",1);
    public static final Level DEBUG = new Level ("Debug log",0);
    
}
