package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_17472_다리만들기 {
	// 기본 세팅
	static int n, m, k;
	static int[][] map;
	static final int INF = 1_000_000_000;
	
	static Queue<int[]> q = new ArrayDeque<>();
	
	static final int[] dr = {-1, 1, 0, 0};
	static final int[] dc = {0, 0, -1, 1};
	
	static class Edge implements Comparable<Edge>{
		int a, b, w;
		public Edge(int a, int b, int w) {
			this.a = a;
			this.b = b;
			this.w = w;
		}
		
		@Override
		public int compareTo(Edge o) {
			return this.w - o.w;
		}
	}
	
	static boolean inRange(int r, int c) {
		return r>=0 && r<n && c>=0 && c<m;
	}
	
	// 입력 받기
	static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		map = new int[n][m];
		
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j=0; j<m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	// 섬마다 번호 붙이기 2 ~ k번
	static void labeling() {
	    k = 1;

	    for (int i=0; i<n; i++) {
	        for (int j=0; j<m; j++) {
	            if (map[i][j] == 1) {
	                label(i, j, ++k);
	            }
	        }
	    }
	}
	static void label(int r, int c, int x) {
		q.clear();
		q.offer(new int[] {r, c});
		map[r][c] = x;
		
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int cr = cur[0];
			int cc = cur[1];
			
			for (int d=0; d<4; d++) {
				int nr = cr + dr[d];
				int nc = cc + dc[d];
				
				if (!inRange(nr,nc)) continue;
				if (map[nr][nc]!=1) continue;
				
				map[nr][nc] = x;
				q.offer(new int[] {nr, nc});
			}
		}
	}
	
	// 간선 만들기
	static List<Edge> buildEdges(int k) {
		int[][] best = new int[k+1][k+1];
		for (int i=0; i<k+1; i++) Arrays.fill(best[i], INF);
		
		for (int r=0; r<n; r++) {
			for (int c=0; c<m; c++) {
				int start = map[r][c];
				if (start <= 1) continue;
				
				for (int d=0; d<4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];
					int len = 0;
					
					while (inRange(nr, nc)) {
						int cur = map[nr][nc];
						
						if (cur==0) {
							len++;
							nr += dr[d];
							nc += dc[d];
							continue;
						}
						
						if (cur==start) break;
						
						if (len>=2) {
							int a = start, b = cur;
							if (best[a][b] > len) {
								best[a][b] = len;
								best[b][a] = len;
							}
						}
						
						break;
					}
				}
			}
		}
		
		List<Edge> edges = new ArrayList<>();
		for (int a=2; a<=k; a++) {
			for (int b=a+1; b<=k; b++) {
				if (best[a][b] < INF) 
					edges.add(new Edge(a, b, best[a][b]));
			}
		}
		
		return edges;
	}
	
	// Union-Find, kruskal 
	static int[] parent, rank;
	
	static void initDSU(int maxId) {
		parent = new int[maxId+1];
		rank = new int[maxId+1];
		for (int i=0; i<=maxId; i++) parent[i]=i;
	}
	
	static int find(int x) {
		if (x==parent[x]) return x;
		return parent[x] = find(parent[x]);
	}
	
	static boolean union(int a, int b) {
		a = find(a); b = find(b);
		if (a==b) return false;
		if (rank[a]<rank[b]) {int t = a; a = b; b = t;}
		
		parent[b] = a;
		if (rank[a]==rank[b]) rank[a]++;
		return true;
	}
	
	static int kruskal(List<Edge> edges, int K) {
		Collections.sort(edges);
		
		initDSU(K+1);
		
		int connected = 0;
		int sum = 0;
		
		for (Edge e : edges) {
			if (union(e.a, e.b)) {
				sum += e.w;
				connected++;
				if (connected == K-1) break;
			}
		}
		return (connected == K-1)? sum : -1;
	}
	
	public static void main(String[] args) throws IOException {
		init();

		labeling();

		List<Edge> edges = buildEdges(k);
	
		System.out.print(kruskal(edges, k-1));
		
	}
}
