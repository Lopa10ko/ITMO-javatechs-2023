package ru.lopa10ko.banks.Tools;

public class BanksException extends RuntimeException {
    public BanksException(String errorMessage) {
        super(errorMessage);
    }
}
