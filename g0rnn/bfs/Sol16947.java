package hello;

import java.io.*;
import java.util.*;

public class Sol16947 {
	
	/**
	 * 1. 차수가 1인 노드를 찾는다.
	 * 2. 차수가 1인 노드를 제거하는 것을 반복
	 * 3. 사이클 생성됨.!
	 * 4. 멀티소스 BFS
	 */
	
	static int n;
	static List<Edge>[] graph;
	static int[] indegree;
	static int[] dist;
	
	static class Edge {
		int from, to;
		Edge(int from, int to) {
			this.from=from;this.to=to;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		graph = new ArrayList[n+1];
		for (int i=1;i<=n;i++) graph[i] = new ArrayList<>();
		dist = new int[n+1];
		indegree = new int[n+1];
		
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			graph[u].add(new Edge(u, v));
			graph[v].add(new Edge(v, u));
			indegree[u]++;
			indegree[v]++;
		}
		
		Deque<Integer> q = new ArrayDeque<>();
		boolean[] visited = new boolean[n+1];
		for (int i = 1; i <= n; i++) 
			if (graph[i].size() == 1) {
				q.offer(i);
				indegree[i]--;
				visited[i] = true;
			}
		while (!q.isEmpty()) {
			int cur = q.poll();
			
			for (Edge adj : graph[cur]) {
				if (visited[adj.to]) continue;
				if (--indegree[adj.to] == 1) {
					q.offer(adj.to);
					visited[adj.to] = true;
				}
			}
		}
		
		Arrays.fill(dist, -1);
		for (int i = 1; i <= n; i++) {
			if (indegree[i] > 1) {
				q.offer(i);
				dist[i] = 0;
			}
		}
		while (!q.isEmpty()) {
			int cur = q.poll();
			
			for (Edge adj : graph[cur]) {
				if (dist[adj.to] != -1) continue;
				dist[adj.to] = dist[cur] + 1;
				q.offer(adj.to);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) sb.append(dist[i]).append(' ');
		System.out.println(sb);
	}
}
