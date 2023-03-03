package ru.lopa10ko.banks.Clients;

import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Banks.Bank;

import java.util.UUID;

public interface BasicClient extends ObserverObject {
    /**
     * Add built account entity to client's list of accounts
     * <p>
     *     Considering situation where client should be able to
     *     aggregate many accounts for transferring transaction within.
     *
     * @param account account of Type(Deposit/Debit/Credit)
     * @see #getIsVerified();
     */
    void addAccount(Account account);

    /**
     * Observer pattern method, providing simple subscription mechanism.
     * @param bank concrete Bank entity
     */
    void subscribeToBank(Bank bank);

    /**
     * Observer pattern method, providing simple unsub-ing mechanism.
     * @param bank concrete Bank entity
     */
    void unsubscribeFromBank(Bank bank);

    /**
     * Implementation of flag for client verification.
     * <p>
     *     Client may not have Address and Passport fields set up.
     *     It results in limitation of all client's accounts remitting options.
     *     Account type must have a BankInfo type (containing WithdrawLimit VO).
     * @return boolean value (true/false -- account verified/unverified)
     */
    boolean getIsVerified();
    UUID getId();
}
