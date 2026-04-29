package social_network.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "date_time_publication", nullable = false)
    private LocalDateTime dateTimePublication;

    public Post() {
    }

    public Post(String content) {
        this.content = content;
        this.dateTimePublication = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateTimePublication() {
        return dateTimePublication;
    }
}
