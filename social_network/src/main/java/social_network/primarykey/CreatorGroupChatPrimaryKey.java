package social_network.primarykey;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import social_network.entity.GroupChat;
import social_network.entity.User;

import java.io.Serializable;

public class CreatorGroupChatPrimaryKey implements Serializable {

    @ManyToOne
    private User creator;

    @OneToOne
    private GroupChat groupChat;

    public CreatorGroupChatPrimaryKey() {
    }

    public CreatorGroupChatPrimaryKey(User creator, GroupChat groupChat) {
        this.creator = creator;
        this.groupChat = groupChat;
    }

    public User getCreator() {
        return creator;
    }

    public GroupChat getGroupChat() {
        return groupChat;
    }
}
