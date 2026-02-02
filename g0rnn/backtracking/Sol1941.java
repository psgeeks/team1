package beakjoon;

import java.util.*;
import java.io.*;

public class Sol1941 {

    static int ans = 0;
    static char[][] board = new char[5][5];
    static int[][] offset = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static List<Integer> selected = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 5; i++) {
            board[i] = br.readLine().toCharArray(); // int 배열로 변경
        }
        br.close();

        comb(0, 0);
        System.out.println(ans);
    }

    private static void comb(int start, int depth) {
        if (depth == 7) {
            if (isValid()) {
                ans++;
            }
            return;
        }

        for (int i = start; i < 25; i++) {
            selected.add(i);
            comb(i + 1, depth + 1);
            selected.remove(selected.size() - 1);
        }
    }

    private static boolean isValid() {
        int cntS = 0;
        boolean[] check = new boolean[25];
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(selected.get(0));
        check[selected.get(0)] = true;

        int countVisited = 1;
        if (board[selected.get(0) / 5][selected.get(0) % 5] == 'S') cntS++;

        while (!q.isEmpty()) {
            int now = q.poll();
            int x = now % 5;
            int y = now / 5;

            for (int[] o : offset) {
                int nx = x + o[0];
                int ny = y + o[1];
                int next = ny * 5 + nx;

                if (nx < 0 || nx >= 5 || ny < 0 || ny >= 5) continue;
                if (!selected.contains(next)) continue;
                if (check[next]) continue;

                check[next] = true;
                q.offer(next);
                countVisited++;
                if (board[ny][nx] == 'S') cntS++;
            }
        }
        return countVisited == 7 && cntS >= 4;
    }
}
