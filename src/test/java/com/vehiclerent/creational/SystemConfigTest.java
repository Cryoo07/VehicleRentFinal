package com.vehiclerent.creational;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemConfigTest {

    @Test
    void singletonReturnsSameInstance() {
        SystemConfig first = SystemConfig.getInstance();
        SystemConfig second = SystemConfig.getInstance();
        assertSame(first, second);
    }

    @Test
    void configurationValuesAreSet() {
        SystemConfig config = SystemConfig.getInstance();
        assertEquals("VehicleRentDB", config.getDatabaseName());
        assertEquals("1.0", config.getSystemVersion());
        assertEquals("org.postgresql.Driver", config.getDatabaseDriver());
    }
}
