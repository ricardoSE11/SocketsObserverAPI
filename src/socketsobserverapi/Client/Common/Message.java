/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsobserverapi.Client.Common;

import java.io.Serializable;

/**
 *
 * @author rshum
 */
public abstract class Message implements Serializable {
    
    protected String sentBy;
    protected String event;
    protected Object objectOfInterest;

    public Message(String sentBy, String event, Object objectOfInterest) {
        this.sentBy = sentBy;
        this.event = event;
        this.objectOfInterest = objectOfInterest;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String SentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getObjectOfInterest() {
        return objectOfInterest;
    }

    public void setObjectOfInterest(Object objectOfInterest) {
        this.objectOfInterest = objectOfInterest;
    }

    // </editor-fold>
    
    
}
