package mychatapp.solyombence.com.mychatapp.chatroom;

import android.graphics.Bitmap;

public class Message {
    private String username;
    private String message;
    private Bitmap image;
    private String chatroomName;
    private String timeStamp;
    private String type;

    public Message() {
    }

    public Message(String username, String message, String chatroomName, String timeStamp, String type) {
        this.username = username;
        this.message = message;
        this.chatroomName = chatroomName;
        this.timeStamp = timeStamp;
        this.type = type;
    }

    public Message(String username, Bitmap image, String chatroomName, String timeStamp, String type) {
        this.username = username;
        this.image = image;
        this.chatroomName = chatroomName;
        this.timeStamp = timeStamp;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatroomName() {
        return chatroomName;
    }

    public void setChatroomName(String chatroomName) {
        this.chatroomName = chatroomName;
    }

    /*public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }*/

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}