package testpackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author dboro
 */
import java.sql.*;
import utils.OracleConn;

public class TestClass { 
    public static void main(String[] args) {
        OracleConn orc = new OracleConn();
        orc.connectDatabase();
        
    }
}