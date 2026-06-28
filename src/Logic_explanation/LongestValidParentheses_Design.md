# Longest Valid Parentheses - Design Document

## Problem Statement

Given a string containing only `'('` and `')'` characters, find the **length of the longest valid (well-formed) parentheses substring**.

**What is "Valid Parentheses"?**
- Every opening bracket `'('` has a matching closing bracket `')'`
- Brackets are in the correct order (no crossing)
- Examples: `"()"`, `"(())"`, `"()()"`

**Important:** We need the **length** of the longest contiguous valid substring, not count all valid parentheses.

---

## Example Walkthroughs

### Example 1: ")()())"

```
String:  ) ( ) ( ) )
Index:   0 1 2 3 4 5

Substrings:
- ")" at 0 → Invalid
- "()" at 1-2 → Valid (length 2)
- "()" at 3-4 → Valid (length 2)
- ")()())" at 0-5 → Invalid

But "()()" from 1-4 → Valid (length 4)

Output: 4 (substring "()()" is longest valid)
```

### Example 2: "(()"

```
String:  ( ( )
Index:   0 1 2

Substrings:
- "()" at 1-2 → Valid (length 2)
- "(()" → Invalid (extra opening bracket)

Output: 2 (substring "()" is longest valid)
```

### Example 3: ""

```
String:  (empty)
Output:  0 (no characters)
```

### Example 4: "()(()"

```
String:  ( ) ( ( )
Index:   0 1 2 3 4

Substrings:
- "()" at 0-1 → Valid (length 2)
- "()" at 3-4 → Invalid (no matching open for index 2)
- "()(()" → Invalid

Output: 2
```

---

## Why This Problem is Tricky

❌ **Naive Approach:** Check every substring for validity
- Time: O(n³) - too slow!

❌ **Greedy Approach:** Track open/close counts
- Fails on strings like `"()(()"`
- Can't handle unmatched brackets

✅ **Stack-Based Approach:** Track indices of unmatched brackets
- Time: O(n) - linear time!
- Elegant and correct

---

## Algorithm Approach: Stack with Index Tracking

### The Key Insight

Instead of storing characters, **store indices** of unmatched brackets:

```
If we know the indices of all unmatched '(' and ')',
we can calculate the length of valid substrings between them.
```

### Why Initialize Stack with -1?

```java
Stack<Integer> stack = new Stack<>();
stack.push(-1);  // Base index for calculating lengths
```

The `-1` acts as a **boundary marker**:
- Provides a base to calculate distance from
- Ensures we can always pop when seeing `')'`
- Makes length calculation: `i - stack.peek()`

---

## Algorithm Flow

```
1. Push -1 as base index
2. For each character at index i:
   a. If '(': Push index i
   b. If ')':
      - Pop the stack
      - If stack is empty: Push i (this ')' is unmatched, marks invalid position)
      - Else: Calculate length = i - stack.peek()
      - Track maximum length
3. Return maximum length found
```

---

## Code Breakdown

### Initialization
```java
int maxLength = 0;           // Track longest valid substring
if(s.length() == 0) 
    return maxLength;        // Edge case: empty string

Stack<Integer> stack = new Stack<>();
stack.push(-1);              // Base marker for length calculation
```

### Main Loop - Processing Each Character

```java
for (int i = 0; i < s.length(); i++) {
    if(s.charAt(i) == '(') {
        // Potential opening bracket
        stack.push(i);
    }
    else {  // s.charAt(i) == ')'
        // Handle closing bracket
        stack.pop();
        if(stack.isEmpty()) {
            // This ')' has no matching '('
            stack.push(i);  // Mark as invalid position
        } 
        else {
            // Valid substring exists
            maxLength = Math.max(maxLength, i - stack.peek());
        }
    }
}
```

### Processing Decision Tree

