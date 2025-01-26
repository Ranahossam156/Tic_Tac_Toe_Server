package classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private boolean acceptingClients = false;

    public void startServer() {
        if (acceptingClients) {
            System.out.println("Server is already running...");
            return;
        }

        acceptingClients = true;
        try {
            serverSocket = new ServerSocket(5005);
            System.out.println("Server started...");

            while (acceptingClients) {
                try {
                    Socket newClient = serverSocket.accept();
                    System.out.println("New client connected...");
                    new Thread(new ClientHandler(newClient)).start();
                } catch (IOException e) {
                    if (acceptingClients) {
                        System.out.println("Error accepting client connection");
                        e.printStackTrace();
                    } else {
                        System.out.println("Stopped accepting new clients.");
                    }

                }
            }

        } catch (IOException ex) {
            System.out.println("Error in server setup");
            ex.printStackTrace();
        }
//        } finally {
//            stopServer();
//        }
    }

    public void stopServer() {
        acceptingClients = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                ClientHandler.notifyServerDisconnection();
                serverSocket.close();
                System.out.println("Server stopped accepting new clients");
            }
        } catch (IOException e) {
            System.out.println("Error stopping server socket");
            e.printStackTrace();
        }
    }
//
//    public void stopServer() {
//        stopAcceptingClients();
//        System.out.println("Server fully shut down.");
//    }
}
