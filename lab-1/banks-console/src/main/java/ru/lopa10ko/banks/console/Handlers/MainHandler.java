package ru.lopa10ko.banks.console.Handlers;

import ru.lopa10ko.banks.Banks.CentralBank;
import ru.lopa10ko.banks.console.Main;
import ru.lopa10ko.banks.console.MasterConsole;
import ru.lopa10ko.banks.console.Options;

import java.util.List;

public class MainHandler extends Handler {
    public MainHandler(Handler successor, List<String> options) {
        this.options = options;
        this.successor = successor;
    }

    @Override
    public void handleRequest(String request, MasterConsole master) {
        if (request.equals(Options.getCreateCentralBank())) {
            master.setCentralBank((CentralBank.getInstance()));
            Main.clearScreen(master.getTextIO().getTextTerminal());
            master.getTextIO().newStringInputReader()
                    .withMinLength(0)
                    .withPropertiesPrefix("custom.neutral")
                    .read("Central Bank successfully created" + Options.getConfirm());
            Main.clearScreen(master.getTextIO().getTextTerminal());
            successor.handleRequest(Options.getCreateCentralBank(), master);
        }
        if (request.equals(Options.getExit()))
        {
            master.getTextIO().newStringInputReader()
                    .withMinLength(0)
                    .withPropertiesPrefix("custom.neutral")
                    .read(Options.getConfirm());
            master.getTextIO().getTextTerminal().dispose();
        }
    }
}