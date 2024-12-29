import java.util.Scanner;

public class isNumberPalindrome {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Swift code (as a string)
        System.out.println("Enter Swift code to check if a number is a palindrome:");
        String swiftCode = scanner.nextLine();

        // Parse and process the Swift code
        boolean isPalindrome = interpretSwiftCode(swiftCode);

        // Output the result (true or false)
        System.out.println(isPalindrome);
    }

    public static boolean interpretSwiftCode(String swiftCode) {
        // Parse the Swift code and execute logic
        // For simplicity, we assume the input Swift code follows a simple structure
        // Extract number (Assuming 'let number = <number>')
        String[] lines = swiftCode.split("\n");
        long number = 0;
        String numberStr = "";

        // Find the line that defines 'let number = <number>'
        for (String line : lines) {
            if (line.startsWith("let number =")) {
                numberStr = line.split("=")[1].trim();
                number = Long.parseLong(numberStr);
                break;
            }
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