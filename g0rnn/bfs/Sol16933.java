package g0rnn.bfs;

import java.util.*;
import java.io.*;

public class Sol16933 {
	static int n, m, k;
	static char[][] grid;
	static int[][] offset = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {0, 0}};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		grid = new char[n][m];
		
		for (int i = 0; i < n; i++) grid[i] = br.readLine().toCharArray();
		
		System.out.println(bfs());
	}
	
	private static int bfs() {
		Deque<Node> q = new ArrayDeque<>();
		boolean[][][][] visited = new boolean[n][m][2][k+1]; // {y, x, day/night, 깬 횟수}
		
		q.offer(new Node(0, 0, 0, 1, false));
		visited[0][0][0][0] = true;
		
		while (!q.isEmpty()) {
			Node cur = q.poll();
			int x = cur.x;
			int y = cur.y;
			int day = cur.isNight ? 1 : 0; // 낮이면 0, 밤이면 1
			int brokeCnt = cur.brokeCnt;
			int dist = cur.dist;
			int nextDay = day == 1 ? 0 : 1;
			
			if (x == m-1 && y == n-1) return dist;
			
			for (int d =0; d < 5; d++) {
				int nx = x + offset[d][0];
				int ny = y + offset[d][1];
				
				if (nx < 0 || ny < 0 || nx >= m || ny >= n) continue;
				
				// 지금 위치가 낮인데 옆에 벽이 있는 경우 부술 수 있는 거였음.
				// 낮인데 왜 이동 안되냐
				
				if (grid[ny][nx] == '1') { // 벽인 경우
					if (day == 1) {
						// 밤에는 부수지 못해서 이돈 안됨
						if (d == 4) {
							// 제자리
							if (visited[ny][nx][nextDay][brokeCnt]) continue;
							q.offer(new Node(nx, ny, brokeCnt, dist+1, !cur.isNight));
							visited[ny][nx][nextDay][brokeCnt] = true;
						} else continue;
					}
					if (brokeCnt >= k) continue;
					
					// 낮인 경우
					if (visited[ny][nx][1][brokeCnt+1]) continue;
					
					q.offer(new Node(nx, ny, brokeCnt+1, dist+1, !cur.isNight));
					visited[ny][nx][1][brokeCnt+1] = true;
					
				} else { // 벽이 아닌 경우
					if (visited[ny][nx][nextDay][brokeCnt]) continue;
					
					q.offer(new Node(nx, ny, brokeCnt, dist+1, !cur.isNight));
					visited[ny][nx][nextDay][brokeCnt] = true;
					
				}
			}
		}
		return -1;
	}
	
	static class Node {
		int x, y;
		int brokeCnt;
		int dist;
		boolean isNight;
		public Node(int x, int y, int brokeCnt, int dist, boolean isNight) {
			this.x = x;
			this.y =y;
			this.brokeCnt = brokeCnt;
			this.dist = dist;
			this.isNight = isNight;
		}
	}
}
