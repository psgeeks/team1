class Solution {
    public int solution(int[][] cost, int[][] hint) {
        int n = cost.length;
        int minAnswer = 1_000_000_000;
        
        for (int mask = 0; mask < (1 << (n - 1)); mask++) {  // 1. 힌트 구매 상태
            int totalCost = 0;
            int[] hintCnt = new int[n];
            
            for (int i = 0; i < n; i++) {   // 2. 각 스테이지마다 확인
                totalCost += cost[i][Math.min(hintCnt[i], n - 1)];
                if ((mask & (1 << i)) != 0) {   // 힌트 구매
                    totalCost += hint[i][0];
                    for (int j = 1; j < hint[0].length; j++) {  // 3. 힌트 개수 증가
                        hintCnt[hint[i][j] - 1]++;
                    }
                }
            }
            minAnswer = Math.min(minAnswer, totalCost);
        }
        return minAnswer;
    }
}