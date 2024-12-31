package src;
/**

 The SwiftReader class is responsible for reading Swift-like code

 interpreting it, and converting it into Java code using the SwiftInterpreterImpl.
 */
public class SwiftReader {
    private SwiftInterpreterImpl interpreter;

    public SwiftReader(SwiftInterpreterImpl interpreter) {
        this.interpreter = interpreter;
    }

    public void readAndInterpret(String swiftCode) {

        // Split the input code into individual lines.
        String[] lines = swiftCode.split("\n");

        StringBuilder functionBody = new StringBuilder();

        // Flag to track if we are currently processing a function.
        boolean insideFunction = false;

        // Variable to store the current function name.
        String functionName = "";

        // Variable to store the function parameters.
        String parameters = "";

        // Variable to store the return type of the function.
        String returnType = "void";

        StringBuilder outsideFunction = new StringBuilder();

        // Process Swift code line by line
        for (String line : lines) {
            line = line.trim();

            // Start of a function definition
            if (line.startsWith("func")) {

                insideFunction = true;

                // Split to extract function name and parameters.
                String[] parts = line.split("func")[1].split("\\(");

                functionName = parts[0].trim();
                String paramAndReturn = parts[1].split("\\{")[0].trim();

                // Determine if the function returns a value or is void
                if (paramAndReturn.contains("->")) {

                    parameters = paramAndReturn.split("->")[0].split("\\)")[0];

                    returnType = paramAndReturn.split("->")[1].trim(); // Capture return type

                } else {
                    parameters = paramAndReturn.split("\\)")[0];

                    // Default to void if no return type is specified.
                    returnType = "void";
                }
            }
            // Function body end
            else if (line.equals("}")) {

                if (insideFunction) {

                    // If the function has a return type and contains a return statement, include it
                    if (!returnType.equals("void")) {

                        functionBody.append(line).append("\n");

                        for (String ret : lines) {

                            if (ret.contains("return")) {

                                functionBody.append(ret).append("\n");
                            }
                        }
                    }
                    // Add the function to the generated code
                    interpreter.translateFunction(functionName, parameters, functionBody.toString(), returnType);

                    // Reset for the next function

                    // Clear the function body for the next function.
                    functionBody = new StringBuilder();

                    insideFunction = false;
                }
            }
            // Print statements in Swift
            else if (line.startsWith("print") && insideFunction) {

                functionBody.append(line);
            }
            // Collect function body lines
            else if (insideFunction && !line.isEmpty()) {

                //appends non-empty lines to the function body
                functionBody.append(line).append("\n");

            } else if (!insideFunction && !line.contains("return")) {

                outsideFunction.append(line).append("\n");
            }
        }

        // Retrieve the generated Java code from the interpreter
        String javaCode = interpreter.getGeneratedCode(outsideFunction.toString());

        System.out.println("Generated Java code:");

        System.out.println(javaCode);
    }
}