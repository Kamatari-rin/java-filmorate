package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.RatingName;

@Data
public class Genre {

    private Long id;
    @RatingName
    private String name;
}
