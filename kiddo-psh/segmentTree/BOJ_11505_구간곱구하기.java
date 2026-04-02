package segmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11505_구간곱구하기 {
	static int N, M, K;
	static int[] nums;
	static long[] tree;
	static final long MOD = 1_000_000_007;
	
	static void build(int node, int start, int end) {
		if (start == end) {
			tree[node] = nums[start];
			return;
		}
		
		int mid = (start+end)/2;
		build(node*2, start, mid);
		build(node*2+1, mid+1, end);
		
		tree[node] = (tree[node*2]*tree[node*2+1])%MOD;
	}
	
	static void update(int node, int start, int end, int target, int val) {
		if (target < start || end < target) return;
		
		if (start == end) {
			tree[node] = val;
			return;
		}
		
		int mid = (start+end)/2;
		update(node*2, start, mid, target, val);
		update(node*2+1, mid+1, end, target, val);
		
		tree[node] = (tree[node*2]*tree[node*2+1])%MOD;
	}
	
	static long query(int node, int start, int end, int l, int r) {
		if (r < start || end < l) return 1L;
		
		if (l <= start && end <= r) {
			return tree[node];
		}
		
		int mid = (start+end)/2;
		return (query(node*2, start, mid, l,r)*query(node*2+1,mid+1,end,l,r))%MOD;
	}
	
	public static void main(String[] args) throws IOException {
		StringBuilder output = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		nums = new int[N+1];
		tree = new long[4*N];
		
		for (int i=1; i<=N; i++) {
			nums[i] = Integer.parseInt(br.readLine());
		}
		
		build(1, 1, N);
		
		for (int i=0; i<M+K; i++) {
			st = new StringTokenizer(br.readLine());
			int Q = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			if (Q==1) {
				update(1,1,N,a,b); // a번째 수를 b로 바꿈
			} else {
				output.append(query(1,1,N,a,b)).append("\n"); // a~b 까지의 곱 
			}
		}
		System.out.println(output);
		br.close();
	}
}
