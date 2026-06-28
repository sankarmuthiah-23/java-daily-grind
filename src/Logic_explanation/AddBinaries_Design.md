# Add Binaries - Design Document

## Problem Statement

Given two binary strings `a` and `b`, return their **sum as a binary string** without leading zeros.

**Key Points:**
- Inputs are binary strings (only '0' and '1')
- Must return result as binary string
- No leading zeros in result (except "0")
- Process similar to manual addition in elementary school

---

## Binary Number System Quick Recap

| Binary | Decimal |
|--------|---------|
| 0 | 0 |
| 1 | 1 |
| 10 | 2 |
| 11 | 3 |
| 100 | 4 |
| 101 | 5 |
| 110 | 6 |
| 111 | 7 |
| 1000 | 8 |

**Key Concept:** In binary, only two digits exist: 0 and 1
- 0 + 0 = 0 (no carry)
- 0 + 1 = 1 (no carry)
- 1 + 0 = 1 (no carry)
- 1 + 1 = 10 (result 0, carry 1)
- 1 + 1 + 1 (carry) = 11 (result 1, carry 1)

---

## Example Walkthroughs

### Example 1: "11" + "1"

```
Binary Addition:
      11  (3 in decimal)
    +  1  (1 in decimal)
    ----
     100  (4 in decimal)

Step-by-step:
Position 0 (rightmost): 1 + 1 = 10 → write 0, carry 1
Position 1: 1 + (no digit) + carry(1) = 10 → write 0, carry 1
Position 2: (no digit) + (no digit) + carry(1) = 1 → write 1, carry 0

Result: 100 ✓
```

### Example 2: "1010" + "1011"

```
Binary Addition:
      1010  (10 in decimal)
    + 1011  (11 in decimal)
    ------
     10101  (21 in decimal)

Step-by-step:
Position 0: 0 + 1 = 1 → write 1, carry 0
Position 1: 1 + 1 + carry(0) = 10 → write 0, carry 1
Position 2: 0 + 0 + carry(1) = 1 → write 1, carry 0
Position 3: 1 + 1 + carry(0) = 10 → write 0, carry 1
Position 4: carry(1) = 1 → write 1, carry 0

Result: 10101 ✓
```

### Example 3: "0" + "0"

```
Binary Addition:
      0
    + 0
    ---
      0

Result: "0" (single zero, no leading zeros)
```

### Example 4: "1111" + "1111"

```
Binary Addition:
      1111  (15 in decimal)
    + 1111  (15 in decimal)
    ------
     11110  (30 in decimal)

Step-by-step:
Position 0: 1 + 1 = 10 → write 0, carry 1
Position 1: 1 + 1 + carry(1) = 11 → write 1, carry 1
Position 2: 1 + 1 + carry(1) = 11 → write 1, carry 1
Position 3: 1 + 1 + carry(1) = 11 → write 1, carry 1
Position 4: carry(1) = 1 → write 1, carry 0

Result: 11110 ✓
```

---

## Algorithm Approach: Two-Pointer from Right to Left

### Why Right to Left?

Like manual addition, we start from the **rightmost digits** (least significant bits):
- Process units place first
- Then twos place
- Then fours place... and so on
- Carry propagates left

```
      1 1 1 1  (Start here: rightmost →)
    +   1 0 1
    ---------
            1
```

### High-Level Flow

```
1. Initialize two pointers at end of both strings
2. Initialize carry = 0
3. While pointers haven't reached beginning:
   a. Calculate sum of:
      - Current carry
      - Current digit from string a (if exists)
      - Current digit from string b (if exists)
   b. Append (sum % 2) to result
      - sum % 2 gives the binary digit (0 or 1)
   c. Calculate carry = sum / 2
      - sum / 2 gives the carry (0 or 1)
4. If carry remains, append it
5. Reverse the result (built backwards!)
```

---

## Code Breakdown

### Initialization

```java
String a = "11";
String b = "1";
StringBuilder sb = new StringBuilder();  // Build result backwards
int i = a.length() - 1;                // Pointer for string a
int j = b.length() - 1;                // Pointer for string b
int carry = 0;                         // Start with no carry
```

### Main Loop - Processing Digit by Digit

```java
while(i >= 0 || j >= 0) {
    int sum = carry;  // Start with current carry
    
    // Add digit from string a if available
    if(i >= 0) {
        sum += a.charAt(i) - '0';  // Convert '0'/'1' to 0/1
        i--;
    }
    
    // Add digit from string b if available
    if(j >= 0) {
        sum += b.charAt(j) - '0';  // Convert '0'/'1' to 0/1
        j--;
    }
    
    // Append the result digit (0 or 1)
    sb.append(sum % 2);
    
    // Calculate new carry
    carry = (sum / 2);
}
```

