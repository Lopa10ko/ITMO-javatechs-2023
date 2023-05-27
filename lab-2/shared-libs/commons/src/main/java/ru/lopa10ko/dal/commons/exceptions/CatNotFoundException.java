package ru.lopa10ko.dal.commons.exceptions;

import ru.lopa10ko.dal.commons.ErrorCode;
import ru.lopa10ko.dal.commons.exceptions.basic.CatsException;
import ru.lopa10ko.dal.commons.exceptions.basic.ErrorCodeException;

import java.util.Map;
import java.util.UUID;

public class CatNotFoundException extends CatsException implements ErrorCodeException {
    private static String message;
    private final ErrorCode errorCode = ErrorCode.CAT_NOT_FOUND;
    private final Map<String, String> errorData;
    private CatNotFoundException(String messageScheme, Map<String, String> errorData) {
        super(messageScheme);
        this.errorData = errorData;
    }

    public static CatNotFoundException byUuid(UUID uuid) {
        message = String.format("Pussy {%s} is not found", uuid.toString());
        return new CatNotFoundException(message, Map.of("uuid", uuid.toString()));
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
