package social_network.repository.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    @Column(name = "date_sending")
    private LocalDate dateSending;

    @Column(name = "time_sending")
    private LocalTime timeSending;

    //Связь с другими таблицами

    @ManyToOne
    private User sender;

    @ManyToOne
    private Chat chat;

    public Message() {
    }

    public Message(String text, LocalDate dateSending, LocalTime timeSending, User sender, Chat chat) {
        this.text = text;
        this.dateSending = dateSending;
        this.timeSending = timeSending;
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

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
