package com.vehiclerent.behavioral;

/**
 * Strategy Pattern: Concrete Strategy - charges a flat amount per day of delay,
 * regardless of the vehicle's daily rate. Simpler and predictable for cheap
 * rentals where a percentage fine might be too small to be meaningful.
 */
public class FlatLateFee implements LateFeeStrategy {

    /** Fixed dollar amount charged per day late. */
    private static final double FLAT_FEE = 25.0;

    @Override
    public double calculateFine(int daysLate, double dailyRate) {
        if (daysLate <= 0) return 0;
        return FLAT_FEE * daysLate;
    }

    @Override
    public String getDescription() {
        return "$25 flat per day late";
    }
}