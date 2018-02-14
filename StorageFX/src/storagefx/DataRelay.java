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
import java.util.ArrayList;
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
        }
        return rs;
    }
    
    private void closeDB (Connection bdcon, Statement stmt, ResultSet rs) throws SQLException{
        rs.close();
        stmt.close();
        bdcon.close();
    }
    
    protected ObservableList<SkydiveSystem> getIndexList() throws SQLException {
        ArrayList<SkydiveSystem> indexList = new ArrayList<>();
        String selectQuery = "SELECT SI.SYSTEM_CODE, SI.SYSTEM_MODEL, CI.CANOPY_MODEL, CI.CANOPY_SIZE, RI.RESERVE_MODEL, RI.RESERVE_SIZE, AI.AAD_MODEL, CI.CANOPY_JUMPS, RI.RESERVE_PACKDATE FROM SYSTEM_INFO SI INNER JOIN CANOPY_INFO CI ON SI.CANOPYID = CI.CANOPYID INNER JOIN RESERVE_INFO RI ON SI.RESERVEID = RI.RESERVEID INNER JOIN AAD_INFO AI ON SI.AADID = AI.AADID WHERE SI.DISABLE = 0";
        ResultSet rs = getData(selectQuery);
        while (rs.next()) {
            indexList.add(new SkydiveSystem(rs.getString("system_Code"), rs.getString("system_Model"), rs.getString("canopy_Model"), rs.getInt("canopy_Size"), rs.getString("reserve_Model"), rs.getInt("reserve_Size"), rs.getString("aad_Model"), rs.getInt("canopy_Jumps"), rs.getString("reserve_PackDate")));
        }
        Statement stmt = rs.getStatement();
        Connection bdcon = stmt.getConnection();
        closeDB(bdcon, stmt, rs);
        ObservableList<SkydiveSystem> list = FXCollections.observableList(indexList);
        return list;
    }
}
