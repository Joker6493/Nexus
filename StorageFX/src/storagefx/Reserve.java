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
public class Reserve extends SkydiveSystem {
    //ПЗ
    protected int manufacturerID;
    
    protected String manufacturerName;
    
    public void Reserve (int systemID, int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int manufacturerID, int reserveJumps, String reservePackDate){
        this.systemID = systemID; 
        this.reserveID = reserveID;
        this.reserveModel = reserveModel;
        this.reserveSize = reserveSize;
        this.reserveSN = reserveSN;
        this.reserveDOM = reserveDOM;
        this.manufacturerID = manufacturerID;
        this.reserveJumps = reserveJumps;
        this.reservePackDate = reservePackDate;
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
