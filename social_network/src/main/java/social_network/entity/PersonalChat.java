package social_network.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "personal_chat")
public class PersonalChat extends Chat {

    @ManyToOne
    private User firstUser;

    @ManyToOne
    private User secondUser;

    public PersonalChat() {
    }

    public PersonalChat(User firstUser, User secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }
}
