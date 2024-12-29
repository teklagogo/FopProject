import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Factorial {
    private final Map<String, Integer> variables = new HashMap<>(); // To store variables

    // Evaluates the Swift-like code
    public void eval(String code) {
        String[] lines = code.split("\n"); // Split by newlines to process each line separately
        for (String line : lines) {
            line = line.trim(); // Trim the line to remove extra spaces
            if (line.isEmpty()) continue; // Skip empty lines

            // Handle variable assignment (let num = 6)
            if (line.contains("=")) {
                handleAssignment(line);
            }
            // Handle factorial calculation (factorial(num))
            else if (line.startsWith("factorial")) {
                handleFactorial(line);
            }
            // Handle print statements (print(num))
            else if (line.startsWith("print")) {
                handlePrint(line);
            } else {
                System.err.println("Error: Unknown command - " + line);
            }
        }
    }

    // Handle variable assignment (let num = 6)
    private void handleAssignment(String line) {
        String[] parts = line.split("=");
        String varName = parts[0].trim().replace("let", "").trim(); // Remove "let" and trim spaces
        int value = Integer.parseInt(parts[1].trim()); // Parse the integer value
        variables.put(varName, value); // Store the value in the variables map
    }

    // Handle factorial calculation (factorial(num))
    private void handleFactorial(String line) {
        // Extract the variable name inside factorial()
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (variables.containsKey(varName)) {
            int value = variables.get(varName); // Get the value of the variable
            int factorialResult = computeFactorial(value); // Calculate factorial
            variables.put(varName, factorialResult); // Update the variable with factorial result
        } else {
            System.err.println("Error: Variable " + varName + " is not defined.");
        }
    }

    // Calculate the factorial of a number
    private int computeFactorial(int n) {
        if (n == 0) return 1;
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i; // Multiply each number to get the factorial
        }
        return result;
    }

    // Handle print statements (print(num))
    private void handlePrint(String line) {
        // Extract the variable name inside print()
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (variables.containsKey(varName)) {
            System.out.println(variables.get(varName)); // Print the value of the variable
        } else {
            System.err.println("Error: Variable " + varName + " is not defined.");
        }
    }

    public static void main(String[] args) {
        Factorial interpreter = new Factorial();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Swift-like Interpreter!");
        System.out.println("Enter your program line by line. Press ENTER on an empty line to execute:");

        // Use StringBuilder to gather all the input lines
        StringBuilder program = new StringBuilder();
        String line;

        // Collect lines until the user presses Enter on an empty line
        while (!(line = scanner.nextLine()).isEmpty()) {
            program.append(line).append("\n"); // Append each line with a newline
        }

        // Execute the program
        interpreter.eval(program.toString());

        System.out.println("Program execution finished.");
        scanner.close();
    }
}


// შესაყვანი კოდი
// let num = 5
//factorial(num)
//print(num)

