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
    public void SkydiveSystem (String systemCode, String systemModel, String canopyModel, int canopySize, String reserveModel, int reserveSize, String aadModel, int canopyJumps, String reservePackDate){
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
    public void SkydiveSystem (int systemID, String systemCode, String systemModel, String systemSN, String systemDOM, int canopyID, String canopyModel, int canopySize, String canopySN, String canopyDOM, int canopyJumps, int reserveID, String reserveModel, int reserveSize, String reserveSN, String reserveDOM, int reserveJumps, String reservePackDate, int aadID, String aadModel, String aadSN, String aadDOM, int aadJumps, String aadNextRegl, boolean aadFired){
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
    public int get_canopyID (){
        return canopyID;
    }
    public int get_reserveID (){
        return reserveID;
    }
    public int get_aadID (){
        return aadID;
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
    public int get_canopyJumps (){
        return canopyJumps;
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
    public int get_reserveJumps (){
        return reserveJumps;
    }
    public String get_reservePackDate () {
        return reservePackDate;
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
    public void set_canopyID (int canopyID){
        this.canopyID = canopyID;
    }
    public void set_reserveID (int reserveID){
        this.reserveID = reserveID;
    }
    public void set_aadID (int aadID){
        this.aadID = aadID;
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
    public void set_canopyJumps (int canopyJumps){
        this.canopyJumps = canopyJumps;
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
    public void set_reserveJumps (int reserveJumps){
        this.reserveJumps = reserveJumps;
    }
    public void set_reservePackDate (String reservePackDate){
        this.reservePackDate = reservePackDate;
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
