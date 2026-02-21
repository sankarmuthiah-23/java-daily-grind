package code.in.java;

import java.util.LinkedList;
import java.util.List;

public class LetterCombination {

//    Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
//
//    A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
//
//    Input: digits = "23"
//    Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]



    public static void main(String[] args) {
        String digits = "234";
        LetterCombination lc = new LetterCombination();
        List<String> result_list = lc.letterCombinations(digits);
        System.out.println(result_list);
    }

    public List<String> letterCombinations(String digits) {
        LinkedList<String> li = new LinkedList<>();
        if ( digits.length() == 0) return li;
        li.add("");
        String[] letters_map = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        for( int i=0; i<digits.length(); i++){
            while( li.peek().length() == i){
                String result_string = li.remove();
                for( char c : letters_map[digits.charAt(i) - '0'].toCharArray()){
                    li.add(result_string + c);
                }
            }
        }
        return li;



    }
}
