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
public class Canopy extends SkydiveSystem {
    //ОП
    protected int manufacturerID;
    
    protected String manufacturerName;
    
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
    public int get_manufacturerID (){
        return manufacturerID;
    }
    //Set methods 
    public void set_manufacturerID (int manufacturerID){
        this.manufacturerID = manufacturerID;
    }
}
