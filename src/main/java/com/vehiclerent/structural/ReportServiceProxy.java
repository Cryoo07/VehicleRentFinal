package com.vehiclerent.structural;

import com.vehiclerent.security.SecurityContext;
import com.vehiclerent.user.User;
import com.vehiclerent.util.ConsoleOutput;

/**
 * Proxy Pattern: Controls access to sensitive report operations based on user role; restricts admin-only reports without embedding security in the real service.
 */
public class ReportServiceProxy implements ReportService {

    private final ReportService realService;

    public ReportServiceProxy(ReportService realService) {
        this.realService = realService;
    }

    @Override
    public String generateRentalHistoryReport() {
        return executeWithAuth("Rental History Report", realService::generateRentalHistoryReport);
    }

    @Override
    public String generateRevenueReport() {
        return executeWithAuth("Revenue Report", realService::generateRevenueReport);
    }

    private String executeWithAuth(String reportName, java.util.function.Supplier<String> action) {
        User currentUser = SecurityContext.getCurrentUser();
        ConsoleOutput.printHeading("PROXY PATTERN - SECURE REPORT ACCESS");
        if (currentUser == null) {
            ConsoleOutput.printLine("Access Denied: No user logged in.");
            return "Access Denied";
        }
        if (!currentUser.isAdmin()) {
            ConsoleOutput.printLine("Access Denied: " + currentUser.getUsername() + " lacks admin privileges.");
            return "Access Denied";
        }
        ConsoleOutput.printLine("Access Granted for Admin: " + currentUser.getUsername());
        ConsoleOutput.printLine("Generating " + reportName + "...");
        return action.get();
    }
}
