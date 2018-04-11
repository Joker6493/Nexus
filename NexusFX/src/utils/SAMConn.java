
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
public class SAMConn {
    private final String url = "jdbc:mysql://localhost:3306/sam_node";
    private final String login = "root";
    //private final String password = "St";
    private final String password = "pathfinder";    
    public Connection connectDatabase() {
        Connection mysqlcon = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlcon = DriverManager.getConnection(url, login, password);
            System.out.println("Соединение установлено");
        } catch (Exception e){
            System.out.println("Ошибка " + e.getMessage());
            e.printStackTrace();
        }
        return mysqlcon;
    }
}
