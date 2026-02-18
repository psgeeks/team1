package boj;

import java.io.*;
import java.util.*;

public class B21608_상어_초등학교_Sort풀이 {
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
	
	static int N, V;
	static int[][] seat;
	static int[][] like;
	static int[] order;
	static int[] dy = {-1, 0, 1, 0};
	static int[] dx = {0, 1, 0, -1};
	
	static class Cand {
		int y, x, like, empty;
		Cand(int y, int x, int like, int empty) {
	        this.y = y; this.x = x; this.like = like; this.empty = empty;
	    }
	}
	
	static boolean isLiked(int s, int other) {
		for (int i = 0; i < 4; i++) {
			if (like[s][i] == other) return true;
		}
		return false;
	}
	
	static void placeStudent(int studentNum) {
		List<Cand> cands = new ArrayList<>();
		
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				if (seat[y][x] != 0) continue;
				
				int likeCnt = 0, emptyCnt = 0;
				
				for (int dir = 0; dir < 4; dir++) {
					int ny = y + dy[dir], nx = x + dx[dir];
					if (ny >= N || ny < 0 || nx >= N || nx < 0) continue;
					
					if (seat[ny][nx] == 0) emptyCnt++;
					else if (isLiked(studentNum, seat[ny][nx])) likeCnt++;
				}
				
				cands.add(new Cand(y, x, likeCnt, emptyCnt));
			}
		}
		
		Collections.sort(cands, (a, b) -> {
			if (a.like != b.like) return b.like - a.like;  // like 내림차순
			if (a.empty != b.empty) return b.empty - a.empty;  // empty 내림차순
			if (a.y != b.y) return a.y - b.y;  // y 오름차순
			return a.x - b.x;  // x 오름차순
		});
		
		Cand best = cands.get(0);
	    seat[best.y][best.x] = studentNum;
	}
	
	static int score(int c) {
	    if (c == 0) return 0;
	    int v = 1;
	    for (int i = 1; i < c; i++) v *= 10;
	    return v;
	}
	
	static int getPoints() {
		int result = 0;
		
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				int likeCnt = 0;
				for (int dir = 0; dir < 4; dir++) {
					int ny = y + dy[dir], nx = x + dx[dir];
					if (ny >= N || ny < 0 || nx >= N || nx < 0) continue;
					if (isLiked(seat[y][x], seat[ny][nx])) likeCnt++;
				}
				result += score(likeCnt);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		N = nextInt();
		V = N * N;
		
		seat = new int[N][N];  // 학생의 자리
		like = new int[V + 1][4];  // 학생별 좋아하는 4명
		order = new int[V];  // 자리를 배치할 순서

		for (int i = 0; i < V; i++) {
			int a = nextInt();
			order[i] = a;
			for (int j = 0; j < 4; j++) {
				like[a][j] = nextInt();
			}
		}
		
		// 정해진 순서대로 학생들을 배치
		for (int i = 0; i < V; i++) {
			placeStudent(order[i]);
		}
		
		// 만족도 계산
		System.out.print(getPoints());
	}
}
