package ru.lopa10ko.dal.commons.exceptions.basic;

public class CatsException extends RuntimeException {
    protected CatsException(String messageScheme, Object... messageParameters) {
        super(String.format(messageScheme, messageParameters));
    }
}
