package code.in.java;

import java.util.Stack;

public class LongestValidParanthesis {

    public static void main(String[] args) {
        LongestValidParanthesis lp   = new LongestValidParanthesis();
        String s = ")()())";
        int result = lp.longestValidParentheses(s);
        System.out.println(result);
    }



    public int longestValidParentheses(String s) {
        int maxLength = 0;
        if(s.length() == 0) return maxLength;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);

        for (int i=0; i< s.length(); i++){
            if( s.charAt(i) == '('){
                stack.push(i);
            }
            else{
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLength = Math.max(maxLength, i - stack.peek());
                }
                
            }
        }
        return  maxLength;


    }
}
