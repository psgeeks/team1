package boj;

import java.io.*;
import java.util.*;

public class B4179_불_levelBFS {
	static BufferedReader br;
	static StringTokenizer st;
	/*
	 * 메모리: 49864 kb, 시간 : 484 ms
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
	
    static final int[] dy = {-1, 0, 1, 0};
    static final int[] dx = {0, 1, 0, -1};
    static int R, C;
    static int[][] humanTime;
    static Queue<int[]> fireQ, humanQ;
    static char[][] map;
    static boolean[][] infected;
    
    static void fireSpreadOneSecond() {
    	int size = fireQ.size();
    	
    	for (int s = 0; s < size; s++) {
    		int[] cur = fireQ.poll();
    		int y = cur[0], x = cur[1];
    		
    		for (int d = 0; d < 4; d++) {
    			int ny = y + dy[d], nx = x + dx[d];
    			if (ny >= R || ny < 0 || nx >= C || nx < 0) continue;
    			if (map[ny][nx] == '#') continue;
    			if (infected[ny][nx]) continue;
    			
    			fireQ.offer(new int[] {ny, nx});
    			infected[ny][nx] = true;
    		}
    	}
    	
    }
    
    // >= 0 : 탈출 시간
    // -1 : IMPOSSIBLE
    static int solve(int sy, int sx) {
    	// 시작과 동시에 불인 경우 불가능
    	if (infected[sy][sx]) return -1;
    	
    	humanQ.clear();
    	for (int i = 0; i < R; i++) Arrays.fill(humanTime[i], -1);  // -1 = 미방문
    	
    	humanQ.offer(new int[] {sy, sx});
    	humanTime[sy][sx] = 0;
    	
    	int time = 0;
    	while (!humanQ.isEmpty()) {
    		// time -> (time + 1) : 불 확산 
    		fireSpreadOneSecond();
    		
    		int size = humanQ.size();
    		
    		for (int s = 0; s < size; s++) {
    			// time -> (time + 1) : 사람 이동
    			int[] cur = humanQ.poll();
    			int y = cur[0], x = cur[1];
    			for (int d = 0; d < 4; d++) {
    				int ny = y + dy[d], nx = x + dx[d];
    				
    				if (ny >= R || ny < 0 || nx >= C || nx < 0) return time + 1;  // 탈출
    				if (map[ny][nx] == '#') continue;  // 벽
    				if (humanTime[ny][nx] != -1) continue;  // 짧은 경로로 이미 방문
    				
    				if (infected[ny][nx]) continue;
    				
    				humanQ.offer(new int[] {ny, nx});
    				humanTime[ny][nx] = humanTime[y][x] + 1;
    			}
    		}
    		
    		time++;  // 한 레벨 탐색 끝나고 시간 증가
    	}
    	
    	return -1;
    	
    }
    
    public static void main(String[] args) throws Exception {

		br = new BufferedReader(new InputStreamReader(System.in));
		R = nextInt(); C = nextInt();
		map = new char[R][C];
		fireQ = new ArrayDeque<>();
		humanQ = new ArrayDeque<>();
		infected = new boolean[R][C];
		humanTime = new int[R][C];
		
		int sy = -1; int sx = -1;
		
		for (int i = 0; i < R; i++) {
			String line = next();
			for (int j = 0; j < C; j++) {
				char ch = line.charAt(j);
				map[i][j] = ch;
				
				if (ch == 'J') {
					sy = i; sx = j;
					map[i][j] = '.';  // 시작 위치는 빈칸으로
				}
				else if (ch == 'F') {
					fireQ.offer(new int[] {i, j});
					infected[i][j] = true;
				}
			}
		}
		
		int answer = solve(sy, sx);
		if (answer == -1) System.out.println("IMPOSSIBLE");
		else System.out.println(answer);
	}
}
