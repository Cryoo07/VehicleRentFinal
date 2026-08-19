package com.vehiclerent.structural;

/**
 * Proxy Pattern: Subject interface for report service.
 */
public interface ReportService {

    String generateRentalHistoryReport();

    String generateRevenueReport();
}
