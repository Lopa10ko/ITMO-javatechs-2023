package ru.lopa10ko.banks.Clients;

import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Banks.Bank;

public interface BasicClient extends ObserverObject {
    void addAccount(Account account);
    void subscribeToBank(Bank bank);
    void unsubscribeFromBank(Bank bank);
    boolean getIsVerified();
}
