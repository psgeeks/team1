package bitMasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1311_할일정하기1 {
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
        int size = 1<<N;
        int[] dp = new int[size];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        for (int mask=0; mask<size; mask++) {
            int person = Integer.bitCount(mask);
            if (person == N) continue;

            for (int job=0; job<N; job++) {
                if ((mask & (1<<job)) != 0) continue;
                int nextMask = mask | (1<<job);
                dp[nextMask] = Math.min(dp[nextMask], dp[mask] + graph[person][job]);
            }
        }

        System.out.println(dp[size-1]);
        br.close();
    }
}
