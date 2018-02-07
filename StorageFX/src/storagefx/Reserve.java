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
    public int get_systemID (){
        return systemID;
    }
    public int get_reserveID (){
        return reserveID;
    }
    public String get_reserveModel (){
        return reserveModel;
    }
    public int get_reserveSize (){
        return reserveSize;
    }
    public String get_reserveSN (){
        return reserveSN;
    }
    public String get_reserveDOM (){
        return reserveDOM;
    }
    public int get_manufacturerID (){
        return manufacturerID;
    }
    public int get_reserveJumps (){
        return reserveJumps;
    }
    public String get_reservePackDate () {
        return reservePackDate;
    }
    //Set methods
    public void set_systemID (int systemID){
        this.systemID = systemID;
    }
    public void set_reserveID (int reserveID){
        //In process
    }
    public void set_reserveModel (String reserveModel){
        this.reserveModel = reserveModel;
    }
    public void set_reserveSize (int reserveSize){
        this.reserveSize = reserveSize;;
    }
    public void set_reserveSN (String reserveSN){
        this.reserveSN = reserveSN;
    }
    public void set_reserveDOM (String reserveDOM){
        this.reserveDOM = reserveDOM;
    }
    public void set_manufacturerID (int manufacturerID){
        this.manufacturerID = manufacturerID;
    }
    public void set_reserveJumps (int reserveJumps){
        this.reserveJumps = reserveJumps;
    }
    public void set_reservePackDate (String reservePackDate){
        this.reservePackDate = reservePackDate;
    }
}
