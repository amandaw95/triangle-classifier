package com.bms.triangleclassifier.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void constructsValidTriangle() {
        assertDoesNotThrow(() -> new Triangle(new BigDecimal("3"), new BigDecimal("4"), new BigDecimal("5")));
    }

    @Test
    void exposesAllThreeSides() {
        Triangle triangle = new Triangle(new BigDecimal("3"), new BigDecimal("4"), new BigDecimal("5"));
        assertEquals(new BigDecimal("3"), triangle.getSideA());
        assertEquals(new BigDecimal("4"), triangle.getSideB());
        assertEquals(new BigDecimal("5"), triangle.getSideC());
    }

    @Test
    void rejectsZeroSide() {
        assertThrows(IllegalArgumentException.class,
            () -> new Triangle(BigDecimal.ZERO, new BigDecimal("4"), new BigDecimal("5")));
    }

    @Test
    void rejectsNegativeSide() {
        assertThrows(IllegalArgumentException.class,
            () -> new Triangle(new BigDecimal("4"), new BigDecimal("-1"), new BigDecimal("5")));
    }

    @Test
    void rejectsDegenerateTriangle() {
        // a + b = c cannot form a triangle
        assertThrows(IllegalArgumentException.class,
            () -> new Triangle(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3")));
    }

    @Test
    void rejectsTriangleInequalityViolation() {
        // a + b < c cannot form a triangle
        assertThrows(IllegalArgumentException.class,
            () -> new Triangle(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("10")));
    }

    @Test
    void rejectsNullSide() {
        assertThrows(IllegalArgumentException.class,
            () -> new Triangle(null, new BigDecimal("4"), new BigDecimal("5")));
    }
}
