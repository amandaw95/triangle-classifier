package com.bms.triangleclassifier.domain;

import java.math.BigDecimal;

public final class Triangle {

    private final BigDecimal sideA;
    private final BigDecimal sideB;
    private final BigDecimal sideC;

    public Triangle(BigDecimal sideA, BigDecimal b, BigDecimal c) {
        validateSides(sideA, b, c);
        this.sideA = sideA;
        this.sideB = b;
        this.sideC = c;
    }

    public BigDecimal getSideA() { return sideA; }
    public BigDecimal getSideB() { return sideB; }
    public BigDecimal getSideC() { return sideC; }

    private static void validateSides(BigDecimal a, BigDecimal b, BigDecimal c) {
        if (a.compareTo(BigDecimal.ZERO) <= 0 || b.compareTo(BigDecimal.ZERO) <= 0 || c.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "All sides must be positive. Got: " + a + ", " + b + ", " + c
            );
        }
        if (a.add(b).compareTo(c) <= 0 || a.add(c).compareTo(b) <= 0 || b.add(c).compareTo(a) <= 0) {
            throw new IllegalArgumentException(
                "Sides do not satisfy the triangle inequality."
            );
        }
    }
}
