/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author andri
 */
public class dataConnect {

    
    public static Connection getConnection() throws ClassNotFoundException {
        Connection connect = null;
        String url = "jdbc:mysql://localhost:3306/db_easytel";

        String username = "root";
        String password = "admin";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);
            // System.out.println("Connection established"+connect);

        } catch (SQLException ex) {
                System.out.println("in exec");
                System.out.println(ex.getMessage());
        }
        return connect;
    }
    
    public static void close(Connection con) {
        try {
            con.close();
        } catch(SQLException e) {
            System.out.print(e);
        }
    }
}
