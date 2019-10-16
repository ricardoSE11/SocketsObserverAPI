/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsobserverapi.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public abstract class Server {
    
    protected int portNumber;
    protected boolean listening = true;
    protected HashMap<Integer , ServerThread> clients;
    protected ArrayList<Observable> observableResources;
    
    protected IClientMessageHandler clientMessageHandler;
    protected INotificationHandler notificationHandler;
    
    public Server(int portnumber, IClientMessageHandler clientMessageHandler , INotificationHandler notificationHandler){
        this.portNumber = portnumber;
        this.clientMessageHandler = clientMessageHandler;
        this.notificationHandler = notificationHandler;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public HashMap<Integer, ServerThread> getClients() {
        return clients;
    }

    public void setClients(HashMap<Integer, ServerThread> clients) {
        this.clients = clients;
    }
    

    public ArrayList<Observable> getObservableResources() {
        return observableResources;
    }

    public void setObservableResources(ArrayList<Observable> observableResources) {
        this.observableResources = observableResources;
    }

    public IClientMessageHandler getClientMessageHandler() {
        return clientMessageHandler;
    }

    public void setClientMessageHandler(IClientMessageHandler clientMessageHandler) {
        this.clientMessageHandler = clientMessageHandler;
    }

    public INotificationHandler getNotificationHandler() {
        return notificationHandler;
    }

    public void setNotificationHandler(INotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }
    
    
    
    // </editor-fold>
    
    public void run(){
        try(ServerSocket serverSocket = new ServerSocket(portNumber)){
            System.out.println("Started server");
            while(listening){
                /* If we receive a connection request, and it is successful, we
                *  create Thread and a Socket, and associated them with a user. 
                *  So the server keep listening for more connection requests */
                int clientID = clients.size();
                Socket clientSocket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(clientSocket, clientID, this);
                serverThread.setClientMessageHandler(clientMessageHandler);
                serverThread.setNotificationHandler(notificationHandler);
                clients.put(clientID, serverThread);
                serverThread.start();
                System.out.println("Got connection request number: " + clientID);
                clientID++;
            }
        } 
        
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
