/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.*;

/**
 *
 * @author user
 */
public class RequestHandles {

    String authorizedUsername = null;
    PrintWriter clientOutput;
    
    
    public void messageDeligator(String networkmsg) {
        JsonReader jsonReader = Json.createReader(new StringReader(networkmsg));
        JsonObject jsonObject = jsonReader.readObject();

        //System.out.println(jsonObject.getString("Header"));
        switch (jsonObject.getString("Header")) {
            case "gameRequest":
                System.out.println("server received request");
                sendGameRequest(jsonObject);
                break;
                
                case "acceptGameRequest":
                    System.out.println("server received acceptance request");
                    gameAcceptanceRequest(jsonObject);
                break;
            case "register":
                handleRegister(jsonObject, clientOutput);
                break;
            default:
            
        }
    }
    private void sendGameRequest(JsonObject jsonMsg)
    {
        
        String opponentName=jsonMsg.getString("username");
        PrintWriter opponentPW= ClientHandler.onlineClientSockets.get(opponentName);
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "gameRequest")
                .add("username", authorizedUsername)
                .build();
        opponentPW.println(jsonmsg.toString());
    }

    private void handleRegister(JsonObject jsonMsg, PrintWriter clientOutput) {
        String username = jsonMsg.getString("username");
        String password = jsonMsg.getString("password");
        String email = jsonMsg.getString("email");

        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

        if (DatabaseLayer.isUsernameTaken(username)) {
            // Username already exists
            responseBuilder.add("Header", "registerResponse")
                    .add("success", false)
                    .add("message", "Username is already taken.");
        } else {
            // Register the user
            boolean isRegistered = DatabaseLayer.registerUser(username, password, email);
            responseBuilder.add("Header", "registerResponse")
                    .add("success", isRegistered)
                    .add("message", isRegistered ? "Registration successful!" : "Registration failed.");
          authorizedUsername=username;
        }

        // Send the response directly to the client
        clientOutput.println(responseBuilder.build().toString());
    }

    /*
    required to set username if authentication sucessful
     */
    //authenticate();

    private void gameAcceptanceRequest(JsonObject jsonMsg) {
        
            //update databse to be unavailable
            //send acceptance to other client
            DatabaseLayer.updateAvailabilty(jsonMsg.getString("opponentUsername"),false);
            DatabaseLayer.updateAvailabilty(authorizedUsername,false);
            sendGameAcceptanceResponce(jsonMsg.getString("opponentUsername"));
       
        
        
        
    }
    
    private void sendGameAcceptanceResponce(String opponentUsername)
    {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg= value
                .add("Header", "gameAcceptanceResponce")
                .add("opponentUsername", authorizedUsername)
                .build();
        
        PrintWriter opponentPW= ClientHandler.onlineClientSockets.get(opponentUsername);
        opponentPW.println(jsonmsg.toString());
    }
    

}