```
Character is '('?
  ├─ YES: Push index onto stack
  │       (Store for potential matching)
  │
  └─ NO: Character is ')'
         ├─ Pop from stack
         │
         ├─ Is stack empty?
         │   ├─ YES: Push i (unmatched ')')
         │   │      Stack=[−1]→[]→[i]
         │   │
         │   └─ NO: Calculate length
         │          length = i - stack.peek()
         │          Update maxLength
         │
```

---

## Step-by-Step Execution: ")()())"

### Initial State
```
String: ) ( ) ( ) )
Index:  0 1 2 3 4 5

Stack: [-1]
maxLength: 0
```

### Step 1: i=0, char=')'
```
Action: Character is ')'
  → Pop: Stack[-1] removed → Stack: []
  → Check: stack.isEmpty() == true
  → Push i: Stack.push(0) → Stack: [0]

Stack: [0]
maxLength: 0
Reason: This ')' has no matching '(', marks invalid position
```

### Step 2: i=1, char='('
```
Action: Character is '('
  → Push index 1 → Stack: [0, 1]

Stack: [0, 1]
maxLength: 0
```

### Step 3: i=2, char=')'
```
Action: Character is ')'
  → Pop: Stack[1] removed → Stack: [0]
  → Check: stack.isEmpty() == false
  → Calculate length: i - stack.peek() = 2 - 0 = 2
  → maxLength = max(0, 2) = 2

Stack: [0]
maxLength: 2
Reason: Found valid substring "()" from index 1-2
```

### Step 4: i=3, char='('
```
Action: Character is '('
  → Push index 3 → Stack: [0, 3]

Stack: [0, 3]
maxLength: 2
```

### Step 5: i=4, char=')'
```
Action: Character is ')'
  → Pop: Stack[3] removed → Stack: [0]
  → Check: stack.isEmpty() == false
  → Calculate length: i - stack.peek() = 4 - 0 = 4
  → maxLength = max(2, 4) = 4

Stack: [0]
maxLength: 4
Reason: Found valid substring from after index 0 to index 4
        This is "()()" with length 4
```

### Step 6: i=5, char=')'
```
Action: Character is ')'
  → Pop: Stack[0] removed → Stack: []
  → Check: stack.isEmpty() == true
  → Push i: Stack.push(5) → Stack: [5]

Stack: [5]
maxLength: 4
Reason: This ')' is unmatched, marks another invalid position
```

### Final Result
```
Stack: [5]
maxLength: 4
Output: 4
```

---

## Visual Stack State Progression

```
String: ) ( ) ( ) )
Index:  0 1 2 3 4 5

i=0  char=')'  │ Stack: [-1] → [] → [0]
                │ maxLen: 0
                │
i=1  char='('  │ Stack: [0] → [0,1]
                │ maxLen: 0
                │
i=2  char=')'  │ Stack: [0,1] → [0]
                │ maxLen: 0 → 2
                │ (calculated: 2 - 0 = 2)
                │
i=3  char='('  │ Stack: [0] → [0,3]
                │ maxLen: 2
                │
i=4  char=')'  │ Stack: [0,3] → [0]
                │ maxLen: 2 → 4
                │ (calculated: 4 - 0 = 4)
                │
i=5  char=')'  │ Stack: [0] → [] → [5]
                │ maxLen: 4
                ▼
            Result: 4
```

---

## Why The Algorithm Works

### Concept 1: Stack Tracks Unmatched Positions

```
Stack contains indices of:
- The base marker (-1)
- Unmatched '(' waiting for ')'
- The latest unmatched ')' that couldn't find '('
```

### Concept 2: Length Calculation

When we find a `')'` that matches with a `'('`:
```
After popping the '(':
  stack.peek() = index of position BEFORE valid substring starts
  i = current index (end of valid substring)
  length = i - stack.peek()

Example: "()()"
         0123
After processing index 3 (')'):
  Pop removes index 2 ('(')
  stack.peek() = 0 (the position before valid substring)
  length = 3 - 0 = 3... wait, that's wrong!

Actually for "()()" at indices 0,1,2,3:
  i=1 char=')', pop 0, stack has -1, len = 1 - (-1) = 2 ✓
  i=3 char=')', pop 2, stack has -1, len = 3 - (-1) = 4 ✓
```