### Handling Final Carry & Reversing

```java
// If there's a remaining carry after all digits processed
if(carry != 0) 
    sb.append(carry);

// Reverse because we built the result backwards
System.out.println(sb.reverse().toString());
```

### Why Reverse?

We build result **backwards** (right to left), so we must reverse:

```
Without reverse: "001" → WRONG
After reverse:   "100" → CORRECT
```

---

## Understanding the Math

### Sum Modulo 2 (Result Digit)

```
Sum = 0: 0 % 2 = 0    → Digit is 0
Sum = 1: 1 % 2 = 1    → Digit is 1
Sum = 2: 2 % 2 = 0    → Digit is 0 (with carry)
Sum = 3: 3 % 2 = 1    → Digit is 1 (with carry)

Reason: In binary, only 0 and 1 are valid digits
```

### Sum Division 2 (Carry)

```
Sum = 0: 0 / 2 = 0    → No carry
Sum = 1: 1 / 2 = 0    → No carry
Sum = 2: 2 / 2 = 1    → Carry 1
Sum = 3: 3 / 2 = 1    → Carry 1

Reason: Sum of 2 or 3 means overflow to next position
```

### Truth Table

| a | b | carry_in | sum | digit (sum%2) | carry_out (sum/2) |
|---|---|----------|-----|---------------|-------------------|
| 0 | 0 | 0 | 0 | 0 | 0 |
| 0 | 0 | 1 | 1 | 1 | 0 |
| 0 | 1 | 0 | 1 | 1 | 0 |
| 0 | 1 | 1 | 2 | 0 | 1 |
| 1 | 0 | 0 | 1 | 1 | 0 |
| 1 | 0 | 1 | 2 | 0 | 1 |
| 1 | 1 | 0 | 2 | 0 | 1 |
| 1 | 1 | 1 | 3 | 1 | 1 |

---

## Step-by-Step Execution: "11" + "1"

### Initial State

```
String a: "11"  (indices: 0=1, 1=1)
String b: "1"   (indices: 0=1)

i = 1 (last index of a)
j = 0 (last index of b)
carry = 0
sb = "" (empty)
```

### Iteration 1

```
Position: Rightmost digits
i=1, j=0, carry=0

sum = carry = 0
  → a.charAt(1) = '1' → sum += ('1' - '0') = 0 + 1 = 1, i=0
  → b.charAt(0) = '1' → sum += ('1' - '0') = 1 + 1 = 2, j=-1

Digit: sum % 2 = 2 % 2 = 0
  → sb.append(0) → sb = "0"

Carry: sum / 2 = 2 / 2 = 1

State after iteration 1:
  i=0, j=-1, carry=1, sb="0"
```

### Iteration 2

```
Position: Middle digit
i=0, j=-1, carry=1

sum = carry = 1
  → i >= 0: a.charAt(0) = '1' → sum += 1 = 2, i=-1
  → j < 0: skip b

Digit: sum % 2 = 2 % 2 = 0
  → sb.append(0) → sb = "00"

Carry: sum / 2 = 2 / 2 = 1

State after iteration 2:
  i=-1, j=-1, carry=1, sb="00"
```

### Iteration 3 - After Loop

```
Loop condition: i >= 0 || j >= 0
  → -1 >= 0 || -1 >= 0 → false || false → EXIT LOOP

Check remaining carry:
  → carry = 1 ≠ 0
  → sb.append(1) → sb = "001"
```

### Reverse & Output

```
Before reverse: sb = "001"
After reverse:  sb.reverse() = "100"
Output:         "100" ✓

Decimal verification: 3 + 1 = 4 ✓
```

---

## Visual Trace: "1010" + "1011"

```
String a: "1010" (indices: 0=1, 1=0, 2=1, 3=0)
String b: "1011" (indices: 0=1, 1=0, 2=1, 3=1)

Starting from right: i=3, j=3

i=3 j=3: a[3]='0' + b[3]='1' + c=0 = 1 → digit=1, c=0, sb="1"
i=2 j=2: a[2]='1' + b[2]='1' + c=0 = 2 → digit=0, c=1, sb="10"
i=1 j=1: a[1]='0' + b[1]='0' + c=1 = 1 → digit=1, c=0, sb="101"
i=0 j=0: a[0]='1' + b[0]='1' + c=0 = 2 → digit=0, c=1, sb="1010"
Loop ends: carry=1 → sb="10101"
Reverse: "10101" ✓
```

