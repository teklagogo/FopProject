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
                handleInput(line);
            } else if (line.startsWith("if")) {
                i = handleIf(lines, i);
            } else if (line.startsWith("while")) {
                i = handleWhile(lines, i);
            }
        }
    }

    private void handleVariableDeclaration(String line) {
        boolean isConstant = line.startsWith("let");
        line = line.replaceFirst("var", "").replaceFirst("let", "").trim();
        String[] parts = line.split("=");

        String varName = parts[0].trim();
        int value = parts.length > 1 ? evaluateExpression(parts[1].trim()) : 0;

        if (isConstant && variables.containsKey(varName)) {
            throw new IllegalStateException("Cannot reassign constant: " + varName);
        }
        variables.put(varName, value);
    }

    private void handlePrint(String line) {
        String expression = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        int result = evaluateExpression(expression);
        System.out.println(result);
    }

    private void handleInput(String line) {
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        System.out.print("Enter value for " + varName + ": ");
        int value = scanner.nextInt();
        variables.put(varName, value);
    }

    private int handleIf(String[] lines, int index) {
        String conditionLine = lines[index].trim();
        String condition = conditionLine.substring(conditionLine.indexOf('(') + 1, conditionLine.indexOf(')')).trim();
        boolean conditionResult = evaluateCondition(condition);

        int blockStart = index + 1;
        int blockEnd = findMatchingEnd(lines, blockStart);

        if (conditionResult) {
            eval(extractBlock(lines, blockStart, blockEnd));
        } else {
            // Look ahead to handle the else block
            if (blockEnd + 1 < lines.length && lines[blockEnd + 1].trim().startsWith("else")) {
                int elseBlockStart = blockEnd + 2;
                int elseBlockEnd = findMatchingEnd(lines, elseBlockStart);
                eval(extractBlock(lines, elseBlockStart, elseBlockEnd));
                return elseBlockEnd;
            }
        }

        return blockEnd;
    }

    private int handleWhile(String[] lines, int index) {
        String conditionLine = lines[index].trim();
        String condition = conditionLine.substring(conditionLine.indexOf('(') + 1, conditionLine.indexOf(')')).trim();

        int blockStart = index + 1;
        int blockEnd = findMatchingEnd(lines, blockStart);

        while (evaluateCondition(condition)) {
            eval(extractBlock(lines, blockStart, blockEnd));
        }

        return blockEnd;
    }

    private int findMatchingEnd(String[] lines, int start) {
        int openBraces = 1;
        for (int i = start; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.contains("{")) openBraces++;
            if (line.contains("}")) openBraces--;
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
        // Evaluate parentheses first
        while (expression.contains("(")) {
            int startIdx = expression.lastIndexOf("(");
            int endIdx = expression.indexOf(")", startIdx);
            String subExpr = expression.substring(startIdx + 1, endIdx);
            int subResult = evaluateExpression(subExpr); // Recursively evaluate the sub-expression
            expression = expression.substring(0, startIdx) + subResult + expression.substring(endIdx + 1);
        }

        String[] tokens = expression.split("\\s+");
        int result = getValue(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            int operand = getValue(tokens[i + 1]);

            switch (operator) {
                case "+" -> result += operand;
                case "-" -> result -= operand;
                case "*" -> result *= operand;
                case "/" -> result /= operand;
                case "%" -> result %= operand;
            }
        }

        return result;
    }

    private boolean evaluateCondition(String condition) {
        String[] parts = condition.split("\\s+");
        int left = getValue(parts[0]);
        int right = getValue(parts[2]);
        String operator = parts[1];

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
            return variables.get(token);
        } else {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Undefined variable or invalid number: " + token);
            }
        }
    }

    public static void main(String[] args) {
        MinimalInterpreter interpreter = new MinimalInterpreter();
        System.out.println("Enter your code. Press Enter without typing anything to execute.");
        StringBuilder program = new StringBuilder();
        Scanner inputScanner = new Scanner(System.in);

        // Read multi-line input until an empty line is encountered
        while (true) {
            String line = inputScanner.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }
            program.append(line).append("\n");
        }

        // Execute the code
        interpreter.eval(program.toString());
    }
}
