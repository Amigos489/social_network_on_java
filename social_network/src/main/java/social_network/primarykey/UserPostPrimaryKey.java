package social_network.primarykey;

import jakarta.persistence.*;
import social_network.entity.Community;
import social_network.entity.Post;
import social_network.entity.User;

import java.io.Serializable;

@Embeddable
public class UserPostPrimaryKey implements Serializable {

    @ManyToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    private Community community;

    public UserPostPrimaryKey() {
    }

    public UserPostPrimaryKey(User user, Post post, Community community) {
        this.user = user;
        this.post = post;
        this.community = community;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public Community getCommunity() {
        return community;
    }
}
