package ru.lopa10ko.cats.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.lopa10ko.cats.commons.ErrorCode;
import ru.lopa10ko.cats.commons.exceptions.basic.CatsException;
import ru.lopa10ko.cats.commons.exceptions.basic.ErrorCodeException;

import java.util.Map;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class CreationException extends CatsException implements ErrorCodeException {
    private static String message = "Creation is not completed";
    private static final ErrorCode errorCode = ErrorCode.ENTITY_NOT_CREATED;
    private final Map<String, String> errorData;
    private CreationException(String messageScheme, Map<String, String> errorData) {
        super(messageScheme);
        this.errorData = errorData;
    }

    public static CreationException throwException() {
        return new CreationException(message, Map.of("errorCode", errorCode.toString()));
    }
    @Override
    public String message() {
        return message;
    }

    @Override
    public ErrorCode errorCode() {
        return errorCode;
    }

    @Override
    public Map<String, String> errorData() {
        return errorData;
    }
}