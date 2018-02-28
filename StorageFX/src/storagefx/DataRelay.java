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
import utils.OracleConn_test;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataRelay {
    
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    private ResultSet getData (String Query){
        ResultSet rs = null;
        try {
            Class oracl = Class.forName("utils.OracleConn");
            //oracl.newInstance().
            //OracleConn ora = (OracleConn) oracl.newInstance();
            //Method conn = oracl.getMethod("connectDatabase");
            
            /*
            //Call a method dynamically (Reflection)
            Class params[] = {};
            Object paramsObj[] = {};

            Class oracl = Class.forName("utils.OracleConn");
            Object oraClass = thisClass.newInstance();
            Method oraConnMethod = thisClass.getDeclaredMethod("connectDatabase", params);
            System.out.println(oraConnMethod.invoke(oraClass, paramsObj).toString());
            */
            
            OracleConn_test oraco = new OracleConn_test();
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
    
    protected ObservableList<SkydiveSystem> getSystemsList() throws SQLException {
        ArrayList<SkydiveSystem> indexList = new ArrayList<>();
        String selectQuery = "select si.systemid, si.system_code, si.system_model, si.system_sn, si.system_dom, si.manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = si.manufacturerid), " +
                             "ci.canopyid, ci.canopy_model, ci.canopy_size, ci.canopy_sn, ci.canopy_dom, ci.canopy_jumps, ci.manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ci.manufacturerid), " +
                             "ri.reserveid, ri.reserve_model, ri.reserve_size, ri.reserve_sn, ri.reserve_dom, ri.reserve_jumps, ri.reserve_packdate, ri.manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ri.manufacturerid), " +
                             "ai.aadid, ai.aad_model, ai.aad_sn, ai.aad_dom, ai.aad_jumps, ai.aad_nextregl, ai.aad_fired, ai.manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ai.manufacturerid) " +
                             "from system_info si " +
                             "inner JOIN canopy_info ci ON si.canopyid = ci.canopyid and ci.disable = 0 " +
                             "inner JOIN reserve_info ri ON si.reserveid = ri.reserveid and ri.disable = 0 " +
                             "inner JOIN aad_info ai ON si.aadid = ai.aadid and ai.disable = 0 " +
                             "where si.disable = 0";
        ResultSet rs = getData(selectQuery);
        while (rs.next()) {
            indexList.add(new SkydiveSystem(rs.getInt("systemid"), rs.getString("system_code"), rs.getString("system_model"), rs.getString("system_sn"), rs.getDate("system_dom").toLocalDate(), rs.getInt(6), rs.getString(7), rs.getInt("canopyid"), rs.getString("canopy_model"), rs.getInt("canopy_size"), rs.getString("canopy_sn"), rs.getDate("canopy_dom").toLocalDate(), rs.getInt("canopy_jumps"), rs.getInt(14), rs.getString(15), rs.getInt("reserveid"), rs.getString("reserve_model"), rs.getInt("reserve_size"), rs.getString("reserve_sn"), rs.getDate("reserve_dom").toLocalDate(), rs.getInt("reserve_jumps"), rs.getDate("reserve_packdate").toLocalDate(),  rs.getInt(23), rs.getString(24), rs.getInt("aadid"), rs.getString("aad_model"), rs.getString("aad_sn"), rs.getDate("aad_dom").toLocalDate(), rs.getInt("aad_jumps"), rs.getDate("aad_nextregl").toLocalDate(), rs.getBoolean("aad_fired"), rs.getInt(32), rs.getString(33)));
        }
        Statement stmt = rs.getStatement();
        Connection bdcon = stmt.getConnection();
        closeDB(bdcon, stmt, rs);
        ObservableList<SkydiveSystem> list = FXCollections.observableList(indexList);
        return list;
    }
}
