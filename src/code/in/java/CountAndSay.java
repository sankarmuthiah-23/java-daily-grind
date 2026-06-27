package code.in.java;

public class CountAndSay {

    /**
     * The count-and-say sequence is a sequence of digit strings defined by the recursive formula:
     * countAndSay(1) = "1"
     * countAndSay(n) is the run-length encoding of countAndSay(n - 1).
     * Run-length encoding (RLE) is a string compression method that works by replacing each maximal group of consecutive identical characters with the concatenation of the length of the group followed by the character itself. For example, to compress the string "3322251" we replace "33" with "23", replace "222" with "32", replace "5" with "15", and replace "1" with "11". Thus the compressed string becomes "23321511".
     * Given a positive integer n, return the nth element of the count-and-say sequence.
     * Example 1:
     * Input: n = 4
     * Output: "1211"
     * Explanation:
     * countAndSay(1) = "1"
     * countAndSay(2) = RLE of "1" = "11"
     * countAndSay(3) = RLE of "11" = "21"
     * countAndSay(4) = RLE of "21" = "1211"
     * @param args
     * this is like reading from left to right and counting the number of digits and then saying the digit. For example, 1 is read as "one 1" or 11. 11 is read as "two 1s" or 21. 21 is read as "one 2, then one 1" or 1211.
     */

    public static void main(String[] args) {
        CountAndSay cs = new CountAndSay();
        int n = 5;
        String result = cs.countAndSay(n);
        System.out.println(result);
    }



    public String countAndSay(int n) {

        String result = "1";

        for(int i=2; i<= n; i++){

            StringBuilder sb = new StringBuilder();
            int pointer = 0;

            while (pointer < result.length()){
                char currentDigit = result.charAt(pointer);
                int count = 0;
                while( pointer < result.length() && result.charAt(pointer) == currentDigit){
                    count++;
                    pointer++;
                }
                sb.append(count);
                sb.append(currentDigit);
            }
            result = sb.toString();
        }
        return result;

    }
}
