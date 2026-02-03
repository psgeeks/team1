package boj;

import java.io.*;
import java.util.*;

public class B14510_퇴사_Brute {
	static BufferedReader br;
	static StringTokenizer st;
	
	static int N;
	static int maxResult = 0;
	static int[] T, P;
	
	static String next() throws Exception {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(br.readLine());
		}
		return st.nextToken();
	}
	
	static int nextInt() throws Exception {
		return Integer.parseInt(next());
	}
	
	static void dfs(int day, int sum) {
		// 기저 조건
		if (day >= N + 1) {
			maxResult = Math.max(maxResult, sum);
			return;
		}
		
		// 일하는 경우
		if (day + T[day] <= N + 1) {
			dfs(day + T[day], sum + P[day]);
		}
		
		// 일하지 않는 경우
		dfs(day + 1, sum);
	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		N = nextInt();
		T = new int[N + 1];
		P = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			T[i] = nextInt(); P[i] = nextInt();
		}
		
		dfs(1, 0);
		System.out.println(maxResult);
	}
}
