import java.util.*;
/**
LRU(Least Recently Used) 총 실행시간 출력
0 <= cacheSize <= 30, cities.length = 100_000
도시 이름은 공백, 숫자, 특수문자 등이 없는 영문자, 대소문자 구분 안함, <= 20
    모든 이름을 소문자로 전처리
*/
class Solution {
    public int solution(int cacheSize, String[] cities) {   
        // 예외 처리
        if (cacheSize == 0) return 5 * cities.length;  
        
        LinkedList<String> cache = new LinkedList<>();
        int answer = 0;
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i].toLowerCase();
            if (!cache.contains(city)) {
                answer += 5;
                if (cache.size() == cacheSize) cache.pollFirst();
            } else {
                answer += 1;
                cache.remove(city);  // O(N)    
            }
            cache.addLast(city);
        }
         
        return answer;
    }
}