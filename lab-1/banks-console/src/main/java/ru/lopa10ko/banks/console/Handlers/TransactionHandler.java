package ru.lopa10ko.banks.console.Handlers;

import org.beryx.textio.TextIO;
import ru.lopa10ko.banks.Tools.ValueAmountException;
import ru.lopa10ko.banks.console.Main;
import ru.lopa10ko.banks.console.MasterConsole;
import ru.lopa10ko.banks.console.Options;

import java.util.UUID;

public class TransactionHandler extends Handler {
    public TransactionHandler(Handler successor) {
        this.successor = successor;
    }
    @Override
    void handleRequest(String request, MasterConsole master) {
        TextIO textIO = master.getTextIO();
        Main.clearScreen(textIO.getTextTerminal());
        textIO.getTextTerminal().printf("Creating Transaction:\n");

        String transactionType = textIO.newStringInputReader()
                .withNumberedPossibleValues(Options.getTransactionTypes())
                .read("Choose transaction type: ");
        if (transactionType.equals(Options.getTwoSide()) && master.getAccounts().size() >= 2) {
            UUID firstAccountId = UUID.fromString(textIO.newStringInputReader()
                    .withNumberedPossibleValues(master.getAccounts().stream().map(UUID::toString).toList())
                    .read("Choose from accounts (actor): "));
            UUID secondAccountId = UUID.fromString(textIO.newStringInputReader()
                    .withNumberedPossibleValues(master.getAccounts().stream().filter(uuid -> !uuid.equals(firstAccountId)).map(UUID::toString).toList())
                    .read("Choose from accounts (recipient): "));
            double value = textIO.newDoubleInputReader()
                    .withDefaultValue(0d)
                    .read("Provide transaction value amount: ");
            try {
                master.getCentralBank().createTransaction(firstAccountId, secondAccountId, value);
            } catch (ValueAmountException e) {
                textIO.getTextTerminal().printf(e.getMessage());
            }
        } else if (transactionType.equals(Options.getOneSideWithdraw()) || transactionType.equals(Options.getOneSideReplenish())) {
            var bankUUIDs = master.getBanks().stream().map(b -> b.getId().toString()).toList();
            UUID bankId;
            if (bankUUIDs.size() != 0) {
                bankId = UUID.fromString(textIO.newStringInputReader()
                        .withNumberedPossibleValues(bankUUIDs)
                        .read("Choose from registered banks to search for an account: "));
            } else {
                bankId = null;
            }
            var bank = master.getBanks().stream().filter(b -> b.getId().equals(bankId)).findFirst();
            UUID accountId = UUID.fromString(textIO.newStringInputReader()
                    .withNumberedPossibleValues(bank.get().getAccounts().stream().map(a -> a.getId().toString()).toList())
                    .read(String.format("Choose from accounts in bank %s: ", bankId)));
            double value = textIO.newDoubleInputReader()
                    .withDefaultValue(0d)
                    .read("Provide transaction value amount: ");
            try {
                if (transactionType.equals(Options.getOneSideReplenish()))
                    bank.get().createReplenishmentTransaction(accountId, value);
                else
                    bank.get().createWithdrawTransaction(accountId, value);
            } catch (RuntimeException e) {
                textIO.getTextTerminal().printf(e.getMessage());
            }
            textIO.getTextTerminal().printf(bank.get().getAccounts().stream().filter(a -> a.getId().equals(accountId)).findFirst().get().toString());
        }

        master.getTextIO().newStringInputReader()
                .withMinLength(0)
                .withPropertiesPrefix("custom.neutral")
                .read(Options.getConfirm());
        Main.clearScreen(master.getTextIO().getTextTerminal());
        successor.handleRequest(request, master);
    }
}
