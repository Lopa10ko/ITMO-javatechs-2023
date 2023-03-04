package ru.lopa10ko.banks.Transactions;

import lombok.Getter;
import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Entities.BasicValueAmount;

import java.util.UUID;

public abstract class BasicTransaction implements Transaction {
    @Getter
    private UUID id;
    @Getter
    private Account actor;
    @Getter
    private Account recipient;
    @Getter
    private BasicValueAmount value;

    public BasicTransaction(Account actor, Account recipient, BasicValueAmount value) {
        this.id = UUID.randomUUID();
        this.actor = actor;
        this.recipient = recipient;
        this.value = value;
    }
}
