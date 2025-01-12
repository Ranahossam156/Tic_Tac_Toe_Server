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
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ClientHandler implements Runnable {
    static Map<String,PrintWriter>  onlineClientSockets= new HashMap<String,PrintWriter>();
    PrintWriter outputStream;
    BufferedReader inputStream;
    Socket mySocket;
    

    
    public ClientHandler(Socket newClient) 
    {
        this.mySocket=newClient;
    }

    @Override
    public void run() {
        String firstmsg;
        try {
            outputStream=new PrintWriter(mySocket.getOutputStream());
            inputStream= new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            firstmsg=inputStream.readLine();
            //deligationfunction
            RequestHandles.messageDeligator(firstmsg);
            if(RequestHandles.username!=null)
            {
                onlineClientSockets.put(RequestHandles.username, outputStream);
            }
            else
            {
                outputStream.close();
                inputStream.close();
                mySocket.close();
                
            }
            
            //database online 
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
