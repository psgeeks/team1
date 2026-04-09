package 완전탐색_구현;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 20748kb	300ms
 */
public class BOJ_20055_컨베이어벨트위의로봇 {
	static int N, K;
	static int[][] belt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		belt = new int[2*N][2];
		int len = 2*N;
		st = new StringTokenizer(br.readLine());
		for(int n = 0; n < 2*N; n++) {
			belt[n][0] = Integer.parseInt(st.nextToken());
			belt[n][1] = 0;
		}
		int s = 1; //단계
		
		while(true) {
			//회전
			int[] tmp = {belt[len-1][0], belt[len-1][1]};
			for(int t = len-1; t > 0; t--) {
				belt[t][0] = belt[t-1][0];
				belt[t][1] = belt[t-1][1];
			}
			belt[0][0] = tmp[0];
			belt[0][1] = tmp[1];
			//로봇내리기  
			belt[N-1][1] = 0;
			//로봇이동  
			for(int r = N - 2; r >= 0; r--) {
				if(belt[r][1] == 1 && belt[r+1][1] == 0 && belt[r+1][0] > 0) { //현재 칸에 로봇 있음, 다음칸 로봇 없음  
					belt[r+1][1] = 1;
					belt[r+1][0]--;
					belt[r][1] = 0;
				}
			}
			belt[N-1][1] = 0;
			//로봇 올리기
			if(belt[0][0] > 0) {
				belt[0][0]--;
				belt[0][1] = 1;
			}
			//내구도 확인
			int k = 0;
			for(int i = 0; i < len; i++) {
				if(belt[i][0] == 0) k++;
			}
			if(k >= K) break;
			s++;
		}
		System.out.println(s);
	}
}
