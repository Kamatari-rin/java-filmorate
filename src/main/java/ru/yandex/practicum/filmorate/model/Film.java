package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmDuration;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Long id;
    @NotBlank(message = "Name cannot be empty.")
    private String name;
    @Size(max = 200, message = "The description length is 200 characters.")
    private String description;
    @ReleaseDate(message = "The film's release date must be after 1894-01-24.")
    private LocalDate releaseDate;
    @FilmDuration(message = "Film duration should be positive.")
    private int duration;
    private final Set<Long> likes = new HashSet<>();
    private Set<Genre> genres = new HashSet<>();
    private MpaRating rating;

    public Film(String name,
                String description,
                LocalDate releaseDate,
                int duration,
                Set<Genre> genres,
                MpaRating rating) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.rating = rating;
    }

    public Film(){}
}
