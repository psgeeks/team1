import java.util.*;

class Solution {
    
    int N, M;
    int[][] board;

    // 1: 오른쪽, 2: 아래, 3: 왼쪽, 4: 위
    int[] dr = {0, 0, 1, 0, -1};
    int[] dc = {0, 1, 0, -1, 0};
    
    public int[][] solution(int[][] board, int[][] commands) {
        this.N = board.length;
        this.M = board[0].length;
        this.board = new int[N][M];
        
        for (int i = 0; i < N; i++) {
            this.board[i] = board[i].clone();
        }
        
        for (int[] cmd : commands) {
            process(cmd[0], cmd[1]);
        }
        
        return this.board;
    }
    
    void process(int startApp, int dir) {
        Set<Integer> group = getGroup(startApp, dir);
        moveGroup(group, dir);
        
        while (true) {
            List<Integer> broken = findBrokenApps(dir);
            if (broken.isEmpty()) break;
            
            int brokenApp = broken.get(0);
            Set<Integer> newGroup = getGroup(brokenApp, dir);
            moveGroup(newGroup, dir);
        }
    }
    
    // startApp을 dir로 한 칸 이동 시 움직여야하는 앱을 반환
    Set<Integer> getGroup(int startApp, int dir) {
        Set<Integer> group = new HashSet<>();
        Queue<Integer> q = new ArrayDeque<>();
        
        group.add(startApp);
        q.offer(startApp);
        
        while (!q.isEmpty()) {
            int cur = q.poll();
            
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    if (board[r][c] != cur) continue;

                    int nr = (r + dr[dir] + N) % N;
                    int nc = (c + dc[dir] + M) % M;
                    
                    int next = board[nr][nc];
                    if (next != 0 && !group.contains(next)) {
                        group.add(next);
                        q.offer(next);
                    }
                }
            }
        }
        
        return group;
    }
    
    // group에 해당하는 앱을 dir로 한 칸 이동
    // 격자 밖을 넘어간 앱은 잘린 상태로 구현
    void moveGroup(Set<Integer> group, int dir) {
        List<Cell> cells = new ArrayList<>();
        
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (group.contains(board[r][c])) {
                    cells.add(new Cell(r, c, board[r][c]));
                }
            }
        }
        
        // 원래 있던 앱 지우기
        for (Cell cell : cells) {
            board[cell.r][cell.c] = 0;
        }
        
        // dir 방향으로 한 칸 이동
        for (Cell cell : cells) {
            int nr = (cell.r + dr[dir] + N) % N;
            int nc = (cell.c + dc[dir] + M) % M;
            board[nr][nc] = cell.id;
        }
    }
    
    // 현재 board 상태에서 격자를 넘어가 잘린 상태로 존재하는 앱들을 반환
    List<Integer> findBrokenApps(int dir) {
        List<Integer> broken = new ArrayList<Integer>();
        boolean[] added = new boolean[101];
        
        if (dir == 1 || dir == 3) {  // 좌우 이동
            for (int r = 0; r < N; r++) {
                int leftId = board[r][0];
                int rightId = board[r][M - 1];
                
                if (leftId == 0 || leftId != rightId) continue;
                
                boolean brokenInThisRow = false;
                for (int c = 0; c < M; c++) {
                    if (board[r][c] != leftId) {
                        brokenInThisRow = true;
                        break;
                    }
                }
                
                if (brokenInThisRow && !added[leftId]) {
                    broken.add(leftId);
                    added[leftId] = true;
                }
            }
        } else {  // 상하 이동
            for (int c = 0; c < M; c++) {
                int topId = board[0][c];
                int bottomId = board[N - 1][c];
                
                if (topId == 0 || topId != bottomId) continue;
                
                boolean brokenInThisCol = false;
                for (int r = 0; r < N; r++) {
                    if (board[r][c] != topId) {
                        brokenInThisCol = true;
                        break;
                    }
                }
                
                if (brokenInThisCol && !added[topId]) {
                    broken.add(topId);
                    added[topId] = true;
                }
            }
        }
        
        return broken;
    }
    
    static class Cell {
        int r, c, id;
        Cell(int r, int c, int id) {
            this.r = r;
            this.c = c;
            this.id = id; 
        }
    }
    
}