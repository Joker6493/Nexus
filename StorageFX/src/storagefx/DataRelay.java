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

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataRelay {
    private int status = 0;
    private int stock = 2;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

        private ResultSet getData (String Query){
        ResultSet rs = null;
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            Connection conn = (Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj);
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(Query);
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
        return rs;
    }
    private void closeDB (Connection bdcon, Statement stmt, ResultSet rs) throws SQLException{
        rs.close();
        stmt.close();
        bdcon.close();
    }
    
    protected ObservableList<SkydiveSystem> getSystemsList() throws SQLException {
        ArrayList<SkydiveSystem> indexList = new ArrayList<>();
        String selectQuery = "select si.systemid, si.system_code, si.system_model, si.system_sn, si.system_dom, si.manufacturerid as system_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = si.manufacturerid) as system_manufacturer_name, " +
                             "ci.canopyid, ci.canopy_model, ci.canopy_size, ci.canopy_sn, ci.canopy_dom, ci.canopy_jumps, ci.manufacturerid as canopy_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ci.manufacturerid) as canopy_manufacturer_name, " +
                             "ri.reserveid, ri.reserve_model, ri.reserve_size, ri.reserve_sn, ri.reserve_dom, ri.reserve_jumps, ri.reserve_packdate, ri.manufacturerid as reserve_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ri.manufacturerid) as reserve_manufacturer_name, " + 
                             "ai.aadid, ai.aad_model, ai.aad_sn, ai.aad_dom, ai.aad_jumps, ai.aad_nextregl, ai.aad_saved, ai.manufacturerid as aad_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ai.manufacturerid) as aad_manufacturer_name " +
                             "from system_info si " +
                             "inner JOIN canopy_info ci ON si.canopyid = ci.canopyid and ci.status = si.status and ci.stockid = si.stockid " +
                             "inner JOIN reserve_info ri ON si.reserveid = ri.reserveid and ri.status = si.status and ri.stockid = si.stockid " +
                             "inner JOIN aad_info ai ON si.aadid = ai.aadid and ai.status = si.status and ai.stockid = si.stockid " +
                             "where si.status = "+ getStatus() +" and si.stockid = "+ getStock() +";";
        ResultSet rs = getData(selectQuery);
        while (rs.next()) {
            //Container
            int systemID = rs.getInt("systemid");
            String systemCode = rs.getString("system_code");
            String systemModel = rs.getString("system_model");
            String systemSN = rs.getString("system_sn");
            LocalDate systemDOM = rs.getDate("system_dom").toLocalDate();
            int systemManufacturerID = rs.getInt("system_manufacturerid");
            String systemManufacturerName = rs.getString("system_manufacturer_name");
            int stockID = getStock();
            //Canopy
            int canopyID = rs.getInt("canopyid");
            String canopyModel = rs.getString("canopy_model");
            int canopySize = rs.getInt("canopy_size");
            String canopySN = rs.getString("canopy_sn");
            LocalDate canopyDOM = rs.getDate("canopy_dom").toLocalDate();
            int canopyJumps = rs.getInt("canopy_jumps");
            int canopyManufacturerID = rs.getInt("canopy_manufacturerid");
            String canopyManufacturerName = rs.getString("canopy_manufacturer_name");
            //Reserve
            int reserveID = rs.getInt("reserveid");
            String reserveModel = rs.getString("reserve_model");
            int reserveSize = rs.getInt("reserve_size");
            String reserveSN = rs.getString("reserve_sn");
            LocalDate reserveDOM = rs.getDate("reserve_dom").toLocalDate();
            int reserveJumps = rs.getInt("reserve_jumps");
            LocalDate reservePackDate = rs.getDate("reserve_packdate").toLocalDate();
            int reserveManufacturerID = rs.getInt("reserve_manufacturerid");
            String reserveManufacturerName = rs.getString("reserve_manufacturer_name");
            //AAD
            int aadID = rs.getInt("aadid");
            String aadModel = rs.getString("aad_model");
            String aadSN = rs.getString("aad_sn");
            LocalDate aadDOM = rs.getDate("aad_dom").toLocalDate();
            int aadJumps = rs.getInt("aad_jumps");
            LocalDate aadNextRegl = rs.getDate("aad_nextregl").toLocalDate();
            int aadSaved = rs.getInt("aad_saved");
            int aadManufacturerID = rs.getInt("aad_manufacturerid");
            String aadManufacturerName = rs.getString("aad_manufacturer_name");
            indexList.add(new SkydiveSystem(systemID, systemCode, systemModel, systemSN, systemDOM, systemManufacturerID, systemManufacturerName, stockID, canopyID, canopyModel, canopySize, canopySN, canopyDOM, canopyJumps, canopyManufacturerID, canopyManufacturerName, reserveID, reserveModel, reserveSize, reserveSN, reserveDOM, reserveJumps, reservePackDate, reserveManufacturerID, reserveManufacturerName, aadID, aadModel, aadSN, aadDOM, aadJumps, aadNextRegl, aadSaved, aadManufacturerID, aadManufacturerName));
        }
        Statement stmt = rs.getStatement();
        Connection bdcon = stmt.getConnection();
        closeDB(bdcon, stmt, rs);
        ObservableList<SkydiveSystem> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<Stock> getStockList() throws SQLException {
        ArrayList<Stock> stockList = new ArrayList<>();
        String selectQuery = "select stockid, stock_name " +
                             "from stock_info " +
                             "where status = " +  + getStatus();
        ResultSet rs = getData(selectQuery);
        while (rs.next()) {
            int stockID = rs.getInt("stockid");
            String stockName = rs.getString("stock_name");
            stockList.add(new Stock(stockID, stockName));
        }
        Statement stmt = rs.getStatement();
        Connection bdcon = stmt.getConnection();
        closeDB(bdcon, stmt, rs);
        ObservableList<Stock> list = FXCollections.observableList(stockList);
        return list;
    }
    protected ObservableList<ElementStatus> getStatusList() throws SQLException {
        ArrayList<ElementStatus> statusList = new ArrayList<>();
        ElementStatus active = new ElementStatus(0,"Активная");
        statusList.add(active);
        ElementStatus disable = new ElementStatus(1,"Удаленная");
        statusList.add(disable);
        ObservableList<ElementStatus> list = FXCollections.observableList(statusList);
        return list;
    }
    
    protected void addSkydiveSystem(SkydiveSystem ss) {
        //some code here in the future
    }
    
    protected void addCanopy(Canopy c) {
        //some code here in the future
    }
    
    protected void addReserve(Reserve r) {
        //some code here in the future
    }
    
    protected void addAAD(AAD aad) {
        //some code here in the future
    }
    
    protected void editSkydiveSystem(SkydiveSystem ss) {
        //some code here in the future
    }
    
    protected void editCanopy(Canopy c) {
        //some code here in the future
    }
    
    protected void editReserve(Reserve r) {
        //some code here in the future
    }
    
    protected void editAAD(AAD aad) {
        //some code here in the future
    }
    
    protected void deleteSkydiveSystem(SkydiveSystem ss) {
        //some code here in the future
    }
    
    protected void deleteCanopy(Canopy c) {
        //some code here in the future
    }
    
    protected void deleteReserve(Reserve r) {
        //some code here in the future
    }
    
    protected void deleteAAD(AAD aad) {
        //some code here in the future
    }
}
