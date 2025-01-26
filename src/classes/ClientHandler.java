/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import  java.lang.NullPointerException;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author user
 */
public class ClientHandler implements Runnable {
    static Map<String,PrintWriter>  onlineClientSockets= new HashMap<String,PrintWriter>();
    PrintWriter outputStream;
    BufferedReader inputStream;
    Socket mySocket;
    RequestHandles handler;
    
     public static void notifyServerDisconnection() {
        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "serverDisconnected")
                .build();

        for (PrintWriter pw : onlineClientSockets.values()) {
            pw.println(jsonmsg.toString());
        }
        onlineClientSockets.clear();
    }

    
    public ClientHandler(Socket newClient) 
    {
        this.mySocket=newClient;
    }

    @Override
    public void run() {
        String firstmsg;
        System.out.println("connection about to establish");
        
        try {
            outputStream = new PrintWriter(mySocket.getOutputStream(),true);
            inputStream= new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            JsonObject authTokenMessage = Json.createObjectBuilder()
                .add("Header", "authToken")
                .add("token", "TicTacToeServer")
                .build();
            outputStream.println(authTokenMessage.toString());
            handler = new RequestHandles();
            handler.clientOutput=outputStream;
            
            do{ 
                try {
                    firstmsg=inputStream.readLine();
                    System.out.println("first msg" + firstmsg);
                    if(firstmsg!=null)
                    {
                        handler.messageDeligator(firstmsg);
                    }
                    else
                    {
                        throw new IOException("Input stream is null");
                    }
                    
                    if(handler.authorizedUsername!=null){
                        onlineClientSockets.put(handler.authorizedUsername, outputStream);
                    }
                } catch (IOException ex) {
                    outputStream.close();
                    inputStream.close();
                    mySocket.close();
                    System.out.println("feild to read msg");
                    break;
                }
            }
            while(handler.authorizedUsername==null);
          
            while (true) {
                try {

                    handler.messageDeligator(inputStream.readLine());

                } catch (IOException ex) {
                    //client disconnected
                    System.out.println("client disconnnnnnnected");
                    //onlineClientSockets.remove(handler.authorizedUsername);
                    outputStream.close();
                    inputStream.close();
                    mySocket.close();
                    break;
                }
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
}
