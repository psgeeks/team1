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
    static int[][] parent; // parent[i][j] : i의 2^j번째 조상
    static int[] depth;    // depth[i] : i노드의 깊이 
    static long[] dist;    // dist[i] : ROOT -> i노드까지 비용
    static int LOG;        // 최대 깊이

    static class Edge {
        int to, w; // 방향, 가중치 

        Edge (int to, int w) {
            this.to = to; this.w = w;
        }
    }

    /*
    # 트리의 전처리 과정
    1. 트리를 순회하며 노드들의 부모노드를 저장한다.
    2. 노드들의 깊이, 루트에서 해당노드로 가는 비용을 전부 저장한다.
    */
    static void dfs(int u, int p) {
        parent[u][0] = p; // 부모노드 저장 

        for (Edge e : adj[u]) {
            int v = e.to;
            if (v == p) continue;
            depth[v] = depth[u] + 1;
            dist[v] = dist[u] + (long)e.w;
            dfs(v, u);
        }
    }

    /*
    # parent 배열을 채우기 위한 과정
    # parent[i][j] = parent[parent[i][j-1]][j-1]
    # i의 2^j번째 조상 = i의 2^(j-1)번째 조상의 2^(j-1)번째 조상 
    */
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
        // 깊이가 큰 쪽을 u로 선택한다.
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        // 깊이의 차이만큼 u를 점프
        int diff = depth[u] - depth[v];
        for (int j=0; j<LOG; j++) {
            if ((diff & (1<<j)) != 0) {
                u = parent[u][j];
            }
        }

        // 깊이를 맞췄을 때 같은 노드라면 그 노드가 lca
        if (u==v) return u;

        /*
        # 깊이가 같지만 다른 노드라면 조상 노드로 점프해야한다.
        # 최대 높이부터 점프를 시도하는데 조상이 다를 때만 점프해야 한다.
        # lca가 되지 않는 모든 점프를 시도한다.
        # 점프 후 그 노드의 부모노드가 lca가 된다.
        */
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

    // k번째 노드가 lca - u 사이에 있는지 lca - v 사이에 있는지 판단해서 구한다.
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

        final int ROOT = 1; // 루트는 아무 노드나 잡아도 상관 없다.
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

