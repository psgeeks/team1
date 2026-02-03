package dp;

import java.io.*;
import java.util.*;

/*
 * 
 * 메모리: 83756 kb / 실행시간:	452 ms
 * 
 * */

public class RemoteRobot_2169 {
	
	static int N, M;
	
	static final int[] dy = {0, 0, 1}; // 왼쪽, 오른쪽, 아래쪽
	static final int[] dx = {-1, 1, 0};
	
	static boolean inRange(int y, int x) {
		return y>=0 && y<N && x>=0 && x<M;
	}
	
	public static void main(String[] args) throws IOException {
		// 입력부
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		int[][] mars = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				mars[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 초기화
		
		int[][] dp = new int[N][M];
		int[] left = new int[M];
		int[] right = new int[M];
		
		for (int i = 0; i < N; i++) {
			if (i==0) { // 첫 행 dp 값은 정해져있다.
				dp[0][0] = mars[0][0];
				for (int j=1; j<M; j++) {
					dp[0][j] = dp[0][j-1] + mars[0][j];
				}
			}
			else Arrays.fill(dp[i], Integer.MIN_VALUE);
		}
		
		/*
	    * DP
	    * 이동방향 좌, 우, 하 
	    * 위로 이동이 불가능하므로 오른쪽으로 가는 경우 + 왼쪽으로 가는 경우를 고려한다.
	    * 이 때 내려오는 경우와 비교하며 가장 큰 값을 찾는다.
	    **/
		for (int i=1; i<N; i++) {
			right[0] = dp[i-1][0] + mars[i][0];
			left[M-1] = dp[i-1][M-1] + mars[i][M-1];
			
			for (int j=1; j<M; j++) {
				right[j] = Math.max(right[j-1], dp[i-1][j]) + mars[i][j];
				left[M-1-j] = Math.max(left[M-j], dp[i-1][M-1-j]) + mars[i][M-1-j];
			}
			
			for (int j=0; j<M; j++) {
				dp[i][j] = Math.max(right[j], left[j]);
			}
		}
		
		// 출력부
		System.out.println(dp[N-1][M-1]);
		br.close();
	}
}
