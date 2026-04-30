import java.util.*;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);

            if (map.containsKey(cur)) {
                left = Math.max(left, map.get(cur)+1);
                // 포함할지(left), 아니면 이동시킬지(map.get(cur)+1)
            }
            // 현재 char를 포함하는 substring에 대해 max 갱신
            int len = i - left + 1;
            map.put(cur, i);
            max = Math.max(len, max);
        }
        return max;
    }
}