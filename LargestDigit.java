import java.util.Scanner;

public class LargestDigit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Swift code. Press ENTER twice to execute:");

        // Read input until two consecutive empty lines
        StringBuilder swiftCode = new StringBuilder();
        String line;
        int emptyLineCount = 0;

        while (true) {
            line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                emptyLineCount++;
                if (emptyLineCount == 2) {
                    break;
                }
            } else {
                emptyLineCount = 0; // Reset empty line count on non-empty input
                swiftCode.append(line).append("\n");
            }
        }

        // Process the input
        String swiftCodeStr = swiftCode.toString().trim();
        if (swiftCodeStr.replaceAll("\\s+", "").contains("print(largestDigit)")) { // Ignore spaces
            try {
                int largestDigit = interpretSwiftCode(swiftCodeStr);
                System.out.println("The largest digit is: " + largestDigit);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("Error: Missing 'print(largestDigit)' in the input.");
        }
    }

    public static int interpretSwiftCode(String swiftCode) {
        // Parse the Swift code
        String[] lines = swiftCode.split("\n");
        long number = 0;
        boolean foundNumber = false;

        for (String line : lines) {
            line = line.trim();

            // Find the line that defines 'let number = <number>'
            if (line.startsWith("let number =")) {
                try {
                    String numberStr = line.split("=")[1].trim();
                    number = Long.parseLong(numberStr);
                    foundNumber = true;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Error: Invalid number format in 'let number = <number>'.");
                }
            }
        }

        // If no 'let number' was found, return an error
        if (!foundNumber) {
            throw new IllegalArgumentException("Error: Missing 'let number = <number>' in the input.");
        }

        // Calculate the largest digit
        String numberAsString = Long.toString(number);
        int largestDigit = 0;

        for (int i = 0; i < numberAsString.length(); i++) {
            int digit = Character.getNumericValue(numberAsString.charAt(i));
            if (digit > largestDigit) {
                largestDigit = digit;
            }
        }

        return largestDigit;
    }
}

// შესაყვანი სტრუქტურა
//let number = 87654
//var largestDigit = 0
//
//for digit in String(number) {
//    let num = Int(String(digit))!
//    if num > largestDigit {
//        largestDigit = num
//    }
//}
//print(largestDigit)
//ბოლოს ორჯეტ დააჭირეთ ENTER-ს