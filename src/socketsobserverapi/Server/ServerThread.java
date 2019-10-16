/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsobserverapi.Server;

import java.io.DataOutputStream;
import socketsobserverapi.Client.Common.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public class ServerThread extends Thread implements Observer {
    
    private Server server = null;
    private Socket socket = null;
    private int id;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private IClientMessageHandler clientMessageHandler;
    private INotificationHandler notificationHandler;

    public ServerThread(Socket socket, int clientID, Server server){
        this.server = server;
        this.socket = socket;
        this.id = clientID;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    

    public ObjectInputStream getReader() {
        return reader;
    }

    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
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

    
    @Override
    public void run() {
        boolean connected = true;
        try {
            // --- Preparing to send messages to client ---
            OutputStream outputStream = socket.getOutputStream();
            this.writer = new ObjectOutputStream(outputStream);
            
            // --- Preparing to receive messages from client ---
            InputStream inputStream = socket.getInputStream();
            this.reader = new ObjectInputStream(inputStream);
            
            // --- We assign an ID to the client --- 
            DataOutputStream idAssigner = new DataOutputStream(outputStream);
            idAssigner.writeInt(id);
            
            while(connected){
                Message clientMessage = (Message) reader.readObject();
                this.clientMessageHandler.handleClientMessage(clientMessage , server);
            }
        } 
        
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable observable, Object obj) {
        System.out.println("Got notified from Observable");
        this.notificationHandler.handleObservableNotification(writer, obj);
    }
    
    
    
}
