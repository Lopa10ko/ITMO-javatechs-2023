package ru.lopa10ko.banks.Transactions;

import lombok.Getter;
import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Entities.BasicValueAmount;

import java.util.UUID;

public abstract class OneSidedTransaction implements Transaction {
    @Getter
    private UUID id;
    @Getter
    private Account actor;
    @Getter
    private BasicValueAmount value;

    public OneSidedTransaction(Account actor, BasicValueAmount value) {
        this.id = UUID.randomUUID();
        this.actor = actor;
        this.value = value;
    }
}
