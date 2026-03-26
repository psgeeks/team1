package boj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class B1038_감소하는수 {
	
	static List<Long> arr;
	static boolean[] selected;
	
	static void dfs(int digit) {
		if (digit == 10) {
			StringBuilder sb = new StringBuilder();
			for (int i = 9; i >= 0; i--) {
				if (selected[i]) sb.append(i);
			}
			if (sb.length() > 0) {
				arr.add(Long.parseLong(sb.toString()));
			}
			return;
		}
		
		// 현재 digit 선택
		selected[digit] = true;
		dfs(digit + 1);
		
		// 현재 digit 미선택
		selected[digit] = false;
		dfs(digit + 1);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		arr = new ArrayList<>();
		selected = new boolean[10];
		
		dfs(0);
		
		arr.sort(null);
		
		if (N >= arr.size()) System.out.println(-1);
		else System.out.println(arr.get(N));
	}
}
