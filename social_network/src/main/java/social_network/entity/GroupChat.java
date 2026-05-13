package social_network.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "group_chat")
public class GroupChat extends Chat {

    @ManyToMany(mappedBy = "chats")
    private Set<User> users;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    public GroupChat() {
    }

    public GroupChat(Set<User> users, User creator) {
        super();
        this.users = users;
        this.creator = creator;
    }

    public Set<User> getUsers() {
        return users;
    }

    public User getCreator() {
        return creator;
    }
}
