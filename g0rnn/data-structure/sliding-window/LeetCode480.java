import java.util.*;

class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] answer = new double[nums.length - k + 1];
        MountainQueue mq = new MountainQueue(k);

        for (int right = 0; right < nums.length; right++) {
            mq.add(nums[right]);

            if (right >= k) mq.remove(nums[right - k]);
            if (right >= k-1) answer[right - k + 1] = mq.getMedian();
        }

        return answer;
    }

    static class MountainQueue {
        // small: 최대 힙 (작은 절반 중 가장 큰 값)
        PriorityQueue<Integer> small = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        // large: 최소 힙 (큰 절반 중 가장 작은 값)
        PriorityQueue<Integer> large = new PriorityQueue<>();
        Map<Integer, Integer> delayed = new HashMap<>();
        int smallSize = 0;
        int largeSize = 0;
        boolean isOdd;

        MountainQueue(int k) {
            this.isOdd = (k % 2 != 0);
        }

        void add(int value) {
            if (small.isEmpty() || small.peek() >= value) {
                small.offer(value);
                smallSize++;
            } else {
                large.offer(value);
                largeSize++;
            }
            rebalance();
        }

        void rebalance() {
            if (smallSize > largeSize + 1){
                large.offer(small.poll());
                smallSize--;
                largeSize++;

                prune(small);
            } else if (smallSize < largeSize) {
                small.offer(large.poll());
                largeSize--;
                smallSize++;

                prune(large);
            }
        }

        // 삭제 등록된 수를 지우는거
        void prune(PriorityQueue<Integer> pq) {
            while (!pq.isEmpty()) {
                int num = pq.peek();

                if (!delayed.containsKey(num)) break; // 꺼낸게 삭제 예정이 아니라면

                delayed.put(num, delayed.get(num) - 1);

                if (delayed.get(num) == 0) delayed.remove(num);

                pq.poll();
            }
        }

        void remove(int num) {
            delayed.put(num, delayed.getOrDefault(num, 0) + 1);

            if (num <= small.peek()) {
                smallSize--;
                if (num == small.peek()) prune(small);
            } else {
                largeSize--;

                if (!large.isEmpty() && num == large.peek()) prune(large);
            }

            rebalance();
        }

        double getMedian() {
            if (isOdd) return (double) small.peek();
            return ((double) small.peek() + large.peek()) / 2.0;
        }
    }
}