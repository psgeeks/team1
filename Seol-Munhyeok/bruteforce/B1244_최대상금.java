package src;

import java.io.*;
import java.util.*;

public class B1244_최대상금 {
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	
	static String next() throws Exception {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(br.readLine());
		}
		return st.nextToken();
	}
	
	static int nextInt() throws Exception {
		return Integer.parseInt(next());
	}
	
	static int N, maxSwapCount, maxAnswer, nums[];
	static List<int[]> combs;
	static HashSet<Integer>[] visited;
	
	static int arrToInt(int[] arr) {
		int num = 0;
		for (int a : arr) {
			num = num * 10 + a;
		}
		return num;
	}
	
	static void swap(int aIdx, int bIdx) {
		int temp = nums[aIdx];
		nums[aIdx] = nums[bIdx];
		nums[bIdx] = temp;
	}
	
	static void dfs(int swapCount) {
		int state = arrToInt(nums);
		if (!visited[swapCount].add(state)) return;  // 이미 봤으면 제외
		
		if (swapCount == maxSwapCount) {
			maxAnswer = Math.max(maxAnswer, arrToInt(nums));
			return;
		}
		
		for (int i = 0; i < combs.size(); i++) {
			int aIdx = combs.get(i)[0];
			int bIdx = combs.get(i)[1];
			
			swap(aIdx, bIdx);
			dfs(swapCount + 1);
			swap(aIdx, bIdx);  // 원상 복구
		}
	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		int T = nextInt();
		for (int tc = 1; tc <= T; tc++) {
			char[] numStr = next().toCharArray();
			N = numStr.length;
			nums = new int[N];
			for (int i = 0; i < N; i++) {
				nums[i] = numStr[i] - '0';
			}
			maxSwapCount = nextInt();
			
			maxAnswer = -1;
			visited = new HashSet[maxSwapCount + 1];
			for (int i = 0; i <= maxSwapCount; i++) {
				visited[i] = new HashSet<>();
			}
			
			combs = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				for (int j = i + 1; j < N; j++) {
					combs.add(new int[] {i, j});
				}
			}
			
			dfs(0);
			sb.append("#").append(tc).append(" ").append(maxAnswer).append("\n");
		}
		System.out.println(sb);
	}
}
