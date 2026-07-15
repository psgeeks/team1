class Solution {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        int fullMask = (1 << n) - 1;
        boolean[][] visited = new boolean[n][1 << n];
        Queue<int[]> queue = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            int mask = 1 << i;
            queue.offer(new int[]{i, mask});
            visited[i][mask] = true;
        }
        
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int node = cur[0], mask = cur[1];
                if (mask == fullMask) return steps;
                for (int nei : graph[node]) {
                    int nextMask = mask | (1 << nei);
                    if (!visited[nei][nextMask]) {
                        visited[nei][nextMask] = true;
                        queue.offer(new int[]{nei, nextMask});
                    }
                }
            }
            steps++;
        }
        return -1; // unreachable (graph is connected)
    }
}