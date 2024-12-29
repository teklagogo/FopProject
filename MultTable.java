import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MultTable {
    private final Map<String, Integer> variables = new HashMap<>(); // Variable storage
    private final Map<String, Boolean> isMutable = new HashMap<>(); // Tracks if a variable is mutable

    public void eval(String code) {
        String[] lines = code.split("\n"); // Split code into lines
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            // Handle variable declaration
            if (line.startsWith("let") || line.startsWith("var")) {
                handleVariableDeclaration(line);
            }
            // Handle print statements
            else if (line.startsWith("print")) {
                handlePrint(line);
            }
            // Handle for loop
            else if (line.startsWith("for")) {
                i = handleForLoop(i, lines); // Update the index after processing the loop
            }
            // Handle reassignment
            else if (line.contains("=")) {
                handleReassignment(line);
            }
        }
    }

    private void handleVariableDeclaration(String line) {
        boolean mutable = line.startsWith("var");
        String[] parts = line.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid variable declaration: " + line);
        }
        String varName = parts[0].replace("let", "").replace("var", "").trim();
        int value = Integer.parseInt(parts[1].trim());
        if (variables.containsKey(varName)) {
            throw new IllegalStateException("Variable already declared: " + varName);
        }
        variables.put(varName, value);
        isMutable.put(varName, mutable); // Track if the variable is mutable
    }

    private void handleReassignment(String line) {
        String[] parts = line.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid assignment statement: " + line);
        }
        String varName = parts[0].trim();
        int value = Integer.parseInt(parts[1].trim());

        if (!isMutable.getOrDefault(varName, false)) {
            throw new IllegalStateException("Cannot reassign to variable declared with 'let': " + varName);
        }
        variables.put(varName, value);
    }

    private void handlePrint(String line) {
        String expression = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (expression.contains("*")) {
            String[] parts = expression.split("\\*");
            int left = getValue(parts[0].trim());
            int right = getValue(parts[1].trim());
            System.out.println(left * right);
        } else {
            System.out.println(getValue(expression));
        }
    }

    private int handleForLoop(int currentIndex, String[] lines) {
        String line = lines[currentIndex];
        String rangePart = line.substring(line.indexOf("in") + 2, line.indexOf("{")).trim();
        String[] rangeBounds = rangePart.split("\\.\\.\\.");
        if (rangeBounds.length != 2) {
            throw new IllegalArgumentException("Invalid range in for loop: " + line);
        }
        int start = Integer.parseInt(rangeBounds[0].trim());
        int end = Integer.parseInt(rangeBounds[1].trim());

        String loopVariable = line.substring(line.indexOf("for") + 3, line.indexOf("in")).trim();

        // Find loop body
        StringBuilder loopBody = new StringBuilder();
        currentIndex++; // Move to the next line after 'for'
        while (!lines[currentIndex].trim().equals("}")) {
            loopBody.append(lines[currentIndex]).append("\n");
            currentIndex++;
        }

        // Execute the loop
        for (int i = start; i <= end; i++) {
            variables.put(loopVariable, i); // Set the loop variable
            eval(loopBody.toString()); // Execute the loop body
        }
        return currentIndex; // Return the index after the loop
    }

    private int getValue(String token) {
        if (variables.containsKey(token)) {
            return variables.get(token);
        }
        return Integer.parseInt(token);
    }

    public static void main(String[] args) {
        MultTable interpreter = new MultTable();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your Swift-like program line by line. Double press Enter to finish:");

        // Read multiline input
        StringBuilder program = new StringBuilder();
        boolean previousEmpty = false; // Track if the last line was empty

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                if (previousEmpty) break; // Exit if two consecutive empty lines are detected
                previousEmpty = true;
            } else {
                previousEmpty = false;
                program.append(line).append("\n");
            }
        }

        // Eval the program
        try {
            interpreter.eval(program.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
