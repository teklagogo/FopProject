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
                System.out.println(evaluateExpression(getContent(line)));
            } else if (line.startsWith("input")) {
                handleInput(getContent(line));
            } else if (line.startsWith("if") || line.startsWith("while")) {
                i = handleConditionalBlock(lines, i, line.startsWith("while"));
            }
        }
    }

    private void handleVariableDeclaration(String line) {
        boolean isConstant = line.startsWith("let");
        String[] parts = line.replaceFirst("let|var", "").trim().split("=");
        String varName = parts[0].trim();
        int value = parts.length > 1 ? evaluateExpression(parts[1].trim()) : 0;

        if (isConstant && variables.containsKey(varName)) {
            throw new IllegalStateException("Cannot reassign constant: " + varName);
        }
        variables.put(varName, value);
    }

    private void handleInput(String varName) {
        System.out.print("Enter value for " + varName + ": ");
        variables.put(varName, scanner.nextInt());
    }

    // Unified If-Else and While Handling
    private int handleConditionalBlock(String[] lines, int index, boolean isWhile) {
        String condition = getContent(lines[index]);
        int blockStart = index + 1;
        int blockEnd = findBlockEnd(lines, blockStart);

        if (isWhile) {
            // Recheck condition each time after evaluating the block
            while (evaluateCondition(condition)) {
                eval(extractBlock(lines, blockStart, blockEnd));
                // Re-evaluate the condition after executing the block
                condition = getContent(lines[index]); // Re-fetch the condition
            }
            return blockEnd;
        }

        // If-Else Handling
        boolean conditionResult = evaluateCondition(condition);
        if (conditionResult) {
            eval(extractBlock(lines, blockStart, blockEnd));
        } else {
            // Check for else block
            if (blockEnd + 1 < lines.length && lines[blockEnd + 1].trim().startsWith("else")) {
                int elseStart = blockEnd + 2;
                int elseEnd = findBlockEnd(lines, elseStart);
                eval(extractBlock(lines, elseStart, elseEnd));
                return elseEnd;
            }
        }
        return blockEnd;
    }

    private String getContent(String line) {
        return line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).trim();
    }

    private int findBlockEnd(String[] lines, int start) {
        int openBraces = 1;
        for (int i = start; i < lines.length; i++) {
            if (lines[i].contains("{")) openBraces++;
            if (lines[i].contains("}")) openBraces--;
            if (openBraces == 0) return i;
        }
        throw new IllegalArgumentException("Unmatched braces in code block.");
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
            int openIdx = expression.lastIndexOf("(");
            int closeIdx = expression.indexOf(")", openIdx);
            String subExpr = expression.substring(openIdx + 1, closeIdx);
            int subResult = evaluateExpression(subExpr);
            expression = expression.substring(0, openIdx) + subResult + expression.substring(closeIdx + 1);
        }

        String[] tokens = expression.split("\\s+");
        int result = getValue(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            int operand = getValue(tokens[i + 1]);
            result = switch (tokens[i]) {
                case "+" -> result + operand;
                case "-" -> result - operand;
                case "*" -> result * operand;
                case "/" -> result / operand;
                case "%" -> result % operand;
                default -> throw new IllegalArgumentException("Unknown operator: " + tokens[i]);
            };
        }
        return result;
    }

    private boolean evaluateCondition(String condition) {
        String[] parts = condition.split("\\s+");
        int left = getValue(parts[0]);
        int right = getValue(parts[2]);
        return switch (parts[1]) {
            case "==" -> left == right;
            case "!=" -> left != right;
            case ">" -> left > right;
            case ">=" -> left >= right;
            case "<" -> left < right;
            case "<=" -> left <= right;
            default -> throw new IllegalArgumentException("Unknown operator: " + parts[1]);
        };
    }

    private int getValue(String token) {
        if (variables.containsKey(token)) {
            return variables.get(token);
        }
        try {
            return Integer.parseInt(token);
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
            if (line.isEmpty()) break;
            program.append(line).append("\n");
        }
        interpreter.eval(program.toString());
    }
}