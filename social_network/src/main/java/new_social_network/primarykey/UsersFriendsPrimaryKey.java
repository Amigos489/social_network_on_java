package new_social_network.primarykey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UsersFriendsPrimaryKey implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "friend_id")
    private Integer friendId;

    public UsersFriendsPrimaryKey() {
    }

    public UsersFriendsPrimaryKey(Integer userId, Integer friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getFriendId() {
        return friendId;
    }
}
