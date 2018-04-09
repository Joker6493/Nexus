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
import java.sql.Date;
import java.sql.PreparedStatement;
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
    
    protected ObservableList<SkydiveSystem> getSystemsList() {
        ArrayList<SkydiveSystem> indexList = new ArrayList<>();
        try{
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
        }catch(SQLException e){
            
        }
        ObservableList<SkydiveSystem> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<SkydiveSystem> getContainersList() {
        ArrayList<SkydiveSystem> indexList = new ArrayList<>();
        try{
            String selectQuery = "select si.systemid, si.system_code, si.system_model, si.system_sn, si.system_dom, si.manufacturerid as system_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = si.manufacturerid) as system_manufacturer_name " +
                                 "from system_info si " +
                                 "where si.canopyid = 0 and si.reserveid = 0 and si.aadid = 0 and si.status = "+ getStatus() +" and si.stockid = "+ getStock() +";";
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
                indexList.add(new SkydiveSystem(systemID, systemCode, systemModel, systemSN, systemDOM, systemManufacturerID, systemManufacturerName, stockID));
            }
            Statement stmt = rs.getStatement();
            Connection bdcon = stmt.getConnection();
            closeDB(bdcon, stmt, rs);
        }catch(SQLException e){
            
        }
        ObservableList<SkydiveSystem> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<Canopy> getCanopyList() {
        ArrayList<Canopy> indexList = new ArrayList<>();
        try{
            String selectQuery = "select ci.canopyid, ci.canopy_model, ci.canopy_size, ci.canopy_sn, ci.canopy_dom, ci.canopy_jumps, ci.manufacturerid as canopy_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ci.manufacturerid) as canopy_manufacturer_name " +
                                 "from canopy_info ci " +
                                 "where ci.systemid = 0 and ci.status = "+ getStatus() +"and ci.stockid = "+ getStock() +";";
            ResultSet rs = getData(selectQuery);
            while (rs.next()) {
                int systemID = 0;
                int stockID = getStock();
                int canopyID = rs.getInt("canopyid");
                String canopyModel = rs.getString("canopy_model");
                int canopySize = rs.getInt("canopy_size");
                String canopySN = rs.getString("canopy_sn");
                LocalDate canopyDOM = rs.getDate("canopy_dom").toLocalDate();
                int canopyJumps = rs.getInt("canopy_jumps");
                int canopyManufacturerID = rs.getInt("canopy_manufacturerid");
                String canopyManufacturerName = rs.getString("canopy_manufacturer_name");
                indexList.add(new Canopy(systemID, canopyID, canopyModel, canopySize, canopySN, canopyDOM, canopyJumps, canopyManufacturerID, canopyManufacturerName, stockID));
            }
            Statement stmt = rs.getStatement();
            Connection bdcon = stmt.getConnection();
            closeDB(bdcon, stmt, rs);
        }catch(SQLException e){
            
        }
        ObservableList<Canopy> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<Reserve> getReserveList() {
        ArrayList<Reserve> indexList = new ArrayList<>();
        try{
            String selectQuery = "select ri.reserveid, ri.reserve_model, ri.reserve_size, ri.reserve_sn, ri.reserve_dom, ri.reserve_jumps, ri.reserve_packdate, ri.manufacturerid as reserve_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ri.manufacturerid) as reserve_manufacturer_name " +
                                 "from reserve_info ri " +
                                 "where ri.systemid = 0 and ri.status = "+ getStatus() +" and ri.stockid = "+ getStock() +";";
            ResultSet rs = getData(selectQuery);
            while (rs.next()) {
                int systemID = 0;
                int stockID = getStock();
                int reserveID = rs.getInt("reserveid");
                String reserveModel = rs.getString("reserve_model");
                int reserveSize = rs.getInt("reserve_size");
                String reserveSN = rs.getString("reserve_sn");
                LocalDate reserveDOM = rs.getDate("reserve_dom").toLocalDate();
                int reserveJumps = rs.getInt("reserve_jumps");
                LocalDate reservePackDate = rs.getDate("reserve_packdate").toLocalDate();
                int reserveManufacturerID = rs.getInt("reserve_manufacturerid");
                String reserveManufacturerName = rs.getString("reserve_manufacturer_name");
                indexList.add(new Reserve(systemID, reserveID, reserveModel, reserveSize, reserveSN, reserveDOM, reserveJumps, reservePackDate, reserveManufacturerID, reserveManufacturerName, stockID));
            }
            Statement stmt = rs.getStatement();
            Connection bdcon = stmt.getConnection();
            closeDB(bdcon, stmt, rs);
        }catch(SQLException e){
            
        }
        ObservableList<Reserve> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<AAD> getAadList() {
        ArrayList<AAD> indexList = new ArrayList<>();
        try{
            String selectQuery = "select ai.aadid, ai.aad_model, ai.aad_sn, ai.aad_dom, ai.aad_jumps, ai.aad_nextregl, ai.aad_saved, ai.manufacturerid as aad_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ai.manufacturerid) as aad_manufacturer_name " +
                                 "from aad_info ai " +
                                 "where ai.systemid = 0 and ai.status = "+ getStatus() +" and ai.stockid = "+ getStock() +";";
            ResultSet rs = getData(selectQuery);
            while (rs.next()) {
                int systemID = 0;
                int stockID = getStock();
                int aadID = rs.getInt("aadid");
                String aadModel = rs.getString("aad_model");
                String aadSN = rs.getString("aad_sn");
                LocalDate aadDOM = rs.getDate("aad_dom").toLocalDate();
                int aadJumps = rs.getInt("aad_jumps");
                LocalDate aadNextRegl = rs.getDate("aad_nextregl").toLocalDate();
                int aadSaved = rs.getInt("aad_saved");
                int aadManufacturerID = rs.getInt("aad_manufacturerid");
                String aadManufacturerName = rs.getString("aad_manufacturer_name");
                indexList.add(new AAD(systemID, aadID, aadModel, aadSN, aadDOM, aadJumps, aadNextRegl, aadSaved, aadManufacturerID, aadManufacturerName, stockID));
            }
            Statement stmt = rs.getStatement();
            Connection bdcon = stmt.getConnection();
            closeDB(bdcon, stmt, rs);
        }catch(SQLException e){
            
        }
        ObservableList<AAD> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<Manufacturer> getManufactirerList() {
        ArrayList<Manufacturer> indexList = new ArrayList<>();
        try{
            String selectQuery = "select mi.manufacturerid, mi.manufacturer_name, mi.manufacturer_country, mi.manufacturer_telephone, mi.manufacturer_email " +
                                 "from manufacturer_info mi";
            ResultSet rs = getData(selectQuery);
            while (rs.next()) {
                int manufacturerID = rs.getInt("manufacturerid");
                String manufacturerName = rs.getString("manufacturer_name");
                String manufacturerCountry = rs.getString("manufacturer_country");
                String manufacturerTelephone = rs.getString("manufacturer_telephone");
                String manufacturerEmail = rs.getString("manufacturer_email");
                indexList.add(new Manufacturer(manufacturerID, manufacturerName, manufacturerCountry, manufacturerTelephone, manufacturerEmail));
            }
            Statement stmt = rs.getStatement();
            Connection bdcon = stmt.getConnection();
            closeDB(bdcon, stmt, rs);
        }catch(SQLException e){
            
        }
        ObservableList<Manufacturer> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<Stock> getStockList() {
        ArrayList<Stock> stockList = new ArrayList<>();
        try{
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
        }catch(SQLException e){
            
        }
        ObservableList<Stock> list = FXCollections.observableList(stockList);
        return list;
    }
    protected ObservableList<Status> getStatusList() {
        ArrayList<Status> statusList = new ArrayList<>();
        Status active = new Status(0,"Активная");
        statusList.add(active);
        Status disable = new Status(1,"Удаленная");
        statusList.add(disable);
        Status repair = new Status(2,"В ремонте");
        statusList.add(repair);
        ObservableList<Status> list = FXCollections.observableList(statusList);
        return list;
    }
    
    protected void addSkydiveSystem(SkydiveSystem ss) {
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            Connection conn = (Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj);
            //insert system only
            PreparedStatement stmt = conn.prepareStatement("Insert into SYSTEM_INFO (SYSTEM_CODE,MANUFACTURERID,SYSTEM_MODEL,SYSTEM_SN,SYSTEM_DOM,CANOPYID,RESERVEID,AADID,STATUS,STOCKID) values (?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, ss.getSystemCode());
            stmt.setInt(2, ss.getSystemManufacturerID());
            stmt.setString(3, ss.getSystemModel());
            stmt.setString(4, ss.getSystemSN());
            stmt.setDate(5, Date.valueOf(ss.getSystemDOM()));
            stmt.setInt(6, ss.getCanopyID());
            stmt.setInt(7, ss.getReserveID());
            stmt.setInt(8, ss.getAadID());
            stmt.setInt(9, 0);
            stmt.setInt(10, ss.getStockID());
            stmt.execute();
            Connection bdcon = stmt.getConnection();
            stmt.close();
            bdcon.close();
            //if added also new elements - insert them
            if (ss.getCanopyID()!=0){
                addCanopy(new Canopy(ss.getSystemID(), ss.getCanopyModel(), ss.getCanopySize(), ss.getCanopySN(), ss.getCanopyDOM(), ss.getCanopyJumps(), ss.getCanopyManufacturerID(), ss.getCanopyManufacturerName(), ss.getStockID()));
            }
            if (ss.getReserveID()!=0){
                addReserve(new Reserve(ss.getSystemID(), ss.getReserveModel(), ss.getReserveSize(), ss.getReserveSN(), ss.getReserveDOM(), ss.getReserveJumps(), ss.getReservePackDate(), ss.getReserveManufacturerID(), ss.getReserveManufacturerName(), ss.getStockID()));
            }
            if (ss.getAadID()!=0){
                addAAD(new AAD(ss.getSystemID(), ss.getAadModel(), ss.getAadSN(), ss.getAadDOM(), ss.getAadJumps(), ss.getAadNextRegl(), ss.getAadSaved(), ss.getAadManufacturerID(), ss.getAadManufacturerName(), ss.getStockID()));
            }
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    protected void addCanopy(Canopy c) {
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            Connection conn = (Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj);
            PreparedStatement stmt = conn.prepareStatement("Insert into CANOPY_INFO (SYSTEMID,MANUFACTURERID,CANOPY_MODEL,CANOPY_SIZE,CANOPY_SN,CANOPY_DOM,CANOPY_JUMPS,STATUS,STOCKID) values (?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, c.getSystemID());
            stmt.setInt(2, c.getCanopyManufacturerID());
            stmt.setString(3, c.getCanopyModel());
            stmt.setInt(4, c.getCanopySize());
            stmt.setString(5, c.getCanopySN());
            stmt.setDate(6, Date.valueOf(c.getCanopyDOM()));
            stmt.setInt(7, c.getCanopyJumps());
            stmt.setInt(8, 0);
            stmt.setInt(9, c.getStockID());
            stmt.execute();
            Connection bdcon = stmt.getConnection();
            stmt.close();
            bdcon.close();           
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    protected void addReserve(Reserve r) {
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            Connection conn = (Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj);
            PreparedStatement stmt = conn.prepareStatement("Insert into RESERVE_INFO (SYSTEMID,MANUFACTURERID,RESERVE_MODEL,RESERVE_SIZE,RESERVE_SN,RESERVE_DOM,RESERVE_JUMPS,RESERVE_PACKDATE,STATUS,STOCKID) values (?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, r.getSystemID());
            stmt.setInt(2, r.getReserveManufacturerID());
            stmt.setString(3, r.getReserveModel());
            stmt.setInt(4, r.getReserveSize());
            stmt.setString(5, r.getReserveSN());
            stmt.setDate(6, Date.valueOf(r.getReserveDOM()));
            stmt.setInt(7, r.getReserveJumps());
            stmt.setDate(8, Date.valueOf(r.getReservePackDate()));
            stmt.setInt(9, 0);
            stmt.setInt(10, r.getStockID());
            stmt.execute();
            Connection bdcon = stmt.getConnection();
            stmt.close();
            bdcon.close();           
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    protected void addAAD(AAD aad) {
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            Connection conn = (Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj);
            PreparedStatement stmt = conn.prepareStatement("Insert into AAD_INFO (SYSTEMID,MANUFACTURERID,AAD_MODEL,AAD_SN,AAD_DOM,AAD_JUMPS,AAD_NEXTREGL,STATUS,STOCKID,AAD_SAVED) values (?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, aad.getSystemID());
            stmt.setInt(2, aad.getAadManufacturerID());
            stmt.setString(3, aad.getAadModel());
            stmt.setString(4, aad.getAadSN());
            stmt.setDate(5, Date.valueOf(aad.getAadDOM()));
            stmt.setInt(6, aad.getAadJumps());
            stmt.setDate(7, Date.valueOf(aad.getAadNextRegl()));
            stmt.setInt(8, 0);
            stmt.setInt(9, aad.getStockID());
            stmt.setInt(10, aad.getAadSaved());
            stmt.execute();
            Connection bdcon = stmt.getConnection();
            stmt.close();
            bdcon.close();           
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    protected void editSkydiveSystem(SkydiveSystem ss) {
        //some code here in the future
        /**/
    }
    
    protected void editCanopy(Canopy c) {
        //some code here in the future
        /**/
    }
    
    protected void editReserve(Reserve r) {
        //some code here in the future
        /**/
    }
    
    protected void editAAD(AAD aad) {
        //some code here in the future
        /**/
    }
    
    protected void deleteSkydiveSystem(SkydiveSystem ss) {
        //some code here in the future
        /*update system_info
set STATUS=1
where systemid=[systemid]
update canopy_info
set STATUS=1
where systemid=[systemid]
update reserve_info
set STATUS=1
where systemid=[systemid]
update aad_info
set STATUS=1
where systemid=[systemid]*/
    }
    
    protected void deleteCanopy(Canopy c) {
        //some code here in the future
        /*update [table_name]
set STATUS=1
where [element]id=[elementid]*/
    }
    
    protected void deleteReserve(Reserve r) {
        //some code here in the future
        /*update [table_name]
set STATUS=1
where [element]id=[elementid]*/
    }
    
    protected void deleteAAD(AAD aad) {
        //some code here in the future
        /*update [table_name]
set STATUS=1
where [element]id=[elementid]*/
    }
}

/*
--creating new stock
Insert into STOCK_INFO (STOCKID,STOCK_NAME,STATUS) values (?,?,?);
--creating new manufacturer
Insert into MANUFACTURER_INFO (MANUFACTURERID,MANUFACTURER_NAME,MANUFACTURER_COUNTRY,MANUFACTURER_TELEPHONE,MANUFACTURER_EMAIL) values (?,?,?,?,?);
--deleting system - full
update system_info
set STATUS=1
where systemid=[systemid]
update canopy_info
set STATUS=1
where systemid=[systemid]
update reserve_info
set STATUS=1
where systemid=[systemid]
update aad_info
set STATUS=1
where systemid=[systemid]
--deleting system - container only, other elements removing
update system_info
set STATUS=1, canopyid = 0, reserveid = 0, aadid = 0
where systemid=[systemid]
--deleting single element
update [table_name]
set STATUS=1
where [element]id=[elementid]
--changing element
update [table_name]
set [set_of_changed_variables]
where [element]id=[elementid]
--replacing elements 
UPDATE [table_name] t1 JOIN [table_name] t2
    ON t1.[element]id = [elementid_old] AND t2.[element]id = [elementid_new]
   SET t1.systemid = 0,
       t2.systemid = [systemid];
update system_info
set [element]id=[elementid_new]
where [element]id=[elementid_old]

select si.systemid, si.system_code, si.system_model, si.system_sn, si.system_dom, si.manufacturerid as system_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = si.manufacturerid) as system_manufacturer_name, ci.canopyid, ci.canopy_model, ci.canopy_size, ci.canopy_sn, ci.canopy_dom, ci.canopy_jumps, ci.manufacturerid as canopy_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ci.manufacturerid) as canopy_manufacturer_name, ri.reserveid, ri.reserve_model, ri.reserve_size, ri.reserve_sn, ri.reserve_dom, ri.reserve_jumps, ri.reserve_packdate, ri.manufacturerid as reserve_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ri.manufacturerid) as reserve_manufacturer_name, ai.aadid, ai.aad_model, ai.aad_sn, ai.aad_dom, ai.aad_jumps, ai.aad_nextregl, ai.aad_saved, ai.manufacturerid as aad_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ai.manufacturerid) as aad_manufacturer_name 
from system_info si
inner JOIN canopy_info ci ON si.canopyid = ci.canopyid and ci.status = si.status and ci.stockid = si.stockid
inner JOIN reserve_info ri ON si.reserveid = ri.reserveid and ri.status = si.status and ri.stockid = si.stockid
inner JOIN aad_info ai ON si.aadid = ai.aadid and ai.status = si.status and ai.stockid = si.stockid
where si.status = getStatus() and si.stockid = getStock();*/