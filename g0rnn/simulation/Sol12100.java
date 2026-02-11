package beakjoon;

import java.util.*;
import java.io.*;

public class Sol12100 {
    // 5번 이동 & sout(만들 수 있는 가장 큰 블록)
    // 1 <= n <= 20

    // idea. 2차원 배열 6개를 만들자. << 이렇게 안하면 메모리가 얼마나 튈지 모르겠음
    // map[0][][] 은 맨 처음 한번도 이동안한 맵
    // map[1][][] 은 한번 이동을 시도한 맵



    static int[][][] map;
    static int n;
    static int max = Integer.MIN_VALUE;
    static int[][] offset = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        map = new int[6][n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) map[0][i][j] = Integer.parseInt(st.nextToken());
        }

        backtracking(0, 0);
    }

    private static void backtracking(int depth, int curSum) {
        // 여기에 가지치기 한판

        if (depth == 5) {
            max = Math.max(max, curSum);
            return;
        }

        for (int dir = 0; dir < 4; dir++) {
            // dir방향으로 한칸 땡겨 합침
            // gravity
            backtracking(depth+1, curSum + point);
            // 복구??
        }
    }

    // 내 이론으론 안됨.. 홀수면 대체 어카노
    private static void moveOne(int[][] prev, int[][] next, int dir) {
        int cnt = n;
        while (cnt-- > 0) {
            // 한 행 or 열을 1칸씩 이동


            int nr = row + offset[dir][1];
            int nc = col + offset[dir][0];
            if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;

            if (prev[nr][nc] == prev[row][col]) {
                next[nr][nc] = prev[row][col]*2;
                next[row][col] = 0;

            }
            // 여기서 rol = nr; row += offset 을 한번 더하고 모듈러
            // 그러면 사실상 두칸을 이동한건데, 그럼 홀수면 대참사가 남. 진짜로;
        }
    }

    // 일단 아래로 당겨봐
    private static void draw(int[][] prev, int[][] next) {
        for (int col = 0; col < n; col++) {
            int wRow = n-1; // 밑에서 채움, 채울곳의 row

            for (int row = n-1; row >= 0; row--) { // 땡길 녀석의 row
                if (prev[row][col] == 0) continue;
                if (wRow == row) continue;
                // 땡길 녀석과 채울 곳이 같은 숫자일 때
                if (prev[wRow][col] == prev[row][col]) {
                    next[wRow][col] = prev[row][col]*2;
                    wRow--;
                } else {
                    next[wRow][col] = prev[row][col]; // 걍 땡기면 wRow를 -1하면 안됨.
                }
            }

            // 다 땡겼는데 wRow가 범위를 넘지못했으면 0으로 채움.
        }
    }
}
