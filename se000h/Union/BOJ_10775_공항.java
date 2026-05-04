import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_10775_공항 {
	static int G, P;
	static int[] parent;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		G = Integer.parseInt(br.readLine());
		P = Integer.parseInt(br.readLine());
		parent = new int[G+1];
		make_set();
		int count = 0;
		for(int p = 0; p < P; p++) {
			int k = Integer.parseInt(br.readLine());
			// 나 == 부모 그냥 반환, 나의 부모를 나-1로 바꾸기 union(나, 나-1)
			// 나 != 부모면 부모 찾아서 반환, 그 집합의 부모를 부모 -1 로 바꾸기 union(부모, 부모 -1)
			int k_parent = find_available(k); //현재 가능한 제일 큰 게이트 번호
			if(k_parent == 0) break;
			union(k_parent, k_parent-1);
			count++;
		}
		System.out.println(count);
	}
	static void make_set() {
		for(int g = 0; g <= G; g++) parent[g] = g;
	}
	static int find_available(int k) {
		if(parent[k] == k) return k;
		else return parent[k] = find_available(parent[k]);
		
	}
	static void union(int a, int b) {
		int p_a = find_available(a);
		int p_b = find_available(b);
		if(p_a == p_b) return;
		parent[p_a] = p_b;
 	}
}
