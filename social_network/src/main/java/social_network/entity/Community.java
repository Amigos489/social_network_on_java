package social_network.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description", length = 100)
    private String description;

    @ManyToMany(mappedBy = "communities")
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "community_id")
    private List<UserPost> userPosts;

    public Community() {
    }

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
        this.users = new ArrayList<>();
        this.userPosts = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<UserPost> getUserPosts() {
        return userPosts;
    }
}
