package 완전탐색_구현;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
 * 22736kb, 776ms
 */
public class BOJ_17143_낚시왕 {
	static int R, C, M, hunt;
	static shark[][] map;
	static int[] dr = {0, -1, 1, 0, 0};
	static int[] dc = {0, 0, 0, 1, -1};
	static PriorityQueue<shark> sharks;
	static class shark implements Comparable<shark>{
		int r, c, s, d, z;
		public shark(int r, int c, int s, int d, int z) {
			this.r = r;
			this.c = c;
			this.s = s;
			this.d = d;
			this.z = z;
		}
		public int compareTo(shark o) {
			return o.z - this.z;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		hunt = 0;
		map = new shark[R+1][C+1];
		sharks = new PriorityQueue<>();
		for(int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken()); //속력
			int d = Integer.parseInt(st.nextToken()); //이동방향
			int z = Integer.parseInt(st.nextToken()); //크기
			
			sharks.add(new shark(r, c, s, d, z));
		}
		
		for(int c = 1; c <= C; c++) {
			//상어맵 만들기
			while(!sharks.isEmpty()) {
				shark cur = sharks.poll();
				if(map[cur.r][cur.c] == null) { //처음 맵에 오는 상어, 제일 큰 상어
					map[cur.r][cur.c] = cur;
				}
			}
			//확인
			for(int r = 1; r <= R; r++) {
				if(map[r][c] != null) {
					hunt += map[r][c].z;
					map[r][c] = null;
					break;
				}
			}
			if(c == C) break;
			//상어 다시 큐에 넣어주기. 방향 바꾼거 
			for(int mr = 1; mr <= R; mr++) {
				for(int mc = 1; mc <= C; mc++) {
					if(map[mr][mc] != null) {
						shark cur = map[mr][mc];
						int move = cur.s;
						if(cur.d <= 2) {
							move = move%(2*R-2);
						}else {
							move = move%(2*C-2);
						}
						for(int i = 0; i < move; i++) {
							
							cur.r += dr[cur.d];
							cur.c += dc[cur.d];
							//벽 체크
							if(cur.d == 1 && cur.r == 0) {
								cur.d = 2;
								cur.r = 2;
							}else if(cur.d == 2 && cur.r == R + 1) {
								cur.d = 1;
								cur.r = R - 1;
							}else if(cur.d == 3 && cur.c == C + 1) {
								cur.d = 4;
								cur.c = C - 1;
							}else if(cur.d == 4 && cur.c == 0) {
								cur.d = 3;
								cur.c = 2;
							}
						}
						
						sharks.add(cur);
						
						map[mr][mc] = null;
					}
				}
			}
		}
		System.out.println(hunt);
	}
}
