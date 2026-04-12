package beakjoon;

import java.util.*;
import java.io.*;

public class Sol3109 {

    static char[][] board;
    static int count;
    static int R, C;
    static int[][] offset = {{-1, 1}, {0, 1}, {1, 1}}; // {r, c}

    public static void main(String[] args) throws  IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        board = new char[R][C];

        for (int i = 0; i < R; i++) board[i] = br.readLine().toCharArray();

        for (int i = 0; i < R; i++) dfs(i, 0);

        System.out.println(count);
    }

    private static boolean dfs(int r, int c) {
        board[r][c] = 'o';

        if (c == board[0].length - 1) {
            count++;
            return true;
        }

        for (int i = 0; i < 3; i++) {
            int nr = r + offset[i][0];
            int nc = c + offset[i][1];

            if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
            if (board[nr][nc] != '.') continue;
            if (dfs(nr, nc)) return true;
        }
        return false;
    }
}
