package com.vehiclerent.behavioral;

/**
 * Strategy Pattern: Defines the family of algorithms for calculating late-return fines.
 * New fine policies can be added without touching the rental system, as each is
 * encapsulated behind this common interface.
 */
public interface LateFeeStrategy {

    /**
     * Calculates the fine for returning a rental vehicle late.
     *
     * @param daysLate  number of days the return was delayed
     * @param dailyRate base daily rental price of the vehicle (used by percentage-based fines)
     * @return the total fine amount in dollars
     */
    double calculateFine(int daysLate, double dailyRate);

    /**
     * Human-readable description shown to the user (used in the return flow).
     */
    String getDescription();
}