import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Sum {

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
        } else if (line.startsWith("for ")) {
            handleForLoop(line);
        } else {
            throw new RuntimeException("Unknown command: " + line);
        }
    }

    private void handleAssignment(String line) {
        // Example: let x = 10
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

    private void handleForLoop(String line) {
        // Example: for i in 1...N {
        if (!line.endsWith("{")) {
            throw new RuntimeException("Malformed for loop: " + line);
        }
        String loopCondition = line.substring("for ".length(), line.length() - 1).trim();
        String[] loopParts = loopCondition.split(" in ");
        if (loopParts.length != 2) {
            throw new RuntimeException("Invalid for loop syntax: " + loopCondition);
        }
        String loopVar = loopParts[0].trim();
        String range = loopParts[1].trim();

        // Evaluate range (e.g., 1...N)
        String[] rangeParts = range.split("\\.\\.\\.");
        if (rangeParts.length != 2) {
            throw new RuntimeException("Invalid range syntax: " + range);
        }
        int start = evaluateExpression(rangeParts[0].trim());
        int end = evaluateExpression(rangeParts[1].trim());

        // Execute the loop body
        String loopBody = getLoopBody();

        // Loop from start to end (inclusive)
        for (int i = start; i <= end; i++) {
            // Set the loop variable
            variables.put(loopVar, i);

            // Execute the loop body for each iteration
            interpret(loopBody); // Re-interpret the loop body for each iteration
        }
    }

    private String getLoopBody() {
        // Here we can hardcode the loop body logic (sum += i)
        return "sum += i"; // You could extend this to dynamically handle more complex loop bodies
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
        if (number.length() > 0) {
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

        // Example: Read Swift-like code from the terminal
        System.out.println("Enter Swift-like code to calculate sum of first N numbers:");

        while (true) {
            line = scanner.nextLine();

            // Stop if the line is empty (User presses Enter without input)
            if (line.trim().isEmpty()) {
                break;
            }

            // Accumulate the code to process
            code.append(line).append("\n");

            // Process the code as it comes
            SwiftInterpreter interpreter = new SwiftInterpreter();
            interpreter.interpret(code.toString());

            // Clear the accumulated code after each execution
            code.setLength(0);
        }
    }
}
