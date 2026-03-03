package boj;

import java.io.*;
import java.util.*;

public class B4179_불_BFS2회 {
	static BufferedReader br;
	static StringTokenizer st;
	/*
	 * 메모리: 54076 kb, 시간 : 468 ms
	 */
	static String next() throws Exception {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(br.readLine());
		}
		return st.nextToken();
	}
	
	static int nextInt() throws Exception {
		return Integer.parseInt(next());
	}
	
	static final int INF = 1_000_000_000;
    static final int[] dy = {-1, 0, 1, 0};
    static final int[] dx = {0, 1, 0, -1};
    static int R, C;
    static char[][] map;
    static List<int[]> fires;
    static int[][] fireTime;  // 불이 도달한 시간
    static int[][] visited;   // 사람이 움직인 거리 (-1 = 미방문)
    
    static void checkFireSpreadTime(List<int[]> fires) {
    	Queue<int[]> q = new ArrayDeque<>();
    	fireTime = new int[R][C];
    	for (int i = 0; i < R; i++) Arrays.fill(fireTime[i], INF);
    	
    	// 초기 시작 불 기록
    	for (int[] fire : fires) {
    		int y = fire[0], x = fire[1];
    		q.offer(new int[] {y, x});
    		fireTime[y][x] = 0;
    	}
    	
    	while (!q.isEmpty()) {
    		int[] cur = q.poll();
    		int y = cur[0], x = cur[1];
    		int t = fireTime[y][x];
    		
    		for (int d = 0; d < 4; d++) {
    			int ny = y + dy[d], nx = x + dx[d];
    			int nt = t + 1;
    			
    			if (ny >= R || ny < 0 || nx >= C || nx < 0) continue;
    			if (map[ny][nx] == '#') continue;  // 벽
    			if (fireTime[ny][nx] > nt) {
    				fireTime[ny][nx] = nt;
    				q.offer(new int[] {ny, nx});
    			}
    		}
    	}
    }
    
    // >= 0 : 탈출 시간
    // -1 : IMPOSSIBLE
    static int bfs(int sy, int sx) {
    	Queue<int[]> q = new ArrayDeque<>();
    	visited = new int[R][C];
    	for (int i = 0; i < R; i++) Arrays.fill(visited[i], -1);
    	
    	q.offer(new int[] {sy, sx});
    	visited[sy][sx] = 0;
    	
    	while (!q.isEmpty()) {
    		int[] cur = q.poll();
    		int y = cur[0], x = cur[1];
    		int t = visited[y][x];
    		
    		for (int d = 0; d < 4; d++) {
    			int ny = y + dy[d], nx = x + dx[d];
    			int nt = t + 1;
    			
    			if (ny >= R || ny < 0 || nx >= C || nx < 0) return nt;  // 탈출
    			if (map[ny][nx] == '#') continue;  // 벽
    			if (visited[ny][nx] != -1) continue;  // 이미 방문
    			if (fireTime[ny][nx] <= nt) continue;  // 이미 불이 있을 때 방문
    			
    			q.offer(new int[] {ny, nx});
    			visited[ny][nx] = nt;
    		}
    	}
    	
    	return -1;  // 탈출 불가
    }
    
    public static void main(String[] args) throws Exception {

		br = new BufferedReader(new InputStreamReader(System.in));
		
		R = nextInt(); C = nextInt();
		map = new char[R][C];
		fires = new ArrayList<>();
		
		int sy = -1, sx = -1;
		
		for (int i = 0; i < R; i++) {
			String line = next();
			for (int j = 0; j < C; j++) {
				char ch = line.charAt(j);
				map[i][j] = ch;
				
				if (ch == 'J') {
					sy = i; sx = j;
					map[i][j] = '.';  // 사람이 있던 곳은 빈 곳으로
				}
				else if (ch == 'F') {
					fires.add(new int[] {i, j});
				}
			}
		}
		
		// 불이 번지는 시간을 기록
		checkFireSpreadTime(fires);
		
		int answer = bfs(sy, sx);
		
		if (answer == -1) System.out.println("IMPOSSIBLE");
		else System.out.println(answer);
	}
}
