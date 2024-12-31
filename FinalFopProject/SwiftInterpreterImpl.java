package FinalFopProject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//The SwiftInterpreterImpl class implements the SwiftSubset interface and translates Swift code into Java.

public class SwiftInterpreterImpl implements SwiftSubset {

    // A StringBuilder to hold the dynamically generated Java code.
    private StringBuilder javaCode;

    //A StringBuilder to store the methods that are translated from Swift.
    private StringBuilder classMethods;

    // Tracks the current level of indentation for maintaining proper code formatting.
    private int indentationLevel;

    // A static constant that defines the standard indentation string (4 spaces).
    private static final String INDENT = "    ";

    public SwiftInterpreterImpl() {
        this.javaCode = new StringBuilder();
        this.classMethods = new StringBuilder();
        this.indentationLevel = 0;
    }

    // Adds the correct level of indentation to the given StringBuilder according to the current indentation depth.
    private void appendIndentation(StringBuilder builder) {

        builder.append(INDENT.repeat(indentationLevel));
    }

    public String getGeneratedCode(String outsideFunction) {
        //StringBuilder used to gather the generated code.
        StringBuilder fullCode = new StringBuilder();
        fullCode.append("public class GeneratedCode {\n");

        // Add the methods defined outside the main method
        fullCode.append(classMethods);

        // Process each line of the outsideFunction input
        for (String line : outsideFunction.split("\n")) {

            if (line.isEmpty()) {
                continue; // skips empty lines

            } else if (line.contains("print")) {

                // translates print statements to the appropriate Java syntax
                translatePrint(line);
            }
            //Append other lines to the javaCode with a semicolon.
            else {
                javaCode.append(line).append(";\n");
            }
        }
        // Add the main method to the generated code
        fullCode.append("    public static void main(String[] args) {\n");
        // Append the accumulated javaCode inside the main method.
        fullCode.append(javaCode);
        fullCode.append("    }\n");
        fullCode.append("}\n");

        return fullCode.toString();
    }


    @Override
/**
 * Translates a print statement from a Swift-like syntax to Java's System.out.println.
 * This method replaces the "print" keyword with "System.out.println" and appends
 * the resulting line to the javaCode StringBuilder with the appropriate indentation.
 */
    public void translatePrint(String var) {
        appendIndentation(javaCode);
        String print = var.replace("print", "System.out.println");
        javaCode.append(print).append(";\n");
    }


    @Override
    /**
     * Transforms a function definition from a Swift-like format to Java format.
     * This method creates the function signature and body, ensuring proper formatting
     * and indentation, while also addressing return types as needed.
     */
    public void translateFunction(String functionName, String parameters, String body, String returnType) {
        appendIndentation(classMethods);

        parameters = parameters.replaceAll("_\\s*", "");

        parameters = convertParameters(parameters);
        returnType = inferType(returnType);

        // Write the function signature
        classMethods.append("    public static ").append(returnType).append(" ")
                .append(functionName).append("(").append(parameters).append(") {\n");

        indentationLevel = 2; // Reset indentation for function body

        // Add function body, making sure that a return statement is present if needed
        String convertedBody = convertSwiftBodyToJava(body);

        // If the function has a return type, ensure a return statement is included
        if (!returnType.equals("void") && !convertedBody.contains("return")) {
            convertedBody += "\n    " + "return 0;";  // Default return for int type
        }

        classMethods.append(convertedBody);

        classMethods.append("    }\n\n");
        indentationLevel = 0; // Reset indentation
    }

