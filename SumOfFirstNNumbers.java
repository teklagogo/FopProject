import java.util.Scanner;

public class SumOfFirstNNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Swift code (as a string)
        System.out.println("Enter Swift code to calculate the sum of the first N numbers:");
        String swiftCode = scanner.nextLine();

        // Parse and process the Swift code
        int sum = interpretSwiftCode(swiftCode);

        // Output the result
        System.out.println("The sum is: " + sum);
    }
    
    public static int interpretSwiftCode(String swiftCode) {
        // Parse the Swift code and execute logic
        // For simplicity, we assume the input Swift code follows a simple structure
        // Extract N (Assuming 'let N = <number>')

        String[] lines = swiftCode.split("\n");
        int N = 0;

        // Find the line that defines 'let N = <number>'
        for (String line : lines) {
            if (line.startsWith("let")) {
                String numberStr = line.split("=")[1].trim();
                N = Integer.parseInt(numberStr);
                break;
            }
        }

        // Calculate the sum of the first N numbers using a loop
        int sum = 0;
        for (int i = 1; i <= N; i++) {
            sum += i;
        }

        // Return the calculated sum
        return sum;

        //შესაყვანი კოდი:
        //let N = 10
        //var sum = 0
        //
        //for i in 1...N {
        //    sum += i
        //}
        //
        //print(sum)
    }
}
