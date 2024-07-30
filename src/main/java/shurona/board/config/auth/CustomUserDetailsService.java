package shurona.board.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shurona.board.user.domain.User;
import shurona.board.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User userByLoginId = null;
        try {
            userByLoginId = this.userRepository.findUserByLoginId(loginId);
        } catch (Exception err) {
            this.log.error("인증 중에 에러가 발생하였습니다 \n {}", err.getMessage());
        }

        if (userByLoginId != null) {
            return new CustomUserDetails(userByLoginId);
        }

        throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
    }
}
