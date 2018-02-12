package storagefx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author d.borodin
 */
public class SkydiveSystem {
    //Система
    protected int systemID;
    protected String systemCode;
    protected String systemModel;
    protected String systemSN;
    protected String systemDOM;
    //ОП
    protected int canopyID;
    protected String canopyModel;
    protected int canopySize;
    protected String canopySN;
    protected String canopyDOM;
    protected int canopyJumps;
    //ПЗ
    protected int reserveID;
    protected String reserveModel;
    protected int reserveSize;
    protected String reserveSN;
    protected String reserveDOM;
    protected int reserveJumps;
    protected String reservePackDate;
    //AAD
    protected int aadID;
    protected String aadModel;
    protected String aadSN;
    protected String aadDOM;
    protected int aadJumps;
    protected String aadNextRegl;
    protected boolean aadFired;
        
    //Short
    SkydiveSystem (String systemCode, String systemModel, String canopyModel, int canopySize, String reserveModel, int reserveSize, String aadModel, int canopyJumps, String reservePackDate){
        this.systemCode = systemCode;
        this.systemModel = systemModel;
        this.canopyModel = canopyModel;
        this.canopySize = canopySize;
        this.reserveModel = reserveModel;
        this.reserveSize = reserveSize;
        this.aadModel = aadModel;
        this.canopyJumps = canopyJumps;
        this.reservePackDate = reservePackDate;
    }
    //Full
    SkydiveSystem (int systemID, String systemCode, String systemModel, String systemSN, String systemDOM, int canopyID, String canopyModel, int canopySize, String canopySN, String canopyDOM, int canopyJumps, int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int reserveJumps, String reservePackDate, int aadID, String aadModel, String aadSN, String aadDOM, int aadJumps, String aadNextRegl, boolean aadFired){
    this.systemID = systemID;
    this.systemCode = systemCode;
    this.systemModel = systemModel;
    this.systemSN = systemSN;
    this.systemDOM = systemDOM;
    this.canopyID = canopyID;
    this.canopyModel = canopyModel;
    this.canopySize = canopySize;
    this.canopySN = canopySN;
    this.canopyDOM = canopyDOM;
    this.canopyJumps = canopyJumps;
    this.reserveID = reserveID;
    this.reserveModel = reserveModel;
    this.reserveSize = reserveSize;
    this.reserveSN = reserveSN;
    this.reserveDOM = reserveDOM;
    this.reserveJumps = reserveJumps;
    this.reservePackDate = reservePackDate;
    this.aadID = aadID;
    this.aadModel = aadModel;
    this.aadSN = aadSN;
    this.aadDOM = aadDOM;
    this.aadJumps = aadJumps;
    this.aadNextRegl = aadNextRegl;
    this.aadFired = aadFired;
    }
    
    //For containers
    SkydiveSystem (int systemID, String systemCode, String systemModel, String systemSN, String systemDOM, int manufacturerID, int canopyID, int reserveID, int aadID){
        this.systemID = systemID; 
        this.systemCode = systemCode;
        this.systemModel = systemModel;
        this.systemSN = systemSN;
        this.systemDOM = systemDOM;
        this.canopyID = canopyID;
        this.reserveID = reserveID;
        this.aadID = aadID;
        }
    //For canopy
    SkydiveSystem (int systemID, int canopyID, String canopyModel, int canopySize, String canopySN, String canopyDOM, int manufacturerID, int canopyJumps){
        this.systemID = systemID; 
        this.canopyID = canopyID;
        this.canopyModel = canopyModel;
        this.canopySize = canopySize;
        this.canopySN = canopySN;
        this.canopyDOM = canopyDOM;
        this.canopyJumps = canopyJumps;
        }
    //For reserve
    SkydiveSystem (int systemID, int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int manufacturerID, int reserveJumps, String reservePackDate){
        this.systemID = systemID; 
        this.reserveID = reserveID;
        this.reserveModel = reserveModel;
        this.reserveSize = reserveSize;
        this.reserveSN = reserveSN;
        this.reserveDOM = reserveDOM;
        this.reserveJumps = reserveJumps;
        this.reservePackDate = reservePackDate;
        }
    //For AAD
    SkydiveSystem (int systemID, int aadID, String aadModel, String aadSN, String aadDOM, int manufacturerID, int aadJumps, String aadNextRegl, boolean aadFired){
        this.systemID = systemID; 
        this.aadID = aadID;
        this.aadModel = aadModel;
        this.aadSN = aadSN;
        this.aadDOM = aadDOM;
        this.aadJumps = aadJumps;
        this.aadNextRegl = aadNextRegl;
        this.aadFired = aadFired;
        }    
    
