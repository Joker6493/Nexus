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
public class AAD extends SkydiveSystem{
    //AAD
    protected int systemID;
    protected int aadID;
    protected String aadModel;
    protected String aadSN;
    protected String aadDOM;
    protected int manufacturerID;
    protected int aadJumps;
    protected String aadNextRegl;
    protected boolean aadFired;
    
    public void Reserve (int systemID, int aadID, String aadModel, String aadSN, String aadDOM, int manufacturerID, int aadJumps, String aadNextRegl, boolean aadFired){
        this.systemID = systemID; 
        this.aadID = aadID;
        this.aadModel = aadModel;
        this.aadSN = aadSN;
        this.aadDOM = aadDOM;
        this.manufacturerID = manufacturerID;
        this.aadJumps = aadJumps;
        this.aadNextRegl = aadNextRegl;
        this.aadFired = aadFired;
        }
    //Get methods
    public int get_systemID (){
        return systemID;
    }
    public int get_aadID (){
        return aadID;
    }
    public String get_aadModel (){
        return aadModel;
    }
    public String get_aadSN (){
        return aadSN;
    }
    public String get_aadDOM (){
        return aadDOM;
    }
    public int get_manufacturerID (){
        return manufacturerID;
    }
    public int get_aadJumps (){
        return aadJumps;
    }
    public String get_aadNextRegl () {
        return aadNextRegl;
    }
    public boolean get_aadFired (){
        return aadFired;
    }
    //Set methods
    public void set_systemID (int systemID){
        this.systemID = systemID;
    }
    public void set_aadID (int aadID){
        //In process
    }
    public void set_aadModel (String aadModel){
        this.aadModel = aadModel;
    }
    public void set_aadSN (String aadSN){
        this.aadSN = aadSN;;
    }
    public void set_aadDOM (String aadDOM){
        this.aadDOM = aadDOM;
    }
    public void set_manufacturerID (int manufacturerID){
        this.manufacturerID = manufacturerID;
    }
    public void set_aadJumps (int aadJumps){
        this.aadJumps = aadJumps;
    }
    public void set_aadNextRegl (String aadNextRegl){
        this.aadNextRegl = aadNextRegl;
    }
    public void set_aadFired (boolean aadFired){
        this.aadFired = aadFired;
    }
}
