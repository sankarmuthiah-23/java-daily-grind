package java;

import java.util.LinkedList;
import java.util.Queue;

public class LongestSubString {

//    Given a string s, find the length of the longest substring without duplicate characters.
//    Example 1:
//    Input: s = "abcabcbb"
//    Output: 3
//    Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.

    public int lengthOfLongestSubstring(String s) {
        Queue<Character> queue = new LinkedList<>();
        int start_index = 0;
        int length_str = 0;
        while(start_index < s.length()){
            char c = s.charAt(start_index);
            if(queue.contains(c) ){
                queue.poll();
            }else{
                queue.add(c);
                length_str = queue.size() > length_str ? queue.size() : length_str;
                start_index++;
            }
        }
        return length_str;
    }

    public static void main(String args[]){
        LongestSubString ls = new LongestSubString();
        ls.lengthOfLongestSubstring("abcabcbb");
    }
}
