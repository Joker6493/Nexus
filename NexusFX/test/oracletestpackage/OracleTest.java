/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracletestpackage;

import java.sql.*;
import utils.OracleConn;

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
            OracleConn oraco = new OracleConn();
            Connection bdcon = oraco.connectDatabase();
            try {
                Statement stmt = bdcon.createStatement();
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
                bdcon.close();
                System.out.println("Соединение закрыто");
            }
        } catch (Exception e) {
            System.out.println("Ошибка " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
