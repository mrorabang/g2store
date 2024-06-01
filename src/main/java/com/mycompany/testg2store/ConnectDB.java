/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testg2store;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Dangm
 */
public class ConnectDB {

    public Connection cn;

    public Connection getConnect() throws IOException {
        String url = "jdbc:mysql://localhost:3306/g2store";
        String usn = "root";
        String pass = "";

        try {
            //load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //connect DB
            cn = DriverManager.getConnection(url, usn, pass);
            System.out.println("Connect successfully !");
        } catch (ClassNotFoundException ex) {
            System.out.println("Loi:" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Cannot connect!");
            System.out.println(ex.getMessage());
//            App.setRoot("ErrorDB");
        }
        return cn;
    }
}
