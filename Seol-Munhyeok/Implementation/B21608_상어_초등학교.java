package boj;

import java.io.*;
import java.util.*;

public class B21608_상어_초등학교 {
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
	
	static boolean isLiked(int s, int other) {
		for (int i = 0; i < 4; i++) {
			if (like[s][i] == other) return true;
		}
		return false;
	}
	
	static void placeStudent(int studentNum) {
		int bestY = -1, bestX = -1;
		int maxLike = -1, maxEmpty = -1;
		
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
				
				if (likeCnt > maxLike
					|| (likeCnt == maxLike && emptyCnt > maxEmpty)
					|| (likeCnt == maxLike && emptyCnt == maxEmpty && y < bestY)
					|| (likeCnt == maxLike && emptyCnt == maxEmpty && y == bestY && x < bestX)) {
					maxLike = likeCnt;
					maxEmpty = emptyCnt;
					bestY = y;
					bestX = x;
				}
			}
		}
		seat[bestY][bestX] = studentNum;
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
