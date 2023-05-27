package ru.lopa10ko.dal.commons.exceptions.basic;

import ru.lopa10ko.dal.commons.ErrorCode;

import java.util.Map;

public interface ErrorCodeException {
    String message();
    ErrorCode errorCode();
    Map<String, String> errorData();
}
