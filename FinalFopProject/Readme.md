# Swift Language Subset Guide
  


## Table of Contents

- [Introduction](#introduction)
- [Data Types](#data-types)
- [Variables and Constants](#variables-and-constants)
- [Control Flow](#control-flow)
- [Functions](#functions)
- [Math Operations](#math-operations)
- [Example Program](#example-program)

---

## Introduction

This subset of Swift is for beginners like us who want to get the hang of basic programming without getting lost in advanced topics like classes. We’re keeping it simple here, focusing on the building blocks we'll use in interpreter of Swift.

---

## Data Types

Here are the basic data types:

- **`Int`**: For whole numbers (e.g., `10`, `-5`)
- **`String`**: For text (e.g., `"Hello"`)
- **`Bool`**: For true or false values

### Example:

```swift
var age: Int = 20
let name: String = "Alice"
var isActive: Bool = true
```

---

## Variables and Constants

In Swift, We can declare variables and constants:

- **`var`**: Use for values that can change.
- **`let`**: Use for values that stay the same (constants).

### Example:

```swift
var score: Int = 100   // This can change
let pi: Double = 3.14  // This stays the same
```

---

## Control Flow
### If-Else

```swift
var number = 10
if number > 5 {
    print("Greater than 5")
} else {
    print("Less than or equal to 5")
}
```

### While Loop

```swift
var count = 0
while count < 5 {
    print("Count is \(count)")
    count += 1  // Increment count by 1
}
```

### For Loop

```swift
for i in 1...5 {
    print("Iteration \(i)")
}
```

---

## Functions

### Example:

```swift
func add(a: Int, b: Int) -> Int {
    return a + b
}

let result = add(a: 3, b: 7)
print(result)  // Output: 10
```

---

## Math Operations

Basic math operations are:

- **Addition (`+`)**
- **Subtraction (`-`)**
- **Multiplication (`*`)**
- **Division (`/`)**
- **Remainder (`%`)**

### Example:

```swift
var a = 9
var b = 3

let sum = a + b           // 12
let difference = a - b    // 6
let product = a * b       // 27
let quotient = a / b      // 3
let remainder = a % b     // 0

```

---

## Example Program

Here’s a simple program to calculate the area of a rectangle:

```swift
func calculateArea(length: Int, width: Int) -> Int {
    return length * width
}

var length = 5
var width = 10

if length > 0 && width > 0 {
    let area = calculateArea(length: length, width: width)
    print("The area is \(area).")  // Output: The area is 50.
} else {
    print("Invalid dimensions.")
}
```

---


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


---


## swiftCodes

The swiftCodes file includes multiple algorithm implementations written in Swift. These algorithms are translated into Java as part of the project's automated translation and execution process.

To test the translation and interpretation of these algorithms, use the Main class. In the Main class, you need to write an only one code snippet that tests a single algorithm. This snippet will showcase how the selected algorithm is translated from Swift to Java and how the resulting Java code is executed.

For demonstration purposes, only one algorithm should be included at a time. The code snippet should remain concise, containing just from func to its corresponding print statement for output, without additional comments or extraneous code. This ensures clarity and focuses on testing the translation and execution process effectively.


---

## Core Components

### 1. Interfaces
The project revolves around two interfaces defined in SwiftSubset. These interfaces provide the foundation for interpreting incoming Swift code by outlining the methods required for translation and execution.

### 2. SwiftReader
The SwiftReader class is responsible for reading the Swift code from the main method. It operates line by line, sending each line to the interpreter for translation. The SwiftReader has an interpreter attribute, which is initialized in the main method. This ensures that the Swift code is read and translated step by step in an organized manner.

### 3. SwiftInterpreterImpl
The SwiftInterpreterImpl class is the heart of the interpreter. It implements the two interfaces from SwiftSubset and provides all the methods necessary to translate Swift code into Java. This includes handling loops, print statements, variable declarations, and other constructs found in Swift.

### 4. JavaExecutor
The JavaExecutor class is responsible for executing the translated Java code. Once the Swift code has been fully interpreted and translated into Java, the SwiftInterpreterImpl returns the generated Java code back to SwiftReader, which forwards it to the main method. The JavaExecutor then takes over, generating a GeneratedCode.java file.

---

##  Output
The GeneratedCode.java file is automatically saved in the out folder within the project directory. This file contains the translated Java code and executes it to produce the desired output. The output is displayed in two forms:

- Printed directly in the terminal.
- Saved in the GeneratedCode.java file for future reference.

## Workflow

1. The main method initiates the process by passing the Swift code to SwiftReader.
2. SwiftReader reads the Swift code line by line and directs the interpreter to translate it.
3. The SwiftInterpreterImpl processes the code and generates Java code.
4. The generated Java code is passed to JavaExecutor.
5. JavaExecutor creates the GeneratedCode.java file in the out folder and executes it, displaying the output in the terminal.

   ## Customization

Change colors and sizes with CSS variables:

```css
:root {
    --primary-color: #3b82f6;
    --phone-size: 80px;
    --screen-bounds: 200px;
}
Modify motion sensitivity:
javascriptconst screenX = euler.yaw * SENSITIVITY_X;
const screenY = -euler.pitch * SENSITIVITY_Y;
Common Issues

Cursor stuck: Quaternion values might need normalization
No 3D effects: Browser may not support CSS 3D transforms
Debug mode: Use console.log('Q:', mapper.quaternion) to check values

Compatibility
Runs on Chrome, Firefox, Safari, and Edge. Mobile-friendly responsive design.

Pure HTML/CSS/JavaScript - no frameworks or build tools required.
