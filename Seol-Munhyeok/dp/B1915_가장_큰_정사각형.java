package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1915_가장_큰_정사각형 {
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
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = nextInt();
		int m = nextInt();
		int[][] arr = new int[n][m];
		int[][] dp = new int[n][m];
		
		for (int i = 0; i < n; i++) {
			String line = next();
			for (int j = 0; j < m; j++) {
				arr[i][j] = line.charAt(j) - '0';
			}
		}
		
		// 초기값 설정
		for (int i = 0; i < n; i++) {
			dp[i][0] = arr[i][0];
		}
		for (int i = 0; i < m; i++) {
			dp[0][i] = arr[0][i];
		}
		
		// 테이블 채우기
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				if (arr[i][j] == 0) {
					dp[i][j] = 0;
				} else {
					dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
				}
			}
		}
		
		// 결과 출력 (직사각형의 넓이)
		int maxLen = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				maxLen = Math.max(maxLen, dp[i][j]);
			}
		}
		System.out.println(maxLen * maxLen);
	}
}
