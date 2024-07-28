package shurona.board.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shurona.board.user.domain.User;
import shurona.board.user.domain.UserRole;
import shurona.board.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저 저장
     */
    @Transactional
    public Long createUser(String loginId, String password, String nickname, String email, String phoneNumber) {
        User user = User.CreateUser(loginId, password, nickname, email, phoneNumber, UserRole.ADMIN);

        return this.userRepository.createUser(user);
    }

    /**
     * UserId 하나로 검색
     */
    public User findByUserId(Long userId) {
        return this.userRepository.findUserById(userId);
    }

}
