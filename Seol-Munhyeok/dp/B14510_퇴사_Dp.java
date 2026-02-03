package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14510_퇴사_Dp {
	static BufferedReader br;
	static StringTokenizer st;
	
	static int N;
	static int[] T, P, dp;
	
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
		
		N = nextInt();
		T = new int[N + 1]; P = new int[N + 1];
		dp = new int[N + 2];  // dp[i] = i일부터 퇴사일까지 얻을 수 있는 최대 수익
		for (int i = 1; i <= N; i++) {
			T[i] = nextInt(); P[i] = nextInt();
		}
		
		for (int i = N; i >= 1; i--) {
			int nextDay = i + T[i];
			
			if (nextDay <= N + 1) {
				// 상담을 할 수 있는 경우: (상담 안 함) vs (상담 함)
				dp[i] = Math.max(dp[i + 1], P[i] + dp[nextDay]);
			} else {
				// 상담을 할 수 없는 경우: 다음날의 최댓값 그대로 가져옴
				dp[i] = dp[i + 1];
			}
		}
		
		System.out.println(dp[1]);
	}
}
