import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SwiftInterpreterGCD {
    private final Map<String, Integer> variables = new HashMap<>();

    public void interpret(String code) {
        String[] lines = code.split("\n");
        int i = 0;
        while (i < lines.length) {
            String line = lines[i].trim();
            if (line.startsWith("while ")) {
                // Handle the while loop with its block
                i = handleWhileLoop(lines, i);
            } else if (line.startsWith("print(")) {
                executeLine(line);
                break; // Stop after print statement
            } else {
                executeLine(line);
                i++;
            }
        }
    }

    private void executeLine(String line) {
        if (line.equals("}")) {
            return; // Ignore closing braces
        }
        if (line.startsWith("let ")) {
            handleAssignment(line);
        } else if (line.startsWith("print(")) {
            handlePrint(line);
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

    private int handleWhileLoop(String[] lines, int startIndex) {
        String condition = lines[startIndex].trim().substring("while ".length()).replace("{", "").trim();
        StringBuilder loopBody = new StringBuilder();
        int i = startIndex + 1;
        int openBraces = 1; // Tracks nested braces

        // Collect loop body until matching braces are closed
        while (i < lines.length && openBraces > 0) {
            String currentLine = lines[i].trim();
            if (currentLine.equals("{")) {
                openBraces++;
            } else if (currentLine.equals("}")) {
                openBraces--;
                if (openBraces == 0) {
                    i++; // Move past the closing brace
                    break;
                }
            } else {
                loopBody.append(currentLine).append("\n");
            }
            i++;
        }

        if (openBraces != 0) {
            throw new RuntimeException("Mismatched braces in while loop");
        }

        // Execute the loop
        while (evaluateExpression(condition) != 0) {
            interpret(loopBody.toString());
        }

        return i; // Return the index after the closing brace
    }

    private int evaluateExpression(String expression) {
        if (expression.matches("\\d+")) {
            return Integer.parseInt(expression);
        } else if (variables.containsKey(expression)) {
            return variables.get(expression);
        } else if (expression.contains("!=")) {
            String[] parts = expression.split("!=");
            int left = evaluateExpression(parts[0].trim());
            int right = evaluateExpression(parts[1].trim());
            return (left != right) ? 1 : 0;
        } else if (expression.contains("%")) {
            String[] parts = expression.split("%");
            int left = evaluateExpression(parts[0].trim());
            int right = evaluateExpression(parts[1].trim());
            return left % right;
        } else {
            throw new RuntimeException("Invalid expression: " + expression);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder code = new StringBuilder();
        String line;

        System.out.println("Enter Swift-like code to calculate GCD of two numbers (type 'print(a)' to execute):");

        while (true) {
            line = scanner.nextLine();
            if (line.trim().startsWith("print(")) {
                // Execute immediately after detecting 'print(a)'
                code.append(line).append("\n");
                break; // Exit after the print statement
            }
            code.append(line).append("\n");
        }

        SwiftInterpreterGCD interpreter = new SwiftInterpreterGCD();
        try {
            interpreter.interpret(code.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
// შესაყვანი კოდის კონსტრუქცია
//let a = 48
//let b = 18
//while b != 0 {
//let temp = b
//let b = a % b
//let a = temp
//}
//print(a)