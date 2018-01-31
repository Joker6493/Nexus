/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracledbconn;

/**
 *
 * @author dboro
 */
import java.sql.*;



public class OracleConn {
    
    public String url = "jdbc:oracle:thin:@localhost:1521:XE";
    public String login = "nexusdb";
    public String password = "Stavr0p0l";
    
    public void connectDatabase(){
       try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, login, password);
            System.out.println("Соединение установлено");
        } catch (Exception e) {
            System.out.println("Ошибка " + e.getMessage());
            e.printStackTrace();
        }
       
    }
}
