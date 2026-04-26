package social_network.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import social_network.primarykey.CreatorGroupChatPrimaryKey;

@Entity
@Table(name = "creator_group_chat")
public class CreatorGroupChat {

    @EmbeddedId
    private CreatorGroupChatPrimaryKey primaryKey;

    public CreatorGroupChat() {
    }

    public CreatorGroupChat(CreatorGroupChatPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public CreatorGroupChatPrimaryKey getPrimaryKey() {
        return primaryKey;
    }
}
