import java.io.*;
import java.util.*;
/*
 * 메모리: 72812 kb, 시간 : 236 ms
 */
public class B31445_부분달리기시합 {
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
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = nextInt(); int M = nextInt();
		
		List<Integer>[] adj = new List[N + 1];
		for (int i = 0; i <= N; i++) adj[i] = new ArrayList<>();
		
		int[] indegree = new int[N + 1];
		for (int i = 0; i < M; i++) {
			int u = nextInt(); int v = nextInt();
			adj[u].add(v);
			indegree[v]++;
		}
		
		Queue<Integer> q = new ArrayDeque<>();
		for (int u = 1; u <= N; u++) {
			if (indegree[u] == 0) q.offer(u);
		}
		
		// 위상 순서 구하기
		List<Integer> topoOrder = new ArrayList<>();
		while (!q.isEmpty()) {
			int cur = q.poll();
			topoOrder.add(cur);
			for (int next : adj[cur]) {
				indegree[next]--;
				if (indegree[next] == 0) q.offer(next);
			}
		}
		
		// ways[v] = v를 가장 느린(마지막) 사람으로 하는 chain의 개수
		int[] ways = new int[N + 1];
		Arrays.fill(ways, 1);  // 자기 자신 선택하는 경우
		
		final int MOD = 1_000_000_007;
		long answer = 0;
		
		// 위상 순서대로 bfs 수행하여 ways 배열을 채움
		for (int u : topoOrder) {
			answer = (answer + ways[u]) % MOD;
			
			Queue<Integer> q2 = new ArrayDeque<>();
			boolean[] visited = new boolean[N + 1];
			
			q2.offer(u);
			visited[u] = true;
			
			while (!q2.isEmpty()) {
				int cur = q2.poll();
				
				for (int next : adj[cur]) {
					if (!visited[next]) {
						visited[next] = true;
						q2.offer(next);
						
						ways[next] = (ways[next] + ways[u]) % MOD;
					}
				}
			}
		}
		System.out.println(answer);
	}
}
