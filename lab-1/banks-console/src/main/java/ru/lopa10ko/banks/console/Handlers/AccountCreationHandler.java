package ru.lopa10ko.banks.console.Handlers;

import org.beryx.textio.TextIO;
import ru.lopa10ko.banks.Accounts.CreditAccount;
import ru.lopa10ko.banks.Accounts.DebitAccount;
import ru.lopa10ko.banks.Accounts.DepositAccount;
import ru.lopa10ko.banks.console.Main;
import ru.lopa10ko.banks.console.MasterConsole;
import ru.lopa10ko.banks.console.Options;

import java.util.UUID;

public class AccountCreationHandler extends Handler {
    public AccountCreationHandler(Handler successor) {
        this.successor = successor;
    }
    @Override
    void handleRequest(String request, MasterConsole master) {
        TextIO textIO = master.getTextIO();
        Main.clearScreen(textIO.getTextTerminal());
        textIO.getTextTerminal().printf("Creating Account:\n");

        var bankUUIDs = master.getBanks().stream().map(b -> b.getId().toString()).toList();
        var clientUUIDs = master.getClients().stream().map(c -> c.getId().toString()).toList();
        if (bankUUIDs.size() != 0 && clientUUIDs.size() != 0) {
            UUID bankId = UUID.fromString(textIO.newStringInputReader()
                    .withNumberedPossibleValues(bankUUIDs)
                    .read("Choose from registered banks: "));
            UUID clientId = UUID.fromString(textIO.newStringInputReader()
                    .withNumberedPossibleValues(clientUUIDs)
                    .read("Choose from registered clients: "));
            var bank = master.getBanks().stream().filter(b -> b.getId().equals(bankId)).findFirst();
            var client = master.getClients().stream().filter(c -> c.getId().equals(clientId)).findFirst();
            double balance = textIO.newDoubleInputReader()
                    .withDefaultValue(0d)
                    .read("Provide account initial balance: ");
            String accountType = textIO.newStringInputReader()
                    .withNumberedPossibleValues(Options.getAccountTypes())
                    .read("Choose account type: ");
            UUID account = null;
            if (accountType.equals(Options.getCreateCredit())) {
                account = bank.get().createAccount(CreditAccount.class, client.get(), balance);
            } else if (accountType.equals(Options.getCreateDebit())) {
                account = bank.get().createAccount(DebitAccount.class, client.get(), balance);
            } else if (accountType.equals(Options.getCreateDeposit())) {
                account = bank.get().createAccount(DepositAccount.class, client.get(), balance);
            }

            master.getAccounts().add(account);
        }

        master.getTextIO().newStringInputReader()
                .withMinLength(0)
                .withPropertiesPrefix("custom.neutral")
                .read(Options.getConfirm());
        Main.clearScreen(master.getTextIO().getTextTerminal());

        successor.handleRequest(request, master);
    }

}
