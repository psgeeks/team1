package backtracking;

import java.util.*;

public class Solution_바이러스파이프 {
    static int n, answer;
    static List<int[]>[] adj;

    public int solution(int n, int infection, int[][] edges, int k) {
        this.n = n;

        adj = new ArrayList[n+1];
        for (int i=1; i<=n; i++) adj[i] = new ArrayList<>();

        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], type = edge[2];

            adj[x].add(new int[]{y, type});
            adj[y].add(new int[]{x, type});
        }

        boolean[] infected = new boolean[n+1];
        infected[infection] = true;

        backtrack(infected, k);

        return answer;
    }

    static void backtrack(boolean[] infected, int remaining) {
        int current = countInfected(infected);
        answer = Math.max(answer, current);

        if (current == n || remaining == 0) return;

        for (int type = 0; type <= 3; type++) {
            boolean[] nextInfected = spread(infected, type);

            if (countInfected(nextInfected) > current) {
                backtrack(nextInfected, remaining-1);
            }
        }
    }

    static boolean[] spread(boolean[] infected, int type) {
        boolean[] nextInfected =  infected.clone();
        Queue<Integer> q = new ArrayDeque<>();

        for (int i=1; i<=n; i++) {
            if (infected[i]) q.offer(i);
        }

        while(!q.isEmpty()) {
            int cur = q.poll();

            for (int[] edge : adj[cur]) {
                int next = edge[0], nType = edge[1];

                if (nextInfected[next]) continue;
                if (nType != type) continue;

                nextInfected[next] = true;
                q.offer(next);
            }
        }

        return nextInfected;
    }

    static int countInfected(boolean[] infected) {
        int count = 0;
        for (boolean b : infected) {
            if (b) count++;
        }
        return count;
    }
}