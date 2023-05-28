package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MpaRating {
    @NotNull
    private Long id;
    private String name;
}
