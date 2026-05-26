class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int[] sums = new int[n+1];

        for(int i = 1; i <= n; i++) {
            sums[i] = sums[i-1] + nums[i-1];
        }

        int min = n+1;
        for (int i = 0; i <= n; i++) {
            int toFind = sums[i] + target;

            int idx = Arrays.binarySearch(sums, toFind);

            if (idx < 0) idx = -idx - 1;

            if (idx <= n) min = Math.min(min, idx - i);
        }

        return min == n+1 ? 0 : min;
    }
}