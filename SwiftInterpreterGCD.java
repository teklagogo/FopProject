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
    } else if (line.startsWith("while ")) {
        handleWhileLoop(line);
    } else {
        throw new RuntimeException("Unknown command: " + line);
    }
}

private void handleAssignment(String line) {
    // Example: let x = 48
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
    // Example: print(a)
    if (!line.endsWith(")")) {
        throw new RuntimeException("Malformed print statement: " + line);
    }
    String expression = line.substring("print(".length(), line.length() - 1).trim();
    int value = evaluateExpression(expression);
    System.out.println(value);
}

private void handleWhileLoop(String line) {
    // Extract the condition from the while statement
    if (!line.endsWith("{")) {
        throw new RuntimeException("Malformed while statement: " + line);
    }
    String condition = line.substring("while ".length(), line.length() - 1).trim();

    // Simplified loop body for demonstration purposes
    // Hardcoded GCD logic (this should ideally parse a block of code)
    String loopBody = """
        let temp = b
        let b = a % b
        let a = temp
    """;

    while (evaluateExpression(condition) != 0) {
        interpret(loopBody);
    }
}

private int evaluateExpression(String expression) {
    // Handle simple numbers or variable names
    if (expression.matches("\\d+")) {
        return Integer.parseInt(expression); // Plain number
    } else if (variables.containsKey(expression)) {
        return variables.get(expression); // Variable lookup
    } else if (expression.contains("!=")) {
        String[] parts = expression.split("!=");
        int left = evaluateExpression(parts[0].trim());
        int right = evaluateExpression(parts[1].trim());
        return (left != right) ? 1 : 0; // Return 1 for true, 0 for false
    } else {
        throw new RuntimeException("Invalid expression: " + expression);
    }
}

public static <SwiftInterpreterGCD> void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    StringBuilder code = new StringBuilder();
    String line;

    System.out.println("Enter Swift-like code to calculate GCD of two numbers:");

    while (true) {
        line = scanner.nextLine();

        // Stop if the line is empty (User presses Enter without input)
        if (line.trim().isEmpty()) {
            break;
        }

        // Accumulate the code to process
        code.append(line).append("\n");
    }

    // Process the accumulated code
    SwiftInterpreterGCD interpreter = new SwiftInterpreterGCD();
    interpreter.finalize();
}