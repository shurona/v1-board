package shurona.board.user.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import shurona.board.user.domain.User;

@Repository
public class UserRepository {
    @PersistenceContext
    EntityManager em;

    public Long createUser(User user) {
        em.persist(user);
        return user.getId();
    }

    public User findUserById(Long userId) {
        return em.find(User.class, userId);
    }
}
