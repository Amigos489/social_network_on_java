package social_network.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_in_community")
public class PostInCommunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "date_time_publication", nullable = false)
    private LocalDateTime dateTimePublication;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    public PostInCommunity() {
    }

    public PostInCommunity(String content, User author, Community community) {
        this.content = content;
        this.dateTimePublication = LocalDateTime.now();
        this.author = author;
        this.community = community;
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

    public User getAuthor() {
        return author;
    }

    public Community getCommunity() {
        return community;
    }
}
