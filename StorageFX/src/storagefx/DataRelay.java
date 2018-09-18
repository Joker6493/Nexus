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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataRelay {
    private int status = 0;
    private int stock = 2;
    private Connection conn;
    private boolean result;
    private int newID;

    public int getNewID() {
        return newID;
    }
    public void setNewID (int newID) {
        this.newID = newID;
    }
    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
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
    
    private void openConn(){
        try {
            if (getConn().isClosed()){
        //Call a method dynamically (Reflection)
                Class params[] = {};
                Object paramsObj[] = {};
                Class mysql = Class.forName("utils.SAMConn");
                Object mysqlClass = mysql.newInstance();
                Method mysqlConnMethod = mysql.getDeclaredMethod("connectDatabase", params);
                setConn((Connection) mysqlConnMethod.invoke(mysqlClass, paramsObj));
            }
        }catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException | SQLException e) {
            System.out.println("Ошибка соединения с сервером:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    private void closeConn (){
        try{
            getConn().close();
        } catch (SQLException e) {
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    private void rollbackConn (){
        try{
            getConn().rollback();
            getConn().close();
        } catch (SQLException e) {
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
//            e.printStackTrace();
        }
    }
    
    protected ObservableList<SkydiveSystem> getSystemsList() {
        ArrayList<SkydiveSystem> indexList = new ArrayList<>();
        try{
            openConn();
            String procedureCall = "{call getSkydiveSystemsList(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1,getStatus());
            stmt.setInt(2,getStock());
            ResultSet rs = stmt.executeQuery();
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
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
        }finally{
            closeConn();
        }
        ObservableList<SkydiveSystem> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<SkydiveSystem> getContainersList() {
        ArrayList<SkydiveSystem> indexList = new ArrayList<>();
        try{
            openConn();
            String procedureCall = "{call getContainersList(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1,getStatus());
            stmt.setInt(2,getStock());
            ResultSet rs = stmt.executeQuery();
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
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
        }finally{
            closeConn();
        }
        ObservableList<SkydiveSystem> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<Canopy> getCanopyList() {
        ArrayList<Canopy> indexList = new ArrayList<>();
        try{
            openConn();
            String procedureCall = "{call getCanopyList(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1,getStatus());
            stmt.setInt(2,getStock());
            ResultSet rs = stmt.executeQuery();
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
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
        }finally{
            closeConn();
        }
        ObservableList<Canopy> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<Reserve> getReserveList() {
        ArrayList<Reserve> indexList = new ArrayList<>();
        try{
            openConn();
            String procedureCall = "{call getReserveList(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1,getStatus());
            stmt.setInt(2,getStock());
            ResultSet rs = stmt.executeQuery();
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
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
        }finally{
            closeConn();
        }
        ObservableList<Reserve> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<AAD> getAadList() {
        ArrayList<AAD> indexList = new ArrayList<>();
        try{
            openConn();
            String procedureCall = "{call getAadList(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1,getStatus());
            stmt.setInt(2,getStock());
            ResultSet rs = stmt.executeQuery();
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
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
        }finally{
            closeConn();
        }
        ObservableList<AAD> list = FXCollections.observableList(indexList);
        return list;
    }

    protected ObservableList<Manufacturer> getManufacturerList() {
        ArrayList<Manufacturer> indexList = new ArrayList<>();
        try{
            openConn();
            String procedureCall = "{call getManufacturerList(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1,getStatus());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int manufacturerID = rs.getInt("manufacturerid");
                String manufacturerName = rs.getString("manufacturer_name");
                String manufacturerCountry = rs.getString("manufacturer_country");
                String manufacturerTelephone = rs.getString("manufacturer_telephone");
                String manufacturerEmail = rs.getString("manufacturer_email");
                indexList.add(new Manufacturer(manufacturerID, manufacturerName, manufacturerCountry, manufacturerTelephone, manufacturerEmail));
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
        }finally{
            closeConn();
        }
        ObservableList<Manufacturer> list = FXCollections.observableList(indexList);
        return list;
    }
    
    protected ObservableList<Stock> getStockList() {
        ArrayList<Stock> stockList = new ArrayList<>();
        try{
            openConn();
            String procedureCall = "{call getStockList(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1,getStatus());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int stockID = rs.getInt("stockid");
                String stockName = rs.getString("stock_name");
                stockList.add(new Stock(stockID, stockName));
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Ошибка MySQL сервера:" + e.getMessage());
        }finally{
            closeConn();
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
            openConn();
            //first insert new canopy, reserve and aad (if nesessary)
            Canopy newCanopy = new Canopy(ss.getSystemID(), ss.getCanopyID(), ss.getCanopyModel(), ss.getCanopySize(), ss.getCanopySN(), ss.getCanopyDOM(), ss.getCanopyJumps(), ss.getCanopyManufacturerID(), ss.getCanopyManufacturerName(), ss.getStockID());
            Reserve newReserve = new Reserve(ss.getSystemID(), ss.getReserveID(), ss.getReserveModel(), ss.getReserveSize(), ss.getReserveSN(), ss.getReserveDOM(), ss.getReserveJumps(), ss.getReservePackDate(), ss.getReserveManufacturerID(), ss.getReserveManufacturerName(), ss.getStockID());
            AAD newAAD = new AAD(ss.getSystemID(), ss.getAadID(), ss.getAadModel(), ss.getAadSN(), ss.getAadDOM(), ss.getAadJumps(), ss.getAadNextRegl(), ss.getAadSaved(), ss.getAadManufacturerID(), ss.getAadManufacturerName(), ss.getStockID());
            if (newCanopy.getCanopyID()==0){
                addCanopy(newCanopy);
                ss.setCanopyID(getNewID());
                newCanopy.setCanopyID(getNewID());
            }
            if (newReserve.getReserveID()==0){
                addReserve(newReserve);
                ss.setReserveID(getNewID());
                newReserve.setReserveID(getNewID());
            }
            if (newAAD.getAadID()==0){
                addAAD(newAAD);
                ss.setAadID(getNewID());
                newAAD.setAadID(getNewID());
            }
            //insert system
            setNewID(0);
            String procedureCall = "{? = call addSkydiveSystem(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, ss.getSystemCode());
            stmt.setString(3, ss.getSystemModel());
            stmt.setString(4, ss.getSystemSN());
            stmt.setDate(5, Date.valueOf(ss.getSystemDOM()));
            stmt.setInt(6, ss.getSystemManufacturerID());
            stmt.setInt(7, ss.getCanopyID());
            stmt.setInt(8, ss.getReserveID());
            stmt.setInt(9, ss.getAadID());
            stmt.setInt(10, ss.getStockID());
            stmt.setInt(11, 0);
            stmt.execute();
            //get ID of new inserted item
            setNewID(stmt.getInt(1));  
            stmt.close();
            if (getNewID()==0){
                getConn().rollback();
                System.out.println("При выполнении запроса произошла ошибка. Повторите, пожалуйста, попытку позднее");
                return;
            }else{
                getConn().commit();
            }
            ss.setSystemID(getNewID());
            //finally assemble system (getting element's ID mechanizm required)
            assembleSkydiveSystem(ss, newCanopy, newReserve, newAAD);
            
        } catch (SQLException e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
            rollbackConn();
//            e.printStackTrace();
        }finally{
            try {
                if (!getConn().isClosed()){  
                    closeConn();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка связи с сервером:" + ex.getMessage());
                closeConn();
            }
        }
    }
    
    protected void addCanopy(Canopy c) {
        try {
            openConn();
            setNewID(0);
            String procedureCall = "{? = call addCanopy(?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, c.getCanopyModel());
            stmt.setInt(3, c.getCanopySize());
            stmt.setString(4, c.getCanopySN());
            stmt.setDate(5, Date.valueOf(c.getCanopyDOM()));
            stmt.setInt(6, c.getCanopyJumps());
            stmt.setInt(7, c.getCanopyManufacturerID());
            stmt.setInt(8, c.getSystemID());
            stmt.setInt(9, c.getStockID());
            stmt.setInt(10, 0);
            stmt.execute();
            //get ID of new inserted item
            setNewID(stmt.getInt(1));
            stmt.close();
            if (getNewID()==0){
                getConn().rollback();
                System.out.println("При выполнении запроса произошла ошибка. Повторите, пожалуйста, попытку позднее");
                return;
            }else{
                getConn().commit();
            }
            c.setCanopyID(getNewID());
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
            rollbackConn();
//            e.printStackTrace();
        }finally{
            if (c.getSystemID()==0){
                closeConn();
            }
        }
    }
    
    protected void addReserve(Reserve r) {
        try {
            openConn();
            setNewID(0);
            String procedureCall = "{? = call addReserve(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, r.getReserveModel());
            stmt.setInt(3, r.getReserveSize());
            stmt.setString(4, r.getReserveSN());
            stmt.setDate(5, Date.valueOf(r.getReserveDOM()));
            stmt.setInt(6, r.getReserveJumps());
            stmt.setDate(7, Date.valueOf(r.getReservePackDate()));
            stmt.setInt(8, r.getReserveManufacturerID());
            stmt.setInt(9, r.getSystemID());
            stmt.setInt(10, r.getStockID());            
            stmt.setInt(11, 0);
            stmt.execute();
            //get ID of new inserted item
            setNewID(stmt.getInt(1));
            stmt.close();
            if (getNewID()==0){
                getConn().rollback();
                System.out.println("При выполнении запроса произошла ошибка. Повторите, пожалуйста, попытку позднее");
                return;
            }else{
                getConn().commit();
            } 
            r.setReserveID(getNewID());
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
            rollbackConn();
//            e.printStackTrace();
        }finally{
            if (r.getSystemID()==0){
                closeConn();
            }
        }
    }
    
    protected void addAAD(AAD aad) {
        try {
            openConn();
            setNewID(0);
            String procedureCall = "{? = call addAAD(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, aad.getAadModel());
            stmt.setString(3, aad.getAadSN());
            stmt.setDate(4, Date.valueOf(aad.getAadDOM()));
            stmt.setInt(5, aad.getAadJumps());
            stmt.setDate(6, Date.valueOf(aad.getAadNextRegl()));
            stmt.setInt(7, aad.getAadSaved());
            stmt.setInt(8, aad.getAadManufacturerID());
            stmt.setInt(9, aad.getSystemID());
            stmt.setInt(10, aad.getStockID());
            stmt.setInt(11, 0);
            stmt.execute();
            //get ID of new inserted item
            setNewID(stmt.getInt(1));
            stmt.close();
            if (getNewID()==0){
                getConn().rollback();
                System.out.println("При выполнении запроса произошла ошибка. Повторите, пожалуйста, попытку позднее");
                return;
            }else{
                getConn().commit();
            } 
            aad.setAadID(getNewID());
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
            rollbackConn();
//            e.printStackTrace();
        }finally{
            if (aad.getSystemID()==0){
                closeConn();
            }
        }
    }
    
    protected void addStock(Stock stock) {
        try {
            openConn();
            String procedureCall = "{? = call addStock(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, stock.getStockName());
            stmt.setInt(3, 0);
            stmt.execute();
            //get ID of new inserted item
            setNewID(stmt.getInt(1));
            stmt.close();
            if (getNewID()==0){
                getConn().rollback();
                System.out.println("При выполнении запроса произошла ошибка. Повторите, пожалуйста, попытку позднее");
                return;
            }else{
                getConn().commit();
            } 
            stock.setStockID(getNewID());
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
            rollbackConn();
//            e.printStackTrace();
        }finally{
            closeConn();
        }
    }
    
    protected void addManufacturer(Manufacturer man) {
        try {
            openConn();
            String procedureCall = "{? = call addManufacturer(?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, man.getManufacturerName());
            stmt.setString(3, man.getManufacturerCountry());
            stmt.setString(4, man.getManufacturerTelephone());
            stmt.setString(5, man.getManufacturerEmail());
            stmt.setInt(6, 0);
            stmt.execute();
            //get ID of new inserted item
            setNewID(stmt.getInt(1));
            stmt.close();
            if (getNewID()==0){
                getConn().rollback();
                System.out.println("При выполнении запроса произошла ошибка. Повторите, пожалуйста, попытку позднее");
                return;
            }else{
                getConn().commit();
            } 
            man.setManufacturerID(getNewID());
        } catch (Exception e) {
            System.out.println("Ошибка связи с сервером:" + e.getMessage());
            rollbackConn();
//            e.printStackTrace();
        }finally{
            closeConn();
        }
    }
    
    protected void editSkydiveSystem(SkydiveSystem ss) {
        try {
            openConn();
            String procedureCall = "{call editSkydiveSystem(?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, ss.getSystemID());
            stmt.setString(2, ss.getSystemCode());
            stmt.setString(3, ss.getSystemModel());
            stmt.setString(4, ss.getSystemSN());
            stmt.setDate(5, Date.valueOf(ss.getSystemDOM()));
            stmt.setInt(6, ss.getSystemManufacturerID());
            stmt.setInt(7, ss.getCanopyID());
            stmt.setInt(8, ss.getReserveID());
            stmt.setInt(9, ss.getAadID());
            stmt.setInt(10, ss.getStockID());
            stmt.setInt(11, getStatus());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void editCanopy(Canopy c) {
        try {
            openConn();
            setNewID(0);
            String procedureCall = "{call editCanopy(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, c.getCanopyID());
            stmt.setString(2, c.getCanopyModel());
            stmt.setInt(3, c.getCanopySize());
            stmt.setString(4, c.getCanopySN());
            stmt.setDate(5, Date.valueOf(c.getCanopyDOM()));
            stmt.setInt(6, c.getCanopyJumps());
            stmt.setInt(7, c.getCanopyManufacturerID());
            stmt.setInt(8, c.getSystemID());
            stmt.setInt(9, c.getStockID());
            stmt.setInt(10, getStatus());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void editReserve(Reserve r) {
        try {
            openConn();
            String procedureCall = "{call editReserve(?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, r.getReserveID());
            stmt.setString(2, r.getReserveModel());
            stmt.setInt(3, r.getReserveSize());
            stmt.setString(4, r.getReserveSN());
            stmt.setDate(5, Date.valueOf(r.getReserveDOM()));
            stmt.setInt(6, r.getReserveJumps());
            stmt.setDate(7, Date.valueOf(r.getReservePackDate()));
            stmt.setInt(8, r.getReserveManufacturerID());
            stmt.setInt(9, r.getSystemID());
            stmt.setInt(10, r.getStockID());            
            stmt.setInt(11, getStatus());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void editAAD(AAD aad) {
        try {
            openConn();
            String procedureCall = "{call editAAD(?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.registerOutParameter(1, aad.getAadID());
            stmt.setString(2, aad.getAadModel());
            stmt.setString(3, aad.getAadSN());
            stmt.setDate(4, Date.valueOf(aad.getAadDOM()));
            stmt.setInt(5, aad.getAadJumps());
            stmt.setDate(6, Date.valueOf(aad.getAadNextRegl()));
            stmt.setInt(7, aad.getAadSaved());
            stmt.setInt(8, aad.getAadManufacturerID());
            stmt.setInt(9, aad.getSystemID());
            stmt.setInt(10, aad.getStockID());
            stmt.setInt(11, getStatus());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void editManufacturer(Manufacturer man) {
        try {
            openConn();
            String procedureCall = "{call editManufacturer(?,?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, man.getManufacturerID());
            stmt.setString(2, man.getManufacturerName());
            stmt.setString(3, man.getManufacturerCountry());
            stmt.setString(4, man.getManufacturerTelephone());
            stmt.setString(5, man.getManufacturerEmail());
            stmt.setInt(6, getStatus());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void editStock (Stock stock) {
        try {
            openConn();
            String procedureCall = "{call editStock(?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, stock.getStockID());
            stmt.setString(2, stock.getStockName());
            stmt.setInt(3, getStatus());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
        
    protected void setStatusSkydiveSystem(SkydiveSystem ss, int status) {
        /*
        0 - Active
        1 - Deleted
        2 - In repair
        */
        try {
            openConn();
            String procedureCall = "{call setStatusSkydiveSystem(?,?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, ss.getSystemID());
            stmt.setInt(2, ss.getCanopyID());
            stmt.setInt(3, ss.getReserveID());
            stmt.setInt(4, ss.getAadID());
            stmt.setInt(5, status);
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void setStatusContainer(SkydiveSystem ss, int status) {
        /*
        0 - Active
        1 - Deleted
        2 - In repair
        */
        try {
            openConn();
            String procedureCall = "{call setStatusContainer(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, ss.getSystemID());
            stmt.setInt(2, status);
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void setStatusCanopy(Canopy c, int status) {
        /*
        0 - Active
        1 - Deleted
        2 - In repair
        */
        try {
            openConn();
            String procedureCall = "{call setStatusCanopy(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, c.getCanopyID());
            stmt.setInt(2, status);
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void setStatusReserve(Reserve r, int status) {
        /*
        0 - Active
        1 - Deleted
        2 - In repair
        */
        try {
            openConn();
            String procedureCall = "{call setStatusReserve(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, r.getReserveID());
            stmt.setInt(2, status);
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void setStatusAAD(AAD aad, int status) {
        /*
        0 - Active
        1 - Deleted
        2 - In repair
        */
        try {
            openConn();
            String procedureCall = "{call setStatusAAD(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, aad.getAadID());
            stmt.setInt(2, status);
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void setStatusStock(Stock stock, int status) {
        /*
        0 - Active
        1 - Deleted
        */
        try {
            openConn();
            String procedureCall = "{call setStatusStock(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, stock.getStockID());
            stmt.setInt(2, status);
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void setStatusManufacturer(Manufacturer man, int status) {
        /*
        0 - Active
        1 - Deleted
        */
        try {
            openConn();
            String procedureCall = "{call setStatusManufacturer(?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, man.getManufacturerID());
            stmt.setInt(2, status);
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
  
    protected void disassembleSkydiveSystem(SkydiveSystem ss) {
        try {
            openConn();
            String procedureCall = "{call disassembleSkydiveSystem(?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, ss.getSystemID());
            stmt.setInt(2, ss.getCanopyID());
            stmt.setInt(3, ss.getReserveID());
            stmt.setInt(4, ss.getAadID());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void assembleSkydiveSystem(SkydiveSystem ss, Canopy c, Reserve r, AAD aad) {
        try {
            openConn();
            String procedureCall = "{call assembleSkydiveSystem(?,?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, ss.getSystemID());
            stmt.setInt(2, c.getCanopyID());
            stmt.setInt(3, r.getReserveID());
            stmt.setInt(4, aad.getAadID());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void replaceCanopy(Canopy c_Old, Canopy c_New) {
        try {
            openConn();
            String procedureCall = "{call replaceCanopy(?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, c_Old.getSystemID());
            stmt.setInt(2, c_Old.getCanopyID());
            stmt.setInt(3, c_New.getCanopyID());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void replaceReserve(Reserve r_Old,Reserve r_New) {
        try {
            openConn();
            String procedureCall = "{call replaceReserve(?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, r_Old.getSystemID());
            stmt.setInt(2, r_Old.getReserveID());
            stmt.setInt(3, r_New.getReserveID());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
    
    protected void replaceAAD(AAD aad_Old, AAD aad_New) {
        try {
            openConn();
            String procedureCall = "{call replaceAAD(?,?,?)}";
            CallableStatement stmt = getConn().prepareCall(procedureCall);
            stmt.setInt(1, aad_Old.getSystemID());
            stmt.setInt(2, aad_Old.getAadID());
            stmt.setInt(3, aad_New.getAadID());
            stmt.execute();
            stmt.close();
            getConn().commit();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении запроса. Проверьте правильность данных и повторите попытку." + ex.getMessage());
            rollbackConn();
        }finally{
            closeConn();
        }
    }
}