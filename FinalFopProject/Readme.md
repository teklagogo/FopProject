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