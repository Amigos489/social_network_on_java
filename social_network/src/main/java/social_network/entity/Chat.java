package social_network.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @OneToMany
    @JoinColumn(name = "chat_id")
    private List<Message> messages;

    public Chat() {
        this.messages = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
