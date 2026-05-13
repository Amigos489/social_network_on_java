package social_network.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<User> users;

    @OneToMany
    @JoinColumn(name = "community_id")
    private Set<PostInCommunity> posts;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    public Community() {
    }

    public Community(String name, String description, User creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.users = new HashSet<>();
        this.posts = new HashSet<>();
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

    public Set<User> getUsers() {
        return users;
    }

    public Set<PostInCommunity> getPosts() {
        return posts;
    }

    public User getCreator() {
        return creator;
    }
}
