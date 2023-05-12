package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    String errorMessage;
    HttpStatus responseStatus;

    public AppException(String errorMessage, HttpStatus responseStatus) {
        this.errorMessage = errorMessage;
        this.responseStatus = responseStatus;
    }
}
