package storagefx;

import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author d.borodin
 */
public class AAD {
    //Container
    private int systemID;
    private String systemCode;
    private String systemModel;
    private String systemSN;
    private LocalDate systemDOM;
    private int systemManufacturerID;
    private String systemManufacturerName;
    //Canopy
    private int canopyID;
    private String canopyModel;
    private int canopySize;
    private String canopySN;
    private LocalDate canopyDOM;
    private int canopyJumps;
    private int canopyManufacturerID;
    private String canopyManufacturerName;
    //Reserve
    private int reserveID;
    private String reserveModel;
    private int reserveSize;
    private String reserveSN;
    private LocalDate reserveDOM;
    private int reserveJumps;
    private LocalDate reservePackDate;
    private int reserveManufacturerID;
    private String reserveManufacturerName;
    //AAD
    private int aadID;
    private String aadModel;
    private String aadSN;
    private LocalDate aadDOM;
    private int aadJumps;
    private LocalDate aadNextRegl;
    private boolean aadFired;
    private int aadManufacturerID;
    private String aadManufacturerName;
        
    //Full
    AAD (int systemID, String systemCode, String systemModel, String systemSN, LocalDate systemDOM, int systemManufacturerID, String systemManufacturerName, int canopyID, String canopyModel, int canopySize, String canopySN, LocalDate canopyDOM, int canopyJumps, int canopyManufacturerID, String canopyManufacturerName, int reserveID, String reserveModel, int reserveSize, String reserveSN, LocalDate reserveDOM, int reserveJumps, LocalDate reservePackDate, int reserveManufacturerID, String reserveManufacturerName, int aadID, String aadModel, String aadSN, LocalDate aadDOM, int aadJumps, LocalDate aadNextRegl, boolean aadFired, int aadManufacturerID, String aadManufacturerName){
    this.systemID = systemID;
    this.systemCode = systemCode;
    this.systemModel = systemModel;
    this.systemSN = systemSN;
    this.systemDOM = systemDOM;
    this.systemManufacturerID = systemManufacturerID;
    this.systemManufacturerName = systemManufacturerName;
    //Canopy
    this.canopyID = canopyID;
    this.canopyModel = canopyModel;
    this.canopySize = canopySize;
    this.canopySN = canopySN;
    this.canopyDOM = canopyDOM;
    this.canopyJumps = canopyJumps;
    this.canopyManufacturerID = canopyManufacturerID;
    this.canopyManufacturerName = canopyManufacturerName;
    //Reserve
    this.reserveID = reserveID;
    this.reserveModel = reserveModel;
    this.reserveSize = reserveSize;
    this.reserveSN = reserveSN;
    this.reserveDOM = reserveDOM;
    this.reserveJumps = reserveJumps;
    this.reservePackDate = reservePackDate;
    this.reserveManufacturerID = reserveManufacturerID;
    this.reserveManufacturerName = reserveManufacturerName;
    //AAD
    this.aadID = aadID;
    this.aadModel = aadModel;
    this.aadSN = aadSN;
    this.aadDOM = aadDOM;
    this.aadJumps = aadJumps;
    this.aadNextRegl = aadNextRegl;
    this.aadFired = aadFired;
    this.aadManufacturerID = aadManufacturerID;
    this.aadManufacturerName = aadManufacturerName;
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
    public LocalDate getSystemDOM (){
        return systemDOM;
    }
    public int getSystemManufacturerID (){
        return systemManufacturerID;
    }
    public String getSystemManufacturerName (){
        return systemManufacturerName;
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
    public LocalDate getCanopyDOM (){
        return canopyDOM;
    }
    public int getCanopyJumps (){
        return canopyJumps;
    }
    public int getCanopyManufacturerID (){
        return canopyManufacturerID;
    }
    public String getCanopyManufacturerName (){
        return canopyManufacturerName;
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
    public LocalDate getReserveDOM (){
        return reserveDOM;
    }
    public int getReserveJumps (){
        return reserveJumps;
    }
    public LocalDate getReservePackDate () {
        return reservePackDate;
    }
    public int getReserveManufacturerID (){
        return reserveManufacturerID;
    }
    public String getReserveManufacturerName (){
        return reserveManufacturerName;
    }
    public String getAadModel (){
        return aadModel;
    }
    public String getAadSN (){
        return aadSN;
    }
    public LocalDate getAadDOM (){
        return aadDOM;
    }
    public int getAadJumps (){
        return aadJumps;
    }
    public LocalDate getAadNextRegl () {
        return aadNextRegl;
    }
    public boolean getAadFired (){
        return aadFired;
    }
    public int getAadManufacturerID (){
        return aadManufacturerID;
    }
    public String getAadManufacturerName (){
        return aadManufacturerName;
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
    public void setSystemDOM (LocalDate systemDOM){
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
    public void setSystemManufacturerID (int systemManufacturerID){
        this.systemManufacturerID = systemManufacturerID;
    }
    public void setSystemManufacturerID (String systemManufacturerName){
        this.systemManufacturerName = systemManufacturerName;
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
    public void setCanopyDOM (LocalDate canopyDOM){
        this.canopyDOM = canopyDOM;
    }
    public void setCanopyJumps (int canopyJumps){
        this.canopyJumps = canopyJumps;
    }
    public void setCanopyManufacturerID (int canopyManufacturerID){
        this.canopyManufacturerID = canopyManufacturerID;
    }
    public void setCanopyManufacturerID (String canopyManufacturerName){
        this.canopyManufacturerName = canopyManufacturerName;
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
    public void setReserveDOM (LocalDate reserveDOM){
        this.reserveDOM = reserveDOM;
    }
    public void setReserveJumps (int reserveJumps){
        this.reserveJumps = reserveJumps;
    }
    public void setReservePackDate (LocalDate reservePackDate){
        this.reservePackDate = reservePackDate;
    }
    public void setReserveManufacturerID (int reserveManufacturerID){
        this.reserveManufacturerID = reserveManufacturerID;
    }
    public void setReserveManufacturerID (String reserveManufacturerName){
        this.reserveManufacturerName = reserveManufacturerName;
    }
    public void setAadModel (String aadModel){
        this.aadModel = aadModel;
    }
    public void setAadSN (String aadSN){
        this.aadSN = aadSN;
    }
    public void setAadDOM (LocalDate aadDOM){
        this.aadDOM = aadDOM;
    }
    public void setAadJumps (int aadJumps){
        this.aadJumps = aadJumps;
    }
    public void setAadNextRegl (LocalDate aadNextRegl){
        this.aadNextRegl = aadNextRegl;
    }
    public void setAadFired (boolean aadFired){
        this.aadFired = aadFired;
    }
    public void setAadManufacturerID (int aadManufacturerID){
        this.aadManufacturerID = aadManufacturerID;
    }
    public void setAadManufacturerID (String aadManufacturerName){
        this.aadManufacturerName = aadManufacturerName;
    }
}
