/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;
/**
 *
 * @author user
 */
public class DatabaseLayer {

    static Connection con = null;

    static {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());

            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Tic Tac Teo", "root", "root");

        } catch (SQLException ex) {
            System.out.println("error in database connection" + ex.getMessage());
        }

    }

    /* public static boolean insert() {
        if (con == null) {
            System.out.println("Database connection is not initialized.");
            return false;
        }
        try {
            System.out.println("test");
            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO PLAYERS (USERNAME, PASSWORD, EMAIL, SCORE, ONLINEFLAG, AVAILABLE) VALUES (?, ?, ?, ?, ?, ?)");

            insertStmt.setString(1, "mohamed");
            insertStmt.setString(2, "123");
            insertStmt.setString(3, "e@gmail.com");
            insertStmt.setInt(4, 100);
            insertStmt.setBoolean(5, true);
            insertStmt.setBoolean(6, false);

            return insertStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error in DB");
            return false;
        }
    }*/
    static boolean updateAvailabilty(String username, boolean isAvailable) {

        PreparedStatement updateStmt;
        try {
            updateStmt = con.prepareStatement("UPDATE PLAYERS SET AVAILABLE=? WHERE USERNAME=?");

            System.out.println("update statement done");
            updateStmt.setString(2, isAvailable ? "true" : "false");
            updateStmt.setString(1, username);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error in updating availabilty in DB");
            ex.printStackTrace();
            return false;
        }

    }

    public static boolean registerUser(String username, String password, String email) {
        try {
            PreparedStatement insertStmt = con.prepareStatement(
                    "INSERT INTO PLAYERS (USERNAME, PASSWORD, EMAIL, SCORE, ONLINEFLAG, AVAILABLE) VALUES (?, ?, ?, ?, ?, ?)"
            );

            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.setString(3, email);
            insertStmt.setInt(4, 0);
            insertStmt.setBoolean(5, false);
            insertStmt.setBoolean(6, false);

            return insertStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean isUsernameTaken(String username) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM PLAYERS WHERE USERNAME = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean checkLoginRequest(String name, String password) {

        if (con != null) {
            try {
                PreparedStatement selectUser = con.prepareStatement("SELECT * From PLAYERS WHERE USERNAME = ? AND PASSWORD = ?");
                selectUser.setString(1, name);
                selectUser.setString(2, password);
                ResultSet result = selectUser.executeQuery();
                if (result.next()) {
                    System.out.println("user exist");
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("connectios is null");
        }
        
        System.out.println("user is not exist");
        return false;
    }
    //methods of database
}
