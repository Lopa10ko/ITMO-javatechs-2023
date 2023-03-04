package ru.lopa10ko.banks.Tools;

import ru.lopa10ko.banks.Banks.Bank;

public class ExistingStateException extends BanksException {
    private ExistingStateException(String errorMessage) {
        super(errorMessage);
    }

    public static ExistingStateException ExistingBank(Bank bank) {
        return new ExistingStateException(String.format("Bank (%s - %s) is already in Central Bank", bank.getName(), bank.getId().toString()));
    }

    public static ExistingStateException ExistingClient(Bank bank) {
        return new ExistingStateException(String.format("Client is already in observers list of bank %s", bank.getId().toString()));
    }

    public static ExistingStateException NonExistingClient(Bank bank) {
        return new ExistingStateException(String.format("Client is not in observers list of bank %s to remove", bank.getId().toString()));
    }
}
