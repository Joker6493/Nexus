
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracledbconn;

import java.sql.*;

/**
 *
 * @author dboro
 */
public class OracleConn {
    private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String login = "nexusdb";
    private final String password = "Stavr0p0l";    
    public Connection connectDatabase() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, login, password);
            System.out.println("Соединение установлено");
        } catch (Exception e){
            System.out.println("Ошибка " + e.getMessage());
            e.printStackTrace();
        }
        return con;
        /*            try {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SYSTEM_INFO");
        System.out.println("Результат запроса");
        System.out.println("ID Систем и Код: ");
        while (rs.next()) {
        String str = rs.getString("systemid") + ":" + rs.getString(2);
        System.out.println("ID: " + str);
        }
        rs.close();
        stmt.close();
        } finally {
        con.close();
        System.out.println("Соединение закрыто");
        }
        } catch (Exception e) {
        System.out.println("Ошибка " + e.getMessage());
        e.printStackTrace();*/
    }
}
