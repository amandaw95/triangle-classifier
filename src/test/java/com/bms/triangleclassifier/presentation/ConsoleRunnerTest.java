package com.bms.triangleclassifier.presentation;

import com.bms.triangleclassifier.domain.TriangleType;
import com.bms.triangleclassifier.service.TriangleClassifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConsoleRunnerTest {

    private TriangleClassifier mockClassifier;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    @BeforeEach
    void setUp() {
        mockClassifier = mock(TriangleClassifier.class);
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    private ConsoleRunner runnerFor(String input) {
        return new ConsoleRunner(mockClassifier, new Scanner(new StringReader(input)), printStream);
    }

    private String capturedOutput() {
        return outputStream.toString();
    }

    // -------------------------------------------------------------------------
    // Exit condition tests
    // -------------------------------------------------------------------------

    @Test
    void exitsOnLowercaseExitCommand() {
        runnerFor("exit\\n3 3 3").run();
        verifyNoInteractions(mockClassifier);
    }

    @Test
    void exitsOnUppercaseExitCommand() {
        runnerFor("EXIT\\n3 3 3").run();
        verifyNoInteractions(mockClassifier);
    }

    @Test
    void exitsOnMixedCaseExitCommand() {
        runnerFor("Exit\\n3 3 3").run();
        verifyNoInteractions(mockClassifier);
    }

    @Test
    void exitsOnExitCommandWithSurroundingWhitespace() {
        // trim() is applied before equalsIgnoreCase — "  exit  " must be treated as "exit"
        runnerFor("  exit  \\n3 3 3").run();
        verifyNoInteractions(mockClassifier);
    }

    @Test
    void exitsWhenInputStreamEnds() {
        runnerFor("").run();
        verifyNoInteractions(mockClassifier);
    }

    // -------------------------------------------------------------------------
    // Classification output tests
    // -------------------------------------------------------------------------

    @Test
    void outputsEquilateralForEqualSides() {
        when(mockClassifier.classify(any())).thenReturn(TriangleType.EQUILATERAL);
        runnerFor("3 3 3").run();
        verify(mockClassifier, times(1)).classify(any());
        assertTrue(capturedOutput().contains("Equilateral"));
    }

    @Test
    void outputsIsoscelesForTwoEqualSides() {
        when(mockClassifier.classify(any())).thenReturn(TriangleType.ISOSCELES);
        runnerFor("3 3 4").run();
        verify(mockClassifier, times(1)).classify(any());
        assertTrue(capturedOutput().contains("Isosceles"));
    }

    @Test
    void outputsScaleneForAllDifferentSides() {
        when(mockClassifier.classify(any())).thenReturn(TriangleType.SCALENE);
        runnerFor("3 4 5").run();
        verify(mockClassifier, times(1)).classify(any());
        assertTrue(capturedOutput().contains("Scalene"));
    }

    @Test
    void classifiesMultipleInputsSequentially() {
        when(mockClassifier.classify(any()))
            .thenReturn(TriangleType.EQUILATERAL)
            .thenReturn(TriangleType.SCALENE);
        runnerFor("3 3 3\n3 4 5").run();
        verify(mockClassifier, times(2)).classify(any());
        String output = capturedOutput();
        int equilateralIndex = output.indexOf("Equilateral");
        int scaleneIndex = output.indexOf("Scalene");

        assertTrue(equilateralIndex >= 0, "Expected Equilateral in output");
        assertTrue(scaleneIndex > equilateralIndex,
                "Expected Scalene after Equilateral");
    }

    @Test
    void handlesExtraWhitespaceBetweenNumbers() {
        // split("\\s+") handles multiple spaces between tokens
        when(mockClassifier.classify(any())).thenReturn(TriangleType.EQUILATERAL);
        runnerFor("3   3   3").run();
        verify(mockClassifier, times(1)).classify(any());
        assertTrue(capturedOutput().contains("Equilateral"));
    }

    // -------------------------------------------------------------------------
    // Parsing error handling tests
    // -------------------------------------------------------------------------

    @Test
    void outputsErrorForEmptyInput() {
        runnerFor("\n").run();
        verifyNoInteractions(mockClassifier);
        assertTrue(capturedOutput().contains("Input cannot be empty. Please enter three " +
                "triangle side lengths separated by spaces."));
    }

    @Test
    void outputsErrorForBlankWhitespaceInput() {
        runnerFor("   ").run();
        verifyNoInteractions(mockClassifier);
        assertTrue(capturedOutput().contains("Input cannot be empty. Please enter three " +
                "triangle side lengths separated by spaces."));
    }

    @Test
    void outputsErrorForSingleSide() {
        runnerFor("3").run();
        verifyNoInteractions(mockClassifier);
        assertTrue(capturedOutput().contains("Please enter exactly three space-separated positive " +
                "numbers as triangle side lengths."));
    }

    @Test
    void outputsErrorForTwoSides() {
        runnerFor("3 4").run();
        verifyNoInteractions(mockClassifier);
        assertTrue(capturedOutput().contains("Please enter exactly three space-separated positive " +
                "numbers as triangle side lengths."));
    }

    @Test
    void outputsErrorForFourSides() {
        runnerFor("3 4 5 6").run();
        verifyNoInteractions(mockClassifier);
        assertTrue(capturedOutput().contains("Please enter exactly three space-separated positive " +
                "numbers as triangle side lengths."));
    }

    @Test
    void outputsErrorForAllNonNumericInput() {
        runnerFor("a b c").run();
        verifyNoInteractions(mockClassifier);
        String output = capturedOutput();
        assertTrue(output.contains("Not a number(s):"));
        assertTrue(output.contains("a"));
        assertTrue(output.contains("b"));
        assertTrue(output.contains("c"));
    }

    @Test
    void outputsErrorForPartialNonNumericInput() {
        runnerFor("3 abc 5").run();
        verifyNoInteractions(mockClassifier);
        String output = capturedOutput();
        assertTrue(output.contains("Not a number(s):"));
        assertTrue(output.contains("abc"));
    }

    // -------------------------------------------------------------------------
    // Triangle domain validation error tests
    // -------------------------------------------------------------------------

    @Test
    void outputsErrorForZeroSide() {
        runnerFor("0 3 4").run();
        verifyNoInteractions(mockClassifier);
        assertTrue(capturedOutput().contains("All sides must be positive"));
    }

    @Test
    void outputsErrorForNegativeSide() {
        runnerFor("-1 3 4").run();
        verifyNoInteractions(mockClassifier);
        assertTrue(capturedOutput().contains("All sides must be positive"));
    }

    @Test
    void outputsErrorForInvalidTriangle() {
        // 1 + 2 = 3: degenerate — Triangle constructor throws before classify() is reached
        runnerFor("1 2 3").run();
        verifyNoInteractions(mockClassifier);
        assertTrue(capturedOutput().contains("Sides do not satisfy the triangle inequality."));
    }
}

