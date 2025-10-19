package com.instant.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DoubleHelperTest {

    @Test
    void shouldReturnNullWhenInputIsNull() {
        assertNull(DoubleHelper.twoDigits(null));
    }

    @Test
    void shouldRoundToTwoDigits() {
        assertEquals(1.0, DoubleHelper.twoDigits(1.0));
        assertEquals(3.14, DoubleHelper.twoDigits(3.14159));
    }
}