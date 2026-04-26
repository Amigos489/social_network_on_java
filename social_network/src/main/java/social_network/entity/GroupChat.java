package social_network.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "group_chat")
public class GroupChat extends Chat {

    @ManyToMany(mappedBy = "chats")
    private List<User> users;

    public GroupChat() {
    }

    public GroupChat(List<User> users) {
        super();
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
