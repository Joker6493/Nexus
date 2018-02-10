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
public class AAD extends SkydiveSystem {
    //AAD
    protected int manufacturerID;
    protected String manufacturerName;

    public AAD(int systemID, int aadID, String aadModel, String aadSN, String aadDOM, int manufacturerID, int aadJumps, String aadNextRegl, boolean aadFired) {
        super(systemID, aadID, aadModel, aadSN, aadDOM, manufacturerID, aadJumps, aadNextRegl, aadFired);
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