---

## Two-Pointer Pattern Used

This solution uses the **Two-Pointer Approach** starting from the END:

```java
int i = a.length() - 1;  // Start at end
int j = b.length() - 1;  // Start at end

while(i >= 0 || j >= 0) {  // Continue while pointers in range
    // Process current digits
    if(i >= 0) {...}
    if(j >= 0) {...}
    
    i--;  // Move left
    j--;  // Move left
}
```

**Why Not Use Regular Iteration?**
- We need to align digits by their position
- Strings may have different lengths
- Two-pointer handles mismatched lengths elegantly

---

## Why Convert Char to Int?

```java
// Convert '0' to 0, '1' to 1
int digit = a.charAt(i) - '0';

Explanation:
  ASCII value of '0' = 48
  ASCII value of '1' = 49
  
  '0' - '0' = 48 - 48 = 0 ✓
  '1' - '0' = 49 - 48 = 1 ✓
```

---

## Comparison: Different Approaches

| Approach | Method | Pros | Cons |
|----------|--------|------|------|
| **String Build (Our Solution)** | Iterate right-to-left, build result | Simple, intuitive | Must reverse |
| **Array Method** | Build array, convert to string | No reverse needed | More lines of code |
| **Recursive** | Recursively add from right | Elegant | Stack overhead |
| **BigInteger** | Use built-in class | Handles large numbers | Overkill for this problem |

---

## Complexity Analysis

### Time Complexity: **O(max(n, m))**
- `n` = length of string a
- `m` = length of string b
- Loop runs max(n, m) times (length of longer string)
- Each iteration: O(1) operations
- Reverse: O(max(n, m))
- **Total: O(max(n, m))**

### Space Complexity: **O(max(n, m))**
- StringBuilder stores result string
- Result length ≈ max(n, m) + 1 (at most one extra digit for carry)
- Result string: O(max(n, m))
- Pointers/variables: O(1)
- **Total: O(max(n, m))**

---

## Edge Cases Handled

| Input | Output | Explanation |
|-------|--------|-------------|
| "0", "0" | "0" | Both zero |
| "1", "1" | "10" | Sum produces carry |
| "1111", "1111" | "11110" | Multiple carries |
| "0", "1" | "1" | One is zero |
| "11111111", "1" | "100000000" | Carries propagate fully |
| "1", "10" | "11" | Different lengths |

---

## Common Mistakes to Avoid

```java
// ❌ WRONG: Forget to handle both strings
for(int i = 0; i < a.length(); i++) {
    // What about string b if it's longer?
}

// ✓ CORRECT: Use || to handle both
while(i >= 0 || j >= 0) {
    if(i >= 0) sum += ...
    if(j >= 0) sum += ...
}

// ❌ WRONG: Forget final carry
if(carry == 1) sb.append(carry);  // What if carry is 1?

// ✓ CORRECT: Check if carry is non-zero
if(carry != 0) sb.append(carry);

// ❌ WRONG: Forget to reverse
return sb.toString();  // Returns backwards!

// ✓ CORRECT: Reverse the result
return sb.reverse().toString();
```

---

## Key Insights

✅ **Right-to-Left Processing:** Matches how we do manual addition  
✅ **Modulo for Digit:** `sum % 2` extracts the binary digit  
✅ **Division for Carry:** `sum / 2` extracts the carry  
✅ **Two-Pointer for Length Mismatch:** Elegantly handles different string lengths  
✅ **Reverse at End:** Result is built backwards, must be reversed  

---

## Real-World Applications

| Application | Example |
|-------------|---------|
| **Computer Arithmetic** | CPU adds binary numbers |
| **Networking** | IP address calculations (binary) |
| **Cryptography** | Bit manipulation operations |
| **Digital Logic** | Circuit addition operations |
| **Embedded Systems** | Low-level binary operations |

---

## Algorithm Summary

```
AddBinaries(string a, string b):
  1. Initialize: i = a.length()-1, j = b.length()-1, carry = 0
  2. While i >= 0 or j >= 0:
     a. sum = carry
     b. If i >= 0: sum += a[i] - '0', i--
     c. If j >= 0: sum += b[j] - '0', j--
     d. Append (sum % 2) to result
     e. carry = sum / 2
  3. If carry != 0: Append carry
  4. Reverse result
  5. Return reversed string
```

This elegant solution mimics how we manually add binary numbers, processing from right to left with careful carry propagation! 🔢
