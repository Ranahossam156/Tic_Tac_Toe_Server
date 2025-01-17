/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.PrintWriter;
import java.io.StringReader;
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
                default:
            
        }
    }
    public void sendGameRequest(JsonObject jsonMsg)
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
    
}
