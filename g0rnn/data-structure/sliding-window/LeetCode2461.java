import java.util.*;

class Solution {
    public long maximumSubarraySum(int[] nums, int k) {
        int n = nums.length;
        long sum = 0;
        Map<Integer, Integer> map = new HashMap<>();

        long max = 0;
        for (int i= 0; i < n; i++) {
            sum += nums[i];
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);

            if (i >= k) {
                int front = nums[i-k];
                sum -= front;
                
                if (map.get(front) == 1) map.remove(front);
                else map.put(front, map.get(front) - 1);
            }

            if (i >= k-1) {
                if (map.size() == k) {
                    max = Math.max(max, sum);
                }
            }
        }

        return max;
    }
}