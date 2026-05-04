/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 * public int get(int index) {}
 * public int length() {}
 * }
 */

class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int n = mountainArr.length();
        int peakIdx = findPeak(mountainArr, n);

        int answer = findInAscending(mountainArr, target, 0, peakIdx);
        if (answer == -1)
            answer = findInDesending(mountainArr, target, peakIdx + 1, n - 1);

        return answer;
    }

    private int findPeak(MountainArray mountainArr, int n) {
        int left = 0;
        int right = n - 1;
        while (left < right) {
            int mid = (left + right) / 2;

            int midValue = mountainArr.get(mid);
            int nextValue = mountainArr.get(mid + 1);
            if (midValue < nextValue)
                left = mid + 1;
            else
                right = mid;
        }

        return left;
    }

    private int findInAscending(MountainArray mountainArr, int target, int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;
            int midValue = mountainArr.get(mid);

            if (midValue == target)
                return mid;

            if (midValue < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    private int findInDesending(MountainArray mountainArr, int target, int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;
            int midValue = mountainArr.get(mid);

            if (midValue == target)
                return mid;

            if (midValue < target)
                right = mid - 1;
            else
                left = mid + 1;
        }
        return -1;
    }
}