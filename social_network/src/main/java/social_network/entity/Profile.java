package social_network.entity;

import jakarta.persistence.*;
import social_network.enums.Gender;

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

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 6)
    private Gender gender;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
