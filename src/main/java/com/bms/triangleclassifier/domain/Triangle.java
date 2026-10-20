package com.bms.triangleclassifier.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Triangle {

    private final BigDecimal sideA;
    private final BigDecimal sideB;
    private final BigDecimal sideC;

    public Triangle(BigDecimal sideA, BigDecimal sideB, BigDecimal sideC) {
        validateSides(sideA, sideB, sideC);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public BigDecimal getSideA() { return sideA; }
    public BigDecimal getSideB() { return sideB; }
    public BigDecimal getSideC() { return sideC; }

    /**
     * Validates the sides of the triangle.
     * Throws IllegalArgumentException if any side is null, non-positive, or if the sides
     * do not satisfy the triangle inequality.
     */
    private static void validateSides(BigDecimal sideA, BigDecimal sideB, BigDecimal sideC) {
        if (Objects.isNull(sideA) || Objects.isNull(sideB) || Objects.isNull(sideC)) {
            throw new IllegalArgumentException("All sides must be non-null.");
        }

        if (sideA.compareTo(BigDecimal.ZERO) <= 0 || sideB.compareTo(BigDecimal.ZERO) <= 0 || sideC.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "All sides must be positive. Got: " + sideA + ", " + sideB + ", " + sideC
            );
        }

        if (sideA.add(sideB).compareTo(sideC) <= 0 || sideA.add(sideC).compareTo(sideB) <= 0 || sideB.add(sideC).compareTo(sideA) <= 0) {
            throw new IllegalArgumentException(
                "Sides do not satisfy the triangle inequality."
            );
        }
    }
}
