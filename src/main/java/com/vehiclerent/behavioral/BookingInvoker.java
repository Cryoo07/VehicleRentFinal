package com.vehiclerent.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * Command Pattern: Invoker that executes and tracks booking commands; supports undo and history.
 */
public class BookingInvoker {

    private final List<BookingCommand> history = new ArrayList<>();

    public void executeCommand(BookingCommand command) {
        command.execute();
        history.add(command);
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            BookingCommand last = history.remove(history.size() - 1);
            last.undo();
        }
    }

    public List<BookingCommand> getHistory() {
        return List.copyOf(history);
    }
}
