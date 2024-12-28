import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Factorial {
    private final Map<String, Integer> variables = new HashMap<>(); // Variable storage

    public void eval(String code) {
        String[] lines = code.split(";"); // Split by statement terminator
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Handle variable assignment
            if (line.contains("=")) {
                handleAssignment(line);
            }
            // Handle factorial calculation
            else if (line.startsWith("factorial")) {
                handleFactorial(line);
            }
            // Handle print statements
            else if (line.startsWith("print")) {
                handlePrint(line);
            }
        }
    }

    private void handleAssignment(String line) {
        String[] parts = line.split("=");
        String varName = parts[0].trim();
        int value = Integer.parseInt(parts[1].trim());
        variables.put(varName, value);
    }

    private void handleFactorial(String line) {
        // Syntax: factorial(varName)
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (variables.containsKey(varName)) {
            variables.compute(varName, (k, value) -> computeFactorial(value));
        } else {
            System.err.println("Error: Variable " + varName + " is not defined.");
        }
    }

    private int computeFactorial(int n) {
        if (n == 0) return 1;
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private void handlePrint(String line) {
        // Syntax: print(varName)
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (variables.containsKey(varName)) {
            System.out.println(variables.get(varName));
        } else {
            System.err.println("Error: Variable " + varName + " is not defined.");
        }
    }

    public static void main(String[] args) {
        Factorial interpreter = new Factorial();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Swift-like Interpreter!");
        System.out.println("Enter your program line by line. Type 'RUN' on a new line to execute:");

        StringBuilder program = new StringBuilder();
        while (true) {
            String line = scanner.nextLine().trim(); // No custom prompt

            if (line.equalsIgnoreCase("RUN")) {
                break; // Stop reading input and execute the program
            }

            program.append(line).append(";");
        }

        // Execute the program
        interpreter.eval(program.toString());

        System.out.println("Program execution finished.");
        scanner.close();
    }
}
