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
    protected int manufacturerID;
    
    protected String manufacturerName;
        
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
    public int get_manufacturerID (){
        return manufacturerID;
    }
    //Set methods 
    public void set_manufacturerID (int manufacturerID){
        this.manufacturerID = manufacturerID;
    }
}