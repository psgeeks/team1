package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_2533_사회망서비스 {
	static int N;
	static List<Integer>[] adj;
	static int[][] dp;
	
	static void dfs(int u, int p) {
		dp[u][0] = 0;
		dp[u][1] = 1;
		
		for (int v : adj[u]) {
			if (v == p) continue;
			
			dfs(v, u);
			
			dp[u][0] += dp[v][1];
			dp[u][1] += Math.min(dp[v][0], dp[v][1]);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		adj = new ArrayList[N+1];
		for (int i=1; i<=N; i++) adj[i] = new ArrayList<>();
		
		for (int i=0; i<N-1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			adj[v].add(u);
		}
		
		dp = new int[N+1][2];
		
		dfs(1, 0);
		
		System.out.println(Math.min(dp[1][0], dp[1][1]));
		
		br.close();
	}
}
