package FinalFopProject;

public class SwiftReader {
    private SwiftInterpreterImpl interpreter;

    public SwiftReader(SwiftInterpreterImpl interpreter) {
        this.interpreter = interpreter;
    }

    public void readAndInterpret(String swiftCode) {
        String[] lines = swiftCode.split("\n");
        StringBuilder functionBody = new StringBuilder();
        boolean insideFunction = false;
        String functionName = "";
        String parameters = "";
        String returnType = "void";
        StringBuilder outsideFunction = new StringBuilder();

        // Process Swift code line by line
        for (String line : lines) {
            line = line.trim();

            // Start of a function definition
            if (line.startsWith("func")) {
                insideFunction = true;
                String[] parts = line.split("func")[1].split("\\(");
                functionName = parts[0].trim();
                String paramAndReturn = parts[1].split("\\{")[0].trim();

                // Determine if the function returns a value or is void
                if (paramAndReturn.contains("->")) {
                    parameters = paramAndReturn.split("->")[0].split("\\)")[0];
                    returnType = paramAndReturn.split("->")[1].trim(); // Capture return type
                } else {
                    parameters = paramAndReturn.split("\\)")[0];
                    returnType = "void"; // Default to void if no return type
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
