package shurona.board.community.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import shurona.board.user.domain.User;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "POST_ID")
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private User writer;

    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    /*
    * Getter
    */

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static Post createCommunity(String title, String content, User userInfo) {

        Post post = new Post();
        post.title = title;
        post.content = content;
        post.writer = userInfo;
        post.createdAt = LocalDateTime.now();
        post.updatedAt = LocalDateTime.now();

        return post;
    }

}
