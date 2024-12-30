package src;

import javax.tools.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class JavaExecutor {
    public void executeJavaCode(String javaCode) throws ExecutionException {
        String currentDir = System.getProperty("user.dir");
        File outputDir = new File(currentDir + "/out");
        File sourceFile = new File(outputDir, "GeneratedCode.java");

        try {
            // Ensure output directory exists
            outputDir.mkdirs();

            // Write Java code to source file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFile))) {
                writer.write(javaCode);
            }

            // Print generated Java code for debugging
            System.out.println("Generated Java Code:\n" + javaCode);

            // Compile the Java code
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(outputDir));
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            boolean success = task.call();

            // If compilation fails, collect and display errors
            if (!success) {
                StringBuilder errorMessage = new StringBuilder("Compilation failed:\n");
                for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                    errorMessage.append(String.format(
                            "Error on line %d in %s: %s%n",
                            diagnostic.getLineNumber(),
                            diagnostic.getSource(),
                            diagnostic.getMessage(null)
                    ));
                }
                throw new ExecutionException(errorMessage.toString(), null);
            }

            // Load and execute the class
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{outputDir.toURI().toURL()});
            Class<?> generatedClass = Class.forName("GeneratedCode", true, classLoader);
            Method mainMethod = generatedClass.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[]{});

        } catch (Exception e) {
            throw new ExecutionException("Execution failed: " + e.getMessage(), e);
        }
    }
}
