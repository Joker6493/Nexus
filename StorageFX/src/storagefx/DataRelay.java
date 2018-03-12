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
            Class oracl = Class.forName("utils.OracleConn");
            Object oraClass = oracl.newInstance();
            Method oraConnMethod = oracl.getDeclaredMethod("connectDatabase", params);
            Connection conn = (Connection) oraConnMethod.invoke(oraClass, paramsObj);
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
        String selectQuery = "select si.systemid, si.system_code, si.system_model, si.system_sn, si.system_dom, si.manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = si.manufacturerid), si.stockid, " +
                             "ci.canopyid, ci.canopy_model, ci.canopy_size, ci.canopy_sn, ci.canopy_dom, ci.canopy_jumps, ci.manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ci.manufacturerid), " +
                             "ri.reserveid, ri.reserve_model, ri.reserve_size, ri.reserve_sn, ri.reserve_dom, ri.reserve_jumps, ri.reserve_packdate, ri.manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ri.manufacturerid), " +
                             "ai.aadid, ai.aad_model, ai.aad_sn, ai.aad_dom, ai.aad_jumps, ai.aad_nextregl, ai.aad_fired, ai.manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ai.manufacturerid) " +
                             "from system_info si " +
                             "inner JOIN canopy_info ci ON si.canopyid = ci.canopyid and ci.disable = 0 " +
                             "inner JOIN reserve_info ri ON si.reserveid = ri.reserveid and ri.disable = 0 " +
                             "inner JOIN aad_info ai ON si.aadid = ai.aadid and ai.disable = 0 " +
                             "inner join stock_info sti on si.stockid = sti.stockid and sti.disable = 0 " +
                             "where si.disable = " + getStatus();
        ResultSet rs = getData(selectQuery);
        while (rs.next()) {
            //Container
            int systemID = rs.getInt("systemid");
            String systemCode = rs.getString("system_code");
            String systemModel = rs.getString("system_model");
            String systemSN = rs.getString("system_sn");
            LocalDate systemDOM = rs.getDate("system_dom").toLocalDate();
            int systemManufacturerID = rs.getInt(6);
            String systemManufacturerName = rs.getString(7);
            int stockID = rs.getInt("stockid");
            //Canopy
            int canopyID = rs.getInt("canopyid");
            String canopyModel = rs.getString("canopy_model");
            int canopySize = rs.getInt("canopy_size");
            String canopySN = rs.getString("canopy_sn");
            LocalDate canopyDOM = rs.getDate("canopy_dom").toLocalDate();
            int canopyJumps = rs.getInt("canopy_jumps");
            int canopyManufacturerID = rs.getInt(14);
            String canopyManufacturerName = rs.getString(15);
            //Reserve
            int reserveID = rs.getInt("reserveid");
            String reserveModel = rs.getString("reserve_model");
            int reserveSize = rs.getInt("reserve_size");
            String reserveSN = rs.getString("reserve_sn");
            LocalDate reserveDOM = rs.getDate("reserve_dom").toLocalDate();
            int reserveJumps = rs.getInt("reserve_jumps");
            LocalDate reservePackDate = rs.getDate("reserve_packdate").toLocalDate();
            int reserveManufacturerID = rs.getInt(24);
            String reserveManufacturerName = rs.getString(25);
            //AAD
            int aadID = rs.getInt("aadid");
            String aadModel = rs.getString("aad_model");
            String aadSN = rs.getString("aad_sn");
            LocalDate aadDOM = rs.getDate("aad_dom").toLocalDate();
            int aadJumps = rs.getInt("aad_jumps");
            LocalDate aadNextRegl = rs.getDate("aad_nextregl").toLocalDate();
            boolean aadFired = false;
            if (rs.getString("aad_fired").equalsIgnoreCase("Y")){
                aadFired = true;
            }
            int aadManufacturerID = rs.getInt(33);
            String aadManufacturerName = rs.getString(34);
            indexList.add(new SkydiveSystem(systemID, systemCode, systemModel, systemSN, systemDOM, systemManufacturerID, systemManufacturerName, stockID, canopyID, canopyModel, canopySize, canopySN, canopyDOM, canopyJumps, canopyManufacturerID, canopyManufacturerName, reserveID, reserveModel, reserveSize, reserveSN, reserveDOM, reserveJumps, reservePackDate, reserveManufacturerID, reserveManufacturerName, aadID, aadModel, aadSN, aadDOM, aadJumps, aadNextRegl, aadFired, aadManufacturerID, aadManufacturerName));
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
                             "where disable = " +  + getStatus();
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
}
