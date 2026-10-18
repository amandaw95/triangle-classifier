# Triangle Classifier

A small Java console application that reads three side lengths and classifies the triangle as **Equilateral**, **Isosceles**, or **Scalene**.

---

## Running the application

```bash
mvn package -DskipTests
java -jar target/triangle-classifier-1.0-SNAPSHOT.jar
```

At the prompt, enter three side lengths separated by spaces, then press Enter. Type `exit` to quit.

```
Enter three side lengths (space-separated, or 'exit' to quit): 3 3 3
Equilateral
Enter three side lengths (space-separated, or 'exit' to quit): 3 4 5
Scalene
Enter three side lengths (space-separated, or 'exit' to quit): exit
```

---

## Running the tests

```bash
mvn test
```

33 tests across three test classes.

---

## Project structure

```
src/
├── main/java/com/bms/triangleclassifier/
│   ├── Main.java                   # Entry point — wires all layers, no logic
│   ├── domain/
│   │   ├── Triangle.java           # Immutable value object; validates sides on construction
│   │   └── TriangleType.java       # Enum: EQUILATERAL, ISOSCELES, SCALENE
│   ├── service/
│   │   └── TriangleClassifier.java # Pure classification logic — no I/O
│   └── presentation/
│       └── ConsoleRunner.java      # All I/O: prompting, parsing, error messages
└── test/java/com/bms/triangleclassifier/
    ├── domain/
    │   └── TriangleTest.java
    ├── service/
    │   └── TriangleClassifierTest.java
    └── presentation/
        └── ConsoleRunnerTest.java
```