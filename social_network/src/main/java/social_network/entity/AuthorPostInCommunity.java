package social_network.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import social_network.primarykey.UserPostPrimaryKey;

@Entity
@Table(name = "author_post_in_community")
public class AuthorPostInCommunity {

    @EmbeddedId
    private UserPostPrimaryKey primaryKey;

    public AuthorPostInCommunity() {
    }

    public AuthorPostInCommunity(UserPostPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public UserPostPrimaryKey getPrimaryKey() {
        return primaryKey;
    }
}
