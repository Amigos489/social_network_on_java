package new_social_network.entity;

import jakarta.persistence.*;
import new_social_network.enums.FriendInvitationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "friend_invitation")
public class FriendInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    @Column(name = "date_sending")
    private LocalDate dateSending;

    @Column(name = "time_sending")
    private LocalTime timeSending;

    @Enumerated(EnumType.STRING)
    @Column(name = "friend_invitation_status")
    private FriendInvitationStatus friendInvitationStatus;

    public FriendInvitation() {
    }

    public FriendInvitation(User sender, User recipient, LocalDate dateSending, LocalTime timeSending, FriendInvitationStatus friendInvitationStatus) {
        this.sender = sender;
        this.recipient = recipient;
        this.dateSending = dateSending;
        this.timeSending = timeSending;
        this.friendInvitationStatus = friendInvitationStatus;
    }

    public FriendInvitation(User sender, User recipient, FriendInvitationStatus friendInvitationStatus) {
        this.sender = sender;
        this.recipient = recipient;
        this.dateSending = LocalDate.now();
        this.timeSending = LocalTime.now();
        this.friendInvitationStatus = friendInvitationStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public LocalDate getDateSending() {
        return dateSending;
    }

    public void setDateSending(LocalDate dateSending) {
        this.dateSending = dateSending;
    }

    public LocalTime getTimeSending() {
        return timeSending;
    }

    public void setTimeSending(LocalTime timeSending) {
        this.timeSending = timeSending;
    }

    public FriendInvitationStatus getFriendInvitationStatus() {
        return friendInvitationStatus;
    }

    public void setFriendInvitationStatus(FriendInvitationStatus friendInvitationStatus) {
        this.friendInvitationStatus = friendInvitationStatus;
    }
}
