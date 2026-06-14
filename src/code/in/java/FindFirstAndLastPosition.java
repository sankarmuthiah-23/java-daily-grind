package code.in.java;


/***
 * Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
 * If target is not found in the array, return [-1, -1].
 * You must write an algorithm with O(log n) runtime complexity.
 * Example 1:
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 * Example 2:
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 * Example 3:
 * Input: nums = [], target = 0
 * Output: [-1,-1]
 * Input: nums= [1,2,2,3,4,4,4], target=4
 * Output: [4,6]
 */
public class FindFirstAndLastPosition {

    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 8;
        FindFirstAndLastPosition findFirstAndLastPosition = new FindFirstAndLastPosition();
        int[] result = findFirstAndLastPosition.searchRange(nums, target);
        System.out.println("[" + result[0] + ", " + result[1] + "]");
    }

    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        if (nums.length <= 0) return result;
        int last_start_occurences = -1;
        int last_end_occurences = -1;
        for (int i = 0; i <= nums.length / 2; i++) {
            int start = i;
            int end = (nums.length) - (i + 1);

            if (nums[start] == target) {

                result[0] = (result[0] != -1) ? result[0] : start;
                last_start_occurences = start;
            }

            if (nums[end] == target) {
                result[1] = (result[1] != -1) ? result[1] : end;
                last_end_occurences = end;
            }
        }
        if (result[0] == -1 && last_end_occurences != -1) {
            result[0] = last_end_occurences;
        }
        if (result[1] == -1 && last_start_occurences != -1) {
            result[1] = last_start_occurences;
        }
        return result;


    }
}
