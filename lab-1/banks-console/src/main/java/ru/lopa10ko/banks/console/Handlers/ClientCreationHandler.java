package ru.lopa10ko.banks.console.Handlers;

import org.beryx.textio.TextIO;
import ru.lopa10ko.banks.Clients.Client;
import ru.lopa10ko.banks.console.Main;
import ru.lopa10ko.banks.console.MasterConsole;
import ru.lopa10ko.banks.console.Options;

public class ClientCreationHandler extends Handler {
    public ClientCreationHandler(Handler successor) {
        this.successor = successor;
    }

    @Override
    void handleRequest(String request, MasterConsole master) {
        TextIO textIO = master.getTextIO();
        Main.clearScreen(textIO.getTextTerminal());
        textIO.getTextTerminal().printf("Creating Bank:\n");

        String name = textIO.newStringInputReader()
                .withDefaultValue("testName")
                .read("Provide client name: ");
        String lastName = textIO.newStringInputReader()
                .withDefaultValue("testLastName")
                .read("Provide client last name: ");
        String address = textIO.newStringInputReader()
                .withDefaultValue(" ")
                .read("Provide client address: ");
        String passport = textIO.newStringInputReader()
                .withDefaultValue(" ")
                .read("Provide client passport: ");

        var client = Client.getBuilder()
                .withName(name)
                .withLastName(lastName)
                .withAddress(address)
                .withPassport(passport)
                .build();
        master.getClients().add(client);
        master.getTextIO().newStringInputReader()
                .withMinLength(0)
                .withPropertiesPrefix("custom.neutral")
                .read("Client successfully created\n" + client + Options.getConfirm());
        Main.clearScreen(master.getTextIO().getTextTerminal());

        successor.handleRequest(request, master);
    }
}
