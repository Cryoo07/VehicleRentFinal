package com.vehiclerent.behavioral;

/**
 * Command Pattern: Interface encapsulating booking operations as objects with execute/undo.
 */
public interface BookingCommand {

    void execute();

    void undo();

    String getDescription();
}
