package social_network.entity;

import jakarta.persistence.*;
import social_network.enums.FriendInvitationStatus;
import social_network.primarykey.FriendInvitationPrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "friend_invitation")
public class FriendInvitation {

    @EmbeddedId
    private FriendInvitationPrimaryKey primaryKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "friend_invitation_status", length = 10)
    private FriendInvitationStatus friendInvitationStatus;

    public FriendInvitation() {
    }
    public FriendInvitation(FriendInvitationPrimaryKey primaryKey, FriendInvitationStatus friendInvitationStatus) {
        this.primaryKey = primaryKey;
        this.friendInvitationStatus = friendInvitationStatus;
    }

    public FriendInvitation(User sender, User recipient, FriendInvitationStatus friendInvitationStatus) {
        this.primaryKey = new FriendInvitationPrimaryKey(sender, recipient);
        this.friendInvitationStatus = friendInvitationStatus;
    }

    public FriendInvitationPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public FriendInvitationStatus getFriendInvitationStatus() {
        return friendInvitationStatus;
    }

    public void setFriendInvitationStatus(FriendInvitationStatus friendInvitationStatus) {
        this.friendInvitationStatus = friendInvitationStatus;
    }
}
