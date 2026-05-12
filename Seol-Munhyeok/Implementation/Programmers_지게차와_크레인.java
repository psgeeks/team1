import java.util.*;
/**
세로 n, 가로 m
알파벳 대문자로 종류 구분
'지게차'는 '접근 가능한 컨테이너'만 꺼내기 가능 = 4면 중 적어도 1면이 외부와 연결됨
'크레인'은 외부와 연결되지 않은 컨테이너도 꺼낼 수 있음

특정 종류의 컨테이너의 요청이 들어오면 창고에서 접근 가능한 '모든' 종류의 컨테이너를 꺼낸다
알파벳 하나로만 요청이 들어오면 지게차로 -> 아직 창고에 요청한 컨테이너가 남아있을 수 있다.
    지금 이 순간의 모습만 봐야함 -> 하나 꺼냈더니 다시 외부와 연결되었다고 또 꺼낼 수 없음.
    storage 배열 테두리부터 탐색해서 빈칸이면 계속 확산 아니면 continue (이때 지워야할 칸이면 삭제 처리)
    삭제 처리는 배열을 '*'로 만들기
같은 알파벳이 두 개 이상 요청이 들어오면 크레인으로 -> 크레인으로 꺼내면 모두 없어짐

50 * 50 = 2500
요청 <= 100
250000 (매 요청마다 풀 스캔을 해도 충분히 가능함)
*/
class Solution {
    int n, m;
    int[] dy = {-1, 0, 1, 0};
    int[] dx = {0, 1, 0, -1};
    char[][] storage;
    
    public int solution(String[] storage, String[] requests) {
        // 입력 처리
        n = storage.length;
        m = storage[0].length();
        this.storage = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.storage[i][j] = storage[i].charAt(j);
            }
        }
        
        // 명령어 처리
        for (String req : requests) {
            if (req.length() == 1) {
                removeByCar(req.charAt(0));
            } else {
                removeByCrain(req.charAt(0));
            }
        }
        
        // 정답 계산
        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (this.storage[i][j] != '*') {
                    answer++;
                }
            }
        }
        return answer;
    }
    
    private void removeByCar(char ch) {
        Queue<Pos> q = new ArrayDeque<>();
        
        // 맵 가장자리 방문 처리
        boolean[][] visited = new boolean[n][m];
        for (int c = 0; c < m; c++) {
            q.offer(new Pos(0, c));
            visited[0][c] = true;
            q.offer(new Pos(n - 1, c));
            visited[n - 1][c] = true;
        }
        for (int r = 1; r < n - 1; r++) {
            q.offer(new Pos(r, 0));
            visited[r][0] = true;
            q.offer(new Pos(r, m - 1));
            visited[r][m - 1] = true;
        }
        
        List<Pos> removedPos = new ArrayList<>();
        while (!q.isEmpty()) {
            Pos cur = q.poll();
            // cur가 컨테이너면 확산 종료
            if (storage[cur.y][cur.x] == ch) {
                removedPos.add(cur);
                continue;
            } else if (storage[cur.y][cur.x] != '*') {
                continue;
            }
            // cur가 빈 공간 ('*')이면 확산
            for (int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];
                if (ny < 0 || ny >= n || nx < 0 || nx >= m) continue;
                if (visited[ny][nx]) continue;
                
                q.offer(new Pos(ny, nx));
                visited[ny][nx] = true;
            }    
        }
        
        // removedPos에 있는 좌표를 빈 칸으로 만들기
        for (Pos cur : removedPos) {
            storage[cur.y][cur.x] = '*';
        }
    }
    
    private void removeByCrain(char ch) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (storage[i][j] == ch) {
                    storage[i][j] = '*';
                }
            }
        }
    }
    
    static class Pos {
        int y, x;
        Pos(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}