import java.util.*;
class Solution {
    
    int n, m, sy, sx, ey, ex;
    char[][] board;
    int[][] visited;
    final int[] dy = {-1, 0, 1, 0};
    final int[] dx = {0, 1, 0, -1};
    
    public int solution(String[] board) {
        // board[] -> char[][] 배열로 변경
        n = board.length;
        m = board[0].length();
        this.board = new char[n][m];
        // 방문 배열 초기화 (미방문 = -1)
        visited = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(visited[i], -1);
        }
        // 시작 위치, 도착 위치 지정
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char ch = board[i].charAt(j);
                this.board[i][j] = ch;
                if (ch == 'R') {
                    sy = i; sx = j;
                } else if (ch == 'G') {
                    ey = i; ex = j;
                }
            }
        }
        
        // R에서부터 도착할 때까지 BFS 수행
        Queue<Pos> q = new ArrayDeque<>();
        q.offer(new Pos(sy, sx));
        visited[sy][sx] = 0;
        
        while (!q.isEmpty()) {
            Pos cur = q.poll();
            if (cur.y == ey && cur.x == ex) {
                return visited[ey][ex];
            }
            
            int py = -1, px = -1;
            for (int d = 0; d < 4; d++) {
                int cy = cur.y, cx = cur.x;
                
                while (possible(cy, cx)) {
                    py = cy; px = cx;
                    cy += dy[d];
                    cx += dx[d];
                }
                
                if (visited[py][px] == -1) {
                    q.offer(new Pos(py, px));
                    visited[py][px] = visited[cur.y][cur.x] + 1;
                }
            }
        }
        
        return -1;
    }
    
    private boolean possible(int y, int x) {
        return y >= 0 && y < n && x >= 0 && x < m && board[y][x] != 'D';
    }
    
    static class Pos {
        int y, x;
        Pos(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}