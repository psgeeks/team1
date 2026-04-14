package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B1949_우수마을 {
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
	
	static int N;
	static int[] arr, d1, d2;
	static List<Integer>[] tree;
	
	static void dfs(int cur, int par) {
		d1[cur] = arr[cur];
		d2[cur] = 0;
		for (int child : tree[cur]) {
			if (child == par) continue;
			
			dfs(child, cur);
			
			d1[cur] += d2[child];
			d2[cur] += Math.max(d1[child], d2[child]);
		}
	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		N = nextInt();
		arr = new int[N + 1];  // 1-based
		tree = new List[N + 1];
		for (int i = 1; i <= N; i++) {
			tree[i] = new ArrayList<>();
		}
		
		for (int i = 1; i <= N; i++) {
			arr[i] = nextInt();
		}
		
		for (int i = 0; i < N - 1; i++) {
			int a = nextInt();
			int b = nextInt();
			tree[a].add(b);
			tree[b].add(a);
		}
		
		d1 = new int[N + 1];  // i번 정점이 우수 마을일 때, 서브 트리에서 우수 마을 주민 수의 총 합의 최대
		d2 = new int[N + 1];  // i번 정점이 우수 마을이 아닐 때, 서브 트리에서 우수 마을 주민 수의 총합의 최대
		dfs(1, 0);
		
		System.out.println(Math.max(d1[1], d2[1]));
	}
}
