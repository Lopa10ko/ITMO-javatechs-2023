package ru.lopa10ko.banks.Accounts;

import ru.lopa10ko.banks.Entities.BankInfo;
import ru.lopa10ko.banks.Entities.BasicValueAmount;
import ru.lopa10ko.banks.Entities.CreditValueAmount;
import ru.lopa10ko.banks.Tools.AlienEntityException;

public class BankAccountFactory extends AccountFactory {
    private final double balance;
    private final BankInfo bankInfo;
    private final boolean isVerified;

    public BankAccountFactory(double balance, BankInfo bankInfo, boolean isVerified) {
        this.balance = balance;
        this.bankInfo = bankInfo;
        this.isVerified = isVerified;
    }

    @Override
    public <TAccount extends Account> Account createAccount(Class<TAccount> accountType) throws AlienEntityException {
        if (accountType == CreditAccount.class)
            return createCreditAccount();
        if (accountType == DebitAccount.class)
            return createDebitAccount();
        if (accountType == DepositAccount.class)
            return createDepositAccount();
        throw AlienEntityException.InvalidAccountType(accountType);
    }

    private Account createDepositAccount() {
        return new DepositAccount(new BasicValueAmount(balance), bankInfo, isVerified);
    }

    private Account createDebitAccount() {
        return new DebitAccount(new BasicValueAmount(balance), bankInfo, isVerified);
    }

    private Account createCreditAccount() {
        return new CreditAccount(new CreditValueAmount(balance), bankInfo, isVerified);
    }
}
