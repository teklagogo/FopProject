import java.util.Scanner;

public class isNumberPalindrome {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Swift code to check if a number is a palindrome. Press ENTER twice to execute:");

        // Read multiline Swift code input
        StringBuilder swiftCode = new StringBuilder();
        String line;
        int emptyLineCount = 0;

        while (true) {
            line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                emptyLineCount++;
                if (emptyLineCount == 2) {
                    break; // Stop reading input on two consecutive empty lines
                }
            } else {
                emptyLineCount = 0; // Reset empty line count on non-empty input
                swiftCode.append(line).append("\n");
            }
        }

        // Process the input
        boolean isPalindrome = interpretSwiftCode(swiftCode.toString());

        // Output the result
        System.out.println(isPalindrome);
    }

    public static boolean interpretSwiftCode(String swiftCode) {
        // Parse the Swift code and execute logic
        String[] lines = swiftCode.split("\n");
        long number = 0;
        String numberStr = "";

        // Find the line that defines 'let number = <number>'
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("let number =")) {
                numberStr = line.split("=")[1].trim();
                try {
                    number = Long.parseLong(numberStr);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Error: Invalid number format in 'let number = <number>'.");
                }
                break;
            }
        }

        // If 'let number' is not found, throw an error
        if (numberStr.isEmpty()) {
            throw new IllegalArgumentException("Error: Missing 'let number = <number>' in the Swift code.");
        }

        // Convert number to a string and check if it's a palindrome
        String originalStr = Long.toString(number);
        String reversedStr = new StringBuilder(originalStr).reverse().toString();

        // Return whether the number is a palindrome
        return originalStr.equals(reversedStr);
    }
}


// შესაყვანი სტრუქტურა
//let number = 123221
//var isPalindrome = true
//
//let numberStr = String(number)
//let reversedStr = String(numberStr.reversed())
//
//if numberStr != reversedStr {
//    isPalindrome = false
//}
//
//print(isPalindrome)