/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author user
 */
public class DatabaseLayer {
    static Connection con;
    
    static
    {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            con= DriverManager.getConnection("jdbc:derby://localhost:1527/Players","root","root");
        } catch (SQLException ex) {
            System.out.println("error in database connection");
        } 
       
    }
    
    public static boolean insert()
    {
        try {
            System.out.println("test");
            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO PLAYERS (USERNAME, PASSWORD, EMAIL, SCORE, ONLINEFLAG, AVAILABLE) VALUES (?, ?, ?, ?, ?, ?)");

           
            insertStmt.setString(1, "mohamed");         
            insertStmt.setString(2, "123");           
            insertStmt.setString(3, "e@gmail.com");   
            insertStmt.setInt(4, 100);        
            insertStmt.setBoolean(5, true);           
            insertStmt.setBoolean(6, false);        

            return insertStmt.executeUpdate()>0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error in DB");
            return false;
        }
    }
    
    static boolean updateAvailabilty(String username,boolean isAvailable ) 
    {
        
        PreparedStatement  updateStmt;
        try {
            updateStmt = con.prepareStatement("UPDATE PLAYERS SET AVAILABLE=? WHERE USERNAME=?");
         
        System.out.println("update statement done");
        updateStmt.setString(2,isAvailable? "true":"false");
        updateStmt.setString(1,username);
        return updateStmt.executeUpdate()>0;
        }
        catch (SQLException ex) {
            System.out.println("Error in updating availabilty in DB");
            ex.printStackTrace();
            return false;
        }

    }
    //methods of database
    
}
