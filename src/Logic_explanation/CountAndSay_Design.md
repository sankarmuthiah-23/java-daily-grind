# Count and Say Sequence - Design Document

## Problem Statement

Generate the **nth element** of the count-and-say sequence.

**Sequence Definition:**
- Base case: `countAndSay(1) = "1"`
- Recursive: `countAndSay(n)` = run-length encoding of `countAndSay(n - 1)`

**What is Run-Length Encoding (RLE)?**
Replace each group of consecutive identical characters with: `[count][character]`

Example: `"3322251"` → `"23321511"`
- `33` → `23` (two 3s)
- `222` → `32` (three 2s)
- `5` → `15` (one 5)
- `1` → `11` (one 1)

---

## The Count-and-Say Concept

**Think of it as "reading aloud":**

```
"1"      →  Read as "one 1"        →  "11"
"11"     →  Read as "two 1s"       →  "21"
"21"     →  Read as "one 2, one 1" →  "1211"
"1211"   →  Read as "one 1, one 2, two 1s" → "111221"
```

---

## Example Walkthrough

### Example 1: n = 4

```
Step 1: countAndSay(1) = "1"

Step 2: Apply RLE to "1"
        Group: "1" (one time)
        Result: "11" (one 1)

Step 3: Apply RLE to "11"
        Group: "11" (two times)
        Result: "21" (two 1s)

Step 4: Apply RLE to "21"
        Groups: "2" (one time), "1" (one time)
        Result: "1211" (one 2, one 1)

Output: "1211"
```

### Example 2: n = 5

```
Starting from Step 4 result: "1211"

Step 5: Apply RLE to "1211"
        Groups: "1" (one), "2" (one), "11" (two)
        Result: "111221" (one 1, one 2, two 1s)

Output: "111221"
```

---

## Algorithm Approach: Iterative Run-Length Encoding

This solution uses an **iterative approach** instead of recursion:

### Why Iterative?
- Avoids function call overhead
- Clearer state management
- More efficient for larger n values

### High-Level Flow

```
1. Start with result = "1"
2. For each iteration from 2 to n:
   - Apply run-length encoding to current result
   - Store encoded result back into result
3. Return final result
```

---

## Code Breakdown

### Main Method
```java
public static void main(String[] args) {
    CountAndSay cs = new CountAndSay();
    int n = 5;
    String result = cs.countAndSay(n);
    System.out.println(result);  // Output: "111221"
}
```

### Approach Overview

```java
public String countAndSay(int n) {
    String result = "1";  // Base case
    
    // Apply RLE (n-1) times to get nth element
    for(int i = 2; i <= n; i++) {
        result = applyRunLengthEncoding(result);
    }
    
    return result;
}
```

---

## Run-Length Encoding Implementation

### The Core Logic

```java
StringBuilder sb = new StringBuilder();
int pointer = 0;

while (pointer < result.length()) {
    char currentDigit = result.charAt(pointer);
    int count = 0;
    
    // Count consecutive identical characters
    while(pointer < result.length() && result.charAt(pointer) == currentDigit) {
        count++;
        pointer++;
    }
    
    // Append count and character to result
    sb.append(count);
    sb.append(currentDigit);
}
result = sb.toString();
```

### Step-by-Step Breakdown

| Step | Input | Purpose |
|------|-------|---------|
| 1 | `currentDigit = result.charAt(pointer)` | Get character to count |
| 2 | `while(result.charAt(pointer) == currentDigit)` | Count all consecutive matches |
| 3 | `count++; pointer++` | Increment counter and move pointer |
| 4 | `sb.append(count); sb.append(currentDigit)` | Write count and character |

---

## Detailed Example Trace: n = 4

### Iteration 1 (i = 2)
```
Input:  result = "1"
Pointer: 0

Process:
  currentDigit = '1'
  Group: "1" (pointer 0 → 1)
  count = 1
  Append: "1" + "1" → "11"

Output: result = "11"
```

### Iteration 2 (i = 3)
```
Input:  result = "11"
Pointer: 0

Process:
  currentDigit = '1'
  Group: "11" (pointer 0 → 2, both are '1')
  count = 2
  Append: "2" + "1" → "21"

Output: result = "21"
```

### Iteration 3 (i = 4)
```
Input:  result = "21"
Pointer: 0

Process:
  Iteration A:
    currentDigit = '2'
    Group: "2" (pointer 0 → 1)
    count = 1
    Append: "1" + "2" → "12"
  
  Iteration B:
    currentDigit = '1'
    Group: "1" (pointer 1 → 2)
    count = 1
    Append: "1" + "1" → "1211"

Output: result = "1211"
```

