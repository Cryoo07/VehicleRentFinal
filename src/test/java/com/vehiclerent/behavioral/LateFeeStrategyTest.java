package com.vehiclerent.behavioral;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Strategy Pattern: verifies that each concrete LateFeeStrategy computes
 * fines correctly and that interchangeable strategies produce different results.
 */
class LateFeeStrategyTest {

    @Test
    void standardFeeIsPercentageOfDailyRate() {
        LateFeeStrategy standard = new StandardLateFee();
        assertEquals(150.0, standard.calculateFine(3, 100.0)); // 50% of $100 x 3 days
        assertEquals(0.0, standard.calculateFine(0, 100.0));
    }

    @Test
    void flatFeeIgnoresDailyRate() {
        LateFeeStrategy flat = new FlatLateFee();
        assertEquals(50.0, flat.calculateFine(2, 1000.0)); // flat $25 x 2 days
        assertEquals(25.0, flat.calculateFine(1, 1.0));
        assertEquals(0.0, flat.calculateFine(0, 500.0));
    }

    @Test
    void strategiesAreInterchangeable() {
        LateFeeStrategy strategy = new FlatLateFee();
        double flatFine = strategy.calculateFine(2, 100.0);
        strategy = new StandardLateFee();
        double standardFine = strategy.calculateFine(2, 100.0);
        assertNotEquals(flatFine, standardFine);
    }
}