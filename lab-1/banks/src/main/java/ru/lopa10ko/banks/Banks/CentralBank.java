package ru.lopa10ko.banks.Banks;

import lombok.Getter;
import ru.lopa10ko.banks.Accounts.Account;
import ru.lopa10ko.banks.Entities.BankInfo;
import ru.lopa10ko.banks.TimeManager.TimeMachine;
import ru.lopa10ko.banks.Tools.AlienEntityException;
import ru.lopa10ko.banks.Tools.ExistingStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CentralBank {
    @Getter
    private UUID id;
    @Getter
    private TimeMachine timeMachine;
    private static volatile CentralBank centralBankInstance;
    private final List<Bank> banks;

    private CentralBank(UUID id, TimeMachine timeMachine) {
        this.id = id;
        this.timeMachine = timeMachine;
        banks = new ArrayList<>();
    }

    public static CentralBank getInstance() {
        CentralBank localInstance = centralBankInstance;
        if (localInstance == null) {
            synchronized (TimeMachine.class) {
                localInstance = centralBankInstance;
                if (localInstance == null) {
                    centralBankInstance = localInstance = new CentralBank(UUID.randomUUID(), TimeMachine.getInstance());
                }
            }
        }
        return localInstance;
    }

    public Bank createBank(String bankName, BankInfo bankInfo) throws ExistingStateException {
        Bank bank = new Bank(bankName, bankInfo);
        if (banks.contains(bank))
            throw ExistingStateException.ExistingBank(bank);
        timeMachine.addObserverBank(bank);
        banks.add(bank);
        return bank;
    }

    public void setBankInfo(Bank bank, BankInfo bankInfo) {
        bank.setBankInfo(bankInfo);
    }

    public ArrayList<Bank> getBanks() {
        return new ArrayList<>(banks);
    }

    public void createTransaction(UUID actorId, UUID recipientId, double value)
    {
        Bank actorBank = getActorBank(actorId);
        Bank recipientBank = getActorBank(recipientId);
        if (actorBank.equals(recipientBank))
            actorBank.createTransaction(actorId, recipientId, value);
        else
            actorBank.createTransaction(actorId, getActor(recipientId), value);
    }

    public void cancelTransaction(UUID actorId, UUID recipientId, double value)
    {
        Bank actorBank = getActorBank(actorId);
        Bank recipientBank = getActorBank(recipientId);
        if (actorBank.equals(recipientBank))
            actorBank.cancelTransaction(actorId, recipientId, value);
        else
            actorBank.cancelTransaction(actorId, getActor(recipientId), value);
    }

    private Account getActor(UUID accountId) throws AlienEntityException {
        return banks.stream()
                .flatMap(bank -> bank.getAccounts().stream())
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> AlienEntityException.InvalidAccount(accountId));
    }
    private Bank getActorBank(UUID accountId) throws AlienEntityException {
        return banks.stream()
                .filter(bank -> bank.getAccounts().stream().anyMatch(account -> account.getId().equals(accountId)))
                .findFirst()
                .orElseThrow(() -> AlienEntityException.InvalidBank(accountId));
    }
}
