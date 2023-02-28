package ru.lopa10ko.banks.Tools;

public class ValueAmountException extends BanksException {
    private ValueAmountException(String errorMessage) {
        super(errorMessage);
    }

    public static ValueAmountException InvalidValueAmount(double value) {
        return new ValueAmountException(String.format("Invalid Value: %f should be nonnegative", value));
    }
}
