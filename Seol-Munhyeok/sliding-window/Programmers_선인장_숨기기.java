import java.util.*;

class Solution {
    final int INF = 1_000_000_000;
    int m, n, h, w;
    int[][] drops, grid, minRow;
    
    public int[] solution(int m, int n, int h, int w, int[][] drops) {
        // 변수 초기화
        this.m = m; this.n = n; this.h = h; this.w = w;
        this.drops = drops;
        grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(grid[i], INF);
        }
        for (int i = 0; i < drops.length; i++) {
            grid[drops[i][0]][drops[i][1]] = i + 1;
        }
        
        // 슬라이딩 윈도우로 부분 격자 최솟값 구하기
        minRow = getMinRow();  // minRow[m][n - w + 1]
        int[][] minCol = getMinCol();  // minCol[m - h + 1][n - w + 1]
        
        // 부분 격자 최솟값 중 최댓값이 정답
        int[] answer = new int[2];
        int maxValue = -INF;
        for (int i = 0; i < m - h + 1; i++) {
            for (int j = 0; j < n - w + 1; j++) {
                if (minCol[i][j] > maxValue) {
                    maxValue = minCol[i][j];
                    answer[0] = i;
                    answer[1] = j;
                }
            }
        }

        return answer;
    }
    
    private int[][] getMinRow() {
        int[][] minRow = new int[m][n];
        for (int r = 0; r < m; r++) {
            Deque<Integer> dq = new ArrayDeque<>();  // 인덱스만 저장
            int idx = 0;
            for (int i = 0; i < n; i++) {
                while (!dq.isEmpty() && grid[r][dq.peekLast()] >= grid[r][i]) {
                    dq.pollLast();
                }
                dq.offer(i);
                while (!dq.isEmpty() && dq.peekFirst() <= i - w) {
                    dq.pollFirst();
                }
                if (i >= w - 1) {
                    minRow[r][idx++] = grid[r][dq.peekFirst()];
                }
            }
        }
        return minRow;
    }
    
    private int[][] getMinCol() {
        int[][] minCol = new int[m][n];
        for (int c = 0; c < n - w + 1; c++) {
            Deque<Integer> dq = new ArrayDeque<>();  // 인덱스만 저장
            int idx = 0;
            for (int i = 0; i < m; i++) {
                while (!dq.isEmpty() && minRow[dq.peekLast()][c] >= minRow[i][c]) {
                    dq.pollLast();
                }
                dq.offer(i);
                while (!dq.isEmpty() && dq.peekFirst() <= i - h) {
                    dq.pollFirst();
                }
                if (i >= h - 1) {
                    minCol[idx++][c] = minRow[dq.peekFirst()][c];
                }
            }
        }
        return minCol;
    }
}