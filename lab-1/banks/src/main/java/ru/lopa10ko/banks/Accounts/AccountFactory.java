package ru.lopa10ko.banks.Accounts;

public abstract class AccountFactory {
    public abstract <TAccount extends Account> Account createAccount(Class<TAccount> accountType);
}
