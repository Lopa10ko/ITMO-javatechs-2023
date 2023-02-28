package ru.lopa10ko.banks.Banks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Accounts.BankAccountFactory;
import ru.lopa10ko.banks.Clients.BasicClient;
import ru.lopa10ko.banks.Clients.ObserverObject;
import ru.lopa10ko.banks.Entities.BankInfo;
import ru.lopa10ko.banks.Entities.BasicValueAmount;
import ru.lopa10ko.banks.Tools.AlienEntityException;
import ru.lopa10ko.banks.Tools.CommissionDayException;
import ru.lopa10ko.banks.Tools.ExistingStateException;
import ru.lopa10ko.banks.Transactions.CancellationTransaction;
import ru.lopa10ko.banks.Transactions.ReplenishmentTransaction;
import ru.lopa10ko.banks.Transactions.TransferTransaction;
import ru.lopa10ko.banks.Transactions.WithdrawTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Bank implements ObservableObject {
    private final List<BasicClient> clients;
    private final List<ObserverObject> observerClients;
    private final List<Account> accounts;
    @Getter(AccessLevel.PUBLIC)
    private String name;
    @Getter(AccessLevel.PUBLIC)
    private BankInfo bankInfo;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.NONE)
    private UUID id;

    public Bank(String name, BankInfo bankInfo)
    {
        clients = new ArrayList<>();
        observerClients = new ArrayList<>();
        accounts = new ArrayList<>();
        this.name = name;
        this.bankInfo = bankInfo;
        id = UUID.randomUUID();
    }

    public <TAccount extends Account> UUID createAccount(Class<TAccount> accountType, BasicClient client, double balance) {
        BankAccountFactory accountFactory = new BankAccountFactory(balance, bankInfo, client.getIsVerified());
        Account account = accountFactory.createAccount(accountType);
        accounts.add(account);
        if (!clients.contains(client))
            clients.add(client);
        client.addAccount(account);
        return account.getId();
    }

    public void addObserverClient(ObserverObject client) throws ExistingStateException {
        if (observerClients.contains(client))
            throw ExistingStateException.ExistingClient(this);
        observerClients.add(client);
    }
    public void removeObserverClient(ObserverObject client) throws ExistingStateException {
        if (!observerClients.contains(client))
            throw ExistingStateException.NonExistingClient(this);
        observerClients.remove(client);
    }

    @Override
    public void notifyClients() {
        for (ObserverObject client : observerClients) {
            client.update(bankInfo.toString());
        }
    }


    public void notifyAccounts(LocalDate currentDate) {
        for (Account account : accounts) {
            account.evaluateCommission();
            if (currentDate.getDayOfMonth() == validateCommissionDay(account.getCommissionDay(), currentDate))
                account.accrueCommission();
        }
    }

    private static int validateCommissionDay(int commissionDay, LocalDate currentDate) throws CommissionDayException
    {
        if (commissionDay > currentDate.lengthOfMonth())
            throw CommissionDayException.InvalidCommissionDay(commissionDay, currentDate);
        return commissionDay;
    }

    public void setBankInfo(BankInfo bankInfo) {
        this.bankInfo = bankInfo;
        notifyClients();
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }


    public void createTransaction(UUID actorId, UUID recipientId, double value)
    {
        Account actorAccount = getActor(actorId);
        Account recipientAccount = getActor(recipientId);
        var transaction = new TransferTransaction(actorAccount, recipientAccount, new BasicValueAmount(value));
        actorAccount.addTransaction(transaction);
        recipientAccount.addTransaction(transaction);
    }

    public void createTransaction(UUID actorId, Account recipientAccount, double value)
    {
        Account actorAccount = getActor(actorId);
        var transaction = new TransferTransaction(actorAccount, recipientAccount, new BasicValueAmount(value));
        actorAccount.addTransaction(transaction);
        recipientAccount.addTransaction(transaction);
    }

    public void cancelTransaction(UUID actorId, UUID recipientId, double value)
    {
        Account actorAccount = getActor(actorId);
        Account recipientAccount = getActor(recipientId);
        var transaction = new CancellationTransaction(actorAccount, recipientAccount, new BasicValueAmount(value));
        actorAccount.addTransaction(transaction);
        recipientAccount.addTransaction(transaction);
    }

    public void cancelTransaction(UUID actorId, Account recipientAccount, double value)
    {
        Account actorAccount = getActor(actorId);
        var transaction = new CancellationTransaction(actorAccount, recipientAccount, new BasicValueAmount(value));
        actorAccount.addTransaction(transaction);
        recipientAccount.addTransaction(transaction);
    }

    public void createWithdrawTransaction(UUID actorId, double value)
    {
        Account actorAccount = getActor(actorId);
        var transaction = new WithdrawTransaction(actorAccount, new BasicValueAmount(value));
        actorAccount.addTransaction(transaction);
    }

    public void createReplenishmentTransaction(UUID actorId, double value)
    {
        Account actorAccount = getActor(actorId);
        var transaction = new ReplenishmentTransaction(actorAccount, new BasicValueAmount(value));
        actorAccount.addTransaction(transaction);
    }

    private Account getActor(UUID id) throws AlienEntityException {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> AlienEntityException.InvalidAccount(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return id.equals(bank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
