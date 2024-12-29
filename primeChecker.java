import java.util.HashMap;
import java.util.Map;

public class primeChecker {
    private final Map<String, Integer> variables = new HashMap<>(); // Variable storage

    public void eval(String code) {
        // Split by newlines to handle Swift-like syntax
        String[] lines = code.split("\\R"); // Matches any line break
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue; // Skip empty lines

            // Handle variable declaration
            if (line.startsWith("let ") || line.startsWith("var ")) {
                handleDeclaration(line);
            }
            // Handle isPrime check
            else if (line.startsWith("isPrime")) {
                handlePrimeCheck(line);
            }
            // Handle print statements
            else if (line.startsWith("print")) {
                handlePrint(line);
            } else {
                System.err.println("Error: Unknown command - " + line);
            }
        }
    }

    private void handleDeclaration(String line) {
        // Example: let num = 29 or var num = 45
        String[] parts = line.split("=");
        if (parts.length != 2) {
            System.err.println("Error: Invalid declaration - " + line);
            return;
        }
        String varName = parts[0].replace("let", "").replace("var", "").trim();
        try {
            int value = Integer.parseInt(parts[1].trim());
            variables.put(varName, value); // Store the variable
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format in declaration - " + line);
        }
    }

    private void handlePrimeCheck(String line) {
        // Syntax: isPrime(varName)
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (variables.containsKey(varName)) {
            int value = variables.get(varName);
            boolean isPrime = checkPrime(value);
            variables.put(varName, isPrime ? 1 : 0); // Update the variable: 1 for prime, 0 for not prime
        } else {
            System.err.println("Error: Variable " + varName + " is not defined.");
        }
    }

    private void handlePrint(String line) {
        // Syntax: print(varName)
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        if (variables.containsKey(varName)) {
            int value = variables.get(varName);
            System.out.println(value == 1 ? "Prime" : "Not Prime");
        } else {
            System.err.println("Error: Variable " + varName + " is not defined.");
        }
    }

    private boolean checkPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= num / 2; ++i) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        primeChecker interpreter = new primeChecker();

        // Example program: Check if a number is prime and print the result
        String program = """
            let num = 11
            isPrime(num)
            print(num)
        """;

        interpreter.eval(program);
    }
}