### Concept 3: Handling Unmatched Brackets

```
When we encounter ')' with empty stack:
  This ')' cannot be matched with any previous '('
  Push its index to use as new base for future valid substrings
  
Example: ")()())"
  Index 0: ')' unmatched → push 0
  Index 1-2: "()" valid
  Index 3-4: "()" valid
  Together with base at 0, we get length from 1 to 4 = 4
```

---

## Comparison: Different Approaches

| Approach | Method | Time | Space | Issues |
|----------|--------|------|-------|--------|
| **Brute Force** | Check every substring | O(n³) | O(1) | Too slow |
| **DP** | Build table of valid lengths | O(n) | O(n) | Complex logic |
| **Stack** | Track indices of unmatched | O(n) | O(n) | Simple & elegant ✓ |

---

## Why Not Just Count Matches?

```
❌ String: "()(())"
   If we only count: '(' = 3, ')' = 3 → Looks balanced
   But it's NOT valid everywhere!
   
✓ Our algorithm correctly finds: length = 4 (substring "()()")
```

---

## Common Edge Cases

| Input | Stack Evolution | Output | Explanation |
|-------|-----------------|--------|-------------|
| `""` | Empty check | 0 | No characters |
| `"("` | [-1, 0] | 0 | No closing bracket |
| `")"` | [-1] → [0] | 0 | No opening bracket |
| `"()"` | [-1, 0] → [-1], len=2 | 2 | Single valid pair |
| `")("` | [0, 1] | 0 | Wrong order |
| `"(())"` | Multiple matches | 4 | Nested valid |
| `"()(())"` | Multiple matches | 6 | Complex pattern |

---

## Complexity Analysis

### Time Complexity: **O(n)**
- Single pass through string (one loop from 0 to n)
- Stack operations (push/pop) are O(1) each
- Total: O(n)

### Space Complexity: **O(n)**
- Stack can hold up to n indices in worst case
- Worst case: all opening brackets like "((((("
- Stack would contain: [-1, 0, 1, 2, 3, 4]

---

## Stack vs Alternative Approaches

### Two-Pass Approach (Also O(n) but harder to understand)
```java
// Left to right: count matching
// Right to left: count matching
// More intuitive but requires two passes
```

### Dynamic Programming Approach (O(n) time, O(n) space)
```java
// dp[i] = length of valid parentheses ending at i
// More space and setup required
```

### Stack Approach (Our solution)
```java
// Single pass, elegant, space-efficient
// Tracks indices smartly
```

---

## Key Insights

✅ **Stack for Matching Problems:** Ideal for bracket matching and nesting  
✅ **Index Tracking:** Store indices, not characters, for length calculations  
✅ **Base Marker:** The -1 is crucial for calculating valid substring lengths  
✅ **Unmatched ')' Handling:** Becomes new base for future valid calculations  
✅ **Single Pass:** Linear time complexity with just one iteration  

---

## Real-World Applications

| Application | Example |
|-------------|---------|
| **Code Parsing** | Find valid code blocks in source files |
| **HTML/XML Validation** | Verify matching tags |
| **Expression Evaluation** | Check balanced formulas |
| **Compiler Design** | Syntax validation |

---

## Algorithm Summary

```
LongestValidParentheses(string s):
  1. Initialize: maxLength = 0, stack = [-1]
  2. For each character at index i:
     a. If '(': Push i
     b. If ')':
        - Pop from stack
        - If stack empty: Push i (unmatched)
        - Else: maxLength = max(maxLength, i - stack.top)
  3. Return maxLength
```

The elegance of this solution lies in how it transforms a seemingly complex problem (finding longest valid substring) into a simple stack manipulation problem! 🎯
