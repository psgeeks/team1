package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_13511_트리와쿼리2 {
    static BufferedReader br;
    static StringTokenizer st;
    static int N, M;
    static List<Edge>[] adj;
    static int[][] parent;
    static int[] depth;
    static long[] dist;
    static int LOG;

    static class Edge {
        int to, w;

        Edge (int to, int w) {
            this.to = to; this.w = w;
        }
    }

    static void dfs(int u, int p) {
        parent[u][0] = p;

        for (Edge e : adj[u]) {
            int v = e.to;
            if (v == p) continue;
            depth[v] = depth[u] + 1;
            dist[v] = dist[u] + (long)e.w;
            dfs(v, u);
        }
    }

    static void fillParent() {
        for (int j=1; j<LOG; j++) {
            for (int i=1; i<=N; i++) {
                parent[i][j] = parent[parent[i][j-1]][j-1];
            }
        }
    }

    @SuppressWarnings("unchecked")
    static void setAdj() throws IOException {
        adj = new ArrayList[N+1];
        for (int i=1; i<=N; i++) adj[i] = new ArrayList<>();

        for (int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            adj[u].add(new Edge(v, w));
            adj[v].add(new Edge(u, w));
        }
    }

    static int lca (int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];
        for (int j=0; j<LOG; j++) {
            if ((diff & (1<<j)) != 0) {
                u = parent[u][j];
            }
        }

        if (u==v) return u;

        for (int j=LOG-1; j>=0; j--) {
            if (parent[u][j] != parent[v][j]) {
                u = parent[u][j];
                v = parent[v][j];
            }
        }

        return parent[u][0];
    }

    static long getCost (int u, int v) {
        int LCA = lca(u, v);

        return dist[u] + dist[v] - 2L*dist[LCA];
    }

    static int findNode(int u, int v, int k) {
        int LCA = lca(u, v);
        int len1 = depth[u] - depth[LCA] + 1;

        if (k <= len1) {
            return lift(u, k-1);
        }

        int len2 = depth[v] - depth[LCA];
        return lift(v, len2 - (k-len1));

    }

    static int lift(int u, int up) {
        for (int j=0; j<LOG; j++) {
            if ((up & (1<<j)) != 0) u = parent[u][j];
        }
        return u;
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        setAdj();

        LOG = (int)(Math.log(N)/Math.log(2))+1;
        parent = new int[N+1][LOG];
        depth = new int[N+1];
        dist = new long[N+1];

        final int ROOT = 1;
        depth[ROOT] = 1;
        dist[ROOT] = 0;

        dfs (ROOT, 0);
        fillParent();

        StringBuilder output = new StringBuilder();
        M = Integer.parseInt(br.readLine());
        for (int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int q = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            if (q==2) {
                int k = Integer.parseInt(st.nextToken());
                output.append(findNode(u, v, k)).append("\n");
            } else {
                output.append(getCost(u, v)).append("\n");
            }
        }

        System.out.println(output);
        br.close();
    }
}

