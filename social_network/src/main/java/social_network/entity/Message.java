package social_network.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text", nullable = false, length = 150)
    private String text;

    @Column(nullable = false, name = "date_time_sending")
    private LocalDateTime dateTimeSending;

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
        this.dateTimeSending = LocalDateTime.now();
        this.isRead = false;
        this.sender = sender;
        this.chat = chat;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDateTimeSending() {
        return dateTimeSending;
    }

    public boolean isRead() {
        return isRead;
    }

    public User getSender() {
        return sender;
    }

    public Chat getChat() {
        return chat;
    }
}
