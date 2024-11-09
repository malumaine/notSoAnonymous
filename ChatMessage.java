import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String sender;
    private String content;

    public ChatMessage() {
       
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
