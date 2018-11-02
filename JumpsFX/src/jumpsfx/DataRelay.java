/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jumpsfx;

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
    private int newID;

    public int getNewID() {
        return newID;
    }
    public void setNewID (int newID) {
        this.newID = newID;
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
            if (getConn()==null || getConn().isClosed()){
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
    
    protected ObservableList<Stock> getStockList() {
        ArrayList<Stock> stockList = new ArrayList<>();
        try{
            openConn();
            String procedureCall = "{call getStockList(?)}";
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
    
    protected ObservableList<Status> getStatusListShort() {
        ArrayList<Status> statusList = new ArrayList<>();
        Status active = new Status(0,"Активные");
        statusList.add(active);
        Status disable = new Status(1,"Удаленные");
        statusList.add(disable);
        ObservableList<Status> list = FXCollections.observableList(statusList);
        return list;
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
}