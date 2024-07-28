package shurona.board.user.dto;

import jakarta.validation.constraints.*;

public class SignUpUserDto {

    @NotEmpty
    @Size(max = 20, message = "아이디의 최대 길이는 20 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "잘못된 형식입니다.")
    private String loginId;
    @NotEmpty
    @Size(max = 20, message = "닉네임의 최대 길이는 20 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "잘못된 형식입니다.")
    private String nickname;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z0-9].*[A-Za-z0-9].*[A-Za-z0-9].*[A-Za-z0-9].*[A-Za-z0-9])(?=.*[!@#$%^&*(),.?\":{}|<>].*[!@#$%^&*(),.?\":{}|<>]).*$",
            message = "비밀번호의 형식이 맞지 않습니다.")
    private String password;
    @NotEmpty
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대전화번호 형식에 맞지 않습니다.")
    private String phoneNumber;
    @NotEmpty
//    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @Email
    private String email;


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId.strip();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname.strip();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.strip();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
