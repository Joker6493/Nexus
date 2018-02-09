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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import connection.OracleConn;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataRelay {
    
    private ResultSet getData (String Query){
        ResultSet rs = null;
        try {
            OracleConn oraco = new OracleConn();
            Connection bdcon = oraco.connectDatabase();
            Statement stmt = bdcon.createStatement();
            rs = stmt.executeQuery(Query);
        } catch (Exception e) {
            System.out.println("Ошибка " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }
    
    private void closeDB (Connection bdcon, Statement stmt, ResultSet rs) throws SQLException{
        rs.close();
        stmt.close();
        bdcon.close();
    }
    
    private ObservableList<SkydiveSystem> getIndexList() throws SQLException {
        ObservableList<SkydiveSystem> list = null;
        String selectQuery = "SELECT\n" +
"SI.SYSTEM_CODE, \n" +
"SI.SYSTEM_MODEL, \n" +
"CI.CANOPY_MODEL, \n" +
"CI.CANOPY_SIZE, \n" +
"RI.RESERVE_MODEL, \n" +
"RI.RESERVE_SIZE, \n" +
"AI.AAD_MODEL, \n" +
"CI.CANOPY_JUMPS, \n" +
"RI.RESERVE_PACKDATE\n" +
"FROM SYSTEM_INFO SI\n" +
"INNER JOIN CANOPY_INFO CI ON SI.CANOPYID = CI.CANOPYID\n" +
"INNER JOIN RESERVE_INFO RI ON SI.RESERVEID = RI.RESERVEID\n" +
"INNER JOIN AAD_INFO AI ON SI.AADID = AI.AADID\n" +
"WHERE SI.DISABLE = 0;";
        ResultSet rs = getData(selectQuery);
        
        while (rs.next()) {
            
            SkydiveSystem skySystem = new SkydiveSystem();
            
        
        }
        
        Statement stmt = rs.getStatement();
        Connection bdcon = stmt.getConnection();
        closeDB(bdcon, stmt, rs);
        
        
        
        
        /*TableColumn <SkydiveSystem, String> systemCode = new TableColumn("Код системы");
        TableColumn <SkydiveSystem, String> systemModel = new TableColumn("Модель системы");
        TableColumn <SkydiveSystem, String> canopy = new TableColumn("Основной парашют");
        TableColumn <SkydiveSystem, String> canopyModel = new TableColumn("Модель");
        TableColumn <SkydiveSystem, Integer> canopySize = new TableColumn("Размер, кв.фут");
        canopy.getColumns().addAll(canopyModel,canopySize);
        TableColumn <SkydiveSystem, String> reserve = new TableColumn("Запасной парашют");
        TableColumn <SkydiveSystem, String> reserveModel = new TableColumn("Модель");
        TableColumn <SkydiveSystem, Integer> reserveSize = new TableColumn("Размер, кв.фут");
        reserve.getColumns().addAll(reserveModel,reserveSize);
        TableColumn <SkydiveSystem, String> aadModel = new TableColumn("Модель AAD");
        TableColumn <SkydiveSystem, Integer> canopyJumps = new TableColumn("Прыжков на куполе");
        TableColumn <SkydiveSystem, String> reservePackDate = new TableColumn("Дата переукладки");*/
        
        //ObservableList<SkydiveSystem> list = FXCollections.observableArrayList();
        
        return list;
    }
    
}
