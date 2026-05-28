/**
 소문자 = 열쇠
 대문자 = 자물쇠
 4방향 탐색 (#(벽)이동, 격자 밖 이동 안됨)
 자물쇠 칸을 지나가는 조건 = 대응되는 열쇠를 가지고 있을 때만 가능
 (대문자와 소문자로 서로 대응됨, 1 <= k <= 6)
 모든 열쇠를 획득하기 위한 최소 이동 횟수 반환 (불가능 = -1)
 (자물쇠를 모두 열 필요는 없음)

최단 경로 => BFS
visited[y][x][keys] => 현재 가지고 있는 열쇠 정보를 bitmask로 관리
종료 조건 => keys = fullMask(111111)
불가능 조건 => fullMask가 나오지 않은 채로 while문 종료
 */

class Solution {
    
    int[] dy = {-1, 0, 1, 0};
    int[] dx = {0, 1, 0, -1};
    char[][] map;
    int[][][] visited;
    int m, n, sy, sx, keyCount;

    public int shortestPathAllKeys(String[] grid) {
        // 맵 정보 저장
        m = grid.length;
        n = grid[0].length();
        map = new char[m][n];
        keyCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = grid[i].charAt(j);
                if (map[i][j] == '@') {
                    sy = i;
                    sx = j;
                } else if (Character.isLowerCase(map[i][j])) {
                    keyCount++;
                }
            }
        }
        // BFS 수행
        Queue<Pos> q = new ArrayDeque<>();
        visited = new int[m][n][1 << keyCount];  // 30 * 30 * 2^6
        int fullMask = (1 << keyCount) - 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(visited[i][j], -1);
            }
        }

        q.offer(new Pos(sy, sx, 0));
        visited[sy][sx][0] = 0;
        while (!q.isEmpty()) {
            Pos cur = q.poll();
            if (cur.mask == fullMask) return visited[cur.y][cur.x][cur.mask];  // 모든 열쇠를 수집하면 종료

            for (int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];
                if (ny < 0 || ny >= m || nx < 0 || nx >= n) continue;
                if (map[ny][nx] == '#') continue;
                if (visited[ny][nx][cur.mask] != -1) continue;
                if (isLock(ny, nx) && ((cur.mask & (1 << charToIndex(map[ny][nx]))) == 0)) continue;

                if (isKey(ny, nx)) {
                    int nextMask = cur.mask | (1 << charToIndex(map[ny][nx]));
                    q.offer(new Pos(ny, nx, nextMask));
                    visited[ny][nx][nextMask] = visited[cur.y][cur.x][cur.mask] + 1;
                } else {
                    q.offer(new Pos(ny, nx, cur.mask));
                    visited[ny][nx][cur.mask] = visited[cur.y][cur.x][cur.mask] + 1;
                }
            }
        }
        return -1;
    }

    private boolean isKey(int y, int x) {
        return Character.isLowerCase(map[y][x]);
    }

    private boolean isLock(int y, int x) {
        return Character.isUpperCase(map[y][x]);
    }

    private int charToIndex(char ch) {
        if (Character.isLowerCase(ch)) return ch - 'a';
        else return ch - 'A';
    }

    static class Pos {
        int y, x, mask;
        Pos(int y, int x, int mask) {
            this.y = y;
            this.x = x;
            this.mask = mask;
        }
    }
}