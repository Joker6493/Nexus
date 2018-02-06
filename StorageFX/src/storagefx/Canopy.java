/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

/**
 *
 * @author dboro
 */
public class Canopy extends Container{
    //ОП
    protected int systemID;
    protected int canopyID;
    protected String canopyModel;
    protected int canopySize;
    protected String canopySN;
    protected String canopyDOM;
    protected int manufacturerID;
    protected int canopyJumps;
    
    public void Canopy (int systemID, int canopyID, String canopyModel, int canopySize, String canopySN, String canopyDOM, int manufacturerID, int canopyJumps){
        this.systemID = systemID; 
        this.canopyID = canopyID;
        this.canopyModel = canopyModel;
        this.canopySize = canopySize;
        this.canopySN = canopySN;
        this.canopyDOM = canopyDOM;
        this.manufacturerID = manufacturerID;
        this.canopyJumps = canopyJumps;
        }
    //Get methods
    public int get_systemID (){
        return systemID;
    }
    public int get_canopyID (){
        return canopyID;
    }
    public String get_canopyModel (){
        return canopyModel;
    }
    public int get_canopySize (){
        return canopySize;
    }
    public String get_canopySN (){
        return canopySN;
    }
    public String get_canopyDOM (){
        return canopyDOM;
    }
    public int get_manufacturerID (){
        return manufacturerID;
    }
    public int get_canopyJumps (){
        return canopyJumps;
    }
    //Set methods
    public void set_systemID (int systemID){
        this.systemID = systemID;
    }
    public void set_canopyID (int canopyID){
        //In process
    }
    public void set_canopyModel (String canopyModel){
        this.canopyModel = canopyModel;
    }
    public void set_canopySize (int canopySize){
        this.canopySize = canopySize;;
    }
    public void set_canopySN (String canopySN){
        this.canopySN = canopySN;
    }
    public void set_canopyDOM (String canopyDOM){
        this.canopyDOM = canopyDOM;
    }
    public void set_manufacturerID (int manufacturerID){
        this.manufacturerID = manufacturerID;
    }
    public void set_canopyJumps (int canopyJumps){
        this.canopyJumps = canopyJumps;
    }
}
