package new_social_network.entity;

import jakarta.persistence.*;
import new_social_network.primarykey.UsersFriendsPrimaryKey;

@Entity
@Table(name = "users_friends")
public class UsersFriends {

    @EmbeddedId
    private UsersFriendsPrimaryKey primaryKey;

    public UsersFriends() {
    }

    public UsersFriends(UsersFriendsPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public UsersFriendsPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(UsersFriendsPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }
}
