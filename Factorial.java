import java.util.Scanner;

public class Factorial {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read full input (including multiple lines)
        String line1 = scanner.nextLine();  // "let number = 5"
        String line2 = scanner.nextLine();  // "var factorial = 1"
        String line3 = scanner.nextLine();  // "for i in 1..number {"

        // Allow user to input multiple lines until "print(factorial)" is entered
        String line4 = "";
        while (scanner.hasNextLine()) {
            line4 = scanner.nextLine();
            if (line4.equals("print(factorial)")) {
                break;  // End when "print(factorial)" is entered
            }
        }

        // Parse 'let number = 5' to extract the number
        int number = Integer.parseInt(line1.split("=")[1].trim());  // Extract number from input

        // Initialize factorial variable from "var factorial = 1"
        int factorial = 1;  // Default value

        // Parse the 'for' loop and handle the 'number' variable correctly
        String loopRange = line3.split("in")[1].split("\\{")[0].trim();  // Extract the range from "for i in 1..number"

        // Replace "number" with the actual value of number in the range
        loopRange = loopRange.replace("number", String.valueOf(number));

        // Now parse the range correctly
        int start = Integer.parseInt(loopRange.split("\\.\\.")[0].trim());  // Extract start of range (1)
        int end = Integer.parseInt(loopRange.split("\\.\\.")[1].trim());  // Extract end of range (number)

        // Execute the loop from 1 to number
        for (int i = start; i <= end; i++) {
            factorial *= i;  // Calculate factorial
        }

        // Output the result after "print(factorial)" is entered
        System.out.println(factorial);  // Equivalent to 'print(factorial)'

        scanner.close();
    }
}
// შესაყვანი სტრუქტურა
//let number = 9
//var factorial = 1
//for i in 1..number {
//    factorial *= i
//}
//print(factorial)