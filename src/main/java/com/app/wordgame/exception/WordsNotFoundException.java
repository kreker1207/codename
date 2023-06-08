package com.app.wordgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WordsNotFoundException extends FileNotFoundException {
    public WordsNotFoundException(String message) {
        super(message);
    }
}
