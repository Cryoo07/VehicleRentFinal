package com.vehiclerent.behavioral;

/**
 * Strategy Pattern - Concrete Strategy: charges 50% of the vehicle's daily rate
 * for every day the return is delayed. More expensive vehicles therefore incur
 * larger fines, which is a fair model for high-value rentals.
 */
public class StandardLateFee implements LateFeeStrategy {

    /** Percentage of the daily rate charged per day late. */
    private static final double RATE_PERCENT = 0.5;

    @Override
    public double calculateFine(int daysLate, double dailyRate) {
        if (daysLate <= 0) return 0;
        return dailyRate * RATE_PERCENT * daysLate;
    }

    @Override
    public String getDescription() {
        return "50% of daily rate per day late";
    }
}