    /**
     * Converts a block of code written in Swift-like syntax into Java syntax.
     * This method processes each line of the input body, translating various
     * control structures and statements from Swift to their Java equivalents.
     */
    private String convertSwiftBodyToJava(String body) {
        StringBuilder convertedBody = new StringBuilder();
        // split the input body into individual lines
        String[] lines = body.split("\n");

        for (String line : lines) {
            //removes whitespace
            line = line.trim();

            if (!line.isEmpty()) { // process non-empty lines
                appendIndentation(convertedBody);

                // Convert Swift for loop syntax to Java
                if (line.startsWith("for") && line.contains("in") && line.contains("...")) {
                    //Split the line into variable and range parts
                    String[] parts = line.split("\\s+in\\s+");

                    // extract the loop variavle
                    String variable = parts[0].replace("for", "").trim();

                    // extract the range
                    String range = parts[1].replace("{", "").trim();

                    // split the range into start and end values
                    String[] rangeParts = range.split("\\.\\.\\.");
                    convertedBody.append(String.format("for (int %s = %s; %s <= %s; %s++) {\n",
                            variable, rangeParts[0], variable, rangeParts[1], variable));
                }
                // Convert Swift while loop syntax to Java
                else if (line.startsWith("while")) {

                    String condition = line.replace("while", "").replace("{", "").trim(); //extract condition

                    convertedBody.append(String.format("while (%s) {\n", condition)); // appends the while loop
                }
                // Convert Swift if statement syntax to Java
                else if (line.startsWith("if")) {

                    String condition = line.split("\\{")[0].replace("if", " ").trim();

                    if (line.endsWith("}")) {

                        String command = line.split("\\{")[1].replace("}", ";}\n");

                        convertedBody.append("if (" + condition + ") {" + command);
                    }
                }
                // Handle return statement
                else if (line.startsWith("return")) {
                    convertedBody.append("return ").append(line.substring("return".length()).trim()).append(";\n");
                }
                // Handle Swift print statements
                else if (line.startsWith("print")) {
                    String argument = line.replace("print", "System.out.println"); // Converts print to System.out.println.
                    convertedBody.append(argument).append(";\n");
                }
                // Handle regular lines and append a semicolon at the end
                else {
                    convertedBody.append(convertSwiftLineToJava(line)).append("\n");
                }
            }
        }
        int openBraces = countOccurrences(convertedBody.toString(), "{");
        int closeBraces = countOccurrences(convertedBody.toString(), "}");

        if (openBraces != closeBraces) {
            convertedBody.append("}\n"); // Append a closing brace if there is an imbalance.
        }

        return convertedBody.toString();
    }


    /**
     * Determines the equivalent Java type for a specified Swift-like type.
     * This method translates Swift-like type names into their corresponding Java types.
     */
    private String inferType(String value) {
        if (value.matches("Int")) {
            return "int";
        } else if (value.matches("Double")) {
            return "double";
        } else if (value.matches("Bool")) {
            return "boolean";
        } else if (value.matches("String")) {
            return "String";
        } else if (value.matches("void")) {
            return "void";
        }
        return "int";
    }

    private String convertSwiftTypeToJava(String swiftType) {
        Map<String, String> typeMapping = new HashMap<>();
        typeMapping.put("Int", "int");
        typeMapping.put("Double", "double");
        typeMapping.put("String", "String");
        typeMapping.put("Bool", "boolean");

        return typeMapping.getOrDefault(swiftType.trim(), swiftType.trim());
    }

    /**
     * This method processes each parameter, removing underscores and converting
     * Swift types to their Java equivalents.
     */
    private String convertParameters(String parameters) {
        //  Return an empty string if no parameters are provided.
        if (parameters.isEmpty()) return "";

        String[] params = parameters.split(","); // Split parameters by comma
        List<String> convertedParams = new ArrayList<>();

        for (String param : params) {
            String withoutUnderscore = param.replace("_", "");
            String[] parts = param.trim().split(":");
            String paramName = parts[0].trim();
            String paramType = convertSwiftTypeToJava(parts[1].trim());

            // Handle underscore in parameter names
            if (paramName.startsWith("_")) {
                paramName = paramName.substring(1).trim(); // Remove leading underscore
            }

            convertedParams.add(paramType + " " + paramName);
        }

        return String.join(", ", convertedParams);
    }

    /**
     * This method handles various Swift constructs, including print statements,
     * variable declarations, increment/decrement operations, and return statements
     * and converts a single line of Swift-like code to its equivalent Java code.
     */
    private String convertSwiftLineToJava(String line) {
        // Convert print statements
        if (line.startsWith("print(")) {

            return "System.out.println" + line.substring(5);
        }

        // Convert Swift increment/decrement
        if (line.contains("+=") || line.contains("-=") || line.contains("*=") || line.contains("/=") || line.contains("%=") || line.contains("=") && !line.contains("let") && !line.contains("var")) {
            return line + ";";
        }

        // Convert return statements
        if (line.startsWith("return")) {
            return line + ";";
        }

        // Convert variable declarations
        if (line.startsWith("var") || line.startsWith("let")) {
            String[] parts = line.split("=");
            String varName = parts[0].split("\\s+")[1].trim();
            String value = parts[1].trim();
            String type = inferType(value);
            return type + " " + varName + " = " + value + ";";
        }

        return line;
    }

    /**
     * Counts the number of occurrences of a specified substring within a given string.
     * This method searches for the target substring and returns the total count of its appearances.
     */

    private int countOccurrences(String str, String target) {
        int count = 0;
        int index = 0;

        while ((index = str.indexOf(target, index)) != -1) {
            count++;
            index += target.length();
        }

        return count;
    }
}