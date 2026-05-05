import java.util.*;

class Solution {
    final int[] dy = {-1, 0, 1, 0};
    final int[] dx = {0, 1, 0, -1};
    final int INF = 1_000_000_000;
    
    int h;         // 건물 층수
    int n, m;      // 세로, 가로
    int k;         // 패널 개수
    int ey, ex;    // 엘리베이터 좌표
    
    char[][] grid;
    int[][] panels, seqs;
    int[] pre;          // pre[i] = i번 패널을 켜기 위해 먼저 켜져 있어야 하는 패널들의 집합
    int[][] panelDist;  // panelDist[i][j] = 같은 층으로 가정 할 때, i번 패널에서 j번 패널까지의 최단 거리
    int[][] memo;
    
    public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {
        n = grid.length;
        m = grid[0].length();
        k = panels.length;
        this.h = h;
        this.grid = new char[n][];
        for (int i = 0; i < n; i++) {
            this.grid[i] = grid[i].toCharArray();
        }
        this.panels = panels;
        this.seqs = seqs;
        
        // 엘리베이터의 위치 저장
        setElevatorPos();
        
        // panelDist 배열 채우기 (엘리베이터는 0번 패널로 간주)
        panelDist = new int[k + 1][k + 1];
        for (int i = 0; i <= k; i++) {
            getPanelDist(i);
        }
        
        // pre 배열 채우기
        pre = new int[k + 1];
        for (int[] s : seqs) {
            int a = s[0];
            int b = s[1];
            pre[b] |= (1 << (a - 1));
        }
        
        memo = new int[1 << k][k + 1];
        for (int i = 0; i < (1 << k); i++) {
            Arrays.fill(memo[i], -1);
        }

        return dfs(0, 1);
    }
    
    private int dfs(int mask, int pos) {
        if (mask == (1 << k) - 1) return 0;
        
        if (memo[mask][pos] != -1) return memo[mask][pos];
        
        int ret = INF;
        
        for (int next = 1; next <= k; next++) {
            // 이미 켠 패널이면 스킵
            if ((mask & (1 << (next - 1))) != 0) continue;
            
            // 선행 조건 미충족이면 스킵
            if ((mask & pre[next]) != pre[next]) continue;
            
            int nextMask = mask | (1 << (next - 1));
            ret = Math.min(ret, getMinDist(pos, next) + dfs(nextMask, next));
        }
        
        return memo[mask][pos] = ret;
    }
    
    private int getMinDist(int a, int b) {
        int fa = panels[a - 1][0];
        int fb = panels[b - 1][0];
        if (fa == fb) return panelDist[a][b];
        else return panelDist[a][0] + Math.abs(fa - fb) + panelDist[0][b];  
    }
    
    private void setElevatorPos() {
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (grid[y][x] == '@') {
                    ey = y;
                    ex = x;
                    return;
                }
            }
        }
        return;
    }
    
    private void getPanelDist(int startPanel) {
        Queue<Pos> q = new ArrayDeque<>();
        int[][] gridDist = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(gridDist[i], -1);
        }
        
        int sy = 0, sx = 0;
        if (startPanel == 0) {  // 엘리베이터
            sy = ey;
            sx = ex;
        } else {  // panels[startPanel - 1] = [f, r, c]
            sy = panels[startPanel - 1][1] - 1;
            sx = panels[startPanel - 1][2] - 1;
        }
        
        q.offer(new Pos(sy, sx));
        gridDist[sy][sx] = 0;
        
        while (!q.isEmpty()) {
            Pos cur = q.poll();
            
            for (int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];
                
                if (!inRange(ny, nx)) continue;
                if (gridDist[ny][nx] != -1) continue;
                if (grid[ny][nx] == '#') continue;
                
                q.offer(new Pos(ny, nx));
                gridDist[ny][nx] = gridDist[cur.y][cur.x] + 1;
            }
        }
        
        // startPanel -> 엘리베이터
        panelDist[startPanel][0] = gridDist[ey][ex];
        panelDist[0][startPanel] = gridDist[ey][ex];
        
        // startPanel -> i번 패널
        for (int i = 1; i <= k; i++) {
            int py = panels[i - 1][1] - 1;
            int px = panels[i - 1][2] - 1;
            panelDist[startPanel][i] = gridDist[py][px];
        }
    }
    
    private boolean inRange(int y, int x) {
        return y >= 0 && y < n && x >= 0 && x < m;
    }
    
    static class Pos {
        int y, x;
        Pos(int y, int x) { this.y = y; this.x = x; }
    }
}