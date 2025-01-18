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
    String username=null; 
    
    public void messageDeligator(String networkmsg){
        JsonReader jsonReader = Json.createReader(new StringReader(networkmsg));
        JsonObject jsonObject = jsonReader.readObject();

        
        //System.out.println(jsonObject.getString("Header"));
        switch(jsonObject.getString("Header"))
        {
            case "gameRequest":
                System.out.println("server received request");
                sendGameRequest(jsonObject);
                break;
                
                case "acceptGameRequest":
                    System.out.println("server received acceptance request");
                    gameAcceptanceRequest(jsonObject);
                default:
        }
    }
    private void sendGameRequest(JsonObject jsonMsg)
    {
        
        String opponentName=jsonMsg.getString("username");
        PrintWriter opponentPW= ClientHandler.onlineClientSockets.get(opponentName);
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg= value
                .add("Header", "gameRequest")
                .add("username", username)
                .build();
        opponentPW.println(jsonmsg.toString());
    }
    
    
    /*
    required to set username if authentication sucessful
    */
    //authenticate();

    private void gameAcceptanceRequest(JsonObject jsonMsg) {
        
            //update databse to be unavailable
            //send acceptance to other client
            DatabaseLayer.updateAvailabilty(jsonMsg.getString("opponentUsername"),false);
            DatabaseLayer.updateAvailabilty(username,false);
            sendGameAcceptanceResponce(jsonMsg.getString("opponentUsername"));
       
        
        
        
    }
    
    private void sendGameAcceptanceResponce(String opponentUsername)
    {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg= value
                .add("Header", "gameAcceptanceResponce")
                .add("opponentUsername", username)
                .build();
        
        PrintWriter opponentPW= ClientHandler.onlineClientSockets.get(opponentUsername);
        opponentPW.println(jsonmsg.toString());
    }
    
}
