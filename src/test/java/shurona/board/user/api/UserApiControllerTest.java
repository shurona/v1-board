package shurona.board.user.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shurona.board.user.dto.SignUpUserDto;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtAuth = "/auth/authenticate";

    @Test
    public void Mock_테스트() throws Exception {
        String user = "user";
        String password = "!!Aa452aaa";
        // given
        String jsonBody = "{\n" +
                "    \"loginId\": \"user\",\n" +
                "    \"password\": \"!!Aa452aaa\",\n" +
                "    \"nickname\": \"nickname\",\n" +
                "    \"phoneNumber\": \"010-2292-2929\",\n" +
                "    \"email\": \"abc1234@abcde.abc\"\n" +
                "}";


        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Map<String, String> credentials = new HashMap<>();
        credentials.put("loginId", user);
        credentials.put("password", password);

        // Perform the authentication request to get the JWT token
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        String contentAsString = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(contentAsString);
        String jwtToken = responseJson.get("jwt").asText();
        System.out.println("결과를 보여주세요 : " + jwtToken);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/test").header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
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