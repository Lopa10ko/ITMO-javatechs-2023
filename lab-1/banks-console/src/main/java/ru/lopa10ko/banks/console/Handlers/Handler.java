package ru.lopa10ko.banks.console.Handlers;

import lombok.Getter;
import lombok.Setter;
import ru.lopa10ko.banks.console.MasterConsole;

import java.util.List;

public abstract class Handler {
    @Getter @Setter
    Handler successor;
    @Getter @Setter
    List<String> options;
    abstract void handleRequest(String request, MasterConsole master);
}