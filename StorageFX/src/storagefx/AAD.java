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
    //AAD
    private int systemID;
    private int aadID;
    private String aadModel;
    private String aadSN;
    private LocalDate aadDOM;
    private int aadJumps;
    private LocalDate aadNextRegl;
    private int aadFired;
    private int aadManufacturerID;
    private String aadManufacturerName;

    public AAD(int systemID, int aadID, String aadModel, String aadSN, LocalDate aadDOM, int aadJumps, LocalDate aadNextRegl, int aadFired, int aadManufacturerID, String aadManufacturerName) {
        this.systemID = systemID;
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
    public int getSystemID() {
        return systemID;
    }
    public void setSystemID(int systemID) {
        this.systemID = systemID;
    }
    public int getAadID() {
        return aadID;
    }
    public void setAadID(int aadID) {
        this.aadID = aadID;
    }
    public String getAadModel() {
        return aadModel;
    }
    public void setAadModel(String aadModel) {
        this.aadModel = aadModel;
    }
    public String getAadSN() {
        return aadSN;
    }
    public void setAadSN(String aadSN) {
        this.aadSN = aadSN;
    }
    public LocalDate getAadDOM() {
        return aadDOM;
    }
    public void setAadDOM(LocalDate aadDOM) {
        this.aadDOM = aadDOM;
    }
    public int getAadJumps() {
        return aadJumps;
    }
    public void setAadJumps(int aadJumps) {
        this.aadJumps = aadJumps;
    }
    public LocalDate getAadNextRegl() {
        return aadNextRegl;
    }
    public void setAadNextRegl(LocalDate aadNextRegl) {
        this.aadNextRegl = aadNextRegl;
    }
    public int isAadFired() {
        return aadFired;
    }
    public void setAadFired(int aadFired) {
        this.aadFired = aadFired;
    }
    public int getAadManufacturerID() {
        return aadManufacturerID;
    }
    public void setAadManufacturerID(int aadManufacturerID) {
        this.aadManufacturerID = aadManufacturerID;
    }
    public String getAadManufacturerName() {
        return aadManufacturerName;
    }
    public void setAadManufacturerName(String aadManufacturerName) {
        this.aadManufacturerName = aadManufacturerName;
    }
}
