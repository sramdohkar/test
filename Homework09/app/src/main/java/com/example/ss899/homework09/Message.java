package com.example.ss899.homework09;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    String timeStamp,deletedBy,receiver,message,sender;
    String message_read;

    public Message(String timeStamp, String deletedBy,String message_read, String message, String receiver, String sender) {
        this.timeStamp = timeStamp;
        this.deletedBy=deletedBy;
        this.message_read = message_read;
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Message(){


    }

    public String getReceiver() {
        return receiver;
    }
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage_read() {
        return message_read;
    }

    public void setMessage_read(String message_read) {
        this.message_read = message_read;
    }
}

