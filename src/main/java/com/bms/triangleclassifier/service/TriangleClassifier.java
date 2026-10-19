package com.bms.triangleclassifier.service;

import com.bms.triangleclassifier.domain.Triangle;
import com.bms.triangleclassifier.domain.TriangleType;

import java.math.BigDecimal;

public class TriangleClassifier {

    public TriangleType classify(Triangle triangle) {
        BigDecimal sideA = triangle.getSideA();
        BigDecimal sideB = triangle.getSideB();
        BigDecimal sideC = triangle.getSideC();

        if (sideA.compareTo(sideB) == 0 && sideB.compareTo(sideC) == 0) {
            return TriangleType.EQUILATERAL;
        }
        if (sideA.compareTo(sideB) == 0 || sideB.compareTo(sideC) == 0 || sideA.compareTo(sideC) == 0) {
            return TriangleType.ISOSCELES;
        }
        return TriangleType.SCALENE;
    }
}
