package ru.lopa10ko.banks.Clients;

import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Banks.Bank;

import java.util.UUID;

public interface BasicClient extends ObserverObject {
    void addAccount(Account account);
    void subscribeToBank(Bank bank);
    void unsubscribeFromBank(Bank bank);
    boolean getIsVerified();
    UUID getId();
}
