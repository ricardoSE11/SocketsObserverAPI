/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsobserverapi.Server;

import java.io.ObjectOutputStream;
import socketsobserverapi.Client.Common.Message;

/**
 *
 * @author rshum
 */
public interface INotificationHandler {
    
    public Message handleObservableNotification(ObjectOutputStream clientWriter, Object obj);
    
}
