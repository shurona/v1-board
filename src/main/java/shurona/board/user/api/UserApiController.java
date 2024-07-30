package shurona.board.user.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shurona.board.user.domain.User;
import shurona.board.common.dto.ApiResponse;
import shurona.board.user.dto.SignUpUserDto;
import shurona.board.user.service.UserService;

@RestController
@RequestMapping("user")
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserApiController(UserService userService, PasswordEncoder passwordEncoder) {
//        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("test")
    public String testing() {
        return "hello";
    }

    @GetMapping("login")
    public ApiResponse<String> login( ) {


        return ApiResponse.success("ok", HttpStatus.OK.value());
    }

    @PostMapping("signup")
    public ApiResponse<Long> createUser(@Validated  @RequestBody SignUpUserDto signUpUserDto
    ) {

        Long userId = null;
        try {
            // password encode
            String encodePassword = this.passwordEncoder.encode(signUpUserDto.getPassword());
            userId = this.userService.createUser(
                    signUpUserDto.getLoginId(), encodePassword, signUpUserDto.getNickname(), signUpUserDto.getEmail(), signUpUserDto.getPhoneNumber()
            );
        } catch (Exception e) {
            // 에러 핸들링
            this.log.error(e.getMessage());
        }

        if (userId == null) {
            return ApiResponse.fail(500, "유저를 생성 하는 중에 오류가 발생했습니다.");
        }

        return ApiResponse.success(userId, HttpStatus.CREATED.value());
    }

    @GetMapping("{id}")
    public ApiResponse<UserOutput> findUserById(@PathVariable("id") Long userId) {
        User finduser = this.userService.findByUserId(userId);
        if (finduser == null) {
            return ApiResponse.fail(404, "없는 유저입니다.");
        }
        return ApiResponse.success(new UserOutput(finduser.getEmail(), finduser.getPhoneNumber()), HttpStatus.OK.value());
    }

    public static class UserOutput {
        String username;
        String nickname;

        public UserOutput(String username, String nickname) {
            this.username = username;
            this.nickname = nickname;
        }

        public String getUsername() {
            return username;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
