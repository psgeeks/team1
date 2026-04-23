package bitMasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2098_외판원순회 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[][] graph = new int[N][N];

        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<N; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        final int INF = 1_000_000_000;
        int[][] dp = new int[1<<N][N];
        for (int i=0; i<(1<<N); i++) Arrays.fill(dp[i], INF);
        dp[1][0] = 0;

        for (int mask=0; mask < (1<<N); mask++) {
            for (int cur=0; cur<N; cur++) {
                if ((mask & (1<<cur)) == 0) continue;

                for (int next=0; next<N; next++) {
                    if ((mask & (1<<next)) != 0) continue;
                    if (graph[cur][next] == 0) continue;

                    int nextMask = mask | (1<<next);
                    dp[nextMask][next] = Math.min(
                            dp[nextMask][next],
                            dp[mask][cur] + graph[cur][next]);
                }
            }
        }

        int full = (1<<N) - 1;
        int ans = INF;
        for (int i=1; i<N; i++) {
            if (graph[i][0] == 0) continue;
            ans = Math.min(ans, dp[full][i] + graph[i][0]);
        }

        System.out.println(ans);
        br.close();
    }
}
