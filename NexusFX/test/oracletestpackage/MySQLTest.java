/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracletestpackage;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.SAMConn;

/**
 *
 * @author dboro
 */
public class MySQLTest {
   
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDate dateDOM;
    
    public static void main(String[] args) {
        MySQLTest m = new MySQLTest();
        m.testDatabase();
    }
 
    private void testDatabase() {
        try {
            SAMConn mysqlco = new SAMConn();
            Connection bdcon = mysqlco.connectDatabase();
            try {
                Statement stmt = bdcon.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM SYSTEM_INFO");
                System.out.println("Результат запроса");
                System.out.println("ID Систем и Код: ");
                while (rs.next()) {
                    String str = rs.getString("systemid") + ":" + rs.getString(2);
                    Date dom = rs.getDate("system_dom");
                    System.out.println("ID: " + str);
                    System.out.println(dom);
                    dateDOM = dom.toLocalDate();
                    Date newDOM = dom.valueOf(dateDOM);
                    System.out.println(dateFormat.format(dateDOM));
                    System.out.println(newDOM);
                    System.out.println(dateFormat.format(rs.getDate("system_dom").toLocalDate()));
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
