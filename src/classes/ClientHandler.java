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
            handler = new RequestHandles();
            /*
            do{ 
                try {
                    firstmsg=inputStream.readLine();
                    handler.messageDeligator(firstmsg);
                    if(handler.username!=null){
                        onlineClientSockets.put(handler.username, outputStream);
                    }
                } catch (IOException ex) {
                    outputStream.close();
                    inputStream.close();
                    mySocket.close();
                }
            }
            while(handler.username==null);*////////////////Commented for testing other requests than authentication//////////////////////////////////////////////////////////////////////////
            String receivedmsg;
            String mynametest;
            while(true){
                try {
                    receivedmsg=inputStream.readLine();
                    ///////////////the following should be removed after integration
                    try
                    {
                        mynametest=Json.createReader(new StringReader(receivedmsg)).readObject().getString("myname");
                        onlineClientSockets.put(mynametest, outputStream);
                        handler.username=mynametest;
                    }
                    catch (Exception e)
                    {
                        
                    }
                    ///////////////the previous should be removed after integration
                    handler.messageDeligator(receivedmsg);
                } catch (IOException ex) {
                    //client disconnected
                    onlineClientSockets.remove(handler.username);
                    outputStream.close();
                    inputStream.close();
                    mySocket.close();
                }
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
}
