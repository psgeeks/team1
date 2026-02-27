package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

//29308kb, 240ms
public class Main_B_17141_연구소2	 {
	static int N, M, vcnt, zcnt, min;
	static int[][] map, cand, virus, visited;
	static int [] dr = {0, 0, 1, -1};
	static int [] dc = {1, -1, 0, 0};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		cand = new int[10][2]; // 바이러스 후보 위치  
		virus = new int[M][2]; // 바이러스 결정 위
		visited = new int[N][N];
		min = Integer.MAX_VALUE;
		
		vcnt = 0; //후보 위치의 개수 
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < N; c++) {
				int tmp = Integer.parseInt(st.nextToken());
				if(tmp == 2) {// 바이러스(2)인곳
					cand[vcnt][0] = r;
					cand[vcnt++][1] = c;
				}else if(tmp == 0) { //0 인 
					zcnt++;
				}
				map[r][c] = tmp;
			}
		}
		
		//바이러스 후보 위치 조합
		dfs(0,0);
		if(min == Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
		System.out.println(min);
		}
		
	}
	
	static void clearvisited() {
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				visited[r][c] = -1;
			}
		}
	}
	
	static void dfs(int n, int s) {
		if(n == M) {
			clearvisited();
			int t = bfs();
			if(t < min) min = t;
			return;
		}
		for(int j = s; j < vcnt; j++) {
			virus[n][0] = cand[j][0];
			virus[n][1] = cand[j][1];
			dfs(n+1, j+1);
		}
	}
	static int bfs() {
		int max = 0;
		int infected = 0;
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		for(int l = 0; l < M; l++) {  
			queue.offer(virus[l]);
			visited[virus[l][0]][virus[l][1]] = 0;
		}
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int r = cur[0];
			int c = cur[1];
			if(visited[r][c] >= min) return min; //가지치기
			for(int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
				if(visited[nr][nc] != -1) continue; //방문 확인
				if(map[nr][nc] == 1) continue; // 벽 확인
				//방문 가능한 곳들 
				visited[nr][nc] = visited[r][c] + 1;
				queue.offer(new int[] {nr, nc});
				infected++; 
				max = visited[nr][nc];
				
			}
		}
		if(infected + vcnt - 2 != zcnt) { //전부 방문하지 못했을 때
			return Integer.MAX_VALUE;
		}
		return max;
	}
}

