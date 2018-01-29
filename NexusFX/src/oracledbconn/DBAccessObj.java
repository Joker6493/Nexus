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

public class DBAccessObj {
    private Connection conn;
    
    public DBAccessObj()
       {
              this.createDBConnection();
       }

       private void createDBConnection()
       {
            try {
                     Class.forName("oracle.jdbc.driver.OracleDriver");
            }
            catch(ClassNotFoundException e) {
                     System.out.println("Oops! Can't find class oracle.jdbc.driver.OracleDriver");
                     System.exit(1);
            }
            try{
                     conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "nexusdb","Stavr0p0l");
            }
            catch(Exception e){
                     System.out.println("ERROR " + e.getMessage());
            }
       }
    
}
