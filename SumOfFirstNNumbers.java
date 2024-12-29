import java.util.Scanner;

public class SumOfFirstNNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Swift-like code as multiple lines
        System.out.println("\n" + "Enter Swift code to calculate the sum of the first N numbers (press Enter twice to finish):");
        StringBuilder swiftCode = new StringBuilder();
        String line;

        // Read Swift-like code until a blank line is encountered
        while (true) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                break; // End input on an empty line
            }
            swiftCode.append(line).append("\n");
        }

        // Parse and process the Swift code
        int sum = interpretSwiftCode(swiftCode.toString());

        // Output the result
        System.out.println("The sum is: " + sum);
    }

    public static int interpretSwiftCode(String swiftCode) {
        // Parse the Swift code and execute logic
        String[] lines = swiftCode.split("\n");
        int N = 0;
        boolean isSumVariableDeclared = false;
        int sum = 0;

        for (String line : lines) {
            line = line.trim();

            // Handle variable declaration for N (e.g., let N = 10)
            if (line.startsWith("let N =") || line.startsWith("var N =")) {
                String numberStr = line.split("=")[1].trim();
                N = Integer.parseInt(numberStr);
            }

            // Handle variable declaration for sum (e.g., var sum = 0)
            if (line.startsWith("var sum =")) {
                isSumVariableDeclared = true;
                sum = Integer.parseInt(line.split("=")[1].trim());
            }

            // Handle a for loop structure (e.g., for i in 1...N { ... })
            if (line.startsWith("for")) {
                if (line.contains("in 1...N")) {
                    for (int i = 1; i <= N; i++) {
                        sum += i;
                    }
                }
            }
        }

        // Check if the sum variable was declared
        if (!isSumVariableDeclared) {
            throw new IllegalArgumentException("Swift code must declare 'var sum = 0' before using it.");
        }

        return sum;
    }
}
