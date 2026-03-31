package binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_12015_가장긴증가하는부분수열2 {
	static int N;
	static int[] arr, lis, idx, prev;
	public static void main(String[] args) throws IOException {
		StringBuilder output = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		lis = new int[N];
		idx = new int[N];
		prev = new int[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int size = 0;
		for (int i=0; i<N; i++) {
			int x = arr[i];
			int pos = lowerBound(lis, 0, size, x);
			lis[pos] = x;
			idx[pos] = i;
			prev[i] = pos>0 ? idx[pos-1] : -1;
			
			if (pos==size) size++;
		}

		output.append(size).append("\n");
		
		int[] answer = new int[size];
		int cur = idx[size-1];
		for (int i=size-1; i>=0; i--) {
			answer[i] = arr[cur];
			if (prev[cur] == -1) break;
			cur = prev[cur];
		}
		
		for (int x : answer) {
			output.append(x).append(" ");
		}
		
		System.out.println(output);
		br.close();
	}
	
	static int lowerBound(int[] arr, int left, int right, int target) {
		while(left < right) {
			int mid = (left+right)/2;
			if (arr[mid] >= target) {
				right = mid;
			} else {
				left = mid+1;
			}
		}
		return left;
	}
}
