import java.util.*;
/**
return (1, 1) -> (n, m) 으로 이동할 수 있도록 격자에 선로를 놓는 방법의 수
    불가능 = 0
선로의 종류 7가지
    모든 선로를 1번 이상 지나가야 한다.
    3번 선로는 상하좌우 모든 방향으로 연결되어 있어야 한다.
격자칸이 빈칸인 경우 0, 선로인 경우 각 선로의 번호(1 ~ 7), 장애물인 경우 -1
n × m ≤ 20
선로를 하나 두면 그 선로 상하좌우 중 갈 수 있는 칸이 제한됨 -> 가지치기

가능한 선로 조건 : 
1. DFS 과정에서 모든 선로를 방문해야 함
    3번 선로는 2번 방문하고, 나머지 선로는 1번만 방문해야 함
2. 각 선로는 모두 연결되어 있어야 함
    특히 3번 선로는 좌우방향으로도 연결되고, 상하방향으로도 연결되어 있어야 함
*/
class Solution {
                    // 상 우 하 좌
                    // 0  1  2  3
    final int[] dy = {-1, 0, 1, 0};
    final int[] dx = {0, 1, 0, -1};
    final int[] opposite = {2, 3, 0, 1};
    final int U = 0, R = 1, D = 2, L = 3;
    int[] railDir = {
        0,
        (1 << R) | (1 << L),          // 1010
        (1 << U) | (1 << D),          // 0101
        (1 << U) | (1 << R) | (1 << D) | (1 << L),  // 1111
        (1 << U) | (1 << L),          // 1001
        (1 << U) | (1 << R),          // 0011
        (1 << D) | (1 << R),          // 0110
        (1 << D) | (1 << L)           // 1100
    };
    int n, m, answer;
    int[][] grid, visited;
    
    public int solution(int[][] grid) {
        this.grid = grid;
        n = grid.length;          // 세로
        m = grid[0].length;       // 가로
        visited = new int[n][m];  // 방문한 횟수 저장
        
        answer = 0;
        visited[0][0]++;
        dfs(0, 1, L);
        return answer;
    }
    
    // requiredDir : 이전 위치에서 (y, x) 위치에 선로를 둘 때 필요한 방향
    private void dfs(int y, int x, int requiredDir) {
        if (!inRange(y, x) || grid[y][x] == -1) return;
        if (checkVisited(y, x)) return;
        
        // [1] 이미 선로가 놓여있다면, 이전 선로와 연결성 확인
        if (grid[y][x] != 0) {
            int railBit = railDir[grid[y][x]];
            if ( (railBit & (1 << requiredDir)) == 0 ) return;
        }
        
        visited[y][x]++;
        
        // [2] 도착 지점이면 올바른 경로인지 확인
        if (y == n - 1 && x == m - 1) {
            if (checkPath()) answer++;
            visited[y][x]--;
            return;
        }
        
        // [3] 이미 놓여있는 선로 다음에 연결될 수 있는 방향으로 dfs 전파
        if (grid[y][x] != 0) {
            checkRail(y, x, grid[y][x], requiredDir);
        } else {   // [4] 선로가 없으면 가능한 선로를 배치하고 다음 방향으로 전파
            for (int rail = 1; rail <= 7; rail++) {
                int railBit = railDir[rail];
                if ( (railBit & (1 << requiredDir)) == 0 ) continue;
                grid[y][x] = rail;
                checkRail(y, x, rail, requiredDir); 
            }
            grid[y][x] = 0;
        }
        
        // 상태 복구
        visited[y][x]--;
    }  
    
    // 3번 선로는 2번, 나머지 선로는 1번 방문해야 함.
    private boolean checkPath() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0 || grid[i][j] == -1) continue;
                if (grid[i][j] == 3) {
                    if (visited[i][j] != 2) return false;
                } else {
                    if (visited[i][j] != 1) return false;
                }
            }
        }
        return true;
    }
    
    private void checkRail(int y, int x, int railNum, int requiredDir) {
        for (int d = 0; d < 4; d++) {
            if (d == requiredDir) continue;
            int railBit = railDir[railNum];
            if ( (railBit & (1 << d)) == 0 ) continue;
            // 3번 선로는 상하 또는 좌우로만 연결 가능
            if (railNum == 3) {
                if (requiredDir != opposite[d]) continue;
            }
            dfs(y + dy[d], x + dx[d], opposite[d]);
        }
    }
    
    private boolean checkVisited(int y, int x) {
        if (grid[y][x] == 3) {
            return visited[y][x] >= 2;
        }
        return visited[y][x] >= 1;
    }
    
    private boolean inRange(int y, int x) {
        return y >= 0 && y < n && x >= 0 && x < m;
    }
}