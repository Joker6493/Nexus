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
public class Reserve {
    //Container
    private int systemID;
    private int stockID;
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

    public Reserve(int systemID, int reserveID, String reserveModel, int reserveSize, String reserveSN, LocalDate reserveDOM, int reserveJumps, LocalDate reservePackDate, int reserveManufacturerID, String reserveManufacturerName, int stockID) {
        this.systemID = systemID;
        this.stockID = stockID;
        this.reserveID = reserveID;
        this.reserveModel = reserveModel;
        this.reserveSize = reserveSize;
        this.reserveSN = reserveSN;
        this.reserveDOM = reserveDOM;
        this.reserveJumps = reserveJumps;
        this.reservePackDate = reservePackDate;
        this.reserveManufacturerID = reserveManufacturerID;
        this.reserveManufacturerName = reserveManufacturerName;
    }
    public Reserve(int systemID, String reserveModel, int reserveSize, String reserveSN, LocalDate reserveDOM, int reserveJumps, LocalDate reservePackDate, int reserveManufacturerID, String reserveManufacturerName, int stockID) {
        this.systemID = systemID;
        this.stockID = stockID;
        this.reserveModel = reserveModel;
        this.reserveSize = reserveSize;
        this.reserveSN = reserveSN;
        this.reserveDOM = reserveDOM;
        this.reserveJumps = reserveJumps;
        this.reservePackDate = reservePackDate;
        this.reserveManufacturerID = reserveManufacturerID;
        this.reserveManufacturerName = reserveManufacturerName;
    }
    public int getSystemID() {
        return systemID;
    }
    public void setSystemID(int systemID) {
        this.systemID = systemID;
    }
    public int getStockID (){
        return stockID;
    }
    public void setStockID (int stockID){
        this.stockID = stockID;
    }
    public int getReserveID() {
        return reserveID;
    }
    public void setReserveID(int reserveID) {
        this.reserveID = reserveID;
    }
    public String getReserveModel() {
        return reserveModel;
    }
    public void setReserveModel(String reserveModel) {
        this.reserveModel = reserveModel;
    }
    public int getReserveSize() {
        return reserveSize;
    }
    public void setReserveSize(int reserveSize) {
        this.reserveSize = reserveSize;
    }
    public String getReserveSN() {
        return reserveSN;
    }
    public void setReserveSN(String reserveSN) {
        this.reserveSN = reserveSN;
    }
    public LocalDate getReserveDOM() {
        return reserveDOM;
    }
    public void setReserveDOM(LocalDate reserveDOM) {
        this.reserveDOM = reserveDOM;
    }
    public int getReserveJumps() {
        return reserveJumps;
    }
    public void setReserveJumps(int reserveJumps) {
        this.reserveJumps = reserveJumps;
    }
    public LocalDate getReservePackDate() {
        return reservePackDate;
    }
    public void setReservePackDate(LocalDate reservePackDate) {
        this.reservePackDate = reservePackDate;
    }
    public int getReserveManufacturerID() {
        return reserveManufacturerID;
    }
    public void setReserveManufacturerID(int reserveManufacturerID) {
        this.reserveManufacturerID = reserveManufacturerID;
    }
    public String getReserveManufacturerName() {
        return reserveManufacturerName;
    }
    public void setReserveManufacturerName(String reserveManufacturerName) {
        this.reserveManufacturerName = reserveManufacturerName;
    }
}
