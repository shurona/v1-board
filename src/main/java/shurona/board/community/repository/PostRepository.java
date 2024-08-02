package shurona.board.community.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import shurona.board.community.domain.Post;

@Repository
public class PostRepository {

    @PersistenceContext
    EntityManager em;

    public Long createPost(Post post) {
        this.em.persist(post);
        return post.getId();
    }

}
