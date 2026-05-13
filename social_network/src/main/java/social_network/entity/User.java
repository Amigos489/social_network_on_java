package social_network.entity;

import jakarta.persistence.*;
import social_network.enums.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "login", unique = true , nullable = false, length = 254)
    private String login;

    @Column(name = "password", nullable = false, length = 72)
    private String password;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @ManyToMany
    private Set<Chat> chats;

    @ManyToMany
    private Set<Community> communities;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 5)
    private Role role;

    public User() {
    }

    public User(String name, String surname, String login, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.chats = new HashSet<>();
        this.communities = new HashSet<>();
        this.role = role;
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

    public Set<Chat> getChats() {
        return chats;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public Role getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
}
