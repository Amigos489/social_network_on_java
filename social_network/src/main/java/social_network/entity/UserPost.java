package social_network.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import social_network.primarykey.UserPostPrimaryKey;

@Entity
@Table(name = "user_post")
public class UserPost {

    @EmbeddedId
    private UserPostPrimaryKey primaryKey;

    public UserPost() {
    }

    public UserPost(UserPostPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public UserPostPrimaryKey getPrimaryKey() {
        return primaryKey;
    }
}
