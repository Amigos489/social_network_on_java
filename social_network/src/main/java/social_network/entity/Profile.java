package social_network.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @OneToOne
    private User user;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "birthday")
    private LocalDate birthday;

    @OneToMany
    @JoinColumn(name = "profile_id")
    private List<Post> posts;

    public Profile() {
    }

    public Profile(User user) {
        this.user = user;
        this.posts = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
