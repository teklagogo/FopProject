package qwert;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SwiftInterpreter {

    // To store variables and their values
    private final Map<String, Integer> variables = new HashMap<>();

    public void interpret(String code) {
        String[] lines = code.split("\n");
        for (String line : lines) {
            executeLine(line.trim());
        }
    }

    private void executeLine(String line) {
        if (line.startsWith("let ")) {
            handleAssignment(line);
        } else if (line.startsWith("print(")) {
            handlePrint(line);
        } else {
            throw new RuntimeException("Unknown command: " + line);
        }
    }

    private void handleAssignment(String line) {
        // Example: let x = 5
        String[] parts = line.split("=", 2);
        if (parts.length != 2 || !parts[0].startsWith("let ")) {
            throw new RuntimeException("Invalid assignment: " + line);
        }
        String variableName = parts[0].replace("let", "").trim();
        String expression = parts[1].trim();
        int value = evaluateExpression(expression);
        variables.put(variableName, value);
    }

    private void handlePrint(String line) {
        // Example: print(z)
        if (!line.endsWith(")")) {
            throw new RuntimeException("Malformed print statement: " + line);
        }
        String expression = line.substring("print(".length(), line.length() - 1).trim();
        int value = evaluateExpression(expression);
        System.out.println(value);
    }

    private int evaluateExpression(String expression) {
        // Replace variable names with their values
        for (Map.Entry<String, Integer> entry : variables.entrySet()) {
            expression = expression.replace(entry.getKey(), String.valueOf(entry.getValue()));
        }

        // Manually evaluate simple arithmetic expressions
        return evaluateSimpleExpression(expression);
    }

    private int evaluateSimpleExpression(String expression) {
        // Handle simple arithmetic with +, -, *, /
        int result = 0;
        char operator = '+';
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                number.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                result = applyOperator(result, Integer.parseInt(number.toString()), operator);
                operator = c;
                number.setLength(0); // Clear the number buffer
            } else if (!Character.isWhitespace(c)) {
                throw new RuntimeException("Invalid character in expression: " + c);
            }
        }

        // Apply the last operation
        if (!number.isEmpty()) {
            result = applyOperator(result, Integer.parseInt(number.toString()), operator);
        }

        return result;
    }

    private int applyOperator(int left, int right, char operator) {
        return switch (operator) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> {
                if (right == 0) throw new ArithmeticException("Division by zero");
                yield left / right;
            }
            default -> throw new RuntimeException("Unknown operator: " + operator);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder code = new StringBuilder();
        String line;

        while (true) {
            line = scanner.nextLine();

            // Stop if the line is empty (User presses Enter without input)
            if (line.trim().isEmpty()) {
                break;
            }

            // Accumulate the code to process
            code.append(line).append("\n");

            // Process each line of input as it comes
            SwiftInterpreter interpreter = new SwiftInterpreter();
            interpreter.interpret(code.toString());

            // Clear the accumulated code after each execution
            code.setLength(0);
        }
    }
}
