package ru.yandex.practicum.filmorate.exception;

public class IncorrectIdException extends NullPointerException {
    public IncorrectIdException(String message) {
        super(message);
    }
}
