package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidfire.gson.Gson;
import com.solidfire.gson.GsonBuilder;
import com.solidfire.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.lang.reflect.Type;
import java.time.LocalDate;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Slf4j
@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @BeforeEach
    public void preBuild() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gson = gsonBuilder.create();
    }

    @Test
    void createFilmTest() throws Exception {
        Film film = new Film("FilmName", "FilmDescription", LocalDate.parse("1980-02-11"), 120);
        Type type = new TypeToken<Film>() {}.getType();

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(film, type)))
                        .andExpect(status().isOk());
    }

    @Test
    void createFilmsBefore1885Test() throws Exception {
        Film film1883Year = new Film("FilmName", "FilmDescription", LocalDate.parse("1883-02-11"), 120);
        Type type = new TypeToken<Film>() {}.getType();

        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(film1883Year, type)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFilmWidthNegativeDurationLoginTest() throws Exception {
        Film filmNegativeDuration = new Film("FilmName", "FilmDescription", LocalDate.parse("1983-02-11"), -120);
        Type type = new TypeToken<Film>() {}.getType();

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(filmNegativeDuration, type)))
                .andExpect(status().isBadRequest());
    }
}