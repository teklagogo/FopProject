import java.util.Scanner;

public class LargestDigit{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Swift code (as a string)
        System.out.println("Enter Swift code to calculate largest digit:");
        String swiftCode = scanner.nextLine();

        // Parse and process the Swift code
        int largestDigit = interpretSwiftCode(swiftCode);

        // Output the result
        System.out.println("The largest digit is: " + largestDigit);
    }

    public static int interpretSwiftCode(String swiftCode) {
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


// შსაყვანი სტრუქტურა
//let number = 11111111111
//var largestDigit = 0
//
//for digit in String(number) {
//    let num = Int(String(digit))!
//    if num > largestDigit {
//        largestDigit = num
//    }
//}
//
//print(largestDigit)
