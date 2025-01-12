/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Server {
        Socket newClient;
        ServerSocket server;
        public Server()
        {
            try {
                server = new ServerSocket(5000);
                while(true){
                newClient=server.accept();
                new Thread(new ClientHandler(newClient)).start();
                }
            } catch (IOException ex) {
               System.out.println("error in server creation");
            }

        }

    
}
