package 정렬;

class Solution {
    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        for(int i = 0; i < len; i++){
            while(nums[i] > 0 && nums[i]-1 < len && nums[i] != nums[nums[i]-1]){
                swap(i, nums);
            }
        }
        for(int i = 0; i < nums.length; i++){
            if(nums[i] != i+1) return i+1;
        }
        return nums.length+1;
    }
    public void swap(int i, int[] nums){
        int tmp = nums[i];
        nums[i] = nums[tmp-1];
        nums[tmp-1] = tmp;
    }
}