package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
 * 메모리: 18,336 kb
 * 실행시간: 176 ms
 */
public class BOJ_1238_파티 {
	static int N, M, X;
	static List<Edge>[] graph;
	static List<Edge>[] reverseGraph;
	
	static PriorityQueue<Node> pq = new PriorityQueue<>();
	
	static class Edge {
		int to, w;
		Edge(int to, int w) {this.to=to; this.w=w;}
	}
	
	static class Node implements Comparable<Node> {
		int v, dist;
		Node(int v, int dist) {this.v=v; this.dist=dist;}
		public int compareTo (Node o) {
			return this.dist - o.dist; 
		}
	}
	
	static int[] dijkstra(List<Edge>[] graph, int start) {
		int[] dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		pq.clear();
		pq.offer(new Node(start, 0));
		
		dist[start] = 0;
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (cur.dist > dist[cur.v]) continue;
			
			for (Edge e : graph[cur.v]) {
				int next = e.to;
				int nextDist = cur.dist + e.w;
				
				if (dist[next] <= nextDist) continue;
				
				dist[next] = nextDist;
				pq.offer(new Node(next, nextDist));
			}
		}
		
		return dist;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[N+1];
		reverseGraph = new ArrayList[N+1];
		
		for (int i=1; i<=N; i++) {
			graph[i] = new ArrayList<>();
			reverseGraph[i] = new ArrayList<>();
		}
		
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
		
			graph[u].add(new Edge(v,w));
			reverseGraph[v].add(new Edge(u,w));
		}
		
		int[] distFromX = dijkstra(graph, X);
		int[] distToX = dijkstra(reverseGraph, X);
		
		int answer = 0;
		for (int i=1; i<=N; i++) {
			answer = Math.max(answer, distFromX[i]+distToX[i]);
		}
		System.out.println(answer);
		br.close();
	}
}
