import java.io.*;
import java.util.*;

public class Sol9370 {
	static List<Edge>[] graph;
	static int n, m, t;
	static int s, g, h;
	static List<Integer> candi;
	static final int INF = Integer.MAX_VALUE;
	
	static class Edge {
		int to, cost;
		Edge(int to, int cost) {this.to=to;this.cost=cost;}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			t = Integer.parseInt(st.nextToken());
			
			candi = new ArrayList<>();
			graph = new ArrayList[n+1];
			for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
			
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			g = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
			
			int ghCost = -1;
			
			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int w = Integer.parseInt(st.nextToken());
				graph[u].add(new Edge(v, w));
				graph[v].add(new Edge(u, w));
				
				if ((u == g && v == h) || (v == g && u == h)) ghCost = w;
			}
			for (int i = 0; i < t; i++) candi.add(Integer.parseInt(br.readLine()));
			Collections.sort(candi);
			
			int[] distS = dijkstra(s); 
			int[] distG = dijkstra(g);
			int[] distH = dijkstra(h);
			
			for(int x : candi) {
				long path1 = (long) distS[g] + ghCost + distH[x];
				long path2 = (long) distS[h] + ghCost + distG[x];
				if (distS[x] == path1 || distS[x] == path2) sb.append(x).append(' ');
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}
	
	private static int[] dijkstra(int s) {
		PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
		int[] dist = new int[n+1];
		Arrays.fill(dist, INF);
		dist[s] = 0;
		pq.offer(new Edge(s, 0));
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			if (dist[cur.to] < cur.cost) continue;
			
			for (Edge adj : graph[cur.to]) {
				if (dist[adj.to] > dist[cur.to] + adj.cost) {
					dist[adj.to] = dist[cur.to] + adj.cost;
					pq.offer(new Edge(adj.to, dist[adj.to]));
				}
			}
		}
		return dist;
	}
}
