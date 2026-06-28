# Combination Sum - Design Document

## Problem Statement

Given an array of **distinct integers** and a **target sum**, find all unique combinations where the chosen numbers add up to the target.

**Key Constraints:**
- The same number can be used **unlimited times**
- Numbers are distinct in the input array
- Return all unique combinations (combinations with different frequencies count as different)
- Result order doesn't matter

---

## Example Walkthrough

### Example 1
```
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
```

**Explanation:**
- `[2,2,3]` → 2 + 2 + 3 = 7 ✓ (2 used twice)
- `[7]` → 7 = 7 ✓
- Other combinations like [3,4] don't work (4 not in array)

### Example 2
```
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
```

---

## Algorithm Approach: Backtracking

The solution uses **backtracking** (depth-first search) to explore all possible combinations.

### How It Works

```
1. Start with an empty combination and target sum = 7
2. Try adding each candidate:
   - Add 2: remaining = 5, current = [2]
     - Add 2 again: remaining = 3, current = [2,2]
       - Add 2 again: remaining = 1, current = [2,2,2] ✗
       - Add 3: remaining = 0, current = [2,2,3] ✓ (FOUND!)
   - Add 3: remaining = 4, current = [3]
     - Continue exploring...
   - Add 7: remaining = 0, current = [7] ✓ (FOUND!)
3. Backtrack and try other paths
```

### Key Concepts

| Concept | Purpose |
|---------|---------|
| **remaining** | Current target sum left to achieve |
| **start** | Current index to avoid duplicates (can reuse same index) |
| **current** | Building the combination step-by-step |
| **result** | Stores all valid combinations found |

---

## Code Breakdown

### Main Method
```java
int[] candidates = {2, 3, 6, 7};
int target = 7;
List<List<Integer>> result = new ArrayList<>();
cs.combinationSum(candidates, target, 0, new ArrayList<>(), result);
```

- Initializes the algorithm with start index `0`
- Uses empty `ArrayList<>()` to build combinations
- Stores results in `result` list

### Recursive Function

```java
private void combinationSum(int[] candidates, int remaining, int start, 
                           List<Integer> current, List<List<Integer>> result)
```

**Parameters:**
- `candidates[]` - Array of numbers to choose from
- `remaining` - Current sum we still need to achieve
- `start` - Index to start searching from (avoids duplicate combinations)
- `current` - List building the current combination
- `result` - Stores all valid combinations

---

## Step-by-Step Execution

### Base Cases (When to Stop Recursion)

1. **If `remaining < 0`:**
   - Sum exceeded target → Stop (no valid combination)
   - Return without adding to result

2. **If `remaining == 0`:**
   - We found a valid combination! ✓
   - Add a copy of `current` to result
   - Return

### Recursive Case (Explore Possibilities)

```java
for (int i = start; i < candidates.length; i++) {
    current.add(candidates[i]);                           // Add number
    combinationSum(candidates, remaining - candidates[i], i, current, result);  // Recurse
    current.remove(current.size() - 1);                   // Remove number (backtrack)
}
```

**This is the Backtrack Pattern:**
1. **Choose** → Add candidate to current combination
2. **Explore** → Recursively try to complete the combination
3. **Unchoose** → Remove the candidate (backtrack) and try next option

---

## Why `start = i` (Not `start = i + 1`)?

In standard combination problems, we use `start = i + 1` to avoid duplicates:
```java
// Standard: Each number used only once
combinationSum(candidates, remaining - candidates[i], i + 1, current, result);
```

This problem uses `start = i`:
```java
// This problem: Each number can be reused
combinationSum(candidates, remaining - candidates[i], i, current, result);
```

**Difference:**
- `i + 1` → Skips to next number (prevents reusing same number)
- `i` → Can use same number again (allows unlimited reuse)

---

## Example Trace: candidates = [2,3,6,7], target = 7

```
combinationSum([2,3,6,7], 7, 0, [], result)
├─ i=0 (candidate=2)
│  └─ Add 2: current=[2], remaining=5
│     combinationSum([2,3,6,7], 5, 0, [2], result)
│     ├─ i=0 (candidate=2)
│     │  └─ Add 2: current=[2,2], remaining=3
│     │     ├─ i=0 (candidate=2): remaining=1 (no solution)
│     │     ├─ i=1 (candidate=3): remaining=0 ✓ FOUND: [2,2,3]
│     │     └─ ...
│     └─ ...
├─ i=1 (candidate=3)
│  └─ Continue exploring...
├─ i=2 (candidate=6)
│  └─ 6 > 7, no solutions
└─ i=3 (candidate=7)
   └─ Add 7: current=[7], remaining=0 ✓ FOUND: [7]
```

---

## Complexity Analysis

### Time Complexity: **O(N^T/M)**
- `N` = length of candidates array
- `T` = target value
- `M` = minimum value in candidates (determines depth)
- In worst case (candidates = [1], target = large) → exponential
- Practical: O(number of combinations × length of each combination)

### Space Complexity: **O(T/M)**
- Recursion stack depth = at most `T/M` (if we always pick smallest number)
- Storing all results requires additional space proportional to output size

---

## Key Takeaways

✅ **Backtracking Pattern:** Choose → Explore → Unchoose  
✅ **Reusable Numbers:** Use `start = i` to allow same number multiple times  
✅ **Early Termination:** Check `remaining < 0` to prune invalid branches  
✅ **Valid Combination:** When `remaining == 0`, save a copy to result  

---

## Edge Cases Handled

| Input | Output | Reason |
|-------|--------|--------|
| `[2], target=1` | `[]` | No combination possible |
| `[1], target=5` | `[[1,1,1,1,1]]` | One solution with repeated 1s |
| Empty candidates | `[]` | No numbers to choose |
