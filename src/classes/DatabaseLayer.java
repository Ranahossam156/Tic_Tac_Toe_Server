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

/**
 *
 * @author user
 */
public class DatabaseLayer {

    static Connection con;

    static {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Tic Tac Toe", "root", "root");
        } catch (SQLException ex) {
            System.out.println("error in database connection");
        }

    }

    public static boolean insert() {
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


    //methods of database
}
