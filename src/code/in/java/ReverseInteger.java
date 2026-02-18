package code.in.java;

public class ReverseInteger {

    /**
     * Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.
     * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
     * Example 1:
     * Input: x = 123
     * Output: 321
     * Example 2:
     * Input: x = -123
     * Output: -321
     * @param args
     */

    public static void main(String args[]){
        int num = -123;
        int numToProcess = (num < 0) ? -num : num;
        int total = 0;
        while( numToProcess > 0){
            int rem = numToProcess % 10;
            numToProcess = numToProcess/10;
            total = (total * 10) + rem;

        }
        if( num < 0){
            System.out.println(-1 * total);
        }else{
            System.out.println(total);
        }

    }
}
