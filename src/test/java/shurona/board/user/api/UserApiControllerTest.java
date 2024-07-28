package shurona.board.user.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shurona.board.user.dto.SignUpUserDto;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Mock_테스트() throws Exception {
        // given

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/test"))
                .andExpect(MockMvcResultMatchers.content().string("hello"));

    }

    //
    @Test
    public void 유저생성_정상_테스트() throws Exception {

        String jsonBody = "{\n" +
                "    \"loginId\": \"user\",\n" +
                "    \"password\": \"!!Aa452aaa\",\n" +
                "    \"nickname\": \"nickname\",\n" +
                "    \"phoneNumber\": \"010-2292-2929\",\n" +
                "    \"email\": \"abc1234@abcde.abc\"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void 잘못된이메일_테스트() throws Exception {
        String jsonBody = "{\n" +
                "    \"loginId\": \"user\",\n" +
                "    \"password\": \"!!Aa452aaa\",\n" +
                "    \"nickname\": \"nickname\",\n" +
                "    \"phoneNumber\": \"010-2292-2929\",\n" +
                "    \"email\": \"abc.abc\"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void 잘못된휴대전화_테스트() throws Exception {
        String jsonBody = "{\n" +
                "    \"loginId\": \"user\",\n" +
                "    \"password\": \"!!Aa452aaa\",\n" +
                "    \"nickname\": \"nickname\",\n" +
                "    \"phoneNumber\": \"010-ddd-2929\",\n" +
                "    \"email\": \"abc1234@abcde.abc\"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void 잘못된비밀번호_테스트() throws Exception {
        String jsonBody = "{\n" +
                "    \"loginId\": \"user\",\n" +
                "    \"password\": \"!aaaaaaaaaa\",\n" +
                "    \"nickname\": \"nickname\",\n" +
                "    \"phoneNumber\": \"010-2292-2929\",\n" +
                "    \"email\": \"abc1234@abcde.abc\"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}