/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracledbconn;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dboro
 */
public class ConnectionCheck implements Runnable {
    public String statusConn = "Off-line";
    OracleConn oc = new OracleConn();
    Connection dbc = oc.connectDatabase();

    @Override
    public void run() {
        try {
            if (dbc.isValid(1)) {
                    statusConn = "On-line";
                }
            } catch (SQLException ex) {
            ex.printStackTrace();
            }
    }
}
