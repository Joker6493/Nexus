
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.*;

/**
 *
 * @author dboro
 */
public class OracleConn {
    private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String login = "nexusdb";
    //private final String password = "St";
    private final String password = "Stavr0p0l";    
    public Connection connectDatabase() {
        Connection oracon = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            oracon = DriverManager.getConnection(url, login, password);
            System.out.println("Соединение установлено");
        } catch (Exception e){
            System.out.println("Ошибка " + e.getMessage());
            e.printStackTrace();
        }
        return oracon;
    }
}
