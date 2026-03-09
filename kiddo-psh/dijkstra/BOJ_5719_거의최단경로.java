package dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_5719_거의최단경로 {
	static int N, M, S, D;
	
	static int[] dist;
	static boolean[][] removed;
	
	static List<Edge>[] adj;
	static List<Integer>[] parents;
	
	static Queue<Integer> q = new ArrayDeque<>();
	static PriorityQueue<Node> pq = new PriorityQueue<>();
	
	static class Edge {
		int to, w;
		Edge (int to, int w) {this.to=to; this.w=w;}
	}
	
	static class Node implements Comparable<Node>{
		int v, dist;
		Node (int v, int dist) {this.v=v; this.dist=dist;}
		public int compareTo (Node o) {
			return this.dist - o.dist;
		}
	}
	
	@SuppressWarnings("unchecked")
	static void init(BufferedReader br) throws IOException {
		adj = new ArrayList[N];
		for (int i=0; i<N; i++) adj[i] = new ArrayList<>();
		
		parents = new ArrayList[N];
		for (int i=0; i<N; i++) parents[i] = new ArrayList<>();
		
		dist = new int[N];
		removed = new boolean[N][N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			adj[u].add(new Edge(v, w));
		}
	}
	
	static void findBest() {
		pq.clear();
		pq.offer(new Node(S,0));
		
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[S] = 0;
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (dist[cur.v] < cur.dist) continue;
			
			for (Edge e : adj[cur.v]) {
				int next = e.to;
				int nextDist = cur.dist + e.w;
				
				if (dist[next] > nextDist) {
					dist[next] = nextDist;
					parents[next].clear();
					parents[next].add(cur.v);
					pq.offer(new Node(next, nextDist));
				} else if (dist[next]==nextDist) {
					parents[next].add(cur.v);
				}
			}
		}
	}
	
	static void removePath() {
		q.clear();
		q.offer(D);
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			for (int prev : parents[cur]) {
				if (removed[prev][cur]) continue;
				removed[prev][cur] = true;
				q.offer(prev);
			}
		}
	}
	
	static int findAnswer() {
		int ret = -1;
		
		pq.clear();
		pq.offer(new Node(S,0));
		
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[S] = 0;
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (cur.v == D){
				ret = cur.dist;
				break;
			}
			if (cur.dist > dist[cur.v]) continue;
			
			for (Edge e : adj[cur.v]) {
				int next = e.to;
				int nextDist = cur.dist + e.w;
				
				if (removed[cur.v][next]) continue;
				if (nextDist >= dist[next]) continue;
				
				dist[next] = nextDist;
				pq.offer(new Node(next, nextDist));
			}
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws IOException {
		StringBuilder output = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		while(!(N==0 && M==0)) {
			init(br);
			
			findBest();
			removePath();
			
			output.append(findAnswer()).append("\n");
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
		}
		System.out.println(output);
		br.close();
	}
	
	static void dbg(Object...objects) {
		System.out.println(Arrays.deepToString(objects));
	}
}
