package 완전탐색_구현;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_15683_감시 {
	static int N, M, K, min;
	static int[] cctvdir;
	static int[] dr = {-1, 0, 1, 0}; //상 우 하 좌  
	static int[] dc = {0, 1, 0, -1};
	static int[][] map;
	static List<cctv> cctvList = new ArrayList<>();
	static class cctv{
		int num, r, c;

		public cctv(int num, int r, int c) {
			super();
			this.num = num;
			this.r = r;
			this.c = c;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		min = Integer.MAX_VALUE;
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < M; c++) {
				int tmp = Integer.parseInt(st.nextToken());
				if(1<=tmp && tmp<=5) {
					cctvList.add(new cctv(tmp, r, c));
				}
				map[r][c] = tmp;
			}
		}
		K = cctvList.size();
		cctvdir = new int[K];
		dfs(0);
		System.out.println(min);
	}
	static void dfs(int n) {
		if(n == K) { 
			int cursum = install(cctvdir);
			if(min > cursum) {
				min = cursum;
			}
			return;
		}
		int cur = cctvList.get(n).num;
		if(cur == 1 || cur == 3 || cur == 4 ) {
			for(int i = 0; i < 4; i++) {
				cctvdir[n] = i;
				dfs(n+1);
			}
		}else if(cur == 2) {
			for(int i = 0; i < 2; i++) {
				cctvdir[n] = i;
				dfs(n+1);
			}
		}else { // cur == 5
			dfs(n+1);
			cctvdir[n] = 0;
		}
	}
	static int install(int[] cctvdir) {
		int sum = 0;
		int[][] newmap = new int[N][M];
		for(int n = 0; n < N; n++) newmap[n] = map[n].clone(); //
		for(int k = 0; k < K; k++) { 
			int num = cctvList.get(k).num;
			int r = cctvList.get(k).r;
			int c = cctvList.get(k).c;
			int dir = cctvdir[k]; //dir 0: 상, 1: 우, 2: 하, 3: 좌  (시계)
			if(num == 1) { // 
				fill(r, c, dir, newmap);
			}else if(num == 2) { //
				fill(r, c, dir, newmap);
				fill(r, c, (dir+2)%4, newmap);
			}else if(num == 3) { // 
				fill(r, c, dir, newmap);
				fill(r, c, (dir+1)%4, newmap);
			}else if(num == 4) { // 
				fill(r, c, dir, newmap);
				fill(r, c, (dir+1)%4, newmap);
				fill(r, c, (dir+2)%4, newmap);
			}else { //num == 5
				fill(r, c, 0, newmap);
				fill(r, c, 1, newmap);
				fill(r, c, 2, newmap);
				fill(r, c, 3, newmap);
			}	
		}
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < M; c++) {
				if(newmap[r][c] == 0) {
					sum++;
				}
			}
		}
		return sum;
	}
	static void fill(int r, int c, int dir, int[][] newmap) { //dir 1: 상, 2: 하, 3: 좌, 4:우   
		int nr = r;
		int nc = c;
		while(true) {
			nr = nr + dr[dir];
			nc = nc + dc[dir];
			if(nr < 0 || nr >= N || nc < 0 || nc >= M) break; // 범위  
			if(newmap[nr][nc] == 6) break; //벽 
			if(1 <= newmap[nr][nc] && newmap[nr][nc] <= 5) continue;//cctv일 
			newmap[nr][nc] = 7;
		}
	}
}