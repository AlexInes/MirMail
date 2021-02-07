package com.ines.model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailMessage {
    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleIntegerProperty size;
    private SimpleObjectProperty<Date> receivedDate;
    private boolean isRead;
    private Message message;

    public EmailMessage(String subject, String sender, String recipient, int size, Date receivedDate, boolean isRead, Message message){
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleIntegerProperty(size);
        this.receivedDate = new SimpleObjectProperty<Date>(receivedDate);
        this.isRead = isRead;
        this.message = message;
    }

    public String getSubject(){
        return this.subject.get();
    }
    public  String getSender(){
        return this.sender.get();
    }
    public String getRecipient(){
        return this.recipient.get();
    }
    public Integer getSize(){
        return this.size.get();
    }
    public Date getReceivedDate(){
        return this.receivedDate.get();
    }

    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }
    public Message getMessage(){
        return this.message;
    }

}
