package ru.lopa10ko.banks.Tools;

import java.util.UUID;

public class AlienEntityException extends BanksException {
    private AlienEntityException(String errorMessage) {
        super(errorMessage);
    }

    public static AlienEntityException InvalidAccount(UUID id) {
        return new AlienEntityException(String.format("Invalid or non-existing account with UUID (%s)", id.toString()));
    }

    public static AlienEntityException InvalidBank(UUID id) {
        return new AlienEntityException(String.format("No bank for account with UUID (%s)", id.toString()));
    }

    public static <T> AlienEntityException InvalidAccountType(Class<T> type) {
        return new AlienEntityException(String.format("Invalid account type (%s), should be Deposit/Debit/Credit", type));
    }
}
