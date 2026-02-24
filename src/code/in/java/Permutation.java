package code.in.java;

import java.util.ArrayList;
import java.util.List;

public class Permutation {

//    Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
//    Input: nums = [1,2,3]
//    Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
//    https://algomap.io/problems/permutations

    public static void main(String args[]){
        List<List<Integer>> result = new ArrayList<>();
        int[] nums = {1,2,3};
        Permutation permutation = new Permutation();
        if( nums.length == 0){
            return;
        }
        List<Integer> list = new ArrayList<>();
        permutation.buildPermutation(nums, list, result, new boolean[nums.length]);
    }

    public void buildPermutation(int[] nums, List<Integer> list,List<List<Integer>> result, boolean[] visited){


        if( list.size() == nums.length){
            result.add(new ArrayList<>(list));
            return;
        }
        for(int i =0; i< nums.length; i++){
            if( visited[i] ){
                continue;
            }
            list.add(nums[i]);
            visited[i] = true;
            buildPermutation(nums, list,result, visited);
            list.remove(list.size()-1);
            visited[i] = false;
        }
    }

}
