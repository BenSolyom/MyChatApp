package mychatapp.solyombence.com.mychatapp.lobby;

public class Chatroom {
    private String name;
    private String description;
    private String lastTimeStamp;

    public Chatroom() {
    }

    public Chatroom(String name, String description) {
        this.name = name;
        this.description = description;
        this.lastTimeStamp = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastTimeStamp() {
        return lastTimeStamp;
    }

    public void setLastTimeStamp(String lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }
}
