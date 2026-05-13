package social_network.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "personal_chat")
public class PersonalChat extends Chat {

    @ManyToOne
    @JoinColumn(name = "first_user_id")
    private User firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user_id")
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
