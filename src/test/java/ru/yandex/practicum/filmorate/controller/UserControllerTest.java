package ru.yandex.practicum.filmorate.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.solidfire.gson.Gson;
import com.solidfire.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void preBuild() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gson = gsonBuilder.create();
    }

    @Test
    void createUserTest() throws Exception {
        User user = new User("user@mail.com", "UserLogin", "UserName", LocalDate.parse("1993-02-10"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"email\":\"user@mail.com\",\"login\":\"UserLogin\",\"name\":\"UserName\",\"birthday\":\"1993-02-10\"}")));
    }

    @Test
    void createUserWidthWrongEmailTest() throws Exception {
        User wrongEmailUser = new User("usermail.com@", "UserLoginTwo", "UserNameTwo", LocalDate.parse("1992-09-10"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(wrongEmailUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserWidthEmptyLoginTest() throws Exception {
        User emptyLoginUser = new User("usermail.com@", "", "UserNameTwo", LocalDate.parse("1992-09-10"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(emptyLoginUser)))
                .andExpect(status().isBadRequest());
    }

}