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
import java.sql.BatchUpdateException;
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
    private Connection conn;
    private Statement stmt;
    private boolean result;

    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public Statement getStmt() {
        return stmt;
    }
    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }
    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
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
            setConn((Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj));
            Statement st = conn.createStatement();
            rs = st.executeQuery(Query);
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
        return rs;
    }
    
    private int updateData (String Query){
        int row = 0;
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            setConn((Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj));
            if (conn.getAutoCommit()==true){
                conn.setAutoCommit(false);
            }
            Statement stmt = conn.createStatement();
            row = stmt.executeUpdate(Query);
            stmt.close();
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
        return row;
    }
    
    private Statement addQuery (String Query){
        Statement st = getStmt();
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            setConn((Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj));
            st = getConn().createStatement();
            st.addBatch(Query);
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
        return st;
    }
    private boolean executeQuery (Statement stmt) throws SQLException{
        boolean result = false;
        try{
            int[] count = stmt.executeBatch();
            stmt.close();
            getConn().commit();
            getConn().close();
            result = true;
        }catch (BatchUpdateException e) {
            System.out.println("Ошибка при выполнении запроса:" + e.getMessage());
            System.out.println("Выполнено:" + e.getUpdateCounts());
            getConn().rollback();
            getConn().close();
            result = false;
        }
        return result;
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
                                 "inner JOIN canopy_info ci ON si.canopyid = ci.canopyid and ci.status = si.status and ci.stockid = si.stockid and ci.systemid = si.systemid " +
                                 "inner JOIN reserve_info ri ON si.reserveid = ri.reserveid and ri.status = si.status and ri.stockid = si.stockid and ri.systemid = si.systemid " +
                                 "inner JOIN aad_info ai ON si.aadid = ai.aadid and ai.status = si.status and ai.stockid = si.stockid and ai.systemid = si.systemid " +
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
                                 "where ci.systemid = 0 and ci.status = "+ getStatus() +" and ci.stockid = "+ getStock();
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
                                 "where ri.systemid = 0 and ri.status = "+ getStatus() +" and ri.stockid = "+ getStock();
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
                                 "from manufacturer_info mi " +
                                 "where mi.status = " + getStatus();
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
                                 "where status = " + getStatus();
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
        Status active = new Status(0,"Активные");
        statusList.add(active);
        Status disable = new Status(1,"Удаленные");
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
            setConn((Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj));
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
            stmt.close();
            getConn().commit();
            getConn().close();
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
            stmt.close();
            getConn().commit();
            getConn().close();          
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());;
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
            stmt.close();
            getConn().commit();
            getConn().close();          
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
            stmt.close();
            getConn().commit();
            getConn().close();         
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    protected void addStock(Stock stock) {
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            Connection conn = (Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj);
            PreparedStatement stmt = conn.prepareStatement("Insert into STOCK_INFO (STOCK_NAME,STATUS) values (?,?)");
            stmt.setString(1, stock.getStockName());
            stmt.setInt(2, 0);
            stmt.execute();
            stmt.close();
            getConn().commit();
            getConn().close();          
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    protected void addManufacturer(Manufacturer man) {
        try {
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};
            Class mysql = Class.forName("utils.SAMConn");
            Object mysqlClass = mysql.newInstance();
            Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
            Connection conn = (Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj);
            PreparedStatement stmt = conn.prepareStatement("Insert into MANUFACTURER_INFO (MANUFACTURER_NAME,MANUFACTURER_COUNTRY,MANUFACTURER_TELEPHONE,MANUFACTURER_EMAIL,STATUS) values (?,?,?,?,?)");
            stmt.setString(1, man.getManufacturerName());
            stmt.setString(2, man.getManufacturerCountry());
            stmt.setString(3, man.getManufacturerTelephone());
            stmt.setString(4, man.getManufacturerEmail());
            stmt.setInt(5, 0);
            stmt.execute();
            stmt.close();
            getConn().commit();
            getConn().close();          
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    protected void editSkydiveSystem(SkydiveSystem ss, String updParams) {
        String updateQuery = "Update system_info set " + updParams + "where systemid = "+ss.getSystemID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void editCanopy(Canopy c, String updParams) {
        String updateQuery = "Update canopy_info set " + updParams + "where canopyid = "+c.getCanopyID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void editReserve(Reserve r, String updParams) {
        String updateQuery = "Update reserve_info set " + updParams + "where reserveid = "+r.getReserveID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void editAAD(AAD aad, String updParams) {
        String updateQuery = "Update aad_info set " + updParams + "where aadid = "+aad.getAadID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void editManufacturer(Manufacturer man, String updParams) {
        String updateQuery = "Update manufacturer_info set " + updParams + "where manufacturerid = "+man.getManufacturerID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void editStock (Stock stock, String updParams) {
        String updateQuery = "Update stock_info set " + updParams + "where stockid = "+stock.getStockID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
        
    protected void deleteSkydiveSystem(SkydiveSystem ss) {
        String updateQuery = "Update system_info si, canopy_info ci, reserve_info ri, aad_info ai " + 
                             "set si.STATUS = 1, ci.STATUS = 1, ri.STATUS = 1, ai.STATUS = 1 " +
                             "where si.systemid = " + ss.getSystemID() + " and si.systemid = ci.systemid = ri.systemid = ai.systemid";
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void deleteContainer(SkydiveSystem ss) {

        String updateQuery = "Update system_info si " + 
                             "set si.STATUS = 1 " +
                             "where si.systemid = " + ss.getSystemID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void deleteCanopy(Canopy c) {
        String updateQuery = "Update canopy_info ci " + 
                             "set ci.STATUS = 1 " +
                             "where ci.canopyid = " + c.getCanopyID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void deleteReserve(Reserve r) {
        String updateQuery = "Update reserve_info ri " + 
                             "set ri.STATUS = 1 " +
                             "where ri.reserveid = " + r.getReserveID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void deleteAAD(AAD aad) {
        String updateQuery = "Update aad_info ai " + 
                             "set ai.STATUS = 1 " +
                             "where ai.aadid = " + aad.getAadID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void deleteStock(Stock stock) {
        String updateQuery = "Update stock_info si " + 
                             "set si.STATUS = 1 " +
                             "where si.stockid = " + stock.getStockID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void deleteManufacturer(Manufacturer man) {
        String updateQuery = "Update manufacturer_info mi " + 
                             "set mi.STATUS = 1 " +
                             "where mi.manufacturerid = " + man.getManufacturerID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }

    protected void restoreSkydiveSystem(SkydiveSystem ss) {
        String updateQuery = "Update system_info si, canopy_info ci, reserve_info ri, aad_info ai " + 
                             "set si.STATUS = 0, ci.STATUS = 0, ri.STATUS = 0, ai.STATUS = 0 " +
                             "where si.systemid = " + ss.getSystemID() + " and si.systemid = ci.systemid = ri.systemid = ai.systemid";
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void restoreContainer(SkydiveSystem ss) {
        String updateQuery = "Update system_info si " + 
                             "set si.STATUS = 0 " +
                             "where si.systemid = " + ss.getSystemID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void restoreCanopy(Canopy c) {
        String updateQuery = "Update canopy_info ci " + 
                             "set ci.STATUS = 0 " +
                             "where ci.canopyid = " + c.getCanopyID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void restoreReserve(Reserve r) {
        String updateQuery = "Update reserve_info ri " + 
                             "set ri.STATUS = 0 " +
                             "where ri.reserveid = " + r.getReserveID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void restoreAAD(AAD aad) {
        String updateQuery = "Update aad_info ai " + 
                             "set ai.STATUS = 0 " +
                             "where ai.aadid = " + aad.getAadID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void restoreStock(Stock stock) {
        String updateQuery = "Update stock_info si " + 
                             "set si.STATUS = 0 " +
                             "where si.stockid = " + stock.getStockID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void restoreManufacturer(Manufacturer man) {
        String updateQuery = "Update manufacturer_info mi " + 
                             "set mi.STATUS = 0 " +
                             "where mi.manufacturerid = " + man.getManufacturerID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void repairSkydiveSystem(SkydiveSystem ss) {
        String updateQuery = "Update system_info si, canopy_info ci, reserve_info ri, aad_info ai " + 
                             "set si.STATUS = 2, ci.STATUS = 2, ri.STATUS = 2, ai.STATUS = 2 " +
                             "where si.systemid = " + ss.getSystemID() + " and si.systemid = ci.systemid = ri.systemid = ai.systemid";
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void repairContainer(SkydiveSystem ss) {
        String updateQuery = "Update system_info si " + 
                             "set si.STATUS = 2 " +
                             "where si.systemid = " + ss.getSystemID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void repairCanopy(Canopy c) {
        String updateQuery = "Update canopy_info ci " + 
                             "set ci.STATUS = 2 " +
                             "where ci.canopyid = " + c.getCanopyID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void repairReserve(Reserve r) {
        String updateQuery = "Update reserve_info ri " + 
                             "set ri.STATUS = 2 " +
                             "where ri.reserveid = " + r.getReserveID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
    
    protected void repairAAD(AAD aad) {
        String updateQuery = "Update aad_info ai " + 
                             "set ai.STATUS = 2 " +
                             "where ai.aadid = " + aad.getAadID();
        try {
            executeQuery(addQuery(updateQuery));
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
        }
    }
        
    protected void disassembleSkydiveSystem(SkydiveSystem ss) {
        String updateQuery = "Update system_info si " + 
                             "set si.canopyid = 0, si.reserveid = 0, si.aadid = 0 " +
                             "where si.systemid = " + ss.getSystemID();
        //debug
//        System.out.println(updateQuery);
        int row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "Update canopy_info ci " + 
                             "set ci.systemid = 0 " +
                             "where ci.systemid = " + ss.getSystemID() + " and ci.canopyid = " + ss.getCanopyID();
        //debug
//        System.out.println(updateQuery);
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "Update reserve_info ri " + 
                             "set ri.systemid = 0 " +
                             "where ri.systemid = " + ss.getSystemID() + " and ri.reserveid = " + ss.getReserveID();
        //debug
//        System.out.println(updateQuery);
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "Update aad_info ai " + 
                             "set ai.systemid = 0 " +
                             "where ai.systemid = " + ss.getSystemID() + " and ai.aadid = " + ss.getAadID();
        //debug
//        System.out.println(updateQuery);
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        
    }
    
    protected void assembleSkydiveSystem(SkydiveSystem ss, Canopy c, Reserve r, AAD aad) {
        String updateQuery = "Update system_info si " + 
                             "set si.canopyid = " + c.getCanopyID() + ", si.reserveid = " + r.getReserveID() + ", si.aadid = " + aad.getAadID() + " " +
                             "where si.systemid = " + ss.getSystemID();
        int row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "Update canopy_info ci " + 
                             "set ci.systemid = " + ss.getSystemID() + " " +
                             "where ci.systemid = 0 and ci.canopyid = " + c.getCanopyID();
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "Update reserve_info ri " + 
                             "ri.systemid = " + ss.getSystemID() + " " +
                             "where ri.systemid = 0 and ri.reserveid = " + r.getReserveID();
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "Update aad_info ai " + 
                             "ai.systemid = " + ss.getSystemID() + " " +
                             "where ai.systemid = " + ss.getSystemID() + " and ai.aadid = " + aad.getAadID();
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
    }
    
    protected void replaceCanopy(Canopy c_Old, Canopy c_New) {
        String updateQuery = "Update system_info si " + 
                             "set si.canopyid = " + c_New.getCanopyID() + " " +
                             "where si.systemid = " + c_Old.getSystemID() + " AND si.canopyid = " + c_Old.getCanopyID();
        int row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "UPDATE canopy_info t1 JOIN canopy_info t2 " +
                        "ON t1.canopyid = " + c_Old.getCanopyID() + " AND t2.canopyid = " + c_New.getCanopyID() + " " +
                       "SET t1.systemid = 0, " +
                           "t2.systemid = " + c_Old.getSystemID();
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
    }
    
    protected void replaceReserve(Reserve r_Old,Reserve r_New) {
        String updateQuery = "Update system_info si " + 
                             "set si.reserveid = " + r_New.getReserveID() + " " +
                             "where si.systemid = " + r_Old.getSystemID() + " AND si.reserveid = " + r_Old.getReserveID();
        int row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "UPDATE reserve_info t1 JOIN reserve_info t2 " +
                        "ON t1.reserveid = " + r_Old.getReserveID() + " AND t2.reserveid = " + r_New.getReserveID() + " " +
                       "SET t1.systemid = 0, " +
                           "t2.systemid = " + r_Old.getSystemID();
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
    }
    
    protected void replaceAAD(AAD aad_Old, AAD aad_New) {
        String updateQuery = "Update system_info si " + 
                             "set si.aadid = " + aad_New.getAadID() + " " +
                             "where si.systemid = " + aad_Old.getSystemID() + " AND si.aadid = " + aad_Old.getAadID();
        int row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
        updateQuery = "UPDATE aad_info t1 JOIN aad_info t2 " +
                        "ON t1.aadid = " + aad_Old.getAadID() + " AND t2.aadid = " + aad_New.getAadID() + " " +
                       "SET t1.systemid = 0, " +
                           "t2.systemid = " + aad_Old.getSystemID();
        row = updateData(updateQuery);
        if (row==0){
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку.");
        }
    }

}

