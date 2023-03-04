package ru.lopa10ko.banks.Tools;

public class ClientBuilderException extends BanksException {
    private ClientBuilderException(String errorMessage) {
        super(errorMessage);
    }

    public static ClientBuilderException NoObligatoryProperties(String name, String lastName) {
        return new ClientBuilderException(String.format("Invalid Obligatory Properties: (%s - %s) should not be null or empty", name, lastName));
    }
}
