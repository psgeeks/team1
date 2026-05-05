class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }
            dq.offer(i);
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }
            if (i >= k - 1) {
                result[idx++] = nums[dq.peekFirst()];
            }
        }
        return result;
    }
}