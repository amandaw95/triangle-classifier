package com.bms.triangleclassifier.presentation;

import com.bms.triangleclassifier.domain.Triangle;
import com.bms.triangleclassifier.domain.TriangleType;
import com.bms.triangleclassifier.service.TriangleClassifier;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleRunner {

    private final TriangleClassifier classifier;
    private final Scanner scanner;
    private final PrintStream output;

    public ConsoleRunner(TriangleClassifier classifier, Scanner scanner, PrintStream out) {
        this.classifier = classifier;
        this.scanner = scanner;
        this.output = out;
    }

    public void run() {
        String input;
        while (true) {
            output.print("Enter three side lengths (space-separated, or 'exit' to quit): ");

            if (!scanner.hasNextLine()) {
                return; // Exit if no more input
            }

            input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                return;
            }

            classifyFromInput(input);
        }
    }

    private void classifyFromInput(String input) {
        final Triangle triangle;

        try {
            BigDecimal[] sides = parseSides(input);
            triangle = new Triangle(sides[0], sides[1], sides[2]);
        } catch (IllegalArgumentException e) {
            output.println( e.getMessage());
            return;
        }

        TriangleType type = classifier.classify(triangle);
        output.println(type.getDisplayName());
    }

    private BigDecimal[] parseSides(String input) {

        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(
                "Input cannot be empty. Please enter three " +
                        "triangle side lengths separated by spaces."
            );
        }

        String[] parts = input.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                "Please enter exactly three space-separated positive " +
                        "numbers as triangle side lengths."
            );
        }
        BigDecimal[] sides = new BigDecimal[3];
        List<String> invalidInputs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            try {
                sides[i] = new BigDecimal(parts[i]);
            } catch (NumberFormatException e) {
                invalidInputs.add(parts[i]);
            }
        }
        if (!invalidInputs.isEmpty()) {
            throw new IllegalArgumentException(
                "Not a number(s): " + String.join(", ", invalidInputs)
            );
        }
        return sides;
    }
}
