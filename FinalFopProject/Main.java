package FinalFopProject;

public class Main {
    public static void main(String[] args) {
        try {
            // To test the project, write a code snippet for a single algorithm from the swiftCodes file.
            // The snippet should include only the essential elements of the algorithm, 
            // starting from the func to print statement and ending with the print statements.
            // Ensure the code is concise and contains no comments or additional unnecessary lines.
            String swiftCode = """
func gcd(_ a: Int, _ b: Int) -> Int {
    var x = a
    var y = b
    while y != 0 {
        let temp = y
        y = x % y
        x = temp
    }
    return x
}
print(gcd(6,9))

""";

            System.out.println("Input Swift code:");
            System.out.println(swiftCode);
            System.out.println("\nInterpreting and executing...\n");

            // Create interpreter and reader instances
            SwiftInterpreterImpl interpreter = new SwiftInterpreterImpl();
            SwiftReader reader = new SwiftReader(interpreter);

            // Use the SwiftReader to process the Swift code
            reader.readAndInterpret(swiftCode);

            // Execute the generated Java code
            JavaExecutor executor = new JavaExecutor();
            executor.executeJavaCode(interpreter.getGeneratedCode(""));

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
