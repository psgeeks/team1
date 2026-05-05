import java.util.*;

class Solution {
    
    int[][] cost;
    int[][] hint;
    int n, minAnswer;
    int[] hintCount;
    
    public int solution(int[][] cost, int[][] hint) {
        this.cost = cost;
        this.hint = hint;
        n = cost.length;
        minAnswer = 1_000_000_000;
        hintCount = new int[n];  // 0-based
        
        dfs(0, 0);  // 스테이지 0-based
        return minAnswer;
    }
    
    private void dfs(int idx, int totalCost) {
        if (idx == n - 1) {
            totalCost += getSolveCost(n - 1);  // 마지막 스테이지 반영
            minAnswer = Math.min(minAnswer, totalCost);
            return;
        }
        
        // 힌트권 구매
        int k = hint[0].length - 1;
        int hintCost = hint[idx][0];
        for (int i = 1; i <= k; i++) {
            hintCount[hint[idx][i] - 1]++;
        }
        dfs(idx + 1, totalCost + hintCost + getSolveCost(idx));
        
        // 힌트권 개수 원상 복구
        for (int i = 1; i <= k; i++) {
            hintCount[hint[idx][i] - 1]--;
        }
        
        // 힌트권 구매 안함
        dfs(idx + 1, totalCost + getSolveCost(idx));
    }
    
    // 힌트권이 있다면 모두 사용
    private int getSolveCost(int idx) {
        return cost[idx][Math.min(hintCount[idx], n - 1)];
    } 
}