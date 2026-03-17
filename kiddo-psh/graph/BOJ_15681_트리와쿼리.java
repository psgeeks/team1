package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_15681_트리와쿼리 {
    static int N, R, Q;

    static List<Integer>[] adj;
    static int[] sub;
    static boolean[] visited;

    static int dfs(int u) {
        visited[u] = true;
        int size = 1;
        for (int v : adj[u]) {
            if (visited[v]) continue;
            size += dfs(v);
        }
        return sub[u] = size;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        adj = new ArrayList[N+1];
        for (int i=0; i<=N; i++) adj[i] = new ArrayList<>();

        for (int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int U = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());

            adj[U].add(V);
            adj[V].add(U);
        }

        sub = new int[N+1];
        visited = new boolean[N+1];

        dfs(R);

        StringBuilder output = new StringBuilder();
        for (int i=0; i<Q; i++) {
            output.append(sub[Integer.parseInt(br.readLine())]).append("\n");
        }
        System.out.println(output);
        br.close();
    }
}
