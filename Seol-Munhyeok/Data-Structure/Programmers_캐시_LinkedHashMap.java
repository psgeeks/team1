import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        // 예외 처리
        if (cacheSize == 0) return 5 * cities.length;
        
        // 모든 문자 소문자로 전처리
        for (int i = 0; i < cities.length; i++) {
            cities[i] = cities[i].toLowerCase();
        }
        
        int answer = 0;
        // accessOrder = true
        // get/put으로 접근한 원소가 뒤로 이동
        LinkedHashMap<String, Integer> cache = new LinkedHashMap<>(16, 0.75f, true);
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            if (!cache.containsKey(city)) {
                answer += 5;
                if (cache.size() == cacheSize) {
                    String oldestKey = cache.keySet().iterator().next();
                    cache.remove(oldestKey);  // O(1)
                }
            } else answer += 1;
            
            cache.put(city, 1);
        }
                
        return answer;
    }
}