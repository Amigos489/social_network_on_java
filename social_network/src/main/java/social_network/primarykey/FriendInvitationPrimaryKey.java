package social_network.primarykey;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import social_network.entity.User;

import java.io.Serializable;

@Embeddable
public class FriendInvitationPrimaryKey implements Serializable {

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    public FriendInvitationPrimaryKey() {
    }

    public FriendInvitationPrimaryKey(User sender, User recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }
}
