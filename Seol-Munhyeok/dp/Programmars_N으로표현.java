import java.util.*;

class Solution {
    static int parse(int N, int repeat) {
        int num = 0;
        for (int i = 0; i < repeat; i++) {
            num *= 10;
            num += N;
        }
        return num;
    }
    
    public int solution(int N, int number) {
        int answer = 0;
        Set<Integer>[] dp = new HashSet[9];  // dp[k] : N을 k번 사용해서 만들 수 있는 수 저장
        for (int k = 1; k <= 8; k++) {
            // 이어붙인 수 추가
            dp[k] = new HashSet<>();
            dp[k].add(parse(N, k));
            
            // dp[k] = dp[i] (연산) dp[k-i]
            for (int i = 1; i < k; i++) {
                for (int a : dp[i]) {
                    for (int b : dp[k - i]) {
                        dp[k].add(a + b);
                        dp[k].add(a - b);
                        dp[k].add(a * b);
                        if (b != 0) dp[k].add(a / b);
                    }
                }
            }
            
            if (dp[k].contains(number)) return k;
        }
        
        return -1;
    }
}