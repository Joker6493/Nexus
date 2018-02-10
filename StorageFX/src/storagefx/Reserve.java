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

    public Reserve(int systemID, int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int manufacturerID, int reserveJumps, String reservePackDate) {
        super(systemID, reserveID, reserveModel, reserveSize, reserveSN, reserveDOM, manufacturerID, reserveJumps, reservePackDate);
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
