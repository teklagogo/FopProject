import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReverseNumber {
    private final Map<String, String> variables = new HashMap<>(); // Use String to handle leading zeros

    // Evaluates the Swift-like code
    public void eval(String code) {
        String[] lines = code.split("\n"); // Split by newlines to process each line
        for (String line : lines) {
            line = line.trim(); // Trim the line to remove extra spaces
            if (line.isEmpty()) continue; // Skip empty lines

            // Handle variable assignment (let num = "123")
            if (line.contains("=")) {
                handleAssignment(line);
            }
            // Handle reverse calculation (reverse(num))
            else if (line.startsWith("reverse")) {
                handleReverse(line);
            }
            // Handle print statements (print(num))
            else if (line.startsWith("print")) {
                handlePrint(line);
            } else {
                System.err.println("Error: Unknown command - " + line);
            }
        }
    }

    // Handle variable assignment (let num = "123")
    private void handleAssignment(String line) {
        String[] parts = line.split("=");
        String varName = parts[0].trim().replace("let", "").trim(); // Remove "let" and trim spaces
        String value = parts[1].trim(); // Treat the value as a String
        variables.put(varName, value); // Store the value in variables map
    }

    // Handle reverse calculation (reverse(num))
    private void handleReverse(String line) {
        // Extract the variable name inside reverse()
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (variables.containsKey(varName)) {
            String value = variables.get(varName); // Get the value of the variable
            String reversed = reverseNumber(value); // Reverse the number (as a string)
            variables.put(varName, reversed); // Update the variable with reversed value
        } else {
            System.err.println("Error: Variable " + varName + " is not defined.");
        }
    }

    // Reverse the digits of a number (as a string)
    private String reverseNumber(String n) {
        StringBuilder reversed = new StringBuilder();
        for (int i = n.length() - 1; i >= 0; i--) {
            reversed.append(n.charAt(i)); // Reverse the string
        }
        return reversed.toString();
    }

    // Handle print statements (print(num))
    private void handlePrint(String line) {
        // Extract the variable name inside print()
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (variables.containsKey(varName)) {
            System.out.println(variables.get(varName)); // Print the variable value (as string)
        } else {
            System.err.println("Error: Variable " + varName + " is not defined.");
        }
    }

    public static void main(String[] args) {
        ReverseNumber interpreter = new ReverseNumber();
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




// შესაყვანი სტრუქტურა
//let num = 1230
//reverse(num)
//print(num)
