/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracletestpackage;

import java.sql.*;

/**
 *
 * @author dboro
 */
public class OracleTest {
 
   public static void main(String[] args) {
        OracleTest m = new OracleTest();
        m.testDatabase();
    }
 
    private void testDatabase() {
        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String login = "nexusdb";
            String password = "Stavr0p0l";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM SYSTEM_INFO");
                System.out.println("Результат запроса");
                System.out.println("ID Систем: ");
                while (rs.next()) {
                    String str = rs.getString("systemid") + ":" + rs.getString(2);
                    System.out.println("Contact:" + str);
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
                System.out.println("Соединение закрыто");
            }
        } catch (Exception e) {
            System.out.println("Ошибка " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
