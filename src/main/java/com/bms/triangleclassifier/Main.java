package com.bms.triangleclassifier;

import com.bms.triangleclassifier.presentation.ConsoleRunner;
import com.bms.triangleclassifier.service.TriangleClassifier;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new ConsoleRunner(new TriangleClassifier(), new Scanner(System.in), System.out).run();
    }
}
