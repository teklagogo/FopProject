import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MinimalInterpreter {
    private final Map<String, Integer> variables = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void eval(String code) {
        String[] lines = code.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("let") || line.startsWith("var")) {
                handleVariableDeclaration(line);
            } else if (line.startsWith("print")) {
                handlePrint(line);
            } else if (line.startsWith("input")) {
            }
        }
    }

    private void handleVariableDeclaration(String line) {
        boolean isConstant = line.startsWith("let");
        String varName = parts[0].trim();
        int value = parts.length > 1 ? evaluateExpression(parts[1].trim()) : 0;

        if (isConstant && variables.containsKey(varName)) {
            throw new IllegalStateException("Cannot reassign constant: " + varName);
        }
        variables.put(varName, value);
    }

        System.out.print("Enter value for " + varName + ": ");
    }


        int blockStart = index + 1;

        if (conditionResult) {
            eval(extractBlock(lines, blockStart, blockEnd));
        } else {
            if (blockEnd + 1 < lines.length && lines[blockEnd + 1].trim().startsWith("else")) {
            }
        }

    }


        }

    }

        for (int i = start; i < lines.length; i++) {
            }
    }

    private String extractBlock(String[] lines, int start, int end) {
        StringBuilder block = new StringBuilder();
        for (int i = start; i < end; i++) {
            block.append(lines[i].trim()).append("\n");
        }
        return block.toString();
    }

    private int evaluateExpression(String expression) {
        while (expression.contains("(")) {
        }

        String[] tokens = expression.split("\\s+");

        for (int i = 1; i < tokens.length; i += 2) {
        }
        return result;
    }

    private boolean evaluateCondition(String condition) {
        String[] parts = condition.split("\\s+");

        return switch (operator) {
            case "==" -> left == right;
            case "!=" -> left != right;
            case ">" -> left > right;
            case ">=" -> left >= right;
            case "<" -> left < right;
            case "<=" -> left <= right;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    private int getValue(String token) {
        if (variables.containsKey(token)) {
        try {
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Undefined variable or invalid number: " + token);
        }
    }

    public static void main(String[] args) {
        MinimalInterpreter interpreter = new MinimalInterpreter();
        System.out.println("Enter your code. Press Enter without typing anything to execute.");
        StringBuilder program = new StringBuilder();
        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            String line = inputScanner.nextLine().trim();
            program.append(line).append("\n");
        }
        interpreter.eval(program.toString());
    }
}
