package com.bms.triangleclassifier.service;

import com.bms.triangleclassifier.domain.Triangle;
import com.bms.triangleclassifier.domain.TriangleType;

import java.math.BigDecimal;

public class TriangleClassifier {

    public TriangleType classify(Triangle triangle) {
        BigDecimal a = triangle.getSideA();
        BigDecimal b = triangle.getSideB();
        BigDecimal c = triangle.getSideC();

        if (a.compareTo(b) == 0 && b.compareTo(c) == 0) {
            return TriangleType.EQUILATERAL;
        }
        if (a.compareTo(b) == 0 || b.compareTo(c) == 0 || a.compareTo(c) == 0) {
            return TriangleType.ISOSCELES;
        }
        return TriangleType.SCALENE;
    }
}
