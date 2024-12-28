import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SumOfDigits {
    private final Map<String, Integer> variables = new HashMap<>();

    public void interpret(String code) {
        String[] lines = code.split("\n");
        int i = 0;

        while (i < lines.length) {
            String line = lines[i].trim();

            if (line.startsWith("while ")) {
                int endIndex = findMatchingBrace(lines, i);
                String condition = line.substring("while ".length(), line.indexOf("{")).trim();
                String loopBody = extractLoopBody(lines, i + 1, endIndex);
                executeWhileLoop(condition, loopBody);
                i = endIndex; // Skip the loop body
            } else {
                executeLine(line);
            }

            i++;
        }
    }

    private int findMatchingBrace(String[] lines, int startIndex) {
        int openBraces = 0;

        for (int i = startIndex; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.endsWith("{")) openBraces++;
            if (line.equals("}")) openBraces--;

            if (openBraces == 0) return i;
        }

        throw new RuntimeException("Unmatched braces in while loop.");
    }

    private String extractLoopBody(String[] lines, int startIndex, int endIndex) {
        StringBuilder body = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            body.append(lines[i].trim()).append("\n");
        }
        return body.toString();
    }

    private void executeWhileLoop(String condition, String body) {
        while (evaluateCondition(condition)) {
            interpret(body); // Re-run the loop body
        }
    }

    private boolean evaluateCondition(String condition) {
        if (condition.contains("!=")) {
            String[] parts = condition.split("!=");
            int left = evaluateExpression(parts[0].trim());
            int right = evaluateExpression(parts[1].trim());
            return left != right;
        } else if (condition.contains("==")) {
            String[] parts = condition.split("==");
            int left = evaluateExpression(parts[0].trim());
            int right = evaluateExpression(parts[1].trim());
            return left == right;
        } else if (condition.contains(">")) {
            String[] parts = condition.split(">");
            int left = evaluateExpression(parts[0].trim());
            int right = evaluateExpression(parts[1].trim());
            return left > right;
        } else if (condition.contains("<")) {
            String[] parts = condition.split("<");
            int left = evaluateExpression(parts[0].trim());
            int right = evaluateExpression(parts[1].trim());
            return left < right;
        } else {
            throw new RuntimeException("Invalid condition: " + condition);
        }
    }

    private void executeLine(String line) {
        if (line.startsWith("let ")) {
            handleAssignment(line);
        } else if (line.startsWith("print(")) {
            handlePrint(line);
        } else if (line.contains("=")) { // Handle variable updates
            handleVariableUpdate(line);
        } else if (!line.isEmpty()) {
            throw new RuntimeException("Unknown command: " + line);
        }
    }

    private void handleAssignment(String line) {
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
        if (!line.endsWith(")")) {
            throw new RuntimeException("Malformed print statement: " + line);
        }
        String expression = line.substring("print(".length(), line.length() - 1).trim();
        int value = evaluateExpression(expression);
        System.out.println(value);
    }
    private void handleVariableUpdate(String line) {
        // Example: sum = sum + digit
        String[] parts = line.split("=", 2);
        if (parts.length != 2) {
            throw new RuntimeException("Invalid update statement: " + line);
        }
        String variableName = parts[0].trim();
        String expression = parts[1].trim();

        if (!variables.containsKey(variableName)) {
            throw new RuntimeException("Variable not defined: " + variableName);
        }

        int value = evaluateExpression(expression);
        variables.put(variableName, value);
    }

    private int evaluateExpression(String expression) {
        if (expression.matches("\\d+")) {
            return Integer.parseInt(expression); // Handles numeric literals
        } else if (variables.containsKey(expression)) {
            return variables.get(expression); // Handles variables
        } else if (expression.contains("+")) {
            String[] parts = expression.split("\\+");
            int sum = 0;
            for (String part : parts) {
                sum += evaluateExpression(part.trim());
            }
            return sum;
        } else if (expression.contains("-")) {
            String[] parts = expression.split("-");
            int result = evaluateExpression(parts[0].trim());
            for (int i = 1; i < parts.length; i++) {
                result -= evaluateExpression(parts[i].trim());
            }
            return result;
        } else if (expression.contains("%")) { // Support for modulo operation
            String[] parts = expression.split("%");
            if (parts.length != 2) {
                throw new RuntimeException("Invalid modulo expression: " + expression);
            }
            int left = evaluateExpression(parts[0].trim());
            int right = evaluateExpression(parts[1].trim());
            return left % right;
        } else if (expression.contains("/")) { // Support for division
            String[] parts = expression.split("/");
            if (parts.length != 2) {
                throw new RuntimeException("Invalid division expression: " + expression);
            }
            int left = evaluateExpression(parts[0].trim());
            int right = evaluateExpression(parts[1].trim());
            if (right == 0) {
                throw new RuntimeException("Division by zero in expression: " + expression);
            }
            return left / right;
        } else {
            throw new RuntimeException("Invalid expression: " + expression);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder code = new StringBuilder();
        String line;

        System.out.println("Enter Swift-like code to calculate the sum of digits (press Enter to execute):");

        while (true) {
            line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                // End input if the user presses Enter without typing
                break;
            }
            code.append(line).append("\n");
        }

        SumOfDigits interpreter = new SumOfDigits();
        try {
            interpreter.interpret(code.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

// ციფრების ჯამის გამომთვლელი დასრულებულია
//let n = 456
//let sum = 0
//while n != 0 {
//let digit = n % 10
//sum = sum + digit
//        n = n / 10
//}
//print(sum)
