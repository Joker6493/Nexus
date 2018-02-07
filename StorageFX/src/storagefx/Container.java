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
public class Container extends SkydiveSystem {
    //Система
    protected int systemID;
    protected String systemCode;
    protected String systemModel;
    protected String systemSN;
    protected String systemDOM;
    protected int manufacturerID;
    protected int canopyID;
    protected int reserveID;
    protected int aadID;

    
    public void Container (int systemID, String systemCode, String systemModel, String systemSN, String systemDOM, int manufacturerID, int canopyID, int reserveID, int aadID){
        this.systemID = systemID; 
        this.systemCode = systemCode;
        this.systemModel = systemModel;
        this.systemSN = systemSN;
        this.systemDOM = systemDOM;
        this.manufacturerID = manufacturerID;
        this.canopyID = canopyID;
        this.reserveID = reserveID;
        this.aadID = aadID;
        }
    //Get methods
    public int get_systemID (){
        return systemID;
    }
    public String get_systemCode (){
        return systemCode;
    }
    public String get_systemModel (){
        return systemModel;
    }
    public String get_systemSN (){
        return systemSN;
    }
    public String get_systemDOM (){
        return systemDOM;
    }
    public int get_manufacturerID (){
        return manufacturerID;
    }
    public int get_canopyID (){
        return manufacturerID;
    }
    public int get_reserveID (){
        return reserveID;
    }
    public int get_aadID (){
        return aadID;
    }
    //Set methods 
    public void set_manufacturerID (){
        //In process
    }
    public void set_systemID (int systemID){
        this.systemID = systemID;
    }
    public void set_systemCode (String systemCode){
        this.systemCode = systemCode;
    }
    public void set_systemModel (String systemModel){
        this.systemModel = systemModel;
    }
    public void set_systemSN (String systemSN){
        this.systemSN = systemSN;
    }
    public void set_systemDOM (String systemDOM){
        this.systemDOM = systemDOM;
    }
    public void set_manufacturerID (int manufacturerID){
        this.manufacturerID = manufacturerID;
    }
    public void set_canopyID (int canopyID){
        this.canopyID = canopyID;
    }
    public void set_reserveID (int reserveID){
        this.reserveID = reserveID;
    }
    public void set_aadID (int aadID){
        this.aadID = aadID;
    }
}