/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.threads.ProcessRequest;

/**
 *
 * @author Mirko
 */
public class Server extends Thread {

    List<Socket> sockets = new ArrayList<>();
    ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(7868);
            while (!serverSocket.isClosed()) {

                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                sockets.add(socket);

                System.out.println("Client connected!");
                handleClient(socket);
            }

        } catch (SocketException se) {
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(rs.ac.bg.fon.ps.server.Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopServer() throws IOException {
        for (Socket s : sockets) {
            s.close();
        }
        serverSocket.close();
        serverSocket = null;
    }

    private void handleClient(Socket socket) {
        ProcessRequest processRequest = new ProcessRequest(socket);
        processRequest.start();
    }

}
