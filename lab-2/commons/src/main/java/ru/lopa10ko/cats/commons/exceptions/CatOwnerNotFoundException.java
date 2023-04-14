package ru.lopa10ko.cats.commons.exceptions;

import ru.lopa10ko.cats.commons.ErrorCode;
import ru.lopa10ko.cats.commons.exceptions.basic.CatsException;
import ru.lopa10ko.cats.commons.exceptions.basic.ErrorCodeException;

import java.util.Map;
import java.util.UUID;

public class CatOwnerNotFoundException extends CatsException implements ErrorCodeException {
    private static String message;
    private final ErrorCode errorCode = ErrorCode.CAT_OWNER_NOT_FOUND;
    private final Map<String, String> errorData;
    private CatOwnerNotFoundException(String messageScheme, Map<String, String> errorData) {
        super(messageScheme);
        this.errorData = errorData;
    }

    public static CatOwnerNotFoundException byUuid(UUID uuid) {
        message = String.format("Cat owner {%s} is not found", uuid.toString());
        return new CatOwnerNotFoundException(message, Map.of("uuid", uuid.toString()));
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
