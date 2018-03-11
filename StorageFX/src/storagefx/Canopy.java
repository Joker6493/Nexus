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
public class Canopy {
    //Container
    private int systemID;
    //Canopy
    private int canopyID;
    private String canopyModel;
    private int canopySize;
    private String canopySN;
    private LocalDate canopyDOM;
    private int canopyJumps;
    private int canopyManufacturerID;
    private String canopyManufacturerName;

    public Canopy(int systemID, int canopyID, String canopyModel, int canopySize, String canopySN, LocalDate canopyDOM, int canopyJumps, int canopyManufacturerID, String canopyManufacturerName) {
        //Canopy
        this.systemID = systemID;
        this.canopyID = canopyID;
        this.canopyModel = canopyModel;
        this.canopySize = canopySize;
        this.canopySN = canopySN;
        this.canopyDOM = canopyDOM;
        this.canopyJumps = canopyJumps;
        this.canopyManufacturerID = canopyManufacturerID;
        this.canopyManufacturerName = canopyManufacturerName;
    }
    public int getSystemID() {
        return systemID;
    }
    public void setSystemID(int systemID) {
        this.systemID = systemID;
    }
    public int getCanopyID() {
        return canopyID;
    }
    public void setCanopyID(int canopyID) {
        this.canopyID = canopyID;
    }
    public String getCanopyModel() {
        return canopyModel;
    }
    public void setCanopyModel(String canopyModel) {
        this.canopyModel = canopyModel;
    }
    public int getCanopySize() {
        return canopySize;
    }
    public void setCanopySize(int canopySize) {
        this.canopySize = canopySize;
    }
    public String getCanopySN() {
        return canopySN;
    }
    public void setCanopySN(String canopySN) {
        this.canopySN = canopySN;
    }
    public LocalDate getCanopyDOM() {
        return canopyDOM;
    }
    public void setCanopyDOM(LocalDate canopyDOM) {
        this.canopyDOM = canopyDOM;
    }
    public int getCanopyJumps() {
        return canopyJumps;
    }
    public void setCanopyJumps(int canopyJumps) {
        this.canopyJumps = canopyJumps;
    }
    public int getCanopyManufacturerID() {
        return canopyManufacturerID;
    }
    public void setCanopyManufacturerID(int canopyManufacturerID) {
        this.canopyManufacturerID = canopyManufacturerID;
    }
    public String getCanopyManufacturerName() {
        return canopyManufacturerName;
    }
    public void setCanopyManufacturerName(String canopyManufacturerName) {
        this.canopyManufacturerName = canopyManufacturerName;
    }
}
