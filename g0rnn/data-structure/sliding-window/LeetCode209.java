class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;

        int left = 0, right = 0;
        int min = n+1;
        int sum = 0;
        while (true) {

            while (right < n && sum < target) {
                sum += nums[right];
                right++;
            }

            if (sum < target) break;

            while (left <= right && sum >= target) {
                min = Math.min(min, right - left);
                sum -= nums[left];
                left++;
            }
            
            if (right == n && sum < target) break;
        }

        if (min == n+1) return 0;
        return min;
    }
}