import java.util.*;

class Solution {

    final int[] dy = {-1, 0, 1, 0};
    final int[] dx = {0, 1, 0, -1};
    int M, N;
    int[][] isInfected;
    boolean[][] visited;
    List<Component> comps;

    public int containVirus(int[][] isInfected) {
        // 변수 초기화
        M = isInfected.length;
        N = isInfected[0].length;
        this.isInfected = new int[M][N];
        for (int i = 0; i < M; i++) {
            this.isInfected[i] = isInfected[i].clone(); 
        }
        
        int answer = 0;
        while (true) {
            comps = new ArrayList<>();
            visited = new boolean[M][N];
            int compCnt = getComponent();

            if (compCnt == 0) break;  // 격리되지 않은 컴포넌트가 없으면 종료

            // 이번 단계에서 격리할 컴포넌트 결정
            int maxFrontier = 0;
            int maxIdx = 0;
            for (int i = 0; i < comps.size(); i++) {
                int curFrontier = comps.get(i).frontier.size();
                if (curFrontier > maxFrontier) {
                    maxFrontier = curFrontier;
                    maxIdx = i;
                }
            }

            // 대상 컴포넌트 격리 (isInfected = 2)
            for (Cell cell : comps.get(maxIdx).cells) {
                this.isInfected[cell.y][cell.x] = 2;
            }
            answer += comps.get(maxIdx).perimeter;

            // 격리되지 않은 칸 확산
            for (int i = 0; i < comps.size(); i++) {
                if (i == maxIdx) continue;

                for (int key : comps.get(i).frontier) {
                    int y = key / N;
                    int x = key % N;
                    this.isInfected[y][x] = 1;
                }
            }
        }
        return answer;
    }

    // 격리되지 않은 컴포넌트의 개수를 반환
    int getComponent() {
        int compCnt = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j] && isInfected[i][j] == 1) {
                    bfs(i, j);
                    compCnt++;
                }
            }
        }
        return compCnt;
    }

    // 해당 컴포넌트의 frontier와 perimeter를 계산
    void bfs(int y, int x) {
        Queue<Cell> q = new ArrayDeque<>();
        q.offer(new Cell(y, x));
        visited[y][x] = true;

        Component comp = new Component();

        while (!q.isEmpty()) {
            Cell cur = q.poll();
            comp.cells.add(cur);

            for (int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];

                if (ny < 0 || ny >= M || nx < 0 || nx >= N) continue;
                
                if (!visited[ny][nx] && isInfected[ny][nx] == 1) {
                    q.offer(new Cell(ny, nx));
                    visited[ny][nx] = true;
                } else if (isInfected[ny][nx] == 0) {
                    comp.frontier.add(ny * N + nx);
                    comp.perimeter++;
                }
            }
        }
        comps.add(comp);
    }

    static class Cell {
        int y, x;
        Cell(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class Component {
        List<Cell> cells = new ArrayList<>();  
        Set<Integer> frontier = new HashSet<>();  // 다음 날 감염될 수 있는 중복 없는 0의 좌표
        int perimeter = 0;  // 그 frontier와 맞닿아 있는 경계선 수
    }
}

