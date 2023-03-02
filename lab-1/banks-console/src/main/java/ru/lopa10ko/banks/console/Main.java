package ru.lopa10ko.banks.console;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.jline.JLineTextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;
import org.beryx.textio.web.RunnerData;

import java.util.function.BiConsumer;

public class Main implements BiConsumer<TextIO, RunnerData> {
    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();
        new Main().accept(textIO, null);
    }

    @Override
    public void accept(TextIO textIO, RunnerData runnerData) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        var master = MasterConsole.getInstance(textIO);

        String request = textIO.newStringInputReader()
                .withDefaultValue(Options.getExit())
                .withNumberedPossibleValues(Options.getMainHandlerOptions())
                .read(Options.getTitle());

        clearScreen(terminal);
        master.getMainHandler().handleRequest(request, master);
    }

    public static void clearScreen(TextTerminal<?> terminal) {
        if (terminal instanceof JLineTextTerminal) {
            terminal.print("\033[H\033[2J");
        } else if (terminal instanceof SwingTextTerminal) {
            ((SwingTextTerminal) terminal).resetToOffset(0);
        }
    }
}