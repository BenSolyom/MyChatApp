package mychatapp.solyombence.com.mychatapp.chatroom;

import android.graphics.Bitmap;

// The Message class holds all the required information about messages: username of the writer of the message,
// the message itself which can be a text or an image, name of the chatroom where the message was written,
// timestamp of the message, type of the message (text or image type)
public class Message {
    private String username;
    private String message;
    private Bitmap image;
    private String chatroomName;
    private String timeStamp;
    private String type;

    public Message() {
    }

    // Two constructors, one for text type, one for image type messages
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getChatroomName() {
        return chatroomName;
    }

    public void setChatroomName(String chatroomName) {
        this.chatroomName = chatroomName;
    }

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
}