package shurona.board.user.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import shurona.board.user.domain.User;

import java.util.List;

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

    public User findUserByLoginId(String loginId) {

        String query = "select u from User as u where u.loginId = :loginId";

        List<User> userList =
                this.em.createQuery(query, User.class).setParameter("loginId", loginId).getResultList();

        return userList.get(0);
    }
}
