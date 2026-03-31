package binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_12015_가장긴증가하는부분수열2 {
	static int N;
	static int[] arr, lis;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		lis = new int[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int size = 0;
		for (int x : arr) {
			int pos = lowerBound(lis, 0, size, x);
			lis[pos] = x;
			
			if (pos==size) size++;
		}
		
		System.out.println(size);
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
