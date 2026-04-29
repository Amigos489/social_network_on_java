package social_network.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "surname", nullable = false, length = 30)
    private String surname;

    @Column(name = "login", unique = true , nullable = false, length = 30)
    private String login;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @ManyToMany
    private List<Chat> chats;

    @ManyToMany
    private List<Community> communities;

    public User() {
    }

    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.chats = new ArrayList<>();
        this.communities = new ArrayList<>();
        this.isBlocked = false;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
}