---

## Visual Pointer Movement

### Example: Encoding "1211"

```
String: "1 2 1 1"
Index:   0 1 2 3

First while loop iteration:
  pointer = 0, currentDigit = '1'
  ✓ result[0] == '1' → count = 1, pointer = 1
  ✗ result[1] == '2' → Stop inner loop
  Append: "11"
  pointer = 1

Second while loop iteration:
  pointer = 1, currentDigit = '2'
  ✓ result[1] == '2' → count = 1, pointer = 2
  ✗ result[2] == '1' → Stop inner loop
  Append: "12"
  pointer = 2

Third while loop iteration:
  pointer = 2, currentDigit = '1'
  ✓ result[2] == '1' → count = 1, pointer = 3
  ✓ result[3] == '1' → count = 2, pointer = 4
  ✗ pointer >= length → Stop inner loop
  Append: "21"
  pointer = 4

Final result: "111221"
```

---

## The Two-Pointer Pattern

This code uses the **Two-Pointer / Grouping Pattern**:

```
pointer → Moves through string
currentDigit → Identifies current group

While pointer hasn't reached end:
  1. Identify current group's character
  2. Count all consecutive occurrences
  3. Move pointer to next different character
  4. Record count and character
```

This pattern is efficient for grouping consecutive elements!

---

## Why Use StringBuilder?

```java
// ❌ Inefficient: String concatenation
String result = "";
result = result + count;      // Creates new string object
result = result + currentDigit; // Creates another new string object

// ✅ Efficient: StringBuilder
StringBuilder sb = new StringBuilder();
sb.append(count);      // Modifies internal buffer
sb.append(currentDigit); // Adds to same buffer
String result = sb.toString(); // Single string creation
```

**Time Complexity Difference:**
- Concatenation: O(n²) - each concatenation copies entire string
- StringBuilder: O(n) - single pass through buffer

---

## Complexity Analysis

### Time Complexity: **O(n × m²)**
- `n` = input number
- `m` = length of string at each step
- String length grows exponentially with each iteration
- Each iteration requires one full pass through current string
- Each pass creates a new string

### Space Complexity: **O(m)**
- `m` = length of result string at step n
- String length grows exponentially: roughly 1.3x per iteration
- For n=30, result length ≈ 1000+ characters
- StringBuilder and intermediate strings use O(m) space

### Growth Pattern
```
n=1:  "1"           (length: 1)
n=2:  "11"          (length: 2)
n=3:  "21"          (length: 2)
n=4:  "1211"        (length: 4)
n=5:  "111221"      (length: 6)
n=6:  "312211"      (length: 6)
n=7:  "13112221"    (length: 8)
n=8:  "1113213211"  (length: 10)
```

---

## Key Data Structures

| Structure | Purpose | Why Used |
|-----------|---------|----------|
| `String result` | Holds current sequence element | Immutable, thread-safe |
| `StringBuilder sb` | Builds encoded string efficiently | Mutable, efficient concatenation |
| `int pointer` | Tracks position in string | Fast iteration through characters |
| `int count` | Tracks consecutive characters | Simple counter |

---

## Key Insights

✅ **Base Case Matters:** Starting with "1" sets up the entire sequence  
✅ **Iteration Over Recursion:** Avoids stack overflow for large n  
✅ **StringBuilder for Efficiency:** Prevents O(n²) string concatenation cost  
✅ **Two-Pointer Grouping:** Elegant way to count consecutive characters  
✅ **Exponential Growth:** String length roughly multiplies by 1.3 each iteration  

---

## Edge Cases Handled

| Input | Output | Reason |
|-------|--------|--------|
| `n = 1` | `"1"` | Base case, no encoding needed |
| `n = 2` | `"11"` | One iteration: "1" → "11" |
| Large `n` | String grows exponentially | Be aware of memory limits |

---

## Algorithm Summary

```
CountAndSay(n):
  1. Initialize result = "1"
  2. Loop from i = 2 to n:
     a. Create empty StringBuilder
     b. Use pointer to scan through result
     c. For each group of consecutive characters:
        - Count the group size
        - Append count + character to StringBuilder
     d. Update result with encoded string
  3. Return result
```

The beauty of this algorithm is its simplicity: just count groups and say what you see! 📖
