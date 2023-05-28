package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.GenreName;

@Data
public class Genre {

    private Long id;
    private String name;
}
