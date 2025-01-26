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


            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Tic Tak Toe", "root", "root");


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
            updateStmt.setString(1, isAvailable ? "true" : "false");
            updateStmt.setString(2, username);
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
            insertStmt.setInt(4, 100);
            insertStmt.setBoolean(5, true);
            insertStmt.setBoolean(6, true);

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


    public static int getOnlineCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM PLAYERS WHERE ONLINEFLAG = TRUE";
        try (PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public static int getAvailableCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM PLAYERS WHERE AVAILABLE = TRUE";
        try (PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public static int getOfflineCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM PLAYERS WHERE ONLINEFLAG = FALSE";
        try (PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public static boolean checkLoginRequest(String name, String password) {
        if (con != null) {
            try {
                PreparedStatement selectUser = con.prepareStatement(
                    "SELECT * FROM PLAYERS WHERE USERNAME = ? AND PASSWORD = ?"
                );
                selectUser.setString(1, name);
                selectUser.setString(2, password);
                ResultSet result = selectUser.executeQuery();
                if (result.next()) {
                    // Update ONLINEFLAG and AVAILABLE to true
                    updatePlayerStatus(name, true, true);
                    System.out.println("User exists and status updated to online and available.");
                    return true;
                }
            } catch (SQLException ex) {
                    Logger.getLogger(DatabaseLayer.class.getName()).log(Level.SEVERE, null, ex);
              }
        } else {
                System.out.println("Connection is null.");
          }
            System.out.println("User does not exist or login failed.");
            return false;
    }
    public static boolean updatePlayerStatus(String username, boolean isOnline, boolean isAvailable) {
    try {
        PreparedStatement updateStmt = con.prepareStatement(
            "UPDATE PLAYERS SET ONLINEFLAG=?, AVAILABLE=? WHERE USERNAME=?"
        );
        updateStmt.setBoolean(1, isOnline);
        updateStmt.setBoolean(2, isAvailable);
        updateStmt.setString(3, username);
        return updateStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error updating player status in DB: " + ex.getMessage());
            ex.printStackTrace();
            return false;
          }
    }
    public static boolean isPlayerAvailable(String username) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT AVAILABLE FROM PLAYERS WHERE USERNAME = ?"
            );
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("AVAILABLE");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; 
    }

    //methods of database
    public static Player getPlayerinfo(String username)
    {
        try {
            PreparedStatement selectUser = con.prepareStatement("SELECT  EMAIL, SCORE From PLAYERS WHERE USERNAME = ?");
            selectUser.setString(1, username);
            ResultSet result = selectUser.executeQuery();
            if(result.next())
            {
                return new Player(username, result.getString(1), result.getInt(2));
            }
            else
            {
                return null;
            }
            //System.out.println(result.getString(1)+ result.getInt(2));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("");
        return null;
    }

    static boolean updateScore(String winnerName, String loserName) {

        try {
            PreparedStatement winnerStmt = con.prepareStatement("UPDATE PLAYERS SET SCORE = SCORE+10 WHERE USERNAME=?");
            winnerStmt.setString(1, winnerName);
            
            PreparedStatement loserStmt = con.prepareStatement("UPDATE PLAYERS SET SCORE = SCORE-10 WHERE USERNAME=?");
            loserStmt.setString(1, loserName);
            return winnerStmt.executeUpdate() > 0 && loserStmt.executeUpdate()>0;
        } catch (SQLException ex) {
            System.out.println("error updating score in DB");
            return false;
        }
    }

}
