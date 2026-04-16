package social_network.repository.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "date_publication")
    private LocalDate datePublication;

    @Column(name = "time_publication")
    private LocalTime timePublication;

    public Post() {
    }

    public Post(String content) {
        this.content = content;
        this.datePublication = LocalDate.now();
        this.timePublication = LocalTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public LocalTime getTimePublication() {
        return timePublication;
    }

    public void setTimePublication(LocalTime timePublication) {
        this.timePublication = timePublication;
    }
}
