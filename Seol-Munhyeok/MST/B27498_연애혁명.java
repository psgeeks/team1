package boj;

import java.io.*;
import java.util.*;

public class B27498_연애혁명 {
	static BufferedReader br;
	static StringTokenizer st;
	
	static String next() throws Exception {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(br.readLine());
		}
		return st.nextToken();
	}
	
	static int nextInt() throws Exception {
		return Integer.parseInt(next());
	}
	
	static class Edge {
		int from, to, w;
		Edge(int from, int to, int w) { this.from = from; this.to = to; this.w = w; }
	}
	
	static int V, E;
	static int[] parent, size;
	static List<Edge> edges;
	
	static boolean union(int a, int b) {
		a = find(a);
		b = find(b);
		
		if (a == b) return false;
		
		// a가 항상 더 크게
		if (size[a] < size[b]) { int t; t = a; a = b; b = t; }
		
		parent[b] = a;
		size[a] += size[b];
		
		return true;
	}
	
	static int find(int x) {
		if (parent[x] == x) return x;
		return parent[x] = find(parent[x]);
	}
	
	static void makeSet(int V, List<Edge> connected) {
		parent = new int[V + 1];
		size = new int[V + 1];
		for (int i = 1; i <= V; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}
	
	static int kruskalMST(int V, List<Edge> connected) {
		makeSet(V, connected);
		
		int total = 0;
		// 이미 성사된 관계는 먼저 union
		for (Edge e : connected) {
			union(e.from, e.to);
			total += e.w;
		}
				
		edges.sort((a, b) -> Integer.compare(b.w, a.w));  // '최대' 스패닝 트리를 찾아아 함.
		
		for (Edge edge : edges) {
			if (!union(edge.from, edge.to)) continue;
			
			union(edge.from, edge.to);
			total += edge.w;
		}
		
		return total;
	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		V = nextInt(); E = nextInt();
		edges = new ArrayList<>();
		List<Edge> connected = new ArrayList<>();  // 이미 성사된 사랑 관계
		
		int sum = 0;
		for (int i = 0; i < E; i++) {
			int a = nextInt(); int b = nextInt();
			int c = nextInt(); int d = nextInt();
			edges.add(new Edge(a, b, c));
			sum += c;
			
			if (d == 1) connected.add(new Edge(a, b, c));
		}
		
		int maxSum = kruskalMST(V, connected);
		System.out.println(sum - maxSum);
	}
}