    //Get methods
    public int getSystemID (){
        return systemID;
    }
    public String getSystemCode (){
        return systemCode;
    }
    public String getSystemModel (){
        return systemModel;
    }
    public String getSystemSN (){
        return systemSN;
    }
    public String getSystemDOM (){
        return systemDOM;
    }
    public int getCanopyID (){
        return canopyID;
    }
    public int getReserveID (){
        return reserveID;
    }
    public int getAadID (){
        return aadID;
    }
    public String getCanopyModel (){
        return canopyModel;
    }
    public int getCanopySize (){
        return canopySize;
    }
    public String getCanopySN (){
        return canopySN;
    }
    public String getCanopyDOM (){
        return canopyDOM;
    }
    public int getCanopyJumps (){
        return canopyJumps;
    }
    public String getReserveModel (){
        return reserveModel;
    }
    public int getReserveSize (){
        return reserveSize;
    }
    public String getReserveSN (){
        return reserveSN;
    }
    public String getReserveDOM (){
        return reserveDOM;
    }
    public int getReserveJumps (){
        return reserveJumps;
    }
    public String getReservePackDate () {
        return reservePackDate;
    }
    public String getAadModel (){
        return aadModel;
    }
    public String getAadSN (){
        return aadSN;
    }
    public String getAadDOM (){
        return aadDOM;
    }
    public int getAadJumps (){
        return aadJumps;
    }
    public String getAadNextRegl () {
        return aadNextRegl;
    }
    public boolean getAadFired (){
        return aadFired;
    }
    //Set methods 
    public void setSystemID (int systemID){
        this.systemID = systemID;
    }
    public void setSystemCode (String systemCode){
        this.systemCode = systemCode;
    }
    public void setSystemModel (String systemModel){
        this.systemModel = systemModel;
    }
    public void setSystemSN (String systemSN){
        this.systemSN = systemSN;
    }
    public void setSystemDOM (String systemDOM){
        this.systemDOM = systemDOM;
    }
    public void setCanopyID (int canopyID){
        this.canopyID = canopyID;
    }
    public void setReserveID (int reserveID){
        this.reserveID = reserveID;
    }
    public void setAadID (int aadID){
        this.aadID = aadID;
    }
    public void setCanopyModel (String canopyModel){
        this.canopyModel = canopyModel;
    }
    public void setCanopySize (int canopySize){
        this.canopySize = canopySize;
    }
    public void setCanopySN (String canopySN){
        this.canopySN = canopySN;
    }
    public void setCanopyDOM (String canopyDOM){
        this.canopyDOM = canopyDOM;
    }
    public void setCanopyJumps (int canopyJumps){
        this.canopyJumps = canopyJumps;
    }
    public void setReserveModel (String reserveModel){
        this.reserveModel = reserveModel;
    }
    public void setReserveSize (int reserveSize){
        this.reserveSize = reserveSize;
    }
    public void setReserveSN (String reserveSN){
        this.reserveSN = reserveSN;
    }
    public void setReserveDOM (String reserveDOM){
        this.reserveDOM = reserveDOM;
    }
    public void setReserveJumps (int reserveJumps){
        this.reserveJumps = reserveJumps;
    }
    public void setReservePackDate (String reservePackDate){
        this.reservePackDate = reservePackDate;
    }
    public void setAadModel (String aadModel){
        this.aadModel = aadModel;
    }
    public void setAadSN (String aadSN){
        this.aadSN = aadSN;
    }
    public void setAadDOM (String aadDOM){
        this.aadDOM = aadDOM;
    }
    public void setAadJumps (int aadJumps){
        this.aadJumps = aadJumps;
    }
    public void setAadNextRegl (String aadNextRegl){
        this.aadNextRegl = aadNextRegl;
    }
    public void setAadFired (boolean aadFired){
        this.aadFired = aadFired;
    }
}
