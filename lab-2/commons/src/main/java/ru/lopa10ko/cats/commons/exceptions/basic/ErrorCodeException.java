package ru.lopa10ko.cats.commons.exceptions.basic;

import ru.lopa10ko.cats.commons.ErrorCode;

import java.util.Map;

public interface ErrorCodeException {
    String message();
    ErrorCode errorCode();
    Map<String, String> errorData();
}
