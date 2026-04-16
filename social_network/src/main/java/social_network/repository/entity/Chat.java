package social_network.repository.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_creating")
    private LocalDate dateCreating;

    @ManyToMany(mappedBy = "chats")
    private List<User> users;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    public Chat() {
    }

    public Chat(LocalDate dateCreating, List<User> users, List<Message> messages) {
        this.dateCreating = dateCreating;
        this.users = users;
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateCreating() {
        return dateCreating;
    }

    public void setDateCreating(LocalDate dateCreating) {
        this.dateCreating = dateCreating;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
