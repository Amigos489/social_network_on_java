package social_network.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String text;

    @Column(nullable = false, name = "date_sending")
    private LocalDate dateSending;

    @Column(nullable = false, name = "time_sending")
    private LocalTime timeSending;

    @Column(nullable = false, name = "is_read")
    private boolean isRead;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Chat chat;

    public Message() {
    }

    public Message(String text, User sender, Chat chat) {
        this.text = text;
        this.dateSending = LocalDate.now();
        this.timeSending = LocalTime.now();
        this.isRead = false;
        this.sender = sender;
        this.chat = chat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
