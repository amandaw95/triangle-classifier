package com.bms.triangleclassifier.service;

import com.bms.triangleclassifier.domain.Triangle;
import com.bms.triangleclassifier.domain.TriangleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleClassifierTest {

    private TriangleClassifier classifier;

    @BeforeEach
    void setUp() {
        classifier = new TriangleClassifier();
    }

    @Test
    void classifiesEquilateral() {
        assertEquals(TriangleType.EQUILATERAL, classifier.classify(
            new Triangle(new BigDecimal("5"), new BigDecimal("5"), new BigDecimal("5"))));
    }

    @Test
    void classifiesIsoscelesWhenFirstTwoSidesMatch() {
        assertEquals(TriangleType.ISOSCELES, classifier.classify(
            new Triangle(new BigDecimal("5"), new BigDecimal("5"), new BigDecimal("3"))));
    }

    @Test
    void classifiesIsoscelesWhenLastTwoSidesMatch() {
        assertEquals(TriangleType.ISOSCELES, classifier.classify(
            new Triangle(new BigDecimal("3"), new BigDecimal("5"), new BigDecimal("5"))));
    }

    @Test
    void classifiesIsoscelesWhenFirstAndLastSidesMatch() {
        assertEquals(TriangleType.ISOSCELES, classifier.classify(
            new Triangle(new BigDecimal("5"), new BigDecimal("3"), new BigDecimal("5"))));
    }

    @Test
    void classifiesScalene() {
        assertEquals(TriangleType.SCALENE, classifier.classify(
            new Triangle(new BigDecimal("3"), new BigDecimal("4"), new BigDecimal("5"))));
    }

    @Test
    void classifiesEquilateralWhenSidesHaveDifferentScale() {
        // "3", "3.00", "3.000" are numerically equal — compareTo()==0, but equals() would say false
        assertEquals(TriangleType.EQUILATERAL, classifier.classify(
            new Triangle(new BigDecimal("3"), new BigDecimal("3.00"), new BigDecimal("3.000"))));
    }

    @Test
    void classifiesScaleneWithHighPrecisionSides() {
        // All three values are numerically distinct at decimal precision
        assertEquals(TriangleType.SCALENE, classifier.classify(
            new Triangle(new BigDecimal("3"), new BigDecimal("3.0001"), new BigDecimal("3.01"))));
    }
}
